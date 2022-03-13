package main;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PropertiesListener extends MouseAdapter{

		JLabel label;
		JButton button;
		JPanel panel;

		public PropertiesListener(JLabel label) {
			this.label = label; 
			label.setBackground(new Color(65, 105, 225));
			label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		
		public PropertiesListener(JButton button) {
			this.button = button;
			button.setBackground(new Color(65, 105, 225));
			button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		
		public PropertiesListener(JPanel panel) {
			this.panel = panel;
			panel.setBackground(new Color(65, 105, 225));
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		public void mousePressed(MouseEvent e) {
			if(e.getSource() == label) {
			label.setForeground(new Color(65, 105, 255).brighter());
			} else if(e.getSource() == button) {
				button.setBackground(new Color(65, 105, 225).brighter());
			} else {
				panel.setBackground(new Color(65, 105, 225).brighter());
			}
		}

		public void mouseReleased(MouseEvent e) {
			if(e.getSource() == label) {
			label.setForeground(new Color(65, 105, 255));
			} else if (e.getSource() == button){
				button.setBackground(new Color(70, 130, 180));
			} else {
				panel.setBackground(new Color(70, 130, 180));
			}
		}

		public void mouseEntered(MouseEvent e) {
			if(e.getSource() == label) {
			label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
			} else if(e.getSource() == button){
				button.setBackground(new Color(70, 130, 180));
			} else {
				panel.setBackground(new Color(70, 130, 180));
			}
		}

		public void mouseExited(MouseEvent e) {
			if(e.getSource() == label) {
			label.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 12));
			} else if(e.getSource() == button){
				button.setBackground(new Color(65, 105, 225));
			} else {
				panel.setBackground(new Color(65, 105, 225));
			}
		}
		
	}
