package main;

import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class panelSectionMembers extends JPanel {
	/**
      	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	public JPanel wholeScreen;

	/**
	 * Create the panel.
	 */
	public panelSectionMembers() {
		setBackground(Color.WHITE);
		setBounds(new Rectangle(0, 0, 559, 539));
		setLayout(null);
		
		wholeScreen = new JPanel();
		wholeScreen.setBackground(Color.WHITE);
		wholeScreen.setBounds(0, 0, 559, 539);
		add(wholeScreen);
		wholeScreen.setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu.menuClicked(AdminMenu.panelSections);
				revalidate();
				repaint();
			}
		});
		backButton.addMouseListener(new PropertiesListener(backButton));
		backButton.setBounds(10, 11, 80, 39);
		wholeScreen.add(backButton);
		
		JButton addMember = new JButton("Add Member");
		addMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addMember.addMouseListener(new PropertiesListener(addMember));
		addMember.setBounds(100, 11, 160, 39);
		wholeScreen.add(addMember);
		
		JButton deleteMember = new JButton("Delete Member");
		deleteMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		deleteMember.addMouseListener(new PropertiesListener(deleteMember));
		deleteMember.setBounds(270, 11, 160, 39);
		wholeScreen.add(deleteMember);
		
		scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(new LineBorder(new Color(65, 105, 225)));
		scrollPane.setBounds(10, 64, 539, 464);
		wholeScreen.add(scrollPane);
		
	}
	
	public void getTable() {
		
		DefaultTableModel model = new DefaultTableModel(new String[] {"Full Name","Occupation"}, 0);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		scrollPane.setViewportView(table);
		table.setBorder(null);
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement puttingInTable = conn.prepareStatement("select concat(firstname, ' ', middlename, ' ', lastname) as fullname, occupation from userInfo where departmentname='"+panelDepartment.whatDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = puttingInTable.executeQuery();
			while(result.next()) {
				String name = result.getString("fullname");
				String occ = result.getString("occupation");
				model.addRow(new Object[] {name, occ});
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Full Name", "Occupation"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMinWidth(150);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setResizable(false);
	}
}
