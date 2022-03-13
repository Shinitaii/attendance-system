package main;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Images {	
		
	public static Image readImage(String streamString) {
		try {
			return ImageIO.read(Images.class.getResourceAsStream(streamString));
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/*public static final Image wLogo = new ImageIcon("src/res/logo.png").getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
	public static final Image bLogo = new ImageIcon("src/res/attendance.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
	public static final Image uid = new ImageIcon("src/res/UID.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image username = new ImageIcon("src/res/user.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image pass = new ImageIcon("src/res/password.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image cpass = new ImageIcon("src/res/cpassword.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image account = new ImageIcon("src/res/account.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image settings = new ImageIcon("src/res/Settings.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image signout = new ImageIcon("src/res/signout.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image dashboard = new ImageIcon("src/res/dashboard.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image showPass = new ImageIcon("src/res/ShowPass.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image doNotShowPass = new ImageIcon("src/res/doNotShowPass.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image question = new ImageIcon("src/res/question.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
	public static final Image courses = new ImageIcon("src/res/course.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	public static final Image signoutred = new ImageIcon("src/res/signoutred.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image logo2 = new ImageIcon("src/res/logo2.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image department = new ImageIcon("src/res/department.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image school = new ImageIcon("src/res/school.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image members = new ImageIcon("src/res/members.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static Image profile; */
}