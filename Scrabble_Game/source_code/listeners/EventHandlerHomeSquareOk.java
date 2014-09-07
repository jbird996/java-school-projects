package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

/**
 * @author Michael
 *
 */
public class EventHandlerHomeSquareOk implements ActionListener {
	private JDialog _dialog; //used to setVisible to false
	
	/**
	 * @param j sets _dialog
	 */
	public EventHandlerHomeSquareOk(JDialog j){
		_dialog = j; 
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Sets the dialog that was passed in to false.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		_dialog.setVisible(false);
	}

}
