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

public class panelAttendance extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> listRecordNames = new ArrayList<String>();
	List<JButton> buttonNames = new ArrayList<JButton>();
	List<Date> listDates = new ArrayList<Date>();
	private int count = 0;
	public boolean addingRecords = false, deletingRecords = false, newRecord;
	
	JPanel mainScreen;
	
	JButton button;
	private JTextField textField;
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
					mainScreen.add(button);
					buttonNames.get(count).addActionListener(new AddDeleteListener());
					checkCount();
					addingRecords = false;
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
		
		textField = new JTextField();
		textField.setBounds(330, 41, 125, 20);
		add(textField);
		textField.setColumns(10);
		
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(465, 38, 84, 23);
		add(searchButton);
		
		JLabel lblSearch = new JLabel("Search Date:");
		lblSearch.setBounds(330, 11, 125, 19);
		add(lblSearch);
		
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
	
	public void checkCount() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement checkCount = conn.prepareStatement("select count(record_name) from attendancerecords where schoolname='"+Login.pubSchoolName+"'");
			ResultSet checking = checkCount.executeQuery();
			if(checking.next()) {
				count = checking.getInt("count(record_name)");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	public void checkName() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement checkName = conn.prepareStatement("select record_name from attendancerecords where schoolname='"+Login.pubSchoolName+"'");
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
	
	public void existingRecords() {
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
}
