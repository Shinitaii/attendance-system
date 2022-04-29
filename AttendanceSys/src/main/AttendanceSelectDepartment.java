package main;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

public class AttendanceSelectDepartment extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JButton> buttonNames = new ArrayList<JButton>();
	private List<String> listDeptNames = new ArrayList<String>();
	private int count = 0;
	public String selectedDept;
	private JPanel selectionScreen;
	private JLabel noteLabel;
	/**
	 * Create the panel.
	 */
	public AttendanceSelectDepartment() {
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(Color.WHITE);
		setBounds(0,0,559,539);
		setLayout(null);
		
		selectionScreen = new JPanel();
		selectionScreen.setBorder(new LineBorder(new Color(65, 105, 225)));
		selectionScreen.setBackground(new Color(255, 255, 255));
		selectionScreen.setBounds(10, 36, 539, 492);
		add(selectionScreen);
		selectionScreen.setLayout(new GridLayout(0, 2, 0, 0));
		
		noteLabel = new JLabel("Select a department");
		noteLabel.setBounds(11, 11, 550, 14);
		add(noteLabel);

	}
	
	public void execute() {
		buttonNames.clear();
		listDeptNames.clear();
		selectionScreen.removeAll();
		checkCount();
		checkName();
		existingRecords();
		revalidate();
		repaint();
	}
	
	public void executeForTeachers() {
		buttonNames.clear();
		listDeptNames.clear();
		selectionScreen.removeAll();
		checkCountForTeachers();
		checkNameForTeachers();
		existingRecords();
		revalidate();
		repaint();
	}
	
	private void checkCount() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement checkCount = conn.prepareStatement("select count(departmentname) from departmentinfo where schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet checking = checkCount.executeQuery();
			if(checking.next()) {
				count = checking.getInt("count(departmentname)");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkName() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {	
			PreparedStatement checkName = conn.prepareStatement("select departmentname from departmentinfo where schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet checking = checkName.executeQuery();
			while(checking.next()) {
				String deptName = checking.getString("departmentname");
				listDeptNames.add(deptName);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}	
	}
	
	private void existingRecords() {
		for(int i = 0; i < count; i++) {
			JButton button = new JButton(listDeptNames.get(i));
			buttonNames.add(button);
			buttonNames.get(i).setName(listDeptNames.get(i));
			buttonNames.get(i).addMouseListener(new PropertiesListener(buttonNames.get(i)));
			buttonNames.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainMenu.menuClicked(MainMenu.AttendanceSelectSection);
					JButton source = (JButton) e.getSource();
					MainMenu.AttendanceSelectSection.obtainedDept = source.getName();
					MainMenu.AttendanceSelectSubject.obtainedDept = source.getName();
					MainMenu.panelAttendance.obtainedDept = source.getName();
					if(!Login.pubOccupation.equals("Admin")) {
						MainMenu.AttendanceSelectSection.executeForTeachers();
					} else {
						MainMenu.AttendanceSelectSection.execute();
					}
				}
			});
			selectionScreen.add(button);
		}
		revalidate();
		repaint();
	}
	
	private void checkCountForTeachers() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement checkCount = conn.prepareStatement("select count(departmentname) from teacherassignedinfo where teachername='"+Login.pubFullName+"' and teacherid in (select max(teacherid) from teacherassignedinfo group by departmentname) and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet checking = checkCount.executeQuery();
			if(checking.next()) {
				count = checking.getInt("count(departmentname)");
			}
			if(count == 0) {
				JButton button = new JButton("Get assigned");
				button.addActionListener(new TeacherAssignListener());
				button.addMouseListener(new PropertiesListener(button));
				selectionScreen.add(button);
				noteLabel.setText("You do not have any departments, sections nor subjects assigned. Click the button to be assigned.");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkNameForTeachers() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {	
			PreparedStatement checkName = conn.prepareStatement("select departmentname from teacherassignedinfo where teachername='"+Login.pubFullName+"' and teacherid in (select max(teacherid) from teacherassignedinfo group by departmentname) and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet checking = checkName.executeQuery();
			while(checking.next()) {
				String deptName = checking.getString("departmentname");
				listDeptNames.add(deptName);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}	
	}
	
}
