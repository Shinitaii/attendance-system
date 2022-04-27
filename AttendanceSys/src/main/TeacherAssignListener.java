package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherAssignListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		MainMenu.menuClicked(MainMenu.TeacherAssignDept);
		MainMenu.TeacherAssignDept.listCB.clear();
		MainMenu.TeacherAssignDept.obtainedDeptNames.clear();
		MainMenu.TeacherAssignDept.obtainedSecNames.clear();
		MainMenu.TeacherAssignDept.obtainedSubNames.clear();
		MainMenu.TeacherAssignDept.execute();
		
	}

}
