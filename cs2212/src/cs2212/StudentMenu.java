package cs2212;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Font;

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
		
		JLabel lblHeader = new JLabel("");
		lblHeader.setOpaque(true);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setForeground(SystemColor.activeCaptionText);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setBounds(6, 6, 488, 43);
		frame.getContentPane().add(lblHeader);
		frame.setVisible(true);
		lblHeader.setText("                                     Student Menu");
		
		JButton btnNewButton = new JButton("Enroll in a course");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new EnrollCourse(student);
			}
		});
		btnNewButton.setBounds(161, 72, 179, 37);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSelectNotificationStatus = new JButton("Select notification status");
		btnSelectNotificationStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new notifStatus(student);
				
			}
		});
		btnSelectNotificationStatus.setBounds(161, 123, 179, 37);
		frame.getContentPane().add(btnSelectNotificationStatus);
		
		JButton btnPrintCourseRecord = new JButton("Print course record");
		btnPrintCourseRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new PrintCourseRecord(student);
			}
		});
		btnPrintCourseRecord.setBounds(161, 175, 179, 37);
		frame.getContentPane().add(btnPrintCourseRecord);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Do you want to log out",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.dispose();
					LoginSystem.main(null);
				}
			}
		});
		btnLogOut.setBounds(161, 224, 179, 37);
		frame.getContentPane().add(btnLogOut);
		
	}
}
