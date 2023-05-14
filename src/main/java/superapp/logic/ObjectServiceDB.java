package superapp.logic;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import superapp.Converter;
import superapp.boundries.Location;
import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.data.SuperAppObjectEntity;

import java.util.*;

@Service
public class ObjectServiceDB implements ObjectServiceWithPagination {

	private SupperAppObjectCrud objectCrud;
	private Converter converter;
	private String nameFromSpringConfig;

	@Value("${spring.application.name:defaultName}")
	public void setNameFromSpringConfig(String nameFromSpringConfig) {
		this.nameFromSpringConfig = nameFromSpringConfig;
	}

	@PostConstruct
	public void init() {
		System.err.println("**** spring.application.name = " + this.nameFromSpringConfig + " ****");
	}

	@Autowired
	public void setObjectCrud(SupperAppObjectCrud objectCrud) {
		this.objectCrud = objectCrud;
	}

	@Autowired
	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	@Override
	public SuperAppObjectBoundary createObject(SuperAppObjectBoundary object) {
		// List<SuperAppObjectEntity> entities = this.objectCrud.findAll();
		// String internalObjectId;
//        if (entities.isEmpty())
//            internalObjectId = "1";
//        else
		// internalObjectId = Integer.toString(entities.size() + 1);
		String internalObjectId = UUID.randomUUID().toString(); // the value is random so the size of entities isn't
																// relevant
		// object.setObjectId(new ObjectId(nameFromSpringConfig));
		object.setObjectId(new ObjectId(nameFromSpringConfig, internalObjectId));
		object.setCreationTimestamp(new Date());
		if (object.getObjectDetails() == null)
			object.setObjectDetails(new HashMap<>());
		SuperAppObjectEntity entity = this.converter.superAppObjectToEntity(object);
		entity = this.objectCrud.save(entity);
		return this.converter.superAppObjectToBoundary(entity);
	}

	@Override
	public SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId,
			SuperAppObjectBoundary update) {
		String objectId = objectSuperApp + "#" + internalObjectId;
		SuperAppObjectEntity existing = this.objectCrud.findById(objectId)
				.orElseThrow(() -> new RuntimeException("could not find object by id: " + objectId));
		if (update.getType() != null)
			existing.setType(update.getType());
		if (update.getAlias() != null)
			existing.setAlias(update.getAlias());
		if (update.getActive() != null)
			existing.setActive(update.getActive());
		if (update.getLocation() != null) {
			existing.setLng(update.getLocation().getLng());
			existing.setLat(update.getLocation().getLat());
		}
		if (update.getObjectDetails() != null)
			// existing.setObjectDetails(this.converter.toEntity(update.getObjectDetails()));
			existing.setObjectDetails(update.getObjectDetails());
		this.objectCrud.save(existing);
		return this.converter.superAppObjectToBoundary(existing);
	}

	@Override
	public SuperAppObjectBoundary getSpecificObject(String objectSuperApp, String internalId) {
		String objectId = objectSuperApp + "#" + internalId;
		SuperAppObjectEntity existing = this.objectCrud.findById(objectId)
				.orElseThrow(() -> new RuntimeException("could not find object by id: " + objectId));
		return this.converter.superAppObjectToBoundary(existing);
	}

	@Override
	public List<SuperAppObjectBoundary> getAllObjects() {
		List<SuperAppObjectEntity> entities = this.objectCrud.findAll();
		List<SuperAppObjectBoundary> rv = new ArrayList<SuperAppObjectBoundary>();
		for (SuperAppObjectEntity e : entities) {
			rv.add(this.converter.superAppObjectToBoundary(e));
		}
		return rv;
	}

	@Override
	public void deleteAllObjects() {
		this.objectCrud.deleteAll();
	}

	@Override
	public void relateParentToChild(ObjectId parentObjectId, ObjectId childObjectId) {
		String parentId = parentObjectId.getSuperapp() + "#" + parentObjectId.getInternalObjectId();
		String childId = childObjectId.getSuperapp() + "#" + childObjectId.getInternalObjectId();
		SuperAppObjectEntity child = this.objectCrud.findById(childId)
				.orElseThrow(() -> new NotFoundException("could not find child object by id: " + childId));

		SuperAppObjectEntity parent = this.objectCrud.findById(parentId)
				.orElseThrow(() -> new NotFoundException("could not find parent object by id: " + parentId));

		child.setParentObject(parent);

		this.objectCrud.save(child);
	}

	@Override
	public List<SuperAppObjectBoundary> getAllChildrenOfObject(ObjectId parentId) {
		String id = parentId.getSuperapp() + "#" + parentId.getInternalObjectId();
		List<SuperAppObjectEntity> parentEntities = this.objectCrud.findAllByParentObject(id);

		List<SuperAppObjectBoundary> rv = new ArrayList<>();

		for (SuperAppObjectEntity entity : parentEntities) {
			rv.add(this.converter.superAppObjectToBoundary(entity));
		}
		return rv;
	}

	@Override
	public List<SuperAppObjectBoundary> getAllParentsOfObject(ObjectId child) {
		List<SuperAppObjectBoundary> allParents = new ArrayList<SuperAppObjectBoundary>();

		String childId = child.getSuperapp() + "#" + child.getInternalObjectId();
		SuperAppObjectEntity childEntity = this.objectCrud.findById(childId)
				.orElseThrow(() -> new NotFoundException("could not find object by id: " + childId));

		// SuperAppObjectEntity parentEntity = childEntity.getParentObject();
		List<SuperAppObjectEntity> parentEntities = childEntity.getParentObject();
		if (parentEntities != null) {
			for (SuperAppObjectEntity entity : parentEntities) {
				allParents.add(Optional.of(this.converter.superAppObjectToBoundary(entity)).get());
			}
		} else {
			// allParents.add(new SuperAppObjectBoundary());
		}
		return allParents;
	}

	@Override
	public List<SuperAppObjectBoundary> searchObjectsByLocation(double lat, double lng, double distance,
			String distanceUnits, int size, int page) {
//		List<SuperAppObjectBoundary> rv = new ArrayList<SuperAppObjectBoundary>();

		return this.objectCrud
				.findAll(PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"))
				.stream() // Stream<superAppObjectEntity>
				.filter(obj -> inRange(obj.getLat(), obj.getLng(), lat, lng, distance))
				.map(this.converter::superAppObjectToBoundary) // Stream<superAppObjectToBoundary>
				.toList(); // List<superAppObjectToBoundary>

//		if (allObjects != null) {
//			for (SuperAppObjectBoundary boundary : allObjects) {
//				Location objectLoc = new Location(boundary.getLocation().getLat(), boundary.getLocation().getLng());
//				if (inRange(objectLoc, inputLocation, distance)) {
//					rv.add(Optional.of(boundary).get());
//				}
//			}
//		} else {
//			throw new RuntimeException("There are no objects in database");
//		}
//		return allObjects;
	}

	public boolean inRange(double objLat, double objLng, double inputLat, double inputLng, double distance) {

		double plusLat = inputLat + distance;
		double minusLat = inputLat - distance;
		double plusLng = inputLng + distance;
		double minusLng = inputLng - distance;

		if (objLat <= plusLat && objLat >= minusLat && objLng <= plusLng && objLng >= minusLng) {
			return true;
		} else {
			return false;
		}

	}
}
