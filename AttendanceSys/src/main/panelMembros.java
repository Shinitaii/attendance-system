package main;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
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
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class panelMembros extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField textField;

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
		
		JButton btnAddMem = new JButton("Add Members");
		btnAddMem.setBackground(new Color(65, 105, 225));
		btnAddMem.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		btnAddMem.setForeground(new Color(255, 255, 255));
		btnAddMem.setBorder(null);
		btnAddMem.setBounds(10, 11, 150, 54);
		panel.add(btnAddMem);
		
		JButton btnDeleteMem = new JButton("Delete Members");
		btnDeleteMem.setForeground(new Color(255, 255, 255));
		btnDeleteMem.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		btnDeleteMem.setBackground(new Color(65, 105, 225));
		btnDeleteMem.setBorder(null);
		btnDeleteMem.setBounds(170, 11, 150, 54);
		panel.add(btnDeleteMem);
		
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
		
		DefaultTableModel model = new DefaultTableModel(new String[] {"Full Name","Department","Occupation"}, 0);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		scrollPane.setViewportView(table);
		table.setBorder(null);
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement puttingInTable = conn.prepareStatement("select concat(firstname, ' ', middlename, ' ', lastname) as fullname, departmentname, occupation from userInfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = puttingInTable.executeQuery();
			while(result.next()) {
				String name = result.getString("fullname");
				String dept = result.getString("departmentname");
				String occ = result.getString("occupation");
				model.addRow(new Object[] {name, dept, occ});
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Full Name", "Department", "Occupation"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMinWidth(150);
		table.getTableHeader().setReorderingAllowed(false);
	}
}
