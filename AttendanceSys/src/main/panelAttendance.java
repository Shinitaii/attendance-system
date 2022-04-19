package main;
import javax.swing.JPanel;
import javax.swing.Timer;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;

public class panelAttendance extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> listRecordNames = new ArrayList<String>();
	List<JButton> buttonNames = new ArrayList<JButton>();
	List<Date> listDates = new ArrayList<Date>();
	JComboBox<String> cbSec = new JComboBox<String>();
	JComboBox<String> cbDept = new JComboBox<String>();
	private int count = 0;
	private String obtainedDept, obtainedSec;
	public boolean addingRecords = false, deletingRecords = false, newRecord;
	
	JPanel mainScreen;
	
	JButton button;
	private JTextField txtSearch;
	/**
	 * Create the panel.
	 */
	public panelAttendance() {
		setBounds(new Rectangle(0, 0, 559, 539));
		setForeground(new Color(65, 105, 225));
		setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		JButton addAttendance = new JButton("Add Attendance");
		addAttendance.setForeground(new Color(255, 255, 255));
		addAttendance.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		addAttendance.setBorder(null);
		addAttendance.setBackground(new Color(65, 105, 225));
		addAttendance.addMouseListener(new PropertiesListener(addAttendance));
		addAttendance.addActionListener(new AddDeleteListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					deletingRecords = false;
					addingRecords = true;
					attendanceSettings.isCancelled = false;
					attendanceSettings dialog = new attendanceSettings();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);	
					
					if(!attendanceSettings.isCancelled) {
						checkName();
						button = new JButton(attendanceSettings.name);
						buttonNames.add(button);
						button = buttonNames.get(count);
						buttonNames.get(count).addMouseListener(new PropertiesListener(buttonNames.get(count)));
						buttonNames.get(count).setName(attendanceSettings.name);
						buttonNames.get(count).addActionListener(new AddDeleteListener());
						if(attendanceSettings.cbSection.getSelectedIndex() == cbSec.getSelectedIndex()) {
							mainScreen.add(button);							
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
		addAttendance.setBounds(10, 11, 150, 50);
		add(addAttendance);
		
		JButton deleteAttendance = new JButton("Delete Attendance");
		deleteAttendance.setBorder(null);
		deleteAttendance.setBackground(new Color(65, 105, 225));
		deleteAttendance.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		deleteAttendance.setForeground(new Color(255, 255, 255));
		deleteAttendance.addMouseListener(new PropertiesListener(deleteAttendance));
		deleteAttendance.addActionListener(new AddDeleteListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deletingRecords) {
					deletingRecords = true;
				} else {
					deletingRecords = false;
				}
			}
		});
		deleteAttendance.setBounds(170, 11, 150, 50);
		add(deleteAttendance);
		
		mainScreen = new JPanel();
		mainScreen.setBackground(new Color(255, 255, 255));
		mainScreen.setBorder(new LineBorder(new Color(65, 105, 225)));
		mainScreen.setBounds(10, 71, 539, 457);
		add(mainScreen);
		mainScreen.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbDept = new JComboBox<String>();
		cbDept.setBackground(new Color(65, 105, 225));
		cbDept.setBounds(369, 39, 85, 22);
		cbDept.addItem("Department");
		cbDept.addItemListener(new selectedDept(cbDept));
		dept(cbDept);
		add(cbDept);
		
		cbSec = new JComboBox<String>();
		cbSec.setBounds(464, 39, 85, 22);
		cbSec.addItem("Section");
		cbSec.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbSec.getSelectedIndex() > 0) {
				obtainedSec = cbSec.getSelectedItem().toString();
				execute();
				} else {
					execute();
				}
			}
		});
		add(cbSec);
		
		JLabel lblNewLabel = new JLabel("Sort :");
		lblNewLabel.setBounds(330, 43, 29, 14);
		add(lblNewLabel);
		
		txtSearch = new JTextField();
		txtSearch.setBorder(new LineBorder(new Color(65, 105, 225)));
		txtSearch.setBounds(330, 11, 150, 22);
		add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton searchButton = new JButton("Search");
		searchButton.setForeground(new Color(255, 255, 255));
		searchButton.setBorder(null);
		searchButton.setBackground(new Color(65, 105, 225));
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchButton.setBounds(484, 11, 65, 23);
		add(searchButton);
		
		checkCount();
		checkName();
		existingRecords();  
		
		Timer time = new Timer(500, (ActionListener) new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
		    		PreparedStatement checkTime = conn.prepareStatement("select timeexpires from attendancerecords where schoolname='"+Login.pubSchoolName+"'");
		    		ResultSet result = checkTime.executeQuery();
		    		while(result.next()) {
		    			Date obtainedTime = result.getTimestamp(("timeexpires"));
		    			listDates.add(obtainedTime);
		    		}
		    		for(int i = 0; i < count; i++) {
		    			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		    			if(timestamp.after(listDates.get(i))) {
		    			} else {
		    				long diff = listDates.get(i).getTime() - timestamp.getTime();
		    				long sec = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;
		    				long mins = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
		    				long hrs = TimeUnit.MILLISECONDS.toHours(diff) % 24;
		    				System.out.println(hrs + ":" +mins + ":" +sec);
		    			}
		    		}
		    	} catch(SQLException sql) {
		    		sql.printStackTrace();
		    	}
		    }
		});
		time.start();
		
	}
	
	private void execute() {
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
			String normal = "select count(record_name) from attendancerecords where schoolname='"+Login.pubSchoolName+"'";
			String sortingDeptOnly = "select count(record_name) from attendancerecords where departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'";
			String sortingDeptAndSec= "select count(record_name) from attendancerecords where departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"'";
			
			PreparedStatement checkCount;
			if(cbDept.getSelectedIndex() == 0 && cbSec.getSelectedIndex() == 0) {
				checkCount = conn.prepareStatement(normal);
			} else if (cbDept.getSelectedIndex() > 0 && cbSec.getSelectedIndex() == 0) {
				checkCount = conn.prepareStatement(sortingDeptOnly);
			} else {
				checkCount = conn.prepareStatement(sortingDeptAndSec);
			}
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
			String normal = "select record_name from attendancerecords where schoolname='"+Login.pubSchoolName+"'";
			String sortingDeptOnly = "select record_name from attendancerecords where departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'";
			String sortingDeptAndSec= "select record_name from attendancerecords where departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"'";

			PreparedStatement checkName;
			
			if(cbDept.getSelectedIndex() == 0 && cbSec.getSelectedIndex() == 0) {
				checkName = conn.prepareStatement(normal);
			} else if (cbDept.getSelectedIndex() > 0 && cbSec.getSelectedIndex() == 0) {
				checkName = conn.prepareStatement(sortingDeptOnly);
			} else {
				checkName = conn.prepareStatement(sortingDeptAndSec);
			} 
			ResultSet checking = checkName.executeQuery();
			if(!addingRecords) {
				while(checking.next()) {
					String recordNames = checking.getString("record_name");
					listRecordNames.add(recordNames);
				}
			} else {
				if(checking.next()) {
					String recordNames = checking.getString("record_name");
					listRecordNames.add(recordNames);
				}
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}	
	}
	
	private void existingRecords() {
		for(int i = 0; i < count; i++) {
			JButton button = new JButton(listRecordNames.get(i));
			buttonNames.add(button);
			buttonNames.get(i).setName(listRecordNames.get(i));
			buttonNames.get(i).addMouseListener(new PropertiesListener(buttonNames.get(i)));
			buttonNames.get(i).addActionListener(new AddDeleteListener());
			mainScreen.add(button);
		}
		revalidate();
		repaint();
	}
	
	private void dept(JComboBox<String> cb) {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
			PreparedStatement getStatement = conn.prepareStatement("select departmentname from departmentinfo where schoolname ='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			while(result.next()) {
				String obtainedString = result.getString("departmentname");
				cb.addItem(obtainedString);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void sec(JComboBox<String> cb) {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
			PreparedStatement getStatement = conn.prepareStatement("select sectionname from sectioninfo where departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			cb.removeAllItems();
			cb.addItem("Section");
			while(result.next()) {
				String obtainedString = result.getString("sectionname");
				cb.addItem(obtainedString);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private class AddDeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!deletingRecords) {
				AdminMenu.menuClicked(AdminMenu.records);
				JButton source = (JButton) e.getSource();
				Records.lblNewLabel.setText(source.getName());			
			} else {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
					JButton source = (JButton) e.getSource();
					int select = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+source.getName()+"?", "Delete", JOptionPane.YES_NO_OPTION);
					if(select == JOptionPane.YES_OPTION) {
						mainScreen.remove(source);
						buttonNames.remove(source);
						PreparedStatement deleteRecord = conn.prepareStatement("delete from attendancerecords where record_name='"+source.getName()+"' and schoolname='"+Login.pubSchoolName+"'");
						deleteRecord.executeUpdate();
						revalidate();
						repaint();
						checkCount();
					}
				} catch (SQLException sql){
					sql.printStackTrace();
				}
			}
		}
	}
	
	private class selectedDept implements ItemListener {
		
		JComboBox<String> cb;

		
		public selectedDept (JComboBox<String> cb) {
			this.cb = cb;

		}
		
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				obtainedDept = cb.getSelectedItem().toString();
				sec(cbSec);
				execute();
			}
		}	
	}
}
