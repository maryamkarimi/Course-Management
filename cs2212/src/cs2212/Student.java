package cs2212;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Student implements SystemUser{
	private String studentID;
	private String name;
	private String surname;
	private String email;
	private String phoneNumber;
	private ArrayList<Course> coursesAllowed;
	private ArrayList<Course> coursesEnrolled;
	private Map<Course, EvaluationTypes> evaluationEntities;
	private Map<Course, Marks> perCourseMarks;
	private NotificationTypes notificationType;
	
	public Student() {
		coursesAllowed = new ArrayList<Course>();
		coursesEnrolled = new ArrayList<Course>();
		evaluationEntities = new HashMap<Course, EvaluationTypes>();
		perCourseMarks = new HashMap<Course, Marks>();
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

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
	public boolean isEnrolledIn(String ID) {
		for (Course course: coursesEnrolled) {
			if (course.getCourseID().equals(ID)) {
				return true;
			}
		}
		return false;
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
	
	public void printCourseMarks(Course targetCourse) {
		System.out.print("\nGrades:\n");
		Marks marks = this.getPerCourseMarks().get(targetCourse);
		marks.initializeIterator();
		Iterator<Entry<String, Double>> iterator = marks.getIterator();
		while (iterator.hasNext()) {
			Entry<String, Double> current = iterator.next();
			System.out.println(current.getKey()+": "+current.getValue());
		}
	}

	public void addMark(Course targetCourse, String eval, double mark) {
		Marks marks  = this.getPerCourseMarks().get(targetCourse);
		if (marks == null) {
			marks = new Marks();
		}
		marks.addToEvalStrategy(eval, mark);
		Map<Course,Marks> map = this.getPerCourseMarks();
		map.put(targetCourse, marks);
	}
}
