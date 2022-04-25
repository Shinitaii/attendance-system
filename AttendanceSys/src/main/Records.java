package main;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JDialog;

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
	public String obtainedDept, obtainedSec, obtainedRecord;
	private boolean isEditing = false;
	DefaultTableModel model;

	public Records() {
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(Color.WHITE);
		setBounds(0, 0, 559, 539);
		setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu.menuClicked(AdminMenu.panelAttendance);
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
		
		revalidate();
		repaint();
	}
	
	public void checkList() {
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
}
