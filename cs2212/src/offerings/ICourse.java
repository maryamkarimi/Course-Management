package offerings;

import java.util.List;
import java.util.Map;

import customDataTypes.EvaluationTypes;
import customDataTypes.Weights;
import systemUsers.Instructor;
import systemUsers.Student;

public interface ICourse {
	
	List<Student> getStudentsAllowedToEnroll();

	void setStudentsAllowedToEnroll(List<Student> studentsAllowedToEnroll);

	List<Student> getStudentsEnrolled();

	void setStudentsEnrolled(List<Student> studentsEnrolled);

	Map<EvaluationTypes, Weights> getEvaluationStrategies();

	void setEvaluationStrategies(Map<EvaluationTypes, Weights> evaluationStrategies);

	String getCourseName();

	void setCourseName(String courseName);

	String getCourseID();

	void setCourseID(String courseID);

	Integer getSemester();

	void setSemester(Integer semester);

	List<Instructor> getInstructor();

	void setInstructor(List<Instructor> instructor);

	void addInstructor(Instructor instructor);

	void removeInstructor(Instructor instructor);
	
}
