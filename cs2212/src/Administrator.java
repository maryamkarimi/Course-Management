
public class Administrator implements SystemUser {
	
	private String adminID;
	private String name;
	private String surname;
	private String birthday;
	
	public Administrator(String ID) {
		adminID = ID;
	}
	
	public String getID(){
		return this.adminID;
	}
	public void setID(String ID){
		this.adminID = ID;
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
	
	public String getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}


