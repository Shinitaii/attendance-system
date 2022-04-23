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
import java.awt.Rectangle;
import java.awt.Font;

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
	private boolean isDeletingSecs = false;
	/*
	 * Create the panel.
	 */
	public panelSections() {
		setBounds(new Rectangle(0, 0, 559, 539));
		setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setBackground(Color.WHITE);
		setBounds(0, 0, 559, 539);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 11, 539, 60);
		add(panel);
		
		JButton backToDept = new JButton("Back");
		backToDept.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		backToDept.setBackground(new Color(65, 105, 225));
		backToDept.setForeground(new Color(255, 255, 255));
		backToDept.setBorder(null);
		backToDept.setBounds(1, 0, 134, 49);
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
		panel.setLayout(null);
		panel.add(backToDept);
		
		JButton addSec = new JButton("Add Section");
		addSec.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		addSec.setBackground(new Color(65, 105, 225));
		addSec.setForeground(new Color(255, 255, 255));
		addSec.setBorder(null);
		addSec.setBounds(145, 0, 134, 49);
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
		
		JLabel lblSelectSection = new JLabel("Select a section.");
		lblSelectSection.setBounds(10, 71, 328, 14);
		add(lblSelectSection);
		
		JButton deleteSec = new JButton("Delete Section");
		deleteSec.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		deleteSec.setBackground(new Color(65, 105, 225));
		deleteSec.setForeground(new Color(255, 255, 255));
		deleteSec.setBorder(null);
		deleteSec.setBounds(289, 0, 134, 49);
		deleteSec.addMouseListener(new PropertiesListener(deleteSec));
		deleteSec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isDeletingSecs) {
					isDeletingSecs = true;
					lblSelectSection.setText("Click on a Section to delete. Click on the \"Delete Section\" again to stop deleting.");
				} else {
					isDeletingSecs = false;
					lblSelectSection.setText("Click on a Section to select its sections.");
				}
			}
		});
		panel.add(deleteSec);
		
		currentDept = new JLabel();
		currentDept.setBounds(433, 0, 104, 60);
		currentDept.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(currentDept);
		
		sectionScreen = new JPanel();
		sectionScreen.setBounds(new Rectangle(0, 0, 539, 426));
		sectionScreen.setBorder(new LineBorder(new Color(65, 105, 225)));
		sectionScreen.setBackground(Color.WHITE);
		sectionScreen.setBounds(10, 96, 539, 432);
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
