package main;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.LineBorder;

public class panelSelectedSchool extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<JButton> buttonList = new ArrayList<JButton>();
	private int obtainedNum;
	 
	public panelSelectedSchool() {
		setBorder(null);
		setBackground(new Color(255, 255, 255));
		setBounds(0,0,539,456);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(0, 0, 539, 456);
		add(panel);

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem","root","Keqingisbestgirl");
			PreparedStatement checkingSchool = conn.prepareStatement("select count(schoolid) from inThatSchool where userid ="+Login.pubUID+" and inThatSchool = 1");
			ResultSet number = checkingSchool.executeQuery();
			while(number.next()) {
				obtainedNum = Integer.valueOf(number.getString("count(schoolid)"));
			}
				if(obtainedNum > 0) {
					panelSkewl.lbl.setVisible(false);
				}
			} catch (SQLException sql) {
			sql.printStackTrace();
		}
		
		for(int i = 0; i < obtainedNum; i++) {
			JButton button = new JButton();
			button.setSize(100, 200);
			panel.add(button);
			buttonList.add(button);
			invalidate();
		}
		
		revalidate();
		repaint();
	}

}
