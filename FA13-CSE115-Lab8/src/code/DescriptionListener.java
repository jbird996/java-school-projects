package code;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextArea;

/**
 * @author Jason
 *Handles the listener that awaits a click on each description.  When clicked, it sets the currently
 *selected description and JPanel in the GUI for future checking.
 */
public class DescriptionListener implements MouseListener {

	private JTextArea _selectedTextArea;
	private GraphicalUserInterface _gui;
	private String _s;
	
	public DescriptionListener(JTextArea j, GraphicalUserInterface g){
		
		_selectedTextArea = j;
		_gui = g;
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		_selectedTextArea =  (JTextArea) e.getSource();
		_s = _selectedTextArea.getText();
		_gui.setDescriptionString(_s);
		_gui.setDescriptionPanel(_selectedTextArea);
		System.out.println("Description Selected!");
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//  Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//  Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//  Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated method stub
	}

}
