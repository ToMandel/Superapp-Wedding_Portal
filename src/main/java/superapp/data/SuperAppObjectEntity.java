package superapp.data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SUPER_APP")
public class SuperAppObjectEntity {
	@Id
////TODO: change id to userId
	private String objectId;
	private String type;
	private String alias;
	
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

	@Override
	public String toString() {
		return "SuperAppObjectEntity [objectId=" + objectId + ", type=" + type + ", alias=" + alias + "]";
	}
	

	
}
