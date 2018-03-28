package cs2212;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Map.Entry;

public class StudentSession implements Session{

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
			printCourseRecord();
			wantToLogOut();
		}
	}
	
	private void enrollIncourse() {
		String ID = "";
		do {
			System.out.print("Please enter the course ID or enter 4 to Log out: ");
			ID = this.input.next().toUpperCase();
				if (ID.equals("4")) {
					return;
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
		String notifType = "";
		int counter = 1;
		System.out.println("Notification Types:");
		for ( NotificationTypes notificationTypes:NotificationTypes.values()) {
			System.out.println(counter + "-"+notificationTypes);
			counter++;
		}
		
		do {
			System.out.print("Choose 1, 2, 3, or 4 to exit: ");
			notifType = this.input.next().toUpperCase();
			if (notifType.equals("4")) {
				return;
			}
		}
		while(!notifType.equals("1") && !notifType.equals("2") && !notifType.equals("3"));
		
		switch (notifType) {
			case "1": 
				System.out.println("Enter your email address: ");
				student.setEmail(input.next());
				student.setNotificationType(NotificationTypes.EMAIL);
				break;
			case "2":
				System.out.println("Enter your phone number: ");
				student.setPhoneNumber(input.next());
				student.setNotificationType(NotificationTypes.CELLPHONE);
				break;
			case "3":
				student.setNotificationType(NotificationTypes.PIGEON_POST);
				break;
		}
		System.out.println("Your notification preference is set to "+student.getNotificationType());
		
	}
	
	private void printCourseRecord() {
		String ID = getCourseID();
		if (!ID.equals("")) {
			Course targetCourse = Register.getInstance().getRegisteredCourse(ID);
			System.out.print("Course ID: "+targetCourse.getCourseID()+"\tCourse name: "+targetCourse.getCourseName()+
					"\tSemester: "+targetCourse.getSemester()+"\nEvaluation Entity: "+student.getEvaluationEntities().get(targetCourse)+"\nInstructor(s): ");
				int counter = 1;
				for (Instructor instructor: targetCourse.getInstructorList()) {
					System.out.print(counter + "-" +instructor.getName() + " " + instructor.getSurname()+"  ");
					counter++;
				}
				try {
					student.printCourseMarks(targetCourse);
				}
				catch(NullPointerException e) {
					System.out.println("No grades have been added to your record yet.");
				}
		}
	}
	
	private void wantToLogOut() {
		showOperations();
		chooseOperation(this.input);
	}
	
	public Scanner getInput() {
		return this.input;
	}
	
	private String getCourseID() {
		String courseID = "";
		do {
			System.out.print("Please enter the course ID or 4 to exit: ");
			courseID = this.input.next().toUpperCase();
			
			if (courseID.equals("4")) {
				courseID = "";
				break;
			}
			
			if (Register.getInstance().getRegisteredCourse(courseID)==null) {
				System.out.print("Invalid Course ID - ");
			}
			
			else if (!student.isEnrolledIn(courseID)) {
				System.out.print("You are not enrolled in this course - ");
			}
		}
		while(Register.getInstance().getRegisteredCourse(courseID)==null || !student.isEnrolledIn(courseID));
		
		return courseID;
	}
	
	
}
