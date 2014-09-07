package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import code.GUI;
import code.Game;
import code.Player;
import code.Tile;

/**
 * @author Michael
 *
 */
public class EventHandlerTileRack implements MouseListener{
	private Tile _tile; //the tile clicked in the rack
	private Game _game; //used to set the current tile that will be used in the place method and EventHandlerTile
	private Player _player; //used to enforce the current player only being able to click his rack
	
	/**
	 * @param game instantiates _game
	 * @param p instantiates _player
	 * @param t instantiates _tile
	 */
	public EventHandlerTileRack(Game game,Player p, Tile t) {
		_game = game;
		_tile = t;
		_player = p;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * enforces the law of the player clicking his rack being the current player
	 * sets current tile in the game class
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(_player.equals(_game.getCurrentPlayer())){
		_game.setCurrentTile(_tile);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	

}
