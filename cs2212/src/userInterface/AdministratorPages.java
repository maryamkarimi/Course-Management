package userInterface;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import systemUserOperations.AdministratorOperations;
import systemUsers.Administrator;

public class AdministratorPages extends SystemUserPages{

	private Administrator admin;
	AdministratorOperations operations;

	/**
	 * Create the application.
	 */
	public AdministratorPages(Administrator administrator) {
		this.admin = administrator;
		operations = new AdministratorOperations(admin);
		adminMenu();
	}

	/* Provides an interface for administrator menu - showing available operations which can be chosen */
	/* ================================================================= */
	private void adminMenu() {
	/* ================================================================= */
		super.initialize();
		// start the system button
		JButton btnStart = new JButton("Start the System");
		
		// Administrator can start the system by clicking on this button
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// calls the startSystem from adminOperations.
				operations.startSystem();
				// shows the following message to user.
				JOptionPane.showMessageDialog(null,"System is started now.","Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnStart.setBounds(195, 76, 187, 44);
		frame.getContentPane().add(btnStart);
		
		// stop the system button
		JButton btnStop = new JButton("Stop the System");
		
		// Administrator can stop the system by clicking on this button
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// calls the stopSystem from adminOperations.
				operations.stopSystem();
				// shows the following message to user.
				JOptionPane.showMessageDialog(null,"System is stopped now.","Successful",JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnStop.setBounds(195, 123, 187, 44);
		frame.getContentPane().add(btnStop);
		
		// read course file button
		JButton btnReadCourseFile = new JButton("Read Course File");
		btnReadCourseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				// calls readCourseFile interface
				readCourseFile();
			}
		});
		btnReadCourseFile.setBounds(195, 170, 187, 44);
		frame.getContentPane().add(btnReadCourseFile);
		
