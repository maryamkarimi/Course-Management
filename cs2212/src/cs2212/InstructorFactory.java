package cs2212;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class InstructorFactory implements SystemUserFactory {

	public Instructor createSystemUserModel(BufferedReader br, Course course) {
		// TODO Auto-generated method stub
		Instructor newInstructorModel = new Instructor();
		try{
		String line = br.readLine();
//		Consume a line and parse it to populate the fields available in an Instructor instance.
//		you may need to implement another such method having a different signature
		if(!Register.getInstance().checkIfUserHasAlreadyBeenCreated(line.split("\t")[2])){
			newInstructorModel.setName(line.split("\t")[0]);
			newInstructorModel.setSurname(line.split("\t")[1]);
			newInstructorModel.setID(line.split("\t")[2]);
			newInstructorModel.setIsTutorOf(new ArrayList<Course>());
			Register.getInstance().registerUser(newInstructorModel.getID(), newInstructorModel);
		} 
		newInstructorModel = (Instructor) Register.getInstance().getRegisteredUser(line.split("\t")[2]);
		newInstructorModel.getIsTutorOf().add(course);
		return newInstructorModel;
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
			return null;
		}
	}

}
