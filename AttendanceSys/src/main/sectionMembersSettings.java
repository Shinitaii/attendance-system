package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class sectionMembersSettings extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfNumber;
	private JPanel panelCheckBox;
	private String obtainedDept = AdminMenu.panelDepartment.whatDept, obtainedSec = AdminMenu.panelSections.whatSec;
	private int count = 0, limitCount = 0, limitCounter = 0;;
	private JCheckBox cb;
	List<String> listMemberNames = new ArrayList<String>();
	List<JCheckBox> listCB = new ArrayList<JCheckBox>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			sectionMembersSettings dialog = new sectionMembersSettings();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public sectionMembersSettings() {
		super(null, ModalityType.TOOLKIT_MODAL);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("How many users do you want to add?");
			lblNewLabel.setBounds(10, 10, 194, 13);
			contentPanel.add(lblNewLabel);
		}
		
		tfNumber = new JTextField("0");
		tfNumber.setBounds(214, 6, 30, 19);
		contentPanel.add(tfNumber);
		tfNumber.setColumns(10);
		
		panelCheckBox = new JPanel();
		panelCheckBox.setBounds(10, 33, 416, 189);
		contentPanel.add(panelCheckBox);
		panelCheckBox.setLayout(new GridLayout(5, 5, 0, 0));
		
		JButton enterButton = new JButton("Enter");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = tfNumber.getText();
				if(tfNumber.getText().matches("[0-9]")) {
					int value = Integer.valueOf(text);
					if(value > 0) {
						executeSettings();
						revalidate();
						repaint();
						limitCount = value;
					}
				} else {
					setVisible(false);
					JOptionPane.showMessageDialog(null, "Input a number!");
					setVisible(true);
				}
			}
		});
		enterButton.addMouseListener(new PropertiesListener(enterButton));
		enterButton.setBounds(254, 5, 67, 23);
		contentPanel.add(enterButton);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton addButton = new JButton("Add");
				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean selected;
						String obtainedName;
						int result = 0;
						for(int i = 0; i <listCB.size(); i++) {
							selected = listCB.get(i).isSelected();
							if(selected) {
								obtainedName = listCB.get(i).getName();
								try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
									PreparedStatement getStatement = conn.prepareStatement("update userinfo set hasADept= true, hasASec=true, sectionname='"+obtainedSec+"', departmentname='"+obtainedDept+"' where concat(firstname, ' ', middlename, ' ', lastname) ='"+obtainedName+"'");
									int success = getStatement.executeUpdate();
									if(success == 1) {
										result++;
									}
								} catch (SQLException sql) {
									sql.printStackTrace();
								}
							} 
						}
						if(result > 0) {
							dispose();
							JOptionPane.showMessageDialog(null, "Success!");
						} else {
							dispose();
							JOptionPane.showMessageDialog(null, "Error!");
						}
					}
				});
				addButton.addMouseListener(new PropertiesListener(addButton));
				addButton.setActionCommand("Add");
				buttonPane.add(addButton);
				getRootPane().setDefaultButton(addButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						AdminMenu.panelSectionMembers.isCancelled = true;
					}
				});
				cancelButton.addMouseListener(new PropertiesListener(cancelButton));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void execute() {
		checkCount();
		checkName();
	}
	
	public void executeSettings() {
		listCB.clear();
		listMemberNames.clear();
		panelCheckBox.removeAll();
		checkCount();
		checkName();
		addCheckBox();
	}
	
	private void checkCount() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement puttingInTable = conn.prepareStatement("select count(concat(firstname, ' ', middlename, ' ', lastname)) as fullname from userInfo where occupation !='Admin' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = puttingInTable.executeQuery();
			if(result.next()) {
				count = result.getInt("fullname");
			}
			revalidate();
			repaint();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void checkName() {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
			PreparedStatement puttingInTable = conn.prepareStatement("select concat(firstname, ' ', middlename, ' ', lastname) as fullname from userInfo where occupation !='Admin' and schoolname='"+Login.pubSchoolName+"'");
			ResultSet result = puttingInTable.executeQuery();
			while(result.next()) {
				String obtainedName = result.getString("fullname");
				listMemberNames.add(obtainedName);
			}
			revalidate();
			repaint();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void addCheckBox() {
		for(int i = 0; i < count; i++) {
			cb = new JCheckBox(listMemberNames.get(i));
			cb.setName(listMemberNames.get(i));
			listCB.add(cb);
			listCB.get(i).addItemListener(new checkCBLimit());
			panelCheckBox.add(cb);
		}
	}
	
	private class checkCBLimit implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				limitCounter++;
				if(limitCounter == limitCount) {
					for(int i = 0; i < listCB.size(); i++) {
						listCB.get(i).setEnabled(listCB.get(i).isSelected()); // all unchecked will be disabled
					}
				}
			} else {
				limitCounter--;
				for(int i = 0; i < listCB.size(); i++) {
				listCB.get(i).setEnabled(true); // all disabled checkboxes will be enabled
				}
			}
		}
		
	}
}
