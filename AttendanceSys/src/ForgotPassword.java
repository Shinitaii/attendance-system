import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ForgotPassword extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtUid;
	public static String userid;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgotPassword frame = new ForgotPassword();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ForgotPassword() {
		setTitle("Check Account");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ForgotPassword.class.getResource("/res/attendance.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(65, 105, 225), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel.setBounds(115, 51, 255, 45);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBorder(null);
		txtUsername.setForeground(new Color(65, 105, 225));
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUsername.setBounds(44, 14, 200, 22);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel logoUser = new JLabel("New label");
		logoUser.setBounds(10, 11, 24, 24);
		panel.add(logoUser);
		logoUser.setIcon(new ImageIcon(Images.username));
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(65, 105, 225));
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsername.setBounds(44, 1, 69, 14);
		panel.add(lblUsername);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(115, 106, 255, 45);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		txtUid = new JTextField();
		txtUid.setBorder(null);
		txtUid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUid.setForeground(new Color(65, 105, 225));
		txtUid.setBounds(44, 14, 200, 22);
		panel_1.add(txtUid);
		txtUid.setColumns(10);
		
		JLabel logoUID = new JLabel("");
		logoUID.setBounds(10, 11, 24, 24);
		panel_1.add(logoUID);
		logoUID.setIcon(new ImageIcon(Images.uid));
		
		JLabel lblUID = new JLabel("UID");
		lblUID.setForeground(new Color(65, 105, 225));
		lblUID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUID.setBounds(44, 1, 69, 14);
		panel_1.add(lblUID);
		
		JButton CheckA = new JButton("Check Account");
		CheckA.setBorder(null);
		CheckA.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		CheckA.setForeground(new Color(255, 255, 255));
		CheckA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		CheckA.addMouseListener(new PropertiesListener(CheckA));
		CheckA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = txtUsername.getText(), uid = txtUid.getText();
					Connection conn = DriverManager.getConnection("jdbc:mysql://sql6.freesqldatabase.com:3306/sql6476155","sql6476155","HHHLDqnNka");
					PreparedStatement check = conn.prepareStatement("select userid from userInfo where username='"+username+"' and userid='"+uid+"'");					
					if(username.isEmpty() && uid.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Enter all the required credentials!");
					} else {
						try {
							ResultSet confirm = check.executeQuery();
							if(confirm.next()) {
								JOptionPane.showMessageDialog(null, "Account found!");
								userid = confirm.getString("userid");
								EventQueue.invokeLater(new Runnable() {
									public void run() {
										try {
											ResetPassword frame = new ResetPassword();
											frame.setVisible(true);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								});
								dispose();
								confirm.close();
							} else {
								JOptionPane.showMessageDialog(null, "Account not found!");
							}
						} catch (Exception a) {
							JOptionPane.showMessageDialog(null, "UID should be a number!");
						}
					}
					conn.close();
					check.close();
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
			}
		});
		
		CheckA.setBackground(new Color(65, 105, 225));
		CheckA.setBounds(115, 189, 254, 61);
		contentPane.add(CheckA);
		CheckA.setLayout(null);
		
		JLabel lblBack = new JLabel("Back");
		lblBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBack.addMouseListener(new PropertiesListener(lblBack) {
			public void mouseClicked(MouseEvent e) {
				dispose();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Login frame = new Login();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		lblBack.setForeground(new Color(65, 105, 225));
		lblBack.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		lblBack.setHorizontalAlignment(SwingConstants.CENTER);
		lblBack.setBounds(224, 261, 34, 12);
		contentPane.add(lblBack);
		
		getRootPane().setDefaultButton(CheckA);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}
