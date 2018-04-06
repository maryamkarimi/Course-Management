
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
	
	//public String printCourseRecord(Course course) {
		
	//}
}
