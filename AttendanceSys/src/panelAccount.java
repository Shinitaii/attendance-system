import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Rectangle;

public class panelAccount extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public panelAccount() {
		setBackground(new Color(255, 255, 255));
		setBounds(new Rectangle(0, 0, 559, 439));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(7, 7, 551, 431);
		add(panel);
		panel.setLayout(null);
	}
}
