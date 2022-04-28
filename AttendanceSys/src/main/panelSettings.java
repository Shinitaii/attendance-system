package main;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Cursor;

public class panelSettings extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	panelProfileDisplay panelProfileDisplay;
	panelAccountSetting panelAccountSetting;
	panelChangePassSetting panelChangePassSetting;	
	
	MainMenu MainMenu;
	
	public panelSettings() {
		setBackground(new Color(255, 255, 255));
		setBounds(new Rectangle(0, 0, 559, 439));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 559, 70);
		add(panel);
		panel.setLayout(null);
		
		JPanel panelEditPf = new JPanel();
		panelEditPf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelEditPf.setBackground(new Color(65, 105, 225));
		panelEditPf.addMouseListener(new PropertiesListener(panelEditPf) {
			public void mouseClicked(MouseEvent e) {
				panelAccountSetting.txtUser.setText(Login.pubUsername);
				panelAccountSetting.txtFN.setText(Login.pubFN);
				panelAccountSetting.txtMN.setText(Login.pubMN);
				panelAccountSetting.txtLN.setText(Login.pubLN);
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user, MySQLConnectivity.pass)) {
					PreparedStatement getPhoto = conn.prepareStatement("select profilePicture from userInfo where userid='"+Login.pubUID+"'");
					ResultSet get = getPhoto.executeQuery();
					Blob photo = null;
					while(get.next()) {
						photo = get.getBlob("profilePicture");
						
					}
					byte[] imagebytes = photo.getBytes(1, (int) photo.length());
					BufferedImage image = ImageIO.read(new ByteArrayInputStream(imagebytes));
					panelAccountSetting.lblpfp.setIcon(new ImageIcon(image));
					conn.close();
				} catch (Exception sql) {
					sql.printStackTrace();
				}
				menuClicked(panelAccountSetting);
			}
		});
		
		panelEditPf.setBounds(10, 10, 150, 50);
		panel.add(panelEditPf);
		panelEditPf.setLayout(null);
		
		JLabel lblEditPf = new JLabel("Edit Profile");
		lblEditPf.setForeground(new Color(255, 255, 255));
		lblEditPf.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblEditPf.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditPf.setBounds(10, 11, 130, 28);
		panelEditPf.add(lblEditPf);
		
		JPanel panelChangePass = new JPanel();
		panelChangePass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelChangePass.setBackground(new Color(65, 105, 225));
		panelChangePass.addMouseListener(new PropertiesListener(panelChangePass) {
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelChangePassSetting);
			}
		});
		panelChangePass.setLayout(null);
		panelChangePass.setBounds(170, 10, 150, 50);
		panel.add(panelChangePass);
		
		JLabel lblChangePass = new JLabel("Change Password");
		lblChangePass.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangePass.setForeground(Color.WHITE);
		lblChangePass.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblChangePass.setBounds(10, 11, 130, 28);
		panelChangePass.add(lblChangePass);
		
		JPanel panelMain = new JPanel();
		panelMain.setBounds(10, 80, 539, 450);
		add(panelMain);
		panelMain.setLayout(null);
		
		panelAccountSetting = new panelAccountSetting();
		panelAccountSetting.setBounds(0, 0, 539, 450);
		panelMain.add(panelAccountSetting);
		panelAccountSetting.setLayout(null);
		
		panelChangePassSetting = new panelChangePassSetting();
		panelChangePassSetting.setBounds(0,0,539,450);
		panelMain.add(panelChangePassSetting);
		panelChangePassSetting.setLayout(null);
		
		panelProfileDisplay = new panelProfileDisplay();
		panelProfileDisplay.setBounds(0,0,539,450);
		panelMain.add(panelProfileDisplay);
		panelProfileDisplay.setLayout(null);
		
		menuClicked(panelProfileDisplay);
	}
	
	public void menuClicked(JPanel panel) {
		panelProfileDisplay.setVisible(false);
		panelAccountSetting.setVisible(false);
		panelChangePassSetting.setVisible(false);
		
		panel.setVisible(true);
		
	}
}
