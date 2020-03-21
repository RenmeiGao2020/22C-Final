
public class user implements FileOutPutFormat {
	private String username;
	private String password;
	
	public user(String username, String password) {
		this.username = username;
		this.password = password;	
	}
	
	public String getUserName() {
		return this.username;
	}
	
	
	public boolean check(String password) {
		return this.password.equals(password);
	}
	
	public void setpassword(String password) {
		this.password = password;
	}
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}else if (!(o instanceof user)) {
			return false;
		} else {
			user u = (user)o;
			return this.username.equals(u.username)&&this.password.equals(u.password);
		}
	}
	@Override public int hashCode() {
    	int key =0;
    	for(int i=0;i<username.length();i++) {
    		key += (int)username.charAt(i);
    	}
    	for(int i=0;i<password.length();i++) {
    		key += (int)password.charAt(i);
    	}
        return key;
    }
	
	@Override public String toString() {
		return "Username: "+username+"\nPassword: "+password;
	}

	@Override
	public String fileOutPutFormat() {
		return username+"\n"+password+"\n";
	}


}


