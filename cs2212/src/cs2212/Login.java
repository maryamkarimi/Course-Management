package cs2212;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Login {

	LoginServer server;
	SystemStatus status;
	ArrayList<Course> courseList;
	
	public Login() {
		courseList = new ArrayList<Course>();
		status = new SystemStatus();
		server = new LoginServer("userpass.txt");
		Scanner input = new Scanner(System.in);
		
		while(true) {
			System.out.println("==============================================================================");
			String username="";
			String password="";
			
			System.out.print("Please enter your username: ");
			username = input.next();
			
			
			System.out.print("Please enter your password: ");
			password = input.next();
		
			String result = server.isValid(username, password);
			
			// if the user is an admin
			if (result.equals("a")) {
				AdministratorSession session = new AdministratorSession(username,status);
				int option = session.chooseOperation(input);
				if (option == 1)	{
					session.startSystem();
				}
				else if (option == 2) {
					session.stopSystem();
				}
				else {
					session.readFile();
				}
				
				input = session.getInput();
				status = session.getStatus();
				for (Course course: session.getNewCoursesAdded()) {
					courseList.add(course);
				}
			}
			
			// if the user is an student
			else if (result.equals("s")) {
				if (status.isStarted()) {
					StudentSession session = new StudentSession(username);
					int option = session.chooseOperation(input);
				}
				else {
					System.out.println("You are not allowed to use the system at the moment, for  more info call the administrator.");
				}
			}
			
			// if the user is an instructor
			else if(result.equals("i")) {
				if (status.isStarted()) {
					InstructorSession session = new InstructorSession(username);
					int option = session.chooseOperation(input);
				}
				else {
					System.out.println("You are not allowed to use the system at the moment, for more info call the administrator.");
				}
			}
			System.out.println("==============================================================================\n");
		}
		
	}
	
	public static void main(String[] args) {
		
		Login login = new Login();
	}
	
}
