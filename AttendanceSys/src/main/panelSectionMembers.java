package main;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class panelSectionMembers extends JPanel {
	/**
      	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table_1;
	private boolean isDeletingMembers = false, isAddingMembers = false;
	DefaultTableModel model;
	private String obtainedDept = AdminMenu.panelSections.whatSec;
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
				AdminMenu.menuClicked(AdminMenu.panelSections);
			}
		});
		btnDelete.addMouseListener(new PropertiesListener(btnDelete));
		btnDelete.setBounds(10, 11, 100, 40);
		add(btnDelete);
		
		JButton addMember = new JButton("Add Member");
		addMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isAddingMembers = true;
				isDeletingMembers = false;
				
			}
		});
		addMember.addMouseListener(new PropertiesListener(addMember));
		addMember.setBounds(120, 11, 100, 40);
		add(addMember);
		
		JButton deleteMember = new JButton("Delete Member");
		deleteMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isDeletingMembers) {
					isDeletingMembers = true;
				} else {
					isDeletingMembers = false;
				}
			}
		});
		deleteMember.addMouseListener(new PropertiesListener(deleteMember));
		deleteMember.setBounds(230, 11, 105, 40);
		add(deleteMember);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 64, 539, 464);
		add(scrollPane_1);
		
		model = new DefaultTableModel(new String[] {"Full Name", "Occupation"}, 0) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table_1 = new JTable();
		table_1.setModel(model);
			
		scrollPane_1.setViewportView(table_1);
	}
	
	public void checkList() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement puttingInTable = conn.prepareStatement("select concat(firstname, ' ', middlename, ' ', lastname) as fullname, occupation from userInfo where departmentname='"+obtainedDept+" schoolname='"+Login.pubSchoolName+"'");
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
}
