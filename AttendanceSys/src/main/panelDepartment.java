  package main;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class panelDepartment extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel MainContent;
	List<String> listDeptNames = new ArrayList<>();
	List<JPanel> panelNames = new ArrayList<>();
	public static String[] deptNames;
	int count = 0;
	JPanel panel;
	
	/**
	 * Create the panel.
	 */
	public panelDepartment() {
		setBounds(new Rectangle(0, 0, 559, 539));
		setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		MainContent = new JPanel();
		MainContent.setBorder(new LineBorder(new Color(65, 105, 225)));
		MainContent.setBackground(Color.WHITE);       
		MainContent.setBounds(10, 102, 539, 426);
		add(MainContent);
		MainContent.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel buttonSelection = new JPanel();
		buttonSelection.setBackground(new Color(255, 255, 255));
		buttonSelection.setBounds(10, 11, 539, 80);
		add(buttonSelection);
		buttonSelection.setLayout(null);
		
		JButton addDept = new JButton("Add Department");
		addDept.addMouseListener(new PropertiesListener(addDept));
		addDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String obtainedDept = "";
				try {
					obtainedDept = JOptionPane.showInputDialog(null, "Input Department Name: ");
					if(obtainedDept.isEmpty()) {
						// error
					} else {
						try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
							PreparedStatement addDept = conn.prepareStatement("insert into departmentinfo (departmentname, schoolname) values (?, ?)");
							addDept.setString(1, obtainedDept);
							addDept.setString(2, Login.pubSchoolName);
							int added = addDept.executeUpdate();
							if(added == 1) {
								PreparedStatement checkCount = conn.prepareStatement("select count(departmentname) from departmentinfo where schoolname='"+Login.pubSchoolName+"'");
								ResultSet checkedCount = checkCount.executeQuery();
								while(checkedCount.next()) {
									count = checkedCount.getInt("count(departmentname)");
								}
								
								panel = new JPanel();
								panel.setLayout(new BorderLayout());
								panelNames.add(panel);
								panel.addMouseListener(new PropertiesListener(panel));
								JLabel deptname = new JLabel(obtainedDept);
								panel.add(deptname, BorderLayout.NORTH);
								
								invalidate();
								MainContent.add(panel);
							}	
							revalidate();
							repaint();
						} catch (SQLException sql) {
							sql.printStackTrace();
						}
					}
				} catch (NullPointerException np) {
					JOptionPane.showMessageDialog(null, "Input Department Name!");
				}
					
			}
		});
		addDept.setBorder(null);
		addDept.setBounds(10, 11, 150, 58);
		buttonSelection.add(addDept);
		
		JButton deleteDept = new JButton("Delete Department");
		deleteDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String whatDept = JOptionPane.showInputDialog(null, "Enter the department name to delete:");
					if(whatDept.isEmpty()) {
						// error
					} else {
						try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
							PreparedStatement deleteDept = conn.prepareStatement("delete from departmentinfo where departmentname='"+whatDept+"' and schoolname='"+Login.pubSchoolName+"'");
							int result = deleteDept.executeUpdate();
							if(result == 1) {
								PreparedStatement checkCount = conn.prepareStatement("select count(departmentname) from departmentinfo where schoolname='"+Login.pubSchoolName+"'");
								ResultSet checkedCount = checkCount.executeQuery();
								while(checkedCount.next()) {
									count = checkedCount.getInt("count(departmentname)");
								}
								panel = new JPanel();
								panel = panelNames.get(count);
								invalidate();
								
								MainContent.remove(panel);
							} else {
								JOptionPane.showMessageDialog(null, "Failed to delete department "+whatDept+"!");
							}
							
							revalidate();
							repaint();

						} catch (SQLException sql) {
							sql.printStackTrace();
						}
					}
				} catch (NullPointerException np) {
					JOptionPane.showMessageDialog(null, "Input department name to delete!");
				}
			}
		});
		deleteDept.addMouseListener(new PropertiesListener(deleteDept));
		deleteDept.setBorder(null);
		deleteDept.setBounds(170, 11, 150, 58);
		buttonSelection.add(deleteDept);
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement checkCount = conn.prepareStatement("select count(departmentname) from departmentinfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet checkedCount = checkCount.executeQuery();
			while(checkedCount.next()) {
				count = checkedCount.getInt("count(departmentname)");
			}
			
			PreparedStatement checkDeptNames = conn.prepareStatement("select departmentname from departmentinfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet checkedNames = checkDeptNames.executeQuery();
			while(checkedNames.next()) {
				String deptName = checkedNames.getString("departmentname");	
				listDeptNames.add(deptName);
			}
			
			deptNames = listDeptNames.toArray(new String[listDeptNames.size()]);
			
			for(int i = 0; i < count; i++){
				panel = new JPanel();
				panel.setLayout(new BorderLayout());
				panelNames.add(panel);
				panel.addMouseListener(new PropertiesListener(panel) {
					public void mouseClicked(MouseEvent e) {
						
					}
				});
				JLabel label = new JLabel(deptNames[i]);
				panel.add(label, BorderLayout.NORTH);
			}
			
			for(JPanel panel : panelNames) {
				MainContent.add(panel);
			}

		} catch (SQLException sql){
			sql.printStackTrace();
		}

	}
}
