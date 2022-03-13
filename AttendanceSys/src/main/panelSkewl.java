package main;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class panelSkewl extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public panelSkewl() {
		setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setBackground(new Color(255, 255, 255));
		
		setBounds(0 , 0, 559, 539);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Skewl");
		lblNewLabel.setForeground(new Color(65, 105, 225));
		lblNewLabel.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 90));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 539, 495);
		add(lblNewLabel);

	}

}
