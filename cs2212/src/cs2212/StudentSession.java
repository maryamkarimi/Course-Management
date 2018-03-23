package cs2212;

public class StudentSession{

	Student student;
	String username;
	
	public StudentSession(String username){
		this.username = username;
		showOperations();
	}
	
	public void showOperations() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("Welcome back "+username+ " - What do you want to do today?");
		System.out.println("1. Enroll in a course\t2. Select notification status\t3. print course record ");
		System.out.println("------------------------------------------------------------------------------");
		System.out.print("Choose one of the above: ");
		
	}
	
	public void chooseOperation(int n) {
	}
	
}
