package cs2212;

import java.util.Scanner;

public class Login {

	public Login() {
		LoginServer server = new LoginServer("userpass.txt");
		Scanner input = new Scanner(System.in);
		
		while(true) {
			System.out.println("==============================================================================");
			String username="";
			String password="";
			
			System.out.print("Please enter your username: ");
			if (input.hasNext()) {
				username = input.next();
			}
			
			System.out.print("Please enter your password: ");
			if (input.hasNext()) {
				password = input.next();
			}
		
			String result = server.isValid(username, password);
			
			// if the user is an administrator
			if (result.equals("a")) {
				try {
					AdministratorSession session = new AdministratorSession(username);
					session.chooseOperation(input);
					input = session.getInput();
				}
				catch(NullPointerException e) {
					System.out.println("You are not registered in the system. Call administrator for more info.");
				}
			}
			
			// if the user is a student
			else if (result.equals("s")) {
				if (SystemStatus.getInstance().isStarted()) {
					try{
						StudentSession session = new StudentSession((Student)Register.getInstance().getRegisteredUser(server.getID(username)));
						session.chooseOperation(input);
						input = session.getInput();
					}
					catch(NullPointerException e) {
						System.out.println("You are not registered in the system. Call administrator for more info.");
					}
					
				}
				else {
					System.out.println("You are not allowed to use the system at the moment, for  more info call the administrator.");
				}
			}
			
			// if the user is an instructor
			else if(result.equals("i")) {
				if (SystemStatus.getInstance().isStarted()) {
					try {
						InstructorSession session = new InstructorSession((Instructor)Register.getInstance().getRegisteredUser(server.getID(username)));
						session.chooseOperation(input);
						input = session.getInput();
					}
					catch(NullPointerException e) {
						System.out.println("You are not registered in the system. Call administrator for more info.");
					}
				}
				else {
					System.out.println("You are not allowed to use the system at the moment, for more info call the administrator.");
				}
			}
			System.out.println("==============================================================================\n\n");
		}
		
	}
	
	public static void main(String[] args) {
		
		new Login();
	}
	
}
