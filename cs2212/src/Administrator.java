
public class Administrator implements SystemUser {
	
	private String password;
	private String adminID;
	private String username;
	private String name;
	private String surname;
	private String email;
	private String birthday;
	
	public Administrator(String name,String surname, String ID,String password, String email, String username) {
	
		this.name = name;
		this.surname = surname;
		adminID = ID;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getID(){
		return this.adminID;
	}
	public void setID(String ID){
		this.adminID = ID;
	}
	public String getAdministratorUserName(){
		return this.username;
	}
	public void setAdministratorUserName(String username){
		this.username = username;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getSurname(){
		return this.surname;
	}
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}


