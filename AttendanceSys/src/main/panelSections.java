package main;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class panelSections extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel sectionScreen;
	public JLabel currentDept;
	public List<String> listSecNames = new ArrayList<String>();
	public List<JButton> buttonNames = new ArrayList<JButton>();
	
	static int count = 0;
	private static JButton existingButton;
	private JButton newButton;
	static boolean isDeletingSec = false;
	boolean isAddingSec = false;
	public static String whatSec;
	/*
	 * Create the panel.
	 */
	public panelSections() {
		setBorder(null);
		setBackground(Color.WHITE);
		setBounds(0, 0, 559, 539);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 11, 539, 60);
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton backToDept = new JButton("Back");
		backToDept.addMouseListener(new PropertiesListener(backToDept));
		backToDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu.menuClicked(AdminMenu.panelDepartment);
				buttonNames.clear();
				listSecNames.clear();
				sectionScreen.removeAll();
				revalidate();
				repaint();
			}
		});
		panel.add(backToDept);
		
		JButton addSec = new JButton("Add Section");
		addSec.addMouseListener(new PropertiesListener(addSec));
		addSec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isDeletingSec = false;
				isAddingSec = true;
				String obtainedSec = JOptionPane.showInputDialog(null, "Input Section Name:");
				if(obtainedSec == null) {
					JOptionPane.showMessageDialog(null,"Input Section Name!");
					isAddingSec = false;
				} else {
					if(isAddingSec) {
						try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
							String checkedName = "";
							PreparedStatement checkDupe = conn.prepareStatement("select sectionname from sectioninfo where sectionname ='"+obtainedSec+"' and departmentname ='"+panelDepartment.whatDept+"' and schoolname = '"+Login.pubSchoolName+"'");
							ResultSet checking = checkDupe.executeQuery();
							while(checking.next()) {
								checkedName = checking.getString("sectionname");
							}
							if(checkedName.equals(obtainedSec)) {
								JOptionPane.showMessageDialog(null,"Duplicate Section Name!");
							} else {
								newButton = new JButton(obtainedSec);
								newButton.setLayout(new BorderLayout());
								buttonNames.add(newButton);
								newButton.addMouseListener(new PropertiesListener(newButton));
								PreparedStatement addSec = conn.prepareStatement("insert into sectioninfo (sectionname, departmentname, schoolname) values (?, ?, ?)");
								addSec.setString(1, obtainedSec);
								addSec.setString(2, panelDepartment.whatDept);
								addSec.setString(3, Login.pubSchoolName);
								int result = addSec.executeUpdate();
								if(result == 1) {
									recheckName();
									newButton = buttonNames.get(count);
									buttonNames.get(count).setName(obtainedSec);
									sectionScreen.add(newButton);
									buttonNames.get(count).addActionListener(new AddAndDeleteListener());
								}
								recheckCount();
								revalidate();
								repaint();
							}
						} catch (SQLException sql) {
							sql.printStackTrace();
						}
					} else {
						// cringe error
					}
				}
			}
		});
		panel.add(addSec);
		
		JButton deleteSec = new JButton("Delete Section");
		deleteSec.addMouseListener(new PropertiesListener(deleteSec));
		deleteSec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isDeletingSec) {
					isDeletingSec = false;

				} else {
					isDeletingSec = true;
				}
			}
		});
		panel.add(deleteSec);
		
		currentDept = new JLabel();
		currentDept.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(currentDept);
		
		sectionScreen = new JPanel();
		sectionScreen.setBorder(new LineBorder(new Color(65, 105, 225)));
		sectionScreen.setBackground(Color.WHITE);
		sectionScreen.setBounds(10, 82, 539, 446);
		add(sectionScreen);
		sectionScreen.setLayout(new GridLayout(0, 2, 0, 0));
		
		
		
		revalidate();
		repaint();
	}
	
	public void execute() {
		recheckCount();
		recheckName();
		addExistingSections();
	}
	
	private void recheckCount() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement checkCount = conn.prepareStatement("select count(sectionname) from sectioninfo where departmentname='"+panelDepartment.whatDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet checkedCount = checkCount.executeQuery();
			if(checkedCount.next()) {
				count = checkedCount.getInt("count(sectionname)");
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void recheckName() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement checkDeptNames = conn.prepareStatement("select sectionname from sectioninfo where departmentname='"+panelDepartment.whatDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet checkedNames = checkDeptNames.executeQuery();
			while(checkedNames.next()) {
				String secName = checkedNames.getString("sectionname");	
				listSecNames.add(secName);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void addExistingSections() {
		for(int i = 0; i < count; i++) {
			existingButton = new JButton(listSecNames.get(i));
			existingButton.setName(listSecNames.get(i));
			buttonNames.add(existingButton);
			existingButton = buttonNames.get(i);
			existingButton.setLayout(new BorderLayout());				
			existingButton.addMouseListener(new PropertiesListener(existingButton));
			buttonNames.get(i).addActionListener(new AddAndDeleteListener());
			sectionScreen.add(existingButton);
		}
	}
	
	private class AddAndDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!isDeletingSec) {
				AdminMenu.menuClicked(AdminMenu.panelSectionMembers);
				JButton source = (JButton) e.getSource();
				whatSec = buttonNames.get(buttonNames.indexOf(source)).getName();
			} else {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
					JButton source = (JButton) e.getSource();
					int select = JOptionPane.showConfirmDialog(null, "You sure you want to delete "+source.getName()+"?", "Delete", JOptionPane.YES_NO_OPTION);
					if(select == JOptionPane.YES_OPTION) {
						sectionScreen.remove(source);
						buttonNames.remove(source);
						PreparedStatement deleteDept = conn.prepareStatement("delete from sectioninfo where sectionname='"+source.getName()+"' and departmentname='"+panelDepartment.whatDept+"' and schoolname='"+Login.pubSchoolName+"'");
						deleteDept.executeUpdate();
						recheckCount();
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
