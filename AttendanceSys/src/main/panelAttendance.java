package main;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class panelAttendance extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> listRecordNames = new ArrayList<String>();
	List<JButton> buttonNames = new ArrayList<JButton>();
	JComboBox<String> cbDate = new JComboBox<String>();
	
	private int count = 0;
	public String obtainedSec;
	public String obtainedSub;
	public String obtainedDept;
	public String selectedSub;
	public boolean addingRecords = false, deletingRecords = false, newRecord;
	private boolean sortingDate = false;
	JPanel mainScreen;
	JButton button;
	JLabel label;
	JLabel timerLabel;
	/**
	 * Create the button.
	 */
	public panelAttendance() {
		setBounds(new Rectangle(0, 0, 559, 539));
		setForeground(new Color(65, 105, 225));
		setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		JLabel lblInstruction = new JLabel("");
		lblInstruction.setBounds(385, 14, 164, 14);
		add(lblInstruction);
		
		JButton addAttendance = new JButton("Add Attendance");
		addAttendance.addMouseListener(new PropertiesListener(addAttendance));
		addAttendance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					deletingRecords = false;
					addingRecords = true;
					attendanceSettings dialog = new attendanceSettings();
					dialog.obtainedDept = MainMenu.panelAttendance.obtainedDept;
					dialog.obtainedSec = MainMenu.panelAttendance.obtainedSec;
					dialog.obtainedDeptName.setText(dialog.obtainedDept);
					dialog.obtainedSecName.setText(dialog.obtainedSec);
					System.out.println(dialog.obtainedDept);
					dialog.isCancelled = false;
					dialog.sub(dialog.cbSub);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);	
					
					if(!dialog.isCancelled) {
						checkName();
						button = new JButton(attendanceSettings.name);
						if(!sortingDate) {
							buttonNames.add(0, button);
							buttonNames.get(0).addMouseListener(new PropertiesListener(buttonNames.get(0)));
							buttonNames.get(0).setName(attendanceSettings.name);
							listRecordNames.add(0, button.getName());
							buttonNames.get(0).addActionListener(new AddDeleteListener());
						} else {
							buttonNames.add(button);
							button = buttonNames.get(count);	
							buttonNames.get(count).addMouseListener(new PropertiesListener(buttonNames.get(count)));
							buttonNames.get(count).setName(attendanceSettings.name);
							buttonNames.get(count).addActionListener(new AddDeleteListener());
						}
						if(dialog.cbSub.getSelectedIndex() > 0) { // will add a button if selected sort is default
							if(!sortingDate) {
								mainScreen.removeAll();
								checkName();
								checkCount();
								existingRecords();
							} else {
								mainScreen.add(button);	
							}
						} else { // will always secretly remove the created record section isn't the same as the selected sort
							if(!sortingDate) {
								buttonNames.remove(0);
							} else {
								buttonNames.remove(count);
							}
						}
						addingRecords = false;
						checkCount();
						revalidate();
						repaint();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		addAttendance.setBounds(90, 11, 135, 50);
		add(addAttendance);
		
		JButton deleteAttendance = new JButton("Delete Attendance");
		deleteAttendance.addMouseListener(new PropertiesListener(deleteAttendance));
		deleteAttendance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deletingRecords) {
					deletingRecords = true;
					lblInstruction.setText("Click on a record to delete");
				} else {
					lblInstruction.setText("");
					deletingRecords = false;
				}
			}
		});
		deleteAttendance.setBounds(235, 11, 140, 50);
		add(deleteAttendance);
		
		mainScreen = new JPanel();
		mainScreen.setBackground(new Color(255, 255, 255));
		mainScreen.setBorder(new LineBorder(new Color(65, 105, 225)));
		mainScreen.setBounds(10, 71, 539, 457);
		add(mainScreen);
		mainScreen.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbDate = new JComboBox<String>();
		cbDate.setBounds(414, 39, 135, 22);
		cbDate.addItem("Newest To Oldest");
		cbDate.addItem("Oldest to Newest");
		cbDate.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbDate.getSelectedIndex() == 0) {
					sortingDate = false;
					execute();
				} else {
					sortingDate = true;
					execute();
				}
			}
		});
		add(cbDate);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.menuClicked(MainMenu.AttendanceSelectSubject);
			}
		});
		backButton.addMouseListener(new PropertiesListener(backButton));
		backButton.setBounds(10, 11, 70, 50);
		add(backButton);
		
		JLabel lblSort = new JLabel("Sort:");
		lblSort.setBounds(385, 44, 39, 14);
		add(lblSort);
		
		if(!Login.pubOccupation.equals("Admin") && !Login.pubOccupation.equals("Teacher")) {
			addAttendance.setVisible(false);
			deleteAttendance.setVisible(false);
		}
		
	}
	
	public void execute() {
		mainScreen.removeAll();
		listRecordNames.clear();
		buttonNames.clear();
		checkCount();
		checkName();
		existingRecords();
		revalidate();
		repaint();
	}
	
	private void checkCount() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			String sortingDeptSecAndSub = "select count(record_name) from attendancerecords where subjectname='"+obtainedSub+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'";
			PreparedStatement checkCount;
			checkCount = conn.prepareStatement(sortingDeptSecAndSub);
			ResultSet checking = checkCount.executeQuery();
			if(checking.next()) {
				count = checking.getInt("count(record_name)");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkName() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			String sortingDeptSecAndSub = "select record_name, UNIX_TIMESTAMP(timecreated) as created from attendancerecords where subjectname='"+obtainedSub+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'";
			String sortedDate = "order by created";
			PreparedStatement checkName;
			if(sortingDate) {
				checkName = conn.prepareStatement(sortingDeptSecAndSub + " " + sortedDate + " asc");
			} else {
				checkName = conn.prepareStatement(sortingDeptSecAndSub + " " + sortedDate + " desc");
			}
			ResultSet checking = checkName.executeQuery();
			while(checking.next()) {
				String recordNames = checking.getString("record_name");
				listRecordNames.add(recordNames);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}	
	}
	
	private void existingRecords() {
		for(int i = 0; i < count; i++) {
			JButton button = new JButton(listRecordNames.get(i));
			buttonNames.add(button);
			button = buttonNames.get(i);
			buttonNames.get(i).setName(listRecordNames.get(i));
			buttonNames.get(i).addMouseListener(new PropertiesListener(buttonNames.get(i)));
			buttonNames.get(i).addActionListener(new AddDeleteListener());
			mainScreen.add(button);
		}
		revalidate();
		repaint();
	}
	
	private class AddDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!deletingRecords) {
				MainMenu.menuClicked(MainMenu.records);
				JButton source = (JButton) e.getSource();
				MainMenu.records.obtainedDept = obtainedDept;
				MainMenu.records.obtainedSec = obtainedSec;
				MainMenu.records.obtainedSub = obtainedSub;
				MainMenu.records.obtainedRecord = source.getName();
				MainMenu.records.model.setRowCount(0);
				MainMenu.records.execute();
			} else {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
					JButton source = (JButton) e.getSource();
					if(deletingRecords) {
						int select = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+source.getName()+"?", "Delete", JOptionPane.YES_NO_OPTION);
						if(select == JOptionPane.YES_OPTION) {
							mainScreen.remove(source);
							buttonNames.remove(source);
							PreparedStatement deleteRecord = conn.prepareStatement("delete from attendancerecords where record_name='"+source.getName()+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
							deleteRecord.executeUpdate();
							PreparedStatement deleteStatusRecord = conn.prepareStatement("delete from attendancestatus where record_name='"+source.getName()+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
							deleteStatusRecord.executeUpdate();
							revalidate();
							repaint();
							checkCount();
						}
					}
				} catch (SQLException sql){
					sql.printStackTrace();
				}
			}
		}
	}
		
}