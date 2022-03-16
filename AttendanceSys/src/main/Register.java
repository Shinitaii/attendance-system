package main;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;

public class Register extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, panelFirstName, panelMiddleName, panelLastName, panelUsername, panelPassword, panelConfirmPassword;
	private JTextField txtFirstName, txtMiddleName, txtLastName, txtUsername;
	public static JPasswordField pwdPassword, pwdCPassword;
	private JLabel labelFirstName, labelMiddleName, labelLastName, labelUsername, labelPassword, labelConfirmPass, labelCreateAccount, gender,
		lblStatus, usernameNote, logoShowPassword, logoShowCPassword, logoPasswordPolicy, lblpfp;
	public static JLabel back;
	private JButton createAccount;
	private JRadioButton male, female, student, teacher, admin;
	private ButtonGroup groupGender, occupationGroup;
	private boolean passwordChecker = false;
	String path;
	boolean photoSizeCheck = false;
	String obtainedGender = "", obtainedOccupation = "";
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Register() {
		setTitle("Register");
		setIconImage(Images.bLogo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		labelFirstName = new JLabel("First Name :");
		labelFirstName.setVerticalAlignment(SwingConstants.BOTTOM);
		labelFirstName.setBounds(12, 113, 78, 24);
		labelFirstName.setForeground(new Color(65, 105, 225));
		labelFirstName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelFirstName);
		
		panelFirstName = new JPanel();
		panelFirstName.setBounds(12, 136, 235, 40);
		panelFirstName.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelFirstName.setBackground(new Color(255, 255, 255));
		contentPane.add(panelFirstName);
		panelFirstName.setLayout(null);
		
		txtFirstName = new JTextField();
		txtFirstName.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtFirstName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		txtFirstName.setForeground(new Color(65, 105, 225));
		txtFirstName.setBounds(10, 9, 215, 21);
		txtFirstName.setColumns(10);
		panelFirstName.add(txtFirstName);
		
		labelMiddleName = new JLabel("Middle Name :");
		labelMiddleName.setVerticalAlignment(SwingConstants.BOTTOM);
		labelMiddleName.setBounds(12, 187, 100, 24);
		labelMiddleName.setForeground(new Color(65, 105, 225));
		labelMiddleName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelMiddleName);
		
		panelMiddleName = new JPanel();
		panelMiddleName.setBounds(12, 210, 235, 40);
		panelMiddleName.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelMiddleName.setBackground(new Color(255, 255, 255));
		contentPane.add(panelMiddleName);
		panelMiddleName.setLayout(null);
		
		txtMiddleName = new JTextField();
		txtMiddleName.setForeground(new Color(65, 105, 225));
		txtMiddleName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		txtMiddleName.setColumns(10);
		txtMiddleName.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtMiddleName.setBounds(10, 9, 215, 21);
		panelMiddleName.add(txtMiddleName);
		
		labelLastName = new JLabel("Last Name :");
		labelLastName.setBounds(12, 261, 100, 24);
		labelLastName.setForeground(new Color(65, 105, 225));
		labelLastName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelLastName);
		
		panelLastName = new JPanel();
		panelLastName.setBounds(12, 285, 235, 40);
		panelLastName.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelLastName.setBackground(new Color(255, 255, 255));
		contentPane.add(panelLastName);
		panelLastName.setLayout(null);
		
		txtLastName = new JTextField();
		txtLastName.setForeground(new Color(65, 105, 225));
		txtLastName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		txtLastName.setColumns(10);
		txtLastName.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtLastName.setBounds(10, 9, 215, 21);
		panelLastName.add(txtLastName);

		labelUsername = new JLabel("Username :");
		labelUsername.setBounds(12, 11, 78, 24);
		labelUsername.setForeground(new Color(65, 105, 225));
		labelUsername.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelUsername);
		
		panelUsername = new JPanel();
		panelUsername.setBounds(12, 33, 235, 40);
		panelUsername.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelUsername.setBackground(new Color(255, 255, 255));
		contentPane.add(panelUsername);
		panelUsername.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setForeground(new Color(65, 105, 225));
		txtUsername.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		txtUsername.setColumns(10);
		txtUsername.setBorder(new EmptyBorder(0, 0, 0, 0));
		txtUsername.setBounds(10, 9, 215, 21);
		panelUsername.add(txtUsername);
		
		labelPassword = new JLabel("Password :");
		labelPassword.setVerticalAlignment(SwingConstants.BOTTOM);
		labelPassword.setBounds(349, 187, 71, 24);
		labelPassword.setForeground(new Color(65, 105, 225));
		labelPassword.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelPassword);
		
		panelPassword = new JPanel();
		panelPassword.setBounds(349, 210, 235, 40);
		panelPassword.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelPassword.setBackground(new Color(255, 255, 255));
		contentPane.add(panelPassword);
		panelPassword.setLayout(null);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setForeground(new Color(65, 105, 225));
		pwdPassword.setBorder(new EmptyBorder(0, 0, 0, 0));
		pwdPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pwdPassword.setBounds(10, 9, 181, 21);
		panelPassword.add(pwdPassword);
		
		logoShowPassword = new JLabel("");
		logoShowPassword.setBounds(201, 9, 24, 24);
		logoShowPassword.setIcon(new ImageIcon(Images.showPass));
		logoShowPassword.addMouseListener(new PasswordIcon(logoShowPassword, pwdPassword));
		panelPassword.add(logoShowPassword);
		
		labelConfirmPass = new JLabel("Confirm Password :");
		labelConfirmPass.setVerticalAlignment(SwingConstants.BOTTOM);
		labelConfirmPass.setBounds(349, 260, 118, 24);
		labelConfirmPass.setForeground(new Color(65, 105, 225));
		labelConfirmPass.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelConfirmPass);
		
		panelConfirmPassword = new JPanel();
		panelConfirmPassword.setBounds(349, 285, 235, 40);
		panelConfirmPassword.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelConfirmPassword.setBackground(new Color(255, 255, 255));
		contentPane.add(panelConfirmPassword);
		panelConfirmPassword.setLayout(null);
		
		pwdCPassword = new JPasswordField();
		pwdCPassword.setForeground(new Color(65, 105, 225));
		pwdCPassword.setBorder(new EmptyBorder(0, 0, 0, 0));
		pwdCPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pwdCPassword.setBounds(10, 9, 181, 21);
		panelConfirmPassword.add(pwdCPassword);
		
		logoShowCPassword = new JLabel("");
		logoShowCPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoShowCPassword.setBounds(201, 11, 24, 24);
		logoShowCPassword.setIcon(new ImageIcon(Images.showPass));
		logoShowCPassword.addMouseListener(new PasswordIcon(logoShowCPassword, pwdCPassword));
		panelConfirmPassword.add(logoShowCPassword);
			
		createAccount = new JButton();
		createAccount.setBorder(null);
		createAccount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		createAccount.setBounds(167, 449, 284, 58);
		createAccount.setBackground(new Color(65, 105, 225));
		contentPane.add(createAccount);
		createAccount.setLayout(null);  
		
		labelCreateAccount = new JLabel("Create Account");
		labelCreateAccount.setHorizontalTextPosition(SwingConstants.CENTER);
		labelCreateAccount.setHorizontalAlignment(SwingConstants.CENTER);
		labelCreateAccount.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
		labelCreateAccount.setForeground(new Color(255, 255, 255));
		labelCreateAccount.setBounds(10, 10, 264, 36);
		
		createAccount.add(labelCreateAccount);
		createAccount.addMouseListener(new PropertiesListener(createAccount));
		createAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					char[] getPassword = pwdPassword.getPassword(), getCPassword = pwdCPassword.getPassword();
					String firstName = txtFirstName.getText(), middleName = txtMiddleName.getText(), lastName = txtLastName.getText(),
							username = txtUsername.getText(), password = String.valueOf(getPassword), cPassword = String.valueOf(getCPassword);
					int delay = 3000;
					ActionListener lblStatusClearer = new ActionListener() {
									public void actionPerformed (ActionEvent e) {
										lblStatus.setText("");
									}
								};
					Timer tick = new Timer(delay, lblStatusClearer);
					tick.setRepeats(false);
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem","root","Keqingisbestgirl");
					if(firstName.isEmpty() || middleName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || cPassword.isEmpty() || obtainedGender.isEmpty()) {		
						lblStatus.setText("Enter all of the credentials!");
						tick.start();
					} else {
						PreparedStatement exist = conn.prepareStatement("select * from userInfo where username='"+username+"'");
						ResultSet getUsername = exist.executeQuery();
						String obtainedUsername = "";
						while(getUsername.next()) {
						obtainedUsername = getUsername.getString("username");
						}
							if(obtainedUsername.equals(username)) {
								lblStatus.setText("Username is already taken!");
								tick.start();
							} else {
								if(!password.equals(cPassword)) {
									lblStatus.setText("Password does not match!");
											tick.start();
								} else {
									if(password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\p{Punct}])(?=\\S+$).{8,}")){
										passwordChecker = true;
									}
									if(!passwordChecker) {
										lblStatus.setText("<html>Password must contain atleast 8 characters and requires a number, a lowercase letter, an uppercase letter, a special character and must not contain any spaces!</html>");
										int longerDelay = 7000;
										Timer longerTick = new Timer(longerDelay, lblStatusClearer);
										longerTick.setRepeats(false);
										longerTick.start();
									} else {
										FileInputStream inputPhoto = null;
										try {
										String path = browseAction.pubPath;
										inputPhoto = new FileInputStream(path);
										} catch (IOException photo) {
											JOptionPane.showMessageDialog(null, "No photo!");
										}
										PreparedStatement register = conn.prepareStatement("INSERT INTO userInfo (firstName, middlename, lastname, username, pass, gender, occupation, profilePicture, datecreated) VALUES (?,?,?,?,?,?,?,?, CURRENT_TIMESTAMP)");
										register.setString(1, firstName);
										register.setString(2, middleName);
										register.setString(3, lastName);
										register.setString(4, username);
										register.setString(5, password);
										register.setString(6, obtainedGender);
										register.setString(7, obtainedOccupation);
										register.setBinaryStream(8, inputPhoto);
										int creation = register.executeUpdate();
												if(creation == 1) {
												String userID = "";
												PreparedStatement checkUserID = conn.prepareStatement("SELECT userid FROM userInfo WHERE username='"+username+"'");
												ResultSet confirmUserID = checkUserID.executeQuery();
												while(confirmUserID.next()) {
													userID = confirmUserID.getString("userid");
												}
												JOptionPane.showMessageDialog(null, "Account created successfully!");
												JOptionPane.showMessageDialog(null, "Your account UID is "+userID+".");
												
												EventQueue.invokeLater(new Runnable() {
													public void run() {
														try {
															Login frame = new Login();
															frame.setVisible(true);
														} catch (Exception e) {
															e.printStackTrace();
														}
													}
												});
												dispose();
												register.close();
												checkUserID.close();
											} else {
												JOptionPane.showMessageDialog(null, "Something went wrong.");
											}
										}
									}							
								}
							}
					conn.close();
					} catch (Exception sql) {
						sql.printStackTrace();
					}
			}
		});
		
		male = new JRadioButton("Male");
		male.setBounds(94, 336, 57, 24);
		male.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		male.setOpaque(false);
		male.setForeground(new Color(65, 105, 225));
		male.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(male);
		
		female = new JRadioButton("Female");
		female.setBounds(176, 336, 71, 24);
		female.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		female.setOpaque(false);
		female.setForeground(new Color(65, 105, 225));
		female.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(female);
		
		gender = new JLabel("Gender :");
		gender.setBounds(10, 336, 78, 24);
		gender.setForeground(new Color(65, 105, 225));
		gender.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(gender);
		
		male.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				obtainedGender = "Male";
			}
		});
		
		female.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				obtainedGender = "Female";
			}
		});
		
		groupGender = new ButtonGroup();
		groupGender.add(male);
		groupGender.add(female);
		
		student = new JRadioButton("Student");
		student.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtainedOccupation = "Student";
			}
		});
		student.setOpaque(false);
		student.setForeground(new Color(65, 105, 225));
		student.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		student.setBounds(430, 336, 78, 24);
		contentPane.add(student);
		
		teacher = new JRadioButton("Teacher");
		teacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtainedOccupation = "Teacher";
			}
		});
		teacher.setOpaque(false);
		teacher.setForeground(new Color(65, 105, 225));
		teacher.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		teacher.setBounds(510, 336, 78, 24);
		contentPane.add(teacher);
		
		admin = new JRadioButton("Admin");
		admin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtainedOccupation = "Admin";
			}
		});
		admin.setOpaque(false);
		admin.setForeground(new Color(65, 105, 225));
		admin.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		admin.setBounds(476, 364, 78, 24);
		contentPane.add(admin);
		
		occupationGroup = new ButtonGroup();
		occupationGroup.add(teacher);
		occupationGroup.add(admin);
		
		back = new JLabel("Back");
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setBounds(283, 512, 40, 14);
		back.setHorizontalAlignment(SwingConstants.CENTER);
		back.setForeground(new Color(65, 105, 225));
		back.setFont(new Font("Yu Gothic UI", Font.PLAIN, 13));
		back.addMouseListener(new PropertiesListener(back) {
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Login frame = new Login();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				dispose();
			}
		});
		contentPane.add(back);
		  
		lblStatus = new JLabel("");
		lblStatus.setVerticalAlignment(SwingConstants.TOP);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblStatus.setForeground(Color.RED);
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(167, 407, 284, 40);
		contentPane.add(lblStatus);
		
		usernameNote = new JLabel("<html>Note: Usernames won't be shown in the public.<br/>Do<font color=\"red\"><b> NOT</b></font> reveal your username to anyone!</html>");
		usernameNote.setForeground(new Color(65, 105, 225));
		usernameNote.setHorizontalAlignment(SwingConstants.LEFT);
		usernameNote.setFont(new Font("Tahoma", Font.PLAIN, 11));
		usernameNote.setBounds(12, 73, 225, 40);
		contentPane.add(usernameNote);
		
		logoPasswordPolicy = new JLabel("");
		logoPasswordPolicy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoPasswordPolicy.setBounds(430, 194, 16, 16);
		logoPasswordPolicy.setIcon(new ImageIcon(Images.question));
		logoPasswordPolicy.setToolTipText("<html>The password must have at least: <br/>- Contains 8 characters or more. <br/>- Contains atleast 1 lowercase and 1 uppercase letter."
				+ "<br/>- Contains 1 special character. <br/>- Does not contain spaces. </html>");
		contentPane.add(logoPasswordPolicy);
		
		getRootPane().setDefaultButton(createAccount);
		
		
		
		JPanel panelPFP = new JPanel();
		panelPFP.setLayout(null);
		panelPFP.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelPFP.setBackground(Color.WHITE);
		panelPFP.setBounds(434, 11, 150, 150);
		contentPane.add(panelPFP);
		
		lblpfp = new JLabel("");
		lblpfp.setHorizontalAlignment(SwingConstants.CENTER);
		lblpfp.setBorder(new LineBorder(new Color(65, 105, 225)));
		lblpfp.setBounds(0, 0, 150, 150);
		panelPFP.add(lblpfp);
		
		JButton browseButton = new JButton("Browse Image");
		browseButton.setForeground(Color.WHITE);
		browseButton.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		browseButton.setBorder(null);
		browseButton.setBackground(new Color(65, 105, 225));
		browseButton.setBounds(349, 131, 78, 30);
		browseButton.addActionListener(new browseAction(lblpfp));
		contentPane.add(browseButton);
		
		JLabel lblNewLabel_1 = new JLabel("Allowed JPG or PNG, Max size of 16MB");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.CYAN);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(409, 169, 175, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel occupation = new JLabel("Occupation:");
		occupation.setForeground(new Color(65, 105, 225));
		occupation.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		occupation.setBounds(349, 336, 78, 24);
		contentPane.add(occupation);
		
		setResizable(false);
	}
}

