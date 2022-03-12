package main;
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
	//test
	private static final long serialVersionUID = 1L;
	
	private panelHome panelHome;
	private panelDashboard panelDashboard;
	private panelSettings panelSettings;
	private panelSkewl panelSkewl;
	private panelDepartment panelDepartment;
	private panelMembros panelMembros;
	private panelAttendance panelAttendance;
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
		panelSkewl = new panelSkewl();
		panelSkewl.setBounds(0, 0, 559, 539);
		panelDepartment = new panelDepartment();
		panelDepartment.setBounds(0, 0, 559, 539);
		panelMembros = new panelMembros();
		panelMembros.setBounds(0, 0, 559, 539);
		panelAttendance = new panelAttendance();
		panelAttendance.setBounds(0, 0, 559, 539);
		
		
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
		
		JPanel panelSchool = new JPanel();
		panelSchool.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelSchool.addMouseListener(new PropertiesListener(panelSchool) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelSkewl);
			}
			
		});
		panelSchool.setLayout(null);
		panelSchool.setBorder(null);
		panelSchool.setBackground(new Color(65, 105, 225));
		panelSchool.setBounds(0, 240, 205, 64);
		panel.add(panelSchool);
		
		JLabel lblSchool = new JLabel("School");
		lblSchool.setForeground(Color.WHITE);
		lblSchool.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblSchool.setBounds(54, 11, 141, 42);
		panelSchool.add(lblSchool);
		
		JLabel lblSkewl = new JLabel("");
		lblSkewl.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSkewl.setHorizontalAlignment(SwingConstants.CENTER);
		lblSkewl.setBounds(10, 11, 36, 42);
		lblSkewl.setIcon(new ImageIcon(Images.school));
		panelSchool.add(lblSkewl);
		
		JPanel panelDept = new JPanel();
		panelDept.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelDept.addMouseListener(new PropertiesListener(panelDept) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelDepartment);
			}
			
		});
		panelDept.setLayout(null);
		panelDept.setBorder(null);
		panelDept.setBackground(new Color(65, 105, 225));
		panelDept.setBounds(0, 305, 205, 64);
		panel.add(panelDept);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setForeground(Color.WHITE);
		lblDepartment.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblDepartment.setBounds(54, 11, 141, 42);
		panelDept.add(lblDepartment);
		
		JLabel lblDept = new JLabel("");
		lblDept.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDept.setHorizontalAlignment(SwingConstants.CENTER);
		lblDept.setBounds(10, 11, 36, 42);
		lblDept.setIcon(new ImageIcon(Images.department));
		panelDept.add(lblDept);
		
		JPanel panelAttend = new JPanel();
		panelAttend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelAttend.addMouseListener(new PropertiesListener(panelAttend) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelAttendance);
			}
			
		});
		panelAttend.setLayout(null);
		panelAttend.setBorder(null);
		panelAttend.setBackground(new Color(65, 105, 225));
		panelAttend.setBounds(0, 369, 205, 64);
		panel.add(panelAttend);
		
		JLabel lblAttendance = new JLabel("Attendance");
		lblAttendance.setForeground(Color.WHITE);
		lblAttendance.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblAttendance.setBounds(54, 11, 141, 42);
		panelAttend.add(lblAttendance);
		
		JLabel lblAttend = new JLabel("");
		lblAttend.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAttend.setHorizontalAlignment(SwingConstants.CENTER);
		lblAttend.setBounds(10, 11, 36, 42);
		lblAttend.setIcon(new ImageIcon(Images.logo2));
		panelAttend.add(lblAttend);
		
		JPanel panelMembers = new JPanel();
		panelMembers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelMembers.addMouseListener(new PropertiesListener(panelMembers) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelMembros);
			}
			
		});
		panelMembers.setLayout(null);
		panelMembers.setBorder(null);
		panelMembers.setBackground(new Color(65, 105, 225));
		panelMembers.setBounds(0, 433, 205, 64);
		panel.add(panelMembers);
		
		JLabel lblMembers = new JLabel("Members");
		lblMembers.setForeground(Color.WHITE);
		lblMembers.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblMembers.setBounds(54, 11, 141, 42);
		panelMembers.add(lblMembers);
		
		JLabel lblMems = new JLabel("");
		lblMems.setHorizontalTextPosition(SwingConstants.CENTER);
		lblMems.setHorizontalAlignment(SwingConstants.CENTER);
		lblMems.setBounds(10, 11, 36, 42);
		lblMems.setIcon(new ImageIcon(Images.members));
		panelMembers.add(lblMems);
		
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
		panelMainContent.add(panelSkewl);
		panelMainContent.add(panelDepartment);
		panelMainContent.add(panelMembros);
		panelMainContent.add(panelAttendance);
		
		menuClicked(panelHome);
		setResizable(false);
	}
	
	
	public void menuClicked(JPanel panel) {
		panelHome.setVisible(false);
		panelDashboard.setVisible(false);
		panelSettings.setVisible(false);
		panelSkewl.setVisible(false);
		panelDepartment.setVisible(false);
		panelMembros.setVisible(false);
		panelAttendance.setVisible(false);
		
		
		panel.setVisible(true);
		
	}
}
