package systemUserInterfaces;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import offerings.Course;
import offerings.ICourse;
import registrar.Register;
import systemUserOperations.InstructorOperations;
import systemUsers.Instructor;
import systemUsers.Student;

public class InstructorPages extends SystemUserPages{

	private Instructor instructor;
	private InstructorOperations operations;

	/**
	 * Create the application.
	 */
	public InstructorPages(Instructor instructor) {
		this.instructor = instructor;
		operations = new InstructorOperations(instructor);
		instructorMenu();
	}

	/* Provides an interface for instructors to see the operations they are allowed to perform and choose one */
	/* ================================================================= */
	private void instructorMenu() {
	/* ================================================================= */
		// initialize the frame by calling the parent class
		super.initialize();
		
		// header label with text "Instructor Menu"
		JLabel lblHeader = new JLabel("                                          Instructor Menu");
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBounds(6, 6, 588, 55);
		frame.getContentPane().add(lblHeader);
		
		// first operation instructor is allowed to perform : add mark for a student
		JButton btnAddMarkFor = new JButton("Add mark for a student");
		btnAddMarkFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				addGrade();
			}
		});
		btnAddMarkFor.setBounds(175, 71, 224, 45);
		frame.getContentPane().add(btnAddMarkFor);
		
		// second operation instructor is allowed to perform : modify mark for a student
		JButton btnModifyMarkFor = new JButton("Modify mark for a student");
		btnModifyMarkFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				modifyGrade();
			}
		});
		btnModifyMarkFor.setBounds(175, 118, 224, 45);
		frame.getContentPane().add(btnModifyMarkFor);
		
		// third operation instructor is allowed to perform : calculate final grade for a student
		JButton btnCalculateFinalGrade = new JButton("Calculate final grade for a student");
		btnCalculateFinalGrade.setBounds(175, 164, 224, 45);
		btnCalculateFinalGrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				calculateStudentFinalGrade();
			}
		});
		frame.getContentPane().add(btnCalculateFinalGrade);
		
		// 4th operation instructor is allowed to perform : change their password
		JButton btnChangeMyPassword = new JButton("Change my password");
		btnChangeMyPassword.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					changeMyPassword(instructor,"i");	
			}
		});
		btnChangeMyPassword.setBounds(175, 260, 224, 45);
		frame.getContentPane().add(btnChangeMyPassword);
		
		// 5th operation instructor is allowed to perform : print record of a class
		JButton btnPrintClassRecord = new JButton("Print class record");
		btnPrintClassRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				printClassRecord();
			}
		});
		btnPrintClassRecord.setBounds(175, 212, 224, 45);
		frame.getContentPane().add(btnPrintClassRecord);
		
		// login button
		JButton btnLogout = new JButton("Log Out");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Do you want to log out",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					new LoginPage();
				}
			}
		});
		btnLogout.setBounds(175, 307, 224, 45);
		frame.getContentPane().add(btnLogout);
		frame.setVisible(true);
	}

	/* Provides an interface for instructor to print a class record */
	/* ================================================================= */
	private void printClassRecord() {
	/* ================================================================= */	
		// header label with the following text
		JLabel lblHeader = new JLabel("                                         Print Class Record");
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblHeader.setBounds(6, 6, 588, 55);
		frame.getContentPane().add(lblHeader);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(195, 66, 277, 40);
		for (ICourse course: instructor.getIsTutorOf()) {
			comboBox.addItem(course.getCourseID()+":"+course.getCourseName());
		}
		comboBox.setEditable(false);
		comboBox.setPrototypeDisplayValue("Choose a course here:");
		frame.getContentPane().add(comboBox);
		
		JLabel lblCourseID = new JLabel("Course ID:");
		lblCourseID.setBounds(111, 73, 72, 25);
		frame.getContentPane().add(lblCourseID);
		
		// scrollBar for txtArea 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(83, 108, 440, 171);
		frame.getContentPane().add(scrollPane);
		
		// txtArea that will contain the record of a course
		JTextArea txtCourseInfo = new JTextArea();
		scrollPane.setViewportView(txtCourseInfo);
		txtCourseInfo.setEditable(false);
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Course targetCourse = Register.getInstance().getRegisteredCourse(instructor.getIsTutorOf().get(comboBox.getSelectedIndex()).getCourseID());
				String toBePrinted = operations.printCourseRecord(targetCourse);
				txtCourseInfo.setText(toBePrinted);
			}
		});
		
		// go back button
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu();
			}
		});
		btnGoBack.setBounds(242, 291, 110, 39);
		frame.getContentPane().add(btnGoBack);
		frame.setVisible(true);
	}
	
	/* Provides an interface for instructor to add grade for a student */
	/* ================================================================= */
	private void addGrade () {
	/* ================================================================= */
		// header label with the following text
		JLabel lblHeader = new JLabel("                                                 Add Grade");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setOpaque(true);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setBounds(6, 6, 588, 55);
		frame.getContentPane().add(lblHeader);
		
		// label for txtEntity
		JLabel lblEntity = new JLabel("Entity:");
		lblEntity.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEntity.setBounds(174, 169, 93, 16);
		frame.getContentPane().add(lblEntity);
		
		// textField that will contain the entity name
		JTextField txtEntity = new JTextField();
		txtEntity.setBounds(265, 160, 147, 36);
		frame.getContentPane().add(txtEntity);
		txtEntity.setColumns(10);
		
		// label for txtCourseID
		JLabel label = new JLabel("Course ID:");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		label.setBounds(174, 97, 93, 16);
		frame.getContentPane().add(label);
		
		// textField that will contain the course ID
		JTextField txtCourseID = new JTextField();
		txtCourseID.setColumns(10);
		txtCourseID.setBounds(265, 88, 147, 36);
		frame.getContentPane().add(txtCourseID);
		
		// label for txtStudentID
		JLabel lblStudentId = new JLabel("Student ID:");
		lblStudentId.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblStudentId.setBounds(174, 134, 107, 16);
		frame.getContentPane().add(lblStudentId);

		// textField that will contain the student ID
		JTextField txtStudentID = new JTextField();
		txtStudentID.setColumns(10);
		txtStudentID.setBounds(265, 124, 147, 36);
		frame.getContentPane().add(txtStudentID);

		// label for txtGrade
		JLabel lblGrade = new JLabel("Grade:");
		lblGrade.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblGrade.setBounds(174, 205, 93, 16);
		frame.getContentPane().add(lblGrade);
		
		// textField that will contain the grade
		JTextField txtGrade = new JTextField();
		txtGrade.setSelectionEnd(100);
		txtGrade.setBounds(300, 200, 42, 29);
		frame.getContentPane().add(txtGrade);
		txtGrade.setColumns(10);

		// add grade button
		JButton btnAddGrade = new JButton("Add Grade");
		frame.getRootPane().setDefaultButton(btnAddGrade);
		btnAddGrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// get the course with this ID from Register
					Course targetCourse = Register.getInstance().getRegisteredCourse(txtCourseID.getText().toUpperCase());
					
					// get the student with this ID from Register
					Student targetStudent = (Student) Register.getInstance().getRegisteredUser(txtStudentID.getText().toUpperCase());
					
					// if any of the text fields is empty, the following message is shown.
					if (txtGrade.getText().equals(null) || txtEntity.getText().equals(null) || txtStudentID.getText().equals(null) || txtCourseID.getText().equals(null)) {
						JOptionPane.showMessageDialog(null,"Please fill out the text fields.","Error",JOptionPane.ERROR_MESSAGE);
					}
					
					// if no course with this ID exists, the following message will be shown.
					else if (targetCourse == null) {
						JOptionPane.showMessageDialog(null,"Course ID is not valid","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
					}
					
					// if no user with this ID exists, the following message will be shown.
					else if (targetStudent == null) {
						JOptionPane.showMessageDialog(null,"Student ID is not valid","Enter valid Student ID.",JOptionPane.ERROR_MESSAGE);
					}
					
					// if student is not enrolled in this course, the following message will be shown.
					else if (!targetStudent.isEnrolledIn(targetCourse.getCourseID())) {
						JOptionPane.showMessageDialog(null,"This student is not enrolled in this course.","Enter valid Student ID.",JOptionPane.ERROR_MESSAGE);
					}
					
					// if the grade is greater than 100 or less than zero, the following message will be shown.
					else if (Double.parseDouble(txtGrade.getText())<0 || Double.parseDouble(txtGrade.getText())>100) {
						JOptionPane.showMessageDialog(null,"Grades have to between between 0 and 100.","Grade not valid.",JOptionPane.ERROR_MESSAGE);
					}
		
					else {
						// calls addGrade from InstructorOperation class
						String result = operations.addGrade(targetCourse,targetStudent, txtEntity.getText().toUpperCase(), Double.parseDouble(txtGrade.getText()));
						
						if (result.equals("InstructorNotAllowed")) {
							JOptionPane.showMessageDialog(null,"You are not listed as an instructor of the course.","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
						}
						// entity is not valid
						else if (result.equals("EntityNotValid")) {
							JOptionPane.showMessageDialog(null,"Entity is not valid.","Enter valid Entity name.",JOptionPane.ERROR_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null,"Grade has been added successfully.","Successful",JOptionPane.PLAIN_MESSAGE);
						}
						
					}
				}
				// this exception is thrown when instructor enters instructor/administrator ID's instead of student
				catch(ClassCastException exception) {
					JOptionPane.showMessageDialog(null,"Something went wrong! try again.","Enter valid IDs.",JOptionPane.ERROR_MESSAGE);
				}
			}
				
		});
		btnAddGrade.setBounds(227, 260, 128, 36);
		frame.getContentPane().add(btnAddGrade);

		// separates buttons from labels/ textFields
		JSeparator separator = new JSeparator();
		separator.setBounds(174, 241, 238, 12);
		frame.getContentPane().add(separator);
		
		// go back button
		JButton btnGoBack = new JButton("Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu();
			}
		});
		
		btnGoBack.setBounds(227, 298, 128, 36);
		frame.getContentPane().add(btnGoBack);

		frame.setVisible(true);
		
	}
	
	/* Provides an interface for instructor to modify grade for a student */
	/* ================================================================= */
	private void modifyGrade() {
	/* ================================================================= */
		// header label with the following text
		JLabel lblHeader = new JLabel("                                                 Modify Grade");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setOpaque(true);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setBounds(6, 6, 588, 55);
		frame.getContentPane().add(lblHeader);
		
		// label for txtEntity
		JLabel lblEntity = new JLabel("Entity:");
		lblEntity.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEntity.setBounds(174, 169, 93, 16);
		frame.getContentPane().add(lblEntity);
		
		// textField that will contain the entity name
		JTextField txtEntity = new JTextField();
		txtEntity.setBounds(265, 160, 147, 36);
		frame.getContentPane().add(txtEntity);
		txtEntity.setColumns(10);
		
		// label for txtCourseID
		JLabel label = new JLabel("Course ID:");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		label.setBounds(174, 97, 93, 16);
		frame.getContentPane().add(label);
		
		// textField that will contain the course ID
		JTextField txtCourseID = new JTextField();
		txtCourseID.setColumns(10);
		txtCourseID.setBounds(265, 88, 147, 36);
		frame.getContentPane().add(txtCourseID);
		
		// label for txtStudentID
		JLabel lblStudentId = new JLabel("Student ID:");
		lblStudentId.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblStudentId.setBounds(174, 134, 107, 16);
		frame.getContentPane().add(lblStudentId);

		// textField that will contain the student ID
		JTextField txtStudentID = new JTextField();
		txtStudentID.setColumns(10);
		txtStudentID.setBounds(265, 124, 147, 36);
		frame.getContentPane().add(txtStudentID);

		// label for txtGrade
		JLabel lblGrade = new JLabel("Grade:");
		lblGrade.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblGrade.setBounds(174, 205, 93, 16);
		frame.getContentPane().add(lblGrade);
		
		// textField that will contain the grade
		JTextField txtGrade = new JTextField();
		txtGrade.setSelectionEnd(100);
		txtGrade.setBounds(300, 200, 42, 29);
		frame.getContentPane().add(txtGrade);
		txtGrade.setColumns(10);

		// modify grade button
		JButton btnAddGrade = new JButton("Modify Grade");
		frame.getRootPane().setDefaultButton(btnAddGrade);
		btnAddGrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// get the course with this ID from Register
					Course targetCourse = Register.getInstance().getRegisteredCourse(txtCourseID.getText().toUpperCase());
					
					// get the student with this ID from Register
					Student targetStudent = (Student) Register.getInstance().getRegisteredUser(txtStudentID.getText().toUpperCase());
					
					// if any of the text fields is empty, the following message is shown.
					if (txtGrade.getText().equals(null) || txtEntity.getText().equals(null) || txtStudentID.getText().equals(null) || txtCourseID.getText().equals(null)) {
						JOptionPane.showMessageDialog(null,"Please fill out the text fields.","Error",JOptionPane.ERROR_MESSAGE);
					}
					
					// if no course with this ID exists, the following message will be shown.
					else if (targetCourse == null) {
						JOptionPane.showMessageDialog(null,"Course ID is not valid","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
					}
					
					// if no user with this ID exists, the following message will be shown.
					else if (targetStudent == null) {
						JOptionPane.showMessageDialog(null,"Student ID is not valid","Enter valid Student ID.",JOptionPane.ERROR_MESSAGE);
					}
					
					// if student is not enrolled in this course, the following message will be shown.
					else if (!targetStudent.isEnrolledIn(targetCourse.getCourseID())) {
						JOptionPane.showMessageDialog(null,"This student is not enrolled in this course.","Enter valid Student ID.",JOptionPane.ERROR_MESSAGE);
					}
					
					// if the grade is greater than 100 or less than zero, the following message will be shown.
					else if (Double.parseDouble(txtGrade.getText())<0 || Double.parseDouble(txtGrade.getText())>100) {
						JOptionPane.showMessageDialog(null,"Grades have to between between 0 and 100.","Grade not valid.",JOptionPane.ERROR_MESSAGE);
					}
		
					else {
						if (targetStudent.getPerCourseMarks().get(targetCourse).getValueWithKey(txtEntity.getText().toUpperCase()) == null) {
							JOptionPane.showMessageDialog(null,"No grade has been added before, to add a grade for the first time go to Add Grade.","No grade found.",JOptionPane.ERROR_MESSAGE);
						}
						else {
							// calls addGrade from InstructorOperation class
							String result = operations.addGrade(targetCourse,targetStudent, txtEntity.getText().toUpperCase(), Double.parseDouble(txtGrade.getText()));
							
							if (result.equals("InstructorNotAllowed")) {
								JOptionPane.showMessageDialog(null,"You are not listed as an instructor of the course.","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
							}
							// entity is not valid
							else if (result.equals("EntityNotValid")) {
								JOptionPane.showMessageDialog(null,"Entity is not valid.","Enter valid Entity name.",JOptionPane.ERROR_MESSAGE);
							}
							else {
								JOptionPane.showMessageDialog(null,"Grade has been changed successfully.","Successful",JOptionPane.PLAIN_MESSAGE);
							}
						}
					}
				}
				// this exception is thrown when instructor enters instructor/administrator ID's instead of student
				catch(ClassCastException exception) {
					JOptionPane.showMessageDialog(null,"Something went wrong! try again.","Enter valid IDs.",JOptionPane.ERROR_MESSAGE);
				}
			}
				
		});
		btnAddGrade.setBounds(227, 260, 128, 36);
		frame.getContentPane().add(btnAddGrade);

		// separates buttons from labels/ textFields
		JSeparator separator = new JSeparator();
		separator.setBounds(174, 241, 238, 12);
		frame.getContentPane().add(separator);
		
		// go back button
		JButton btnGoBack = new JButton("Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu();
			}
		});
		
		btnGoBack.setBounds(227, 298, 128, 36);
		frame.getContentPane().add(btnGoBack);

		frame.setVisible(true);
		
	}
	
	/* ================================================================= */
	private void calculateStudentFinalGrade() {
	/* ================================================================= */	
		// create an object of InstructorOperation by passing instructor object to it.
		InstructorOperations operations = new InstructorOperations(instructor);
		
		JLabel lblEnterTheNames = new JLabel("Enter the course ID and student ID.");
		lblEnterTheNames.setBounds(147, 85, 335, 22);
		frame.getContentPane().add(lblEnterTheNames);
	
		// textField that will contain the course ID
		JTextField txtCourseID = new JTextField();
		txtCourseID.setBounds(253, 119, 156, 33);
		frame.getContentPane().add(txtCourseID);
		txtCourseID.setColumns(10);
		
		// label for txtCourseID
		JLabel lblCourseID = new JLabel("Course ID");
		lblCourseID.setBounds(170, 123, 87, 25);
		frame.getContentPane().add(lblCourseID);
		
		// textField that will contain the student ID
		JTextField txtStudentID = new JTextField();
		txtStudentID.setColumns(10);
		txtStudentID.setBounds(253, 164, 156, 33);
		frame.getContentPane().add(txtStudentID);

		// label for txtStudentID
		JLabel lblStudentID = new JLabel("Student ID");
		lblStudentID.setBounds(170, 164, 87, 25);
		frame.getContentPane().add(lblStudentID);
				
		// separates buttons from textFields/ labels
		JSeparator separator = new JSeparator();
		separator.setBounds(170, 215, 239, 12);
		frame.getContentPane().add(separator);
		
		JButton btnCalculateGrade = new JButton("Calculate Final Grade");
		frame.getRootPane().setDefaultButton(btnCalculateGrade);
		btnCalculateGrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if any of the text fields is empty
				if (txtStudentID.getText().equals("") || txtCourseID.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Please fill out the textfield(s)","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						// get the course with this ID from Register
						Course targetCourse = Register.getInstance().getRegisteredCourse(txtCourseID.getText().toUpperCase());
						
						// get the student with this ID from Register
						Student targetStudent = (Student) Register.getInstance().getRegisteredUser(txtStudentID.getText().toUpperCase());

						// if no course with this ID exists, the following message will be shown.
						if (targetCourse == null) {
							JOptionPane.showMessageDialog(null,"Course ID is not valid","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
						}
						
						// if no user with this ID exists, the following message will be shown.
						else if (targetStudent == null) {
							JOptionPane.showMessageDialog(null,"Student ID is not valid","Enter valid Student ID.",JOptionPane.ERROR_MESSAGE);
						}
						
						// if student is not enrolled in this course, the following message will be shown.
						else if (!targetStudent.isEnrolledIn(targetCourse.getCourseID())) {
							JOptionPane.showMessageDialog(null,"This student is not enrolled in this course.","Enter valid Student ID.",JOptionPane.ERROR_MESSAGE);
						}
						
						// if instructor is not tutor of this course, the following message will be shown.
						else if (!instructor.isTutorOf(targetCourse.getCourseID())) {
							JOptionPane.showMessageDialog(null,"You are not listed as an instructor of the course.","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
						}
						
						else {
							try{
								// calls calculateFinalGrade from InstrcutorOperations
								Double grade = operations.calculateFinalGrade(targetCourse, targetStudent);
								JOptionPane.showMessageDialog(null,"Successful : Final Grade for " +targetStudent.getName()+" "+targetStudent.getSurname()+" = "+grade,"Final Grade calculated",JOptionPane.PLAIN_MESSAGE);
							}
							// if NullPointerException is thrown, it means some grades are not available to calculate final grade
							catch(NullPointerException ex) {
								JOptionPane.showMessageDialog(null,"Some grades are not available yet to calculate the final course grade.","Error Calculating Final Grade",JOptionPane.ERROR_MESSAGE);
							}
						}
					}
					// this exception is thrown when instructor enters instructor/administrator ID's instead of student
					catch(ClassCastException exception) {
						JOptionPane.showMessageDialog(null,"Something went wrong! try again.","Enter valid IDs.",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnCalculateGrade.setBounds(220, 230, 143, 38);
		frame.getContentPane().add(btnCalculateGrade);
		
		// go back button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu();
			}
		});
		btnBack.setBounds(220, 273, 143, 38);
		frame.getContentPane().add(btnBack);
		
		// header label
		JLabel lblHeader = new JLabel("                                         Calculate Final Grade");
		lblHeader.setOpaque(true);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBounds(6, 6, 588, 55);
		frame.getContentPane().add(lblHeader);
		
		frame.setVisible(true);
	
	}
	
	private void returnToMenu() {
		frame.setVisible(false);
		frame.getContentPane().removeAll();
		instructorMenu();
	}

}
