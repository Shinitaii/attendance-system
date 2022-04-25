package main;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JTextField;

public class panelAccountSetting extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtUser;
	private JTextField txtFN;
	private JTextField txtLN;
	private JTextField txtMN;
	private JLabel lblpfp;
	AdminMenu AdminMenu;
	panelProfileDisplay panelProfileDisplay;
	private int uid = Integer.valueOf(Login.pubUID);
	String path;
	boolean photoSizeCheck = false;
	FileInputStream isPhoto;

	public panelAccountSetting() {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 540, 539);
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setLayout(null);
		
		JPanel panelPFP = new JPanel();
		panelPFP.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelPFP.setBackground(new Color(255, 255, 255));
		panelPFP.setBounds(334, 71, 150, 150);
		add(panelPFP);
		panelPFP.setLayout(null);
		
		lblpfp = new JLabel("");
		lblpfp.setBorder(new LineBorder(new Color(65, 105, 225)));
		lblpfp.setBounds(0, 0, 150, 150);
		panelPFP.add(lblpfp);
		lblpfp.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton browseButton = new JButton("Browse Image");
		browseButton.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		browseButton.setBorder(null);
		browseButton.addActionListener(new browseAction(lblpfp));
		browseButton.setForeground(new Color(255, 255, 255));
		browseButton.setBackground(new Color(65, 105, 225));
		browseButton.setBounds(344, 232, 131, 30);
		add(browseButton);
		
		JLabel lblNewLabel_1 = new JLabel("Allowed JPG or PNG, Max size of 30KB");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(0, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(323, 266, 175, 14);
		add(lblNewLabel_1);
		
		JPanel panelFN = new JPanel();
		panelFN.setBackground(new Color(255, 255, 255));
		panelFN.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelFN.setBounds(10, 149, 265, 40);
		add(panelFN);
		panelFN.setLayout(null);
		
		txtFN = new JTextField();
		txtFN.setForeground(new Color(65, 105, 225));
		txtFN.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtFN.setBorder(null);
		txtFN.setSelectionColor(new Color(65, 105, 225));
		txtFN.setColumns(10);
		txtFN.setBounds(10, 7, 245, 25);
		panelFN.add(txtFN);
		
		JPanel panelLN = new JPanel();
		panelLN.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelLN.setBackground(Color.WHITE);
		panelLN.setBounds(10, 230, 265, 40);
		add(panelLN);
		panelLN.setLayout(null);
		
		txtLN = new JTextField();
		txtLN.setForeground(new Color(65, 105, 225));
		txtLN.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtLN.setBorder(null);
		txtLN.setSelectionColor(new Color(65, 105, 225));
		txtLN.setColumns(10);
		txtLN.setBounds(10, 7, 245, 25);
		panelLN.add(txtLN);
		
		JPanel panelMN = new JPanel();
		panelMN.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelMN.setBackground(Color.WHITE);
		panelMN.setBounds(10, 310, 265, 40);
		add(panelMN);
		panelMN.setLayout(null);
		
		txtMN = new JTextField();
		txtMN.setForeground(new Color(65, 105, 225));
		txtMN.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtMN.setBorder(null);
		txtMN.setSelectionColor(new Color(65, 105, 225));
		txtMN.setColumns(10);
		txtMN.setBounds(10, 7, 245, 25);
		panelMN.add(txtMN);
		
		JLabel lblFname = new JLabel("First Name :");
		lblFname.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblFname.setForeground(new Color(65, 105, 225));
		lblFname.setBounds(10, 120, 150, 29);
		add(lblFname);
		
		JLabel lblLname = new JLabel("Last Name :");
		lblLname.setForeground(new Color(65, 105, 225));
		lblLname.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblLname.setBounds(10, 200, 150, 29);
		add(lblLname);
		
		JLabel lblMname = new JLabel("Middle Name :");
		lblMname.setForeground(new Color(65, 105, 225));
		lblMname.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblMname.setBounds(10, 281, 150, 29);
		add(lblMname);
		
		JLabel userid = new JLabel("UID : "+uid);
		userid.setFont(new Font("Yu Gothic UI", Font.ITALIC, 20));
		userid.setBounds(323, 273, 151, 40);
		add(userid);
		
		JPanel panelUserN = new JPanel();
		panelUserN.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelUserN.setBackground(Color.WHITE);
		panelUserN.setBounds(10, 69, 265, 40);
		add(panelUserN);
		panelUserN.setLayout(null);
		
		txtUser = new JTextField();
		txtUser.setBorder(null);
		txtUser.setSelectionColor(new Color(65, 105, 225));
		txtUser.setForeground(new Color(65, 105, 225));
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUser.setBounds(10, 7, 245, 25);
		panelUserN.add(txtUser);
		txtUser.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setForeground(new Color(65, 105, 225));
		lblUsername.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblUsername.setBounds(10, 39, 224, 29);
		add(lblUsername);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setForeground(Color.WHITE);
		btnReset.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		btnReset.setBorder(null);
		btnReset.setBackground(new Color(65, 105, 225));
		btnReset.setBounds(310, 11, 90, 30);
		btnReset.addMouseListener(new PropertiesListener(btnReset));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblpfp.setIcon(null);
			}
		});
		add(btnReset);
		
		JButton btnSave = new JButton("Save");
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		btnSave.setBorder(null);
		btnSave.setBackground(new Color(65, 105, 225));
		btnSave.setBounds(415, 11, 90, 30);
		btnSave.addMouseListener(new PropertiesListener(btnSave));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JPasswordField passField = new JPasswordField(20);
					char[] pass = passField.getPassword();
					String databasePass = "", obtainedPass = String.valueOf(pass), username = txtUser.getText(), firstname = txtFN.getText(), middlename = txtMN.getText(), lastname = txtLN.getText();
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem","root","Keqingisbestgirl");
					Box box = Box.createHorizontalBox();
					box.add(passField);
					int button = JOptionPane.showConfirmDialog(null, box, "Input your password.", JOptionPane.OK_CANCEL_OPTION);
					if(button == JOptionPane.OK_OPTION) {
						obtainedPass = String.valueOf(pass);
						PreparedStatement checkPass = conn.prepareStatement("select pass from userInfo where pass='"+obtainedPass+"'");
						ResultSet checkingPass = checkPass.executeQuery();
						while(checkingPass.next()) {
							databasePass = checkingPass.getString("pass");
						}
						if(databasePass.equals(obtainedPass)) {
							FileInputStream isPhoto = null;
							try {
								String photo = browseAction.pubPath;
								isPhoto = new FileInputStream(photo);
							} catch (Exception photo) {
								photo.printStackTrace();
							}
							
							PreparedStatement saveCredentials = conn.prepareStatement("update userInfo set username='"+username+"', firstname='"+firstname+"', middlename='"+middlename+"', lastname='"+lastname+"', profilepicture=? where userid ='"+uid+"'");
							saveCredentials.setBinaryStream(1, isPhoto);
							int saving = saveCredentials.executeUpdate();
							if(saving == 1) {
								JOptionPane.showMessageDialog(null, "Credentials are now updated and saved!");
								
							} else {
								JOptionPane.showMessageDialog(null, "Failed to update credentials!");
							}
							checkPass.close();
							saveCredentials.close();
						} else {
							JOptionPane.showMessageDialog(null, "Incorrect password!");
						}
					}
					conn.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
			}
		});
		add(btnSave);
		
		JLabel Dept = new JLabel("Department : ");
		Dept.setFont(new Font("Yu Gothic UI", Font.ITALIC, 20));
		Dept.setBounds(324, 310, 151, 40);
		add(Dept);
		
		JLabel Occup = new JLabel("Occupation :");
		Occup.setFont(new Font("Yu Gothic UI", Font.ITALIC, 20));
		Occup.setBounds(323, 350, 151, 40);
		add(Occup);
		
		
	
		

		
	}
}
