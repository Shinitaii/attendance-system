package main;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
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
	public JTextField txtUser, txtFN, txtLN, txtMN;
	public JLabel lblpfp;
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
		
		JLabel lblNewLabel_1 = new JLabel("Allowed JPG or PNG, Max size of 50MB");
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
		panelLN.setBounds(10, 284, 265, 40);
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
		panelMN.setBounds(10, 213, 265, 40);
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
		lblLname.setBounds(10, 256, 150, 29);
		add(lblLname);
		
		JLabel lblMname = new JLabel("Middle Name :");
		lblMname.setForeground(new Color(65, 105, 225));
		lblMname.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblMname.setBounds(10, 186, 150, 29);
		add(lblMname);
		
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
		
		JButton btnSave = new JButton("Save");
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		btnSave.setBorder(null);
		btnSave.setBackground(new Color(65, 105, 225));
		btnSave.setBounds(415, 11, 90, 30);
		btnSave.addMouseListener(new PropertiesListener(btnSave));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
					JPanel panel = new JPanel();
					JLabel label = new JLabel("Input your password: ");
					JPasswordField passField = new JPasswordField(20);
					panel.add(label);
					panel.add(passField);
					String username = txtUser.getText(), firstname = txtFN.getText(), middlename = txtMN.getText(), lastname = txtLN.getText();
					int button = JOptionPane.showConfirmDialog(null, panel, "Input pass",JOptionPane.OK_CANCEL_OPTION);
					if(button == JOptionPane.OK_OPTION) {
						char[] getPass = passField.getPassword();
						String obtainedPass = String.valueOf(getPass);
						byte[] obtainedSalt = null;
						PreparedStatement checkSalt = conn.prepareStatement("select saltpass from userinfo where userid='"+Login.pubUID+"'");
						ResultSet checkingSalt = checkSalt.executeQuery();
						if(checkingSalt.next()) {
							obtainedSalt = checkingSalt.getBytes("saltpass");
						}
						PreparedStatement checkPass = conn.prepareStatement("select pass from userinfo where pass='"+HashedPassword.existingSalt(obtainedPass, obtainedSalt)+"' and userid='"+Login.pubUID+"'");
						ResultSet checkingPass = checkPass.executeQuery();
						if(checkingPass.next()) {
							FileInputStream isPhoto = null;
							try {
								String photo = browseAction.pubPath;
								if(photo != null) {
									isPhoto = new FileInputStream(photo);
								}
							} catch (Exception photo) {
								photo.printStackTrace();
							}
							PreparedStatement saveCredentials;
							String withPhoto = "update userinfo set username='"+username+"', firstname='"+firstname+"', middlename='"+middlename+"', lastname='"+lastname+"', profilepicture=? where userid ='"+uid+"'";
							String withoutPhoto = "update userinfo set username='"+username+"', firstname='"+firstname+"', middlename='"+middlename+"', lastname='"+lastname+"' where userid ='"+uid+"'";
							if(isPhoto == null) {
								saveCredentials = conn.prepareStatement(withoutPhoto);
							} else {
								saveCredentials = conn.prepareStatement(withPhoto);
								saveCredentials.setBinaryStream(1, isPhoto);
							}
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
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.panelSettings.menuClicked(MainMenu.panelSettings.panelProfileDisplay);
			}
		});
		cancelButton.addMouseListener(new PropertiesListener(cancelButton));
		cancelButton.setBounds(10, 11, 89, 23);
		add(cancelButton);

	}
}
