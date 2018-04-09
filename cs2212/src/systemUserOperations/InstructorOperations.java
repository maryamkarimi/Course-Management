package systemUserOperations;
import java.util.ArrayList;
import java.util.Map;

import customDataTypes.Marks;
import customDataTypes.Weights;
import offerings.Course;
import offerings.ICourse;
import systemUsers.Instructor;
import systemUsers.Student;

// This class represents the operations that instructors perform - these operations are only allowed when system is started by administrator
public class InstructorOperations {
	
	private Instructor instructor;
	
	public InstructorOperations(Instructor instructor) {
		this.instructor = instructor;
	}
	
	// This method calculates final grade for a specific student in a specific course
	// throws NullPointerException if some grades are not available to calculate the final grade, returns the final grade otherwise.
	public double calculateFinalGrade(Course targetCourse, Student targetStudent) throws NullPointerException {
		double grade = targetCourse.calculateFinalGrade(targetStudent);
		addGrade(targetCourse, targetStudent,"FINAL GRADE", grade);
		return grade;
	}
	
	// returns true if successful, returns false if entity is not valid.
	public String addGrade(Course targetCourse,Student targetStudent, String eval, double mark) {
		
		// if instructor is not tutor of this course, the following message will be shown.
		if (!instructor.isTutorOf(targetCourse.getCourseID())) {
			return "InstructorNotAllowed";
		}
		else {
			ArrayList<String> entities = new ArrayList<String>();
			Weights weights = targetCourse.getEvaluationStrategies().get(targetStudent.getEvaluationEntities().get(targetCourse));
			weights.initializeIterator();
			while(weights.hasNext()) {
				weights.next();
				entities.add(weights.getCurrentKey().toLowerCase());
			}
			
			if (!entities.contains(eval.toLowerCase()) && !eval.equals("FINAL GRADE")) {
				return "EntityNotValid";
			}
			else {
				Marks marks  = targetStudent.getPerCourseMarks().get(targetCourse);
				if (marks == null) {
					marks = new Marks();
				}
				
				marks.addToEvalStrategy(eval, mark);
				Map<ICourse,Marks> map = targetStudent.getPerCourseMarks();
				map.put(targetCourse, marks);
				return "successful";
			}
		}
	}
	
	// this method returns an string with all of the info for a specified course
	public String printCourseRecord(Course targetCourse) {
		String result = "";
		result+="Course ID: "+targetCourse.getCourseID()+"\n\nCourse Name: "+targetCourse.getCourseName()+
				"\n\nSemester: "+targetCourse.getSemester()+"\n\nInstrcutors:\n";
		int counter =1;
		for (Instructor instructor: targetCourse.getInstructor()) {
			result+=counter+"-"+instructor.getName()+" "+instructor.getSurname()+"\n";
			counter++;
		}
		result+= "\nStudents Enrolled: ( Total :"+targetCourse.getStudentsEnrolled().size()+" )\n";
		for(Student student : targetCourse.getStudentsEnrolled()){
			result+="Student Name : " + student.getName() + "\nStudent Surname : " + student.getSurname() + 
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
			
			if (student.getPerCourseMarks().get(targetCourse).getValueWithKey("FINAL GRADE") != null) {
				result += "FinalGrade: "+student.getPerCourseMarks().get(targetCourse).getValueWithKey("FINAL GRADE")+"\n";
			}
			result+="\n";			
		}
		
		result+= "Students Allowed: ( Total :"+targetCourse.getStudentsAllowedToEnroll().size()+" )\n";
		for(Student student : targetCourse.getStudentsAllowedToEnroll()){
			result+="Student name : " + student.getName() + "\nStudent surname : " + student.getSurname() + 
					"\nStudent ID : " + student.getID() + "\nStudent EvaluationType : " + 
					student.getEvaluationEntities().get(targetCourse) + "\n\n";
		}
		return result;
	}
}
