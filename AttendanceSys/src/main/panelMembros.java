package main;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class panelMembros extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> listDeptNames = new ArrayList<String>();
	JComboBox<String> cbName,cbOccup,cbDept;
	private JTable table;
	private boolean isEditing = false;
	public DefaultTableModel model;
	private String selectedDept, nameAoD = "asc";
	/**
	 * Create the panel.
	 */
	public panelMembros() {
		setBounds(new Rectangle(0, 0, 559, 539));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 559, 68);
		add(panel);
		panel.setLayout(null);
		
		JButton editButton = new JButton("Edit Member");
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
		editButton.setBounds(10, 11, 120, 47);
		panel.add(editButton);
		
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
		cbName.setBorder(new LineBorder(new Color(65, 105, 225)));
		cbName.setBackground(Color.WHITE);
		cbName.setBounds(299, 36, 120, 22);
		panel.add(cbName);
		
		JLabel lblSortName = new JLabel("Sort Name:");
		lblSortName.setBounds(299, 11, 120, 14);
		panel.add(lblSortName);
		
		JLabel lblSortOccupation = new JLabel("Sort Occupation:");
		lblSortOccupation.setBounds(429, 11, 120, 14);
		panel.add(lblSortOccupation);
		
		cbOccup = new JComboBox<String>();
		cbOccup.addItem("Teachers first");
		cbOccup.addItem("Students first");
		cbOccup.addItem("Admins first");
		cbOccup.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cbName.setSelectedIndex(0);
				model.setRowCount(0);
				checkList();
			}
		});
		cbOccup.setBorder(new LineBorder(new Color(65, 105, 225)));
		cbOccup.setBounds(429, 36, 119, 22);
		panel.add(cbOccup);
		
		JLabel lblSortDept = new JLabel("Sort Department:");
		lblSortDept.setBounds(169, 11, 120, 14);
		panel.add(lblSortDept);
		
		cbDept = new JComboBox<String>();
		cbDept.addItem("No Department");
		dept(cbDept);
		cbDept.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				selectedDept = cbDept.getSelectedItem().toString();
				model.setRowCount(0);
				cbName.setSelectedIndex(0);
				cbOccup.setSelectedIndex(0);
				checkList();
			}
		});
		cbDept.setBorder(new LineBorder(new Color(65, 105, 225)));
		cbDept.setBackground(Color.WHITE);
		cbDept.setBounds(169, 36, 120, 22);
		panel.add(cbDept);
		
		JLabel lblTotalMem = new JLabel("Total Members :");
		lblTotalMem.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotalMem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalMem.setForeground(new Color(65, 105, 225));
		lblTotalMem.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblTotalMem.setBounds(397, 500, 125, 39);
		add(lblTotalMem);
		
		JLabel lblTotalNum = new JLabel();
		checkTotalMembers(lblTotalNum);
		lblTotalNum.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblTotalNum.setForeground(new Color(65, 105, 225));
		lblTotalNum.setBounds(524, 500, 35, 42);
		add(lblTotalNum);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(65, 105, 225)));
		scrollPane.setBounds(0, 67, 559, 434);
		add(scrollPane);
		
		model = new DefaultTableModel(new String[] {"Full Name","Department","Occupation"}, 0) {
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
		checkList();
		
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMinWidth(150);
		table.getTableHeader().setReorderingAllowed(false);
		
		revalidate();
		repaint();
	}
	
	public void checkList() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			String query = "select concat(firstname, ' ', middlename, ' ', lastname) as fullname, departmentname, occupation from userInfo where schoolname='"+Login.pubSchoolName+"'";
			String orderBy = "order by";
			String teachers = "occupation = 'Teacher' desc, occupation = 'Student' desc, fullname "+nameAoD+"";
			String students = "occupation = 'Student' desc, occupation = 'Teacher' desc, fullname "+nameAoD+"";
			String admins = "occupation = 'Admin' desc, occupation = 'Teacher' desc, fullname "+nameAoD+"";
			String orderDept = "departmentname ='"+selectedDept+"' desc,";
			PreparedStatement puttingInTable;
			if(cbDept.getSelectedIndex() == 0) {
				if(cbOccup.getSelectedIndex() == 0) {
					puttingInTable = conn.prepareStatement(query + " " + orderBy + " " + teachers);
				} else if (cbOccup.getSelectedIndex() == 1) {
					puttingInTable = conn.prepareStatement(query + " " + orderBy + " " + students);
				} else {
					puttingInTable = conn.prepareStatement(query + " " + orderBy + " " + admins);
				}
			} else {
				if(cbOccup.getSelectedIndex() == 0) {
					puttingInTable = conn.prepareStatement(query + " " + orderBy + " " + orderDept + " " + teachers);
				} else if (cbOccup.getSelectedIndex() == 1) {
					puttingInTable = conn.prepareStatement(query + " " + orderBy + " " + orderDept + " " + students);
				} else {
					puttingInTable = conn.prepareStatement(query + " " + orderBy + " " + orderDept + " " + admins);
				}
			}
			ResultSet result = puttingInTable.executeQuery();
			while(result.next()) {
				String name = result.getString("fullname");
				String dept = result.getString("departmentname");
				String occ = result.getString("occupation");
				model.addRow(new Object[] {name, dept, occ});
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
		   			   memberSettings dialog = new memberSettings();
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
	
	private void dept(JComboBox<String> cb) {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement getStatement = conn.prepareStatement("select departmentname from departmentinfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			while(result.next()) {
				String obtainedDept = result.getString("departmentname");
				cbDept.addItem(obtainedDept);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkTotalMembers(JLabel label) {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement getStatement = conn.prepareStatement("select count(concat(firstname, ' ', middlename, ' ', lastname)) as fullname from userInfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			if(result.next()) {
				int num = result.getInt("fullname");
				label.setText(String.valueOf(num));
			}
		} catch(SQLException sql) {
			
		}
	}
	
}
