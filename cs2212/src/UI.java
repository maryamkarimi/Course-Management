import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class UI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new UI();
	}

	/**
	 * Create the application.
	 */
	public UI() {
		login();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void login() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(350, 150, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCourseManagementSystem = new JLabel("Course Management System");
		lblCourseManagementSystem.setForeground(SystemColor.activeCaptionText);
		lblCourseManagementSystem.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblCourseManagementSystem.setBounds(168, 58, 267, 37);
		frame.getContentPane().add(lblCourseManagementSystem);
		
		JPasswordField txtPassword = new JPasswordField();
		txtPassword.setBounds(267, 157, 130, 31);
		frame.getContentPane().add(txtPassword);
		
		JTextField txtUserName = new JTextField();
		txtUserName.setBounds(267, 122, 130, 31);
		frame.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblUsername = new JLabel("User ID:");
		lblUsername.setBounds(194, 125, 71, 24);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(194, 164, 71, 16);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("Sign In");
		frame.getRootPane().setDefaultButton(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ID = txtUserName.getText();
				String password = txtPassword.getText();
				LoginServer server = LoginServer.getInstance();
				try {
					String result = server.isValid(ID, password);
					if (result.equals("n1")) {
						txtUserName.setText(null);
						txtPassword.setText(null);
						JOptionPane.showMessageDialog(null,"This username is not recongnized, try again!","Login Error",JOptionPane.ERROR_MESSAGE);
					}
					else if(result.equals("n2")) {
						txtPassword.setText(null);
						JOptionPane.showMessageDialog(null,"Username and Password do not match.","Login Error",JOptionPane.ERROR_MESSAGE);
					}
					// if the user is a student
					else if (result.equals("s")) {
						if (SystemStatus.getInstance().isStarted()) {
							frame.setVisible(false);
							frame.getContentPane().removeAll();
							studentMenu((Student)Register.getInstance().getRegisteredUser(ID));
						}
						else {
							JOptionPane.showMessageDialog(null,"You are not allowed to use the system at the moment, for more info call the administrator.","System Status Error",JOptionPane.PLAIN_MESSAGE);
						}
					}
					// if the user is an instructor
					else if (result.equals("i")) {
						if (SystemStatus.getInstance().isStarted()) {
							frame.setVisible(false);
							frame.getContentPane().removeAll();
							instructorMenu((Instructor)Register.getInstance().getRegisteredUser(ID));
						}
						else {
							JOptionPane.showMessageDialog(null,"You are not allowed to use the system at the moment, for  more info call the administrator.","System Status Error",JOptionPane.PLAIN_MESSAGE);
						}
						
					}
					// if the user is an administrator
					else {
						frame.setVisible(false);
						frame.getContentPane().removeAll();
						adminMenu(ID);
					}
				}
				catch(NoSuchAlgorithmException exception) {
					exception.getStackTrace();
				}
			}
		});
		btnLogin.setBounds(303, 221, 117, 29);
		frame.getContentPane().add(btnLogin);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUserName.setText(null);
				txtPassword.setText(null);
			}
		});
		btnReset.setBounds(174, 221, 117, 29);
		frame.getContentPane().add(btnReset);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(174, 200, 244, 12);
		frame.getContentPane().add(separator);
		
		JLabel lblStudentsAndInstructors = new JLabel("<html>* Students and Instructors might not be able to use the system at the moment.<p>\n** If this is the first time you are signing in, enter your date of birthday as a password. (YYYYMMDD) </html>");
		lblStudentsAndInstructors.setForeground(SystemColor.infoText);
		lblStudentsAndInstructors.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblStudentsAndInstructors.setBounds(58, 263, 536, 37);
		frame.getContentPane().add(lblStudentsAndInstructors);
		
		frame.setVisible(true);
	}
	
	private void adminMenu(String ID) {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Start the System");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemStatus.getInstance().start();
				JOptionPane.showMessageDialog(null,"System is started now.","Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnNewButton.setBounds(193, 94, 187, 44);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnStopTheSystem = new JButton("Stop the System");
		btnStopTheSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemStatus.getInstance().stop();
				JOptionPane.showMessageDialog(null,"System is stopped now.","Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnStopTheSystem.setBounds(193, 141, 187, 44);
		frame.getContentPane().add(btnStopTheSystem);
		
		JButton btnReadCourseFile = new JButton("Read Course File");
		btnReadCourseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				readCourseFile(ID);
			}
		});
		btnReadCourseFile.setBounds(193, 188, 187, 44);
		frame.getContentPane().add(btnReadCourseFile);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Do you want to log out",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					login();
				}
			}
		});
		btnLogOut.setBounds(193, 281, 187, 44);
		frame.getContentPane().add(btnLogOut);
		
		JLabel lblNewLabel = new JLabel("                                         Administrator Menu");
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setBounds(6, 6, 588, 62);
		frame.getContentPane().add(lblNewLabel);
		frame.setVisible(true);
		
		
		JButton btnChangeMyPassword = new JButton("Change My Password");
		btnChangeMyPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				changeMyPassword(ID,"a");	
			}
		});
		btnChangeMyPassword.setBounds(193, 235, 187, 44);
		frame.getContentPane().add(btnChangeMyPassword);
	}
		
	private void readCourseFile(String ID) {

		JLabel lblEnterTheNames = new JLabel("Enter the names of the file(s) and press add.");
		lblEnterTheNames.setBounds(147, 85, 335, 22);
		frame.getContentPane().add(lblEnterTheNames);
		
		JTextField textField = new JTextField();
		textField.setBounds(253, 119, 156, 33);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JTextField textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(253, 164, 156, 33);
		frame.getContentPane().add(textField_1);
		
		JLabel lblNewLabel = new JLabel("File Name");
		lblNewLabel.setBounds(170, 123, 87, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("File Name");
		label.setBounds(170, 164, 87, 25);
		frame.getContentPane().add(label);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(395, 215, -179, 12);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(170, 215, 239, 12);
		frame.getContentPane().add(separator_1);
		
		JButton btnNewButton = new JButton("Read Course File(s)");
		frame.getRootPane().setDefaultButton(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = "";
				String fileName2 = "";
				if (textField_1.getText().equals("") && textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Please fill out the textbox(es)","Read File Error",JOptionPane.ERROR_MESSAGE);
				}
				else {

					if (textField_1.getText().equals("") && !textField.getText().equals("")) {
						fileName = textField.getText();
					}
					else if (!textField_1.getText().equals("") && textField.getText().equals("")) {
						fileName = textField_1.getText();
					}
					else {
						fileName = textField.getText();
						fileName2 = textField_1.getText();
					}
					
					try {
						OfferingFactory factory = new OfferingFactory();
						BufferedReader br = new BufferedReader(new FileReader(fileName));
						Course courseOffering1 = factory.createCourseOffering(br);
						br.close();
						if (!fileName2.equals("")) {
							OfferingFactory factory2 = new OfferingFactory();
							BufferedReader br2 = new BufferedReader(new FileReader(fileName2));
							Course courseOffering2 = factory2.createCourseOffering(br2);
							br2.close();
							JOptionPane.showMessageDialog(null,courseOffering1.getCourseName()+" and "+courseOffering2.getCourseName()+ " has been added successfully","Successful",JOptionPane.PLAIN_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null,courseOffering1.getCourseName()+ " has been added successfully","Successful",JOptionPane.PLAIN_MESSAGE);
						}
					}	
					catch(IOException exception) {
						JOptionPane.showMessageDialog(null,"No such file exists.","Invalid File Name",JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		btnNewButton.setBounds(220, 230, 143, 38);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				adminMenu(ID);
			}
		});
		btnBack.setBounds(220, 273, 143, 38);
		frame.getContentPane().add(btnBack);
		
		JLabel lblNewLabel_1 = new JLabel("                                         Read Course Files");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(SystemColor.textHighlight);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setBounds(6, 6, 588, 45);
		frame.getContentPane().add(lblNewLabel_1);
		frame.setVisible(true);
	}
	
	private void studentMenu(Student student) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(350, 150, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblHeader = new JLabel("");
		lblHeader.setOpaque(true);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setForeground(SystemColor.activeCaptionText);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setBounds(6, 6, 588, 50);
		frame.getContentPane().add(lblHeader);
		frame.setVisible(true);
		lblHeader.setText("                                             Student Menu");
		
		JButton btnNewButton = new JButton("Enroll in a course");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				enrollCourse(student);
			}
		});
		btnNewButton.setBounds(189, 94, 198, 42);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSelectNotificationStatus = new JButton("Select notification status");
		btnSelectNotificationStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				chooseNotificationStatus(student);
				
			}
		});
		btnSelectNotificationStatus.setBounds(189, 145, 198, 42);
		frame.getContentPane().add(btnSelectNotificationStatus);
		
		JButton btnPrintCourseRecord = new JButton("Print course record");
		btnPrintCourseRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				printCourseRecord(student);
			}
		});
		btnPrintCourseRecord.setBounds(189, 197, 198, 42);
		frame.getContentPane().add(btnPrintCourseRecord);
		

		JButton btnChangePass = new JButton("Change my password");
		 btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				changeMyPassword(student.getID(),"s");	
			}
		});
		btnChangePass.setBounds(189, 246, 198, 42);
		frame.getContentPane().add( btnChangePass);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Do you want to log out",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					login();
				}
			}
		});
		btnLogOut.setBounds(189, 297, 198, 42);
		frame.getContentPane().add(btnLogOut);
		
	}
	
	private void printCourseRecord(Student student) {

		JTextField textField = new JTextField();
		textField.setBounds(269, 58, 151, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblCourseId = new JLabel("Course ID:");
		lblCourseId.setBounds(190, 71, 93, 16);
		frame.getContentPane().add(lblCourseId);
		
		JLabel lblCourseName = new JLabel("Course Name:");
		lblCourseName.setVisible(false);
		lblCourseName.setBounds(93, 142, 388, 21);
		frame.getContentPane().add(lblCourseName);
		
		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setVisible(false);
		lblSemester.setBounds(93, 166, 388, 21);
		frame.getContentPane().add(lblSemester);
		
		JLabel lblInstructors = new JLabel("Instructors:");
		lblInstructors.setHorizontalAlignment(SwingConstants.LEFT);
		lblInstructors.setVisible(false);
		lblInstructors.setBounds(93, 190, 388, 21);
		frame.getContentPane().add(lblInstructors);
		
		JLabel lblEvalEntity = new JLabel("Evaluation Entity: ");
		lblEvalEntity.setBounds(93, 214, 375, 16);
		frame.getContentPane().add(lblEvalEntity);
		lblEvalEntity.setVisible(false);
		
		JLabel lblNewLabel = new JLabel("Grades:");
		lblNewLabel.setBounds(93, 235, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setVisible(false);
		
		JLabel gradeslbl = new JLabel("");
		gradeslbl.setVerticalAlignment(SwingConstants.TOP);
		gradeslbl.setBounds(93, 254, 364, 89);
		frame.getContentPane().add(gradeslbl);
		gradeslbl.setVisible(false);
		
		JButton btnNewButton = new JButton("Print Record");
		frame.getRootPane().setDefaultButton(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String courseID = textField.getText().toUpperCase();
				if (Register.getInstance().getRegisteredCourse(courseID)==null) {
					JOptionPane.showMessageDialog(null,"Invalid Course ID ","Error",JOptionPane.ERROR_MESSAGE);
				}
				
				else if (!student.isEnrolledIn(courseID)) {
					JOptionPane.showMessageDialog(null,"You are not enrolled in this course","Error",JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					Course targetCourse = Register.getInstance().getRegisteredCourse(courseID);
					lblCourseName.setText(lblCourseName.getText()+" "+targetCourse.getCourseName());
					lblSemester.setText(lblSemester.getText()+" "+targetCourse.getSemester());
					String instructors ="";
					for (Instructor instructor: targetCourse.getInstructorList()) {
						instructors+=instructor.getName()+" "+instructor.getSurname()+" - ";
					}
					lblInstructors.setText(lblInstructors.getText()+" "+instructors);
					lblEvalEntity.setText(lblEvalEntity.getText()+student.getEvaluationEntities().get(targetCourse));
					String grades;
					try {
					grades = "<html>" +student.printCourseMarks(targetCourse)+"</html>";
					}
					catch(NullPointerException exception) {
						grades = "No Grades have been added to your record yet.";
					}
					gradeslbl.setText(grades);
					lblCourseName.setVisible(true);
					lblSemester.setVisible(true);
					lblInstructors.setVisible(true);
					lblEvalEntity.setVisible(true);
					gradeslbl.setVisible(true);
					lblNewLabel.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(190, 92, 110, 38);
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				studentMenu(student);
			}
		});
		btnGoBack.setBounds(309, 90, 111, 40);
		frame.getContentPane().add(btnGoBack);
		
		JLabel lblNewLabel_1 = new JLabel("                                          Print Course Record");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setBackground(SystemColor.textHighlight);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBounds(6, 6, 588, 40);
		frame.getContentPane().add(lblNewLabel_1);
		frame.setVisible(true);

	}
	
	private void chooseNotificationStatus(Student student) {

		JLabel lblChoose = new JLabel("Choose your Notification Preference:");
		lblChoose.setBounds(180, 78, 243, 16);
		frame.getContentPane().add(lblChoose);
		
		JButton btn1 = new JButton("");
		btn1.setText(NotificationTypes.EMAIL.toString());
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentOperation operations = new StudentOperation(student);
				operations.chooseNotificationPreference(NotificationTypes.EMAIL);
				JOptionPane.showMessageDialog(null,"Your notification preference is set to "+student.getNotificationType().toString(),"Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btn1.setBounds(190, 113, 220, 36);
		frame.getContentPane().add(btn1);

		JButton btn2 = new JButton("");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentOperation operations = new StudentOperation(student);
				operations.chooseNotificationPreference(NotificationTypes.CELLPHONE);
				JOptionPane.showMessageDialog(null,"Your notification preference is set to "+student.getNotificationType().toString(),"Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btn2.setBounds(190, 161, 220, 36);
		frame.getContentPane().add(btn2);
		btn2.setText(NotificationTypes.CELLPHONE.toString());
		
		JButton btn3 = new JButton("");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentOperation operations = new StudentOperation(student);
				operations.chooseNotificationPreference(NotificationTypes.PIGEON_POST);
				JOptionPane.showMessageDialog(null,"Your notification preference is set to "+student.getNotificationType().toString(),"Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btn3.setBounds(190, 209, 220, 36);
		frame.getContentPane().add(btn3);
		btn3.setText(NotificationTypes.PIGEON_POST.toString());
		
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				studentMenu(student);
			}
		});
		btnGoBack.setBounds(190, 257, 220, 36);
		frame.getContentPane().add(btnGoBack);
		
		JLabel lblNewLabel = new JLabel("                                           Notification Status");
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setBounds(6, 6, 588, 41);
		frame.getContentPane().add(lblNewLabel);
		frame.setVisible(true);
	}
	
	
	private void enrollCourse(Student student) {

		JLabel lblEnterTheCourse = new JLabel("Enter the course ID:");
		lblEnterTheCourse.setBounds(154, 82, 192, 25);
		frame.getContentPane().add(lblEnterTheCourse);
		
		JTextField textField = new JTextField();
		textField.setBounds(290, 76, 146, 37);
		frame.getContentPane().add(textField);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(153, 122, 283, 12);
		frame.getContentPane().add(separator);
		
		
		JButton btnEnroll = new JButton("Enroll");
		frame.getRootPane().setDefaultButton(btnEnroll);
		btnEnroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Course targetCourse = Register.getInstance().getRegisteredCourse(textField.getText().toUpperCase());
				if (targetCourse == null) {
					JOptionPane.showMessageDialog(null,"Course ID is not valid.","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					StudentOperation operation = new StudentOperation(student);
					String result = operation.enroll(targetCourse);
					if (result.equals("notAllowed")) {
						JOptionPane.showMessageDialog(null,"You are not allowed to enroll in "+targetCourse.getCourseName(),"Error",JOptionPane.ERROR_MESSAGE);
					}

					
					else if (result.equals("alreadyEnrolled")){
						JOptionPane.showMessageDialog(null,"You are already enrolled in "+targetCourse.getCourseName(),"Error",JOptionPane.ERROR_MESSAGE);
					}
					
					else {
						JOptionPane.showMessageDialog(null,"You are now enrolled in "+targetCourse.getCourseName(),"Successful",JOptionPane.PLAIN_MESSAGE);
					}
				}
				
			}
		});
		btnEnroll.setBounds(230, 143, 130, 37);
		frame.getContentPane().add(btnEnroll);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				frame.getContentPane().removeAll();
				studentMenu(student);
			}
		});
		btnBack.setBounds(230, 182, 130, 37);
		frame.getContentPane().add(btnBack);
		
		JLabel lblNewLabel = new JLabel("                                        Enroll in a Course");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(6, 6, 588, 43);
		frame.getContentPane().add(lblNewLabel);
		frame.setVisible(true);
	}
	
	
	private void instructorMenu(Instructor instructor) {

		JLabel lblNewLabel = new JLabel("                                          Instructor Menu");
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setBounds(6, 6, 588, 53);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnAddMarkFor = new JButton("Add mark for a student");
		btnAddMarkFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				addGrade(instructor);
			}
		});
		btnAddMarkFor.setBounds(175, 71, 224, 45);
		frame.getContentPane().add(btnAddMarkFor);
		
		JButton btnModifyMarkFor = new JButton("Modify mark for a student");
		btnModifyMarkFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				addGrade(instructor);
			}
		});
		btnModifyMarkFor.setBounds(175, 118, 224, 45);
		frame.getContentPane().add(btnModifyMarkFor);
		
		JButton btnCalculateFinalGrade = new JButton("Calculate final grade for a student");
		btnCalculateFinalGrade.setBounds(175, 164, 224, 45);
		btnCalculateFinalGrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				calculateStudentFinalGrade(instructor);
			}
		});
		frame.getContentPane().add(btnCalculateFinalGrade);
		
		JButton btnChangeMyPassword = new JButton("Change my password");
		btnChangeMyPassword.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					changeMyPassword(instructor.getID(),"i");	
			}
		});
		btnChangeMyPassword.setBounds(175, 260, 224, 45);
		frame.getContentPane().add(btnChangeMyPassword);
		
		
		JButton btnPrintClassRecord = new JButton("Print class record");
		btnPrintClassRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				printClassRecord(instructor);
			}
		});
		btnPrintClassRecord.setBounds(175, 212, 224, 45);
		frame.getContentPane().add(btnPrintClassRecord);
		frame.setVisible(true);
		
		JButton button_3 = new JButton("Log Out");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Do you want to log out",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					login();
				}
			}
		});
		button_3.setBounds(175, 307, 224, 45);
		frame.getContentPane().add(button_3);
		
	}

	
	private void printClassRecord(Instructor instructor) {

		
	}
	
	private void addGrade (Instructor instructor) {

		JLabel lblNewLabel = new JLabel("                                                 Add Grade");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setBounds(6, 6, 588, 44);
		frame.getContentPane().add(lblNewLabel);
		

		JLabel lblEntity = new JLabel("Entity:");
		lblEntity.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEntity.setBounds(174, 169, 93, 16);
		frame.getContentPane().add(lblEntity);
		
		JLabel label = new JLabel("Course ID:");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		label.setBounds(174, 97, 93, 16);
		frame.getContentPane().add(label);
		
		JTextField txtCourseID = new JTextField();
		txtCourseID.setColumns(10);
		txtCourseID.setBounds(265, 88, 147, 36);
		frame.getContentPane().add(txtCourseID);

		
		JTextField txtEntity = new JTextField();
		txtEntity.setBounds(265, 160, 147, 36);
		frame.getContentPane().add(txtEntity);
		txtEntity.setColumns(10);
		
		JLabel lblStudentId = new JLabel("Student ID:");
		lblStudentId.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblStudentId.setBounds(174, 134, 107, 16);
		frame.getContentPane().add(lblStudentId);

		JTextField txtStudentID = new JTextField();
		txtStudentID.setColumns(10);
		txtStudentID.setBounds(265, 124, 147, 36);
		frame.getContentPane().add(txtStudentID);

		JTextField txtGrade = new JTextField();
		txtGrade.setSelectionEnd(100);
		txtGrade.setBounds(300, 200, 42, 29);
		frame.getContentPane().add(txtGrade);
		txtGrade.setColumns(10);
		
		JLabel lblGrade = new JLabel("Grade:");
		lblGrade.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblGrade.setBounds(174, 205, 93, 16);
		frame.getContentPane().add(lblGrade);
		
		JButton btnGoBack = new JButton("Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					instructorMenu(instructor);
			}
		});
		
		btnGoBack.setBounds(227, 298, 128, 36);
		frame.getContentPane().add(btnGoBack);

		JButton btnAddGrade = new JButton("Add Grade");
		frame.getRootPane().setDefaultButton(btnAddGrade);
		btnAddGrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Course targetCourse = Register.getInstance().getRegisteredCourse(txtCourseID.getText().toUpperCase());
					Student targetStudent = (Student) Register.getInstance().getRegisteredUser(txtStudentID.getText().toUpperCase());
					
					if (txtGrade.getText().equals(null) || txtEntity.getText().equals(null) || txtStudentID.getText().equals(null) || txtCourseID.getText().equals(null)) {
						JOptionPane.showMessageDialog(null,"Please fill out the box(es).","Error",JOptionPane.ERROR_MESSAGE);
					}
					else if (targetCourse == null) {
						JOptionPane.showMessageDialog(null,"Course ID is not valid","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
					}
					else if (targetStudent == null) {
						JOptionPane.showMessageDialog(null,"Student ID is not valid","Enter valid Student ID.",JOptionPane.ERROR_MESSAGE);
					}
					else if (!targetStudent.isEnrolledIn(targetCourse.getCourseID())) {
						JOptionPane.showMessageDialog(null,"This student is not enrolled in this course.","Enter valid Student ID.",JOptionPane.ERROR_MESSAGE);
					}
					else if (Double.parseDouble(txtGrade.getText())<0 || Double.parseDouble(txtGrade.getText())>100) {
						JOptionPane.showMessageDialog(null,"Grades have to between between 0 and 100.","Grade not valid.",JOptionPane.ERROR_MESSAGE);
					}
					else {
						
						ArrayList<String> entities = new ArrayList<String>();
						Weights weights = targetCourse.getEvaluationStrategies().get(targetStudent.getEvaluationEntities().get(targetCourse));
						weights.initializeIterator();
						while(weights.hasNext()) {
							weights.next();
							entities.add(weights.getCurrentKey().toLowerCase());
						}
						
						if (!entities.contains(txtEntity.getText().toLowerCase())) {
							JOptionPane.showMessageDialog(null,"Entity is not valid.","Enter valid Entity name.",JOptionPane.ERROR_MESSAGE);
						}
						else {
							targetStudent.addMark(targetCourse, txtEntity.getText().toUpperCase(), Double.parseDouble(txtGrade.getText()));
							JOptionPane.showMessageDialog(null,"Grade has been added successfully.","Successful",JOptionPane.PLAIN_MESSAGE);
						}
						
					}
				}
				catch(ClassCastException exception) {
					JOptionPane.showMessageDialog(null,"Something went wrong! try again.","Enter valid IDs.",JOptionPane.ERROR_MESSAGE);
				}
			}
				
		});
		btnAddGrade.setBounds(227, 260, 128, 36);
		frame.getContentPane().add(btnAddGrade);

		JSeparator separator = new JSeparator();
		separator.setBounds(174, 241, 238, 12);
		frame.getContentPane().add(separator);
		frame.setVisible(true);
		
	}
	
	private void calculateStudentFinalGrade(Instructor instructor) {
		JLabel lblEnterTheNames = new JLabel("Enter the course ID and student ID.");
		lblEnterTheNames.setBounds(147, 85, 335, 22);
		frame.getContentPane().add(lblEnterTheNames);
		
		JTextField textField = new JTextField();
		textField.setBounds(253, 119, 156, 33);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JTextField textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(253, 164, 156, 33);
		frame.getContentPane().add(textField_1);
		
		JLabel lblNewLabel = new JLabel("Course ID");
		lblNewLabel.setBounds(170, 123, 87, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("Student ID");
		label.setBounds(170, 164, 87, 25);
		frame.getContentPane().add(label);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(395, 215, -179, 12);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(170, 215, 239, 12);
		frame.getContentPane().add(separator_1);
		
		JButton btnNewButton = new JButton("Calculate Final Grade");
		frame.getRootPane().setDefaultButton(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_1.getText().equals("") && textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Please fill out the textbox(es)","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						Course targetCourse = Register.getInstance().getRegisteredCourse(textField.getText().toUpperCase());
						Student targetStudent =(Student) Register.getInstance().getRegisteredUser(textField_1.getText().toUpperCase());

					
						if (targetCourse == null) {
							JOptionPane.showMessageDialog(null,"Course ID is not valid","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
						}
						else if (targetStudent == null) {
							JOptionPane.showMessageDialog(null,"Student ID is not valid","Enter valid Student ID.",JOptionPane.ERROR_MESSAGE);
						}
						else if (!targetStudent.isEnrolledIn(targetCourse.getCourseID())) {
							JOptionPane.showMessageDialog(null,"This student is not enrolled in this course.","Enter valid Student ID.",JOptionPane.ERROR_MESSAGE);
						}
						else {
							try{
								double grade = targetCourse.calculateFinalGrade(targetStudent);
								targetStudent.addMark(targetCourse, "Final Grade", grade);
								JOptionPane.showMessageDialog(null,"Successful : Final Grade for " +targetStudent.getName()+" "+targetStudent.getSurname()+" = "+grade,"Final Grade calculated",JOptionPane.PLAIN_MESSAGE);
							}
							catch(NullPointerException ex) {
								JOptionPane.showMessageDialog(null,"Some grades are not available yet to calculate the final course grade.","Error Calculating Final Grade",JOptionPane.ERROR_MESSAGE);
							}
						}
					}
					catch(ClassCastException exception) {
						JOptionPane.showMessageDialog(null,"Something went wrong! try again.","Enter valid IDs.",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnNewButton.setBounds(220, 230, 143, 38);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				instructorMenu(instructor);
			}
		});
		btnBack.setBounds(220, 273, 143, 38);
		frame.getContentPane().add(btnBack);
		
		JLabel lblNewLabel_1 = new JLabel("                                         Calculate Final Grade");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(SystemColor.textHighlight);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setBounds(6, 6, 588, 45);
		frame.getContentPane().add(lblNewLabel_1);
		frame.setVisible(true);
	
	}
	
	private void changeMyPassword(String ID,String userType) {
		
		JLabel lblNewLabel = new JLabel("                                        Change Password");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(6, 6, 588, 43);
		frame.getContentPane().add(lblNewLabel);
		frame.setVisible(true);
		
		JLabel lblEnterTheCourse = new JLabel("New password:");
		lblEnterTheCourse.setBounds(154, 82, 192, 25);
		frame.getContentPane().add(lblEnterTheCourse);
		
		JTextField textField = new JTextField();
		textField.setBounds(290, 76, 146, 37);
		frame.getContentPane().add(textField);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(153, 122, 283, 12);
		frame.getContentPane().add(separator);
		
		
		JButton btnChangePass = new JButton("Change password");
		frame.getRootPane().setDefaultButton( btnChangePass);
		 btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.equals(null)) {
					JOptionPane.showMessageDialog(null,"Please enter your new password.","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					LoginServer.getInstance().addUser(userType, ID, textField.getText());
					JOptionPane.showMessageDialog(null,"Password changed successfully. ","Successful.",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		 btnChangePass.setBounds(230, 143, 130, 37);
		frame.getContentPane().add(btnChangePass);
		

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				frame.getContentPane().removeAll();
				if (userType.equals("i")) {
					instructorMenu((Instructor)Register.getInstance().getRegisteredUser(ID));
				}
				else if (userType.equals("a")) {
					adminMenu(ID);
				}
				else {
					studentMenu((Student)Register.getInstance().getRegisteredUser(ID));
				}
			}
		});
		btnBack.setBounds(230, 182, 130, 37);
		frame.getContentPane().add(btnBack);
		
		frame.setVisible(true);
	}
}


