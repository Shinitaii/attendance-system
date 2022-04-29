package main;

import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Toolkit;

public class SelectSchool extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtSchool;
	private JTextField txtInvite;
	String obtainedSchool = "";  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SelectSchool dialog = new SelectSchool();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SelectSchool() {
		super(null, ModalityType.TOOLKIT_MODAL);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SelectSchool.class.getResource("/res/attendance.png")));
		setTitle("School");
		
		setBounds(100, 100, 300, 200);
		getContentPane().setLayout(new BorderLayout());
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setForeground(new Color(65, 105, 225));
			tabbedPane.setBorder(new LineBorder(new Color(65, 105, 225)));
			tabbedPane.setBackground(Color.WHITE);
			getContentPane().add(tabbedPane, BorderLayout.CENTER);
			{	
				JPanel createSchool = new JPanel();
				createSchool.setBorder(new LineBorder(new Color(65, 105, 225)));
				createSchool.setBackground(new Color(65, 105, 225));
				tabbedPane.addTab("Create School", null, createSchool, "Create School!");
				tabbedPane.setBackgroundAt(0, new Color(65, 105, 225));
				createSchool.setLayout(new BorderLayout(0, 0));
				{
					JPanel csButtonPanel = new JPanel();
					createSchool.add(csButtonPanel, BorderLayout.SOUTH);
					csButtonPanel.setLayout(new GridLayout(0, 2, 0, 0));
					{
						JButton createButton = new JButton("Create");
						createButton.addMouseListener(new PropertiesListener(createButton) {
							@Override
							public void mouseClicked(MouseEvent e) {
								try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL,MySQLConnectivity.user,MySQLConnectivity.pass)) {
									obtainedSchool = txtSchool.getText();
									if(obtainedSchool.isEmpty()) {
										JOptionPane.showMessageDialog(null, "Input name!");
									} else {
										String obtainedInviteCode = getInviteCode(5);
										PreparedStatement addSchool = conn.prepareStatement("insert into schoolInfo (schoolname, creator, inviteCode) values (?, ?, ?)");
										addSchool.setString(1, obtainedSchool);
										addSchool.setString(2, Login.pubUID);
										addSchool.setString(3, obtainedInviteCode);
										int addedSchool = addSchool.executeUpdate();
										if(addedSchool == 1) {
											Login.pubSchoolName = obtainedSchool;
											Login.pubOccupation = "Admin";
											setVisible(false);
											JOptionPane.showMessageDialog(null, "School added!");
											JOptionPane.showMessageDialog(null, "Here is the invite code: "+obtainedInviteCode);
											PreparedStatement inSchool = conn.prepareStatement("update userInfo set occupation='Admin', hasASchool = true, inviteCodeOfSchool ='"+obtainedInviteCode+"', schoolname ='"+obtainedSchool+"' where userid ='"+Login.pubUID+"'");
											inSchool.executeUpdate();
												
											conn.close();
											addSchool.close();
											inSchool.close(); 
											
											EventQueue.invokeLater(new Runnable() {
												public void run() {
													try {
														MainMenu frame = new MainMenu();
														frame.setVisible(true);
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											});
											dispose();
										} else {
											setVisible(false);
											JOptionPane.showMessageDialog(null, "Failed to add school!");
											setVisible(true);
										}
									}
							} catch (SQLException sql) {
								sql.printStackTrace();
							}
						}
					});
					csButtonPanel.add(createButton);
				}
			}
			{
					JPanel csContentPanel = new JPanel();
					csContentPanel.setBackground(Color.WHITE);
					createSchool.add(csContentPanel, BorderLayout.CENTER);
					csContentPanel.setLayout(null);
						
					JLabel lblSchool = new JLabel("Enter School Name:");
					lblSchool.setBounds(10, 50, 94, 14);
					csContentPanel.add(lblSchool);
					
					txtSchool = new JTextField();
					txtSchool.setBounds(114, 47, 150, 20);
					csContentPanel.add(txtSchool);
					txtSchool.setColumns(10);
				}
			}
			{
				JPanel inviteSchool = new JPanel();
				tabbedPane.addTab("Join School", null, inviteSchool, "Join a school by getting an invite code from any members from the school!");
				inviteSchool.setLayout(new BorderLayout(0, 0));
				{
					JPanel isButtonPanel = new JPanel();
					inviteSchool.add(isButtonPanel, BorderLayout.SOUTH);
					isButtonPanel.setLayout(new GridLayout(0, 2, 0, 0));
					{
						JButton joinButton = new JButton("Join");
						joinButton.addMouseListener(new PropertiesListener(joinButton) {
							@Override
							public void mouseClicked(MouseEvent e) {
								String inviteCode = txtInvite.getText();
								String obtainedInviteCode = "";
								try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user, MySQLConnectivity.pass)) {
									if(inviteCode.isEmpty()) {
										JOptionPane.showMessageDialog(null, "Input code!");
									} else {
										PreparedStatement checkInvite = conn.prepareStatement("select inviteCode, schoolname from schoolInfo where inviteCode='"+inviteCode+"'");
										ResultSet obtainedInvite = checkInvite.executeQuery();
										while(obtainedInvite.next()) {
											obtainedInviteCode = obtainedInvite.getString("inviteCode");
											obtainedSchool = obtainedInvite.getString("schoolname");
										}
										if(inviteCode.equals(obtainedInviteCode)) {
											Login.pubSchoolName = obtainedSchool;
											PreparedStatement joined = conn.prepareStatement("update userInfo set schoolname ='"+obtainedSchool+"', hasASchool = 1 where userid="+Login.pubUID);
											int joinedResult = joined.executeUpdate();
											if(joinedResult == 1) {
												setVisible(false);
												JOptionPane.showMessageDialog(null, "You have joined to "+obtainedSchool+"!");
												EventQueue.invokeLater(new Runnable() {
													public void run() {
														try {
															MainMenu frame = new MainMenu();
															frame.setVisible(true);
														} catch (Exception e) {
															e.printStackTrace();
														}
													}
												}); 
												dispose();

											}
										} else {
											setVisible(false);
											JOptionPane.showMessageDialog(null, "Invite Code does not exist!");
											setVisible(true);
										}
									}
								} catch (SQLException sql) {
									sql.printStackTrace();
								}
							}
						});
						isButtonPanel.add(joinButton);
					}
				}
				{
					JPanel isContentPanel = new JPanel();
					isContentPanel.setBackground(Color.WHITE);
					inviteSchool.add(isContentPanel, BorderLayout.CENTER);
					isContentPanel.setLayout(null);
					{
						JLabel lblInvite = new JLabel("Enter Invite Code:");
						lblInvite.setBounds(10, 50, 89, 14);
						isContentPanel.add(lblInvite);
					}
					
					txtInvite = new JTextField();
					txtInvite.setBounds(109, 47, 160, 20);
					isContentPanel.add(txtInvite);
					txtInvite.setColumns(10);
				}
			}
		}
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	private String getInviteCode(int num) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder randomCode = new StringBuilder(num);
		for(int i = 0; i <= num; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			randomCode.append(AlphaNumericString.charAt(index));
		}
		return randomCode.toString();
	}
	
}


