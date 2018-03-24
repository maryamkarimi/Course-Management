package cs2212;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Login {

	LoginServer server;
	Scanner input;
	SystemStatus status;
	
	public Login() {
		status = new SystemStatus();
		server = new LoginServer("userpass.txt");
		Scanner input = new Scanner(System.in);
		
		for (int i=0; i<3; i++) {
			System.out.println("==============================================================================");
			String username="";
			String password="";
			
			System.out.print("Please enter your username: ");
			username = input.next();
			
			
			System.out.print("Please enter your password: ");
			password = input.next();
		
			String result = server.isValid(username, password);
			if (result.equals("a")) {
				AdministratorSession session = new AdministratorSession(username);
				int a = input.nextInt();
				if (a==1) {
					status.start();
					System.out.println("System is started now.");
				}
				
				else if (a==2) {
					status.stop();
					System.out.println("System is sttoped now.");
				}
				
				else {
					try {
						OfferingFactory factory = new OfferingFactory();
						System.out.print("Please enter the name of the file: ");
						String filename = input.next();
						BufferedReader br = new BufferedReader(new FileReader(filename));
						//Use the factory to populate as many instances of courses as many files we've got.
						Course courseOffering = factory.createCourseOffering(br);
						br.close();
					}	
					catch(IOException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
			else if (result.equals("s")) {
				if (status.isStarted()) {
					StudentSession session = new StudentSession(username);
					int a = input.nextInt();
					session.chooseOperation(a);
				}
				else {
					System.out.println("You are not allowed to use the system at the moment, for  more info call the administrator.");
				}
			}
			
			else if(result.equals("i")) {
				if (status.isStarted()) {
					InstructorSession session = new InstructorSession(username);
					int a = input.nextInt();
					session.chooseOperation(a);
				}
				else {
					System.out.println("You are not allowed to use the system at the moment, for more info call the administrator.");
				}
			}
			System.out.println("==============================================================================\n");
		}
		
		input.close();
	}
	
	public static void main(String[] args) {
		
		Login login = new Login();
	}
	
}
