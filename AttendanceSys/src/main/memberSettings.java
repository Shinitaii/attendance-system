package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class memberSettings extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public String obtainedUser;
	public JLabel lblCurrentUserSelected;
	String[] roles;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			memberSettings dialog = new memberSettings();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public memberSettings() {
		super(null, ModalityType.TOOLKIT_MODAL);
		setIconImage(Toolkit.getDefaultToolkit().getImage(memberSettings.class.getResource("/res/attendance.png")));
		setTitle("Edit Role");
		setBounds(100, 100, 250, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		if(Login.pubOccupation.equals("Admin")) {
			roles = new String[]{"Student", "Teacher", "Admin"};
		} else {
			roles = new String[]{"Student", "Teacher", "Admin", "Head Admin"};
		}
		
		lblCurrentUserSelected = new JLabel();
		lblCurrentUserSelected.setBounds(10, 10, 416, 13);
		contentPanel.add(lblCurrentUserSelected);
		
		JLabel lblInstruction = new JLabel("Select a role to change:");
		lblInstruction.setBounds(10, 33, 120, 13);
		contentPanel.add(lblInstruction);
		
		JTextPane roleDesc = new JTextPane();
		roleDesc.setEditable(false);
		roleDesc.setText("Student are responsible for attending in their classes, they can only view most of the menus here and can only press a button to attend on an attendance record created by a teacher.");
		roleDesc.setBounds(10, 88, 214, 129);
		contentPanel.add(roleDesc);
		
		JComboBox<String> cbRoles = new JComboBox<String>();
		for(int i = 0; i < roles.length; i++) {
			cbRoles.addItem(roles[i]);
		}
		cbRoles.setBounds(10, 56, 110, 22);
		cbRoles.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbRoles.getSelectedIndex() == 0) {
					roleDesc.setText("Students are responsible for attending in their classes, they can only view most of the menus here and can only press a button to attend on an attendance record created by a teacher.");
				} else if (cbRoles.getSelectedIndex() == 1) {
					roleDesc.setText("Teachers are responsible for creating an attendance record on a certain subject of a certain section. Like students, they can only view most of the menus here and can only create an attendance record for students to press.");
				} else if (cbRoles.getSelectedIndex() == 2) {
					roleDesc.setText("Admins are responsible for managing all of the things in the school. They can create departments, sections, subjects, attendance records, edit roles of an user and can remove/kick an user.");
				} else {
					roleDesc.setText("Head Admins are more powerful than Admins along side having the same power as Admins.");
				}
				revalidate();
				repaint();
			}
		});
		contentPanel.add(cbRoles);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						int result = JOptionPane.showConfirmDialog(null, "Are you sure you're going to make "+obtainedUser+" as "+cbRoles.getSelectedItem()+"?", "Note!", JOptionPane.YES_NO_OPTION);
						if(result == JOptionPane.YES_OPTION) {
							try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
								PreparedStatement getStatement = conn.prepareStatement("update userinfo set occupation='"+cbRoles.getSelectedItem()+"' where concat(firstname, ' ', middlename, ' ', lastname)='"+obtainedUser+"' and schoolname='"+Login.pubSchoolName+"' and inviteCodeOfSchool='"+Login.pubInviteCode+"'");
								if(cbRoles.getSelectedItem() == "Head Admin") {
									setVisible(false);
									int r = JOptionPane.showConfirmDialog(null, "Do you really want to transfer your ownership to "+obtainedUser+"?", "Last Warning!", JOptionPane.YES_NO_OPTION);
									if(r == JOptionPane.YES_OPTION) {
										int sqlResult = getStatement.executeUpdate();
										PreparedStatement getStatement2 = conn.prepareStatement("update userinfo set occupation='Admin' where concat(firstname, ' ', middlename, ' ', lastname)='"+Login.pubFullName+"' and schoolname='"+Login.pubSchoolName+"' and inviteCodeOfSchool='"+Login.pubInviteCode+"'");
										getStatement2.executeUpdate();
										if(sqlResult == 1) {
											JOptionPane.showMessageDialog(null, "Successfully changed the role of "+obtainedUser+" to "+cbRoles.getSelectedItem()+"!");
											MainMenu.panelMembros.model.setRowCount(0);
											MainMenu.panelMembros.checkList();
											MainMenu.panelMembros.transferred = true;
										}
									}
								} else {
									int sqlResult = getStatement.executeUpdate();
									if(sqlResult == 1) {
										JOptionPane.showMessageDialog(null, "Successfully changed the role of "+obtainedUser+" to "+cbRoles.getSelectedItem()+"!");
										MainMenu.panelMembros.model.setRowCount(0);
										MainMenu.panelMembros.checkList();
										if(cbRoles.getSelectedItem() == "Teacher") {
											MainMenu.menuClicked(MainMenu.TeacherAssignDept);
											MainMenu.TeacherAssignDept.obtainedTeacherName = obtainedUser;
											MainMenu.TeacherAssignDept.lblSec.setText("How many subjects will "+obtainedUser+" handle?");
											MainMenu.TeacherAssignDept.listCB.clear();
											MainMenu.TeacherAssignDept.obtainedDeptNames.clear();
											MainMenu.TeacherAssignDept.obtainedSecNames.clear();
											MainMenu.TeacherAssignDept.obtainedSubNames.clear();
											MainMenu.TeacherAssignDept.execute();
										} else {										
											PreparedStatement deleteExisting = conn.prepareStatement("delete from teacherassignedinfo where teachername=?");
											deleteExisting.setString(1, obtainedUser);
											deleteExisting.executeUpdate();
										}
									} else {
										JOptionPane.showMessageDialog(null, "Error!");
									}
								}	
								revalidate();
								repaint();
							} catch (SQLException sql) {
								sql.printStackTrace();
							}
						} else {
							setVisible(true);
						}
					}
				});
				saveButton.addMouseListener(new PropertiesListener(saveButton));
				saveButton.setActionCommand("Save");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.addMouseListener(new PropertiesListener(cancelButton));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
