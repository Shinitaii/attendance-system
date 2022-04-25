package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class recordsSettings extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public String obtainedUser;
	private String obtainedStatus;
	public JLabel lblCurrentUserSelected;
	private ButtonGroup group;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			recordsSettings dialog = new recordsSettings();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public recordsSettings() {
		super(null,ModalityType.TOOLKIT_MODAL);
		setTitle("Edit Status");
		setIconImage(Toolkit.getDefaultToolkit().getImage(recordsSettings.class.getResource("/res/attendance.png")));
		setBounds(100, 100, 225, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblCurrentUserSelected = new JLabel("");
		lblCurrentUserSelected.setBounds(10, 11, 145, 14);
		contentPanel.add(lblCurrentUserSelected);
		
		JPanel RadioButtonList = new JPanel();
		RadioButtonList.setBackground(new Color(255, 255, 255));
		RadioButtonList.setBorder(new LineBorder(new Color(65, 105, 225)));
		RadioButtonList.setBounds(10, 36, 189, 181);
		contentPanel.add(RadioButtonList);
		RadioButtonList.setLayout(new GridLayout(0, 1, 0, 0));
		
		group = new ButtonGroup();
		
		JRadioButton presentRD = new JRadioButton("Present");
		presentRD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtainedStatus = "Present";
			}
		});
		group.add(presentRD);
		RadioButtonList.add(presentRD);
		
		JRadioButton absentRD = new JRadioButton("Absent");
		absentRD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtainedStatus = "Absent";
			}
		});
		group.add(absentRD);
		RadioButtonList.add(absentRD);
		
		JRadioButton lateRD = new JRadioButton("Late");
		lateRD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtainedStatus = "Late";
			}
		});
		group.add(lateRD);
		RadioButtonList.add(lateRD);
		
		JRadioButton excusedRD = new JRadioButton("Excused");
		excusedRD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtainedStatus = "Excused";
			}
		});
		group.add(excusedRD);
		RadioButtonList.add(excusedRD);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to set "+obtainedStatus+" to "+obtainedUser+"?", "Note!", JOptionPane.YES_NO_OPTION);
						if(confirm == JOptionPane.YES_OPTION) {
							try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
								PreparedStatement getStatement = conn.prepareStatement("update attendancestatus set studentstatus='"+obtainedStatus+"' where concat(firstname, ' ', middlename, ' ', lastname) = '"+obtainedUser+"'");
								int result = getStatement.executeUpdate();
								if(result == 1) {
									JOptionPane.showMessageDialog(null, "Successfully set "+obtainedStatus+" to "+obtainedUser+"!");
									AdminMenu.records.model.setRowCount(0);
									AdminMenu.records.checkList();
								} else {
									JOptionPane.showMessageDialog(null, "Error!");
								}
							} catch(SQLException sql) {
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
