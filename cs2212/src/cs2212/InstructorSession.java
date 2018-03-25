package cs2212;

import java.util.Map;
import java.util.Scanner;

public class InstructorSession {
	Instructor instructor;
	Scanner input;
	
	public InstructorSession(Instructor instructor){
		this.instructor = instructor;
		System.out.println("Welcome back "+instructor.getName()+ " - What do you want to do today?");
		showOperations();
	}
	
	private void showOperations() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("1. Add mark for a student\t2. Modify mark for a student\t3. Calculate final grade for a student\t4. Print class record\t 5.Logout");
		System.out.println("------------------------------------------------------------------------------");
	}
	
	public void chooseOperation(Scanner input) {
		this.input = input;
		int option = 4;
		
		do {
			System.out.print("Choose one of the above: ");
			if (input.hasNextInt()) {
				option = this.input.nextInt();
			}
			else {
				this.input.next();
			}
		}
		while(option!=1 && option!=2 && option!=3 && option!=4 && option!=5);
		
		if (option == 1)	{
			addMarkForStudent();
			wantToLogOut();
		}
		else if (option == 2) {
			addMarkForStudent();
			wantToLogOut();
		}
		else if (option == 3) {
			calculateFinalGradeForStudent();
			wantToLogOut();
		}
		else if(option == 4) {
			printClassRecord();
			wantToLogOut();
		}
	}
	
	private void wantToLogOut() {
		showOperations();
		chooseOperation(this.input);
	}
	
	private void addMarkForStudent() {
		boolean isAlreadyEnrolled = false;
		String CourseID = "";
		String StudentID = "";
		do {
			System.out.print("Please enter the course ID: ");
			if (input.hasNext()) {
			CourseID = this.input.next();
			}
		}
		while(Register.getInstance().getRegisteredCourse(CourseID)==null);
		
		Course targetCourse = Register.getInstance().getRegisteredCourse(CourseID);
		do {
			System.out.print("Please enter the student ID: ");
			if (input.hasNext()) {
				StudentID = this.input.next();
			}
		}
		while ((Student) Register.getInstance().getRegisteredUser(StudentID) == null);
		
		Student targetStudent =(Student) Register.getInstance().getRegisteredUser(StudentID);
		
		for (Course course: targetStudent.getCoursesEnrolled()) {
			if (course.getCourseID() == CourseID) {
				System.out.println("This student is not enrolled in this course.");
				isAlreadyEnrolled = true;
				break;
			}
		}
		
		if (isAlreadyEnrolled == false) {
			System.out.print("Please enter the Evaluation Entity name (e.g. Final, Assignment, etc.): ");
			String eval = "";
			if (input.hasNext()) {
				eval = this.input.next();
			}
			
			double mark = 0;
			do {
				System.out.print("Please enter the mark: ");
				if (input.hasNextInt()) {
					mark = this.input.nextInt();
				}
			}
			while(mark<0 || mark>100);
			Map<Course,Marks> map = targetStudent.getPerCourseMarks();
			Marks marks = new Marks();
			marks.addToEvalStrategy(eval, mark);
			map.put(targetCourse, marks);
		}
	}
	
	private void calculateFinalGradeForStudent() {
		String CourseID = "";
		String StudentID = "";
		do {
			System.out.print("Please enter the course ID: ");
			if (input.hasNext()) {
			CourseID = this.input.next();
			}
		}
		while(Register.getInstance().getRegisteredCourse(CourseID)==null);
		Course targetCourse = Register.getInstance().getRegisteredCourse(CourseID);
		do {
			System.out.print("Please enter the student ID: ");
			if (input.hasNext()) {
				StudentID = this.input.next();
			}
		}
		while ((Student) Register.getInstance().getRegisteredUser(StudentID) == null);

		targetCourse.calculateFinalGrade(StudentID);
	}
	
	private void printClassRecord() {
		String ID = "";
		do {
			System.out.print("Please enter the course ID: ");
			if (input.hasNext()) {
			ID = this.input.next();
			}
		}
		while(Register.getInstance().getRegisteredCourse(ID)==null);
		
		Course targetCourse = Register.getInstance().getRegisteredCourse(ID);
		if (instructor.getIsTutorOf().contains(targetCourse)) {
			System.out.println("Course ID: "+targetCourse.getCourseID()+"\tCourse name: "+targetCourse.getCourseName()+
					"\tSemester: "+targetCourse.getSemester());
			System.out.println("Students allowed to enroll\n");
			for(Student student : targetCourse.getStudentsAllowedList()){
				System.out.println("Student name : " + student.getName() + "\nStudent surname : " + student.getSurname() + 
						"\nStudent ID : " + student.getID() + "\nStudent EvaluationType : " + 
						student.getEvaluationEntities().get(targetCourse) + "\n\n");
			}
		}
	}
	
	public Scanner getInput() {
		return this.input;
	}
	
}
