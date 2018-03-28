package cs2212;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Course {

	private String courseName;
	private String courseID;
	private int semester;
	private ArrayList<Student> studentsAllowedToEnroll;
	private ArrayList<Student> studentsEnrolled;
	private ArrayList<Instructor> instructorList;
	private Map<EvaluationTypes, Weights> evaluationStrategies;

	
	Course(){
		studentsAllowedToEnroll = new ArrayList<Student>();
		studentsEnrolled =  new ArrayList<Student>();
		instructorList =  new ArrayList<Instructor>();
		evaluationStrategies = new HashMap<EvaluationTypes, Weights>();
	}
	


	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public ArrayList<Student> getStudentsAllowedList() {
		return studentsAllowedToEnroll;
	}

	public void setStudentsAllowedList(ArrayList<Student> studentList) {
		this.studentsAllowedToEnroll = studentList;
	}


	public ArrayList<Student> getStudentsEnrolledList() {
		return studentsEnrolled;

	}
	public void setStudentsEnrolledList(ArrayList<Student> studentList) {
		this.studentsEnrolled = studentList;

	}
	
	public ArrayList<Instructor> getInstructorList() {
		return instructorList;
	}
	
	public void addInstructor(Instructor instructor){
		this.instructorList.add(instructor);
	}
	
	public void removeInstructor(Instructor instructor){
		this.instructorList.remove(instructor);
	}

	public void setInstructorList(ArrayList<Instructor> instructorList) {
		this.instructorList = instructorList;
	}
	
	public Map<EvaluationTypes, Weights> getEvaluationStrategies() {
		return evaluationStrategies;
	}
	
	public void setEvaluationStrategies(Map<EvaluationTypes, Weights> evaluationStrategies) {
		this.evaluationStrategies = evaluationStrategies;
	}
	
	public boolean isStudentEnrolled(String studentID) {
		for (Student student:studentsEnrolled) {
			if (student.getID() == studentID) {
				return true;
			}
		}
		return false;
	}
	public void calculateFinalGrades(){
		Double finalGrade; 
		for(Student student : studentsEnrolled){
			finalGrade = 0D;
			Weights weights = evaluationStrategies.get(student.getEvaluationEntities().get(this));
			Marks marks  = student.getPerCourseMarks().get(this);
			weights.initializeIterator();
			while(weights.hasNext()){
				weights.next();
				finalGrade += weights.getCurrentValue() * marks.getValueWithKey(weights.getCurrentKey());
			}
		}
	}
	
//	Calculates the Final Grades using the Weights and Marks utility classes see the comments in 
//	these classes if unsure of how this works
	public double calculateFinalGrade(Student targetStudent) throws Exception{
		if (targetStudent == null || targetStudent.getPerCourseMarks().get(this) == null) {
			throw new Exception("");
		}
		double finalGrade = 0D;
		Weights weights = evaluationStrategies.get(targetStudent.getEvaluationEntities().get(this));
		Marks marks  = targetStudent.getPerCourseMarks().get(this);
		weights.initializeIterator();
		while(weights.hasNext()){
			weights.next();
			System.out.println(weights.getCurrentValue() + marks.getValueWithKey(weights.getCurrentKey()));
			//finalGrade += weights.getCurrentValue() * marks.getValueWithKey(weights.getCurrentKey());
			// this is the problem
		}
		return finalGrade;
	}
	
}

