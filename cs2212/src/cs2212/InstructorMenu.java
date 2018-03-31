package cs2212;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InstructorMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstructorMenu window = new InstructorMenu();
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
	public InstructorMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("                                Instructor Menu");
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setBounds(6, 6, 488, 45);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnAddMarkFor = new JButton("Add mark for a student");
		btnAddMarkFor.setBounds(131, 76, 224, 37);
		frame.getContentPane().add(btnAddMarkFor);
		
		JButton btnModifyMarkFor = new JButton("Modify mark for a student");
		btnModifyMarkFor.setBounds(131, 118, 224, 37);
		frame.getContentPane().add(btnModifyMarkFor);
		
		JButton btnCalculateFinalGrade = new JButton("Calculate final grade for a student");
		btnCalculateFinalGrade.setBounds(131, 161, 224, 37);
		frame.getContentPane().add(btnCalculateFinalGrade);
		
		JButton button_3 = new JButton("Log Out");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Do you want to log out",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.dispose();
					LoginSystem.main(null);
				}
			}
		});
		button_3.setBounds(131, 246, 224, 37);
		frame.getContentPane().add(button_3);
		
		JButton btnPrintClassRecord = new JButton("Print class record");
		btnPrintClassRecord.setBounds(131, 203, 224, 37);
		frame.getContentPane().add(btnPrintClassRecord);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
	}
}
