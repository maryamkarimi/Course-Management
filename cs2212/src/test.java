import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import offerings.Course;
import registrar.Register;

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
		frame.setBounds(350, 150, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCourseID = new JLabel("Course ID:");
		lblCourseID.setBounds(111, 73, 72, 25);
		frame.getContentPane().add(lblCourseID);
		
		// scrollBar for textArea that represent course info
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(83, 108, 440, 171);
		frame.getContentPane().add(scrollPane);
		
		// textArea that shows course info
		JTextArea txtCourseInfo = new JTextArea();
		scrollPane.setViewportView(txtCourseInfo);
		txtCourseInfo.setEditable(false);

		// go back button - goes back to studentMenu
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGoBack.setBounds(242, 291, 110, 39);
		frame.getContentPane().add(btnGoBack);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(195, 66, 277, 40);
		frame.getContentPane().add(comboBox);
	}

}
