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

public class panelDashboard extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Image img_Subject = new ImageIcon(Login.class.getResource("res/subject.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	//private Image img_View = new ImageIcon(Login.class.getResource("res/view.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	panelCourse panelCourse = new panelCourse();
	
	public panelDashboard() {
		setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 11));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(new Color(255, 255, 255));
		setBounds(new Rectangle(0, 0, 559, 439));
		setLayout(null);
		
		JPanel panelCourse = new JPanel();
		panelCourse.addMouseListener(new MouseAdapter() {
			
		});
		panelCourse.setBorder(null);
		panelCourse.setBackground(new Color(65, 105, 225));
		panelCourse.setForeground(new Color(65, 105, 225));
		panelCourse.setBounds(10, 11, 266, 200);
		add(panelCourse);
		panelCourse.setLayout(null);
		
		JLabel lblCourse2 = new JLabel("Courses");
		lblCourse2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourse2.setForeground(new Color(255, 255, 255));
		lblCourse2.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 25));
		lblCourse2.setBounds(10, 140, 246, 57);
		panelCourse.add(lblCourse2);
		
		JLabel lblCourse = new JLabel("");
		lblCourse.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourse.setBounds(10, 11, 246, 117);
		panelCourse.add(lblCourse);
		lblCourse.setIcon(new ImageIcon(Images.courses));
		
		JPanel panelSubject = new JPanel();
		panelSubject.setBackground(new Color(65, 105, 225));
		panelSubject.setBounds(286, 11, 263, 200);
		add(panelSubject);
		panelSubject.setLayout(null);
		
		JLabel lblAddStudent = new JLabel("Subjects");
		lblAddStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddStudent.setForeground(Color.WHITE);
		lblAddStudent.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 25));
		lblAddStudent.setBounds(10, 140, 246, 57);
		panelSubject.add(lblAddStudent);
		
		JLabel lblSub = new JLabel("");
		lblSub.setHorizontalAlignment(SwingConstants.CENTER);
		lblSub.setBounds(10, 12, 246, 117);
		panelSubject.add(lblSub);
		//lblSub.setIcon(new ImageIcon(img_Subject));
		
		JPanel panelCourse_1 = new JPanel();
		panelCourse_1.setLayout(null);
		panelCourse_1.setForeground(new Color(65, 105, 225));
		panelCourse_1.setBorder(null);
		panelCourse_1.setBackground(new Color(65, 105, 225));
		panelCourse_1.setBounds(10, 221, 266, 200);
		add(panelCourse_1);
		
		JLabel lblView = new JLabel("View");
		lblView.setHorizontalAlignment(SwingConstants.CENTER);
		lblView.setForeground(Color.WHITE);
		lblView.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 25));
		lblView.setBounds(10, 140, 246, 57);
		panelCourse_1.add(lblView);
		
		JLabel lblView2 = new JLabel("");
		lblView2.setBounds(10, 11, 246, 117);
		panelCourse_1.add(lblView2);
		lblView2.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 25));
		lblView2.setForeground(new Color(65, 105, 225));
		lblView2.setHorizontalAlignment(SwingConstants.CENTER);
		//lblView2.setIcon(new ImageIcon(img_View));

	}
	public void menuClicked(JPanel panel) {	
		panelCourse.setVisible(true);
		panel.setVisible(true);
		
	}
}
