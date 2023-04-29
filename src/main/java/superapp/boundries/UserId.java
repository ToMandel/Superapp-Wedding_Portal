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
		
		public static UserId userIdFromString(String str) {
			String arr[] = str.split("#");
		    return new UserId(arr[0], arr[1]);
		}
		
		
		public String getEmail() {
			return email;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
		
		
}
