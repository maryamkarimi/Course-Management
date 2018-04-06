
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentFactory implements SystemUserFactory {

	public Student createSystemUserModel(BufferedReader br, Course course) {

		Student newStudent;
		try{
		String line = br.readLine();
		if(!Register.getInstance().checkIfUserHasAlreadyBeenCreated(line.split("\t")[2])){

			newStudent = new Student();
			newStudent.setName(line.split("\t")[0]);
			newStudent.setSurname(line.split("\t")[1]);
			newStudent.setID(line.split("\t")[2]);
			newStudent.setBirthday(line.split("\t")[4]);
			ArrayList<Course> toInput = new ArrayList<Course>();
			newStudent.setCoursesAllowed(toInput);
			Map<Course, EvaluationTypes> toInput1 = new HashMap<Course, EvaluationTypes>();
			newStudent.setEvaluationEntities(toInput1);
			Register.getInstance().registerUser(newStudent.getID(), newStudent);
		} 	
			newStudent = (Student) Register.getInstance().getRegisteredUser(line.split("\t")[2]);
			(newStudent.getCoursesAllowed()).add(course);
			newStudent.getEvaluationEntities().put(course, EvaluationTypes.fromString(line.split("\t")[3]));

			LoginServer.getInstance().addUser("s",line.split("\t")[2], line.split("\t")[4]);
			return newStudent;
		}catch(IOException ioe){
			System.out.println(ioe.getMessage() + "exception thrown at StudentModelCreation"); 
			return null;
		}
	}
}
