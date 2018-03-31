package cs2212;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.SystemColor;

public class ReadCourseFile {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReadCourseFile window = new ReadCourseFile();
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
	public ReadCourseFile() {
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
		
		
		JLabel lblEnterTheNames = new JLabel("Enter the names of the file(s) and press add.");
		lblEnterTheNames.setBounds(104, 65, 335, 22);
		frame.getContentPane().add(lblEnterTheNames);
		
		textField = new JTextField();
		textField.setBounds(210, 99, 156, 33);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(210, 144, 156, 33);
		frame.getContentPane().add(textField_1);
		
		JLabel lblNewLabel = new JLabel("File Name");
		lblNewLabel.setBounds(127, 103, 87, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("File Name");
		label.setBounds(127, 144, 87, 25);
		frame.getContentPane().add(label);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(352, 195, -179, 12);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(127, 195, 239, 12);
		frame.getContentPane().add(separator_1);
		
		JButton btnNewButton = new JButton("Read Course File(s)");
		frame.getRootPane().setDefaultButton(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = "";
				String fileName2 = "";
				if (textField_1.getText().equals("") && textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Please fill out the textbox(es)","Read File Error",JOptionPane.ERROR_MESSAGE);
				}
				else {

					if (textField_1.getText().equals("") && !textField.getText().equals("")) {
						fileName = textField.getText();
					}
					else if (!textField_1.getText().equals("") && textField.getText().equals("")) {
						fileName = textField_1.getText();
					}
					else {
						fileName = textField.getText();
						fileName2 = textField_1.getText();
					}
					
					try {
						OfferingFactory factory = new OfferingFactory();
						BufferedReader br = new BufferedReader(new FileReader(fileName));
						Course courseOffering1 = factory.createCourseOffering(br);
						br.close();
						if (!fileName2.equals("")) {
							OfferingFactory factory2 = new OfferingFactory();
							BufferedReader br2 = new BufferedReader(new FileReader(fileName2));
							Course courseOffering2 = factory2.createCourseOffering(br2);
							br2.close();
							JOptionPane.showMessageDialog(null,courseOffering1.getCourseName()+" and "+courseOffering2.getCourseName()+ " has been added successfully","Successful",JOptionPane.PLAIN_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null,courseOffering1.getCourseName()+ " has been added successfully","Successful",JOptionPane.PLAIN_MESSAGE);
						}
					}	
					catch(IOException exception) {
						JOptionPane.showMessageDialog(null,"No such file exists.","Invalid File Name",JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		btnNewButton.setBounds(177, 210, 143, 33);
		frame.getContentPane().add(btnNewButton);
		
		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		frame.setBounds(200, 200, 500, 300);
		frame.getContentPane().add(button);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		btnBack.setBounds(177, 245, 143, 33);
		frame.getContentPane().add(btnBack);
		
		JLabel lblNewLabel_1 = new JLabel("                                 Read Course Files");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(SystemColor.textHighlight);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setBounds(6, 6, 488, 45);
		frame.getContentPane().add(lblNewLabel_1);
		
	}

}
