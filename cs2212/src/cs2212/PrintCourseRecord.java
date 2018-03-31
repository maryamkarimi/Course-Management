package cs2212;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.Font;

public class PrintCourseRecord {

	private JFrame frame;
	private JTextField textField;
	private Student student;

	/**
	 * Create the application.
	 */
	public PrintCourseRecord(Student student) {
		this.student = student;
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
		
		textField = new JTextField();
		textField.setBounds(232, 61, 130, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblCourseId = new JLabel("Course ID:");
		lblCourseId.setBounds(153, 64, 93, 16);
		frame.getContentPane().add(lblCourseId);
		
		JLabel lblCourseName = new JLabel("Course Name:");
		lblCourseName.setVisible(false);
		lblCourseName.setBounds(56, 135, 388, 21);
		frame.getContentPane().add(lblCourseName);
		
		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setVisible(false);
		lblSemester.setBounds(56, 159, 388, 21);
		frame.getContentPane().add(lblSemester);
		
		JLabel lblInstructors = new JLabel("Instructors:");
		lblInstructors.setHorizontalAlignment(SwingConstants.LEFT);
		lblInstructors.setVisible(false);
		lblInstructors.setBounds(56, 183, 388, 21);
		frame.getContentPane().add(lblInstructors);
		frame.setVisible(true);
		
		
		JLabel lblEvalEntity = new JLabel("Evaluation Entity: ");
		lblEvalEntity.setBounds(56, 207, 375, 16);
		frame.getContentPane().add(lblEvalEntity);
		lblEvalEntity.setVisible(false);
		
		JLabel lblNewLabel = new JLabel("Grades:");
		lblNewLabel.setBounds(56, 228, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setVisible(false);
		
		JLabel gradeslbl = new JLabel("");
		gradeslbl.setVerticalAlignment(SwingConstants.TOP);
		gradeslbl.setBounds(56, 249, 364, 89);
		frame.getContentPane().add(gradeslbl);
		gradeslbl.setVisible(false);
		
		JButton btnNewButton = new JButton("Print Record");
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
		btnNewButton.setBounds(139, 94, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				StudentMenu menu = new StudentMenu();
				menu.setStudent(student);
			}
		});
		btnGoBack.setBounds(261, 94, 117, 29);
		frame.getContentPane().add(btnGoBack);
		
		JLabel lblNewLabel_1 = new JLabel("                                Print Course Record");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setBackground(SystemColor.textHighlight);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBounds(6, 6, 488, 40);
		frame.getContentPane().add(lblNewLabel_1);

	}
}
