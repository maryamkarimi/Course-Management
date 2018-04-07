package systemUserFactories;

import java.io.BufferedReader;

import offerings.Course;
import systemUsers.SystemUser;

public interface SystemUserFactory {
	SystemUser createSystemUserModel(BufferedReader br, Course course);
}
