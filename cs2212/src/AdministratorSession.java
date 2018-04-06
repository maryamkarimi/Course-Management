package cs2212;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AdministratorSession implements Session{

	String username;
	Scanner input;

	public AdministratorSession(String username){
		this.username = username;
		System.out.println("Welcome back "+username+ " - What do you want to do?");
		showOperations();
	}
	
	private void showOperations() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("1. Start the System\t2. Stop the System\t3. Read Course Files\t4. Logout");
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
			startSystem();
			wantToLogOut();
		}
		else if (option.equals("2")) {
			stopSystem();
			wantToLogOut();
		}
		else if (option.equals("3")) {
			readFile();
			wantToLogOut();
		}
	
	}
	
	private void startSystem() {
		SystemStatus.getInstance().start();
		System.out.println("System is started now.");
	}
	
	private void stopSystem() {
		SystemStatus.getInstance().stop();
		System.out.println("System is sttoped now.");
	}
	
	private void readFile() {
		try {
			OfferingFactory factory = new OfferingFactory();
			System.out.print("enter the name of the file: ");
			String filename = this.input.next();
			if (filename.equals("4")) {
				return;
			}
			BufferedReader br = new BufferedReader(new FileReader(filename));
			Course courseOffering = factory.createCourseOffering(br);
			br.close();
			System.out.println(courseOffering.getCourseName() +" has been added successfully.");
		}	
		catch(IOException e) {
			System.out.print("No such file. Enter 4 to Log out or ");
			readFile();
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
