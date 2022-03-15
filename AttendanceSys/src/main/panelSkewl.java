package main;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class panelSkewl extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String school;
	static JLabel lbl;
	panelSelectedSchool panelSelectedSchool;
	/**
	 * Create the panel.
	 */
	public panelSkewl() {
		setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setBackground(new Color(255, 255, 255));
		
		setBounds(0 , 0, 559, 539);
		setLayout(null);
		
		JPanel SchoolSelection = new JPanel();
		SchoolSelection.setBorder(new LineBorder(new Color(65, 105, 225)));
		SchoolSelection.setBackground(new Color(255, 255, 255));
		SchoolSelection.setBounds(10, 72, 539, 456);
		add(SchoolSelection);
		
		JPanel ButtonSelection = new JPanel();
		ButtonSelection.setBackground(new Color(255, 255, 255));
		ButtonSelection.setBounds(10, 11, 539, 50);
		ButtonSelection.setLayout(null);
		add(ButtonSelection);
		SchoolSelection.setLayout(null);
		
		lbl = new JLabel("You currently do not have a school attended. Ask your teacher for an invitation or create a new one.");
		lbl.setForeground(new Color(65, 105, 225));
		lbl.setBounds(23, 5, 493, 16);
		lbl.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 12));
		SchoolSelection.add(lbl);
		
		panelSelectedSchool = new panelSelectedSchool();
		panelSelectedSchool.setBounds(0,0,539,456);
		
		JButton inviteButton = new JButton("Enter Invite Code");
		inviteButton.setBorder(null);
		inviteButton.addMouseListener(new PropertiesListener(inviteButton) {
			public void mouseClicked(MouseEvent e) {
				String inviteCode;
				String obtainedInviteCode = "";
				try {
					String obtainedSchool = "";
					inviteCode = JOptionPane.showInputDialog(null, "Enter Invite Code:");
					if(inviteCode.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Input code!");
					} else {
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem","root","Keqingisbestgirl");
						PreparedStatement checkInvite = conn.prepareStatement("select schoolid, schoolname from schoolInfo where schoolid='"+inviteCode+"'");
						ResultSet obtainedInvite = checkInvite.executeQuery();
						while(obtainedInvite.next()) {
							obtainedInviteCode = obtainedInvite.getString("schoolid");
							obtainedSchool = obtainedInvite.getString("schoolname");
						}
						if(inviteCode.equals(obtainedInviteCode)) {
							JOptionPane.showMessageDialog(null, "You have joined to "+obtainedSchool+"! Wait for any of the teachers or admins to approve you.");
						} else {
							JOptionPane.showMessageDialog(null, "Invite Code does not exist!");
						}
					}
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
			}
		});
		inviteButton.setBounds(0, 0, 170, 50);
		ButtonSelection.add(inviteButton);
		
		JButton CreateSchool = new JButton("Create School");
		CreateSchool.setBorder(null);
		CreateSchool.addMouseListener(new PropertiesListener(CreateSchool) {
			@Override
			public void mouseClicked(MouseEvent e) {
				try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem","root","Keqingisbestgirl")) {
					school = JOptionPane.showInputDialog(null, "Enter School Name:");
					String obtainedSchool = school;
					String obtainedSchoolID = "";
					if(obtainedSchool.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Input name!");
					} else {
						String checkedSameSchoolName = "";
						PreparedStatement checkingSameSchoolName = conn.prepareStatement("select schoolname from schoolInfo where schoolname='"+obtainedSchool+"'");
						int checking = checkingSameSchoolName.executeUpdate();
						if(checking == 1) {
							if(obtainedSchool.equals(checkedSameSchoolName)) {
								JOptionPane.showMessageDialog(null, "School already exists!");
							} else {
								PreparedStatement addSchool = conn.prepareStatement("insert into schoolInfo (schoolname, creator) values (?, ?)");
								addSchool.setString(1, school);
								addSchool.setString(2, Login.pubUID);
								int addedSchool = addSchool.executeUpdate();
								if(addedSchool == 1) {
									JOptionPane.showMessageDialog(null, "School added!");
									PreparedStatement checkSchoolID = conn.prepareStatement("select schoolid from schoolInfo where schoolname='"+obtainedSchool+"'");
									ResultSet checkedSchool = checkSchoolID.executeQuery();
									if(checkedSchool.next()) {
										obtainedSchoolID = checkedSchool.getString("schoolid");
									}
									PreparedStatement ownership = conn.prepareStatement("insert into inThatSchool (schoolid, schoolname, userid) select schoolid, schoolname, userid from schoolInfo join userInfo on userid where userid="+Login.pubUID+" and schoolid="+obtainedSchoolID);
									ownership.executeUpdate();
									PreparedStatement ownership2 = conn.prepareStatement("update inThatSchool set inThatSchool = 1 where userid ="+Login.pubUID+" and schoolname ='"+obtainedSchool+"'");
									ownership2.executeUpdate();
									
									conn.close();
									checkingSameSchoolName.close();
									addSchool.close();
									checkSchoolID.close();
									ownership.close();
									ownership2.close();
								} else {
									JOptionPane.showMessageDialog(null, "Failed to add school!");
								}
							}
						}
					}
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
			}
		});
		CreateSchool.setBounds(180, 0, 170, 50);
		ButtonSelection.add(CreateSchool);
		
		SchoolSelection.add(panelSelectedSchool);
		
		menuClicked(panelSelectedSchool);
		
		revalidate();
		repaint();
	}
	
	public void menuClicked(JPanel panel) {
		panel.setVisible(true);
	}
}
