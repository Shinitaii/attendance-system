package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class subjectSettings extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfSubj;
	public static String subjectName;
	public String subjectDesc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			subjectSettings dialog = new subjectSettings();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public subjectSettings() {
		super(null, ModalityType.TOOLKIT_MODAL);
		setBounds(100, 100, 270, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblSubjName = new JLabel("Subject Name: ");
		lblSubjName.setBounds(10, 11, 73, 14);
		contentPanel.add(lblSubjName);
		
		tfSubj = new JTextField();
		tfSubj.setBounds(93, 8, 150, 20);
		contentPanel.add(tfSubj);
		tfSubj.setColumns(10);
		
		JLabel lblSubjDesc = new JLabel("Subject Description: ");
		lblSubjDesc.setBounds(10, 36, 99, 14);
		contentPanel.add(lblSubjDesc);
		
		JLabel lblNote = new JLabel("Note: 200 characters limit!");
		lblNote.setBounds(116, 36, 127, 14);
		contentPanel.add(lblNote);
		
		JTextPane taSubjDesc = new JTextPane();
		taSubjDesc.setBounds(10, 61, 233, 156);
		contentPanel.add(taSubjDesc);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton createButton = new JButton("Create");
				createButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){	
							subjectName = tfSubj.getText();
							subjectDesc = taSubjDesc.getText();
							
							PreparedStatement getStatement = conn.prepareStatement("insert into subjectinfo (subjectname, subjectdesc, departmentname, schoolname) values (?,?,?,?)");
							getStatement.setString(1, subjectName);
							getStatement.setString(2, subjectDesc);
							getStatement.setString(3, AdminMenu.SubjectSelectDepartment.selectedDept);
							getStatement.setString(4, Login.pubSchoolName);
							int result = getStatement.executeUpdate();
							if(result == 1) {
								dispose();
								JOptionPane.showMessageDialog(null, "Successfully created!");
								revalidate();
								repaint();
							}
							getStatement.close();
							conn.close();						
						} catch (SQLException sql) {
							sql.printStackTrace();
						}
					}
				});
				createButton.addMouseListener(new PropertiesListener(createButton));
				createButton.setActionCommand("Create");
				buttonPane.add(createButton);
				getRootPane().setDefaultButton(createButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						panelSubjects.isCancelled = true;
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
