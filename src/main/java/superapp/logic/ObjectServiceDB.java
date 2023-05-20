package superapp.logic;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import superapp.Converter;
import superapp.dal.UserCrud;
import superapp.data.UserEntity;
import superapp.data.UserRole;
import superapp.boundries.ObjectId;
import superapp.boundries.SuperAppObjectBoundary;
import superapp.dal.SupperAppObjectCrud;
import superapp.data.SuperAppObjectEntity;

import java.util.*;

@Service
public class ObjectServiceDB implements ObjectServiceWithPagination {

	private SupperAppObjectCrud objectCrud;

	private UserCrud userCrud;
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
	public SuperAppObjectBoundary createObject(SuperAppObjectBoundary object) {
		UserEntity user = getUser(object.getCreatedBy().getUserId().getSuperapp(),
				object.getCreatedBy().getUserId().getEmail());
		if (user.getRole() == UserRole.SUPERAPP_USER) {
			String internalObjectId = UUID.randomUUID().toString(); // the value is random so the size of entities isn't
																	// relevant
			object.setObjectId(new ObjectId(nameFromSpringConfig, internalObjectId));
			object.setCreationTimestamp(new Date());
			if (object.getObjectDetails() == null)
				object.setObjectDetails(new HashMap<>());
			SuperAppObjectEntity entity = this.converter.superAppObjectToEntity(object);
			entity = this.objectCrud.save(entity);
			return this.converter.superAppObjectToBoundary(entity);
		}
		throw new ForbiddenException("Only SUPERAPP users can create new objects");
	}

	@Override

	@Deprecated
	public SuperAppObjectBoundary updateObject(String objectSuperApp, String internalObjectId,
			SuperAppObjectBoundary update) {
		throw new DeprecatedOperationException();
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
			if (update.getLocation() != null) {
				existing.setLng(update.getLocation().getLng());
				existing.setLat(update.getLocation().getLat());
			}
			if (update.getObjectDetails() != null)
				existing.setObjectDetails(update.getObjectDetails());
			this.objectCrud.save(existing);
			return this.converter.superAppObjectToBoundary(existing);
		} else
			throw new ForbiddenException("Operation is not allowed, the user is not SUPERAPP_USER");

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
	@Deprecated
	public SuperAppObjectBoundary getSpecificObject(String objectSuperApp, String internalId) {
		throw new DeprecatedOperationException();
	}

