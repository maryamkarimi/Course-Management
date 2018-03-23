package cs2212;

public class Administrator {
	private String password;
	private int adminID;
	private String username;
	private String name;
	private String surname;
	private String email;
	
	public Administrator(String name,String surname, int ID,String password, String email, String username) {
	
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
	public int getAdministratorID(){
		return this.adminID;
	}
	public void setAdministratorID(int ID){
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
	public String getSurName(){
		return this.surname;
	}
	public void setSurName(String surname){
		this.surname = surname;
	}
	
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	

}


