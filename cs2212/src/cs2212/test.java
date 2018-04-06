package cs2212;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class test {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
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
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(350, 150, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Start the System");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"System is started now.","Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnNewButton.setBounds(195, 76, 187, 44);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnStopTheSystem = new JButton("Stop the System");
		btnStopTheSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"System is stopped now.","Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnStopTheSystem.setBounds(195, 123, 187, 44);
		frame.getContentPane().add(btnStopTheSystem);
		
		JButton btnReadCourseFile = new JButton("Read Course File");
		btnReadCourseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
			}
		});
		btnReadCourseFile.setBounds(195, 170, 187, 44);
		frame.getContentPane().add(btnReadCourseFile);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Do you want to log out",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.getContentPane().removeAll();
				}
			}
		});
		btnLogOut.setBounds(195, 310, 187, 44);
		frame.getContentPane().add(btnLogOut);
		
		JLabel lblNewLabel = new JLabel("                                         Administrator Menu");
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setBounds(6, 6, 588, 62);
		frame.getContentPane().add(lblNewLabel);
		frame.setVisible(true);
		
		
		JButton btnChangeMyPassword = new JButton("Change My Password");
		btnChangeMyPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();	
			}
		});
		btnChangeMyPassword.setBounds(195, 217, 187, 44);
		frame.getContentPane().add(btnChangeMyPassword);
		
		JButton btnChangePersonalInfo = new JButton("Change personal info\n");
		btnChangePersonalInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnChangePersonalInfo.setBounds(195, 264, 187, 44);
		frame.getContentPane().add(btnChangePersonalInfo);
		
		JLabel lblDBirth = new JLabel("Date of birth (YYYYMMDD):");
		lblDBirth.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblDBirth.setBounds(150, 134, 216, 16);
		frame.getContentPane().add(lblDBirth);
	}
}
