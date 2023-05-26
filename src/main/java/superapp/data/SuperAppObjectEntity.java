package superapp.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "SUPER_APP_OBJECTS")
@CompoundIndexes({@CompoundIndex(name = "location_2dsphere", def = "{'location': '2dsphere'}")})
public class SuperAppObjectEntity {
	@Id
	private String objectId;
	private String type;
	private String alias;
	private Boolean active;
	@GeoSpatialIndexed
	private Point location;
	private String createdBy;
	private Date creationTempStamp;

	
	//converted to list so you can add several parents to child
	@DBRef
	private List <SuperAppObjectEntity> parentObject;
	
	@DBRef
	private List <SuperAppObjectEntity> childrenObject;
	

	private Map<String, Object> objectDetails;

	
	public SuperAppObjectEntity(){
		this.parentObject = new ArrayList<SuperAppObjectEntity>();
		this.childrenObject = new ArrayList<SuperAppObjectEntity>();
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTempStamp() {
		return creationTempStamp;
	}

	public void setCreationTempStamp(Date creationTempStamp) {
		this.creationTempStamp = creationTempStamp;
	}

	public List<SuperAppObjectEntity> getParentObject() {
		return parentObject;
	}

	public void setParentObject(SuperAppObjectEntity parentObject) {
		this.parentObject.add(parentObject);
	}
	public void setChildrenObject(SuperAppObjectEntity childrenObject) {
		this.childrenObject.add(childrenObject);
	}

	public Map<String, Object> getObjectDetails() {
		return objectDetails;
	}

	public void setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
	}

	public List<SuperAppObjectEntity> getChildrenObject() {
		return childrenObject;
	}


}
