package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherAssignDept extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<JCheckBox> listCB = new ArrayList<JCheckBox>();
	public List<String> obtainedDeptNames = new ArrayList<String>();
	public List<String> obtainedSecNames = new ArrayList<String>();
	public List<String> obtainedSubNames = new ArrayList<String>();
	private JTextField deptTF;
	private JPanel deptContent;
	private JButton doneButton;
	public int count, limitCounter = 0, limitCount;
	
	/**
	 * Create the panel.
	 */
	public TeacherAssignDept() {
		setBounds(0,0,559,539);
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setLayout(new BorderLayout(0, 0));
		
		JPanel deptAsk = new JPanel();
		deptAsk.setBackground(new Color(255, 255, 255));
		deptAsk.setBorder(new LineBorder(new Color(65, 105, 225)));
		add(deptAsk, BorderLayout.NORTH);
		deptAsk.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		deptAsk.add(panel);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.menuClicked(MainMenu.panelMembros);
			}		
		});
		backButton.addMouseListener(new PropertiesListener(backButton));
		deptAsk.add(backButton, BorderLayout.WEST);
		
		doneButton = new JButton("Assign");
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean selected;
				int result = 0;
				for(int i = 0; i < listCB.size(); i++) {
					selected = listCB.get(i).isSelected();
					if(selected) {
						try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
							PreparedStatement getStatement = conn.prepareStatement("insert into teacherassignedinfo (teachername, subjectname, sectionname, departmentname, schoolname) values (?,?,?,?,?)");
							getStatement.setString(1, Login.pubFN+" "+Login.pubMN+" "+Login.pubLN);
							getStatement.setString(2, obtainedSubNames.get(i));
							getStatement.setString(3, obtainedSecNames.get(i));
							getStatement.setString(4, obtainedDeptNames.get(i));
							getStatement.setString(5, Login.pubSchoolName);
							int success = getStatement.executeUpdate();
							if(success == 1) {
								result++;
							}
						} catch(SQLException sql) {
							sql.printStackTrace();
						}
					}
				}
				if(result >= 1) {
					JOptionPane.showMessageDialog(null, "Successfully assigned subjects!");
				} else {
					JOptionPane.showMessageDialog(null, "Error!");
				}
			}		
		});
		doneButton.addMouseListener(new PropertiesListener(doneButton));
		doneButton.setVisible(false);
		deptAsk.add(doneButton, BorderLayout.EAST);
		
		JLabel lblSec = new JLabel("How many subjects are you tasked to handle?");
		panel.add(lblSec);
		
		deptTF = new JTextField();
		panel.add(deptTF);
		deptTF.setColumns(2);		
		
		JButton enterButton = new JButton("Enter");
		panel.add(enterButton);
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doneButton.setVisible(false);
				limitCounter = 0;
				listCB.clear();
				deptContent.removeAll();
				limitCount = Integer.valueOf(deptTF.getText());
				addCheckBoxes();
				revalidate();
				repaint();
			}
		});
		enterButton.addMouseListener(new PropertiesListener(enterButton));
		
		deptContent = new JPanel();
		deptContent.setBackground(new Color(255, 255, 255));
		deptContent.setBorder(new LineBorder(new Color(65, 105, 225)));
		deptContent.setLayout(new GridLayout(0, 2, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane(deptContent, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);	
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void execute() {
		getSubjects(obtainedDeptNames, obtainedSecNames, obtainedSubNames);
	}
	
	private void getSubjects(List<String> list, List<String> list2, List<String> list3) {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement getStatement = conn.prepareStatement("select subjectname, sectionname ,departmentname from subjectinfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			while(result.next()) {
				String obtainedSub = result.getString("subjectname");
				String obtainedSec = result.getString("sectionname");
				String obtainedDept = result.getString("departmentname");
				list.add(obtainedDept);
				list2.add(obtainedSec);
				list3.add(obtainedSub);
			}
		} catch(SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void addCheckBoxes() {
		int height = 0;
		for(int i = 0; i < obtainedSubNames.size(); i++) {
			JCheckBox cb = new JCheckBox("Subject: "+ obtainedSubNames.get(i)+" - Department: "+obtainedDeptNames.get(i)+" - Section: "+obtainedSecNames.get(i));
			cb.setName(obtainedSubNames.get(i)+"-"+obtainedDeptNames.get(i)+":"+obtainedSecNames.get(i));
			cb.setBackground(Color.WHITE);
			listCB.add(cb);
			listCB.get(i).addItemListener(checkBoxListener);
			deptContent.add(cb);
			height += 33;
		}
		deptContent.setPreferredSize(new Dimension(0, height));
		revalidate();
		repaint();
	}
	
	private ItemListener checkBoxListener = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				limitCounter++;
				if(limitCounter == limitCount) {
					for(int i = 0; i < listCB.size(); i++) {
						listCB.get(i).setEnabled(listCB.get(i).isSelected()); // all unchecked will be disabled
					}
					doneButton.setVisible(true);
				}
			} else {
				limitCounter--;
				for(int i = 0; i < listCB.size(); i++) {
				listCB.get(i).setEnabled(true); // all disabled checkboxes will be enabled
				}
				doneButton.setVisible(false);
			}
			revalidate();
			repaint();
		}
	};

}
