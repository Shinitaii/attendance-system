package main;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class panelHome extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private String username = Login.pubUsername, firstname = Login.pubFN, middlename = Login.pubMN, lastname = Login.pubLN;
	private int uid = Integer.valueOf(Login.pubUID);
 	
	public panelHome() {
		setBackground(new Color(255, 255, 255));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		
		setBounds(0, 0, 559, 439);
		setLayout(null);
		setVisible(true);
		
		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setForeground(new Color(65, 105, 225));
		lblWelcome.setHorizontalAlignment(SwingConstants.LEFT);
		lblWelcome.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 35));
		lblWelcome.setBounds(10, 60, 153, 60);
		add(lblWelcome);
		
		JPanel panelpfp = new JPanel();
		panelpfp.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelpfp.setBackground(new Color(255, 255, 255));
		panelpfp.setBounds(10, 131, 150, 150);
		add(panelpfp);
		panelpfp.setLayout(null);
		
		JLabel lblpfp = new JLabel();
		lblpfp.setHorizontalAlignment(SwingConstants.CENTER);
		lblpfp.setBorder(new LineBorder(new Color(65, 105, 225)));
		lblpfp.setBounds(0, 0, 150, 150);
		lblpfp.setIcon(new ImageIcon(Images.profile));
		panelpfp.add(lblpfp);
		
		JLabel lblAccountS = new JLabel(username);
		lblAccountS.setForeground(new Color(0, 0, 0));
		lblAccountS.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblAccountS.setBounds(179, 194, 280, 42);
		add(lblAccountS);
		
		JLabel lblUID = new JLabel("UID:"+uid);
		lblUID.setForeground(new Color(0, 0, 0));
		lblUID.setFont(new Font("Yu Gothic UI Light", Font.ITALIC, 30));
		lblUID.setBounds(10, 285, 376, 52);
		add(lblUID);
		
		JLabel lblName = new JLabel(firstname + " " + middlename + " " + lastname + ".");
		lblName.setForeground(new Color(65, 105, 225));
		lblName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblName.setBounds(162, 74, 366, 42);
		add(lblName);
		
		JLabel lblUsername = new JLabel("*Account Status*");
		lblUsername.setForeground(new Color(255, 0, 0));
		lblUsername.setFont(new Font("Yu Gothic UI Light", Font.ITALIC, 30));
		lblUsername.setBounds(173, 232, 376, 52);
		add(lblUsername);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 559, 60);
		add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Edit Profile");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(new Color(65, 105, 225));
		btnNewButton.setBounds(404, 11, 145, 38);
		panel.add(btnNewButton);

	}
}
