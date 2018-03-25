package cs2212;

import java.util.ArrayList;
import java.util.Map;

public class Student implements SystemUser{
	private String studentID;
	private String name;
	private String surname;
	private ArrayList<Course> coursesAllowed;
	private ArrayList<Course> coursesEnrolled;
	private Map<Course, EvaluationTypes> evaluationEntities;
	private Map<Course, Marks> perCourseMarks;
	private NotificationTypes notificationType;
	
	public Student() {
		coursesAllowed = new ArrayList<Course>();
		coursesEnrolled = new ArrayList<Course>();
	}

	public String getID(){
		return this.studentID;
	}
	
	public void setID(String ID){
		this.studentID = ID;
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
