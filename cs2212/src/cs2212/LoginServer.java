package cs2212;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class LoginServer {

	private Hashtable<String,String> userPassHashtable; // username - password
	private Hashtable<String,String> userTypeHashtable; // username - type (i-s-m)
	
	public LoginServer(String fileName){
		
		userPassHashtable = new Hashtable<String,String>();
		userTypeHashtable = new Hashtable<String,String>();
		
		try {
			Scanner scanner = new Scanner (new FileReader(fileName));
			
			while (scanner.hasNext()) {
				String line = scanner.next();
				String token[] = line.split(",");
				userTypeHashtable.put(token[1],token[0]);
				userPassHashtable.put(token[1], token[2]);
			}
		
			scanner.close();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String isValid(String username, String password) {
		if (!userPassHashtable.keySet().contains(username)) {
			System.out.println("This username is not recongnized, try again!");
			return "n";
		}
		
		else if (!userPassHashtable.get(username).equals(password)) {
			System.out.println("Username and Password do not match.");
			return "n";
		}
		
		else {
			return userTypeHashtable.get(username);
		}
		
		
	}
}
