package cs2212;

public class Student {
	private int studentID;
	private String username;
	private String name;
	private String surname;
	private int phoneNumber;
	private String email;
	private String password;
	
	public Student(String name,String surname, int ID,String password, String email,int phoneNumber, String username) {
		this.name = name;
		this.surname = surname;
		studentID = ID;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public int getStudentID(){
		return this.studentID;
	}
	public void setStudentID(int ID){
		this.studentID = ID;
	}
	public String getStudentUserName(){
		return this.username;
	}
	public void setStudentUserName(String username){
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
	public int getPhoneNum(){
		return this.phoneNumber;
	}
	public void setPhoneNumber(int phonenumber){
		this.phoneNumber = phonenumber;
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
}
