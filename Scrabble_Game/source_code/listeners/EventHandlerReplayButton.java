package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.GUI;
import code.Game;

/**
 * @author Michael
 *
 */
public class EventHandlerReplayButton implements ActionListener {
	private GUI _gui; //used to call the replay method
	
	/**
	 * @param g instantiates _gui.
	 */
	public EventHandlerReplayButton(GUI g){
		_gui = g;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * calls the replay() method in GUI to reset the game.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		_gui.replay();
	}
}
