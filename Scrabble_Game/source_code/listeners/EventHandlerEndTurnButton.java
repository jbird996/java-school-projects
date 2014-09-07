package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.GUI;
import code.Game;
import code.Player;

/**
 * @author Michael
 *
 */
public class EventHandlerEndTurnButton implements ActionListener {
	private Game _game; //Passed in from the gui used to check for endgame
	private GUI _gui;	//Used to redraw the screen
	private Player _player; //Used to set the owner of the tiles on the board upon succesfull placing

	/**
	 * @param g game instance to check for endgame
	 * @param gui	used to redraw the screen
	 * @param p	used to set owners of the tiles on the board upon succesful placement
	 */
	public EventHandlerEndTurnButton(Game g, GUI gui, Player p) {
		_game = g;
		_gui = gui;
		_player = p;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Checks if the game is over, if true it will draw the endgame scoreboard(drawEndGamePopUp())
	 * checks for the current player against the player that clicked the button to set the owner of the tiles on the board
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(_game.isEndGame())_gui.drawEndGamePopUp();
		
		if(_player.equals(_game.getCurrentPlayer())){
			if(_game.endTurn() == true){
				_game.getBoard().setAllOwnerToBoard();
				_gui.drawMainPanels();
			}
			
		}
	}
}
