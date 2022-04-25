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
		statusButton.addMouseListener(new PropertiesListener(statusButton));
		statusButton.setBounds(479, 11, 70, 50);
		add(statusButton);
		
		revalidate();
		repaint();
	}
	
	public void execute() {
		checkList();
		recordStatusChecker();
	}
	
	private void checkList() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement puttingInTable = conn.prepareStatement("select concat(firstname, ' ', middlename, ' ', lastname) as fullname, studentstatus from attendancestatus where record_name='"+obtainedRecord+"' and sectionname='"+obtainedSec+"' and  departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
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
	
	private void recordStatusChecker() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement getStatement = conn.prepareStatement("select recordcompleted from attendancerecords where record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			if(result.next()) {
				recordStatusComplete = result.getBoolean("recordcompleted");
				System.out.println(recordStatusComplete);
			}
			if(recordStatusComplete) {
				statusButton.setText("Enable");
				statusButton.addActionListener(enabled);
				statusButton.removeActionListener(disable);
			} else {
				statusButton.setText("Disable");
				statusButton.addActionListener(disable);
				statusButton.removeActionListener(enabled);
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
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to enable the record?\rStudents clicking the attend button from now on will be labelled as \"Present\"! You may still re-enable this by pressing the 'Disable' button.", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);	
				if(result == JOptionPane.YES_OPTION) {
					PreparedStatement getStatement = conn.prepareStatement("update attendancerecords set recordcompleted=false where record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
					int sqlResult = getStatement.executeUpdate();
						System.out.println(obtainedRecord + " " + obtainedSub + " " + obtainedSec + " " + obtainedDept + " " +Login.pubSchoolName);
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
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to disable the record?\rStudents clicking the attend button from now on will be labelled as \"Present\"! You may still re-enable this by pressing the 'Disable' button.", "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);	
				if(result == JOptionPane.YES_OPTION) {
					PreparedStatement getStatement = conn.prepareStatement("update attendancerecords set recordcompleted=true where record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
					int sqlResult = getStatement.executeUpdate();
						System.out.println(obtainedRecord + " " + obtainedSub + " " + obtainedSec + " " + obtainedDept + " " +Login.pubSchoolName);
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
}
