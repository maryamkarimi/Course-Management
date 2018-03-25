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
		while(option!=1 && option!=2 && option!=3 && option!=4);
		
		if (option == 1)	{
			enrollIncourse();
			wantToLogOut();
		}
		else if (option == 2) {
			selectNotificationStatus();
			wantToLogOut();
		}
		else if (option == 3) {
			printCourseEnrolled();
			wantToLogOut();
		}
	}
	
	private void enrollIncourse() {
		String ID = "";
		do {
			System.out.print("Please enter the course ID: ");
			if (input.hasNext()) {
			ID = this.input.next();
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
				if (student.getCoursesEnrolled().contains(targetCourse)){
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
			System.out.print("Please enter the course ID: ");
			if (input.hasNext()) {
			ID = this.input.next();
			}
		}
		while(Register.getInstance().getRegisteredCourse(ID)==null);
		
		Course targetCourse = Register.getInstance().getRegisteredCourse(ID);
		for (Course course : student.getCoursesEnrolled()) {
			System.out.println("enrolled in"+course.getCourseName());
			if (course.getCourseName().equals(targetCourse.getCourseName())) {
					isValidCourse = true;
					break;
			}
		}
		if ( isValidCourse == true) {
			System.out.println("Course ID: "+targetCourse.getCourseID()+"\tCourse name: "+targetCourse.getCourseName()+
				"\tSemester: "+targetCourse.getSemester()+"\nEvaluation Entity: "+student.getEvaluationEntities().get(targetCourse));
				
			Marks marks = student.getPerCourseMarks().get(targetCourse);
			marks.initializeIterator();
			Iterator<Entry<String, Double>> iterator = marks.getIterator();
			while (iterator.hasNext()) {
				Entry<String, Double> current = iterator.next();
				System.out.println(current.getKey()+": "+current.getValue());
			}
		}
				
		else {
			System.out.println("Not able to print : You are now enrolled in "+targetCourse.getCourseName());
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
