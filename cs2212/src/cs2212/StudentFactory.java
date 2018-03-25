package cs2212;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentFactory implements SystemUserFactory {

	public Student createSystemUserModel(BufferedReader br, Course course) {
		// TODO Auto-generated method stub
		Student newStudent;
		try{
		String line = br.readLine();
		if(!Register.getInstance().checkIfUserHasAlreadyBeenCreated(line.split("\t")[2])){
//			Consume a line and populate the available fields as well as initialize all fields that need initialization
//			notice that we are using ModelRegister which is used to keep track of previously created instances with specific IDs
			newStudent = new Student();
			newStudent.setName(line.split("\t")[0]);
			newStudent.setSurname(line.split("\t")[1]);
			newStudent.setID(line.split("\t")[2]);
			ArrayList<Course> toInput = new ArrayList<Course>();
			newStudent.setCoursesAllowed(toInput);
			Map<Course, EvaluationTypes> toInput1 = new HashMap<Course, EvaluationTypes>();
			newStudent.setEvaluationEntities(toInput1);
			Register.getInstance().registerUser(newStudent.getID(), newStudent);
		} 	
			newStudent = (Student) Register.getInstance().getRegisteredUser(line.split("\t")[2]);
			(newStudent.getCoursesAllowed()).add(course);
			newStudent.getEvaluationEntities().put(course, EvaluationTypes.fromString(line.split("\t")[3]));
//			for debugging purposes
//			System.out.println("Name : " + newStudent.getName() + "\nSurname : " + newStudent.getSurname() + "\nID : " + 
//			newStudent.getID() + "\n");
			
			return newStudent;
		}catch(IOException ioe){
			System.out.println(ioe.getMessage() + "exception thrown at StudentModelCreation"); 
			return null;
		}
	}
}
