package systemUserInterfaces;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import systemServers.LoginServer;
import systemUsers.Administrator;
import systemUsers.ISystemUser;
import systemUsers.Instructor;
import systemUsers.Student;

public class SystemUserPages {

	protected JFrame frame;
	protected JLabel lblHeader;
	
	protected SystemUserPages() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		// create the frame of fixed size
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(350, 150, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	/* This interface allows Instructors, Students, and Administrator to change their password */
	/* ================================================================= */
	protected void changeMyPassword(ISystemUser user, String userType) {
	/* ================================================================= */	
		// label header
		JLabel lblHeader = new JLabel("                                        Change Password");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setOpaque(true);
		lblHeader.setBounds(6, 6, 588, 55);
		frame.getContentPane().add(lblHeader);
		
		// textField that will contain the new password entered by the user
		JTextField txtNewPassword = new JTextField();
		txtNewPassword.setBounds(290, 76, 146, 37);
		frame.getContentPane().add(txtNewPassword);
		
		// label for txtNewPassword
		JLabel lblNewPassword = new JLabel("New password:");
		lblNewPassword.setBounds(154, 82, 192, 25);
		frame.getContentPane().add(lblNewPassword);
		
		// separates buttons from labels/ textFields
		JSeparator separator = new JSeparator();
		separator.setBounds(153, 122, 283, 12);
		frame.getContentPane().add(separator);
		
		// changePass button - is set as the default button of the frame
		JButton btnChangePass = new JButton("Change password");
		frame.getRootPane().setDefaultButton( btnChangePass);
		 btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// if textField is empty, the following message is shown
				if (txtNewPassword.equals(null)) {
					JOptionPane.showMessageDialog(null,"Please enter your new password.","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					// add new user (this is the same as changing user's password)
					if (userType.equals("a")) {
						LoginServer.getInstance().addUser("a", user.getID(), txtNewPassword.getText());
					}
					else if (userType.equals("i")) {
						LoginServer.getInstance().addUser("i", user.getID(), txtNewPassword.getText());
					}
					else {
						LoginServer.getInstance().addUser("s", user.getID(), txtNewPassword.getText());
					}
					JOptionPane.showMessageDialog(null,"Password changed successfully. ","Successful.",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		 btnChangePass.setBounds(230, 143, 130, 37);
		frame.getContentPane().add(btnChangePass);
		
		// go back button - shows a menu based on userType
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				if (userType.equals("a")) {
					new AdministratorPages((Administrator)user);
				}
				else if (userType.equals("i")) {
					new InstructorPages((Instructor)user);
				}
				else {
					new StudentPages((Student)user);
				}
			}
		});
		btnBack.setBounds(230, 182, 130, 37);
		frame.getContentPane().add(btnBack);
		
		frame.setVisible(true);
	}
	

}
