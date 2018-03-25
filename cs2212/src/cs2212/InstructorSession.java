package cs2212;

import java.util.Scanner;

public class InstructorSession {
	Administrator instructor;
	String username;
	Scanner input;
	
	public InstructorSession(String username){
		this.username = username;
		showOperations();
	}
	
	public void showOperations() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("Welcome back "+username+ " - What do you want to do today?");
		System.out.println("1. Add mark for a student\t2. Modify mark for a student\t3. Calculate final grade for a student\t4. Print class record");
		System.out.println("------------------------------------------------------------------------------");
		System.out.print("Choose one of the above: ");
		
	}
	
	public int chooseOperation(Scanner input) {
		this.input = input;
		int a = input.nextInt();
		return a;
	}
}
