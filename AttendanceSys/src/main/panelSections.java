package main;

import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class panelSections extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String selectedDepartment = panelDepartment.selectedDept;
	/**
	 * Create the panel.
	 */
	public panelSections() {
		setBounds(new Rectangle(0, 0, 559, 539));
		
		JLabel lblNewLabel = new JLabel(selectedDepartment);
		add(lblNewLabel);
	}

}
