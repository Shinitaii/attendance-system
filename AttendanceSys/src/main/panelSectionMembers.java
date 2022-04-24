package main;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ScrollPaneConstants;

public class panelSectionMembers extends JPanel {
	/**
      	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private boolean isDeletingMembers = false, isAddingMembers = false;
	DefaultTableModel model;
	public String obtainedDept, obtainedSec;
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
				model.setRowCount(0);
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
				try {
					sectionMembersSettings dialog = new sectionMembersSettings();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.execute();
					dialog.setVisible(true);
				} catch (Exception dialog) {
					dialog.printStackTrace();
				}
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(65, 105, 225)));
		scrollPane.setBounds(0, 74, 559, 427);
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
		
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMinWidth(150);
		table.getTableHeader().setReorderingAllowed(false);
		
		revalidate();
		repaint();

	}
	
	public void checkList() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement puttingInTable = conn.prepareStatement("select concat(firstname, ' ', middlename, ' ', lastname) as fullname, occupation from userInfo where sectionname='"+obtainedSec+"' and  departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = puttingInTable.executeQuery();
			System.out.println(obtainedDept + obtainedSec);
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
