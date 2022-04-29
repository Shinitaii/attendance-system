	package main;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class Records extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	public String obtainedDept, obtainedSec, obtainedRecord, obtainedSub;
	private int obtainedID;
	private boolean isEditing = false, recordStatusComplete;
	DefaultTableModel model;
	JButton disableButton, statusButton;

	public Records() {
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(Color.WHITE);
		setBounds(0, 0, 559, 539);
		setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.menuClicked(MainMenu.panelAttendance);
				model.setRowCount(0);
			}
		});
		backButton.addMouseListener(new PropertiesListener(backButton));
		backButton.setBounds(10, 11, 70, 50);
		add(backButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 72, 539, 456);
		add(scrollPane);
		
		model = new DefaultTableModel(new String[] {"Full Name", "Status"}, 0) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBorder(null);
		table.setModel(model);
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isEditing) {
					isEditing = true;
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				} else {
					isEditing = false;
					table.setRowSelectionAllowed(false);
				}
				
				if(isEditing) {
					table.getSelectionModel().addListSelectionListener(selectedRow);
				} else {
					table.getSelectionModel().removeListSelectionListener(selectedRow);
				}
			}
		});
		editButton.addMouseListener(new PropertiesListener(editButton));
		editButton.setBounds(90, 11, 70, 50);
		add(editButton);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMinWidth(150);
		table.getTableHeader().setReorderingAllowed(false);
		
		statusButton = new JButton();
		statusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!recordStatusComplete) {
					statusButton.setText("Disable");
					statusButton.removeActionListener(enabled);
					statusButton.addActionListener(disable);
				} else {
					statusButton.setText("Enable");
					statusButton.removeActionListener(disable);
					statusButton.addActionListener(enabled);
				}
			}
		});
		statusButton.addMouseListener(new PropertiesListener(statusButton));
		statusButton.setBounds(479, 11, 70, 50);
		add(statusButton);
		
		if(Login.pubOccupation.equals("Teacher")) {
			editButton.setVisible(false);
		} else if(Login.pubOccupation.equals("Student")) {
			editButton.setVisible(false);
			statusButton.setVisible(false);
			JButton attendButton = new JButton("Attend");
			attendButton.addActionListener(attend);
			attendButton.addMouseListener(new PropertiesListener(attendButton));
			attendButton.setBounds(90, 11, 70, 50);
			add(attendButton);
		}
		
		revalidate();
		repaint();
	}
	
	public void execute() {
		checkList();
		getRecordID();
		recordStatusChecker();
	}
	
	private void checkList() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement puttingInTable = conn.prepareStatement("select concat(firstname, ' ', middlename, ' ', lastname) as fullname, studentstatus from attendancestatus where record_name='"+obtainedRecord+"' and sectionname='"+obtainedSec+"' and  departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet result = puttingInTable.executeQuery();
			while(result.next()) {
				String name = result.getString("fullname");
				String status = result.getString("studentstatus");
				model.addRow(new Object[] {name, status});
			}
			revalidate();
			repaint();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void getRecordID() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement getStatement = conn.prepareStatement("select recordid from attendancerecords where record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet result = getStatement.executeQuery();
			if(result.next()) {
				obtainedID = result.getInt("recordid");
			}
		} catch(SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void recordStatusChecker() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement getStatement = conn.prepareStatement("select recordcompleted from attendancerecords where recordid='"+obtainedID+"' and record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
			ResultSet result = getStatement.executeQuery();
			if(result.next()) {
				recordStatusComplete = result.getBoolean("recordcompleted");
			}
			if(recordStatusComplete) {
				statusButton.setText("Enable");
			} else {
				statusButton.setText("Disable");
			}
			revalidate();
			repaint();
		} catch(SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private ListSelectionListener selectedRow = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()){
				if (table.getSelectedRow() > -1) {
		    	   String value = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
		   		   try {
		   			   recordsSettings dialog = new recordsSettings();
		   			   dialog.obtainedUser = value;
		   			   dialog.lblCurrentUserSelected.setText("User: "+dialog.obtainedUser);
	    			   dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    			   dialog.setVisible(true);
	    		   } catch (Exception dialog) {
		    		   dialog.printStackTrace();
		    	   }
		   	   }
	       }
		}
	};
	
	private ActionListener enabled = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to enable the record?\r\nStudents clicking the attend button from now on will be labelled as \"Present\"!\r\nYou may still re-enable this by pressing the 'Disable' button.", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);	
				if(result == JOptionPane.YES_OPTION) {
					PreparedStatement getStatement = conn.prepareStatement("update attendancerecords set recordcompleted=false where recordid='"+obtainedID+"' and record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
					int sqlResult = getStatement.executeUpdate();
						if(sqlResult == 1) {
							JOptionPane.showMessageDialog(null, "You enabled the record: "+obtainedRecord+"!");
							model.setRowCount(0);
							execute();
						} else {
							JOptionPane.showMessageDialog(null, "Error!");
						}
					}
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
		};
	};
	
	private ActionListener disable = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to disable the record?\r\nStudents clicking the attend button from now on will be labelled as \"Late\"!\r\nYou may still re-enable this by pressing the 'Disable' button.", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);	
				if(result == JOptionPane.YES_OPTION) {
					PreparedStatement getStatement = conn.prepareStatement("update attendancerecords set recordcompleted=true where recordid='"+obtainedID+"' and record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
					int sqlResult = getStatement.executeUpdate();
						if(sqlResult == 1) {
							JOptionPane.showMessageDialog(null, "You disabled the record: "+obtainedRecord+"!");
							model.setRowCount(0);
							execute();
						} else {
							JOptionPane.showMessageDialog(null, "Error!");
						}
					}
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
		};
	};
	
	private ActionListener attend = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
				boolean ifRecordCompleted = false;
				PreparedStatement getStatement = conn.prepareStatement("select recordcompleted from attendancerecords where recordid='"+obtainedID+"' and record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");	
				ResultSet result = getStatement.executeQuery();
				if(result.next()) {
					ifRecordCompleted = result.getBoolean("recordcompleted");
					String status ="";
					if(ifRecordCompleted) {
						status = "Late";
					} else {
						status = "Present";
					}
					PreparedStatement attend = conn.prepareStatement("update attendancestatus set studentstatus='"+status+"' where firstname='"+Login.pubFN+"' and middlename='"+Login.pubMN+"' and lastname='"+Login.pubLN+"' and recordid='"+obtainedID+"' and record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
					int attendResult = attend.executeUpdate();
					if(attendResult == 1) {
						if(ifRecordCompleted) {
							JOptionPane.showMessageDialog(null, "You have attended but you are labelled as \"Late\"!");
						} else {
							JOptionPane.showMessageDialog(null, "You have successfully attended!");
						}
					}
				}
				model.setRowCount(0);
				checkList();
			} catch(SQLException sql) {
				sql.printStackTrace();
			}
		}
	};
	
}
