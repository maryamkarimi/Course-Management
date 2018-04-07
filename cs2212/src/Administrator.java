
public class Administrator implements SystemUser {
	
	private String adminID;
	private String name;
	private String surname;
	private String birthday;
	
	// constructor of this class
	public Administrator(String ID) {
		adminID = ID;
	}
	
	// returns administrator's ID
	public String getID(){
		return this.adminID;
	}
	
	// sets administrator's ID to the specified value
	public void setID(String ID){
		this.adminID = ID;
	}

	// returns administrator's name
	public String getName(){
		return this.name;
	}
	
	// sets administrator's name to the specified string
	public void setName(String name){
		this.name = name;
	}
	
	// returns administrator's surname
	public String getSurname(){
		return this.surname;
	}
	
	// sets administrator's surname to the specified string
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	// returns administrator's date of birth
	public String getBirthday() {
		return this.birthday;
	}
	
	// sets administrator's date of birth to the specified value
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}


