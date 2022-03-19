  package main;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Rectangle;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

public class panelDepartment extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel MainContent;
	List<String> listDeptNames = new ArrayList<String>();
	List<JButton> buttonNames = new ArrayList<JButton>();
	List<List<JButton>> listButtonNames = new ArrayList<>();
	public static String[] deptNames;
	int count = 0;
	JButton existingButton, newButton, buttonToDelete;
	String checkedName = "";
	boolean isDeletingDepts = false;
	public static String selectedDept;
	panelSections panelSections;
	/**
	 * Create the button.
	 */
	public panelDepartment() {
		setBounds(new Rectangle(0, 0, 559, 539));
		setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		panelSections = new panelSections();
		panelSections.setVisible(false);
		add(panelSections);
		
		MainContent = new JPanel();
		MainContent.setBorder(new LineBorder(new Color(65, 105, 225)));
		MainContent.setBackground(Color.WHITE);       
		MainContent.setBounds(10, 102, 539, 426);
		add(MainContent);
		MainContent.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel buttonSelection = new JPanel();
		buttonSelection.setBackground(new Color(255, 255, 255));
		buttonSelection.setBounds(10, 11, 539, 58);
		add(buttonSelection);
		buttonSelection.setLayout(null);
		
		JLabel lblSelectToDelete = new JLabel("Click on a department to delete.");
		lblSelectToDelete.setVisible(false);
		lblSelectToDelete.setBounds(10, 80, 154, 12);
		add(lblSelectToDelete);

		JButton addDept = new JButton("Add Department");
		addDept.addMouseListener(new PropertiesListener(addDept));
		addDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isDeletingDepts = false;
				lblSelectToDelete.setVisible(false);
				String obtainedDept = "";
				obtainedDept = JOptionPane.showInputDialog(null, "Input Department Name: ");
				if(obtainedDept == null) {
					JOptionPane.showMessageDialog(null, "Input Department Name!");
				} else {
					try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
						String checkedName = "";
						PreparedStatement checkingDupe = conn.prepareStatement("select departmentname from departmentinfo where departmentname='"+obtainedDept+"'");
						ResultSet result = checkingDupe.executeQuery();
						while(result.next()) {
							checkedName = result.getString("departmentname");
						}
						if(obtainedDept.equals(checkedName)) {
							JOptionPane.showMessageDialog(null, "Duplicate Department Name!");
						} else {
							newButton = new JButton(obtainedDept);
							newButton.setLayout(new BorderLayout());
							buttonNames.add(newButton);
							newButton.addMouseListener(new PropertiesListener(newButton));							
							PreparedStatement addDept = conn.prepareStatement("insert into departmentinfo (departmentname, schoolname) values (?, ?)");
							addDept.setString(1, obtainedDept);
							addDept.setString(2, Login.pubSchoolName);
							int result2 = addDept.executeUpdate();
							if(result2 == 1) {
								PreparedStatement checkDeptNames = conn.prepareStatement("select departmentname from departmentinfo where schoolname='"+Login.pubSchoolName+"'");
								ResultSet checkedNames = checkDeptNames.executeQuery();
								while(checkedNames.next()) {
									String deptName = checkedNames.getString("departmentname");	
									listDeptNames.add(deptName);
								}
								newButton = buttonNames.get(count);
								buttonNames.get(count).setName(obtainedDept);
								MainContent.add(newButton);
								listButtonNames.add(buttonNames);
							}
							recountCheck();
							revalidate();
							repaint();
						}
					} catch (SQLException sql) {
						sql.printStackTrace();						
					}
				}		
			}
		});
		addDept.setBorder(null);
		addDept.setBounds(10, 0, 150, 58);
		buttonSelection.add(addDept);
		
		JButton deleteDept = new JButton("Delete Department");
		deleteDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == deleteDept) {
				isDeletingDepts = true;
				lblSelectToDelete.setVisible(true);
				} else {
					isDeletingDepts = true;
				}
				if(isDeletingDepts) {
					for(int i = 0; i <= buttonNames.size() - 1; i++) {
						int index = i;
						String whatDept = buttonNames.get(index).getName();
						buttonNames.get(i).addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
									PreparedStatement deleteDept = conn.prepareStatement("delete from departmentinfo where departmentname='"+whatDept+"' and schoolname='"+Login.pubSchoolName+"'");
									deleteDept.executeUpdate();
									JButton source = (JButton) ae.getSource();
									int select = JOptionPane.showConfirmDialog(null, "You sure you want to delete "+source.getName()+"?", "Delete", JOptionPane.YES_NO_OPTION);
									if(select == JOptionPane.YES_OPTION) {
									MainContent.remove(source);
									buttonNames.remove(source);
									}
									recountCheck();
									revalidate();
									repaint();
								} catch (SQLException sql) {
									sql.printStackTrace();
								}
							}
						});
					}
				}
			}
		});
		deleteDept.addMouseListener(new PropertiesListener(deleteDept));
		deleteDept.setBorder(null);
		deleteDept.setBounds(170, 0, 150, 58);
		buttonSelection.add(deleteDept);
		
		recountCheck();	
		recheckName();
		
		for(int i = 0; i < count; i++){
			int index = i;
			existingButton = new JButton(listDeptNames.get(i));
			existingButton.setName(listDeptNames.get(i));
			buttonNames.add(existingButton);
			existingButton = buttonNames.get(i);
			existingButton.setLayout(new BorderLayout());				
			existingButton.addMouseListener(new PropertiesListener(existingButton));
			String whatDept = buttonNames.get(index).getName();
			buttonNames.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedDept = whatDept;
					setVisible(false);
					
					revalidate();
					repaint();

				}
			});
			MainContent.add(existingButton);
		}
	}
	
	private void recountCheck() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement checkCount = conn.prepareStatement("select count(departmentname) from departmentinfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet checkedCount = checkCount.executeQuery();
			while(checkedCount.next()) {
				count = checkedCount.getInt("count(departmentname)");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void recheckName() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement checkDeptNames = conn.prepareStatement("select departmentname from departmentinfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet checkedNames = checkDeptNames.executeQuery();
			while(checkedNames.next()) {
				String deptName = checkedNames.getString("departmentname");	
				listDeptNames.add(deptName);
			}
			deptNames = listDeptNames.toArray(new String[listDeptNames.size()]);
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
}