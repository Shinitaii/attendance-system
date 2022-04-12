package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class attendanceSettings extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	panelAttendance panelAttendance;
	public static String obtainedDept;
	int[] hr = new int[24];
	int[] num = new int[60];
	int selectedHour, selectedMinute, selectedSecond;
	int obtainedNum;
	int deptCount;
	int secCount;
	String month;
	public static String name;
	int day, year;
	public static int currentRecordCount = 0;
	public static String obtainedSec;
	String currentTime;
	JComboBox<String> cbSection;
	public static boolean isCancelled = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			attendanceSettings dialog = new attendanceSettings();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public attendanceSettings() {
		super(null, ModalityType.TOOLKIT_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(attendanceSettings.class.getResource("/res/attendance.png")));
		setTitle("Add Attendance");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		for(int i = 0; i < num.length; i++) {
			num[i] = i;
		}
		
		for(int i = 0; i < hr.length; i++) {
			hr[i] = i;
		}
		
		panelAttendance = new panelAttendance();
		
		JLabel attendanceName = new JLabel("Attendance Name: ");
		attendanceName.setBounds(10, 79, 262, 14);
		contentPanel.add(attendanceName);
		
		JLabel sectionName = new JLabel("Section Name: ");
		sectionName.setBounds(10, 36, 72, 14);
		contentPanel.add(sectionName);
		
		JLabel departmentName = new JLabel("Department Name: ");
		departmentName.setBounds(10, 11, 97, 14);
		contentPanel.add(departmentName);
		
		JPanel panelSetTimer = new JPanel();
		panelSetTimer.setBounds(10, 123, 180, 68);
		contentPanel.add(panelSetTimer);
		panelSetTimer.setVisible(false);
		panelSetTimer.setLayout(null);
		
		JLabel lblHour = new JLabel("Hours:");
		lblHour.setBounds(10, 11, 46, 14);
		panelSetTimer.add(lblHour);
		
		JLabel lblMinute = new JLabel("Minutes:");
		lblMinute.setBounds(66, 11, 46, 14);
		panelSetTimer.add(lblMinute);
		
		JLabel lblSeconds = new JLabel("Seconds:");
		lblSeconds.setBounds(122, 11, 46, 14);
		panelSetTimer.add(lblSeconds);
		
		JComboBox<Integer> cbHour = new JComboBox<Integer>();
		cbHour.setName("Hour");
		cbHour.setBounds(10, 36, 50, 22);
		cbHour.setSelectedItem(hr[0]);
		addItemsHour(cbHour);
		panelSetTimer.add(cbHour);
		
		JComboBox<Integer> cbMinute = new JComboBox<Integer>();
		cbMinute.setName("Minute");
		cbMinute.setBounds(66, 36, 50, 22);
		cbMinute.setSelectedItem(num[0]);
		addItems(cbMinute);
		panelSetTimer.add(cbMinute);
		
		JComboBox<Integer> cbSecond = new JComboBox<Integer>();
		cbSecond.setName("Second");
		cbSecond.setBounds(122, 36, 50, 22);
		cbSecond.setSelectedItem(num[0]);
		addItems(cbSecond);
		panelSetTimer.add(cbSecond);
		
		cbHour.addItemListener(new selectedItem(cbHour));
		cbMinute.addItemListener(new selectedItem(cbMinute));
		cbSecond.addItemListener(new selectedItem(cbSecond));
		
		JCheckBox withTimer = new JCheckBox("With Time Limit");
		withTimer.setBounds(10, 198, 97, 23);
		contentPanel.add(withTimer);
		
		JComboBox<String> cbDept = new JComboBox<String>();
		cbDept.setBounds(117, 7, 155, 22);
		cbDept.addItem("Select a department");
		dept(cbDept);
		cbDept.addItemListener(new selectedDept(cbDept));
		contentPanel.add(cbDept);
		
		cbSection = new JComboBox<String>();
		cbSection.setBounds(92, 32, 180, 22);
		cbSection.addItem("Select a department first");
		cbSection.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					if(cbSection.getSelectedIndex() == 0) {
						attendanceName.setText("Attendance Name: ");
					} else {
						obtainedSec = cbSection.getSelectedItem().toString();
						checkRecordCount();
						Date date = new Date();
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						month = new SimpleDateFormat("MMM").format(cal.getTime());
						day = cal.get(Calendar.DAY_OF_MONTH);
						year = cal.get(Calendar.YEAR);
						name = obtainedDept+"-"+obtainedSec+" | "+month+" "+day+", "+year;
						attendanceName.setText("Attendance Name: " + name); 
					}
				}
				revalidate();
				repaint();
			}
		});
		contentPanel.add(cbSection);
		
		JLabel warningLabel = new JLabel("");
		warningLabel.setBounds(295, 177, 129, 40);
		warningLabel.setForeground(Color.RED);
		contentPanel.add(warningLabel);
		
		withTimer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					panelSetTimer.setVisible(true);
				} else {
					panelSetTimer.setVisible(false);
					cbHour.setSelectedItem(hr[0]);
					cbMinute.setSelectedItem(num[0]);
					cbSecond.setSelectedItem(num[0]);
				}
				revalidate();
				repaint();
			}
		});

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton createButton = new JButton("Create");
				createButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(cbDept.getSelectedIndex() == 0 || cbSection.getSelectedIndex() == 0) {
							warningLabel.setText("<html>Set all the Department</br> and the Sections!</html>");
							Timer timer = new Timer(3000, new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									warningLabel.setText("");
								}
							});
							timer.setRepeats(false);
							timer.start();
						} else {
							try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
								PreparedStatement getStatement = conn.prepareStatement("insert into attendancerecords (record_name, sectionname, departmentname, schoolname, timecreated, timelimit, currenttime, timeexpires) values (?,?,?,?,CURRENT_TIMESTAMP,TIME(\"" + selectedHour + ":" + selectedMinute + ":" + selectedSecond + "\"),CURRENT_TIMESTAMP, ADDTIME(attendancerecords.timecreated, attendancerecords.timelimit))");
								getStatement.setString(1, obtainedDept+"-"+obtainedSec+" | "+month+" "+day+", "+year);
								getStatement.setString(2, obtainedSec);
								getStatement.setString(3, obtainedDept);
								getStatement.setString(4, Login.pubSchoolName);
								int result = getStatement.executeUpdate();
								if(result == 1) {
									dispose();
									JOptionPane.showMessageDialog(null, "Successfully created!");
									revalidate();
									repaint();
								}	
							} catch (SQLException sql) {
								sql.printStackTrace();
							}
						}
					}
				});
				createButton.addMouseListener(new PropertiesListener(createButton));
				createButton.setActionCommand("Create");
				buttonPane.add(createButton);
				getRootPane().setDefaultButton(createButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						isCancelled = true;
					}
				});
				cancelButton.addMouseListener(new PropertiesListener(cancelButton));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
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
			cb.addItem("Select a section.");
			while(result.next()) {
				String obtainedString = result.getString("sectionname");
				cb.addItem(obtainedString);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void addItems(JComboBox<Integer> cb) {
		for(int i = 0; i < num.length; i++) {
			cb.addItem(num[i]);
		}
	}
	
	private void addItemsHour(JComboBox<Integer> cb) {
		for(int i = 0; i < hr.length; i++) {
			cb.addItem(hr[i]);
		}
	}
	
	private void checkRecordCount() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement getStatement = conn.prepareStatement("select count(record_name) from attendancerecords where sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			if(result.next()) {
				currentRecordCount = result.getInt("count(record_name)");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private class selectedItem implements ItemListener {
		JComboBox<Integer> cb;
		
		private selectedItem (JComboBox<Integer> cb) {
			this.cb = cb;
		}
		
		public void itemStateChanged(ItemEvent e) {
			JComboBox<?> cbS = (JComboBox<?>) e.getSource();
			String obtainedName = cbS.getName();
			if(e.getStateChange() == ItemEvent.SELECTED) {
				int obtainedNum = Integer.valueOf(cb.getSelectedItem().toString());
				if(obtainedName.equals("Hour")) {
					selectedHour = obtainedNum;
				} else if (obtainedName.equals("Minute")) {
					selectedMinute = obtainedNum;
				} else if (obtainedName.equals("Second")) {
					selectedSecond = obtainedNum;
				}
			}
		}
	}
	
	private class selectedDept implements ItemListener {
		
		JComboBox<String> cb;
		
		private selectedDept (JComboBox<String> cb) {
			this.cb = cb;
		}
		
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				obtainedDept = cb.getSelectedItem().toString();
				sec(cbSection);
			}
		}	
	}
}
