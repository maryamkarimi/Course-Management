package systemUserOperations;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import offerings.OfferingFactory;
import systemStatus.SystemStatus;
import systemUsers.Administrator;

// This class represents the operations of administrator
public class AdministratorOperations {

	private Administrator admin;
	
	public AdministratorOperations(Administrator admin) {
		this.admin = admin;
	}
	
	// Administrators can start the system by calling this method - it sets the flag of SystemStatus instance to true which as a result allows students and instructors to use the system
	public void startSystem() {
		SystemStatus.getInstance().start();
	}
	
	// Administrators can stop the system by calling this method - it sets the flag of SystemStatus instance to false.
	public void stopSystem() {
		SystemStatus.getInstance().stop();
	}
	
	// this method gets a file name as an input and adds all the courses and users in the file to the system, it throws IOException if the name of the file is not valid.
	public void readCourseFile(String fileName) throws IOException {
		try {
		OfferingFactory factory = new OfferingFactory();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		factory.createCourseOffering(br);
		br.close();
		}
		catch(IOException e) {
			throw new IOException();
		}
	}
	
	// this is a setter method for name, surname, and date of birth of administrators
	public void changePersonalInfo(String name, String surname, String DBirth) {
		this.admin.setBirthday(DBirth);
		this.admin.setName(name);
		this.admin.setSurname(surname);
	}
}
