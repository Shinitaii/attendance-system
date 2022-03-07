import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Cursor;

public class panelSettings extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	panelGeneralSetting panelGeneralSetting;
	panelChangePassSetting panelChangePassSetting;	
	AdminMenu AdminMenu;
	
	public panelSettings() {
		setBackground(new Color(255, 255, 255));
		setBounds(new Rectangle(0, 0, 559, 439));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 559, 70);
		add(panel);
		panel.setLayout(null);
		
		JPanel panelGeneral = new JPanel();
		panelGeneral.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelGeneral.setBackground(new Color(65, 105, 225));
		panelGeneral.addMouseListener(new PropertiesListener(panelGeneral) {

			public void mouseClicked(MouseEvent e) {
				menuClicked(panelGeneralSetting);
			}
		});
		
		panelGeneral.setBounds(10, 10, 150, 50);
		panel.add(panelGeneral);
		panelGeneral.setLayout(null);
		
		JLabel lblGeneral = new JLabel("General");
		lblGeneral.setForeground(new Color(255, 255, 255));
		lblGeneral.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblGeneral.setHorizontalAlignment(SwingConstants.CENTER);
		lblGeneral.setBounds(10, 11, 130, 28);
		panelGeneral.add(lblGeneral);
		
		JPanel panelChangePass = new JPanel();
		panelChangePass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelChangePass.setBackground(new Color(65, 105, 225));
		panelChangePass.addMouseListener(new PropertiesListener(panelChangePass) {
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelChangePassSetting);
			}
		});
		panelChangePass.setLayout(null);
		panelChangePass.setBounds(170, 10, 150, 50);
		panel.add(panelChangePass);
		
		JLabel lblChangePass = new JLabel("Change Password");
		lblChangePass.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangePass.setForeground(Color.WHITE);
		lblChangePass.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
		lblChangePass.setBounds(10, 11, 130, 28);
		panelChangePass.add(lblChangePass);
		
		JPanel panelMain = new JPanel();
		panelMain.setBounds(10, 80, 539, 450);
		add(panelMain);
		panelMain.setLayout(null);
		
		panelGeneralSetting = new panelGeneralSetting();
		panelGeneralSetting.setBounds(0, 0, 539, 450);
		panelMain.add(panelGeneralSetting);
		panelGeneralSetting.setLayout(null);
		
		panelChangePassSetting = new panelChangePassSetting();
		panelChangePassSetting.setBounds(0,0,539,450);
		panelMain.add(panelChangePassSetting);
		panelChangePassSetting.setLayout(null);
		
		menuClicked(panelGeneralSetting);
	}
	
	public void menuClicked(JPanel panel) {
		panelGeneralSetting.setVisible(false);
		panelChangePassSetting.setVisible(false);
		
		panel.setVisible(true);
		
	}
}
