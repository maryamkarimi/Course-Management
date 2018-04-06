
import java.util.ArrayList;

public class Instructor implements SystemUser{
	
	private String instructorID;
	private String name;
	private String surname;
	private String birthday; 
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

	public String getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public void printCourseRecord(Course targetCourse) {
		System.out.println("Course ID: "+targetCourse.getCourseID()+"\tCourse name: "+targetCourse.getCourseName()+
				"\tSemester: "+targetCourse.getSemester());
		System.out.println("Students enrolled: ( Total :"+targetCourse.getStudentsEnrolledList().size()+" )\n");
		for(Student student : targetCourse.getStudentsEnrolledList()){
			System.out.println("Student name : " + student.getName() + "\nStudent surname : " + student.getSurname() + 
					"\nStudent ID : " + student.getID() + "\nStudent EvaluationType : " + 
					student.getEvaluationEntities().get(targetCourse) + "\n");
		}
	}

}
