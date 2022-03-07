import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class panelAccountSetting extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public panelAccountSetting() {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 559, 539);
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 11, 150, 150);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 150, 150);
		panel.add(lblNewLabel);
		
		JButton browseButton = new JButton("Browse Image");
		browseButton.setBounds(170, 138, 110, 23);
		add(browseButton);
	}
}
