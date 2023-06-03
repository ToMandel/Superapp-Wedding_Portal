package superapp.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import superapp.Converter;
import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.dal.UserCrud;
import superapp.data.SuperAppObjectEntity;
import superapp.data.UserEntity;
import superapp.data.UserRole;

import java.util.*;

@Service
public class ObjectServiceDB implements ObjectServiceWithPagination {

	private SupperAppObjectCrud objectCrud;
	private UserCrud userCrud;
	private Converter converter;
	private String nameFromSpringConfig;
	private Log logger = LogFactory.getLog(ObjectServiceDB.class);

	private ObjectMapper jackson;

	@Value("${spring.application.name:defaultName}")
	public void setNameFromSpringConfig(String nameFromSpringConfig) {
		this.nameFromSpringConfig = nameFromSpringConfig;
	}

	@PostConstruct
	public void init() {
		this.logger.info("**** spring.application.name=" + this.nameFromSpringConfig);
		this.jackson = new ObjectMapper();
	}

	@Autowired
	public void setObjectCrud(SupperAppObjectCrud objectCrud) {
		this.objectCrud = objectCrud;
	}

	@Autowired
	public void setUserCrud(UserCrud userCrud) {
		this.userCrud = userCrud;
	}

	@Autowired
	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	private UserEntity getUser(String superAppName, String email) {
		String id = superAppName + "#" + email;
		return this.userCrud.findById(id).orElseThrow(() -> new UnauthorizedException(
				"There is no user with email: " + email + " in " + superAppName + " superapp"));
	}

