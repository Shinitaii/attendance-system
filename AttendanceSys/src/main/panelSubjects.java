package main;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.border.LineBorder;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class panelSubjects extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<JButton> buttonNames = new ArrayList<JButton>();
	List<String> listSubNames = new ArrayList<String>();
	private JPanel subjectScreen;
	private boolean isAddingSub = false, isDeletingSub = false;
	public static boolean isCancelled = false;
	private int count = 0;
	public String obtainedSub, obtainedDept;
	public panelSubjects() {
		setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 11));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(new Color(255, 255, 255));
		setBounds(new Rectangle(0, 0, 559, 539));
		setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMenu.menuClicked(AdminMenu.SubjectSelectDepartment);
			}
		});
		backButton.addMouseListener(new PropertiesListener(backButton));
		backButton.setBounds(10, 11, 55, 45);
		add(backButton);
		
		JButton addSubject = new JButton("Add Subject");
		addSubject.addActionListener(new AddDeleteListener() {
			public void actionPerformed(ActionEvent e) {
				isAddingSub = true;
				isDeletingSub = false;
				try {
					subjectSettings dialog = new subjectSettings();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception dialog) {
					dialog.printStackTrace();
				}
				
				if(!isCancelled) {
				checkName();
				JButton button = new JButton(subjectSettings.subjectName);
				buttonNames.add(button);
				button = buttonNames.get(count);	
				buttonNames.get(count).addMouseListener(new PropertiesListener(buttonNames.get(count)));
				buttonNames.get(count).setName(subjectSettings.subjectName);
				buttonNames.get(count).addActionListener(new AddDeleteListener());
				subjectScreen.add(button);
				}
				checkCount();
				revalidate();
				repaint();
			}
		});
		addSubject.addMouseListener(new PropertiesListener(addSubject));
		addSubject.setBounds(75, 11, 120, 45);
		add(addSubject);
		
		JButton deleteSubject = new JButton("Delete Subject");
		deleteSubject.addActionListener(new AddDeleteListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isDeletingSub) {
					isDeletingSub = true;
				} else {
					isDeletingSub = false;
				}
			}
		});
		deleteSubject.addMouseListener(new PropertiesListener(deleteSubject));
		deleteSubject.setBounds(205, 11, 120, 45);
		add(deleteSubject);
		
		subjectScreen = new JPanel();
		subjectScreen.setBorder(new LineBorder(new Color(65, 105, 225)));
		subjectScreen.setBackground(new Color(255, 255, 255));
		subjectScreen.setBounds(10, 67, 539, 461);
		add(subjectScreen);
		subjectScreen.setLayout(new GridLayout(0, 2, 0, 0));

	}
	
	public void execute() {
		checkCount();
		checkName();
		existingSub();
		revalidate();
		repaint();
	}
	
	private void checkCount() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement getStatement = conn.prepareStatement("select count(subjectname) from subjectinfo where departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			if(result.next()) {
				count = result.getInt("count(subjectname)");
			}
			result.close();
			getStatement.close();
			conn.close();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkName() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
			PreparedStatement getStatement = conn.prepareStatement("select subjectname from subjectinfo where departmentname='"+obtainedDept+"' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = getStatement.executeQuery();
			if(isAddingSub) {
				if(result.next()) {
					String obtainedSub = result.getString("subjectname");
					listSubNames.add(obtainedSub);
				}
			} else {
				while(result.next()) {
					String obtainedSub = result.getString("subjectname");
					listSubNames.add(obtainedSub);
				}
			}
			result.close();
			getStatement.close();
			conn.close();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void existingSub() {
		for(int i = 0; i < count; i++) {
			JButton button = new JButton(listSubNames.get(i));
			buttonNames.add(button);
			button = buttonNames.get(i);
			buttonNames.get(i).setName(listSubNames.get(i));
			buttonNames.get(i).addMouseListener(new PropertiesListener(buttonNames.get(i)));
			buttonNames.get(i).addActionListener(new AddDeleteListener());
			subjectScreen.add(button);
		}
		revalidate();
		repaint();
	}
	
	private class AddDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!isDeletingSub) {
				
			} else {
				try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)) {
					
					conn.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
			}
		}
	}
}
