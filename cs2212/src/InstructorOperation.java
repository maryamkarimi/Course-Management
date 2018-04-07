import java.util.ArrayList;
import java.util.Map;

public class InstructorOperation {
	
	private Instructor instructor;
	
	public InstructorOperation(Instructor instructor) {
		this.instructor = instructor;
	}
	
	// throws NullPointerException if some grades are not available to calculate the final grade, returns the final grade otherwise.
	public double calculateFinalGrade(Course targetCourse, Student targetStudent) throws NullPointerException {
		double grade = targetCourse.calculateFinalGrade(targetStudent);
		addGrade(targetCourse, targetStudent,"Final Grade", grade);
		return grade;
	}
	
	// returns true if successful, returns false if entity is not valid.
	public boolean addGrade(Course targetCourse,Student targetStudent, String eval, double mark) {
		
		ArrayList<String> entities = new ArrayList<String>();
		Weights weights = targetCourse.getEvaluationStrategies().get(targetStudent.getEvaluationEntities().get(targetCourse));
		weights.initializeIterator();
		while(weights.hasNext()) {
			weights.next();
			entities.add(weights.getCurrentKey().toLowerCase());
		}
		
		if (!entities.contains(eval.toLowerCase())) {
			return false;
		}
		else {
			Marks marks  = targetStudent.getPerCourseMarks().get(targetCourse);
			if (marks == null) {
				marks = new Marks();
			}
			marks.addToEvalStrategy(eval, mark);
			Map<Course,Marks> map = targetStudent.getPerCourseMarks();
			map.put(targetCourse, marks);
			return true;
		}
	}
	
	
	public String printCourseRecord(Course targetCourse) {
		String result = "";
		result+="Course ID: "+targetCourse.getCourseID()+"\n\nCourse name: "+targetCourse.getCourseName()+
				"\n\nSemester: "+targetCourse.getSemester()+"\n\nInstrcutors:\n";
		int counter =1;
		for (Instructor instructor: targetCourse.getInstructorList()) {
			result+=counter+"-"+instructor.getName()+" "+instructor.getSurname()+"\n";
			counter++;
		}
		
		result+= "\nStudents Enrolled: ( Total :"+targetCourse.getStudentsEnrolledList().size()+" )\n";
		for(Student student : targetCourse.getStudentsEnrolledList()){
			result+="Student name : " + student.getName() + "\nStudent surname : " + student.getSurname() + 
					"\nStudent ID : " + student.getID() + "\nStudent EvaluationType : " + 
					student.getEvaluationEntities().get(targetCourse)+"\nGrades:\n";

					Weights weights = targetCourse.getEvaluationStrategies().get(student.getEvaluationEntities().get(targetCourse));
					weights.initializeIterator();
					while (weights.hasNext()) {
							try {
								weights.next();
								result+=weights.getCurrentKey()+": ";
								if (student.getPerCourseMarks().get(targetCourse).getValueWithKey(weights.getCurrentKey()) != null) {
									result+=student.getPerCourseMarks().get(targetCourse).getValueWithKey(weights.getCurrentKey());
								}
								else {
									result+="N/A";
								}
							}
							catch(NullPointerException e) {
								result+="N/A";
							}	
							result+="\n";
					}
			result+="\n";			
		}
		
		result+= "Students Allowed: ( Total :"+targetCourse.getStudentsAllowedList().size()+" )\n";
		for(Student student : targetCourse.getStudentsAllowedList()){
			result+="Student name : " + student.getName() + "\nStudent surname : " + student.getSurname() + 
					"\nStudent ID : " + student.getID() + "\nStudent EvaluationType : " + 
					student.getEvaluationEntities().get(targetCourse) + "\n\n";
		}
		return result;
	}
	
}
