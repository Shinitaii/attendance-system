package main;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class panelSectionMembers extends JPanel {
	/**
      	 * 
	 */
	private static final long serialVersionUID = 1L;
	JComboBox<String> cbName, cbOccup;
	private JTable table;
	private boolean isDeletingMembers = false;
	DefaultTableModel model;
	public String obtainedDept, obtainedSec;
	public boolean isCancelled = false;
	/**
	 * Create the panel.
	 */
	public panelSectionMembers() {
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(Color.WHITE);
		setBounds(0, 0, 559, 539);
		setLayout(null);
		
		JButton btnDelete = new JButton("Back");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.menuClicked(MainMenu.panelSections);
				model.setRowCount(0);
			}
		});
		btnDelete.addMouseListener(new PropertiesListener(btnDelete));
		btnDelete.setBounds(10, 11, 55, 54);
		add(btnDelete);
		
		JButton addMember = new JButton("Add Member");
		addMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isDeletingMembers = false;
				try {
					sectionMembersSettings dialog = new sectionMembersSettings();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.execute();
					dialog.setVisible(true);
					
					if(!isCancelled) {
					model.setRowCount(0);
					checkList();
					}
					isCancelled = false;
				} catch (Exception dialog) {
					dialog.printStackTrace();
				}
			}
		});
		addMember.addMouseListener(new PropertiesListener(addMember));
		addMember.setBounds(75, 11, 100, 54);
		add(addMember);
		
		JButton deleteMember = new JButton("Delete Member");
		deleteMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isDeletingMembers) {
					isDeletingMembers = true;
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				} else {
					isDeletingMembers = false;
					table.setRowSelectionAllowed(false);
				}
				
				if(isDeletingMembers) {
					table.getSelectionModel().addListSelectionListener(selectedRow);
				} else {
					table.getSelectionModel().removeListSelectionListener(selectedRow);
				}
			}
		});
		deleteMember.addMouseListener(new PropertiesListener(deleteMember));
		deleteMember.setBounds(185, 11, 105, 54);
		add(deleteMember);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(65, 105, 225)));
		scrollPane.setBounds(0, 76, 559, 452);
		add(scrollPane);
		
		model = new DefaultTableModel(new String[] {"Full Name","Occupation"}, 0) {
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
		
		cbName = new JComboBox<String>();
		cbName.addItem("Sort Name: A to Z");
		cbName.addItem("Sort name: Z to A");
		cbName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				model.setRowCount(0);
				checkList();
			}
		});
		cbName.setBorder(new LineBorder(new Color(65, 105, 225)));
		cbName.setBackground(Color.WHITE);
		cbName.setBounds(300, 43, 120, 22);
		add(cbName);
		
		cbOccup = new JComboBox<String>();
		cbOccup.addItem("Teachers first");
		cbOccup.addItem("Students first");
		cbOccup.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cbName.setSelectedIndex(0);
				model.setRowCount(0);
				checkList();
			}
		});
		cbOccup.setBorder(new LineBorder(new Color(65, 105, 225)));
		cbOccup.setBounds(430, 43, 119, 22);
		add(cbOccup);
		
		JLabel lblSortName = new JLabel("Sort Name:");
		lblSortName.setBounds(300, 18, 120, 14);
		add(lblSortName);
		
		JLabel lblSortOccupation = new JLabel("Sort Occupation:");
		lblSortOccupation.setBounds(430, 18, 120, 14);
		add(lblSortOccupation);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMinWidth(150);
		table.getTableHeader().setReorderingAllowed(false);
		
		revalidate();
		repaint();

	}
	
	public void checkList() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			String azTeachers = "select concat(firstname, ' ', middlename, ' ', lastname) as fullname, occupation from userInfo where occupation != 'Admin' and sectionname='"+obtainedSec+"' and  departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' order by occupation = 'Teacher' desc, occupation = 'Student' desc, fullname asc";
			String zaTeachers = "select concat(firstname, ' ', middlename, ' ', lastname) as fullname, occupation from userInfo where occupation != 'Admin' and sectionname='"+obtainedSec+"' and  departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' order by occupation = 'Teacher' desc, occupation = 'Student' desc, fullname desc";
			String azStudents = "select concat(firstname, ' ', middlename, ' ', lastname) as fullname, occupation from userInfo where occupation != 'Admin' and sectionname='"+obtainedSec+"' and  departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' order by occupation = 'Student' desc, occupation = 'Teacher' desc, fullname asc";
			String zaStudents = "select concat(firstname, ' ', middlename, ' ', lastname) as fullname, occupation from userInfo where occupation != 'Admin' and sectionname='"+obtainedSec+"' and  departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"' order by occupation = 'Student' desc, occupation = 'Teacher' desc, fullname desc";
			PreparedStatement puttingInTable;
			if(cbName.getSelectedIndex() == 0 && cbOccup.getSelectedIndex() == 0) { // if a-z, teachers first -> default
				puttingInTable = conn.prepareStatement(azTeachers);
			} else if (cbName.getSelectedIndex() == 1 && cbOccup.getSelectedIndex() == 0) { // if z-a, teachers first
				puttingInTable = conn.prepareStatement(zaTeachers);
			} else if (cbName.getSelectedIndex() == 0 && cbOccup.getSelectedIndex() == 1){ // if a-z, student first
				puttingInTable = conn.prepareStatement(azStudents);
			} else { // if z-a, student first
				puttingInTable = conn.prepareStatement(zaStudents);
			}
			ResultSet result = puttingInTable.executeQuery();
			while(result.next()) {
				String name = result.getString("fullname");
				String occ = result.getString("occupation");
				model.addRow(new Object[] {name, occ});
			}
			revalidate();
			repaint();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private ListSelectionListener selectedRow = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()){
				if (table.getSelectedRow() > -1) {
		    	   String value = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
		   		   try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
		   			   PreparedStatement getStatement = conn.prepareStatement("update userinfo set hasASec=false, sectionname=null where concat(firstname, ' ', middlename, ' ', lastname) = '"+value+"' and schoolname='"+Login.pubSchoolName+"'");
		   			   getStatement.executeUpdate();
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
