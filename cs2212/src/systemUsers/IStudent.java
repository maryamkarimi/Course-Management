package systemUsers;

import java.util.List;
import java.util.Map;

import customDataTypes.EvaluationTypes;
import customDataTypes.Marks;
import customDataTypes.NotificationTypes;
import offerings.ICourse;

public interface IStudent extends ISystemUser {

	void setCoursesAllowed(List<ICourse> allowedCourses);
	void setCoursesEnrolled(List<ICourse> enrolledCourses);
	void setEvaluationEntities(Map<ICourse, EvaluationTypes> evaluationEntities);
	void setPerCourseMarks(Map<ICourse, Marks> perCourseMarks);
	void setNotificationType(NotificationTypes notificationType);
	
	List<ICourse> getCoursesAllowed();
	List<ICourse> getCoursesEnrolled();
	Map<ICourse, EvaluationTypes> getEvaluationEntities();
	Map<ICourse, Marks> getPerCourseMarks();
	NotificationTypes getNotificationType();
	
	
}
