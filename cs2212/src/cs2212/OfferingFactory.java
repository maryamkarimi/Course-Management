package cs2212;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class OfferingFactory {

//	Instantiate 
	public Course createCourseOffering(BufferedReader br) {
		// TODO Auto-generated method stub
		try{
		String line = br.readLine();
		Course course = new Course();
//		Using the ModelRegister - its a utility class that allows us to keep track of which IDs have already been populated
//		if we don't have it registered
		if(!Register.getInstance().checkIfCourseHasAlreadyBeenCreated(line.split("\t")[1])){
//			we populate the course object and initialize all fields
			course.setCourseName(line.split("\t")[0]);
			course.setCourseID(line.split("\t")[1]);
			course.setSemester(Integer.parseInt(line.split("\t")[2]));
			course.setInstructorList(new ArrayList<Instructor>());
			course.setStudentsAllowedList(new ArrayList<Student>());
			course.setEvaluationStrategies(new HashMap<EvaluationTypes, Weights>());
			course.setStudentsEnrolledList(new ArrayList<Student>());
//			and add the new course to the register
			Register.getInstance().registerCourse(course.getCourseID(), course);
		}
//			otherwise we load the existing instance from the register
			course = Register.getInstance().getRegisteredCourse(line.split("\t")[1]);
			line = br.readLine();
//			We create an instance of an InstructorModelFactory to create InstructorModel instances
			SystemUserFactory theFactory = new InstructorFactory();
			for (int i=0;i<Integer.parseInt(line.split("\t")[2]);i++) {
//				the actual create instance method call
				course.getInstructorList().add((Instructor)theFactory.createSystemUserModel(br, course));
			}
			line = br.readLine();
//			create an instance of StudentModelFactory and allocate it to the theFactory variable (same interface) to create StudentModel instances 
			theFactory = new StudentFactory();
			for (int i=0;i<Integer.parseInt(line.split("\t")[2]);i++) {
//				the actual create instance method call
				course.getStudentsAllowedList().add((Student)theFactory.createSystemUserModel(br, course));
			}
//			consuming EVALUATION ENTITIES tag (you'll notice there are other standalone br.readLine() calls 
//			that consume and generally advance the input to the desirable line
			line = br.readLine();
			for(int i=0;i<4;i++) {
				line = br.readLine();
//				Check how this works || we select he appropriate enum value
				EvaluationTypes evaluationTypes = EvaluationTypes.fromString(line);
//				weights is an object we created for encapsulating the bare minimum of Map functionality that you'll probably need
				Weights weights = new Weights();
				line = br.readLine();
				int numberOfEvaluationWeights = Integer.parseInt(line.split("\t")[2]);
//				Read all the evaluation strategies that are available for a particular evaluation type and
				for(int j=0; j<numberOfEvaluationWeights;j++){
					line = br.readLine();
					weights.addToEvalStrategy(line.split("\t")[0], Double.parseDouble(line.split("\t")[1]));
				}
//				add them to the course
				course.getEvaluationStrategies().put(evaluationTypes, weights);
			}
			return course;
		}catch(IOException ioe){
			System.out.println(ioe.getMessage() + "exception thrown at CourseOfferingCreation"); 
			return null;
		}
	}
	
	
}