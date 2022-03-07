import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;


public class AdminMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private panelHome panelHome;
	private panelDashboard panelDashboard;
	private panelSettings panelSettings;
	private JPanel contentPane;
	private String username = Login.pubUsername, uid = Login.pubUID;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					AdminMenu frame = new AdminMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdminMenu() {
		setIconImage(Images.bLogo);
		setTitle("Attendance");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		panelHome = new panelHome();
		panelHome.setBounds(0, 0, 559, 539);
		panelDashboard = new panelDashboard();
		panelDashboard.setBounds(0, 0, 559, 539);
		panelSettings = new panelSettings();
		panelSettings.setBounds(0, 0, 559, 539);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225).darker()));
		panel.setBackground(new Color(65, 105, 225));
		panel.setBounds(0, 0, 205, 561);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBorder(null);
		lblLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelHome);
			}
		});
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblLogo.setBounds(10, 11, 185, 155);
		panel.add(lblLogo);
		lblLogo.setIcon(new ImageIcon(Images.wLogo));
		
		JPanel panelDashB = new JPanel();
		panelDashB.setBorder(null);
		panelDashB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelDashB.addMouseListener(new PropertiesListener(panelDashB) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelDashboard);
			}
			
		});
		panelDashB.setBackground(new Color(65, 105, 225));
		panelDashB.setBounds(0, 177, 205, 64);
		panel.add(panelDashB);
		panelDashB.setLayout(null);
		
		JLabel lblDashBoard = new JLabel("Dashboard");
		lblDashBoard.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblDashBoard.setForeground(new Color(255, 255, 255));
		lblDashBoard.setBounds(54, 11, 141, 42);
		panelDashB.add(lblDashBoard);
		
		JLabel lblDashb = new JLabel("");
		lblDashb.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDashb.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashb.setBounds(10, 11, 36, 42);
		panelDashB.add(lblDashb);
		lblDashb.setIcon(new ImageIcon(Images.dashboard));
		
		JPanel panelUserInfo = new JPanel();
		panelUserInfo.setLayout(null);
		panelUserInfo.setBorder(new LineBorder(new Color(65, 105, 225).darker()));
		panelUserInfo.setBackground(new Color(65, 105, 225));
		panelUserInfo.setBounds(0, 497, 205, 64);
		panel.add(panelUserInfo);
		
		JLabel lblUsername = new JLabel(username);
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 14));
		lblUsername.setBounds(2, 0, 98, 21);
		panelUserInfo.add(lblUsername);
		
		JLabel lblUID = new JLabel("UID: "+uid);
		lblUID.setBounds(2, 21, 98, 21);
		panelUserInfo.add(lblUID);
		lblUID.setForeground(Color.WHITE);
		lblUID.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 14));
		lblUID.setHorizontalTextPosition(SwingConstants.CENTER);
		lblUID.setHorizontalAlignment(SwingConstants.LEFT);
		
		JPanel panelSett = new JPanel();
		panelSett.setBounds(100, 0, 54, 64);
		panelUserInfo.add(panelSett);
		panelSett.setBorder(new LineBorder(new Color(65, 105, 225).darker()));
		panelSett.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelSett.addMouseListener(new PropertiesListener(panelSett) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelSettings);
			}
		});
		panelSett.setBackground(new Color(65, 105, 225));
		panelSett.setLayout(null);
		
		JLabel lblSetting = new JLabel("");
		lblSetting.setBounds(10, 11, 34, 42);
		panelSett.add(lblSetting);
		lblSetting.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSetting.setHorizontalAlignment(SwingConstants.CENTER);
		lblSetting.setIcon(new ImageIcon(Images.settings));
		
		JPanel panelSignOut = new JPanel();
		panelSignOut.addMouseListener(new PropertiesListener(panelSignOut) {
			
			public void mouseClicked(MouseEvent e) {
				int yes = JOptionPane.showConfirmDialog(null, "Are you sure?", "Sign out", JOptionPane.YES_NO_OPTION);
				if(yes == JOptionPane.YES_OPTION) {
					EventQueue.invokeLater(new Runnable() {
						public void run () {
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
			}
		});
		panelSignOut.setBounds(154, 0, 51, 64);
		panelUserInfo.add(panelSignOut);
		panelSignOut.setLayout(null);
		panelSignOut.setBorder(new LineBorder(new Color(65, 105, 225).darker()));
		panelSignOut.setBackground(new Color(65, 105, 225));
		
		JLabel lblSignOut = new JLabel("");
		lblSignOut.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSignOut.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignOut.setBounds(10, 11, 30, 42);
		lblSignOut.setIcon(new ImageIcon(Images.signoutred));
		panelSignOut.add(lblSignOut);
		
		JPanel panelMainContent = new JPanel();
		panelMainContent.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelMainContent.setBackground(new Color(255, 255, 255));
		panelMainContent.setBounds(215, 11, 559, 539);
		contentPane.add(panelMainContent);
		panelMainContent.setLayout(null);
		
		panelMainContent.add(panelHome);
		panelMainContent.add(panelDashboard);
		panelMainContent.add(panelSettings);
		
		menuClicked(panelHome);
		setResizable(false);
	}
	
	
	public void menuClicked(JPanel panel) {
		panelHome.setVisible(false);
		panelDashboard.setVisible(false);
		panelSettings.setVisible(false);
		
		panel.setVisible(true);
		
	}
}

