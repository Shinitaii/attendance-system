	package main;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class Records extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> listDates = new ArrayList<String>();
	List<String> listTime = new ArrayList<String>();
	private JTable table;
	public String obtainedDept, obtainedSec, obtainedRecord, obtainedSub;
	public int obtainedID;
	private boolean isEditing = false, recordStatusComplete;
	DefaultTableModel model;
	JButton disableButton, statusButton;
	JLabel lblStatus;

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
		
		model = new DefaultTableModel(new String[] {"Full Name", "Status", "Time Attended"}, 0) {

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
		statusButton.setBounds(479, 11, 70, 30);
		add(statusButton);
		
		JButton exportButton = new JButton("Export");
		exportButton.setBounds(399, 11, 70, 30);
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser choose = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xls");
				choose.setFileFilter(filter);
				int option = choose.showSaveDialog(null);
				if(option == JFileChooser.APPROVE_OPTION) {
					String name = choose.getSelectedFile().getName();
					String path = choose.getSelectedFile().getParentFile().getAbsolutePath();
					String file;
					if(name.contains(".xls")) {
						file = path + "\\" + name;
					} else {
						file = path + "\\" + name + ".xls";
					}
					export(table, new File(file));
					JOptionPane.showMessageDialog(null, "Successfully exported to: "+ file +"!");
				}
			}
		});
		exportButton.addMouseListener(new PropertiesListener(exportButton));
		add(exportButton);
		
		lblStatus = new JLabel("Status:");
		lblStatus.setBounds(399, 47, 149, 14);
		add(lblStatus);
		
		JButton viewExcused = new JButton("View Excuse Requests");
		viewExcused.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.ViewExcuseRequest.obtainedRecord = obtainedRecord;
				MainMenu.ViewExcuseRequest.obtainedDept = obtainedDept;
				MainMenu.ViewExcuseRequest.obtainedSec = obtainedSec;
				MainMenu.ViewExcuseRequest.obtainedSub = obtainedSub;
				MainMenu.ViewExcuseRequest.execute();
				MainMenu.menuClicked(MainMenu.ViewExcuseRequest);
			}
		});
		viewExcused.addMouseListener(new PropertiesListener(viewExcused));
		viewExcused.setBounds(170, 11, 149, 50);
		add(viewExcused);
		
		if(Login.pubOccupation.equals("Teacher")) {
			editButton.setVisible(false);
			viewExcused.setBounds(90, 11, 149, 50);
		} else if(Login.pubOccupation.equals("Student")) {
			editButton.setVisible(false);
			statusButton.setVisible(false);
			exportButton.setVisible(false);
			viewExcused.setVisible(false);
			JButton attendButton = new JButton("Attend");
			attendButton.addActionListener(attend);
			attendButton.addMouseListener(new PropertiesListener(attendButton));
			attendButton.setBounds(90, 11, 70, 50);
			add(attendButton);
			JButton excusedButton = new JButton("Request Excuse");
			excusedButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
						PreparedStatement checkStatement = conn.prepareStatement("select hasAttended from attendancestatus where firstname='"+Login.pubFN+"' and middlename='"+Login.pubMN+"' and lastname='"+Login.pubLN+"' and recordid='"+obtainedID+"' and record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
						ResultSet ifAttended = checkStatement.executeQuery();
						if(ifAttended.next()) {
							boolean present = ifAttended.getBoolean("hasAttended");
							if(present) {
								JOptionPane.showMessageDialog(null, "You've attended, if you're excused midway in your class, ask your teacher to manually set your status to \"Excused\".");
							} else {
								PreparedStatement getStatement = conn.prepareStatement("select count(*) from excuserequests where fullname='"+Login.pubFullName+"'");
								ResultSet result = getStatement.executeQuery();
								if(result.next()) {
									int count = result.getInt("count(*)");
									if(count == 1) {
										JOptionPane.showMessageDialog(null, "You've already created an excuse letter!");
									} else {
										MakeExcuse dialog = new MakeExcuse();
										dialog.setVisible(true);
									}
								}
							}
						}
					} catch(SQLException sql) {
						sql.printStackTrace();
					}
				}
			});
			excusedButton.addMouseListener(new PropertiesListener(excusedButton));
			excusedButton.setBounds(170, 11, 120, 50);
			add(excusedButton);
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
			PreparedStatement puttingInTable = conn.prepareStatement("select concat(firstname, ' ', middlename, ' ', lastname) as fullname, studentstatus, date(timeattended) as date,time_format(timeattended, '%h:%i %p') as timeattended from attendancestatus where record_name='"+obtainedRecord+"' and sectionname='"+obtainedSec+"' and  departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"' order by lastname asc");
			ResultSet result = puttingInTable.executeQuery();
			while(result.next()) {
				String name = result.getString("fullname");
				String status = result.getString("studentstatus");
				String date = result.getString("date");
				if(date == null) {
					date = "";
				}
				String attend = result.getString("timeattended");
				if(attend == null) {
					attend = "";
				}
				listDates.add(date);
				listTime.add(attend);
				model.addRow(new Object[] {name, status, date+" "+attend});
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
				lblStatus.setText("Status: Completed");
			} else {
				statusButton.setText("Disable");
				lblStatus.setText("Status: Ongoing");
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
					PreparedStatement getSecondStatement = conn.prepareStatement("update attendancestatus set hasAttended=false where recordid='"+obtainedID+"' and record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
					getSecondStatement.executeUpdate();
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
					boolean ifAttended = false;
					String obtainedStatus = "";
					PreparedStatement checkIfAttended = conn.prepareStatement("select hasAttended, studentstatus from attendancestatus where firstname='"+Login.pubFN+"' and middlename='"+Login.pubMN+"' and lastname='"+Login.pubLN+"' and recordid='"+obtainedID+"' and record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
					ResultSet resultSet = checkIfAttended.executeQuery();
					if(resultSet.next()) {
						ifAttended = resultSet.getBoolean("hasAttended");
						obtainedStatus = resultSet.getString("studentstatus");
					}
					if(ifAttended) {
						JOptionPane.showMessageDialog(null,"You already attended! You cannot attend more than once!");
					} else {
						if(obtainedStatus == "Excused") {
							JOptionPane.showMessageDialog(null, "You are excused! You cannot attend however, you are noted by the teacher!");
						} else {
							PreparedStatement attend = conn.prepareStatement("update attendancestatus set hasAttended=true, studentstatus='"+status+"', timeattended=current_timestamp where firstname='"+Login.pubFN+"' and middlename='"+Login.pubMN+"' and lastname='"+Login.pubLN+"' and recordid='"+obtainedID+"' and record_name='"+obtainedRecord+"' and subjectname='"+obtainedSub+"' and sectionname='"+obtainedSec+"' and departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
							int attendResult = attend.executeUpdate();
							if(attendResult == 1) {
								if(ifRecordCompleted) {
									JOptionPane.showMessageDialog(null, "You have attended but you are labelled as \"Late\"!");
								} else {
									JOptionPane.showMessageDialog(null, "You have successfully attended!");
								}
							}
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
	
	private void export (JTable table, File file) {
		try {
			TableModel tm = table.getModel();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
	
			for(int i = 0; i < tm.getColumnCount() - 1; i++) {
				if(i==0){// columns
					bw.write(tm.getColumnName(i) + "\t\t\t");
				} else {
					bw.write(tm.getColumnName(i) + "\t");
				}
			}
			
			bw.write(tm.getColumnName(2));
			bw.write("\n");
			
			for(int i=0; i <tm.getRowCount(); i++) { // rows
				for(int j=0; j<tm.getColumnCount() - 1; j++) {
					if(j<1) {
						bw.write(tm.getValueAt(i,j).toString()+"\t\t\t");
					} else {
						bw.write(tm.getValueAt(i,j).toString() + "\t");
					}
				}
				bw.write(listDates.get(i)+"\t"+listTime.get(i));
				bw.write("\n");
			}
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
