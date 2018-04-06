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
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.awt.Color;
import java.awt.ComponentOrientation;
import javax.swing.JScrollPane;

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
		
		JTextField textField = new JTextField();
		textField.setBounds(267, 87, 151, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblCourseId = new JLabel("Course ID:");
		lblCourseId.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCourseId.setBounds(188, 93, 93, 16);
		frame.getContentPane().add(lblCourseId);
		
		JButton btnNewButton = new JButton("Print Record");

		frame.getRootPane().setDefaultButton(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setBounds(188, 93, 110, 38);
		frame.getContentPane().add(btnNewButton);

		frame.setVisible(true);
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				//studentMenu(student);
			}
		});
		btnGoBack.setBounds(309, 90, 111, 40);
		frame.getContentPane().add(btnGoBack);

		
	

	}
}
