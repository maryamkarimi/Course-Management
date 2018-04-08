package systemServers;

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

	private Hashtable<String,String> IDTypeHashtable; // Maps ID to userType (i-s-m) e.g. 1234 >>> "i"
	private Hashtable<String,String> IDPasswordHashtable; // Unique ID - password e.g. 5433 >>>> encrypted value of "98765"
	private static LoginServer instance;
	private static String fileName = "userpass.txt"; // the name of the file that contains IDs, encrypted passwords, and their user type.
	
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
			
			// reads every line
			while (scanner.hasNext()) {
				line = scanner.next();
				// splits the line with delimiter ","
				String token[] = line.split(",");
				// adds ID and userType to IDTypeHashtable
				IDTypeHashtable.put(token[1], token[0]);
				// adds ID and password to IDPasswordHashtable
				IDPasswordHashtable.put(token[1], token[2]);
			}
		
			scanner.close();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// this method gets an ID and password and checks whether they match or not.
	public String isValid(String id, String password) throws NoSuchAlgorithmException{
		try {
			// no such ID exists
			if (!IDPasswordHashtable.keySet().contains(id)) {
				return "dontExist";
			}
			
			// ID and password don't match
			else if (!IDPasswordHashtable.get(id).equals(encrypt(password))) {
				return "dontMatch";
			}
			
			// ID and password match
			else {
				// return the userType of this ID
				return IDTypeHashtable.get(id);
			}
		}
		catch(NoSuchAlgorithmException e) {
			e.getStackTrace();
		}
		return "dontExist";
	}
	
	// this method returns an instance of this class, creates a new one the first time it is called
	public static LoginServer getInstance(){
		if(instance == null)
			instance = new LoginServer();
		return instance;
	}
	
	// this method gets a user ID, password, and userType ("i" representing instructor, "a" representing administrator, and "s" representing student)
	public void addUser(String userType,String ID, String password) {
		
		try {
			// add the user to hash tables
			IDTypeHashtable.put(ID, userType);
			IDPasswordHashtable.put(ID, encrypt(password));

			// add it to userpass.txt file
			try {
				FileWriter fileWriter = new FileWriter(fileName);
				BufferedWriter bufferedWriter =  new BufferedWriter(fileWriter);
				
				Iterator<String> iterator = IDTypeHashtable.keySet().iterator();
				// writes the following text first and goes to the next line
				bufferedWriter.write("UserType,UniqueID,Password");
				bufferedWriter.newLine();
				// writes the info of every user in hash table in each line of file
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
	
	// this method gets a password and encrypts it using MD5
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
			System.out.println(e.getMessage());
		}
		// return the encrypted password
		return generatedPassword;
	}
	
}
