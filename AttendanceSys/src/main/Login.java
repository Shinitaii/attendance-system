package main;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {

	private static final long serialVersionUID = 12L;
	
	private JPanel contentPane, panelUsername, panelPassword;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JButton login;
	public static JLabel logo, logoUsername, logoPassword, forgotPass, register, lblLogin;
	public static String pubUsername, pubUID, pubFN, pubMN, pubLN, pubFullName, pubOccupation, pubSchoolName, pubSchoolID, pubInviteCode, pubDeptName, pubSecName;
	public static boolean pubHasASchool, pubHasADept, pubHasASec;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblStatus;
	int delayLabel = 100, delayButton = 50;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setTitle("Login");
		setIconImage(Images.bLogo);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelUsername = new JPanel();
		panelUsername.setOpaque(false);
		panelUsername.setBounds(124, 171, 235, 46);
		panelUsername.setBorder(new LineBorder(new Color(65, 105, 225)));
		contentPane.add(panelUsername);
		panelUsername.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBorder(null);
		txtUsername.setForeground(new Color(65, 105, 225));
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUsername.setBounds(41, 14, 184, 24);
		panelUsername.add(txtUsername);
		txtUsername.setColumns(10);
		
		logoUsername = new JLabel("");
		logoUsername.setBounds(7, 11, 24, 24);
		panelUsername.add(logoUsername);
		logoUsername.setIcon(new ImageIcon(Images.user));
		
		lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(65, 105, 225));
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsername.setBounds(41, 1, 87, 14);
		panelUsername.add(lblUsername);
		
		panelPassword = new JPanel();
		panelPassword.setOpaque(false);
		panelPassword.setBounds(124, 228, 235, 46);
		panelPassword.setBorder(new LineBorder(new Color(65, 105, 225)));
		contentPane.add(panelPassword);
		panelPassword.setLayout(null);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBorder(null);	
		pwdPassword.setForeground(new Color(65, 105, 225));
		pwdPassword.setEchoChar('�');
		pwdPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pwdPassword.setBounds(41, 14, 150, 24);
		panelPassword.add(pwdPassword);
			
		logoPassword = new JLabel("");
		logoPassword.setBounds(7, 11, 24, 24);
		panelPassword.add(logoPassword);
		logoPassword.setIcon(new ImageIcon(Images.pass));
		
		lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(65, 105, 225));
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPassword.setBounds(41, 1, 87, 14);
		panelPassword.add(lblPassword);
		
		JLabel logoShowPassword = new JLabel("");
		logoShowPassword.setBounds(201, 14, 24, 24);
		panelPassword.add(logoShowPassword);
		logoShowPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoShowPassword.setIcon(new ImageIcon(Images.showPass));
		logoShowPassword.addMouseListener(new PasswordIcon(logoShowPassword, pwdPassword));
		
		lblStatus = new JLabel("");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(124, 285, 235, 28);
		contentPane.add(lblStatus);
		
		login = new JButton();
		login.setBorder(null);
		login.addMouseListener(new PropertiesListener(login));
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user, MySQLConnectivity.pass)) {	
					char[] getPassword = pwdPassword.getPassword();
					String username = txtUsername.getText(), password = String.valueOf(getPassword);
					pubUsername = username;
					byte[] obtainedSalt = null;
					PreparedStatement getSalt = conn.prepareStatement("select saltpass from userinfo where username='"+username+"'");
					ResultSet get = getSalt.executeQuery();
					if(get.next()) {
						obtainedSalt = get.getBytes("saltpass");
					}
					if(obtainedSalt != null) {
						PreparedStatement checkAccount = conn.prepareStatement("select * from userinfo where username='"+username+"' and pass='"+HashedPassword.existingSalt(password, obtainedSalt)+"'");
						ResultSet x = checkAccount.executeQuery();
						if(x.next()) {
							PreparedStatement checkInfo = conn.prepareStatement("select userid, firstname, middlename, lastname, occupation, hasASchool, schoolname, departmentname, hasADept, sectionname, hasASec, inviteCodeOfSchool from userinfo where username='"+username+"'");
							ResultSet whatInfo = checkInfo.executeQuery();
							while (whatInfo.next()) {
								pubUID = whatInfo.getString("userid");
								pubFN = whatInfo.getString("firstname");
								pubMN = whatInfo.getString("middlename");
								pubLN = whatInfo.getString("lastname");
								pubOccupation = whatInfo.getString("occupation");
								pubHasASchool = whatInfo.getBoolean("hasASchool");
								pubSchoolName = whatInfo.getString("schoolname");
								pubDeptName = whatInfo.getString("departmentname");
								pubHasADept = whatInfo.getBoolean("hasADept");
								pubSecName = whatInfo.getString("sectionname");
								pubHasASec = whatInfo.getBoolean("hasASec");
								pubInviteCode = whatInfo.getString("inviteCodeOfSchool");
							}	
							PreparedStatement checkSchoolInfo = conn.prepareStatement("select schoolid from schoolinfo where schoolname='"+pubSchoolName+"' and inviteCode='"+pubInviteCode+"'");
							ResultSet whatSchoolInfo = checkSchoolInfo.executeQuery();
							if(whatSchoolInfo.next()) {
								pubSchoolID = whatSchoolInfo.getString("schoolid");
							}
							pubFullName = pubFN+" "+pubMN+" "+pubLN;
							if(!pubHasASchool) {
								try {
									SelectSchool dialog = new SelectSchool();
									dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
									dialog.setVisible(true);
							} catch (Exception dialog) {
								dialog.printStackTrace();
							}
							} else {
								EventQueue.invokeLater(new Runnable() {
									public void run() {
										try {
											MainMenu frame = new MainMenu();
											frame.setVisible(true);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								});
							}
							dispose();
							whatInfo.close();
							checkInfo.close();
						} else {
							lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
							lblStatus.setText("Incorrect username or password!");
							lblStatus.setForeground(Color.RED);
						}
						x.close();
						conn.close();
						checkAccount.close();
					} else {
						lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
						lblStatus.setText("Incorrect username or password!");
						lblStatus.setForeground(Color.RED);
					}
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
			}
		});
		login.setBounds(124, 321, 235, 53);
		contentPane.add(login);
		login.setLayout(null);
		
		lblLogin = new JLabel("LOGIN");
		lblLogin.setForeground(new Color(255, 255, 255));
		lblLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(10, 11, 215, 31);
		login.add(lblLogin);
		
		logo = new JLabel("");
		logo.setBackground(new Color(255, 255, 255));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBounds(124, 11, 235, 136);
		contentPane.add(logo);
		logo.setIcon(new ImageIcon(Images.bLogo));
		
		forgotPass = new JLabel("Forgot password?");
		forgotPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forgotPass.addMouseListener(new PropertiesListener(forgotPass) {
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ForgotPassword frame = new ForgotPassword();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});	
				dispose();
			}
		});
		
		JLabel lblTitle = new JLabel("ATTENDANCE SYSTEM");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitle.setBounds(124, 122, 235, 25);
		contentPane.add(lblTitle);
		forgotPass.setHorizontalAlignment(SwingConstants.CENTER);
		forgotPass.setForeground(new Color(65, 105, 225));
		forgotPass.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 12));
		forgotPass.setBounds(193, 385, 93, 26);
		contentPane.add(forgotPass);
		
		register = new JLabel("Create an account");
		register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		register.addMouseListener(new PropertiesListener(register) {
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Register frame = new Register();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});	
				dispose();
			}
		});
		register.setHorizontalAlignment(SwingConstants.LEFT);
		register.setForeground(new Color(65, 105, 225));
		register.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 12));
		register.setBounds(193, 410, 96, 26);
		contentPane.add(register);
		
		getRootPane().setDefaultButton(login);
		setResizable(false);
		setLocationRelativeTo(null);
		revalidate();
		repaint();

	}
	
}
