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
	
}
