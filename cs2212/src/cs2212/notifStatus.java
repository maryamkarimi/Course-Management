package cs2212;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Choice;

public class notifStatus {

	private JFrame frame;
	private Student student;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					notifStatus window = new notifStatus();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	/**
	 * Create the application.
	 */
	public notifStatus() {
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
		
		
		JLabel lblChoose = new JLabel("Choose your Notification Preference:");
		lblChoose.setBounds(83, 24, 243, 16);
		frame.getContentPane().add(lblChoose);
		
		JButton btn1 = new JButton("");
		btn1.setText(NotificationTypes.EMAIL.toString());
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				student.setNotificationType(NotificationTypes.EMAIL);
				JOptionPane.showMessageDialog(null,"Your notification preference is set to "+student.getNotificationType().toString(),"Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btn1.setBounds(93, 59, 220, 36);
		frame.getContentPane().add(btn1);

		JButton btn2 = new JButton("");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				student.setNotificationType(NotificationTypes.CELLPHONE);
				JOptionPane.showMessageDialog(null,"Your notification preference is set to "+student.getNotificationType().toString(),"Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btn2.setBounds(93, 107, 220, 36);
		frame.getContentPane().add(btn2);
		btn2.setText(NotificationTypes.CELLPHONE.toString());
		
		JButton btn3 = new JButton("");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				student.setNotificationType(NotificationTypes.PIGEON_POST);
				JOptionPane.showMessageDialog(null,"Your notification preference is set to "+student.getNotificationType().toString(),"Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btn3.setBounds(93, 155, 220, 36);
		frame.getContentPane().add(btn3);
		btn3.setText(NotificationTypes.PIGEON_POST.toString());
		
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				StudentMenu menu = new StudentMenu();
				menu.setStudent(student);
			}
		});
		btnGoBack.setBounds(93, 203, 220, 36);
		frame.getContentPane().add(btnGoBack);
		frame.setVisible(true);
	}
}
