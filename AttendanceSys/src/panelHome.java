import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class panelHome extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public panelHome() {
		setBackground(new Color(255, 255, 255));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		
		setBounds(0, 0, 559, 439);
		setLayout(null);
		setVisible(true);
		
		JLabel lblNewLabel = new JLabel("HOME");
		lblNewLabel.setForeground(new Color(65, 105, 225));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 90));
		lblNewLabel.setBounds(10, 11, 539, 417);
		add(lblNewLabel);

	}

}
