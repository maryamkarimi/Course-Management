package systemUserInterfaces;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import registrar.Register;
import systemServers.LoginServer;
import systemStatus.SystemStatus;
import systemUsers.Administrator;
import systemUsers.Instructor;
import systemUsers.Student;

public class LoginPage {

	private JFrame frame;

	/* Launch the application */
	/* ================================================================= */
	public static void main(String[] args) {
	/* ================================================================= */
		// please note: you can sign in with ID:5433 and Password:98765 as an administrator
		// all other IDs can be found in note_1.txt and note_2.txt / passwords are set to user's date of birth initially
		new LoginPage();
	}
	
	/* Create the application.*/
	/* ================================================================= */
	public LoginPage() {
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
						
						//passes the ID and password to server and gets either "dontExist","dontMatch","i","s",or "a" as an output.
						String result = server.isValid(ID, password);
						
						// "dontExist" represents the case that no user with such ID exists.
						if (result.equals("dontExist")) {
							
							// reset both of the text fields and shows a message to user indicating the situation.
							txtUserName.setText(null);
							txtPassword.setText(null);
							JOptionPane.showMessageDialog(null,"This username is not recongnized, try again!","Login Error",JOptionPane.ERROR_MESSAGE);
						}
						
						// "dontMatch" represents the case that the ID and password do not match.
						else if(result.equals("dontMatch")) {
							
							// reset the password text field and shows a message to user indicating the situation.
							txtPassword.setText(null);
							JOptionPane.showMessageDialog(null,"Username and Password do not match.","Login Error",JOptionPane.ERROR_MESSAGE);
						}
						
						// "s"  means the user s of type student.
						else if (result.equals("s")) {
							
							// checks the system status, it allows the student to access student menu if the system is started.
							if (SystemStatus.getInstance().isStarted()) {
								// create an object of student with the entered ID and shows the studentMenu
								Student loggedInStudent = (Student)Register.getInstance().getRegisteredUser(ID);
								frame.setVisible(false);
								frame.dispose();
								new StudentPages(loggedInStudent);
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
								// create an object of instructor with the entered ID and shows the instructorMenu
								Instructor loggedInInstrcutor = (Instructor)Register.getInstance().getRegisteredUser(ID);
								frame.setVisible(false);
								frame.dispose();
								new InstructorPages(loggedInInstrcutor);
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
							frame.dispose();
							new AdministratorPages(new Administrator(ID));
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
}


