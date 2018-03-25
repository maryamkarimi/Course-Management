package cs2212;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdministratorSession{

	Administrator admin;
	String username;
	SystemStatus status;
	Scanner input;
	ArrayList<Course> newCoursesAddedList;
	
	public AdministratorSession(String username, SystemStatus status){
		System.out.println("Welcome back "+username+ " - What do you want to do?");
		this.newCoursesAddedList = new ArrayList<Course> ();
		this.username = username;
		this.status = status;
		showOperations();
	}
	
	public void showOperations() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("1. Start the System\t2. Stop the System\t3. Read Course Files");
		System.out.println("------------------------------------------------------------------------------");
		System.out.print("Choose one of the above: ");
	}
	
	public int chooseOperation(Scanner input) {
		this.input = input;
		int a = this.input.nextInt();
		return a;
	}
	
	public void startSystem() {
		status.start();
		System.out.println("System is started now.");
		wantToLogOut();
	}
	
	public void stopSystem() {
		status.stop();
		System.out.println("System is sttoped now.");
		wantToLogOut();
	}
	
	public void readFile() {
		try {
			OfferingFactory factory = new OfferingFactory();
			System.out.print("Please enter the name of the file: ");
			String filename = this.input.next();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			Course courseOffering = factory.createCourseOffering(br);
			br.close();
			System.out.println(courseOffering.getCourseName() +" has been added successfully.");
			newCoursesAddedList.add(courseOffering);
			wantToLogOut();
		}	
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void wantToLogOut() {
		System.out.println("To log out the system, enter exit. To continue, press any other key.");
		if (!this.input.next().toLowerCase().equals("exit")) {
			showOperations();
			int option = chooseOperation(this.input);
			if ( option == 1) {
				startSystem();
			}
			else if (option == 2) {
				stopSystem();
			}
			else {
				readFile();
			}
		}
	}
	
	public Scanner getInput() {
		return this.input;
	}
	
	public ArrayList<Course> getNewCoursesAdded(){
		return newCoursesAddedList;
	}
	
	public SystemStatus getStatus() {
		return status;
	}
	
}
