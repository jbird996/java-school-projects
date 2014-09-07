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
public class EventHandlerTile implements MouseListener{
	private GUI _gui; //used to be able to use methods in the GUI class
	private Tile _tile; //Used to collect the needed information on the tile clicked
	private Game _game; //Used enable method use and getters from the game
	private Player _player; //Used to check owners and ensure no out of turn play

	/**
	 * @param g instantiates _gui
	 * @param game instantiates _game
	 * @param p instantiates _player
	 * @param t instantiates _tile
	 */
	public EventHandlerTile(GUI g,Game game,Player p, Tile t){
		_gui = g;
		_game = game;
		_tile = t;
		_player = p;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 *  if statement makes sure the player clicking is the currentPlayer
	 * 	if statement checks for first turn to ensure homesquare placement, will call drawHomeSquarePopUp if a tile is clicked in the wrong spot
	 * 	if statement checks for first turn to place on the homesquare and check firstturn to false.
	 * 	if statement checks for first turn to be false if it is it wil place the tile, made firstturn false, sets current tile to null and redraws the screen
	 * ensures that the tile being clicked to be retracted is of value, and is the proper owner clicking the tile
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(_player.equals(_game.getCurrentPlayer())){
			if(_game.getCurrentTile() != null && _tile.getValue() == 0){

				if(_game.isFirstTurn() == true &&( _tile.getRow() != _game.getBoard().homeSquareRow() 
												|| _tile.getCol() != _game.getBoard().homeSquareCol())){
					System.out.println("Place on homesquare!");
					_gui.drawHomeSquarePopUp();
					return;
				}

				if(_game.isFirstTurn() == true && (_tile.getRow() == _game.getBoard().homeSquareRow() 
												&& _tile.getCol() == _game.getBoard().homeSquareCol())){
					_game.setFirstTurn(false);
				}

				if(_game.isFirstTurn() == false){
					_game.getBoard().place(_game.getCurrentTile(), _tile.getCol(), _tile.getRow());
					_game.setFirstTurn(false);
					_game.setCurrentTile(null);
					_gui.drawMainPanels();

				}
			}

			if(_tile.getValue() > 0 && _tile.getOwner().equals(_game.getCurrentPlayer())){
				System.out.println(_tile.getOwner().getName());

				_game.getBoard().retract(_tile);
				_gui.drawMainPanels();
			}
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
