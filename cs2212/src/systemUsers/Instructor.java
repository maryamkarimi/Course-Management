package systemUsers;

import java.util.ArrayList;

import offerings.Course;

public class Instructor implements SystemUser{
	
	private String instructorID;
	private String name;
	private String surname;
	private String birthday; 
	private ArrayList<Course> isTutorOf; // list of courses that instructor teaches

	// the constructor of this class, the list of courses this instructor teaches is initialized here.
	public Instructor() {
		isTutorOf = new ArrayList<Course>();
	}

	// returns instructor's ID
	public String getID(){
		return this.instructorID;
	}
	
	// sets instructor's ID to the specified value
	public void setID(String ID){
		this.instructorID = ID;
	}

	// returns instructor's name
	public String getName(){
		return this.name;
	}
	
	// sets instructor's name to the specified string
	public void setName(String name){
		this.name = name;
	}
	
	// returns instructor's surname
	public String getSurname(){
		return this.surname;
	}
	
	// sets instructor's surname to the specified string
	public void setSurname(String surname){
		this.surname = surname;
	}

	// returns the list of courses this instructor teaches
	public ArrayList<Course> getIsTutorOf() {
		return isTutorOf;
	}
	
	// this method gets a course ID as an input and returns true if the instructor is a tutor of this course, it returns false otherwise.
	public boolean isTutorOf(String ID) {
		for (Course course: getIsTutorOf()) {
			if (course.getCourseID().equals(ID)) {
				return true;
			}
		}
		return false;
	}

	// sets the list of courses that this instructor teaches to the specified list
	public void setIsTutorOf(ArrayList<Course> isTutorOf) {
		this.isTutorOf = isTutorOf;
	}

	// returns instructor's date of birth
	public String getBirthday() {
		return this.birthday;
	}
	
	// sets instructor's date of birth to the specified value - it has to be of form YYYYMMDD
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}
