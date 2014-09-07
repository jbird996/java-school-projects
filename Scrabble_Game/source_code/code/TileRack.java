package code;

import java.util.ArrayList;

/**
 * @author Jason, Mike, Cole
 * 
 * Created to hold each player's tiles, so each player has a unique rack that is maintained and
 * only visible to the player.  Has a maximum size of 12, and methods to fill the rack when
 * depleted.
 *
 */
public class TileRack {
	private ArrayList<Tile> _tileList;//list holding the tiles in the rack
	private int _size = 0;//current size of rack
	private int _maxSize = 12;//max size of rack
	
	/**
	 * no-argument constructor to create a tale rack, used only when creating a new player.  
	 */
	public TileRack(){
		_tileList = new ArrayList<Tile>();
	}
	
	/**
	 * @param player - player associated with rack
	 * @param Tilebag - game bag of tiles
	 * fills or replenishes rack as needed
	 */
	public void fillRack(Player p, TileBag bag){
		for(_size = _size; _size < _maxSize; _size++){
			_tileList.add(bag.getTile());	
			
		}
		setOwner(p);
	}
	
	/**
	 * @param player - sets the owner of the tile rack
	 */
	public void setOwner(Player p){
		for(int i = 0; i < _tileList.size(); i++){
			_tileList.get(i).setOwner(p);
		}
	}
	
	/**
	 * @param tile - tile to be removed from rack
	 */
	public void remove(Tile t){
		_tileList.remove(t);
		_size --;
	}
	
	/**
	 * @param tile - tile to be added to rack
	 */
	public void add(Tile t){
		if(_tileList.size() < 14){
			_tileList.add(t);
			_size++;
		}
	}
	
	/**
	 * @return player's tile rack
	 */
	public ArrayList<Tile> getRack(){
		return _tileList;
	}
	
}
