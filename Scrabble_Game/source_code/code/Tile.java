package code;


/**
 * @author Jason, Mike, Cole
 * 
 * Tile() creates empty tile, Tile(char,int) creates tile with associated char and int values.
 * Each tile has an owner, letter/word multipliers, and a suite of accessor and mutator methods.  
 * Each tile can check the tiles immediately next to it to see if there is a non-empty tile.
 *
 */
public class Tile {
	private char _c = '_';//char value of tile
	private int _value = 0;//point value of tile
	private boolean _isEmpty = true;//true if tile lacks point value
	private boolean _retractable = false;//true if tile is retractable
	private Player _owner;//the player in possession of the tile
	private int _row;//row location of tile on board
	private int _col;//column location of tile on board
	private int _wordMult;//word multiplier of tile
	private int _letterMult;//letter multiplier of tile
	private boolean _inHand = false;//in hand status of tile


	/**
	 * no argument constructor used exclusively to create empty tiles
	 */
	public Tile(){
		_owner = new Player();
	}

	/**
	 * @param char
	 * @param int
	 * Constructs tile with char and int values
	 */
	public Tile(char c, int v){
		setChar(c);
		setValue(v);
		setRetractable(true);
		_isEmpty = false;
	}

	/**
	 * @param int col
	 * @param int row
	 * sets location of tile when placed on board(col,row)
	 */
	public void setLocation(int col, int row){
		_row = row;
		_col = col;
	}

	/**
	 * @return int value for row location on board
	 */
	public int getRow(){
		return _row;
	}

	/**
	 * @return int value for column location on board
	 */
	public int getCol(){
		return _col;
	}
	/**
	 * @return player - owner of tile
	 */
	public Player getOwner(){
		return _owner;
	}

	/**
	 * @param player - set tile owner to passed player
	 */
	public void setOwner(Player p){
		_owner = p;
	}

	/**
	 * @return boolean true if tile is empty
	 */
	public boolean checkEmpty(){
		return _isEmpty;
	}
	
	/**
	 * @param boolean - sets the empty status of a tile
	 */
	public void setEmpty(boolean b){
		_isEmpty = b;
	}
	
	/**
	 * @param char - sets tile char during construction
	 */
	public void setChar(char c){
		_c = c;
	}

	/**
	 * @return char value of tile
	 */
	public char getChar(){
		return _c;
	}

	/**
	 * @param int
	 * 
	 * sets int value of tile, used by constructor
	 */
	public void setValue(int v){
		_value = v;
	}
	/**
	 * @return int value of tile (letter score)
	 */
	public int getValue(){
		return _value;
	}

	/**
	 * @return this tile
	 */
	public Tile getTile(){
		return this;
	}

	/**
	 * @return boolean true if tile is able to be retracted
	 */
	public boolean isRetractable(){
		return _retractable;
	}

	/**
	 * @param boolean
	 * 
	 * sets retractable status
	 */
	public void setRetractable(Boolean b){
		_retractable = b;
	}

	/**
	 * @param Tile t
	 * @return boolean true if char of argument equals char of tile
	 * 
	 * compares equality based solely on char
	 */
	public boolean equals(Tile t){
		if (this.getChar() == t.getChar())return true;
		else return false;
	}


	/**
	 * @param board
	 * @param column
	 * @param row
	 * @return - true if a non-empty is located above tile.
	 * 
	 * checks above current tile for a non-empty tile
	 */
	public boolean hasTop(Board b, int col, int row){
		if( row > 0 && b.get(col, row-1) != null ){
			return b.get(col, row-1).getChar() != _c;
		}
		return false;
	}
	
	/**
	 * @param board
	 * @param column
	 * @param row
	 * @return - true if a non-empty is located below tile.
	 * 
	 * checks below current tile for a non-empty tile
	 */
	public boolean hasBottom(Board b, int col, int row){
		if( row < 19 && b.get(col, row+1) != null ){
			return b.get(col, row+1).getChar() != _c;
		}
		return false;
	}
	/**
	 * @param board
	 * @param column
	 * @param row
	 * @return - true if a non-empty is located to the left of tile.
	 * 
	 * checks space to the left of current tile for a non-empty tile
	 */
	public boolean hasLeft(Board b, int col, int row){
		if( col > 0 && b.get(col-1, row) != null ){
			return b.get(col-1, row).getChar() != _c;
		}
		return false;
	}
	
	/**
	 * @param board
	 * @param column
	 * @param row
	 * @return - true if a non-empty is located to the right of tile.
	 * 
	 * checks space to the right of current tile for a non-empty tile
	 */
	public boolean hasRight(Board b, int col, int row){
		if( col < 19 && b.get(col +1, row) != null ){
			return b.get(col+1, row).getChar() != _c;
		}
		return false;
	}

	/**
	 * @param i
	 * sets word multiplier of tile
	 */
	public void setWordMult(int i){
		_wordMult = i;
	}

	/**
	 * @param i
	 * sets letter multiplier of tile
	 */
	public void setLetterMult(int i){
		_letterMult = i;
	}

	/**
	 * @return int value of tile word multiplier
	 */
	public int getWordMult(){
		return _wordMult;
	}

	/**
	 * @return int value of tile letter multiplier
	 */
	public int getLetterMult(){
		return _letterMult;
	}

	/**
	 * @return boolean
	 * true if a tile is in hand 
	 */
	public boolean getInHand(){
		return _inHand;
	}

	/**
	 * @param b
	 * takes boolean and sets the in hand status of a tile
	 */
	public void setInHand(Boolean b){
		_inHand = b;
	}
}
