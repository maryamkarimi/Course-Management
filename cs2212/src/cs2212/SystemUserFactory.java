
import java.io.BufferedReader;

public interface SystemUserFactory {
	SystemUser createSystemUserModel(BufferedReader br, Course course);
}
