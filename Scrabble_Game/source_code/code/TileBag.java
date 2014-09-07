package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author Jason, Cole, Mike, Bill
 * TileBag Class instantiated creating a arraylist and iterator via composition.
 * Once filled, will prevent duplicate draws by iterating over the list.
 *
 */
public class TileBag {
	private ArrayList<Tile> _tileSet;
	private Iterator<Tile> _it;
	
	/**
	 * No-argument constructor to create ArrayList and iterator via composition, as well as fill bag.
	 */
	public TileBag(){
		_tileSet = new ArrayList<Tile>();
		fillBag();
		_it = _tileSet.iterator(); 
	}
	
	/**
	 * @returns if the iterator has a next item then the bag is not empty and returns false
	 */
	public boolean isEmpty(){
		if(_it.hasNext())return false;
		else return true;
	}
	
	/**
	 * When called in the constructor, fillBag will create all tiles required for 
	 * game play with proper char and int values.
	 */
	public void fillBag(){
		for(int i = 0; i < 41; i++){
			_tileSet.add(new Tile('A',3));
		}
		for(int i = 0; i < 7; i++){
			_tileSet.add(new Tile('B',7));
		}
		for(int i = 0; i < 14; i++){
			_tileSet.add(new Tile('C',6));
			_tileSet.add(new Tile('U',6));
		}
		for(int i = 0; i < 21; i++){
			_tileSet.add(new Tile('D',5));
		}
		for(int i = 0; i < 56; i++){
			_tileSet.add(new Tile('E',1));
		}
		for(int i = 0; i < 11; i++){
			_tileSet.add(new Tile('F',7));
		}
		for(int i = 0; i < 10; i++){
			_tileSet.add(new Tile('G',7));
			_tileSet.add(new Tile('P',7));
			_tileSet.add(new Tile('Y',7));
			
		}
		for(int i = 0; i < 30; i++){
			_tileSet.add(new Tile('H',4));
			_tileSet.add(new Tile('R',4));
		}
		for(int i = 0; i < 35; i++){
			_tileSet.add(new Tile('I',4));
		}
		for(int i = 0; i < 2; i++){
			_tileSet.add(new Tile('J',8));
			_tileSet.add(new Tile('Q',8));
			_tileSet.add(new Tile('X',8));
			_tileSet.add(new Tile('Z',8));
		}
		for(int i = 0; i < 4; i++){
			_tileSet.add(new Tile('K',7));
		}
		for(int i = 0; i < 20; i++){
			_tileSet.add(new Tile('L',5));
		}
		for(int i = 0; i < 12; i++){
			_tileSet.add(new Tile('M',6));
			_tileSet.add(new Tile('W',6));
		}
		for(int i = 0; i < 33; i++){
			_tileSet.add(new Tile('N',4));
		}
		for(int i = 0; i < 37; i++){
			_tileSet.add(new Tile('O', 3));
		}
		for(int i = 0; i < 31; i++){
			_tileSet.add(new Tile('S', 4));
		}
		for(int i = 0; i < 45; i++){
			_tileSet.add(new Tile('T', 2));
		}
		for(int i = 0; i < 5; i++){
			_tileSet.add(new Tile('V', 7));
		}
		
		Collections.shuffle(_tileSet);
	}
	
	/**
	 * @return int number of items in the tileBag
	 */
	public int getSize(){
		return _tileSet.size();
	}
	
	/**
	 * @return Tile which is the next drawn from the bag, then iterates to the next in line.
	 */
	public Tile getTile(){
		if (_it.hasNext()){
			return _it.next();
		}
		
		return new Tile('_', 0); //expecting nullpointer exception for now . throw out of tile error etc
	}

	
}
