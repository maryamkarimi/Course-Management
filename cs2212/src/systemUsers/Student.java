package systemUsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import customDataTypes.EvaluationTypes;
import customDataTypes.Marks;
import customDataTypes.NotificationTypes;
import offerings.ICourse;

public class Student implements IStudent{
	private String studentID;
	private String name;
	private String surname;
	private String birthday;
	private List<ICourse> coursesAllowed; // list of courses that student is allowed to enroll in
	private List<ICourse> coursesEnrolled; // list of courses that student is enrolled in
	private Map<ICourse, EvaluationTypes> evaluationEntities; // Map every course to an evaluationType
	private Map<ICourse, Marks> perCourseMarks; // Maps every course to its marks
	private NotificationTypes notificationType; // student notification type

	// the constructor of this class, all the lists and maps are initialized here.
	public Student() {
		coursesAllowed = new ArrayList<ICourse>();
		coursesEnrolled = new ArrayList<ICourse>();
		evaluationEntities = new HashMap<ICourse, EvaluationTypes>();
		perCourseMarks = new HashMap<ICourse, Marks>();
	}

	// returns student's ID
	public String getID(){
		return this.studentID;
	}
	
	// sets student's ID to the specified value
	public void setID(String ID){
		this.studentID = ID;
	}
	
	// returns student's name
	public String getName(){
		return this.name;
	}
	
	// sets student's name to the specified string
	public void setName(String name){
		this.name = name;
	}
	
	// returns student's surname
	public String getSurname(){
		return this.surname;
	}
	
	// sets student surname to the specified string
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	// returns the list of courses student is allowed to enroll in
	public List<ICourse> getCoursesAllowed() {
		return coursesAllowed;
	}

	// sets the list coursesAllowed to the specified list
	public void setCoursesAllowed(List<ICourse> coursesAllowed) {
		this.coursesAllowed = coursesAllowed;
	}

	// returns the list of courses student is enrolled in
	public List<ICourse> getCoursesEnrolled() {
		return coursesEnrolled;
	}

	// sets the list coursesEnrolled to the specified list
	public void setCoursesEnrolled(List<ICourse> coursesEnrolled) {
		this.coursesEnrolled = coursesEnrolled;
	}

	// returns the course-EvaluationTypes map
	public Map<ICourse, EvaluationTypes> getEvaluationEntities() {
		return evaluationEntities;
	}

	// sets the Course-EvalutionType map to the specified map
	public void setEvaluationEntities(Map<ICourse, EvaluationTypes> evaluationEntities) {
		this.evaluationEntities = evaluationEntities;
	}

	// returns the perCourseMarks map
	public Map<ICourse, Marks> getPerCourseMarks() {
		return perCourseMarks;
	}

	// sets the perCourseMarks map to the specified map
	public void setPerCourseMarks(Map<ICourse, Marks> perCourseMarks) {
		this.perCourseMarks = perCourseMarks;
	}

	// returns notificationType for this student
	public NotificationTypes getNotificationType() {
		return notificationType;
	}

	// sets notificationType to the specified type
	public void setNotificationType(NotificationTypes notificationType) {
		this.notificationType = notificationType;
	}

	// returns the student birthday
	public String getBirthday() {
		return this.birthday;
	}
	
	// sets the student birthday to the specified string
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	// this method gets a course as an input and returns true if student is allowed to enroll in it. it returns false otherwise.
	public boolean isAllowedToEnrollIn(ICourse course) {
		for (ICourse curCourse: this.getCoursesAllowed()) {
			if (curCourse.getCourseID().equals(course.getCourseID())) {
				return true;
			}
		}
		return false;
	}
	
	// this method gets a course ID as an input and returns true if the student is enrolled in this course and false otherwise.
	public boolean isEnrolledIn(String ID) {
		for (ICourse course: coursesEnrolled) {
			if (course.getCourseID().equals(ID)) {
				return true;
			}
		}
		return false;
	}
}
