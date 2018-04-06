
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class InstructorFactory implements SystemUserFactory {

	public Instructor createSystemUserModel(BufferedReader br, Course course) {

		Instructor newInstructor = new Instructor();
		try{
		String line = br.readLine();
//		Consume a line and parse it to populate the fields available in an Instructor instance.
//		you may need to implement another such method having a different signature
		if(!Register.getInstance().checkIfUserHasAlreadyBeenCreated(line.split("\t")[2])){
			newInstructor.setName(line.split("\t")[0]);
			newInstructor.setSurname(line.split("\t")[1]);
			newInstructor.setID(line.split("\t")[2]);
			newInstructor.setBirthday(line.split("\t")[3]);
			
			newInstructor.setIsTutorOf(new ArrayList<Course>());
			Register.getInstance().registerUser(newInstructor.getID(), newInstructor);
		} 
		newInstructor = (Instructor) Register.getInstance().getRegisteredUser(line.split("\t")[2]);
		newInstructor.getIsTutorOf().add(course);
		LoginServer.getInstance().addUser("i",line.split("\t")[2], line.split("\t")[3]);
		return newInstructor;
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
			return null;
		}
	}

}
