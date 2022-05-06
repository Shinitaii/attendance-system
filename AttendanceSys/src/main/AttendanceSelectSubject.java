package main;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class AttendanceSelectSubject extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JButton> buttonNames = new ArrayList<JButton>();
	private List<String> listSecNames = new ArrayList<String>();
	private int count = 0;
	JPanel subjectScreen;
	String obtainedDept, obtainedSec, obtainedSub;
	/**
	 * Create the panel.
	 */
	public AttendanceSelectSubject() {
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBounds(0,0,559,539);
		setBackground(Color.white);
		setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.menuClicked(MainMenu.AttendanceSelectSection);
			}
		});
		backButton.addMouseListener(new PropertiesListener(backButton));
		backButton.setBounds(10, 11, 64, 50);
		add(backButton);
		
		subjectScreen = new JPanel();
		subjectScreen.setBorder(new LineBorder(new Color(65, 105, 225)));
		subjectScreen.setBackground(new Color(255, 255, 255));
		subjectScreen.setBounds(10, 72, 539, 456);
		add(subjectScreen);
		subjectScreen.setLayout(new GridLayout(0, 2, 2, 2));
		
		JLabel lblNewLabel = new JLabel("Select a subject:");
		lblNewLabel.setBounds(84, 47, 178, 14);
		add(lblNewLabel);
		
		if(Login.pubOccupation.equals("Student")) {
			backButton.setVisible(false);
			lblNewLabel.setBounds(10,11,178,14);
			subjectScreen.setBounds(10,30,539,496);
		}
	}
	
	public void execute() {
		buttonNames.clear();
		listSecNames.clear();
		subjectScreen.removeAll();
		checkCount();
		checkName();
		existingRecords();
		revalidate();
		repaint();
	}
	
	public void executeForTeachers() {
		buttonNames.clear();
		listSecNames.clear();
		subjectScreen.removeAll();
		checkCountForTeachers();
		checkNameForTeachers();
		existingRecords();
		revalidate();
		repaint();
	}
	
	private void checkCount() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement checkCount = conn.prepareStatement("select count(subjectname) from subjectinfo where sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet checking = checkCount.executeQuery();
			if(checking.next()) {
				count = checking.getInt("count(subjectname)");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkName() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {	
			PreparedStatement checkName = conn.prepareStatement("select subjectname from subjectinfo where sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet checking = checkName.executeQuery();
			while(checking.next()) {
				String deptName = checking.getString("subjectname");
				listSecNames.add(deptName);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}	
	}
	
	private void existingRecords() {
		for(int i = 0; i < count; i++) {
			JButton button = new JButton(listSecNames.get(i));
			buttonNames.add(button);
			buttonNames.get(i).setName(listSecNames.get(i));
			buttonNames.get(i).addMouseListener(new PropertiesListener(buttonNames.get(i)));
			buttonNames.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainMenu.menuClicked(MainMenu.panelAttendance);
					JButton source = (JButton) e.getSource();
					MainMenu.records.obtainedSub = source.getName();
					MainMenu.panelAttendance.obtainedSub = source.getName();
					if(Login.pubOccupation.equals("Student")) {
						MainMenu.records.obtainedDept = Login.pubDeptName;
						MainMenu.panelAttendance.obtainedDept = Login.pubDeptName;
						MainMenu.records.obtainedSec = Login.pubSecName;
						MainMenu.panelAttendance.obtainedSec = Login.pubSecName;
					}
					MainMenu.panelAttendance.execute();
				}
			});
			subjectScreen.add(button);
		}
		revalidate();
		repaint();
	}
	
	private void checkCountForTeachers() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement checkCount = conn.prepareStatement("select count(subjectname) from subjectinfo where teachername='"+Login.pubFullName+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet checking = checkCount.executeQuery();
			if(checking.next()) {
				count = checking.getInt("count(subjectname)");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkNameForTeachers() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {	
			PreparedStatement checkName = conn.prepareStatement("select subjectname from subjectinfo where teachername='"+Login.pubFullName+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet checking = checkName.executeQuery();
			while(checking.next()) {
				String deptName = checking.getString("subjectname");
				listSecNames.add(deptName);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}	
	}
}
