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

import customDataTypes.NotificationTypes;
import offerings.Course;
import offerings.ICourse;
import registrar.Register;
import systemUserOperations.StudentOperations;
import systemUsers.Student;

public class StudentPages extends SystemUserPages {

	private Student student;
	private StudentOperations operations;

	public StudentPages(Student student) {
		this.student = student;
		this.operations = new StudentOperations(student);
		studentMenu();
	}
	
	/* Provides an interface for students to see the operations they are allowed to perform and choose one */
	/* ================================================================= */
	private void studentMenu() {
	/* ================================================================= */
		super.initialize();
		JLabel lblHeader = new JLabel("                                             Student Menu");
		lblHeader.setOpaque(true);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setForeground(SystemColor.activeCaptionText);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setBounds(6, 6, 588, 55);
		frame.getContentPane().add(lblHeader);
		
		// first operation student is allowed to perform : enroll in a course
		JButton btnEnrollInCourse = new JButton("Enroll in a course");
		btnEnrollInCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				// shows the enrollCourse interface
				enrollCourse();
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
				chooseNotificationStatus();	
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
				printCourseRecord();
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
				changeMyPassword(student,"s");	
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
					new LoginPage();
				}
			}
		});
		btnLogOut.setBounds(189, 297, 198, 42);
		frame.getContentPane().add(btnLogOut);
		frame.setVisible(true);
	}
	
	/* Provides an interface for student to print the record of a course he/she is enrolled in */
	/* ================================================================= */
	private void printCourseRecord() {
	/* ================================================================= */
		// comboBox that contains list of courses student is enrolled in
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(195, 66, 277, 40);
		for (ICourse course: student.getCoursesEnrolled()) {
			comboBox.addItem(course.getCourseID()+":"+course.getCourseName());
		}
		comboBox.setEditable(false);
		comboBox.setPrototypeDisplayValue("Choose a course here");
		frame.getContentPane().add(comboBox);
		
		JLabel lblCourseID = new JLabel("Course ID:");
		lblCourseID.setBounds(111, 73, 72, 25);
		frame.getContentPane().add(lblCourseID);
		
		// scrollBar for textArea that represent course info
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(83, 108, 440, 171);
		frame.getContentPane().add(scrollPane);
		
		// textArea that shows course info
		JTextArea txtCourseInfo = new JTextArea();
		scrollPane.setViewportView(txtCourseInfo);
		txtCourseInfo.setEditable(false);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Course targetCourse = Register.getInstance().getRegisteredCourse(student.getCoursesEnrolled().get(comboBox.getSelectedIndex()).getCourseID());
				String toBePrinted = operations.printCourseRecord(targetCourse);
				txtCourseInfo.setText(toBePrinted);
			}
		});

		// go back button - goes back to studentMenu
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu();
			}
		});
		btnGoBack.setBounds(242, 291, 110, 39);
		frame.getContentPane().add(btnGoBack);
		
		// header label with the following text
		JLabel lblHeader = new JLabel("                                          Print Course Record");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setBounds(6, 6, 588, 55);
		frame.getContentPane().add(lblHeader);
		frame.setVisible(true);
	}
	
	/* Provides an interface for student to choose her/his Notification preference */
	/* ================================================================= */
	private void chooseNotificationStatus() {
	/* ================================================================= */
		// create an object of StudentOperation by passing student as input
		StudentOperations operations = new StudentOperations(student);
		
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
		// sets student notification preference to cell phone.
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
				returnToMenu();
			}
		});
		btnGoBack.setBounds(190, 257, 220, 36);
		frame.getContentPane().add(btnGoBack);
		
		// header label with text "Notification Status"
		JLabel lblHeader = new JLabel("                                           Notification Status");
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBounds(6, 6, 588, 55);
		frame.getContentPane().add(lblHeader);
		
		frame.setVisible(true);
	}
	
	/* Provides an interface for student to enroll in a course */
	/* ================================================================= */
	private void enrollCourse() {
	/* ================================================================= */
		// create an object of StudentOperations by passing student to it.
		StudentOperations operations = new StudentOperations(student);
		
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
				returnToMenu();
			}
		});
		btnBack.setBounds(230, 182, 130, 37);
		frame.getContentPane().add(btnBack);
		
		// header label with text "Enroll in a course"
		JLabel lblHeader = new JLabel("                                        Enroll in a Course");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setBounds(6, 6, 588, 55);
		frame.getContentPane().add(lblHeader);
		frame.setVisible(true);
	}
	
	private void returnToMenu() {
		frame.setVisible(false);
		frame.getContentPane().removeAll();
		studentMenu();
	}
	

}
