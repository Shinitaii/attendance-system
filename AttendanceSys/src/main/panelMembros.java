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

public class panelMembros extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTable table;

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
		panel.setBounds(0, 0, 559, 65);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblSearchM = new JLabel("Search Members :");
		lblSearchM.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 25));
		lblSearchM.setForeground(new Color(65, 105, 225));
		lblSearchM.setBounds(10, 11, 226, 43);
		panel.add(lblSearchM);
		
		textField = new JTextField();
		textField.setBorder(new LineBorder(new Color(65, 105, 225)));
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField.setForeground(new Color(0, 0, 0));
		textField.setBounds(203, 13, 226, 43);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		btnNewButton.setBackground(new Color(65, 105, 225));
		btnNewButton.setBorder(null);
		btnNewButton.setBounds(439, 17, 110, 35);
		panel.add(btnNewButton);
		
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
		scrollPane.setBounds(0, 64, 559, 437);
		add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel(new String[] {"Full Name","Department","Occupation"}, 0);
		
		table = new JTable();
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
		table.setModel(model);

		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMinWidth(150);
		table.getTableHeader().setReorderingAllowed(false);
	}
}
