package superapp.boundries;

public class UserId {

		private String superapp;
		private String email;
		
		
		public UserId() {
			
		}
		
		public UserId(String superapp, String email) {
			super();
			this.superapp = superapp;
			this.email = email;
		}
		
		public String getSuperapp() {
			return superapp;
		}
		
		public void setSuperapp(String superapp) {
			this.superapp = superapp;
		}
		
		@Override
		public String toString() {
			return "UserId [superapp=" + superapp + ", email=" + email + "]";
		}
		
		public static UserId fromString(String str) {
		    if (str == null || str.isEmpty()) {
		        return null;
		    }
		    String[] parts = str.split(", ");
		    if (parts.length != 2) {
		        return null;
		    }
		    String superapp = parts[0].substring(parts[0].indexOf("=") + 1);
		    String email = parts[1].substring(parts[1].indexOf("=") + 1, parts[1].length() - 1);
		    return new UserId(superapp, email);
		}
		
		
		public String getEmail() {
			return email;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
		
		
}
