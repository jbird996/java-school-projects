package code;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

/**
 * @author Jason
 *Handles click on each image, sets the currently selected image and JLabel in the GUI for future checking.
 */
public class ImageListener implements MouseListener {

	JLabel _selectedLabel;
	GraphicalUserInterface _gui;
	String _s;
	public ImageListener(JLabel j, GraphicalUserInterface g){
		
		_selectedLabel = j;
		_gui = g;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		_selectedLabel =  (JLabel) e.getSource();
		_s = _selectedLabel.getIcon().toString();
		_gui.setImageString(_s);
		_gui.setImageLabel(_selectedLabel);
		System.out.println("Image Selected!");
		
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
	public void mouseReleased(MouseEvent arg0) {
		//  Auto-generated method stub
	}

}
