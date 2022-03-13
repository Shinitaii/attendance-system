package main;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class PasswordIcon extends MouseAdapter{
	
	JLabel label;
	JPasswordField passField;
	
	private boolean isChecked = false;
	
	public PasswordIcon(JLabel label, JPasswordField passField) {
		this.label = label; this.passField = passField;
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void mouseClicked(MouseEvent e) {
		if(isChecked) {
			label.setIcon(new ImageIcon(Images.showPass));
			isChecked = false;
			passField.setEchoChar('•');
		} else {
			label.setIcon(new ImageIcon(Images.doNotShowPass));
			isChecked = true;
			passField.setEchoChar((char)0);
		}
	}
}
