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


public class MainMenu extends JFrame {
	//test
	private static final long serialVersionUID = 1L;
	
	public static panelHome panelHome;
	public static panelSubjects panelSubjects;
	public static SubjectSelectDepartment SubjectSelectDepartment;
	private static panelSettings panelSettings;
	public static panelDepartment panelDepartment;
	public static panelMembros panelMembros;
	public static AttendanceSelectDepartment AttendanceSelectDepartment;
	public static AttendanceSelectSection AttendanceSelectSection;
	public static AttendanceSelectSubject AttendanceSelectSubject;
	public static panelAttendance panelAttendance;
	public static panelSections panelSections;
	public static panelSectionMembers panelSectionMembers;
	public static Records records;
	public static studentRecords studentRecords;
	public static TeacherAssignDept TeacherAssignDept;
	private JPanel contentPane;
	private String username = Login.pubUsername,uid = Login.pubUID;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainMenu() {
		setIconImage(Images.bLogo);
		if(Login.pubOccupation.equals("Admin")) {
			setTitle("Attendance: Admin");
		} else if (Login.pubOccupation.equals("Teacher")) {
			setTitle("Attendance: Teacher");
		} else {
			setTitle("Attendance: Student");
		}
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
		panelSubjects = new panelSubjects();
		panelSubjects.setBounds(0, 0, 559, 539);
		SubjectSelectDepartment = new SubjectSelectDepartment();
		SubjectSelectDepartment.setBounds(0,0,559,539);
		panelSettings = new panelSettings();
		panelSettings.setBounds(0, 0, 559, 539);
		panelDepartment = new panelDepartment();
		panelDepartment.setBounds(0, 0, 559, 539);
		panelMembros = new panelMembros();
		panelMembros.setBounds(0, 0, 559, 539);
		AttendanceSelectDepartment = new AttendanceSelectDepartment();
		AttendanceSelectDepartment.setBounds(0,0,559,539);		
		AttendanceSelectSection = new AttendanceSelectSection();
		AttendanceSelectSection.setBounds(0,0,559,539);
		AttendanceSelectSubject = new AttendanceSelectSubject();
		AttendanceSelectSubject.setBounds(0,0,559,539);
		panelAttendance = new panelAttendance();
		panelAttendance.setBounds(0, 0, 559, 539);
		panelSections = new panelSections();
		panelSections.setBounds(0, 0, 559, 539);
		panelSectionMembers = new panelSectionMembers();
		panelSectionMembers.setBounds(0,0,559,539);
		records = new Records();
		records.setBounds(0,0,559,539);
		studentRecords = new studentRecords();
		studentRecords.setBounds(0,0,559,539);
		TeacherAssignDept = new TeacherAssignDept();
		TeacherAssignDept.setBounds(0,0,559,539);
		
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
		
		JPanel panelDept = new JPanel();
		panelDept.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelDept.addMouseListener(new PropertiesListener(panelDept) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelDepartment);
				panelSections.buttonNames.clear();
				panelSections.listSecNames.clear();
				panelSections.sectionScreen.removeAll();
				if(!Login.pubOccupation.equals("Teacher")) {
					panelDepartment.execute();
				} else {
					panelDepartment.executeForTeachers();
				}
				revalidate();
				repaint();
			}
			
		});
		panelDept.setLayout(null);
		panelDept.setBorder(new LineBorder(new Color(65, 105, 225).darker()));
		panelDept.setBackground(new Color(65, 105, 225));
		panelDept.setBounds(0, 166, 205, 64);
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
			public void mouseClicked(MouseEvent e) {
				menuClicked(AttendanceSelectDepartment);
				panelAttendance.buttonNames.clear();
				panelAttendance.listRecordNames.clear();
				panelAttendance.mainScreen.removeAll();
				if(!Login.pubOccupation.equals("Teacher")) {
					AttendanceSelectDepartment.execute();
				} else {
					AttendanceSelectDepartment.executeForTeachers();
				}
				revalidate();
				repaint();
			}
		});
		panelAttend.setLayout(null);
		panelAttend.setBorder(new LineBorder(new Color(65, 105, 225).darker()));
		panelAttend.setBackground(new Color(65, 105, 225));
		panelAttend.setBounds(0, 230, 205, 64);
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
		panelMembers.setBorder(new LineBorder(new Color(65, 105, 225).darker()));
		panelMembers.setBackground(new Color(65, 105, 225));
		panelMembers.setBounds(0, 357, 205, 64);
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
		
		JLabel lblSchoolName = new JLabel(Login.pubSchoolName);
		lblSchoolName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSchoolName.setHorizontalAlignment(SwingConstants.LEFT);
		lblSchoolName.setForeground(Color.WHITE);
		lblSchoolName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 14));
		lblSchoolName.setBounds(2, 43, 98, 21);
		panelUserInfo.add(lblSchoolName);
		
		JPanel panelSched = new JPanel();
		panelSched.setLayout(null);
		panelSched.setBorder(new LineBorder(new Color(65, 105, 225).darker()));
		panelSched.setBackground(new Color(65, 105, 225));
		panelSched.setBounds(0, 293, 205, 64);
		panelSched.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelSched.addMouseListener(new PropertiesListener(panelSched) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(SubjectSelectDepartment);
				if(!Login.pubOccupation.equals("Teacher")) {
					SubjectSelectDepartment.execute();
				} else {
					SubjectSelectDepartment.executeForTeachers();
				}
			}
			
		});
		panel.add(panelSched);
		
		JLabel lblSubject = new JLabel("Subject");
		lblSubject.setForeground(Color.WHITE);
		lblSubject.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblSubject.setBounds(54, 11, 141, 42);
		panelSched.add(lblSubject);
		
		JLabel lblSched = new JLabel("");
		lblSched.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSched.setHorizontalAlignment(SwingConstants.CENTER);
		lblSched.setBounds(10, 11, 36, 42);
		lblSched.setIcon(new ImageIcon(Images.subject));
		panelSched.add(lblSched);
		
		JPanel panelMainContent = new JPanel();
		panelMainContent.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelMainContent.setBackground(new Color(255, 255, 255));
		panelMainContent.setBounds(215, 11, 559, 539);
		contentPane.add(panelMainContent);
		panelMainContent.setLayout(null);
		
		panelMainContent.add(panelHome);
		panelMainContent.add(panelSubjects);
		panelMainContent.add(SubjectSelectDepartment);
		panelMainContent.add(panelSettings);
		panelMainContent.add(panelDepartment);
		panelMainContent.add(panelMembros);
		panelMainContent.add(AttendanceSelectDepartment);
		panelMainContent.add(AttendanceSelectSection);
		panelMainContent.add(AttendanceSelectSubject);
		panelMainContent.add(panelAttendance);
		panelMainContent.add(panelSections);
		panelMainContent.add(panelSectionMembers);
		panelMainContent.add(records);
		panelMainContent.add(studentRecords);
		panelMainContent.add(TeacherAssignDept);
		
		menuClicked(panelHome);
		setResizable(false);
	}
	
	
	public static void menuClicked(JPanel panel) {
		panelHome.setVisible(false);
		panelSubjects.setVisible(false);
		SubjectSelectDepartment.setVisible(false);
		panelSettings.setVisible(false);
		panelDepartment.setVisible(false);
		panelMembros.setVisible(false);
		AttendanceSelectDepartment.setVisible(false);
		AttendanceSelectSection.setVisible(false);
		AttendanceSelectSubject.setVisible(false);
		panelAttendance.setVisible(false);
		panelSections.setVisible(false);
		panelSectionMembers.setVisible(false);
		records.setVisible(false);
		studentRecords.setVisible(false);
		TeacherAssignDept.setVisible(false);
		
		panel.setVisible(true);		
	}
}

