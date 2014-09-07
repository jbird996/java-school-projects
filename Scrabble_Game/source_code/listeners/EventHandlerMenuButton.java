package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.GUI;

/**
 * @author Michael
 *
 */
public class EventHandlerMenuButton implements ActionListener {
	private GUI _gui; //used to draw the scoreBoard with buttons

	/**
	 * @param g instantiates the _gui
	 */
	public EventHandlerMenuButton(GUI g){
		_gui = g;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Calls upon the method that will draw the scoreboard with buttons aka the menu..
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		_gui.drawEndGamePopUp();
	}

}
