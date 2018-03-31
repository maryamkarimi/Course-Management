package cs2212;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		btnNewButton.setBounds(134, 57, 171, 35);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnStopTheSystem = new JButton("Stop the System");
		btnStopTheSystem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemStatus.getInstance().stop();

			}
		});
		btnStopTheSystem.setBounds(134, 104, 171, 35);
		frame.getContentPane().add(btnStopTheSystem);
		
		JButton btnReadCourseFile = new JButton("Read Course File");
		btnReadCourseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				ReadCourseFile.main(null);
				frame.setVisible(true);
			}
		});
		btnReadCourseFile.setBounds(134, 151, 171, 35);
		frame.getContentPane().add(btnReadCourseFile);
		
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
		btnLogOut.setBounds(134, 198, 171, 35);
		frame.getContentPane().add(btnLogOut);
	}

}
