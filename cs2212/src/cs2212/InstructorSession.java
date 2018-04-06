
import java.util.ArrayList;
import java.util.Scanner;

public class InstructorSession{
	Instructor instructor;
	Scanner input;
	
	public InstructorSession(Instructor instructor){
		this.instructor = instructor;
		System.out.println("Welcome back "+instructor.getName()+ " - What do you want to do today?");
		showOperations();
	}
	
	private void showOperations() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("1. Add mark for a student\t2. Modify mark for a student\t3. Calculate final grade for a student\t4. Print class record\t 5.Logout");
		System.out.println("------------------------------------------------------------------------------");
	}
	
	public void chooseOperation(Scanner input) {
		this.input = input;
		String option = "4";
		
		do {
			System.out.print("Choose one of the above: ");
			option = this.input.next();
		}
		while(!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") && !option.equals("5"));
	
		
		if (option.equals("1"))	{
			addMarkForStudent();
			wantToLogOut();
		}
		else if (option.equals("2")) {
			modifyMarkForStudent();
			wantToLogOut();
		}
		else if (option.equals("3")) {
			calculateFinalGradeForStudent();
			wantToLogOut();
		}
		else if (option.equals("4")) {
			printClassRecord();
			wantToLogOut();
		}
	}
	
	private void addMarkForStudent() {
		Course targetCourse = Register.getInstance().getRegisteredCourse(getCourseID());
		Student targetStudent =(Student) Register.getInstance().getRegisteredUser(getStudentID());
		if (!targetStudent.isEnrolledIn(targetCourse.getCourseID())) {
			System.out.println("This student is not enrolled in this course.");
		}
		
		else {
				Weights weights = targetCourse.getEvaluationStrategies().get(targetStudent.getEvaluationEntities().get(targetCourse));
				weights.initializeIterator();
				ArrayList<String> entities = new ArrayList<String>();
				System.out.print("Entities for "+targetStudent.getEvaluationEntities().get(targetCourse)+" in " +targetCourse.getCourseName()+" are: ");
				while(weights.hasNext()){
					weights.next();
					System.out.print(weights.getCurrentKey()+" / ");
					entities.add(weights.getCurrentKey().toUpperCase());
				}
				System.out.println(")");
				String eval = "";
				do{
					System.out.println("Choose one of the above or type exit.");
					eval = this.input.next().toUpperCase();
					if (eval.equals("EXIT")) {
						return;
					}
				}
				while (!entities.contains(eval));
				
				double mark = 0;
				do {
					System.out.print("Please enter the mark: ");
					mark = this.input.nextInt();
				}
				while(mark<0 || mark>100);
				targetStudent.addMark(targetCourse,eval,mark);
				System.out.println("Successful: "+eval+" = " + mark);
		}
	}
	
	private void modifyMarkForStudent() {
		Course targetCourse = Register.getInstance().getRegisteredCourse(getCourseID());
		Student targetStudent =(Student) Register.getInstance().getRegisteredUser(getStudentID());
		
		if (!targetStudent.isEnrolledIn(targetCourse.getCourseID())) {
			System.out.println("This student is not enrolled in this course.");
		}
		
		else {
				Weights weights = targetCourse.getEvaluationStrategies().get(targetStudent.getEvaluationEntities().get(targetCourse));
				weights.initializeIterator();
				ArrayList<String> entities = new ArrayList<String>();
				while(weights.hasNext()){
					weights.next();
					if ( targetStudent.getPerCourseMarks().get(targetCourse).getValueWithKey(weights.getCurrentKey()) == null) {
						System.out.print(weights.getCurrentKey()+" : " + "not available\n");
					}
					else {
						System.out.print(weights.getCurrentKey()+" : " + targetStudent.getPerCourseMarks().get(targetCourse).getValueWithKey(weights.getCurrentKey())+"\n");
					}
					entities.add(weights.getCurrentKey().toUpperCase());
				}
				String eval = getValidEval(entities);
				if (!eval.equals("")) {
					double mark = getValidMark();
					targetStudent.addMark(targetCourse,eval,mark);
					System.out.println("Successful: "+ eval+" has been changed to "+mark);
				}
		}
	}
	
	private void calculateFinalGradeForStudent() {
		Course targetCourse = Register.getInstance().getRegisteredCourse(getCourseID());
		Student targetStudent =(Student) Register.getInstance().getRegisteredUser(getStudentID());
		
		try{
			double grade = targetCourse.calculateFinalGrade(targetStudent);
			targetStudent.addMark(targetCourse, "Final Grade", grade);
			System.out.println("Successful : Final Grade for " +targetStudent.getName()+" "+targetStudent.getSurname()+" = "+grade);
		}
		catch(NullPointerException e) {
			System.out.println("Some grades are not available yet to calculate the final course grade.");
		}
	}
	
	private void printClassRecord() {
		Course targetCourse = Register.getInstance().getRegisteredCourse(getCourseID());
		instructor.printCourseRecord(targetCourse);
	}
	
	public Scanner getInput() {
		return this.input;
	}
	
	private void wantToLogOut() {
		showOperations();
		chooseOperation(this.input);
	}
	
	private String getStudentID() {
		String studentID = "";
		do {
			System.out.print("Please enter the student ID: ");
			studentID = this.input.next();
		}
		while (Register.getInstance().getRegisteredUser(studentID) == null);
		return studentID;
	}
	
	private String getCourseID() {
		String courseID = "";
		do {
			System.out.print("Please enter the course ID: ");
			courseID = this.input.next().toUpperCase();
			
			if (Register.getInstance().getRegisteredCourse(courseID) == null) {
				System.out.println("Invalid Course ID");
			}
			
			else if (!instructor.isTutorOf(courseID)) {
				System.out.println("You are not listed as an instructor for this course");
			}
		}
		while(Register.getInstance().getRegisteredCourse(courseID)==null || !instructor.isTutorOf(courseID));
		return courseID;
	}
	
	private double getValidMark() {
		double mark = 0;
		do {
			System.out.print("Please enter the new grade: ");
			mark = this.input.nextInt();
		}
		while(mark<0 || mark>100);
		return mark;
	}
	
	private String getValidEval(ArrayList<String> entities) {
		String eval = "";
		do{
			System.out.println("Enter the name of the entity you want to modify or type in exit.");
			eval = this.input.next().toUpperCase();
			if (eval.equals("EXIT")) {
				return "";
			}
		}
		while (!entities.contains(eval));
		return eval;
		
	}
}
