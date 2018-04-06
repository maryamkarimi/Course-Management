
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;

public class LoginServer {

	private Hashtable<String,String> IDTypeHashtable; // ID - type (i-s-m)
	private Hashtable<String,String> IDPasswordHashtable; // Unique ID - password
	private static LoginServer instance;
	private static String fileName = "userpass.txt";
	
	private LoginServer(){
		
		IDTypeHashtable = new Hashtable<String,String>();
		IDPasswordHashtable = new Hashtable<String,String>();
		
		try {
			Scanner scanner = new Scanner (new FileReader(fileName));
			String line = "";
			// skip the first line
			if (scanner.hasNext()) {
				line = scanner.next();
			}
			
			while (scanner.hasNext()) {
				line = scanner.next();
				String token[] = line.split(",");
				IDTypeHashtable.put(token[1], token[0]);
				IDPasswordHashtable.put(token[1], token[2]);
			}
		
			scanner.close();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String isValid(String id, String password) throws NoSuchAlgorithmException{
		try {
			// no such ID
			if (!IDPasswordHashtable.keySet().contains(id)) {
				return "n1";
			}
			
			// ID and password don't match
			else if (!IDPasswordHashtable.get(id).equals(encrypt(password))) {
				return "n2";
			}
			
			// ID and password match
			else {
				return IDTypeHashtable.get(id);
			}
		}
		catch(NoSuchAlgorithmException e) {
			e.getStackTrace();
		}
		return "n1";
	}
	
	public static LoginServer getInstance(){
		if(instance == null)
			instance = new LoginServer();
		return instance;
	}
	
	public void addUser(String userType,String ID, String password) {
		
		try {
			// add it to hashtables.
			IDTypeHashtable.put(ID, userType);
			IDPasswordHashtable.put(ID, encrypt(password));

			// add it to userpass file
			try {
				FileWriter fileWriter = new FileWriter(fileName);
				BufferedWriter bufferedWriter =  new BufferedWriter(fileWriter);
				
				Iterator<String> iterator = IDTypeHashtable.keySet().iterator();
				bufferedWriter.write("UserType,UniqueID,Password");
				bufferedWriter.newLine();
				while (iterator.hasNext()) {
					String UserID = iterator.next();
					bufferedWriter.write(IDTypeHashtable.get(UserID)+","+UserID+","+ IDPasswordHashtable.get(UserID));
					bufferedWriter.newLine();
				}
				bufferedWriter.close();
			}
			catch(IOException e) {
				System.out.println(e.getMessage());
			}
		
		}
		catch(NoSuchAlgorithmException e) {
			e.getStackTrace();
		}
		
	}
	
	private String encrypt(String password) throws NoSuchAlgorithmException {
		String generatedPassword = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] bytes = md.digest();
			
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
		}
		catch(NoSuchAlgorithmException e) {
			
		}
		return generatedPassword;
	}
	
}
