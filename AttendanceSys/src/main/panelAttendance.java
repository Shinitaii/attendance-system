package main;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class panelAttendance extends JPanel {

	/**
	 * Create the panel.
	 */
	public panelAttendance() {
		setBounds(new Rectangle(0, 0, 559, 539));
		setForeground(new Color(65, 105, 225));
		setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Attendance");
		lblNewLabel.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 90));
		lblNewLabel.setForeground(new Color(65, 105, 225));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 539, 517);
		add(lblNewLabel);

	}

}
