package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Toolkit;
import javax.swing.JTextPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MakeExcuse extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MakeExcuse dialog = new MakeExcuse();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MakeExcuse() {
		super(null, ModalityType.TOOLKIT_MODAL);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MakeExcuse.class.getResource("/res/attendance.png")));
		setTitle("Letter Request\r\n");
		setBounds(100, 100, 600, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelTitle = new JPanel();
			FlowLayout fl_panelTitle = (FlowLayout) panelTitle.getLayout();
			fl_panelTitle.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panelTitle, BorderLayout.NORTH);
			{
				JLabel lblLetterTitle = new JLabel("Title (100 characters max):");
				panelTitle.add(lblLetterTitle);
			}
			{
				textField = new JTextField();
				panelTitle.add(textField);
				textField.setColumns(52);
			}
		}
		{
			JPanel panelDesc = new JPanel();
			contentPanel.add(panelDesc, BorderLayout.CENTER);
			panelDesc.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblLetterDesc = new JLabel("Body (500 characters max):");
				panelDesc.add(lblLetterDesc, BorderLayout.NORTH);
			}
			{
				textPane = new JTextPane();
				panelDesc.add(textPane, BorderLayout.CENTER);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton createButton = new JButton("Create");
				createButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						String title = textField.getText(); String desc = textPane.getText();
						try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user ,MySQLConnectivity.pass)){
							PreparedStatement getStatement = conn.prepareStatement("insert into excuserequests (requesttitle, requestdesc, fullname, record_name, departmentname, sectionname, subjectname, schoolname, schoolid) values (?,?,?,?,?,?,?,?,?)");
							getStatement.setString(1, title);
							getStatement.setString(2, desc);
							getStatement.setString(3, Login.pubFullName);
							getStatement.setString(4, MainMenu.records.obtainedRecord);
							getStatement.setString(5, MainMenu.records.obtainedDept);
							getStatement.setString(6, MainMenu.records.obtainedSec);
							getStatement.setString(7, MainMenu.records.obtainedSub);
							getStatement.setString(8, Login.pubSchoolName);
							getStatement.setString(9, Login.pubSchoolID);
							int result = getStatement.executeUpdate();
							if(result == 1) {
								setVisible(false);
								JOptionPane.showMessageDialog(null, "Successfully created a letter!");
								setVisible(true);
							} else {
								setVisible(false);
								JOptionPane.showMessageDialog(null, "Error!");
								setVisible(true);
							}
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
