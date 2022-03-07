import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;

public class panelAccountSetting extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtUser;
	private JTextField txtFN;
	private JTextField txtLN;
	private JTextField txtMN;
	private JLabel lblpfp;
	AdminMenu AdminMenu;

	public panelAccountSetting() {
		setBackground(new Color(255, 255, 255));
		setBounds(0, 0, 540, 539);
		setBorder(new LineBorder(new Color(65, 105, 225)));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225)));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(334, 71, 150, 150);
		add(panel);
		panel.setLayout(null);
		
		lblpfp = new JLabel("");
		lblpfp.setBounds(0, 0, 150, 150);
		panel.add(lblpfp);
		lblpfp.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton browseButton = new JButton("Browse Image");
		browseButton.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		browseButton.setBorder(null);
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser photoSelector = new JFileChooser();
				photoSelector.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "png");
				photoSelector.setFileFilter(filter);
				int returnPhoto = photoSelector.showOpenDialog(AdminMenu);
				if(returnPhoto == JFileChooser.APPROVE_OPTION) {
					File selectedPhoto = photoSelector.getSelectedFile();
					String path = selectedPhoto.getAbsolutePath();
					lblpfp.setIcon(ResizeImage(path));
				}
			}
		});
		browseButton.setForeground(new Color(255, 255, 255));
		browseButton.setBackground(new Color(65, 105, 225));
		browseButton.setBounds(344, 262, 131, 30);
		add(browseButton);
		
		JLabel lblNewLabel_1 = new JLabel("Allowed JPG or PNG, Max size of 800k");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(0, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(322, 295, 175, 14);
		add(lblNewLabel_1);
		
		JPanel panelFN = new JPanel();
		panelFN.setBackground(new Color(255, 255, 255));
		panelFN.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelFN.setBounds(10, 149, 265, 40);
		add(panelFN);
		panelFN.setLayout(null);
		
		txtFN = new JTextField();
		txtFN.setBorder(null);
		txtFN.setSelectionColor(new Color(65, 105, 225));
		txtFN.setColumns(10);
		txtFN.setBounds(10, 7, 245, 25);
		panelFN.add(txtFN);
		
		JPanel panelLN = new JPanel();
		panelLN.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelLN.setBackground(Color.WHITE);
		panelLN.setBounds(10, 230, 265, 40);
		add(panelLN);
		panelLN.setLayout(null);
		
		txtLN = new JTextField();
		txtLN.setBorder(null);
		txtLN.setSelectionColor(new Color(65, 105, 225));
		txtLN.setColumns(10);
		txtLN.setBounds(10, 7, 245, 25);
		panelLN.add(txtLN);
		
		JPanel panelMN = new JPanel();
		panelMN.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelMN.setBackground(Color.WHITE);
		panelMN.setBounds(10, 310, 265, 40);
		add(panelMN);
		panelMN.setLayout(null);
		
		txtMN = new JTextField();
		txtMN.setBorder(null);
		txtMN.setSelectionColor(new Color(65, 105, 225));
		txtMN.setColumns(10);
		txtMN.setBounds(10, 7, 245, 25);
		panelMN.add(txtMN);
		
		JLabel lblFname = new JLabel("First Name :");
		lblFname.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblFname.setForeground(new Color(65, 105, 225));
		lblFname.setBounds(10, 120, 150, 29);
		add(lblFname);
		
		JLabel lblLname = new JLabel("Last Name :");
		lblLname.setForeground(new Color(65, 105, 225));
		lblLname.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblLname.setBounds(10, 200, 150, 29);
		add(lblLname);
		
		JLabel lblMname = new JLabel("Middle Name :");
		lblMname.setForeground(new Color(65, 105, 225));
		lblMname.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblMname.setBounds(10, 281, 150, 29);
		add(lblMname);
		
		JLabel lblNewLabel_3 = new JLabel("UID : ");
		lblNewLabel_3.setFont(new Font("Yu Gothic UI", Font.ITALIC, 20));
		lblNewLabel_3.setBounds(334, 222, 151, 40);
		add(lblNewLabel_3);
		
		JPanel panelUserN = new JPanel();
		panelUserN.setBorder(new LineBorder(new Color(65, 105, 225)));
		panelUserN.setBackground(Color.WHITE);
		panelUserN.setBounds(10, 69, 265, 40);
		add(panelUserN);
		panelUserN.setLayout(null);
		
		txtUser = new JTextField();
		txtUser.setBorder(null);
		txtUser.setSelectionColor(new Color(65, 105, 225));
		txtUser.setForeground(new Color(65, 105, 225));
		txtUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUser.setBounds(10, 7, 245, 25);
		panelUserN.add(txtUser);
		txtUser.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setForeground(new Color(65, 105, 225));
		lblUsername.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		lblUsername.setBounds(10, 39, 224, 29);
		add(lblUsername);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setForeground(Color.WHITE);
		btnReset.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		btnReset.setBorder(null);
		btnReset.setBackground(new Color(65, 105, 225));
		btnReset.setBounds(315, 11, 90, 30);
		btnReset.addMouseListener(new PropertiesListener(btnReset));
		add(btnReset);
		
		JButton btnSave = new JButton("Save");
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		btnSave.setBorder(null);
		btnSave.setBackground(new Color(65, 105, 225));
		btnSave.setBounds(415, 11, 90, 30);
		btnSave.addMouseListener(new PropertiesListener(btnSave));
		add(btnSave);
	}
	
	public ImageIcon ResizeImage(String ImagePath) { //this is where you get the images
		Image profile = new ImageIcon(ImagePath).getImage().getScaledInstance(lblpfp.getWidth(), lblpfp.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon iconProfile = new ImageIcon(profile);
		return iconProfile;
	}
}
