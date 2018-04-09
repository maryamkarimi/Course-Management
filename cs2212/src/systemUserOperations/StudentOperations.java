package systemUserOperations;
import java.util.Iterator;
import java.util.Map.Entry;

import customDataTypes.Marks;
import customDataTypes.NotificationTypes;
import offerings.Course;
import systemUsers.Instructor;
import systemUsers.Student;

// this class represents the operations that students can perform using the system, only when the system is in a running state i.e. started by the administrators already
public class StudentOperations {

	private Student student;
	
	public StudentOperations(Student student) {
		this.student = student;
	}
	
	// this method gets a notificationType as input and sets student's preference to that.
	public void chooseNotificationPreference(NotificationTypes notificationType) {
		student.setNotificationType(notificationType);
	}
	
	// this method gets a course object as input and enrolls the student in that course
	// returns true if successful, "notAllowed" if student is not allowed to enroll in it, and "alreadyEnrolled" if student is already enrolled in the specified course.
	public String enroll(Course course) {
		if (!student.isAllowedToEnrollIn(course)) {
			return "notAllowed";
		}
		else if (student.isEnrolledIn(course.getCourseID())) {
			return "alreadyEnrolled";
		}
		else {
			student.getCoursesEnrolled().add(course);
			course.getStudentsEnrolled().add(student);
			return "successful";
		}
	}
	
	// this method returns a string of all the info for a specified course.
	public String printCourseRecord(Course course) {
		String result = "";
		result+="Course ID: "+course.getCourseID()+"\n\nCourse name: "+course.getCourseName()+
				"\n\nSemester: "+course.getSemester()+"\n\nInstructors:\n";
		int counter =1;
		for (Instructor instructor: course.getInstructor()) {
			result+=counter+"-"+instructor.getName()+" "+instructor.getSurname()+"\n";
			counter++;
		}
		
		String grades = "\nGrades:\n";
		try {
		grades+= printCourseMarks(course);
		}
		catch(NullPointerException exception) {
			grades = "\nGrades: No Grades have been added to your record yet.";
		}
		
		result+=grades;
		
		return result;
	}
	
	// prints the grades of a course - used by printCourseRecord method above
	private String printCourseMarks(Course targetCourse) {
		String results = "";
		Marks marks = this.student.getPerCourseMarks().get(targetCourse);
		marks.initializeIterator();
		Iterator<Entry<String, Double>> iterator = marks.getIterator();
		while (iterator.hasNext()) {
			Entry<String, Double> current = iterator.next();
			results+=current.getKey()+": "+current.getValue()+"\n";
		}
		return results;
	}
}
