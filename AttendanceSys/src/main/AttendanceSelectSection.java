package main;

import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class AttendanceSelectSection extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JButton> buttonNames = new ArrayList<JButton>();
	private List<String> listSecNames = new ArrayList<String>();
	private int count = 0;
	public String selectedSec, obtainedDept;
	private JPanel selectionScreen;
	/**
	 * Create the panel.
	 */
	public AttendanceSelectSection() {
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
		
		JLabel noteLabel = new JLabel("Select a department");
		noteLabel.setBounds(10, 11, 97, 14);
		add(noteLabel);
	
	}
	
	public void execute() {
		buttonNames.clear();
		listSecNames.clear();
		selectionScreen.removeAll();
		checkCount();
		checkName();
		existingRecords();
		revalidate();
		repaint();
	}
	
	private void checkCount() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement checkCount = conn.prepareStatement("select count(sectionname) from sectioninfo where departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet checking = checkCount.executeQuery();
			if(checking.next()) {
				count = checking.getInt("count(sectionname)");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkName() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {	
			PreparedStatement checkName = conn.prepareStatement("select sectionname from sectioninfo where departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet checking = checkName.executeQuery();
			while(checking.next()) {
				String deptName = checking.getString("sectionname");
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
					AdminMenu.menuClicked(AdminMenu.panelAttendance);
					JButton source = (JButton) e.getSource();
					AdminMenu.panelAttendance.obtainedSec = source.getName();
					AdminMenu.panelAttendance.execute();
					AdminMenu.panelAttendance.sub(AdminMenu.panelAttendance.cbSub);
				}
			});
			selectionScreen.add(button);
		}
		revalidate();
		repaint();
	}

}
