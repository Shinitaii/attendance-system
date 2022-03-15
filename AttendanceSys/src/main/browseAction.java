package main;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class browseAction implements ActionListener {

	JLabel label;
	
	public browseAction(JLabel label) {
		this.label = label;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser photoSelector = new JFileChooser();
		photoSelector.setCurrentDirectory(new File(System.getProperty("user.home")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "png");
		photoSelector.setFileFilter(filter);
		int result = photoSelector.showSaveDialog(null);
		File selectedPhoto = photoSelector.getSelectedFile();
		String fileName = selectedPhoto.getName();
		if(fileName.endsWith(".jpg") || fileName.endsWith(".JPG") || fileName.endsWith(".PNG") || fileName.endsWith(".png")) {
			if(result == JFileChooser.APPROVE_OPTION) {
				boolean photoSizeCheck = false;
				String path = selectedPhoto.getAbsolutePath();
				Path photoLocation = Paths.get(path);
				try {
					long bytes = Files.size(photoLocation);
					if(bytes < 160000) {
						photoSizeCheck = true;
					} else {
						photoSizeCheck = false;
					}
				} catch (IOException getPhoto) {
					getPhoto.printStackTrace();
				}
				if(photoSizeCheck) {
					Images.profile = new ImageIcon(path).getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
					label.setIcon(new ImageIcon(Images.profile));
				} else {
					JOptionPane.showMessageDialog(null, "The photo you selected is higher than 16 MB!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "This is not a photo!");
			}
		}
	}

}
