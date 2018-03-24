package cs2212;

import java.util.ArrayList;
import java.util.Map;

public class Student {
	private String studentID;
	private String name;
	private String surname;
	private ArrayList<Course> coursesAllowed;
	private ArrayList<Course> coursesEnrolled;
	private Map<Course, EvaluationTypes> evaluationEntities;
	private Map<Course, Marks> perCourseMarks;
	private NotificationTypes notificationType;
	
	public Student(String name,String surname, String ID) {
		this.name = name;
		this.surname = surname;
		studentID = ID;
	}

	public String getStudentID(){
		return this.studentID;
	}
	
	public void setStudentID(String ID){
		this.studentID = ID;
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

	public ArrayList<Course> getCoursesAllowed() {
		return coursesAllowed;
	}

	public void setCoursesAllowed(ArrayList<Course> coursesAllowed) {
		this.coursesAllowed = coursesAllowed;
	}

	public ArrayList<Course> getCoursesEnrolled() {
		return coursesEnrolled;
	}

	public void setCoursesEnrolled(ArrayList<Course> coursesEnrolled) {
		this.coursesEnrolled = coursesEnrolled;
	}

	public Map<Course, EvaluationTypes> getEvaluationEntities() {
		return evaluationEntities;
	}

	public void setEvaluationEntities(Map<Course, EvaluationTypes> evaluationEntities) {
		this.evaluationEntities = evaluationEntities;
	}

	public Map<Course, Marks> getPerCourseMarks() {
		return perCourseMarks;
	}

	public void setPerCourseMarks(Map<Course, Marks> perCourseMarks) {
		this.perCourseMarks = perCourseMarks;
	}

	public NotificationTypes getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationTypes notificationType) {
		this.notificationType = notificationType;
	}
	
}
