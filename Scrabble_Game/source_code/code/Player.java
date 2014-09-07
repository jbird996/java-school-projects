package code;

/**
 * @author Jason, Mike, Cole
 * When a player is created it has a name (String), score(int), and tile rack (tileRack). 
 * Also stores the updated score, last word, and score of previous turn.  
 */
public class Player {

	private String _name = "";//Name of the player
	private int _score = 0;//current total score of player
	private int _lastScore = 0;//score of last word played by player
	private String _lastWord = "";//last word played and scored by player
	private Tile	_lastTile;//last tile played by player
	private TileRack _rack;//player's tile rack
	
	/**
	 * empty constructor used only to create the "board" player
	 */
	public Player(){
		setName("Board");
		setScore(0);
	}
	
	/**
	 * @param string - creates new player with passed in name, assigns a tile rack
	 */
	public Player(String n){
		setName(n);
		_rack = new TileRack();
		_lastTile = new Tile();
	}
	
	/**
	 * @param int - adds passed in value to player's current score
	 */
	public void setScore(int i) {
		
		
		_lastScore = i;
		_score += i;
		
		if(_score < 0){
			_score = 0;
		}
	}
	
	/**
	 * @return int - current cumulative score of player
	 */
	public int getScore() {
		return _score;
	}
	
	/**
	 * @return int - last turn score of player
	 */
	public int getLastScore(){
		return _lastScore;
	}
	
	/**
	 * @param string - sets last word played by player
	 */
	public void setLastWord(String s){
		_lastWord = s;
		
	}
	/**
	 * @return string representation of last word played by player
	 */
	public String getLastWord(){
		return _lastWord;
	}
	
	/**
	 * @param tile - sets the last tile played by the player
	 */
	public void setLastTile(Tile t){
		_lastTile = t;
	}
	
	/**
	 * @return last tile played by player
	 */
	public Tile getLastTile(){
		return _lastTile;
	}
	
	/**
	 * @param String - sets the player's name
	 */
	public void setName(String n){
		_name = n;
	}
	
	/**
	 * @return String - name of player
	 */
	public String getName(){
		return _name;
	}
	
	/**
	 * @return player's tile rack
	 */
	public TileRack getRack(){
		return _rack;
	}
	
	/**
	 * @param player
	 * @return true if the player's name matches that of the passed in player
	 */
	public boolean equals(Player p){
		if(this.getName().equals(p.getName()))return true;
		else return false;
	}
}
