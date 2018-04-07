package offerings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import customDataTypes.EvaluationTypes;
import customDataTypes.Marks;
import customDataTypes.Weights;
import systemUsers.Instructor;
import systemUsers.Student;

public class Course {

	private String courseName;
	private String courseID;
	private int semester;
	private ArrayList<Student> studentsAllowedToEnroll; // the list of students that are allowed to enroll in this course
	private ArrayList<Student> studentsEnrolled; // the list of students that are enrolled in this course
	private ArrayList<Instructor> instructorList; // the list of instructors that teach this course
	private Map<EvaluationTypes, Weights> evaluationStrategies; // every evaluationType is mapped to weights 

	// the constructor of this class, all the lists and maps are initialized here.
	public Course(){
		studentsAllowedToEnroll = new ArrayList<Student>();
		studentsEnrolled =  new ArrayList<Student>();
		instructorList =  new ArrayList<Instructor>();
		evaluationStrategies = new HashMap<EvaluationTypes, Weights>();
	}

	// returns the name of the course
	public String getCourseName() {
		return courseName;
	}
	
	// sets course name to the specified string
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	// returns the ID of the course
	public String getCourseID() {
		return courseID;
	}

	// sets the course ID to the specified value
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	// returns the semester of the course : can be 1 or 2
	public int getSemester() {
		return semester;
	}

	// sets the semester for this course to the specified value : can be 1 or 2
	public void setSemester(int semester) {
		this.semester = semester;
	}

	// returns the list of students that are allowed to enroll in this course
	public ArrayList<Student> getStudentsAllowedList() {
		return studentsAllowedToEnroll;
	}

	// sets studentsAllowedToEnroll list to the specified studentList
	public void setStudentsAllowedList(ArrayList<Student> studentList) {
		this.studentsAllowedToEnroll = studentList;
	}

	// returns the list of student enrolled in this course
	public ArrayList<Student> getStudentsEnrolledList() {
		return studentsEnrolled;
	}
	
	// sets studentEnrolled list to the specified studentList
	public void setStudentsEnrolledList(ArrayList<Student> studentList) {
		this.studentsEnrolled = studentList;
	}
	
	// returns the list of instructors for this course
	public ArrayList<Instructor> getInstructorList() {
		return instructorList;
	}
	
	// adds an instructor to the list of instructors for this course
	public void addInstructor(Instructor instructor){
		this.instructorList.add(instructor);
	}
	
	// removes an instructor from the list of instructors for this course
	public void removeInstructor(Instructor instructor){
		this.instructorList.remove(instructor);
	}

	// sets the list of instructors for this course to the specified list
	public void setInstructorList(ArrayList<Instructor> instructorList) {
		this.instructorList = instructorList;
	}
	
	// returns evaluationStrategies for this course
	public Map<EvaluationTypes, Weights> getEvaluationStrategies() {
		return evaluationStrategies;
	}
	
	// sets evaluationStrategies for this course to a specified map
	public void setEvaluationStrategies(Map<EvaluationTypes, Weights> evaluationStrategies) {
		this.evaluationStrategies = evaluationStrategies;
	}
	
	// this method gets a student ID and returns true if she/he is enrolled in this course, it returns false otherwise.
	public boolean isStudentEnrolled(String studentID) {
		for (Student student:studentsEnrolled) {
			if (student.getID() == studentID) {
				return true;
			}
		}
		return false;
	}
	
	// this method calculates Final grades for all the students enrolled in this course
	public void calculateFinalGrades(){
		Double finalGrade; 
		for(Student student : studentsEnrolled){
			finalGrade = 0D;
			Weights weights = evaluationStrategies.get(student.getEvaluationEntities().get(this));
			Marks marks  = student.getPerCourseMarks().get(this);
			weights.initializeIterator();
			while(weights.hasNext()){
				weights.next();
				finalGrade += weights.getCurrentValue() * marks.getValueWithKey(weights.getCurrentKey())/100;
			}
		}
	}
	
	// Calculates the Final Grades for a student using the Weights and Marks and returns the final grade
	public double calculateFinalGrade(Student targetStudent){
		double finalGrade = 0D;
		Weights weights = evaluationStrategies.get(targetStudent.getEvaluationEntities().get(this));
		Marks marks  = targetStudent.getPerCourseMarks().get(this);
		weights.initializeIterator();
		while(weights.hasNext()){
			weights.next();
			finalGrade += weights.getCurrentValue() * marks.getValueWithKey(weights.getCurrentKey())/100;
		}
		return finalGrade;
	}
	
}

