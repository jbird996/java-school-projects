package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.GUI;

/**
 * @author Michael
 *
 */
public class EventHandlerExitButton implements ActionListener {
	private GUI _gui; //used to call exitGame() on the _gui.
	/**
	 * @param g used to set the instance variable _gui
	 */
	public EventHandlerExitButton(GUI g){
		_gui = g;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Calls the exit game method on _gui.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		_gui.exitGame();
	}

}
