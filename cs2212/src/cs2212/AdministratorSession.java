package cs2212;

public class AdministratorSession{

	Administrator admin;
	String username;
	
	public AdministratorSession(String username){
		this.username = username;
		showOperations();
	}
	
	public void showOperations() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("Welcome back "+username+ " - What do you want to do today?");
		System.out.println("1. Start the System\t2. Stop the System\t3. Read Course Files");
		System.out.println("------------------------------------------------------------------------------");
		System.out.print("Choose one of the above: ");
		
	}
	
}
