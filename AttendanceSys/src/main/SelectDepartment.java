package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class SelectDepartment extends JDialog {

	private final JPanel contentPanel = new JPanel();
	String obtainedResult;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SelectDepartment dialog = new SelectDepartment();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SelectDepartment() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new LineBorder(new Color(65, 105, 225)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblSelectDept = new JLabel("Select Department:");
		lblSelectDept.setBounds(10, 11, 93, 14);
		contentPanel.add(lblSelectDept);
		
		String[] deptNames = panelDepartment.deptNames;
		JComboBox<String> comboBox = new JComboBox<>(deptNames);
		obtainedResult = comboBox.getItemAt(comboBox.getSelectedIndex());

		comboBox.setBounds(113, 7, 135, 22);
		contentPanel.add(comboBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(65, 105, 225)));
			buttonPane.setBackground(new Color(255, 255, 255));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			
			JButton joinDept = new JButton("Join Department");
			joinDept.addMouseListener(new PropertiesListener(joinDept));
			joinDept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
						PreparedStatement join = conn.prepareStatement("update userinfo set departmentname='"+obtainedResult+"' where userid ="+Login.pubUID);
						
					} catch (SQLException sql) {
						sql.printStackTrace();
					}
				}
			});
			buttonPane.add(joinDept);
		}
	}
}
