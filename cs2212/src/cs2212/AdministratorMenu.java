package cs2212;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;

public class AdministratorMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministratorMenu window = new AdministratorMenu();
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
	public AdministratorMenu() {
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
		
		JButton btnNewButton = new JButton("Start the System");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemStatus.getInstance().start();
				JOptionPane.showMessageDialog(null,"System is started now.","Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnNewButton.setBounds(159, 79, 171, 35);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnStopTheSystem = new JButton("Stop the System");
		btnStopTheSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemStatus.getInstance().stop();

			}
		});
		btnStopTheSystem.setBounds(159, 126, 171, 35);
		frame.getContentPane().add(btnStopTheSystem);
		
		JButton btnReadCourseFile = new JButton("Read Course File");
		btnReadCourseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ReadCourseFile.main(null);
				frame.setVisible(true);
			}
		});
		btnReadCourseFile.setBounds(159, 173, 171, 35);
		frame.getContentPane().add(btnReadCourseFile);
		
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
		btnLogOut.setBounds(159, 220, 171, 35);
		frame.getContentPane().add(btnLogOut);
		
		JLabel lblNewLabel = new JLabel("                                Administrator Menu");
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setBounds(6, 6, 488, 49);
		frame.getContentPane().add(lblNewLabel);
	}

}
