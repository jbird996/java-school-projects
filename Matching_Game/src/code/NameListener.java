package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * @author Jason
 *Handles clicks on each name button, sets the currently selected name and button in the GUI.
 */
public class NameListener implements ActionListener {

	GraphicalUserInterface _gui;
	String _s;
	JButton _button;
	
	public NameListener(JButton j, GraphicalUserInterface g){
		_button = j;
		_gui = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		_s = _button.getText();
		_gui.setNameString(_s);
		_gui.setNameButton(_button);
		System.out.println("Name Selected!");
		
	}

}
