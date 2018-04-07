package systemUserFactories;

import java.io.BufferedReader;

import offerings.ICourse;
import systemUsers.ISystemUser;

public interface SystemUserFactory {
	ISystemUser createSystemUserModel(BufferedReader br, ICourse course);
}
