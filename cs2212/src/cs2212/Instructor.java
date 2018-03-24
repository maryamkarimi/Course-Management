package cs2212;

import java.util.ArrayList;

public class Instructor {
	private int instructorID;
	private String name;
	private String surname;
	private ArrayList<Course> isTutorOf;
	
	public Instructor(String name,String surname, int ID) {
	
		this.name = name;
		this.surname = surname;
		instructorID = ID;
	}

	public int getInstructorID(){
		return this.instructorID;
	}
	public void setInstructorID(int ID){
		this.instructorID = ID;
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

	public ArrayList<Course> getIsTutorOf() {
		return isTutorOf;
	}

	public void setIsTutorOf(ArrayList<Course> isTutorOf) {
		this.isTutorOf = isTutorOf;
	}

	

}
