import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AdministratorOperation {

	Administrator admin;
	
	public AdministratorOperation(Administrator admin) {
		this.admin = admin;
	}
	
	public void startSystem() {
		SystemStatus.getInstance().start();
	}
	
	public void stopSystem() {
		SystemStatus.getInstance().stop();
	}
	
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
	
	public void changePersonalInfo(String name, String surname, String DBirth) {
		this.admin.setBirthday(DBirth);
		this.admin.setName(name);
		this.admin.setSurname(surname);
	}
}
