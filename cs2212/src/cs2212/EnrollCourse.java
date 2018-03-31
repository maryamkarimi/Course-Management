package cs2212;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EnrollCourse {

	private JFrame frame;
	private JTextField textField;
	private Student student;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnrollCourse window = new EnrollCourse();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EnrollCourse() {
		initialize();
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		JLabel lblEnterTheCourse = new JLabel("Enter the course ID:");
		lblEnterTheCourse.setBounds(70, 80, 192, 25);
		frame.getContentPane().add(lblEnterTheCourse);
		
		JButton btnEnroll = new JButton("Enroll");
		btnEnroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!Register.getInstance().checkIfCourseHasAlreadyBeenCreated(textField.getText().toUpperCase())) {
					JOptionPane.showMessageDialog(null,"Course ID is not valid.","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					Course targetCourse = Register.getInstance().getRegisteredCourse(textField.getText().toUpperCase());
					if (!student.isAllowedToEnrollIn(targetCourse)) {
						JOptionPane.showMessageDialog(null,"You are not allowed to enroll in "+targetCourse.getCourseName(),"Error",JOptionPane.ERROR_MESSAGE);
					}

					
					else if (student.isEnrolledIn(targetCourse.getCourseID())){
						JOptionPane.showMessageDialog(null,"You are already enrolled in "+targetCourse.getCourseName(),"Error",JOptionPane.ERROR_MESSAGE);
					}
					
					else {
						student.getCoursesEnrolled().add(targetCourse);
						targetCourse.getStudentsEnrolledList().add(student);
						JOptionPane.showMessageDialog(null,"You are now enrolled in "+targetCourse.getCourseName(),"Successful",JOptionPane.PLAIN_MESSAGE);
					}
				}
				
			}
		});
		btnEnroll.setBounds(145, 139, 117, 29);
		frame.getContentPane().add(btnEnroll);
		
		textField = new JTextField();
		textField.setBounds(209, 79, 130, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(69, 120, 270, 12);
		frame.getContentPane().add(separator);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				StudentMenu menu = new StudentMenu();
				menu.setStudent(student);
			}
		});
		btnBack.setBounds(145, 170, 117, 29);
		frame.getContentPane().add(btnBack);
		frame.setVisible(true);
	}

}
