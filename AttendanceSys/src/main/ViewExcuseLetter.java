package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JTextPane;

public class ViewExcuseLetter extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String obtainedRecord, obtainedDept, obtainedSec, obtainedSub, obtainedName, obtainedTitle;
	JLabel title;
	JTextPane desc;
	/**
	 * Create the panel.
	 */
	public ViewExcuseLetter() {
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBounds(0,0,559,539);
		setBackground(Color.white);
		setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.ViewExcuseRequest.execute();
				MainMenu.menuClicked(MainMenu.ViewExcuseRequest);
			}
		});
		backButton.addMouseListener(new PropertiesListener(backButton));
		backButton.setBounds(10, 11, 100, 50);
		add(backButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel.setBounds(10, 72, 539, 456);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(new LineBorder(new Color(65, 105, 225)));
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_1, BorderLayout.NORTH);
		
		title = new JLabel("Title: ");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_1.add(title);
		
		desc = new JTextPane();
		desc.setBorder(new LineBorder(new Color(65, 105, 225)));
		desc.setEditable(false);
		panel.add(desc, BorderLayout.CENTER);
		
		JButton disapproveButton = new JButton("Disapprove");
		disapproveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user, MySQLConnectivity.pass)) {
					PreparedStatement statement = conn.prepareStatement("update excuserequests set isApproved=false, approvedBy='"+Login.pubFullName+"' where record_name='"+obtainedRecord+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and subjectname='"+obtainedSub+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
					int result = statement.executeUpdate();
					if(result == 1) {
						JOptionPane.showMessageDialog(null, "You've disapproved the letter!");
					}
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
			}
		});
		disapproveButton.addMouseListener(new PropertiesListener(disapproveButton));
		disapproveButton.setBounds(449, 11, 100, 50);
		add(disapproveButton);
		
		JButton approveButton = new JButton("Approve");
		approveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user, MySQLConnectivity.pass)) {
					PreparedStatement statement = conn.prepareStatement("update excuserequests set isApproved=true, approvedBy='"+Login.pubFullName+"' where record_name='"+obtainedRecord+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and subjectname='"+obtainedSub+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
					int result = statement.executeUpdate();
					if(result == 1) {
						PreparedStatement attend = conn.prepareStatement("update attendancestatus set hasAttended=true, studentstatus='Excused', timeattended=current_timestamp where firstname='"+Login.pubFN+"' and middlename='"+Login.pubMN+"' and lastname='"+Login.pubLN+"' and recordid='"+MainMenu.records.obtainedID+"' and record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
						attend.executeUpdate();
						JOptionPane.showMessageDialog(null, "You've approved the letter!");
					}
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
					
			}
		});
		approveButton.addMouseListener(new PropertiesListener(approveButton));
		approveButton.setBounds(339, 11, 100, 50);
		add(approveButton);
	}
	
	public void getLetter() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user, MySQLConnectivity.pass)) {
			PreparedStatement statement = conn.prepareStatement("select requesttitle, requestdesc from excuserequests where record_name='"+obtainedRecord+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and subjectname='"+obtainedSub+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"' and fullname='"+obtainedName+"' and requesttitle='"+obtainedTitle+"'");
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				String titleLetter = result.getString("requesttitle");
				String descLetter = result.getString("requestdesc");
				title.setText("Title: "+titleLetter);
				desc.setText(descLetter);
			}
			revalidate();
			repaint();
		} catch(SQLException sql) {
			sql.printStackTrace();
		}
	}
}
