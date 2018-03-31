package cs2212;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class StudentMenu {

	private JFrame frame;
	private Student student;
	/**
	 * Create the application.
	 */
	public StudentMenu() {
		initialize();
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				StudentMenu window = new StudentMenu();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
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
		frame.setVisible(true);
		
		JButton btnNewButton = new JButton("Enroll in a course");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				EnrollCourse enrollCourse = new EnrollCourse();
				enrollCourse.setStudent(student);
			}
		});
		btnNewButton.setBounds(151, 41, 179, 37);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSelectNotificationStatus = new JButton("Select notification status");
		btnSelectNotificationStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				notifStatus notifStat = new notifStatus();
				notifStat.setStudent(student);
				
			}
		});
		btnSelectNotificationStatus.setBounds(151, 92, 179, 37);
		frame.getContentPane().add(btnSelectNotificationStatus);
		
		JButton btnPrintCourseRecord = new JButton("Print course record");
		btnPrintCourseRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				PrintCourseRecord printCourseRecord = new PrintCourseRecord();
				printCourseRecord.setStudent(student);
			}
		});
		btnPrintCourseRecord.setBounds(151, 144, 179, 37);
		frame.getContentPane().add(btnPrintCourseRecord);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Aministrator Menu",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.dispose();
					LoginSystem.main(null);
				}
			}
		});
		btnLogOut.setBounds(151, 193, 179, 37);
		frame.getContentPane().add(btnLogOut);
		frame.setVisible(true);
	}
}
