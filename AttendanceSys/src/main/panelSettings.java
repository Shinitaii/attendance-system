package main;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.Cursor;
import java.awt.GridLayout;


public class panelSettings extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public panelProfileDisplay panelProfileDisplay;
	public panelAccountSetting panelAccountSetting;
	public panelChangePassSetting panelChangePassSetting;	
	
	public panelSettings() {
		setBackground(new Color(255, 255, 255));
		setBounds(new Rectangle(0, 0, 559, 539));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 559, 70);
		add(panel);
		
		JButton buttonEditPf = new JButton("Edit Profile");
		buttonEditPf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonEditPf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAccountSetting.txtUser.setText(Login.pubUsername);
				panelAccountSetting.txtFN.setText(Login.pubFN);
				panelAccountSetting.txtMN.setText(Login.pubMN);
				panelAccountSetting.txtLN.setText(Login.pubLN);
				Images.pfp(panelAccountSetting.lblpfp);
				menuClicked(panelAccountSetting);
			}
		});
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		buttonEditPf.addMouseListener(new PropertiesListener(buttonEditPf));
		panel.add(buttonEditPf);
		buttonEditPf.setLayout(null);
		
		JButton buttonChangePass = new JButton("Change Password");
		buttonChangePass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuClicked(panelChangePassSetting);
			}
		});
		buttonChangePass.addMouseListener(new PropertiesListener(buttonChangePass));
		buttonChangePass.setLayout(null);
		panel.add(buttonChangePass);
		
		if(Login.pubOccupation.equals("Teacher")) {
		JButton changeSubjects = new JButton("Change Subjects");
		changeSubjects.addActionListener(new TeacherAssignListener());
		changeSubjects.addMouseListener(new PropertiesListener(changeSubjects));
		panel.add(changeSubjects);
		}
		
		JButton leaveSchool = new JButton("Leave School");
		leaveSchool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to leave "+Login.pubSchoolName+"?","Warning!",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
						PreparedStatement checkMemberCount = conn.prepareStatement("select count(occupation) from userinfo where schoolname='"+Login.pubSchoolName+"' and inviteCodeOfSchool='"+Login.pubInviteCode+"'");
						ResultSet count = checkMemberCount.executeQuery();
						if(count.next()) {
							int num = count.getInt("count(occupation)");
							if(num == 1) {
								int choose = JOptionPane.showConfirmDialog(null, "You're the only one left in the school, leaving will permanently delete the school.\r\nDo you proceed?", "Warning!", JOptionPane.YES_NO_OPTION);
								if(choose == JOptionPane.YES_OPTION) {
									PreparedStatement editMember = conn.prepareStatement("update userinfo set hasASchool=false, schoolname=null, inviteCodeOfSchool=null");
									editMember.executeUpdate();
									PreparedStatement deleteSchool = conn.prepareStatement("delete from schoolinfo where schoolname='"+Login.pubSchoolName+"' and inviteCodeOfSchool='"+Login.pubInviteCode+"'");
									deleteSchool.executeUpdate();
									JOptionPane.showMessageDialog(null, "You have left "+Login.pubSchoolName+"  and it is permanently removed!");
									((Window) getRootPane().getParent()).dispose();
									SelectSchool dialog = new SelectSchool();
									dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
									dialog.setVisible(true);
								}
							} else {
								if(Login.pubOccupation.equals("Admin")) {
									PreparedStatement checkAdminCount = conn.prepareStatement("select count(*) from userinfo where occupation='Admin' and schoolname='"+Login.pubSchoolName+"' and inviteCodeOfSchool='"+Login.pubInviteCode+"'");
									ResultSet sqlResult = checkAdminCount.executeQuery();
									if(sqlResult.next()) {
										int obtainedNum = sqlResult.getInt("count(*)");
										if(obtainedNum > 1) {
											executeLeave();
										} else {
											JOptionPane.showMessageDialog(null, "You have to give someone an Admin role first before you leave.\r\nYou're the only admin in this school.");
										}
									}
								} else if(Login.pubOccupation.equals("Owner")){
									JOptionPane.showMessageDialog(null, "You have to give someone the ownership to someone else first.");
								} else {
									executeLeave();
								}
							}
						}			
					} catch(SQLException sql) {
						sql.printStackTrace();
					}
				}
			}
		});
		leaveSchool.addMouseListener(new PropertiesListener(leaveSchool));
		panel.add(leaveSchool);
		
		JPanel panelMain = new JPanel();
		panelMain.setBounds(10, 80, 539, 450);
		add(panelMain);
		panelMain.setLayout(null);
		
		panelProfileDisplay = new panelProfileDisplay();
		panelProfileDisplay.setBounds(0,0,539,450);
		panelMain.add(panelProfileDisplay);
		panelProfileDisplay.setLayout(null);
		
		panelAccountSetting = new panelAccountSetting();
		panelAccountSetting.setBounds(0, 0, 539, 450);
		panelMain.add(panelAccountSetting);
		panelAccountSetting.setLayout(null);
		
		panelChangePassSetting = new panelChangePassSetting();
		panelChangePassSetting.setBounds(0,0,539,450);
		panelMain.add(panelChangePassSetting);
		panelChangePassSetting.setLayout(null);
	}
	
	public void menuClicked(JPanel panel) {
		panelProfileDisplay.setVisible(false);
		panelAccountSetting.setVisible(false);
		panelChangePassSetting.setVisible(false);
		
		panel.setVisible(true);
	}
	
	private void executeLeave() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement deleteExisting = conn.prepareStatement("delete from teacherassignedinfo where teachername=?");
			deleteExisting.setString(1, Login.pubFullName);
			PreparedStatement getStatement = conn.prepareStatement("update userinfo set occupation='Student', schoolname=null, hasASchool=false, inviteCodeOfSchool=null , departmentname=null , hasADept=false , sectionname=null , hasASec=false where userid='"+Login.pubUID+"'");
			deleteExisting.executeUpdate();
			int leaving = getStatement.executeUpdate();
			if(leaving == 1) {
				
				((Window) getRootPane().getParent()).dispose();
				JOptionPane.showMessageDialog(null, "You successfully left "+Login.pubSchoolName+"!");
				SelectSchool school = new SelectSchool();
				school.setVisible(true);
			}
		} catch(SQLException sql) {
			sql.printStackTrace();
		}
	}
}
