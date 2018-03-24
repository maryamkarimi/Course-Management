package cs2212;

import java.util.ArrayList;
import java.util.Map;

public class Course {
	private String courseName;
	private int courseID;
	private String semester;
	private ArrayList<Student> studentsAllowedToEnroll;
	private ArrayList<Student> studentsEnrolled;
	private ArrayList<Instructor> instructorList;
	Map<EvaluationTypes, Weights> evaluationStrategies;
	
	Course(String fileName){
		
	}


	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
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
	public void calculateFinalGrade(String ID){
		Student target = null;
		Double finalGrade;
		for(Student student : studentsEnrolled)
			if (student.getStudentID().equals(ID)) 
				target = student;
		finalGrade = 0D;
		Weights weights = evaluationStrategies.get(target.getEvaluationEntities().get(this));
		Marks marks  = target.getPerCourseMarks().get(this);
		weights.initializeIterator();
		while(weights.hasNext()){
			weights.next();
			finalGrade += weights.getCurrentValue() * marks.getValueWithKey(weights.getCurrentKey());
		}
	}
	
}

