package cs2212;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Font;

public class EnrollCourse {

	private JFrame frame;
	private JTextField textField;
	private Student student;

	/**
	 * Create the application.
	 */
	public EnrollCourse(Student student) {
		this.student = student;
		initialize();
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
		lblEnterTheCourse.setBounds(109, 82, 192, 25);
		frame.getContentPane().add(lblEnterTheCourse);
		
		JButton btnEnroll = new JButton("Enroll");
		frame.getRootPane().setDefaultButton(btnEnroll);
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
		btnEnroll.setBounds(184, 141, 117, 29);
		frame.getContentPane().add(btnEnroll);
		
		textField = new JTextField();
		textField.setBounds(248, 81, 130, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(108, 122, 270, 12);
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
		btnBack.setBounds(184, 172, 117, 29);
		frame.getContentPane().add(btnBack);
		
		JLabel lblNewLabel = new JLabel("                                Enroll in a Course");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(6, 6, 488, 43);
		frame.getContentPane().add(lblNewLabel);
		frame.setVisible(true);
	}
}
