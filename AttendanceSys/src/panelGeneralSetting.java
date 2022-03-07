
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class panelGeneralSetting extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public panelGeneralSetting() {
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 539, 450);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(212, 183, 46, 14);
		add(lblNewLabel);

	}

}
