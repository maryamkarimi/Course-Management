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
	
	
	/* Shows the administrator menu */
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
	
	/* Administrator can change her info here */
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
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					
					// goes back to adminMenu
					adminMenu(admin);
			}
		});
		btnGoBack.setBounds(240, 250, 128, 36);
		frame.getContentPane().add(btnGoBack);
		frame.setVisible(true);
	}
	
	/* ================================================================= */
	private void readCourseFile(Administrator admin) {
	/* ================================================================= */
		AdministratorOperation operations = new AdministratorOperation(admin);
		
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
					JOptionPane.showMessageDialog(null,"Please fill out the textfield(s)","Read File Error",JOptionPane.ERROR_MESSAGE);
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
						operations.readCourseFile(fileName);
						if (!fileName2.equals("")) {
							operations.readCourseFile(fileName2);
							JOptionPane.showMessageDialog(null,"Courses has been added successfully","Successful",JOptionPane.PLAIN_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null,"Course has been added successfully","Successful",JOptionPane.PLAIN_MESSAGE);
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
				adminMenu(admin);
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
	
	/* ================================================================= */
	private void studentMenu(Student student) {
	/* ================================================================= */
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
	
	/* ================================================================= */
	private void printCourseRecord(Student student) {
	/* ================================================================= */

		StudentOperation operations = new StudentOperation(student);
		
		JTextField textField = new JTextField();
		textField.setBounds(269, 58, 151, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblCourseId = new JLabel("Course ID:");
		lblCourseId.setBounds(190, 71, 93, 16);
		frame.getContentPane().add(lblCourseId);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(88, 171, 440, 171);
		frame.getContentPane().add(scrollPane);

		
		JTextArea txtCourseInfo = new JTextArea();
		scrollPane.setViewportView(txtCourseInfo);
		txtCourseInfo.setEditable(false);
		
		
		JButton btnNewButton = new JButton("Print Record");
		frame.getRootPane().setDefaultButton(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Course targetCourse = Register.getInstance().getRegisteredCourse(textField.getText().toUpperCase());
				if (targetCourse == null) {
					JOptionPane.showMessageDialog(null,"Course ID is not valid.","Enter a valid course ID.",JOptionPane.ERROR_MESSAGE);
				}
				else if (!student.isEnrolledIn(targetCourse.getCourseID())) {
					JOptionPane.showMessageDialog(null,"You are not enrolled in this course.","Not able to print.",JOptionPane.ERROR_MESSAGE);
				}
				else {
				String toBePrinted = operations.printCourseRecord(targetCourse);
				txtCourseInfo.setText(toBePrinted);
				}
			}
		});
		btnNewButton.setBounds(188, 93, 110, 38);
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
	
	/* ================================================================= */
	private void chooseNotificationStatus(Student student) {
	/* ================================================================= */
		StudentOperation operations = new StudentOperation(student);
		JLabel lblChoose = new JLabel("Choose your Notification Preference:");
		lblChoose.setBounds(180, 78, 243, 16);
		frame.getContentPane().add(lblChoose);
		
		JButton btn1 = new JButton("");
		btn1.setText(NotificationTypes.EMAIL.toString());
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operations.chooseNotificationPreference(NotificationTypes.EMAIL);
				JOptionPane.showMessageDialog(null,"Your notification preference is set to "+student.getNotificationType().toString(),"Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btn1.setBounds(190, 113, 220, 36);
		frame.getContentPane().add(btn1);

		JButton btn2 = new JButton("");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
	
	
	/* ================================================================= */
	private void enrollCourse(Student student) {
	/* ================================================================= */
		StudentOperation operations = new StudentOperation(student);
		
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
	
	/* ================================================================= */
	private void instructorMenu(Instructor instructor) {
	/* ================================================================= */
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

	/* ================================================================= */
	private void printClassRecord(Instructor instructor) {
	/* ================================================================= */	
		InstructorOperation operations = new InstructorOperation(instructor);
		
		JTextField textField = new JTextField();
		textField.setBounds(267, 87, 151, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblCourseId = new JLabel("Course ID:");
		lblCourseId.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCourseId.setBounds(188, 93, 93, 16);
		frame.getContentPane().add(lblCourseId);
		
		
		JLabel lblNewLabel = new JLabel("                                         Print Class Record");
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setBounds(6, 6, 588, 62);
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(88, 171, 440, 171);
		frame.getContentPane().add(scrollPane);
		
		JTextArea txtCourseInfo = new JTextArea();
		scrollPane.setViewportView(txtCourseInfo);
		txtCourseInfo.setEditable(false);
		
		
		JButton btnNewButton = new JButton("Print Record");
		frame.getRootPane().setDefaultButton(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Course targetCourse = Register.getInstance().getRegisteredCourse(textField.getText().toUpperCase());
				if (targetCourse == null) {
					JOptionPane.showMessageDialog(null,"Course ID is not valid.","Enter a valid course ID.",JOptionPane.ERROR_MESSAGE);
				}
				else if (!instructor.isTutorOf(targetCourse.getCourseID())) {
					JOptionPane.showMessageDialog(null,"You are not listed as a tutor for this course.","Not able to print.",JOptionPane.ERROR_MESSAGE);
				}
				else {
				String toBePrinted = operations.printCourseRecord(targetCourse);
				txtCourseInfo.setText(toBePrinted);
				}
			}
		});
		btnNewButton.setBounds(188, 121, 110, 38);
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				instructorMenu(instructor);
			}
		});
		btnGoBack.setBounds(307, 120, 111, 40);
		frame.getContentPane().add(btnGoBack);

		
	}
	
	/* ================================================================= */
	private void addGrade (Instructor instructor) {
	/* ================================================================= */
		InstructorOperation operations = new InstructorOperation(instructor);
		
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
					else if (!instructor.isTutorOf(targetCourse.getCourseID())) {
						JOptionPane.showMessageDialog(null,"You are not listed as an instructor of the course.","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
					}
					else if (Double.parseDouble(txtGrade.getText())<0 || Double.parseDouble(txtGrade.getText())>100) {
						JOptionPane.showMessageDialog(null,"Grades have to between between 0 and 100.","Grade not valid.",JOptionPane.ERROR_MESSAGE);
					}
					else {
						Boolean result = operations.addGrade(targetCourse,targetStudent, txtEntity.getText().toUpperCase(), Double.parseDouble(txtGrade.getText()));
						if (result == false) {
							JOptionPane.showMessageDialog(null,"Entity is not valid.","Enter valid Entity name.",JOptionPane.ERROR_MESSAGE);
						}
						else {
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
	
	/* ================================================================= */
	private void calculateStudentFinalGrade(Instructor instructor) {
	/* ================================================================= */	
		InstructorOperation operations = new InstructorOperation(instructor);
		
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
					JOptionPane.showMessageDialog(null,"Please fill out the textfield(es)","Error",JOptionPane.ERROR_MESSAGE);
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
						else if (!instructor.isTutorOf(targetCourse.getCourseID())) {
							JOptionPane.showMessageDialog(null,"You are not listed as an instructor of the course.","Enter valid Course ID.",JOptionPane.ERROR_MESSAGE);
						}
						else {
							try{
								Double grade = operations.calculateFinalGrade(targetCourse, targetStudent);
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
	
	/* ================================================================= */
	private void changeMyPassword(String ID,String userType) {
	/* ================================================================= */	
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
					adminMenu(new Administrator(ID));
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


