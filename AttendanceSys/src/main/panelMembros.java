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

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class panelMembros extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textField;
	private boolean isEditing = false;
	public DefaultTableModel model;
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
		panel.setBounds(0, 0, 559, 76);
		add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		btnNewButton.setBackground(new Color(65, 105, 225));
		btnNewButton.setBorder(null);
		btnNewButton.setBounds(484, 11, 65, 23);
		panel.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBorder(new LineBorder(new Color(65, 105, 225)));
		textField.setBounds(330, 11, 150, 22);
		panel.add(textField);
		textField.setColumns(10);
		
		JComboBox<String> cbDept = new JComboBox<String>();
		cbDept.setBackground(new Color(255, 255, 255));
		cbDept.setBorder(new LineBorder(new Color(65, 105, 225)));
		cbDept.setBounds(369, 43, 85, 22);
		panel.add(cbDept);
		
		JComboBox<String> cbOccup = new JComboBox<String>();
		cbOccup.setBorder(new LineBorder(new Color(65, 105, 225)));
		cbOccup.setBounds(464, 43, 85, 22);
		panel.add(cbOccup);
		
		JLabel lblNewLabel = new JLabel("Sort :");
		lblNewLabel.setBounds(330, 47, 32, 18);
		panel.add(lblNewLabel);
		
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
		editButton.setBounds(10, 11, 120, 55);
		panel.add(editButton);
		
		JLabel lblTotalMem = new JLabel("Total Members :");
		lblTotalMem.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTotalMem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalMem.setForeground(new Color(65, 105, 225));
		lblTotalMem.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblTotalMem.setBounds(397, 500, 125, 39);
		add(lblTotalMem);
		
		JLabel lblTotalNum = new JLabel("");
		lblTotalNum.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblTotalNum.setForeground(new Color(65, 105, 225));
		lblTotalNum.setBounds(524, 500, 35, 42);
		add(lblTotalNum);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(65, 105, 225)));
		scrollPane.setBounds(0, 74, 559, 427);
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
			PreparedStatement puttingInTable = conn.prepareStatement("select concat(firstname, ' ', middlename, ' ', lastname) as fullname, departmentname, occupation from userInfo where schoolname='"+Login.pubSchoolName+"'");
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
	
	public ListSelectionListener selectedRow = new ListSelectionListener() {
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
	
}
