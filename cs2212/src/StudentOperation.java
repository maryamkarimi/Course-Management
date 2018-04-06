import java.util.Iterator;
import java.util.Map.Entry;

public class StudentOperation {

	private Student student;
	
	public StudentOperation(Student student) {
		this.student = student;
	}
	
	public void chooseNotificationPreference(NotificationTypes notificationType) {
		student.setNotificationType(notificationType);
	}
	
	// returns true if successful, false otherwise.
	public String enroll(Course course) {
		if (!student.isAllowedToEnrollIn(course)) {
			return "notAllowed";
		}
		else if (student.isEnrolledIn(course.getCourseID())) {
			return "alreadyEnrolled";
		}
		else {
			student.getCoursesEnrolled().add(course);
			course.getStudentsEnrolledList().add(student);
			return "successful";
		}
	}
	
	// prints the grades of a course
	public String printCourseMarks(Course targetCourse) {
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
	
	public String printCourseRecord(Course course) {
		String result = "";
		result+="Course ID: "+course.getCourseID()+"\n\nCourse name: "+course.getCourseName()+
				"\n\nSemester: "+course.getSemester()+"\n\nInstructors:\n";
		int counter =1;
		for (Instructor instructor: course.getInstructorList()) {
			result+=counter+"-"+instructor.getName()+" "+instructor.getSurname()+"\n";
			counter++;
		}
		
		String grades = "\n\nGrades:\n";
		try {
		grades = printCourseMarks(course);
		}
		catch(NullPointerException exception) {
			grades = "\nGrades: No Grades have been added to your record yet.";
		}
		
		result+=grades;
		
		return result;
	}
}
