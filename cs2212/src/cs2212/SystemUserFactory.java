package cs2212;

import java.io.BufferedReader;

public interface SystemUserFactory {
	SystemUser createSystemUserModel(BufferedReader br, Course course);
}
