import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UI {

	private JFrame frame;

	/* Launch the application */
	/* ================================================================= */
	public static void main(String[] args) {
	/* ================================================================= */
		new UI();
	}


	/* Create the application.*/
	/* ================================================================= */
	public UI() {
	/* ================================================================= */
		login();
	}


	/* Initialize the contents of the frame and show the login interface */
	/* ================================================================= */
	private void login() {
	/* ================================================================= */
		
		// create the frame of fixed size
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(350, 150, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// the label on top of the login page
		JLabel lblCourseManagementSystem = new JLabel("Course Management System");
		lblCourseManagementSystem.setForeground(SystemColor.activeCaptionText);
		lblCourseManagementSystem.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblCourseManagementSystem.setBounds(168, 58, 267, 37);
		frame.getContentPane().add(lblCourseManagementSystem);
		
		// label for ID
		JLabel lblUsername = new JLabel("User ID:");
		lblUsername.setBounds(194, 125, 71, 24);
		frame.getContentPane().add(lblUsername);
		
		// the textField that gets the Unique ID from user
		JTextField txtUserName = new JTextField();
		txtUserName.setBounds(267, 122, 130, 31);
		frame.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		// label for Password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(194, 164, 71, 16);
		frame.getContentPane().add(lblPassword);
		
		// the textField that gets the password from user
		JPasswordField txtPassword = new JPasswordField();
		txtPassword.setBounds(267, 157, 130, 31);
		frame.getContentPane().add(txtPassword);
		
		// sign in button
		JButton btnLogin = new JButton("Sign In");
		// set sign in button as the default button of this page so user can press enter to login.
		frame.getRootPane().setDefaultButton(btnLogin);
		
		// when user click on sign in button
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// set ID, password based on text fields.
				String ID = txtUserName.getText();
				String password = String.valueOf(txtPassword.getPassword());
				
				// if any of the text fields is empty, shows an error message.
				if (ID.equals(null) || password.equals(null)) {
					JOptionPane.showMessageDialog(null,"Fill out the boxes to sign in.","Login Error",JOptionPane.ERROR_MESSAGE);
				}
				
				// if both of the text fields are filled, requests validation from login server.
				else {
					
					// get the LoginServer instance
					LoginServer server = LoginServer.getInstance();
					
					try {
						
						//passes the ID and password to server and gets either "n1","n2","i","s",or "a" as an output.
						String result = server.isValid(ID, password);
						
						// "n1" represents the case that no user with such ID exists.
						if (result.equals("n1")) {
							
							// reset both of the text fields and shows a message to user indicating the situation.
							txtUserName.setText(null);
							txtPassword.setText(null);
							JOptionPane.showMessageDialog(null,"This username is not recongnized, try again!","Login Error",JOptionPane.ERROR_MESSAGE);
						}
						
						// "n2" represents the case that the ID and password do not match.
						else if(result.equals("n2")) {
							
							// reset the password text field and shows a message to user indicating the situation.
							txtPassword.setText(null);
							JOptionPane.showMessageDialog(null,"Username and Password do not match.","Login Error",JOptionPane.ERROR_MESSAGE);
						}
						
						// "s"  means the user s of type student.
						else if (result.equals("s")) {
							
							// checks the system status, it allows the student to access student menu if the system is started.
							if (SystemStatus.getInstance().isStarted()) {
								
								// remove all of the components of the frame
								frame.setVisible(false);
								frame.getContentPane().removeAll();
								
								// create an object of student with the entered ID and shows the studentMenu
								Student loggedInStudent = (Student)Register.getInstance().getRegisteredUser(ID);
								studentMenu(loggedInStudent);
							}
							
							// if the system is stopped, the following message is shown to the user.
							else {
								JOptionPane.showMessageDialog(null,"You are not allowed to use the system at the moment, for more info call the administrator.","System Status Error",JOptionPane.PLAIN_MESSAGE);
							}
						}
						
						// "i"  means the user is of type instructor.
						else if (result.equals("i")) {
							
							// checks the system status, it allows the instructor to access instructor menu if the system is started.
							if (SystemStatus.getInstance().isStarted()) {
								
								// remove all of the components of the frame
								frame.setVisible(false);
								frame.getContentPane().removeAll();
								
								// create an object of instructor with the entered ID and shows the instructorMenu
								Instructor loggedInInstrcutor = (Instructor)Register.getInstance().getRegisteredUser(ID);
								instructorMenu(loggedInInstrcutor);
							}
							
							// if the system is stopped, the following message is shown to the user.
							else {
								JOptionPane.showMessageDialog(null,"You are not allowed to use the system at the moment, for  more info call the administrator.","System Status Error",JOptionPane.PLAIN_MESSAGE);
							}
	
						}
						// else the out put is "a" which means the user is of type administrator.
						else {
							
							// remove all of the components of the frame
							frame.setVisible(false);
							frame.getContentPane().removeAll();
							// create an object of administrator with the entered ID and shows the adminMenu
							adminMenu(new Administrator(ID));
						}
					}
					
					// encryption exception
					catch(NoSuchAlgorithmException exception) {
						exception.getStackTrace();
					}
				}
			}
		});
		btnLogin.setBounds(303, 221, 117, 29);
		frame.getContentPane().add(btnLogin);
		
		// Reset button, the contents of both text fields will be erased when user clicks this button.
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUserName.setText(null);
				txtPassword.setText(null);
			}
		});
		btnReset.setBounds(174, 221, 117, 29);
		frame.getContentPane().add(btnReset);
		
		
		// Separator for login interface - separates the buttons on the bottom from labels/ text fields.
		JSeparator separator = new JSeparator();
		separator.setBounds(174, 200, 244, 12);
		frame.getContentPane().add(separator);
		
		// this label gives some information about the system to users.
		JLabel lblInfo = new JLabel("<html>* Students and Instructors might not be able to use the system at the moment.<p>\n* If this is the first time you are signing in, enter your date of birthday as a password. (YYYYMMDD) <p>* Students and instructors should change their passwords after their first login.</html>");
		lblInfo.setForeground(SystemColor.infoText);
		lblInfo.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblInfo.setBounds(58, 263, 536, 37);
		frame.getContentPane().add(lblInfo);
		// set this frame to be visible.
		frame.setVisible(true);
	}
	
	
	/* Provides an interface for administrator menu - showing available operations which can be chosen */
	/* ================================================================= */
	private void adminMenu(Administrator admin) {
	/* ================================================================= */
		// creating an object of administrator operation.
		AdministratorOperation operations = new AdministratorOperation(admin);

		// start the system button
		JButton btnStart = new JButton("Start the System");
		
		// Administrator can start the system by clicking on this button
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// calls the startSystem from adminOperations.
				operations.startSystem();
				// shows the following message to user.
				JOptionPane.showMessageDialog(null,"System is started now.","Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnStart.setBounds(195, 76, 187, 44);
		frame.getContentPane().add(btnStart);
		
		// stop the system button
		JButton btnStop = new JButton("Stop the System");
		
		// Administrator can stop the system by clicking on this button
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// calls the stopSystem from adminOperations.
				operations.stopSystem();
				// shows the following message to user.
				JOptionPane.showMessageDialog(null,"System is stopped now.","Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnStop.setBounds(195, 123, 187, 44);
		frame.getContentPane().add(btnStop);
		
		// read course file button
		JButton btnReadCourseFile = new JButton("Read Course File");
		btnReadCourseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				// calls readCourseFile interface
				readCourseFile(admin);
			}
		});
		btnReadCourseFile.setBounds(195, 170, 187, 44);
		frame.getContentPane().add(btnReadCourseFile);
		
		// log out button
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// confirms if the user wants to log out
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Do you want to log out",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					// go back to login interface
					login();
				}
			}
		});
		btnLogOut.setBounds(195, 310, 187, 44);
		frame.getContentPane().add(btnLogOut);
		
		// label on top of the page
		JLabel lblAdminMenu = new JLabel("                                         Administrator Menu");
		lblAdminMenu.setBackground(SystemColor.textHighlight);
		lblAdminMenu.setOpaque(true);
		lblAdminMenu.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblAdminMenu.setBounds(6, 6, 588, 62);
		frame.getContentPane().add(lblAdminMenu);
		
		// change password button
		JButton btnChangeMyPassword = new JButton("Change My Password");
		btnChangeMyPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				// calls changeMyPassword with admin's ID and "a" which indicates user is an administrator
				changeMyPassword(admin.getID(),"a");	
			}
		});
		btnChangeMyPassword.setBounds(195, 217, 187, 44);
		frame.getContentPane().add(btnChangeMyPassword);
		
		// change personal info button
		JButton btnChangePersonalInfo = new JButton("Change personal info\n");
		btnChangePersonalInfo.setBounds(195, 264, 187, 44);
		btnChangePersonalInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				changePersonalInfo(admin);
			}
		});
		frame.getContentPane().add(btnChangePersonalInfo);
		// set the frame to be visible
		frame.setVisible(true);
	}
	
	/* Provides an interface for administrator to change her/his info  */
	/* ================================================================= */
	private void changePersonalInfo(Administrator admin) {
	/* ================================================================= */
		// creates an object of administratorOperations
		AdministratorOperation operations = new AdministratorOperation(admin);
		
		// label used as a header with text "change personal info"
		JLabel lblHeader = new JLabel("                                          Change Personal Info");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setOpaque(true);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setBounds(6, 6, 588, 44);
		frame.getContentPane().add(lblHeader);
		
		// label for name
		JLabel name = new JLabel("Name:");
		name.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		name.setBounds(150, 169, 93, 16);
		frame.getContentPane().add(name);
		
		// textField for name
		JTextField txtName = new JTextField();
		txtName.setBounds(350, 160, 147, 36);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		// label for surname
		JLabel surname = new JLabel("Surname:");
		surname.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		surname.setBounds(150, 97, 93, 16);
		frame.getContentPane().add(surname);
		
		// textField for surname
		JTextField txtSurname = new JTextField();
		txtSurname.setColumns(10);
		txtSurname.setBounds(350, 88, 147, 36);
		frame.getContentPane().add(txtSurname);
		
		// label for date of birth
		JLabel lblDBirth = new JLabel("Date of birth (YYYYMMDD):");
		lblDBirth.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblDBirth.setBounds(150, 134, 216, 16);
		frame.getContentPane().add(lblDBirth);

		// textField for date of birth
		JTextField txtDBirth = new JTextField();
		txtDBirth.setColumns(10);
		txtDBirth.setBounds(350, 124, 147, 36);
		frame.getContentPane().add(txtDBirth);

		// submit button - when user click on this button, her/his info will be updated.
		JButton btnsubmit = new JButton("Submit");
		frame.getRootPane().setDefaultButton(btnsubmit);
		btnsubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String nameStr = txtName.getText();
					String surNameStr = txtSurname.getText();
					String DBirthStr = txtDBirth.getText();
					// call changePersonalInfo operation from adminOperations class
					operations.changePersonalInfo(nameStr, surNameStr, DBirthStr);
					JOptionPane.showMessageDialog(null,"Personal info updated.","Successful",JOptionPane.PLAIN_MESSAGE);
			}	
		});
		btnsubmit.setBounds(240, 210, 128, 36);
		frame.getContentPane().add(btnsubmit);

		// separates textFields/ labels from buttons
		JSeparator separator = new JSeparator();
		separator.setBounds(150, 195, 350, 12);
		frame.getContentPane().add(separator);
		
		// go back button
		JButton btnGoBack = new JButton("Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//goes back to adminMenu
					returnToMenu(admin,"a");
			}
		});
		btnGoBack.setBounds(240, 250, 128, 36);
		frame.getContentPane().add(btnGoBack);
		frame.setVisible(true);
	}
	
	/* Provides an interface for administrator to read course files */
	/* ================================================================= */
	private void readCourseFile(Administrator admin) {
	/* ================================================================= */
		AdministratorOperation operations = new AdministratorOperation(admin);
		
		JLabel lblEnterTheNames = new JLabel("Enter the names of the file(s) and press add.");
		lblEnterTheNames.setBounds(151, 85, 335, 22);
		frame.getContentPane().add(lblEnterTheNames);
		
		// textField for fileName
		JTextField txtFile1Name = new JTextField();
		txtFile1Name.setBounds(253, 119, 156, 33);
		frame.getContentPane().add(txtFile1Name);
		txtFile1Name.setColumns(10);
		
		// textField for fileName
		JTextField txtFile2Name = new JTextField();
		txtFile2Name.setColumns(10);
		txtFile2Name.setBounds(253, 164, 156, 33);
		frame.getContentPane().add(txtFile2Name);
		
		JLabel lblFile1Name = new JLabel("File Name");
		lblFile1Name.setBounds(170, 123, 87, 25);
		frame.getContentPane().add(lblFile1Name);
		
		JLabel lblFile2Name = new JLabel("File Name");
		lblFile2Name.setBounds(170, 164, 87, 25);
		frame.getContentPane().add(lblFile2Name);

		// separates buttons from textFields/ labels
		JSeparator separator = new JSeparator();
		separator.setBounds(170, 215, 239, 12);
		frame.getContentPane().add(separator);
		
		JButton btnReadCourseFile = new JButton("Read Course File(s)");
		frame.getRootPane().setDefaultButton(btnReadCourseFile);
		btnReadCourseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = "";
				String fileName2 = "";
				
				// if both of textFields are empty
				if (txtFile2Name.getText().equals("") && txtFile1Name.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Please fill out the textfield(s)","Read File Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					// if the first textField is empty and the second one is not.
					if (!txtFile2Name.getText().equals("") && txtFile1Name.getText().equals("")) {
						fileName = txtFile2Name.getText();
					}
					// if the second textField is empty and the first one is not.
					else if (txtFile2Name.getText().equals("") && !txtFile1Name.getText().equals("")) {
						fileName = txtFile1Name.getText();
					}
					// if none of the textFields is empty.
					else {
						fileName = txtFile1Name.getText();
						fileName2 = txtFile2Name.getText();
					}
					
					try {
						// adds the first course by calling readCourseFile
						operations.readCourseFile(fileName);
						
						// if the second file exists, adds the course
						if (!fileName2.equals("")) {
							operations.readCourseFile(fileName2);
							// message showing that both of courses has been added.
							JOptionPane.showMessageDialog(null,"Courses has been added successfully","Successful",JOptionPane.PLAIN_MESSAGE);
						}
						else {
							// message showing that one course has been added.
							JOptionPane.showMessageDialog(null,"Course has been added successfully","Successful",JOptionPane.PLAIN_MESSAGE);
						}
					}	
					catch(IOException IOexception) {
						// no file with this name is found
						JOptionPane.showMessageDialog(null,"No such file exists.","Invalid File Name",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnReadCourseFile.setBounds(220, 230, 143, 38);
		frame.getContentPane().add(btnReadCourseFile);
		
		// back button - goes back to adminMenu once clicked
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu(admin,"a");
			}
		});
		btnBack.setBounds(220, 273, 143, 38);
		frame.getContentPane().add(btnBack);
		
		// header label with text "Read Course Files"
		JLabel lblHeader = new JLabel("                                         Read Course Files");
		lblHeader.setOpaque(true);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBounds(6, 6, 588, 45);
		frame.getContentPane().add(lblHeader);
		// set this frame to be visible
		frame.setVisible(true);
	}
	
	/* Provides an interface for students to see the operations they are allowed to perform and choose one */
	/* ================================================================= */
	private void studentMenu(Student student) {
	/* ================================================================= */
		
		JLabel lblHeader = new JLabel("                                             Student Menu");
		lblHeader.setOpaque(true);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setForeground(SystemColor.activeCaptionText);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setBounds(6, 6, 588, 50);
		frame.getContentPane().add(lblHeader);
		
		// first operation student is allowed to perform : enroll in a course
		JButton btnEnrollInCourse = new JButton("Enroll in a course");
		btnEnrollInCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				// shows the enrollCourse interface
				enrollCourse(student);
			}
		});
		btnEnrollInCourse.setBounds(189, 94, 198, 42);
		frame.getContentPane().add(btnEnrollInCourse);
		
		// second operation student is allowed to perform : select notification status
		JButton btnSelectNotificationStatus = new JButton("Select notification status");
		btnSelectNotificationStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				// shows the chooseNotificationStatus interface
				chooseNotificationStatus(student);	
			}
		});
		btnSelectNotificationStatus.setBounds(189, 145, 198, 42);
		frame.getContentPane().add(btnSelectNotificationStatus);
		
		// third operation student is allowed to perform : print course record
		JButton btnPrintCourseRecord = new JButton("Print course record");
		btnPrintCourseRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				// shows the printCourseRecord interface
				printCourseRecord(student);
			}
		});
		btnPrintCourseRecord.setBounds(189, 197, 198, 42);
		frame.getContentPane().add(btnPrintCourseRecord);
		
		// 4th operation student is allowed to perform : change password
		JButton btnChangePass = new JButton("Change my password");
		 btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				// shows the changeMyPass interface with student's ID and "s" which indicates the user is a student
				changeMyPassword(student.getID(),"s");	
			}
		});
		btnChangePass.setBounds(189, 246, 198, 42);
		frame.getContentPane().add( btnChangePass);
		
		// logout button
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
		frame.setVisible(true);
	}
	
	/* Provides an interface for student to print the record of a course he/she is enrolled in */
	/* ================================================================= */
	private void printCourseRecord(Student student) {
	/* ================================================================= */
		// create an object of StudentOperations
		StudentOperation operations = new StudentOperation(student);
		
		// TextField that gets targetCourse's ID
		JTextField txtCourseID = new JTextField();
		txtCourseID.setBounds(269, 58, 151, 31);
		frame.getContentPane().add(txtCourseID);
		txtCourseID.setColumns(10);
		
		JLabel lblCourseID = new JLabel("Course ID:");
		lblCourseID.setBounds(190, 71, 93, 16);
		frame.getContentPane().add(lblCourseID);
		
		// scrollBar for textArea that represent course info
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(88, 171, 440, 171);
		frame.getContentPane().add(scrollPane);
		
		// textArea that shows course info
		JTextArea txtCourseInfo = new JTextArea();
		scrollPane.setViewportView(txtCourseInfo);
		txtCourseInfo.setEditable(false);
		
		// print record button
		JButton btnPrintRecord = new JButton("Print Record");
		frame.getRootPane().setDefaultButton(btnPrintRecord);
		btnPrintRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// gets the course with this ID from Register
				Course targetCourse = Register.getInstance().getRegisteredCourse(txtCourseID.getText().toUpperCase());
				// if no such course exits, shows a message indicating the situation.
				if (targetCourse == null) {
					JOptionPane.showMessageDialog(null,"Course ID is not valid.","Enter a valid course ID.",JOptionPane.ERROR_MESSAGE);
				}
				// if student is not enrolled in this course, they can not see the course record
				else if (!student.isEnrolledIn(targetCourse.getCourseID())) {
					JOptionPane.showMessageDialog(null,"You are not enrolled in this course.","Not able to print.",JOptionPane.ERROR_MESSAGE);
				}
				else {
					// calls printCourseRecord from StudentOperations and sets textField to the info
					String toBePrinted = operations.printCourseRecord(targetCourse);
					txtCourseInfo.setText(toBePrinted);
				}
			}
		});
		btnPrintRecord.setBounds(188, 93, 110, 38);
		frame.getContentPane().add(btnPrintRecord);

		// go back button - goes back to studentMenu
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu(student,"s");
			}
		});
		btnGoBack.setBounds(309, 90, 111, 40);
		frame.getContentPane().add(btnGoBack);
		
		// header label with the following text
		JLabel lblHeader = new JLabel("                                          Print Course Record");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setBounds(6, 6, 588, 40);
		frame.getContentPane().add(lblHeader);
		frame.setVisible(true);
	}
	
	/* Provides an interface for student to choose her/his Notification preference */
	/* ================================================================= */
	private void chooseNotificationStatus(Student student) {
	/* ================================================================= */
		// create an object of StudentOperation by passing student as input
		StudentOperation operations = new StudentOperation(student);
		
		JLabel lblChoose = new JLabel("Choose your Notification Preference:");
		lblChoose.setBounds(180, 78, 243, 16);
		frame.getContentPane().add(lblChoose);
		
		JButton btnEmail = new JButton(NotificationTypes.EMAIL.toString());
		// sets student notification preference to email.
		btnEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations.chooseNotificationPreference(NotificationTypes.EMAIL);
				JOptionPane.showMessageDialog(null,"Your notification preference is set to "+student.getNotificationType().toString(),"Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnEmail.setBounds(190, 113, 220, 36);
		frame.getContentPane().add(btnEmail);

		JButton btnCellphone = new JButton(NotificationTypes.CELLPHONE.toString());
		// sets student notification preference to cellphone.
		btnCellphone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations.chooseNotificationPreference(NotificationTypes.CELLPHONE);
				JOptionPane.showMessageDialog(null,"Your notification preference is set to "+student.getNotificationType().toString(),"Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnCellphone.setBounds(190, 161, 220, 36);
		frame.getContentPane().add(btnCellphone);
		
		
		JButton btnPost = new JButton(NotificationTypes.PIGEON_POST.toString());
		// sets student notification preference to pigeon post.
		btnPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations.chooseNotificationPreference(NotificationTypes.PIGEON_POST);
				JOptionPane.showMessageDialog(null,"Your notification preference is set to "+student.getNotificationType().toString(),"Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnPost.setBounds(190, 209, 220, 36);
		frame.getContentPane().add(btnPost);
		
		// go back button
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu(student,"s");
			}
		});
		btnGoBack.setBounds(190, 257, 220, 36);
		frame.getContentPane().add(btnGoBack);
		
		// header label with text "Notification Status"
		JLabel lblHeader = new JLabel("                                           Notification Status");
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBounds(6, 6, 588, 41);
		frame.getContentPane().add(lblHeader);
		
		frame.setVisible(true);
	}
	
	/* Provides an interface for student to enroll in a course */
	/* ================================================================= */
	private void enrollCourse(Student student) {
	/* ================================================================= */
		// create an object of StudentOperations by passing student to it.
		StudentOperation operations = new StudentOperation(student);
		
		JLabel lblEnterTheCourse = new JLabel("Enter the course ID:");
		lblEnterTheCourse.setBounds(154, 82, 192, 25);
		frame.getContentPane().add(lblEnterTheCourse);
		
		// textField that will contain the ID of targetCourse
		JTextField txtCourseID = new JTextField();
		txtCourseID.setBounds(290, 76, 146, 37);
		frame.getContentPane().add(txtCourseID);
		
		// separates labels/ textFields from buttons
		JSeparator separator = new JSeparator();
		separator.setBounds(153, 122, 283, 12);
		frame.getContentPane().add(separator);
		
		JButton btnEnroll = new JButton("Enroll");
		// set enroll as the default button of frame so user can enroll by pressing enter
		frame.getRootPane().setDefaultButton(btnEnroll);
		btnEnroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the course with this ID from Register
				Course targetCourse = Register.getInstance().getRegisteredCourse(txtCourseID.getText().toUpperCase());
				
				// if no course with this ID exists, shows the following message.
				if (targetCourse == null) {
					JOptionPane.showMessageDialog(null,"Course ID is not valid.","Error",JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					
					// calls enroll from StudentOperation - the return value can be : "notAllowed" - "alreadyEnrolled" - " successful".
					String result = operations.enroll(targetCourse);
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
		
		// go back button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu(student,"s");
			}
		});
		btnBack.setBounds(230, 182, 130, 37);
		frame.getContentPane().add(btnBack);
		
		// header label with text "Enroll in a course"
		JLabel lblHeader = new JLabel("                                        Enroll in a Course");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setBounds(6, 6, 588, 43);
		frame.getContentPane().add(lblHeader);
		frame.setVisible(true);
	}
	
	/* Provides an interface for instructors to see the operations they are allowed to perform and choose one */
	/* ================================================================= */
	private void instructorMenu(Instructor instructor) {
	/* ================================================================= */
		// header label with text "Instructor Menu"
		JLabel lblHeader = new JLabel("                                          Instructor Menu");
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBounds(6, 6, 588, 53);
		frame.getContentPane().add(lblHeader);
		
		// first operation instructor is allowed to perform : add mark for a student
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
		
		// second operation instructor is allowed to perform : modify mark for a student
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
		
		// third operation instructor is allowed to perform : calculate final grade for a student
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
		
		// 4th operation instructor is allowed to perform : change their password
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
		
		// 5th operation instructor is allowed to perform : print record of a class
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
		
		// login button
		JButton btnLogout = new JButton("Log Out");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Do you want to log out",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					login();
				}
			}
		});
		btnLogout.setBounds(175, 307, 224, 45);
		frame.getContentPane().add(btnLogout);
		frame.setVisible(true);
	}

	/* Provides an interface for instructor to print a class record */
	/* ================================================================= */
	private void printClassRecord(Instructor instructor) {
	/* ================================================================= */	
		// create an object of InstructorOperation by passing instructor object to it.
		InstructorOperation operations = new InstructorOperation(instructor);
		
		// textField that will contain the ID of the targetCourse
		JTextField txtCourseID = new JTextField();
		txtCourseID.setBounds(267, 87, 151, 31);
		frame.getContentPane().add(txtCourseID);
		txtCourseID.setColumns(10);
		
		// label for txtCourseID
		JLabel lblCourseID = new JLabel("Course ID:");
		lblCourseID.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCourseID.setBounds(188, 93, 93, 16);
		frame.getContentPane().add(lblCourseID);
		
		// header label with the following text
		JLabel lblHeader = new JLabel("                                         Print Class Record");
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblHeader.setBounds(6, 6, 588, 62);
		frame.getContentPane().add(lblHeader);
		
		// scrollBar for txtArea 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(88, 171, 440, 171);
		frame.getContentPane().add(scrollPane);
		
		// txtArea that will contain the record of a course
		JTextArea txtCourseInfo = new JTextArea();
		scrollPane.setViewportView(txtCourseInfo);
		txtCourseInfo.setEditable(false);
		
		// print record button - when the user clicks on this button, the record will be shown in the text area.
		JButton btnPrintRecord = new JButton("Print Record");
		frame.getRootPane().setDefaultButton(btnPrintRecord);
		btnPrintRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the course with this ID from Register
				Course targetCourse = Register.getInstance().getRegisteredCourse(txtCourseID.getText().toUpperCase());
				// if no course with this ID exists, the following message will be shown.
				if (targetCourse == null) {
					JOptionPane.showMessageDialog(null,"Course ID is not valid.","Enter a valid course ID.",JOptionPane.ERROR_MESSAGE);
				}
				// if instructor is not tutor of this course, the following message will be shown.
				else if (!instructor.isTutorOf(targetCourse.getCourseID())) {
					JOptionPane.showMessageDialog(null,"You are not listed as a tutor for this course.","Not able to print.",JOptionPane.ERROR_MESSAGE);
				}
				else {
					// gets the info by calling printCourseRecord from InstructorOperations class
					String toBePrinted = operations.printCourseRecord(targetCourse);
					txtCourseInfo.setText(toBePrinted);
				}
			}
		});
		btnPrintRecord.setBounds(188, 121, 110, 38);
		frame.getContentPane().add(btnPrintRecord);

		// go back button
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu(instructor,"i");
			}
		});
		btnGoBack.setBounds(307, 120, 111, 40);
		frame.getContentPane().add(btnGoBack);
		frame.setVisible(true);
	}
	
	/* Provides an interface for instructor to add grade for a student */
	/* ================================================================= */
	private void addGrade (Instructor instructor) {
	/* ================================================================= */
		// create an object of InstructorOperation by passing instructor object to it.
		InstructorOperation operations = new InstructorOperation(instructor);
		
		// header label with the following text
		JLabel lblHeader = new JLabel("                                                 Add Grade");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setOpaque(true);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setBounds(6, 6, 588, 44);
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
					
					// if instructor is not tutor of this course, the following message will be shown.
					else if (!instructor.isTutorOf(targetCourse.getCourseID())) {
						JOptionPane.showMessageDialog(null,"You are not listed as an instructor of the course.","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
					}
					
					// if the grade is greater than 100 or less than zero, the following message will be shown.
					else if (Double.parseDouble(txtGrade.getText())<0 || Double.parseDouble(txtGrade.getText())>100) {
						JOptionPane.showMessageDialog(null,"Grades have to between between 0 and 100.","Grade not valid.",JOptionPane.ERROR_MESSAGE);
					}
		
					else {
						// calls addGrade from InstructorOperation class - result will be true if grade has been added successfully, it will be false if entity entered is not valid.
						Boolean result = operations.addGrade(targetCourse,targetStudent, txtEntity.getText().toUpperCase(), Double.parseDouble(txtGrade.getText()));
						// entity is not valid
						if (result == false) {
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
				returnToMenu(instructor,"i");
			}
		});
		
		btnGoBack.setBounds(227, 298, 128, 36);
		frame.getContentPane().add(btnGoBack);

		frame.setVisible(true);
		
	}
	
	/* ================================================================= */
	private void calculateStudentFinalGrade(Instructor instructor) {
	/* ================================================================= */	
		// create an object of InstructorOperation by passing instructor object to it.
		InstructorOperation operations = new InstructorOperation(instructor);
		
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
				returnToMenu(instructor,"i");
			}
		});
		btnBack.setBounds(220, 273, 143, 38);
		frame.getContentPane().add(btnBack);
		
		// header label
		JLabel lblHeader = new JLabel("                                         Calculate Final Grade");
		lblHeader.setOpaque(true);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBounds(6, 6, 588, 45);
		frame.getContentPane().add(lblHeader);
		
		frame.setVisible(true);
	
	}
	
	/* This interface allows Instructors, Students, and Administrator to change their password */
	/* ================================================================= */
	private void changeMyPassword(String ID,String userType) {
	/* ================================================================= */	
		// label header
		JLabel lblHeader = new JLabel("                                        Change Password");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setBounds(6, 6, 588, 43);
		frame.getContentPane().add(lblHeader);
		
		// textField that will contain the new password entered by the user
		JTextField txtNewPassword = new JTextField();
		txtNewPassword.setBounds(290, 76, 146, 37);
		frame.getContentPane().add(txtNewPassword);
		
		// label for txtNewPassword
		JLabel lblNewPassword = new JLabel("New password:");
		lblNewPassword.setBounds(154, 82, 192, 25);
		frame.getContentPane().add(lblNewPassword);
		
		// separates buttons from labels/ textFields
		JSeparator separator = new JSeparator();
		separator.setBounds(153, 122, 283, 12);
		frame.getContentPane().add(separator);
		
		// changePass button - is set as the default button of the frame
		JButton btnChangePass = new JButton("Change password");
		frame.getRootPane().setDefaultButton( btnChangePass);
		 btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if textField is empty, the following message is shown
				if (txtNewPassword.equals(null)) {
					JOptionPane.showMessageDialog(null,"Please enter your new password.","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					// add new user (this is the same changing user's password)
					LoginServer.getInstance().addUser(userType, ID, txtNewPassword.getText());
					JOptionPane.showMessageDialog(null,"Password changed successfully. ","Successful.",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		 btnChangePass.setBounds(230, 143, 130, 37);
		frame.getContentPane().add(btnChangePass);
		
		// go back button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				frame.getContentPane().removeAll();
				
				// shows instructor menu if userType is "i"
				if (userType.equals("i")) {
					instructorMenu((Instructor)Register.getInstance().getRegisteredUser(ID));
				}
				
				// shows administrator menu if userType is "a"
				else if (userType.equals("a")) {
					adminMenu(new Administrator(ID));
				}
				
				// shows student menu if userType is "a"
				else {
					studentMenu((Student)Register.getInstance().getRegisteredUser(ID));
				}
			}
		});
		btnBack.setBounds(230, 182, 130, 37);
		frame.getContentPane().add(btnBack);
		
		frame.setVisible(true);
	}
	
	private void returnToMenu(SystemUser user, String userType) {
		frame.setVisible(false);
		frame.getContentPane().removeAll();
		if (userType.equals("i")) {
			instructorMenu((Instructor)user);
		}
		else if (userType.equals("a")) {
			adminMenu((Administrator)user);
		}
		else {
			studentMenu((Student)user);
		}
	}
}


