package systemUsers;

import java.util.List;

import offerings.ICourse;

public interface IInstructor extends ISystemUser {
	
	void setIsTutorOf(List<ICourse> courseInstructed);
	
	boolean isTutorOf(String ID);
	List<ICourse> getIsTutorOf();
}
