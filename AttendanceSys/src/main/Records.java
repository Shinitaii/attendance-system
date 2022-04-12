package main;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class Records extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JLabel lblNewLabel;

	public Records() {
		setBounds(0, 0, 559, 539);
		setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel();
		add(lblNewLabel, BorderLayout.CENTER);

	}

}
