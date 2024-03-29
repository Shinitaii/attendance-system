package main;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class panelProfileDisplay extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JLabel lblUID, lblpfp, lblFN, lblDept, lblSecs, lblOccup;
	/**
	 * Create the panel.
	 */
	public panelProfileDisplay() {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 540, 539);
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setLayout(null);
		
		JPanel panelpfp = new JPanel();
		panelpfp.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelpfp.setBackground(new Color(255, 255, 255));
		panelpfp.setBounds(171, 11, 180, 180);
		add(panelpfp);
		panelpfp.setLayout(new BorderLayout());
		
		lblUID = new JLabel();
		lblUID.setFont(new Font("Yu Gothic UI Light", Font.ITALIC, 20));
		lblUID.setBounds(10, 202, 520, 50);
		add(lblUID);
		
		lblpfp = new JLabel("");
		panelpfp.add(lblpfp, BorderLayout.CENTER);
		
		lblFN = new JLabel("Name :");
		lblFN.setFont(new Font("Yu Gothic UI Light", Font.ITALIC, 20));
		lblFN.setBounds(10, 260, 520, 50);
		add(lblFN);
		
		lblDept = new JLabel("Department :");
		lblDept.setFont(new Font("Yu Gothic UI Light", Font.ITALIC, 20));
		lblDept.setBounds(10, 321, 520, 50);
		add(lblDept);
		
		lblSecs = new JLabel("Section :");
		lblSecs.setFont(new Font("Yu Gothic UI Light", Font.ITALIC, 20));
		lblSecs.setBounds(10, 383, 520, 50);
		add(lblSecs);
		
		lblOccup = new JLabel("Occupation :");
		lblOccup.setFont(new Font("Yu Gothic UI Light", Font.ITALIC, 20));
		lblOccup.setBounds(10, 444, 520, 50);
		add(lblOccup);

	}

}
