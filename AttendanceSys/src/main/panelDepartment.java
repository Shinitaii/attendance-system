package main;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class panelDepartment extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public panelDepartment() {
		setBounds(new Rectangle(0, 0, 559, 539));
		setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Department");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(65, 105, 225));
		lblNewLabel.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 90));
		lblNewLabel.setBounds(10, 11, 539, 517);
		add(lblNewLabel);

	}

}
