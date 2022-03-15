package main;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;

public class panelSchedule extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Image img_Subject = new ImageIcon(Login.class.getResource("res/subject.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	//private Image img_View = new ImageIcon(Login.class.getResource("res/view.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	panelCourse panelCourse = new panelCourse();
	
	public panelSchedule() {
		setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 11));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(new Color(255, 255, 255));
		setBounds(new Rectangle(0, 0, 559, 439));
		setLayout(null);
		//lblView2.setIcon(new ImageIcon(img_View));

	}
	public void menuClicked(JPanel panel) {	
		panelCourse.setVisible(true);
		panel.setVisible(true);
		
	}
}
