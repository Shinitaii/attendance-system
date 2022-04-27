  package main;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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

	private static final long serialVersionUID = 1L;
	private JPanel MainContent;
	private JLabel lblSelectToDelete;
	private List<String> listDeptNames = new ArrayList<String>();
	private List<JButton> buttonNames = new ArrayList<JButton>();
	private int count = 0;
	private JButton existingButton, newButton, addDept, deleteDept;
	private boolean isDeletingDepts = false, isAddingDepts = false;
	public String whatDept;
	public static boolean hasOpenedADept = false;
          	
	public panelDepartment() {
		setBounds(new Rectangle(0, 0, 559, 539));
		setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		MainContent = new JPanel();
		MainContent.setBorder(new LineBorder(new Color(65, 105, 225)));
		MainContent.setBackground(Color.WHITE);       
		MainContent.setBounds(10, 102, 539, 426);
		MainContent.setLayout(new GridLayout(0, 2, 2, 2));
		
		JScrollPane scrollPane = new JScrollPane(MainContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10,102,539,426);
		add(scrollPane);
		
		JPanel buttonSelection = new JPanel();
		buttonSelection.setBackground(new Color(255, 255, 255));
		buttonSelection.setBounds(10, 11, 539, 58);
		add(buttonSelection);
		buttonSelection.setLayout(null);
		
		lblSelectToDelete = new JLabel("Click on a department to select its sections.");
		lblSelectToDelete.setVisible(true);
		lblSelectToDelete.setBounds(10, 80, 425, 12);
		add(lblSelectToDelete);

		addDept = new JButton("Add Department");
		addDept.addMouseListener(new PropertiesListener(addDept));
		addDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isDeletingDepts = false;
				isAddingDepts = true;
				String obtainedDept = "";
				obtainedDept = JOptionPane.showInputDialog(null, "Input Department Name: ");
				if(obtainedDept == null) {
					JOptionPane.showMessageDialog(null, "Input Department Name!");
					isAddingDepts = false;
				} else {
					if(isAddingDepts) {
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
									recheckName();
									newButton = buttonNames.get(count);
									buttonNames.get(count).setName(obtainedDept);
									MainContent.add(newButton);
									buttonNames.get(count).addActionListener(new AddDeleteListener());
								}
								isAddingDepts = false;
								recountCheck();
								revalidate();
								repaint();
							}
						} catch (SQLException sql) {
							sql.printStackTrace();						
						}
					} else {
						// cringe error
					}
					isAddingDepts = false;
				}		
			}
		});
		addDept.setBorder(null);
		addDept.setBounds(10, 0, 150, 58);
		buttonSelection.add(addDept);
		
		deleteDept = new JButton("Delete Department");
		deleteDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isDeletingDepts) {
					isDeletingDepts = true;
					lblSelectToDelete.setText("Click on a department to delete. Click on the \"Delete Department\" again to stop deleting.");
				} else {
					isDeletingDepts = false;
					lblSelectToDelete.setText("Click on a department to select its sections.");
				}
			}
		});
		deleteDept.addMouseListener(new PropertiesListener(deleteDept));
		deleteDept.setBorder(null);
		deleteDept.setBounds(170, 0, 150, 58);
		buttonSelection.add(deleteDept);
		
		
		
		if(!Login.pubOccupation.equals("Admin")) {
			buttonSelection.setVisible(false);
			lblSelectToDelete.setText("Click on a department to select its sections.");
			lblSelectToDelete.setBounds(10, 11, 425, 12);
			scrollPane.setBounds(10,30,539,499);
		}
		
		revalidate();
		repaint();
	}
	
	public void execute() {
		buttonNames.clear();
		listDeptNames.clear();
		MainContent.removeAll();
		recountCheck();
		recheckName();
		checkExistingDepts();
		revalidate();
		repaint();
	}
	
	public void executeForTeachers() {
		buttonNames.clear();
		listDeptNames.clear();
		MainContent.removeAll();
		recountCheckForTeachers();
		recheckNameForTeachers();
		checkExistingDepts();
		revalidate();
		repaint();
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
			if(!isAddingDepts) {
				while(checkedNames.next()) {
					String deptName = checkedNames.getString("departmentname");	
					listDeptNames.add(deptName);
				}
			} else {
				if(checkedNames.next()) {
					String deptName = checkedNames.getString("departmentname");	
					listDeptNames.add(deptName);
				}
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkExistingDepts() {
		int height = 0;
		for(int i = 0; i < count; i++){	
			existingButton = new JButton(listDeptNames.get(i));
			existingButton.setName(listDeptNames.get(i));
			buttonNames.add(existingButton);
			existingButton = buttonNames.get(i);
			existingButton.setLayout(new BorderLayout());				
			existingButton.addMouseListener(new PropertiesListener(existingButton));
			buttonNames.get(i).addActionListener(new AddDeleteListener());
			MainContent.add(existingButton);
			height += 33;
		}
		MainContent.setPreferredSize(new Dimension(0, height));
	}
	
	private void recountCheckForTeachers() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement checkCount = conn.prepareStatement("select count(departmentname) from teacherassignedinfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet checkedCount = checkCount.executeQuery();
			if(checkedCount.next()) {
				count = checkedCount.getInt("count(departmentname)");
			}
			if(count == 0) {
				JButton button = new JButton("Get assigned");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MainMenu.menuClicked(MainMenu.TeacherAssignDept);
						MainMenu.TeacherAssignDept.obtainedDeptNames.clear();
						MainMenu.TeacherAssignDept.obtainedSubNames.clear();
						MainMenu.TeacherAssignDept.getSubjects(MainMenu.TeacherAssignDept.obtainedDeptNames, MainMenu.TeacherAssignDept.obtainedSubNames);
					}
				});
				button.addMouseListener(new PropertiesListener(button));
				MainContent.add(button);
				lblSelectToDelete.setText("You do not have any departments, sections nor subjects assigned. Click the button to be assigned.");	
				lblSelectToDelete.setBounds(10, 11, 425, 12);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void recheckNameForTeachers() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement checkDeptNames = conn.prepareStatement("select departmentname from teacherassignedinfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet checkedNames = checkDeptNames.executeQuery();
			if(!isAddingDepts) {
				while(checkedNames.next()) {
					String deptName = checkedNames.getString("departmentname");	
					listDeptNames.add(deptName);
				}
			} else {
				if(checkedNames.next()) {
					String deptName = checkedNames.getString("departmentname");	
					listDeptNames.add(deptName);
				}
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private class AddDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!isDeletingDepts) {
				MainMenu.menuClicked(MainMenu.panelSections);
				JButton source = (JButton) e.getSource();
				whatDept = buttonNames.get(buttonNames.indexOf(source)).getName();
				MainMenu.panelSectionMembers.obtainedDept = whatDept;
				MainMenu.panelSections.currentDept.setText("Department: "+whatDept);
				MainMenu.panelSections.execute();
			} else {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
					JButton source = (JButton) e.getSource();
					whatDept = source.getName();
					int select = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+source.getName()+"?", "Delete", JOptionPane.YES_NO_OPTION);
					if(select == JOptionPane.YES_OPTION) {
						MainContent.remove(source);
						buttonNames.remove(source);
						PreparedStatement deleteDept = conn.prepareStatement("delete from departmentinfo where departmentname='"+source.getName()+"' and schoolname='"+Login.pubSchoolName+"'");
						deleteDept.executeUpdate();
						PreparedStatement deleteSec = conn.prepareStatement("delete from sectioninfo where departmentname='"+source.getName()+"' and schoolname='"+Login.pubSchoolName+"'");
						deleteSec.executeUpdate();
						recountCheck();
					}
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
			}
			revalidate();
			repaint();
		}
	}
}