package cs2212;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class LoginSystem {

	private JFrame frame;
	private JPasswordField txtPassword;
	private JTextField txtUserName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginSystem window = new LoginSystem();
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
	public LoginSystem() {
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
		
		
		JLabel lblCourseManagementSystem = new JLabel("Course Management System");
		lblCourseManagementSystem.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblCourseManagementSystem.setBounds(114, 18, 244, 37);
		frame.getContentPane().add(lblCourseManagementSystem);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(202, 119, 130, 31);
		frame.getContentPane().add(txtPassword);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(202, 84, 130, 31);
		frame.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(129, 87, 71, 24);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(129, 126, 71, 16);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUserName.getText();
				String password = txtPassword.getText();
				LoginServer server = new LoginServer("userpass.txt");
				String result = server.isValid(username, password);
				if (result.equals("n1")) {
					txtUserName.setText(null);
					txtPassword.setText(null);
					JOptionPane.showMessageDialog(null,"This username is not recongnized, try again!","Login Error",JOptionPane.ERROR_MESSAGE);
				}
				else if(result.equals("n2")) {
					txtPassword.setText(null);
					JOptionPane.showMessageDialog(null,"Username and Password do not match.","Login Error",JOptionPane.ERROR_MESSAGE);
				}
				// if the user is a student
				else if (result.equals("s")) {
					if (SystemStatus.getInstance().isStarted()) {
						frame.setVisible(false);
						StudentMenu menu = new StudentMenu();
						menu.setStudent((Student)Register.getInstance().getRegisteredUser(server.getID(username)));
					}
					else {
						JOptionPane.showMessageDialog(null,"You are not allowed to use the system at the moment, for more info call the administrator.","System Status Error",JOptionPane.PLAIN_MESSAGE);
					}
				}
				// if the user is an instructor
				else if (result.equals("i")) {
					if (SystemStatus.getInstance().isStarted()) {
						frame.setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null,"You are not allowed to use the system at the moment, for  more info call the administrator.","System Status Error",JOptionPane.PLAIN_MESSAGE);
					}
					
				}
				// if the user is an administrator
				else {
					frame.setVisible(false);
					AdministratorMenu.main(null);
				}
			}
		});
		btnLogin.setBounds(243, 183, 117, 29);
		frame.getContentPane().add(btnLogin);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUserName.setText(null);
				txtPassword.setText(null);
			}
		});
		btnReset.setBounds(114, 183, 117, 29);
		frame.getContentPane().add(btnReset);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(114, 172, 244, 12);
		frame.getContentPane().add(separator);
	}
}
