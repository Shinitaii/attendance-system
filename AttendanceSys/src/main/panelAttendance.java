package main;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

import javax.swing.border.LineBorder;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class panelAttendance extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> listSubNames = new ArrayList<String>();
	List<String> listSecNames = new ArrayList<String>();
	List<String> listDeptNames = new ArrayList<String>();
	List<String> listTeacherNames = new ArrayList<String>();
	List<String> listRecordNames = new ArrayList<String>();
	List<Date> listDateNames = new ArrayList<Date>();
	JComboBox<String> cbDate = new JComboBox<String>();
	List<JPanel> panelNames = new ArrayList<JPanel>();
	
	private int count = 0;
	public String obtainedSec;
	public String obtainedSub;
	public String obtainedDept;
	public String selectedSub;
	public boolean addingRecords = false, deletingRecords = false, newRecord;
	private boolean sortingDate = false;
	JPanel mainScreen;
	JPanel panel;
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
						panel = new JPanel(new GridLayout(0,1,0,0));
						JLabel label = new JLabel(dialog.obtainedDept+"-"+dialog.obtainedSec+"-"+dialog.obtainedSub);
						label.setForeground(Color.white);
						label.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
						label.setHorizontalAlignment(SwingConstants.CENTER);
						JLabel label2 = new JLabel(dialog.year+"-"+dialog.month+"-"+dialog.day);
						label2.setForeground(Color.white);
						label2.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
						label2.setHorizontalAlignment(SwingConstants.CENTER);
						JLabel label3 = new JLabel("Teacher: "+Login.pubFullName);
						label3.setForeground(Color.white);
						label3.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
						label3.setHorizontalAlignment(SwingConstants.CENTER);
						panel.add(label);
						panel.add(label2);
						panel.add(label3);

						if(!sortingDate) {
							panelNames.add(0, panel);
							panelNames.get(0).addMouseListener(new PropertiesListener(panelNames.get(0)));
							panelNames.get(0).addMouseListener(new AddDelete());
							panelNames.get(0).setName(dialog.name);
						} else {
							panelNames.add(panel);
							panel = panelNames.get(count);	
							panelNames.get(count).addMouseListener(new PropertiesListener(panelNames.get(count)));
							panelNames.get(count).addMouseListener(new AddDelete());
							panelNames.get(count).setName(dialog.name);
						}
						if(dialog.cbSub.getSelectedIndex() > 0) { // will add a button if selected sort is default
							if(!sortingDate) {
								mainScreen.removeAll();
								checkName();
								checkCount();
								existingRecords();
							} else {
								mainScreen.add(panel);	
							}
						} else { // will always secretly remove the created record section isn't the same as the selected sort
							if(!sortingDate) {
								panelNames.remove(0);
							} else {
								panelNames.remove(count);
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
		mainScreen.setLayout(new GridLayout(0, 2, 2, 2));
		
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
		
		if(Login.pubOccupation.equals("Student")) {
			addAttendance.setVisible(false);
			deleteAttendance.setVisible(false);
		}
		
	}
	
	public void execute() {
		mainScreen.removeAll();
		listRecordNames.clear();
		listSubNames.clear();
		listSecNames.clear();
		listDeptNames.clear();
		listDateNames.clear();
		listTeacherNames.clear();
		panelNames.clear();
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
			String sortingDeptSecAndSub = "select record_name, subjectname, sectionname, departmentname, timecreated, creator, UNIX_TIMESTAMP(timecreated) as created from attendancerecords where subjectname='"+obtainedSub+"' and departmentname='"+obtainedDept+"' and sectionname='"+obtainedSec+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'";
			String sortedDate = "order by created";
			PreparedStatement checkName;
			if(sortingDate) {
				checkName = conn.prepareStatement(sortingDeptSecAndSub + " " + sortedDate + " asc");
			} else {
				checkName = conn.prepareStatement(sortingDeptSecAndSub + " " + sortedDate + " desc");
			}
			ResultSet checking = checkName.executeQuery();
			while(checking.next()) {
				String rec = checking.getString("record_name");
				String sub = checking.getString("subjectname");
				String sec = checking.getString("sectionname");
				String dept = checking.getString("departmentname");
				Date date = checking.getDate("timecreated");
				String teacher = checking.getString("creator");
				listRecordNames.add(rec);
				listSubNames.add(sub);
				listSecNames.add(sec);
				listDeptNames.add(dept);
				listDateNames.add(date);
				listTeacherNames.add(teacher);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}	
	}
	
	private void existingRecords() {
		for(int i = 0; i < count; i++) {
			JPanel panel = new JPanel(new GridLayout(0,1,2,2));
			JLabel label = new JLabel(listDeptNames.get(i)+"-"+listSecNames.get(i)+"-"+listSubNames.get(i));
			label.setForeground(Color.white);
			label.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			JLabel label2 = new JLabel(listDateNames.get(i).toString());
			label2.setForeground(Color.white);
			label2.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
			label2.setHorizontalAlignment(SwingConstants.CENTER);
			JLabel label3 = new JLabel("Teacher: "+listTeacherNames.get(i));
			label3.setForeground(Color.white);
			label3.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
			label3.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label);
			panel.add(label2);
			panel.add(label3);
			
			
			panelNames.add(panel);
			panel = panelNames.get(i);
			panelNames.get(i).addMouseListener(new PropertiesListener(panelNames.get(i)));
			panelNames.get(i).addMouseListener(new AddDelete());
			panelNames.get(i).setName(listRecordNames.get(i));
			mainScreen.add(panel);
		}
		revalidate();
		repaint();
	}
	
	private class AddDelete implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			JPanel source = (JPanel) e.getSource();
			if(!deletingRecords) {
				MainMenu.menuClicked(MainMenu.records);
				MainMenu.records.obtainedDept = obtainedDept;
				MainMenu.records.obtainedSec = obtainedSec;
				MainMenu.records.obtainedSub = obtainedSub;
				MainMenu.records.obtainedRecord = source.getName();
				MainMenu.records.model.setRowCount(0);
				MainMenu.records.execute();
			} else {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
					if(deletingRecords) {
						int select = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+source.getName()+"?", "Delete", JOptionPane.YES_NO_OPTION);
						if(select == JOptionPane.YES_OPTION) {
							mainScreen.remove(source);
							panelNames.remove(source);
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

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
	}
}