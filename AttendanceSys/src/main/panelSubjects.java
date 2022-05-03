package main;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class panelSubjects extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JComboBox<String> cbName, cbSec;
	DefaultTableModel model;
	List<JButton> buttonNames = new ArrayList<JButton>();
	List<String> listSubNames = new ArrayList<String>();
	private boolean isDeletingSub = false;
	public static boolean isCancelled = false;
	public String obtainedSub, obtainedDept, selectedSec;
	private JTable table;
	private String nameAoD = "asc";
	
	public panelSubjects() {
		setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 11));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(new Color(255, 255, 255));
		setBounds(new Rectangle(0, 0, 559, 539));
		setLayout(null);
		
		cbName = new JComboBox<String>();
		cbName.addItem("Sort Name: A to Z");
		cbName.addItem("Sort name: Z to A");
		cbName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbName.getSelectedIndex() == 0) {
					nameAoD = "asc";
				} else {
					nameAoD = "desc";
				}
				model.setRowCount(0);
				checkList();
			}
		});
		cbName.setBounds(349, 36, 90, 22);
		add(cbName);
		
		
		cbSec = new JComboBox<String>();
		cbSec.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbSec.getSelectedIndex() > 0) {
					selectedSec = cbSec.getSelectedItem().toString();
					cbName.setSelectedIndex(0);
					model.setRowCount(0);
					checkList();
				}
				
			}
		});
		cbSec.setBounds(459, 36, 90, 22);
		add(cbSec);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.menuClicked(MainMenu.SubjectSelectDepartment);
				model.setRowCount(0);
			}
		});
		backButton.addMouseListener(new PropertiesListener(backButton));
		backButton.setBounds(10, 11, 55, 45);
		add(backButton);
		
		JLabel lblDelete = new JLabel("");
		lblDelete.setBounds(10, 67, 315, 14);
		add(lblDelete);
		
		JButton addSubject = new JButton("Add Subject");
		addSubject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isDeletingSub = false;
				lblDelete.setText("");
				try {
					subjectSettings dialog = new subjectSettings();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception dialog) {
					dialog.printStackTrace();
				}
				model.setRowCount(0);
				checkList();
				revalidate();
				repaint();
			}
		});
		addSubject.addMouseListener(new PropertiesListener(addSubject));
		addSubject.setBounds(75, 11, 120, 45);
		add(addSubject);
		
		JButton deleteSubject = new JButton("Delete Subject");
		deleteSubject.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!isDeletingSub) {
					isDeletingSub = true;
					lblDelete.setText("Click on a subject to delete.");
				} else {
					isDeletingSub = false;
					lblDelete.setText("");
				}
				
				if(isDeletingSub) {
					table.getSelectionModel().addListSelectionListener(deleteRow);
				} else {
					table.getSelectionModel().removeListSelectionListener(deleteRow);
				}
			}
		});
		deleteSubject.addMouseListener(new PropertiesListener(deleteSubject));
		deleteSubject.setBounds(205, 11, 120, 45);
		add(deleteSubject);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 92, 539, 436);
		add(scrollPane);
		
		if(!Login.pubOccupation.equals("Student")) {
			model = new DefaultTableModel(new String[] {"Subject","Description","Section", "Teacher"}, 0) {
				/**
			 	* 
			 	*/
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		} else {
			model = new DefaultTableModel(new String[] {"Subject", "Description", "Teacher"}, 0) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		}
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBorder(null);
		table.setModel(model);
		TableColumnModel columnModel = table.getColumnModel();
		table.setRowHeight(50);
		columnModel.getColumn(0).setPreferredWidth(125);
	    columnModel.getColumn(0).setMaxWidth(125);
	    columnModel.getColumn(1).setPreferredWidth(300);
	    columnModel.getColumn(1).setMaxWidth(300);
		if(!Login.pubOccupation.equals("Student")) {
			columnModel.getColumn(2).setPreferredWidth(50);
			columnModel.getColumn(2).setMaxWidth(50);
			columnModel.getColumn(3).setPreferredWidth(120);
			columnModel.getColumn(3).setMaxWidth(120);
		} else {
			columnModel.getColumn(2).setPreferredWidth(170);
			columnModel.getColumn(2).setMaxWidth(170);
		}
		
		JLabel lblSortName = new JLabel("Sort Name:");
		lblSortName.setBounds(349, 11, 90, 14);
		add(lblSortName);
		
		JLabel lblSortSec = new JLabel("Sort Section:");
		lblSortSec.setBounds(459, 11, 90, 14);
		add(lblSortSec);

		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMinWidth(150);
		table.getTableHeader().setReorderingAllowed(false);
		
		if(Login.pubOccupation.equals("Teacher")) {
			addSubject.setVisible(false);
			deleteSubject.setVisible(false);
			cbName.setVisible(false);
			cbSec.setVisible(false);
			lblSortName.setVisible(false);
			lblSortSec.setVisible(false);
		} else if(Login.pubOccupation.equals("Student")) {
			addSubject.setVisible(false);
			deleteSubject.setVisible(false);
			backButton.setVisible(false);
			cbName.setVisible(false);
			cbSec.setVisible(false);
			lblSortName.setVisible(false);
			lblSortSec.setVisible(false);
		}

	}
	
	public void execute() {
		section(cbSec);
		checkList();
		revalidate();
		repaint();
	}
	
	public void executeForStudents() {
		cbSec.setVisible(false);
		checkList();
		revalidate();
		repaint();
	}
	
	private void section(JComboBox<String>cb) {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
			String normal = "select sectionname from sectioninfo where departmentname='"+MainMenu.SubjectSelectDepartment.selectedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'";
			String teacher = "select sectionname from teacherassignedinfo where teachername='"+Login.pubFN+" "+Login.pubMN+" "+Login.pubLN+"' and departmentname='"+MainMenu.SubjectSelectDepartment.selectedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'";
			PreparedStatement getStatement;
			if(Login.pubOccupation.equals("Admin")) {
				getStatement = conn.prepareStatement(normal);
			} else {
				getStatement = conn.prepareStatement(teacher);
			}
			ResultSet result = getStatement.executeQuery();
			cb.addItem("Select a section");
			while(result.next()) {
				String obtainedSec = result.getString("sectionname");
				cbSec.addItem(obtainedSec);
			}
		} catch(SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkList() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){		
			String query;
			if(Login.pubOccupation.equals("Teacher")) {
				query = "select subjectname, sectionname, subjectdesc, teachername from subjectinfo where teachername='"+Login.pubFN+" "+Login.pubMN+" "+Login.pubLN+"' and departmentname='"+MainMenu.SubjectSelectDepartment.selectedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'";
			} else if (Login.pubOccupation.equals("Admin")) {
				query = "select subjectname, sectionname, subjectdesc, teachername from subjectinfo where departmentname='"+MainMenu.SubjectSelectDepartment.selectedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'";
			} else {
				query = "select subjectname, subjectdesc, teachername from subjectinfo where sectionname='"+Login.pubSecName+"' and departmentname='"+Login.pubDeptName+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'";
			}
			String orderBy = "order by";
			String selectSec = "sectionname ='"+selectedSec+"' desc, subjectname " + nameAoD;
			PreparedStatement puttingInTable;
			if(cbSec.getSelectedIndex() == 0) {
				if(cbSec.isVisible()) {
					puttingInTable = conn.prepareStatement(query + " " + orderBy + " subjectname " + nameAoD);
				} else {
					puttingInTable = conn.prepareStatement(query);
				}
			} else {
				puttingInTable = conn.prepareStatement(query + " " + orderBy + " " + selectSec);
			}
			ResultSet result = puttingInTable.executeQuery();
			while(result.next()) {
				String sub = result.getString("subjectname");
				String desc = result.getString("subjectdesc");
				String teacher = result.getString("teachername");
				if(!Login.pubOccupation.equals("Student")) {
					String sec = result.getString("sectionname");
					model.addRow(new Object[] {sub, desc, sec, teacher});
				} else {
					model.addRow(new Object[] {sub, desc, teacher});	
				}
			}
			revalidate();
			repaint();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private ListSelectionListener deleteRow = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()){
				if (table.getSelectedRow() > -1) {
					String value = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
					try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
						int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+value+"?\r\nDeleting the subject will also delete all the records and teachers assigned inside of the subject!\r\nIt will also get deleted from all sections!", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if(result == JOptionPane.YES_OPTION) {
							PreparedStatement getStatement = conn.prepareStatement("delete from subjectinfo where subjectname='"+value+"' and departmentname='"+MainMenu.SubjectSelectDepartment.selectedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
							PreparedStatement getSecondStatement = conn.prepareStatement("delete from teacherassignedinfo where subjectname='"+value+"' and departmentname='"+MainMenu.SubjectSelectDepartment.selectedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
							PreparedStatement getThirdStatement = conn.prepareStatement("delete from attendancerecords where subjectname='"+value+"' and departmentname='"+MainMenu.SubjectSelectDepartment.selectedDept+"' and schoolname='"+Login.pubSchoolName+"' and schoolid='"+Login.pubSchoolID+"'");
							getStatement.executeUpdate();
							getSecondStatement.executeUpdate();
							getThirdStatement.executeUpdate();
						}
						model.setRowCount(0);
						checkList();
					} catch (SQLException sql) {
						sql.printStackTrace();
					}
		   	   	}
			}
		}
	};
}
