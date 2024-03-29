package main;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Images {	
	
	public static final Image wLogo = new ImageIcon(Images.class.getResource("/res/logo.png")).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
	public static final Image bLogo = new ImageIcon(Images.class.getResource("/res/attendance.png")).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
	public static final Image uid = new ImageIcon(Images.class.getResource("/res/UID.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image user = new ImageIcon(Images.class.getResource("/res/user.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image pass = new ImageIcon(Images.class.getResource("/res/password.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image cpass = new ImageIcon(Images.class.getResource("/res/cpassword.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image account = new ImageIcon(Images.class.getResource("/res/account.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image settings = new ImageIcon(Images.class.getResource("/res/Settings.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image signout = new ImageIcon(Images.class.getResource("/res/signout.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image dashboard = new ImageIcon(Images.class.getResource("/res/dashboard.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image showPass = new ImageIcon(Images.class.getResource("/res/ShowPass.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image doNotShowPass = new ImageIcon(Images.class.getResource("/res/doNotShowPass.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	public static final Image question = new ImageIcon(Images.class.getResource("/res/question.png")).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
	public static final Image courses = new ImageIcon(Images.class.getResource("/res/course.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
	public static final Image signoutred = new ImageIcon(Images.class.getResource("/res/signoutred.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image logo2 = new ImageIcon(Images.class.getResource("/res/logo2.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image department = new ImageIcon(Images.class.getResource("/res/department.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image school = new ImageIcon(Images.class.getResource("/res/school.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image members = new ImageIcon(Images.class.getResource("/res/members.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image schedule = new ImageIcon(Images.class.getResource("/res/Schedul.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	public static final Image subject = new ImageIcon(Images.class.getResource("/res/subject.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

	public static void pfp (JLabel label) {
		try (Connection conn = DriverManager.getConnection(MySQLConnectivity.URL, MySQLConnectivity.user, MySQLConnectivity.pass)) {
			PreparedStatement getPhoto = conn.prepareStatement("select profilePicture from userinfo where userid='"+Login.pubUID+"'");
			ResultSet get = getPhoto.executeQuery();
			Blob photo = null;
			while(get.next()) {
				photo = get.getBlob("profilePicture");
				
			}
			if(photo != null) {
				byte[] imagebytes = photo.getBytes(1, (int) photo.length());
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(imagebytes));
				Image img = new ImageIcon(image).getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
				label.setIcon(new ImageIcon(img));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}