package main;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class panelHome extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private String username = Login.pubUsername, firstname = Login.pubFN, middlename = Login.pubMN, lastname = Login.pubLN;
	private String uid = Login.pubUID;
 
 	
	public panelHome() {
		setBackground(new Color(255, 255, 255));
		setBorder(new LineBorder(new Color(65, 105, 225)));
		
		setBounds(0, 0, 559, 439);
		setLayout(null);
		setVisible(true);
		
		JLabel lblWelcome = new JLabel("Welcome,");
		lblWelcome.setForeground(new Color(65, 105, 225));
		lblWelcome.setHorizontalAlignment(SwingConstants.LEFT);
		lblWelcome.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 35));
		lblWelcome.setBounds(10, 60, 153, 60);
		add(lblWelcome);
		
		JPanel panelpfp = new JPanel();
		panelpfp.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelpfp.setBackground(new Color(255, 255, 255));
		panelpfp.setBounds(10, 131, 153, 153);
		add(panelpfp);
		panelpfp.setLayout(null);
		
		JLabel lblpfp = new JLabel();
		lblpfp.setHorizontalAlignment(SwingConstants.CENTER);
		lblpfp.setBorder(new LineBorder(new Color(65, 105, 225)));
		lblpfp.setBounds(0, 0, 150, 150);
		lblpfp.setBounds(0, 0, 153, 153);
		Images.pfp(lblpfp);
		panelpfp.add(lblpfp);
		
		JLabel lblAccountS = new JLabel(username);
		lblAccountS.setForeground(new Color(0, 0, 0));
		lblAccountS.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblAccountS.setBounds(179, 194, 280, 42);
		add(lblAccountS);
		
		JLabel lblUID = new JLabel("UID:"+uid);
		lblUID.setForeground(new Color(0, 0, 0));
		lblUID.setFont(new Font("Yu Gothic UI Light", Font.ITALIC, 30));
		lblUID.setBounds(10, 285, 376, 52);
		add(lblUID);
		
		JLabel lblName = new JLabel(firstname + " " + middlename + " " + lastname + ".");
		lblName.setForeground(new Color(65, 105, 225));
		lblName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblName.setBounds(162, 74, 366, 42);
		add(lblName);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 559, 60);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblCode = new JLabel(" Invite Code: "+Login.pubInviteCode);
		lblCode.setHorizontalAlignment(SwingConstants.LEFT);
		lblCode.setForeground(new Color(65, 105, 225));
		lblCode.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblCode.setBounds(0, 0, 559, 60);
		panel.add(lblCode);
		
		JLabel lblTime = new JLabel();
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblTime.setForeground(new Color(65, 105, 225));
		lblTime.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 35));
		lblTime.setBounds(10, 368, 539, 60);
		add(lblTime);
		
		DateFormat dateandtime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
		Timer time = new Timer(500, (ActionListener) new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        Date date = new Date();
		        lblTime.setText("Time: "+dateandtime.format(date));
		        repaint();
		    }
		});
		time.start();

	}
}
