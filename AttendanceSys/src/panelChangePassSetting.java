import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class panelChangePassSetting extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPasswordField currentPass;
	private JPasswordField newPass;
	private JPasswordField confirmNewPass;

	public panelChangePassSetting() {
		setBounds(0,0, 539, 450);
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 539, 450);
		add(panel);
		panel.setLayout(null);
		
		JPanel panelCurrentPass = new JPanel();
		panelCurrentPass.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelCurrentPass.setBackground(new Color(255, 255, 255));
		panelCurrentPass.setBounds(101, 36, 335, 47);
		panel.add(panelCurrentPass);
		panelCurrentPass.setLayout(null);
		
		JLabel lblCurrentPass = new JLabel("Current Password:");
		lblCurrentPass.setForeground(new Color(65, 105, 225));
		lblCurrentPass.setBounds(10, 11, 90, 28);
		panelCurrentPass.add(lblCurrentPass);
		
		currentPass = new JPasswordField();
		currentPass.setBorder(null);
		currentPass.setBounds(110, 11, 186, 24);
		panelCurrentPass.add(currentPass);
		
		JLabel logoShowCurrentPass = new JLabel("");
		logoShowCurrentPass.setBounds(301, 11, 24, 24);
		logoShowCurrentPass.setIcon(new ImageIcon(Images.showPass));
		logoShowCurrentPass.addMouseListener(new PasswordIcon(logoShowCurrentPass, currentPass));
		panelCurrentPass.add(logoShowCurrentPass);
		
		JPanel panelNewPass = new JPanel();
		panelNewPass.setLayout(null);
		panelNewPass.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelNewPass.setBackground(Color.WHITE);
		panelNewPass.setBounds(101, 141, 335, 47);
		panel.add(panelNewPass);
		
		JLabel lblNewPass = new JLabel("New Password:");
		lblNewPass.setForeground(new Color(65, 105, 225));
		lblNewPass.setBounds(10, 11, 74, 28);
		panelNewPass.add(lblNewPass);
		
		newPass = new JPasswordField();
		newPass.setBorder(null);
		newPass.setBounds(94, 11, 202, 24);
		panelNewPass.add(newPass);
		
		JLabel logoShowNewPass = new JLabel("");
		logoShowNewPass.setBounds(301, 11, 24, 24);
		logoShowNewPass.setIcon(new ImageIcon(Images.showPass));
		logoShowNewPass.addMouseListener(new PasswordIcon(logoShowCurrentPass, currentPass));
		panelNewPass.add(logoShowNewPass);
		
		JPanel panelConfirmNewPass = new JPanel();
		panelConfirmNewPass.setLayout(null);
		panelConfirmNewPass.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelConfirmNewPass.setBackground(Color.WHITE);
		panelConfirmNewPass.setBounds(101, 246, 335, 47);
		panel.add(panelConfirmNewPass);
		
		JLabel lblConfirmNewPass = new JLabel("Confirm New Password:");
		lblConfirmNewPass.setForeground(new Color(65, 105, 225));
		lblConfirmNewPass.setBounds(10, 11, 115, 28);
		panelConfirmNewPass.add(lblConfirmNewPass);
		
		confirmNewPass = new JPasswordField();
		confirmNewPass.setBorder(null);
		confirmNewPass.setBounds(135, 11, 161, 24);
		panelConfirmNewPass.add(confirmNewPass);
		
		JLabel logoShowConfirmNewPass = new JLabel("");
		logoShowConfirmNewPass.setBounds(301, 11, 24, 24);
		logoShowConfirmNewPass.setIcon(new ImageIcon(Images.showPass));
		logoShowConfirmNewPass.addMouseListener(new PasswordIcon(logoShowCurrentPass, currentPass));
		panelConfirmNewPass.add(logoShowConfirmNewPass);
		
		JButton changePassButton = new JButton("Change Password");
		changePassButton.setBorder(null);
		changePassButton.setForeground(new Color(255, 255, 255));
		changePassButton.setBackground(new Color(65, 105, 225));
		changePassButton.setBounds(187, 354, 170, 40);
		changePassButton.addMouseListener(new PropertiesListener(changePassButton));
		changePassButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://sql6.freesqldatabase.com:3306/sql6476155","sql6476155","HHHLDqnNka");
					//PreparedStatement changePass =
				} catch (SQLException sql) {
					sql.printStackTrace();
				}
			}
		});
		panel.add(changePassButton);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(101, 304, 334, 38);
		panel.add(lblNewLabel);

	}
}
