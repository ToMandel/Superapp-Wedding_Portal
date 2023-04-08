package superapp.data;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "SUPER_APP")
public class SuperAppObjectEntity {
	@Id
	private String objectId;
	private String type;
	private String alias;
	private Boolean active;
	private Double lat;
	private Double lng;
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTempStamp;

	@Lob
	private String objectDetails;


	
	public SuperAppObjectEntity(){
		
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

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
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

	public String getObjectDetails() {
		return objectDetails;
	}

	public void setObjectDetails(String objectDetails) {
		this.objectDetails = objectDetails;
	}
}
