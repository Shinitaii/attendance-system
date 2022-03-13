package main;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.*;

public class ResetPassword extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPasswordField pwdPassword;
	private JPasswordField pwdCPassword;
	private JLabel logoShowPass, lblStatus;
	private JLabel logoShowCPass;
	public int uid = Integer.valueOf(ForgotPassword.userid);
	private boolean passwordChecker = false;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResetPassword frame = new ResetPassword();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ResetPassword() {
		setTitle("Reset Password");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ForgotPassword.class.getResource("/res/attendance.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 375);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelPass = new JPanel();
		panelPass.setBackground(Color.WHITE);
		panelPass.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelPass.setBounds(115, 60, 255, 45);
		contentPane.add(panelPass);
		panelPass.setLayout(null);
		
		JLabel logoPassword = new JLabel("");
		logoPassword.setBounds(10, 11, 24, 24);
		panelPass.add(logoPassword);
		logoPassword.setIcon(new ImageIcon(Images.pass));
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBorder(null);
		pwdPassword.setForeground(new Color(65, 105, 225));
		pwdPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pwdPassword.setBounds(44, 13, 170, 21);
		panelPass.add(pwdPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(65, 105, 225));
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPassword.setBounds(44, 0, 46, 14);
		panelPass.add(lblPassword);
		
		logoShowPass = new JLabel("");
		logoShowPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoShowPass.setBounds(221, 11, 24, 24);
		logoShowPass.setIcon(new ImageIcon(Images.showPass));
		logoShowPass.addMouseListener(new PasswordIcon(logoShowPass, pwdPassword));
		panelPass.add(logoShowPass);
		
		JPanel panelCPass = new JPanel();
		panelCPass.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelCPass.setBackground(new Color(255, 255, 255));
		panelCPass.setBounds(115, 120, 255, 45);
		contentPane.add(panelCPass);
		panelCPass.setLayout(null);
		
		JLabel logoCPassword = new JLabel("");
		logoCPassword.setBounds(10, 11, 24, 24);
		panelCPass.add(logoCPassword);
		logoCPassword.setIcon(new ImageIcon(Images.cpass));
		
		pwdCPassword = new JPasswordField();
		pwdCPassword.setBorder(null);
		pwdCPassword.setForeground(new Color(65, 105, 225));
		pwdCPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pwdCPassword.setBounds(44, 13, 170, 23);
		panelCPass.add(pwdCPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setForeground(new Color(65, 105, 225));
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfirmPassword.setBounds(44, 0, 86, 14);
		panelCPass.add(lblConfirmPassword);
		
		logoShowCPass = new JLabel("");
		logoShowCPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoShowCPass.setBounds(221, 11, 24, 24);
		logoShowCPass.setIcon(new ImageIcon(Images.showPass));
		logoShowCPass.addMouseListener(new PasswordIcon(logoShowCPass, pwdCPassword));
		panelCPass.add(logoShowCPass);
		
		JButton CheckA = new JButton();
		CheckA.setBorder(null);
		CheckA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		CheckA.addMouseListener(new PropertiesListener(CheckA));
		CheckA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					char[] getPassword = pwdPassword.getPassword(), getCPassword = pwdCPassword.getPassword();
					String password = String.valueOf(getPassword), cPassword = String.valueOf(getCPassword);
					int delay = 3000;
					ActionListener lblStatusClearer = new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							lblStatus.setText("");
						}
					};
					Timer tick = new Timer(delay, lblStatusClearer);
					tick.setRepeats(false);
					Connection conn = DriverManager.getConnection("jdbc:mysql://sql6.freesqldatabase.com:3306/sql6476155","sql6476155","HHHLDqnNka");
					PreparedStatement changePassword = conn.prepareStatement("update userInfo set pass='"+password+"' where userid ='"+uid+"';");
					if(password.isEmpty() || cPassword.isEmpty()) {
						lblStatus.setText("Enter all of the credentials!");
					} else {
						if(password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\p{Punct}])(?=\\S+$).{8,}")) {
							passwordChecker = true;
						}
							if(!passwordChecker) {
								lblStatus.setText("<html>Password must contain atleast 8 characters and requires a number, a lowercase letter, an uppercase letter, a special character and must not contain any spaces!</html>");
								int longerDelay = 7000;
								Timer longerTick = new Timer(longerDelay, lblStatusClearer);
								longerTick.setRepeats(false);
								longerTick.start();
							} else {
								if(!password.equals(cPassword)) {
									lblStatus.setText("Password does not match!");
									tick.start();
								} else {
									int success = changePassword.executeUpdate();
									if(success == 1) {
										ForgotPassword.userid = "";
										dispose();	
										JOptionPane.showMessageDialog(null, "Changed password successfully!");
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
										
									} else {
										JOptionPane.showMessageDialog(null, "Changed password failed!");
									}	
								}
							}
						}	
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
			}
		});
		CheckA.setBackground(new Color(65, 105, 225));
		CheckA.setBounds(115, 230, 254, 61);
		contentPane.add(CheckA);
		CheckA.setLayout(null);
		
		JLabel lblCA = new JLabel("Reset Password");
		lblCA.setHorizontalAlignment(SwingConstants.CENTER);
		lblCA.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
		lblCA.setForeground(new Color(255, 250, 250));
		lblCA.setBounds(10, 11, 234, 39);
		CheckA.add(lblCA);
		
		JLabel lblBack = new JLabel("Back");
		lblBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBack.addMouseListener(new PropertiesListener(lblBack) {
			public void mouseClicked(MouseEvent e) {
				dispose();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ForgotPassword.userid = "";
							ForgotPassword frame = new ForgotPassword();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		lblBack.setForeground(new Color(65, 105, 225));
		lblBack.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		lblBack.setHorizontalAlignment(SwingConstants.CENTER);
		lblBack.setBounds(225, 300, 34, 12);
		contentPane.add(lblBack);
		
		lblStatus = new JLabel("");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblStatus.setForeground(Color.RED);
		lblStatus.setBounds(115, 176, 255, 45);
		contentPane.add(lblStatus);
		
		JLabel logoPasswordPolicy = new JLabel("");
		logoPasswordPolicy.setToolTipText("<html>The password must have at least: <br/>- Contains 8 characters or more. <br/>- Contains atleast 1 lowercase and 1 uppercase letter.<br/>- Contains 1 special character. <br/>- Does not contain spaces. </html>");
		logoPasswordPolicy.setIcon(new ImageIcon(Images.question));
		logoPasswordPolicy.setBounds(371, 60, 16, 16);
		contentPane.add(logoPasswordPolicy);
		
		getRootPane().setDefaultButton(CheckA);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}
