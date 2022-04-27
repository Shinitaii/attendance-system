package main;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
	private List<JCheckBox> listCB = new ArrayList<JCheckBox>();
	public List<String> obtainedDeptNames = new ArrayList<String>();
	public List<String> obtainedSecNames = new ArrayList<String>();
	public List<String> obtainedSubNames = new ArrayList<String>();
	private JTextField deptTF;
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
		deptAsk.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton backButton = new JButton("Back");
		deptAsk.add(backButton);
		
		JLabel lblSec = new JLabel("How many subjects are you tasked to handle?");
		deptAsk.add(lblSec);
		
		deptTF = new JTextField();
		deptAsk.add(deptTF);
		deptTF.setColumns(2);
		
		JButton enterButton = new JButton("Enter");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int amount = Integer.valueOf(deptTF.getText());
				for(int i = 0; i < amount; i++) {
					JCheckBox cb = new JCheckBox(obtainedSubNames.get(i));
				}
			}
		});
		enterButton.addMouseListener(new PropertiesListener(enterButton));
		deptAsk.add(enterButton);
		
		JPanel deptContent = new JPanel();
		deptContent.setBackground(new Color(255, 255, 255));
		deptContent.setBorder(new LineBorder(new Color(65, 105, 225)));
		add(deptContent, BorderLayout.CENTER);
		deptContent.setLayout(new GridLayout(0, 2, 0, 0));
	}
	
	public void getSubjects(List<String> list, List<String> list2) {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement getStatement = conn.prepareStatement("select subjectname, departmentname from subjectinfo where schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			while(result.next()) {
				String obtainedSub = result.getString("subjectname");
				String obtainedDept = result.getString("departmentname");
				list.add(obtainedDept);
				list2.add(obtainedSub);
			}
		} catch(SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private ItemListener checkBoxListener = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			JCheckBox source = (JCheckBox) e.getSource();
			if(e.getStateChange() == ItemEvent.SELECTED) {
				limitCounter++;
				if(limitCounter == limitCount) {
					for(int i = 0; i < listCB.size(); i++) {
						source.setEnabled(source.isSelected()); // all unchecked will be disabled
					}
				}
			} else {
				limitCounter--;
				for(int i = 0; i < listCB.size(); i++) {
				listCB.get(i).setEnabled(true); // all disabled checkboxes will be enabled
				}
			}
		}
	};

}
