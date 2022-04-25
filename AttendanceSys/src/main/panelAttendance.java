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
	public JComboBox<String> cbSub = new JComboBox<String>();
	JComboBox<String> cbDate = new JComboBox<String>();
	private int count = 0;
	public String obtainedSec;
	private String obtainedSub;
	public String obtainedDept;
	public boolean addingRecords = false, deletingRecords = false, newRecord;
	private boolean sortingDate = false;
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
		addAttendance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					deletingRecords = false;
					addingRecords = true;
					attendanceSettings dialog = new attendanceSettings();
					dialog.obtainedDept = AdminMenu.panelAttendance.obtainedDept;
					dialog.obtainedSec = AdminMenu.panelAttendance.obtainedSec;
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
						if(dialog.cbSub.getSelectedIndex() > 0 && cbSub.getSelectedIndex() == 0) { // will add a button if selected sort is default
							if(!sortingDate) {
								mainScreen.removeAll();
								checkName();
								checkCount();
								existingRecords();
							} else {
								mainScreen.add(button);	
							}
						} else if (dialog.cbSub.getSelectedIndex() == cbSub.getSelectedIndex()) {// will add a button if the created record is the same section as the selected sort
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
		addAttendance.setBounds(10, 11, 135, 50);
		add(addAttendance);
		
		JButton deleteAttendance = new JButton("Delete Attendance");
		deleteAttendance.setBorder(null);
		deleteAttendance.setBackground(new Color(65, 105, 225));
		deleteAttendance.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		deleteAttendance.setForeground(new Color(255, 255, 255));
		deleteAttendance.addMouseListener(new PropertiesListener(deleteAttendance));
		deleteAttendance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deletingRecords) {
					deletingRecords = true;
				} else {
					deletingRecords = false;
				}
			}
		});
		deleteAttendance.setBounds(155, 11, 140, 50);
		add(deleteAttendance);
		
		mainScreen = new JPanel();
		mainScreen.setBackground(new Color(255, 255, 255));
		mainScreen.setBorder(new LineBorder(new Color(65, 105, 225)));
		mainScreen.setBounds(10, 71, 539, 457);
		add(mainScreen);
		mainScreen.setLayout(new GridLayout(0, 2, 0, 0));
		
		cbDate = new JComboBox<String>();
		cbDate.setBounds(340, 39, 114, 22);
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
		
		cbSub = new JComboBox<String>();
		cbSub.setBounds(464, 39, 85, 22);
		cbSub.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbSub.getSelectedIndex() > 0) {
					obtainedSub = cbSub.getSelectedItem().toString();
					execute();
				} else {
					execute();
				}
				cbDate.setSelectedIndex(0);
			}
		});
		add(cbSub);
		
		JLabel lblNewLabel = new JLabel("Sort :");
		lblNewLabel.setBounds(305, 43, 29, 14);
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
			String sortingDeptAndSec= "select count(record_name) from attendancerecords where departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"'";
			String sortingDeptSecAndSub = "select count(record_name) from attendancerecords where subjectname='"+obtainedSub+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"'";
			PreparedStatement checkCount;
			if (cbSub.getSelectedIndex() == 0) {
				checkCount = conn.prepareStatement(sortingDeptAndSec);
			} else {
				checkCount = conn.prepareStatement(sortingDeptSecAndSub);
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
			String sortingDeptAndSec= "select record_name, UNIX_TIMESTAMP(timecreated) as created from attendancerecords where departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"'";
			String sortingDeptSecAndSub = "select record_name, UNIX_TIMESTAMP(timecreated) as created from attendancerecords where subjectname='"+obtainedSub+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"'";
			String sortedDate = "order by created";
			PreparedStatement checkName;
			if (cbSub.getSelectedIndex() == 0) {
				if(sortingDate) {
					checkName = conn.prepareStatement(sortingDeptAndSec + " " + sortedDate + " asc");
				} else {
					checkName = conn.prepareStatement(sortingDeptAndSec + " " + sortedDate + " desc");
				}
			} else {
				if(sortingDate) {
					checkName = conn.prepareStatement(sortingDeptSecAndSub + " " + sortedDate + " asc");
				} else {
					checkName = conn.prepareStatement(sortingDeptSecAndSub + " " + sortedDate + " desc");
				}
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
	
	public void sub(JComboBox<String> cb) {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
			PreparedStatement getStatement = conn.prepareStatement("select subjectname from subjectinfo where departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			cb.removeAllItems();
			cb.addItem("Subject");
			while(result.next()) {
				String obtainedString = result.getString("subjectname");
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
				AdminMenu.records.obtainedDept = obtainedDept;
				AdminMenu.records.obtainedSec = obtainedSec;
				AdminMenu.records.obtainedRecord = source.getName();
				AdminMenu.records.model.setRowCount(0);
				AdminMenu.records.checkList();
			} else {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
					JButton source = (JButton) e.getSource();
					int select = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+source.getName()+"?", "Delete", JOptionPane.YES_NO_OPTION);
					if(select == JOptionPane.YES_OPTION) {
						mainScreen.remove(source);
						buttonNames.remove(source);
						PreparedStatement deleteRecord = conn.prepareStatement("delete from attendancerecords where record_name='"+source.getName()+"' and schoolname='"+Login.pubSchoolName+"'");
						deleteRecord.executeUpdate();
						PreparedStatement deleteStatusRecord = conn.prepareStatement("delete from attendancestatus where record_name='"+source.getName()+"' and schoolname='"+Login.pubSchoolName+"'");
						deleteStatusRecord.executeUpdate();
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
}