		// log out button
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// confirms if the user wants to log out
				JFrame frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit","Do you want to log out",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
					frame.setVisible(false);
					frame.getContentPane().removeAll();
					// go back to login interface
					new LoginPage();
				}
			}
		});
		btnLogOut.setBounds(195, 310, 187, 44);
		frame.getContentPane().add(btnLogOut);
		
		// label on top of the page
		JLabel lblAdminMenu = new JLabel("                                         Administrator Menu");
		lblAdminMenu.setBackground(SystemColor.textHighlight);
		lblAdminMenu.setOpaque(true);
		lblAdminMenu.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblAdminMenu.setBounds(6, 6, 588, 62);
		frame.getContentPane().add(lblAdminMenu);
		
		// change password button
		JButton btnChangeMyPassword = new JButton("Change My Password");
		btnChangeMyPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				// calls changeMyPassword with admin's ID and "a" which indicates user is an administrator
				changeMyPassword(admin,"a");	
			}
		});
		btnChangeMyPassword.setBounds(195, 217, 187, 44);
		frame.getContentPane().add(btnChangeMyPassword);
		
		// change personal info button
		JButton btnChangePersonalInfo = new JButton("Change personal info\n");
		btnChangePersonalInfo.setBounds(195, 264, 187, 44);
		btnChangePersonalInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.getContentPane().removeAll();
				changePersonalInfo();
			}
		});
		frame.getContentPane().add(btnChangePersonalInfo);
		// set the frame to be visible
		frame.setVisible(true);
	}
	
	/* Provides an interface for administrator to change her/his info  */
	/* ================================================================= */
	private void changePersonalInfo() {
	/* ================================================================= */
		// label used as a header with text "change personal info"
		JLabel lblHeader = new JLabel("                                          Change Personal Info");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setOpaque(true);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setBounds(6, 6, 588, 44);
		frame.getContentPane().add(lblHeader);
		
		// label for name
		JLabel name = new JLabel("Name:");
		name.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		name.setBounds(150, 169, 93, 16);
		frame.getContentPane().add(name);
		
		// textField for name
		JTextField txtName = new JTextField();
		txtName.setBounds(350, 160, 147, 36);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		// label for surname
		JLabel surname = new JLabel("Surname:");
		surname.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		surname.setBounds(150, 97, 93, 16);
		frame.getContentPane().add(surname);
		
		// textField for surname
		JTextField txtSurname = new JTextField();
		txtSurname.setColumns(10);
		txtSurname.setBounds(350, 88, 147, 36);
		frame.getContentPane().add(txtSurname);
		
		// label for date of birth
		JLabel lblDBirth = new JLabel("Date of birth (YYYYMMDD):");
		lblDBirth.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblDBirth.setBounds(150, 134, 216, 16);
		frame.getContentPane().add(lblDBirth);

		// textField for date of birth
		JTextField txtDBirth = new JTextField();
		txtDBirth.setColumns(10);
		txtDBirth.setBounds(350, 124, 147, 36);
		frame.getContentPane().add(txtDBirth);

		// submit button - when user click on this button, her/his info will be updated.
		JButton btnsubmit = new JButton("Submit");
		frame.getRootPane().setDefaultButton(btnsubmit);
		btnsubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String nameStr = txtName.getText();
					String surNameStr = txtSurname.getText();
					String DBirthStr = txtDBirth.getText();
					// call changePersonalInfo operation from adminOperations class
					operations.changePersonalInfo(nameStr, surNameStr, DBirthStr);
					JOptionPane.showMessageDialog(null,"Personal info updated.","Successful",JOptionPane.PLAIN_MESSAGE);
			}	
		});
		btnsubmit.setBounds(240, 210, 128, 36);
		frame.getContentPane().add(btnsubmit);

		// separates textFields/ labels from buttons
		JSeparator separator = new JSeparator();
		separator.setBounds(150, 195, 350, 12);
		frame.getContentPane().add(separator);
		
		// go back button
		JButton btnGoBack = new JButton("Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//goes back to adminMenu
					returnToMenu();
			}
		});
		btnGoBack.setBounds(240, 250, 128, 36);
		frame.getContentPane().add(btnGoBack);
		frame.setVisible(true);
	}
	
	/* Provides an interface for administrator to read course files */
	/* ================================================================= */
	private void readCourseFile() {
	/* ================================================================= */

		JLabel lblEnterTheNames = new JLabel("Enter the names of the file(s) and press add.");
		lblEnterTheNames.setBounds(151, 85, 335, 22);
		frame.getContentPane().add(lblEnterTheNames);
		
		// textField for fileName
		JTextField txtFile1Name = new JTextField();
		txtFile1Name.setBounds(253, 119, 156, 33);
		frame.getContentPane().add(txtFile1Name);
		txtFile1Name.setColumns(10);
		
		// textField for fileName
		JTextField txtFile2Name = new JTextField();
		txtFile2Name.setColumns(10);
		txtFile2Name.setBounds(253, 164, 156, 33);
		frame.getContentPane().add(txtFile2Name);
		
		JLabel lblFile1Name = new JLabel("File Name");
		lblFile1Name.setBounds(170, 123, 87, 25);
		frame.getContentPane().add(lblFile1Name);
		
		JLabel lblFile2Name = new JLabel("File Name");
		lblFile2Name.setBounds(170, 164, 87, 25);
		frame.getContentPane().add(lblFile2Name);

		// separates buttons from textFields/ labels
		JSeparator separator = new JSeparator();
		separator.setBounds(170, 215, 239, 12);
		frame.getContentPane().add(separator);
		
		JButton btnReadCourseFile = new JButton("Read Course File(s)");
		frame.getRootPane().setDefaultButton(btnReadCourseFile);
		btnReadCourseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = "";
				String fileName2 = "";
				
				// if both of textFields are empty
				if (txtFile2Name.getText().equals("") && txtFile1Name.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Please fill out the textfield(s)","Read File Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					// if the first textField is empty and the second one is not.
					if (!txtFile2Name.getText().equals("") && txtFile1Name.getText().equals("")) {
						fileName = txtFile2Name.getText();
					}
					// if the second textField is empty and the first one is not.
					else if (txtFile2Name.getText().equals("") && !txtFile1Name.getText().equals("")) {
						fileName = txtFile1Name.getText();
					}
					// if none of the textFields is empty.
					else {
						fileName = txtFile1Name.getText();
						fileName2 = txtFile2Name.getText();
					}
					
					try {
						// adds the first course by calling readCourseFile
						operations.readCourseFile(fileName);
						
						// if the second file exists, adds the course
						if (!fileName2.equals("")) {
							operations.readCourseFile(fileName2);
							// message showing that both of courses has been added.
							JOptionPane.showMessageDialog(null,"Courses has been added successfully","Successful",JOptionPane.PLAIN_MESSAGE);
						}
						else {
							// message showing that one course has been added.
							JOptionPane.showMessageDialog(null,"Course has been added successfully","Successful",JOptionPane.PLAIN_MESSAGE);
						}
					}	
					catch(IOException IOexception) {
						// no file with this name is found
						JOptionPane.showMessageDialog(null,"No such file exists.","Invalid File Name",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnReadCourseFile.setBounds(220, 230, 143, 38);
		frame.getContentPane().add(btnReadCourseFile);
		
		// back button - goes back to adminMenu once clicked
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnToMenu();
			}
		});
		btnBack.setBounds(220, 273, 143, 38);
		frame.getContentPane().add(btnBack);
		
		// header label with text "Read Course Files"
		JLabel lblHeader = new JLabel("                                         Read Course Files");
		lblHeader.setOpaque(true);
		lblHeader.setBackground(SystemColor.textHighlight);
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblHeader.setBounds(6, 6, 588, 45);
		frame.getContentPane().add(lblHeader);
		// set this frame to be visible
		frame.setVisible(true);
	}
	
	private void returnToMenu() {
		frame.setVisible(false);
		frame.getContentPane().removeAll();
		adminMenu();
	}

}
