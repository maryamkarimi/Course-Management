package cs2212;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrintCourseRecord {

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
					PrintCourseRecord window = new PrintCourseRecord();
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
	public PrintCourseRecord() {
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		textField = new JTextField();
		textField.setBounds(197, 20, 130, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblCourseId = new JLabel("Course ID:");
		lblCourseId.setBounds(118, 23, 93, 16);
		frame.getContentPane().add(lblCourseId);
		
		JLabel lblCourseInfo = new JLabel("\n\n\n\n\n\n\n\n\n\n\n");
		lblCourseInfo.setBounds(56, 83, 350, 197);
		frame.getContentPane().add(lblCourseInfo);
		
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
					lblCourseInfo.setText("Course ID: " +targetCourse.getCourseID()+ "\nCourse Name: "+targetCourse.getCourseName());
				}
			}
		});
		btnNewButton.setBounds(104, 53, 117, 29);
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
		btnGoBack.setBounds(226, 53, 117, 29);
		frame.getContentPane().add(btnGoBack);
		frame.setVisible(true);
	}
}
