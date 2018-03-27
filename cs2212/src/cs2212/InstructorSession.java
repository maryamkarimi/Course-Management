package cs2212;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

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
		String option = "4";
		
		do {
			System.out.print("Choose one of the above: ");
			option = this.input.next();
		}
		while(!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") && !option.equals("5"));
	
		
		if (option.equals("1"))	{
			addMarkForStudent();
			wantToLogOut();
		}
		else if (option.equals("2")) {
			addMarkForStudent();
			wantToLogOut();
		}
		else if (option.equals("3")) {
			calculateFinalGradeForStudent();
			wantToLogOut();
		}
		else if (option.equals("4")) {
			printClassRecord();
			wantToLogOut();
		}
	}
	
	private void wantToLogOut() {
		showOperations();
		chooseOperation(this.input);
	}
	
	private void addMarkForStudent() {
		String CourseID = "";
		String StudentID = "";
		do {
			System.out.print("Please enter the course ID: ");
			CourseID = this.input.next().toUpperCase();
		}
		while(Register.getInstance().getRegisteredCourse(CourseID)==null || !instructor.isTutorOf(CourseID));
		
		Course targetCourse = Register.getInstance().getRegisteredCourse(CourseID);
		do {
			System.out.print("Please enter the student ID: ");
			StudentID = this.input.next();
		}
		while (Register.getInstance().getRegisteredUser(StudentID) == null);
		
		Student targetStudent =(Student) Register.getInstance().getRegisteredUser(StudentID);
		
		if (!targetStudent.isEnrolledIn(targetCourse.getCourseID())) {
			System.out.println("This student is not enrolled in this course.");
			}
		
		else {
			try {
				if (targetCourse.getEvaluationStrategies().get(targetStudent.getEvaluationEntities().get(targetCourse))==null)
					System.out.println("null");;

				System.out.print("Choose one of the above:");
				
				String eval = "";
				if (input.hasNext()) {
					eval = this.input.next();
				}
				
				double mark = 0;
				do {
					System.out.print("Please enter the mark: ");
					mark = this.input.nextInt();
				}
				while(mark<0 || mark>100);
				
				Map<Course,Marks> map = targetStudent.getPerCourseMarks();
				Marks marks = new Marks();
				marks.addToEvalStrategy(eval, mark);
				map.put(targetCourse, marks);
			}
			catch(NullPointerException e) {
				System.out.println("Instructor session exception");
			}
		}
	}
	
	private void calculateFinalGradeForStudent() {
		String CourseID = "";
		String StudentID = "";
		do {
			System.out.print("Please enter the course ID: ");
			CourseID = this.input.next().toUpperCase();
			if (!instructor.isTutorOf(CourseID)){
				System.out.println("You are not listed as an instructor for this course");
			}
			if (Register.getInstance().getRegisteredCourse(CourseID)==null) {
				System.out.println("Invalid course ID");
			}
		}
		while(Register.getInstance().getRegisteredCourse(CourseID)==null || !instructor.isTutorOf(CourseID));
		
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
				ID = this.input.next().toUpperCase();
			}
			if (!instructor.isTutorOf(ID)){
				System.out.println("You are not listed as an instructor for this course");
			}
			if (Register.getInstance().getRegisteredCourse(ID)==null) {
				System.out.println("Invalid course ID");
			}
		}
		while(Register.getInstance().getRegisteredCourse(ID)==null || !instructor.isTutorOf(ID));
		
		Course targetCourse = Register.getInstance().getRegisteredCourse(ID);
		if (instructor.getIsTutorOf().contains(targetCourse)) {
			System.out.println("Course ID: "+targetCourse.getCourseID()+"\tCourse name: "+targetCourse.getCourseName()+
					"\tSemester: "+targetCourse.getSemester());
			System.out.println("Students enrolled:\n");
			for(Student student : targetCourse.getStudentsEnrolledList()){
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
