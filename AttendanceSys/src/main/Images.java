package main;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class Images {	
	public static final Image wLogo = new ImageIcon(Images.class.getResource("src/res/logo.png")).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
	public static final Image bLogo = new ImageIcon(Images.class.getResource("src/res/attendance.png")).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
	public static final Image uid = new ImageIcon(Images.class.getResource("src/res/UID.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image username = new ImageIcon(Images.class.getResource("src/res/user.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image pass = new ImageIcon(Images.class.getResource("src/res/password.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image cpass = new ImageIcon(Images.class.getResource("src/res/cpassword.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image account = new ImageIcon(Images.class.getResource("src/res/account.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image settings = new ImageIcon(Images.class.getResource("src/res/Settings.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image signout = new ImageIcon(Images.class.getResource("src/res/signout.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image dashboard = new ImageIcon(Images.class.getResource("src/res/dashboard.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image showPass = new ImageIcon(Images.class.getResource("src/res/ShowPass.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image doNotShowPass = new ImageIcon(Images.class.getResource("src/res/doNotShowPass.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image question = new ImageIcon(Images.class.getResource("src/res/question.png")).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
	public static final Image courses = new ImageIcon(Images.class.getResource("src/res/course.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	public static final Image signoutred = new ImageIcon(Images.class.getResource("src/res/signoutred.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image logo2 = new ImageIcon(Images.class.getResource("src/res/logo2.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image department = new ImageIcon(Images.class.getResource("src/res/department.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image school = new ImageIcon(Images.class.getResource("src/res/school.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image members = new ImageIcon(Images.class.getResource("src/res/members.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static Image profile = Toolkit.getDefaultToolkit().createImage(Login.pubPhoto);
}