	@Override
	@Deprecated
	public SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId,
			SuperAppObjectBoundary update) {
		throw new DeprecatedOperationException();
	}

	@Override
	@Deprecated
	public SuperAppObjectBoundary getSpecificObject(String objectSuperApp, String internalId) {
		throw new DeprecatedOperationException();
	}

	@Override
	@Deprecated
	public List<SuperAppObjectBoundary> getAllObjects() {
		throw new DeprecatedOperationException();
	}

	@Override
	@Deprecated
	public void relateParentToChild(ObjectId parentObjectId, ObjectId childObjectId) {
		throw new DeprecatedOperationException();
	}

	@Override
	@Deprecated
	public List<SuperAppObjectBoundary> getAllChildrenOfObject(ObjectId parentId) {
		throw new DeprecatedOperationException();
	}

	@Override
	@Deprecated
	public List<SuperAppObjectBoundary> getAllParentsOfObject(ObjectId child) {
		throw new DeprecatedOperationException();
	}

	@Override
	@Deprecated
	public void deleteAllObjects() {
		throw new DeprecatedOperationException();
	}

	@Override
	public SuperAppObjectBoundary createObject(SuperAppObjectBoundary object) {
		this.logger.trace("&&&&& inserting super app object to db started");
		long beginCount = System.currentTimeMillis();

		SuperAppObjectEntity entity = null;

		UserEntity user = getUser(object.getCreatedBy().getUserId().getSuperapp(),
				object.getCreatedBy().getUserId().getEmail());
		if (user.getRole() == UserRole.SUPERAPP_USER) {
			try {
				String internalObjectId = UUID.randomUUID().toString(); // the value is random so the size of entities isn' relevant
				object.setObjectId(new ObjectId(nameFromSpringConfig, internalObjectId));
				object.setCreationTimestamp(new Date());
				if (object.getObjectDetails() == null)
					object.setObjectDetails(new HashMap<>());
				entity = this.converter.superAppObjectToEntity(object);
				entity = this.objectCrud.save(entity);
				return this.converter.superAppObjectToBoundary(entity);
			}
			finally {
				long endCount = System.currentTimeMillis();
				long elapsed = endCount - beginCount;
				this.logger.debug("&&&&& inserting to db: " + entity.getObjectId().toString() + " - ended - and took " + elapsed + "ms");
			}
		}
		throw new ForbiddenException("Only SUPERAPP_USER users can create new objects");
	}

	@Override
	public SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId,
			SuperAppObjectBoundary update, String userSuperapp, String email) {
		UserEntity user = getUser(userSuperapp, email);
		if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER) {
			String objectId = objectSuperApp + "#" + internalObjectId;
			SuperAppObjectEntity existing = this.objectCrud.findById(objectId)
					.orElseThrow(() -> new RuntimeException("could not find object by id: " + objectId));
			if (update.getType() != null)
				existing.setType(update.getType());
			if (update.getAlias() != null)
				existing.setAlias(update.getAlias());
			if (update.getActive() != null)
				existing.setActive(update.getActive());
			if (update.getLocation() != null)
				existing.setLocation(new Point(update.getLocation().getLat(), update.getLocation().getLng()));
			if (update.getObjectDetails() != null)
				existing.setObjectDetails(update.getObjectDetails());
			this.objectCrud.save(existing);
			return this.converter.superAppObjectToBoundary(existing);
		} else
			throw new ForbiddenException("Only SUPERAPP_USER users can update an object");

	}

	@Override
	public SuperAppObjectBoundary getSpecificObject(String userSuperapp, String email, String objectSuperApp,
			String internalObjectId) {

		UserEntity user = getUser(userSuperapp, email);
		String objectId = objectSuperApp + "#" + internalObjectId;

		SuperAppObjectEntity existing = this.objectCrud.findById(objectId)
				.orElseThrow(() -> new NotFoundException("could not find object by id: " + objectId));

		if (user.getRole() != null && user.getRole() == UserRole.MINIAPP_USER)
			if (existing.getActive())
				return this.converter.superAppObjectToBoundary(existing);
			else
				throw new NotFoundException("There is no active object with id: " + objectId);
		else if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER)
			return this.converter.superAppObjectToBoundary(existing);
		else
			throw new ForbiddenException("Operation is not allowed, the user is ADMIN");
	}

	@Override
	public List<SuperAppObjectBoundary> getAllObjects(String userSuperapp, String email, int size, int page) {

		UserEntity user = getUser(userSuperapp, email);
		List<SuperAppObjectEntity> objects = new ArrayList<SuperAppObjectEntity>();

		if (user.getRole() != null && user.getRole() == UserRole.MINIAPP_USER) {
			objects = this.objectCrud.findAllByActiveIsTrue(
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));

		} else if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER) {
			objects = this.objectCrud
					.findAll(
							PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"))
					.getContent();
		} else
			throw new ForbiddenException("Operation is not allowed, the user is ADMIN");

		//if (objects.isEmpty())
		//	throw new NotFoundException("There are no objects");

		return objects.stream().map(this.converter::superAppObjectToBoundary).toList();

	}

	@Override
	public void relateParentToChild(ObjectId parentObjectId, ObjectId childObjectId, String userSuperApp,
			String email) {

		String parentId = parentObjectId.getSuperapp() + "#" + parentObjectId.getInternalObjectId();
		String childId = childObjectId.getSuperapp() + "#" + childObjectId.getInternalObjectId();
		UserEntity user = getUser(userSuperApp, email);

		if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER) {
			SuperAppObjectEntity child = this.objectCrud.findById(childId)
					.orElseThrow(() -> new NotFoundException("could not find child object by id: " + childId));

			SuperAppObjectEntity parent = this.objectCrud.findById(parentId)
					.orElseThrow(() -> new NotFoundException("could not find parent object by id: " + parentId));
			child.setParentObject(parent);
			parent.setChildrenObject(child);
			this.objectCrud.save(child);
			this.objectCrud.save(parent);
		} else
			throw new ForbiddenException("Only SUPERAPP_USER users can relate parent to child");

	}

	@Override
	public List<SuperAppObjectBoundary> getAllChildrenOfObject(ObjectId parent, String userSuperApp, String email,
			int size, int page) {
		String parentId = parent.getSuperapp() + "#" + parent.getInternalObjectId();
		SuperAppObjectEntity parentEntity = this.objectCrud.findById(parentId)
				.orElseThrow(() -> new NotFoundException("could not find object by id: " + parentId));


		UserEntity user = getUser(userSuperApp, email);

		List<SuperAppObjectEntity> objects = new ArrayList<SuperAppObjectEntity>();

		if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER) {
			objects = this.objectCrud.findAllByParentObject(parentId,
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));

		} else if (user.getRole() != null && user.getRole() == UserRole.MINIAPP_USER) {
			objects = this.objectCrud.findAllByParentObjectAndActiveIsTrue(parentId,
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));

		} else
			throw new ForbiddenException("Operation is not allowed only for ADMIN");

		return objects.stream().map(this.converter::superAppObjectToBoundary).toList();
	}

	@Override
	public List<SuperAppObjectBoundary> getAllParentsOfObject(ObjectId child, String userSuperApp, String email,
			int page, int size) {

		String childId = child.getSuperapp() + "#" + child.getInternalObjectId();
		List<SuperAppObjectEntity> allParents = new ArrayList<SuperAppObjectEntity>();

		SuperAppObjectEntity childEntity = this.objectCrud.findById(childId)
				.orElseThrow(() -> new NotFoundException("could not find object by id: " + childId));

		UserEntity user = getUser(userSuperApp, email);

		if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER) {

			allParents = this.objectCrud.findAllByChildrenObject(childEntity.getObjectId(),
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));


		} else if (user.getRole() != null && user.getRole() == UserRole.MINIAPP_USER) {
			allParents = this.objectCrud.findAllByChildrenObjectAndActiveIsTrue(childEntity.getObjectId(),
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));

		} else
			throw new ForbiddenException("Operation is not allowed for ADMIN users");

		return allParents.stream().map(this.converter::superAppObjectToBoundary).toList();
	}

	@Override
	public List<SuperAppObjectBoundary> searchObjectsByLocation(String userSuperApp, String email, double lat,
			double lng, double distance, String distanceUnits, int size, int page) {
		List<SuperAppObjectEntity> objects = new ArrayList<SuperAppObjectEntity>();

		UserEntity user = getUser(userSuperApp, email);
		Point p = new Point(lat, lng);
		Distance d = new Distance(distance, getMetricUnit(distanceUnits));
		PageRequest pageRequest =  PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId");

		if (user.getRole() != null) {
			if (user.getRole() == UserRole.MINIAPP_USER)
				objects = this.objectCrud.findAllByLocationNearAndActiveIsTrue(p, d, pageRequest);
			else if (user.getRole() == UserRole.SUPERAPP_USER)
				objects = this.objectCrud.findAllByLocationNear(p, d, pageRequest);
			else
				throw new ForbiddenException("Operation is not allowed for ADMIN users");
		}
		return objects.stream().map(this.converter::superAppObjectToBoundary).toList();
	}

	private Metrics getMetricUnit (String unit){
		switch (unit.toLowerCase()){
			case "kilometers":
				return Metrics.KILOMETERS;
			case "miles":
				return Metrics.MILES;
			case "neutral":
				return Metrics.NEUTRAL;
			default:
				throw new BadRequestException("Invalid metric unit: " + unit);
		}
	}

	@Override
	public List<SuperAppObjectBoundary> searchObjectsByType(String type, int size, int page, String userSuperApp,
			String email) {
		UserEntity user = getUser(userSuperApp, email);
		List<SuperAppObjectEntity> objects = new ArrayList<SuperAppObjectEntity>();
		if (user.getRole() != null && user.getRole() == UserRole.MINIAPP_USER) {
			objects = this.objectCrud.findAllByTypeAndActiveIsTrue(type,
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));
		} else if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER)
			objects = this.objectCrud.findAllByType(type,
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));
		else if (user.getRole() != null)
			throw new ForbiddenException("Operation is not allowed for ADMIN users");
		return objects.stream().map(this.converter::superAppObjectToBoundary).toList();
	}

	@Override
	public List<SuperAppObjectBoundary> searchObjectsByAlias(String alias, int size, int page, String userSuperApp,
			String email) {
		UserEntity user = getUser(userSuperApp, email);
		List<SuperAppObjectEntity> objects = new ArrayList<SuperAppObjectEntity>();
		if (user.getRole() != null && user.getRole() == UserRole.MINIAPP_USER) {
			objects = this.objectCrud.findAllByAliasAndActiveIsTrue(alias,
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));

		} else if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER)
			objects = this.objectCrud.findAllByAlias(alias,
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));
		else if (user.getRole() != null)
			throw new ForbiddenException("Operation is not allowed for ADMIN users");
		return objects.stream().map(this.converter::superAppObjectToBoundary).toList();
	}

	@Override
	public void deleteAllObjects(String userSuperApp, String email) {
		UserEntity user = getUser(userSuperApp, email);
		if (user.getRole() != null && user.getRole() == UserRole.ADMIN) {
			this.objectCrud.deleteAll();
		} else
			throw new ForbiddenException("Only ADMIN users can delete all Objects");
	}
}
