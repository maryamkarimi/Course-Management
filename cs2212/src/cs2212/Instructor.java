package cs2212;

import java.util.ArrayList;

public class Instructor implements SystemUser{
	
	private String instructorID;
	private String name;
	private String surname;
	private ArrayList<Course> isTutorOf;
	
	public Instructor() {
		isTutorOf = new ArrayList<Course>();
	}

	public String getID(){
		return this.instructorID;
	}
	public void setID(String ID){
		this.instructorID = ID;
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

	public ArrayList<Course> getIsTutorOf() {
		return isTutorOf;
	}
	
	public boolean isTutorOf(String ID) {
		for (Course course: getIsTutorOf()) {
			if (course.getCourseID().equals(ID)) {
				return true;
			}
		}
		return false;
	}

	public void setIsTutorOf(ArrayList<Course> isTutorOf) {
		this.isTutorOf = isTutorOf;
	}

	

}
