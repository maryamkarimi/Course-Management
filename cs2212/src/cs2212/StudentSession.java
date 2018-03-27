package cs2212;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Map.Entry;

public class StudentSession{

	Student student;
	Scanner input;
	
	public StudentSession(Student student){
		System.out.println("Welcome back "+student.getName()+ " - What do you want to do today?");
		this.student = student;
		showOperations();
	}
	
	private void showOperations() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("1. Enroll in a course\t2. Select notification status\t3. print course record\t4. Logout ");
		System.out.println("------------------------------------------------------------------------------");
	}
	
	public void chooseOperation(Scanner input) {
		this.input = input;
		String option = "4";
		
		do {
			System.out.print("Choose one of the above: ");
			option = this.input.next();
		}
		while(!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4"));
	
		
		if (option.equals("1"))	{
			enrollIncourse();
			wantToLogOut();
		}
		else if (option.equals("2")) {
			selectNotificationStatus();
			wantToLogOut();
		}
		else if (option.equals("3")) {
			printCourseEnrolled();
			wantToLogOut();
		}
	}
	
	private void enrollIncourse() {
		String ID = "";
		do {
			System.out.print("Please enter the course ID or enter 4 to Log out: ");
			if (input.hasNext()) {
				ID = this.input.next().toUpperCase();
				if (ID.equals("4")) {
					return;
				}
			}
		}
		while(!Register.getInstance().checkIfCourseHasAlreadyBeenCreated(ID));
		
		Course targetCourse = Register.getInstance().getRegisteredCourse(ID);
		
		// if the course ID is not valid
		if (targetCourse==null) {
			System.out.println("The course ID is not valid.");
		}
		
		// if the course id is valid.
		else {
			
			if (!student.getCoursesAllowed().contains(targetCourse)) {
				System.out.println("You are not allowed to enroll in "+targetCourse.getCourseName());
			}
			
			else {
				if (student.isEnrolledIn(targetCourse.getCourseID())){
					System.out.println("You are already enrolled in: "+targetCourse.getCourseName());
				}
					
				else {
					student.getCoursesEnrolled().add(targetCourse);
					targetCourse.getStudentsEnrolledList().add(student);
					System.out.println("You are now enrolled in "+targetCourse.getCourseName());
				}
			}
		}
		
		
		
	}
	
	private void selectNotificationStatus() {
	}
	
	private void printCourseEnrolled() {
		boolean isValidCourse = false;
		String ID = "";
		do {
			System.out.print("Please enter the course ID or enter 4 to Log out: ");
			if (input.hasNext()) {
				ID = this.input.next().toUpperCase();
				if (ID.equals("4")) {
					return;
				}
			}
		}
		while(Register.getInstance().getRegisteredCourse(ID)==null);
		
		Course targetCourse = Register.getInstance().getRegisteredCourse(ID);
		for (Course course : student.getCoursesEnrolled()) {
			if (course.getCourseName().equals(targetCourse.getCourseName())) {
					isValidCourse = true;
					break;
			}
		}
		if ( isValidCourse == true) {
			System.out.print("Course ID: "+targetCourse.getCourseID()+"\tCourse name: "+targetCourse.getCourseName()+
				"\tSemester: "+targetCourse.getSemester()+"\nEvaluation Entity: "+student.getEvaluationEntities().get(targetCourse)+"\nInstructor(s): ");
			int counter = 1;
			for (Instructor instructor: targetCourse.getInstructorList()) {
				System.out.print(counter + "-" +instructor.getName() + " " + instructor.getSurname()+"  ");
				counter++;
			}
			try {
				System.out.print("\nGrades: ");
				Marks marks = student.getPerCourseMarks().get(targetCourse);
				marks.initializeIterator();
				Iterator<Entry<String, Double>> iterator = marks.getIterator();
				while (iterator.hasNext()) {
					Entry<String, Double> current = iterator.next();
					System.out.println(current.getKey()+": "+current.getValue());
				}
			}
			catch(NullPointerException e) {
				System.out.println("No grades have been added to your record yet.");
			}
		}
				
		else {
			System.out.println("Not able to print : You are NOT enrolled in "+targetCourse.getCourseName());
		}
	}
	
	private void wantToLogOut() {
		showOperations();
		chooseOperation(this.input);
	}
	
	public Scanner getInput() {
		return this.input;
	}
	
	
}
