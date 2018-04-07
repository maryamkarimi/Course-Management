package registrar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import offerings.Course;
import systemUsers.SystemUser;

public class Register {
//	Map maintaining copies of existing SystemUserModel objects mapped using their Unique IDs
	private Map<String, SystemUser> modelRegister = new HashMap<String, SystemUser>();
//	Map maintaining copies of existing CourseOffering objects mapped using their Unique IDs
	private Map<String, Course> courseRegister = new HashMap<String, Course>();
	
	private static Register instance;
	private Register(){
	}
	
	public static Register getInstance(){
		if(instance == null)
			instance = new Register();
		return instance;
	}

	public boolean checkIfUserHasAlreadyBeenCreated(String ID){
		return modelRegister.containsKey(ID);
	}
	
	public void registerUser(String ID, SystemUser user){
		modelRegister.put(ID, user);
	}
	
	public SystemUser getRegisteredUser(String ID){
		return modelRegister.get(ID);
	}
	
	public boolean checkIfCourseHasAlreadyBeenCreated(String ID){
		return courseRegister.containsKey(ID);
	}
	
	public void registerCourse(String ID, Course course){
		courseRegister.put(ID, course);
	}
	
	public Course getRegisteredCourse(String ID){
		return courseRegister.get(ID);
	}
	
	public List<Course> getAllCourses(){
		List<Course> courses = new ArrayList<Course>();
		courses.addAll(courseRegister.values());
		return courses;
	}

}
