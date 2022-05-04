package main;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;

public class ViewExcuseRequest extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> titleNames = new ArrayList<String>();
	List<String> fullNames = new ArrayList<String>();
	List<Boolean> approveList = new ArrayList<Boolean>();
	List<JPanel> panelNames = new ArrayList<JPanel>();
	JPanel panelRequests;
	public String obtainedRecord, obtainedDept, obtainedSec, obtainedSub;
	int count = 0;
	/**
	 * Create the panel.
	 */
	public ViewExcuseRequest() {
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBounds(0,0,559,539);
		setBackground(Color.white);
		setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.menuClicked(MainMenu.records);
			}
		});
		backButton.addMouseListener(new PropertiesListener(backButton));
		backButton.setBounds(10, 11, 100, 50);
		add(backButton);
		
		panelRequests = new JPanel();
		panelRequests.setBackground(new Color(255, 255, 255));
		panelRequests.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelRequests.setBounds(10, 72, 539, 456);
		add(panelRequests);
		panelRequests.setLayout(new GridLayout(0, 2, 0, 0));
	}
	
	public void execute() {
		titleNames.clear();
		fullNames.clear();
		approveList.clear();
		panelNames.clear();
		panelRequests.removeAll();
		checkCount();
		checkName();
		addExistingPanels();
		revalidate();
		repaint();
	}
	
	private void checkCount() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user, MySQLConnectivity.pass)) {
			PreparedStatement statement = conn.prepareStatement("select count(requesttitle) from excuserequests where record_name='"+obtainedRecord+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and subjectname='"+obtainedSub+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				count = result.getInt("count(requesttitle)");
			}
		} catch(SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkName() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user, MySQLConnectivity.pass)) {
			PreparedStatement statement = conn.prepareStatement("select requesttitle, fullname, isApproved from excuserequests where record_name='"+obtainedRecord+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and subjectname='"+obtainedSub+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				String req = result.getString("requesttitle");
				String name = result.getString("fullname");
				boolean approve = result.getBoolean("isApproved");
				titleNames.add(req);
				fullNames.add(name);
				approveList.add(approve);
			}
		} catch(SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void isApprovedOrNot(boolean bool, JLabel label) {
		if(bool) {
			label.setText("Status: Approved");
		} else if(!bool){
			label.setText("Status: Disapproved");
		} else {
			label.setText("Status: To be reviewed");
		}
	}
	
	private void addExistingPanels() {
		for(int i = 0; i < count; i++) {
			JPanel panel = new JPanel(new GridLayout(0,1,2,2));
			JLabel label = new JLabel(titleNames.get(i));
			JLabel label2 = new JLabel(fullNames.get(i));
			JLabel label3 = new JLabel();
			isApprovedOrNot(approveList.get(i),label3);
			panel.add(label);
			panel.add(label2);
			panel.add(label3);
			panelNames.add(panel);
			panelNames.get(i).addMouseListener(new PropertiesListener(panelNames.get(i)) {
				public void mouseClicked(MouseEvent e) {
					MainMenu.ViewExcuseLetter.obtainedRecord = obtainedRecord;
					MainMenu.ViewExcuseLetter.obtainedDept = obtainedDept;
					MainMenu.ViewExcuseLetter.obtainedSec = obtainedSec;
					MainMenu.ViewExcuseLetter.obtainedSub = obtainedSub;
					MainMenu.ViewExcuseLetter.getLetter();
					MainMenu.menuClicked(MainMenu.ViewExcuseLetter);
				}
			});
			panelRequests.add(panel);
		}
	}
}
