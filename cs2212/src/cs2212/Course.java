package cs2212;

import java.lang.reflect.Array;

public class Course {
	private String startDate;
	private String endDate;
	private int courseID;
	private String semester;
	private String department;
	private Array studentList[];
	private Array instructorList[];
	
	Course(String startDate, String endDate, int courseID, String semester,String department, Array studentList[], Array instructorList[]){
		this.startDate = startDate;
		this.endDate = endDate;
		this.courseID = courseID;
		this.semester = semester;
		this.department = department;
		this.studentList = studentList;
		this.instructorList = instructorList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Array[] getStudentList() {
		return studentList;
	}

	public void setStudentList(Array[] studentList) {
		this.studentList = studentList;
	}

	public Array[] getInstructorList() {
		return instructorList;
	}

	public void setInstructorList(Array[] instructorList) {
		this.instructorList = instructorList;
	}
}
