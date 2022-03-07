import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;

public class Register extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, panelFirstName, panelMiddleName, panelLastName, panelUsername, panelPassword, panelConfirmPassword;
	private JTextField txtFirstName, txtMiddleName, txtLastName, txtUsername;
	public static JPasswordField pwdPassword, pwdCPassword;
	private JLabel r, labelFirstName, labelMiddleName, labelLastName, labelUsername, labelPassword, labelConfirmPass, labelCreateAccount, gender,
		lblStatus, usernameNote, logoShowPassword, logoShowCPassword, logoPasswordPolicy;
	public static JLabel back;
	private JButton createAccount;
	private JRadioButton male, female;
	private ButtonGroup groupGender;
	private boolean passwordChecker = false;
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
		setBounds(100, 100, 610, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		r = new JLabel("Register");
		r.setBounds(12, 0, 574, 75);
		r.setHorizontalAlignment(SwingConstants.CENTER);
		r.setForeground(new Color(65, 105, 225));
		r.setFont(new Font("Yu Gothic UI", Font.PLAIN, 30));
		contentPane.add(r);
		
		labelFirstName = new JLabel("First Name :");
		labelFirstName.setVerticalAlignment(SwingConstants.BOTTOM);
		labelFirstName.setBounds(12, 79, 78, 24);
		labelFirstName.setForeground(new Color(65, 105, 225));
		labelFirstName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelFirstName);
		
		panelFirstName = new JPanel();
		panelFirstName.setBounds(12, 102, 235, 40);
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
		labelMiddleName.setBounds(12, 141, 100, 24);
		labelMiddleName.setForeground(new Color(65, 105, 225));
		labelMiddleName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelMiddleName);
		
		panelMiddleName = new JPanel();
		panelMiddleName.setBounds(12, 164, 235, 40);
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
		labelLastName.setBounds(12, 205, 100, 24);
		labelLastName.setForeground(new Color(65, 105, 225));
		labelLastName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelLastName);
		
		panelLastName = new JPanel();
		panelLastName.setBounds(12, 229, 235, 40);
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
		labelUsername.setBounds(349, 80, 78, 24);
		labelUsername.setForeground(new Color(65, 105, 225));
		labelUsername.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelUsername);
		
		panelUsername = new JPanel();
		panelUsername.setBounds(349, 102, 235, 40);
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
		labelPassword.setBounds(349, 181, 71, 24);
		labelPassword.setForeground(new Color(65, 105, 225));
		labelPassword.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelPassword);
		
		panelPassword = new JPanel();
		panelPassword.setBounds(351, 205, 235, 40);
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
		labelConfirmPass.setBounds(348, 264, 118, 24);
		labelConfirmPass.setForeground(new Color(65, 105, 225));
		labelConfirmPass.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(labelConfirmPass);
		
		panelConfirmPassword = new JPanel();
		panelConfirmPassword.setBounds(351, 287, 235, 40);
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
		createAccount.setBounds(157, 372, 284, 58);
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
					Connection conn = DriverManager.getConnection("jdbc:mysql://sql6.freesqldatabase.com:3306/sql6476155","sql6476155","HHHLDqnNka");
					if(firstName.isEmpty() && middleName.isEmpty() && lastName.isEmpty() && username.isEmpty() && password.isEmpty() && cPassword.isEmpty() && obtainedGender.isEmpty()) {		
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
										PreparedStatement register = conn.prepareStatement("INSERT INTO userInfo (firstName, middlename, lastname, username, pass, gender, datecreated) VALUES (?,?,?,?,?,?, CURRENT_TIMESTAMP)");
										register.setString(1, firstName);
										register.setString(2, middleName);
										register.setString(3, lastName);
										register.setString(4, username);
										register.setString(5, password);
										register.setString(6, obtainedGender);
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
											}
										}
									}							
								}
							}
					conn.close();
					} catch (SQLException sql) {
						sql.printStackTrace();
					}
			}
		});
		
		male = new JRadioButton("Male");
		male.setBounds(96, 276, 57, 24);
		male.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		male.setOpaque(false);
		male.setForeground(new Color(65, 105, 225));
		male.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(male);
		
		female = new JRadioButton("Female");
		female.setBounds(176, 276, 71, 24);
		female.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		female.setOpaque(false);
		female.setForeground(new Color(65, 105, 225));
		female.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		contentPane.add(female);
		
		gender = new JLabel("Gender :");
		gender.setBounds(12, 276, 78, 24);
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
		
		back = new JLabel("Back");
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setBounds(280, 436, 40, 14);
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
		lblStatus.setBounds(157, 331, 284, 40);
		contentPane.add(lblStatus);
		
		usernameNote = new JLabel("<html>Note: Usernames won't be shown in the public.<br/>Do<font color=\"red\"><b> NOT</b></font> reveal your username to anyone!</html>");
		usernameNote.setForeground(new Color(65, 105, 225));
		usernameNote.setHorizontalAlignment(SwingConstants.LEFT);
		usernameNote.setFont(new Font("Tahoma", Font.PLAIN, 11));
		usernameNote.setBounds(349, 144, 225, 40);
		contentPane.add(usernameNote);
		
		logoPasswordPolicy = new JLabel("");
		logoPasswordPolicy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoPasswordPolicy.setBounds(417, 188, 16, 16);
		logoPasswordPolicy.setIcon(new ImageIcon(Images.question));
		logoPasswordPolicy.setToolTipText("<html>The password must have at least: <br/>- Contains 8 characters or more. <br/>- Contains atleast 1 lowercase and 1 uppercase letter."
				+ "<br/>- Contains 1 special character. <br/>- Does not contain spaces. </html>");
		contentPane.add(logoPasswordPolicy);
		
		getRootPane().setDefaultButton(createAccount);
		setResizable(false);
	}
}