	@Override
	public List<SuperAppObjectBoundary> getAllObjects(String userSuperapp, String email, int size, int page) {

		UserEntity user = getUser(userSuperapp, email);
		List<SuperAppObjectEntity> objects = new ArrayList<SuperAppObjectEntity>();

		if (user.getRole() != null && user.getRole() == UserRole.MINIAPP_USER) {
			objects = this.objectCrud.findAllByActiveIsTrue(
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));
			if (objects.isEmpty())
				throw new NotFoundException("There are no active objects");
		} else if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER) {
			objects = this.objectCrud
					.findAll(
							PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"))
					.getContent();
		} else
			throw new ForbiddenException("Operation is not allowed, the user is ADMIN");

		return objects.stream().map(this.converter::superAppObjectToBoundary).toList();

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
			this.objectCrud.save(child);
		} else
			throw new ForbiddenException("Operation is allowed only for SUPERAPP_USER");

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
	public List<SuperAppObjectBoundary> getAllChildrenOfObject(ObjectId parent, String userSuperApp, String email,
			int size, int page) {
		String id = parent.getSuperapp() + "#" + parent.getInternalObjectId();

		UserEntity user = getUser(userSuperApp, email);

		if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER) {
			return this.objectCrud
					.findAllByParentObject(id,
							PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"))
					.stream().map(this.converter::superAppObjectToBoundary).toList();

		} else if (user.getRole() != null && user.getRole() == UserRole.MINIAPP_USER) {
			return this.objectCrud
					.findAllByParentObjectAndActiveIsTrue(id,
							PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"))
					.stream().map(this.converter::superAppObjectToBoundary).toList();

		} else
			throw new ForbiddenException("Operation is not allowed only for ADMIN");
	}

	@Override
	public List<SuperAppObjectBoundary> getAllParentsOfObject(ObjectId child, String userSuperApp, String email,
			int size, int page) {

		String childId = child.getSuperapp() + "#" + child.getInternalObjectId();
		UserEntity user = getUser(userSuperApp, email);
		List<SuperAppObjectBoundary> allParents = new ArrayList<SuperAppObjectBoundary>();
		SuperAppObjectEntity childEntity = this.objectCrud.findById(childId)
				.orElseThrow(() -> new NotFoundException("could not find object by id: " + childId));

		if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER) {

			// SuperAppObjectEntity parentEntity = childEntity.getParentObject();
			List<SuperAppObjectEntity> parentEntities = childEntity.getParentObject();
			if (parentEntities != null) {
				for (SuperAppObjectEntity entity : parentEntities) {
					allParents.add(Optional.of(this.converter.superAppObjectToBoundary(entity)).get());
				}
			}
		} else if (user.getRole() != null && user.getRole() == UserRole.MINIAPP_USER) {

			// SuperAppObjectEntity parentEntity = childEntity.getParentObject();
			List<SuperAppObjectEntity> parentEntities = childEntity.getParentObject();
			if (parentEntities != null) {
				for (SuperAppObjectEntity entity : parentEntities) {
					if (entity.getActive() == true)
						allParents.add(Optional.of(this.converter.superAppObjectToBoundary(entity)).get());
				}
			}
		} else
			throw new ForbiddenException("Operation is not allowed only for ADMIN");

		return allParents;
	}

	@Override
	public List<SuperAppObjectBoundary> searchObjectsByLocation(String userSuperApp, String email, double lat,
			double lng, double distance, String distanceUnits, int size, int page) {
		List<SuperAppObjectEntity> objects = new ArrayList<SuperAppObjectEntity>();

		double minLat = lat - distance;
		double maxLat = lat + distance;
		double minLng = lng - distance;
		double maxLng = lng + distance;

		UserEntity user = getUser(userSuperApp, email);

		if (user.getRole() != null) {
			if (user.getRole() == UserRole.MINIAPP_USER) {
				objects = this.objectCrud
						.findByLatBetweenAndLngBetweenAndActiveIsTrue(minLat, maxLat, minLng, maxLng,
								PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp",
										"objectId"));

			} else if (user.getRole() == UserRole.SUPERAPP_USER) {
				objects = this.objectCrud
						.findByLatBetweenAndLngBetween(minLat, maxLat, minLng, maxLng,
								PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp",
										"objectId"));

			} else {
				throw new ForbiddenException("Operation is not allowed for ADMIN user");
			}
		}
		if (objects.isEmpty()) {
			throw new NotFoundException("Couldn't find any objects nearby");
		}
		return objects.stream().map(this.converter::superAppObjectToBoundary).toList();
	}

	@Override
	public List<SuperAppObjectBoundary> searchObjectsByType(String type, int size, int page, String userSuperApp,
			String email) {
		UserEntity user = getUser(userSuperApp, email);
		List<SuperAppObjectEntity> objects = new ArrayList<SuperAppObjectEntity>();
		if (user.getRole() != null && user.getRole() == UserRole.MINIAPP_USER) {
			objects = this.objectCrud.findAllByTypeAndActiveIsTrue(type,
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));
			if (objects.isEmpty())
				throw new NotFoundException("There are no active objects with type " + type);

		} else if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER)
			objects = this.objectCrud.findAllByType(type,
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));
		else if (user.getRole() != null)
			throw new ForbiddenException("Operation is not allowed for ADMIN user");
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
			if (objects.isEmpty())
				throw new NotFoundException("There are no active objects with alias " + alias);

		} else if (user.getRole() != null && user.getRole() == UserRole.SUPERAPP_USER)
			objects = this.objectCrud.findAllByAlias(alias,
					PageRequest.of(page, size, Direction.ASC, "type", "alias", "creationTimestamp", "objectId"));
		else if (user.getRole() != null)
			throw new ForbiddenException("Operation is not allowed for ADMIN user");
		return objects.stream().map(this.converter::superAppObjectToBoundary).toList();
	}

	@Override
	@Deprecated
	public void deleteAllObjects() {
		throw new DeprecatedOperationException();
	}

	@Override
	public void deleteAllObjects(String userSuperApp, String email) {
		UserEntity user = getUser(userSuperApp, email);
		if (user.getRole() != null && user.getRole() == UserRole.ADMIN) {
			this.objectCrud.deleteAll();
		} else
			throw new ForbiddenException("Operation is not allowed, the user is not ADMIN");

	}
}
