package code;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import code.interfaces.IGame;

/**
 * @author Jason, Mike, Cole
 * 
 * Main gameplay class of the game, creates the board, registers players, sets order of play
 * manages current player and currently selected tile.  Also handles the flow of play and 
 * ending of turns.
 *
 */
public class Game implements IGame {	
	private ArrayList<Player> _players;//List of players in the game
	private TileBag 		  _tileBag;//Holds all the originally generated tiles, drawn from by players
	private	Board			  _board;//game board itself
	private Player			  _currentPlayer;//player whose turn it is
	private Tile			  _currentTile;//currently selected tile 
	private Iterator<Player>  _it;//iterates over player list to maintain order of play
	private String _path;//path to dictionary file, passed to board
	private boolean _firstTurn;// true if it is the very first turn

	/**
	 * @param path to dictionary file which is passed to board when created at start
	 * 
	 * initiates game start, initializes player list and first turn status.
	 */
	public Game(String path){
		_path = path;
		_players = new ArrayList<Player>();
		_tileBag = new TileBag();
		_firstTurn = true;
	}

	
	/* (non-Javadoc)
	 * @see code.interfaces.IGame#orderOfPlay()
	 * Returns the order of play the players are in.
	 */
	@Override
	public List<Player> orderOfPlay() {
		return _players	;
	}

	/* (non-Javadoc)
	 * @see code.interfaces.IGame#score(code.Player)
	 * Returns the score of a passed in player.
	 */
	@Override
	public int score(Player player) {
		return player.getScore();
	}
	
	
	
	/**
	 * @param s
	 * Creates a new player with a passed in name.
	 */
	public void createPlayer(String s){
		register(new Player(s));
	}

	/* (non-Javadoc)
	 * @see code.interfaces.IGame#register(code.Player)
	 * Registers a new player to the game 
	 * and gives them a rack of tiles which is then filled.
	 */
	@Override
	public boolean register(Player player) {
		if(player != null){
		Player p = player;
		p.getRack().fillRack(player, _tileBag);
		p.getRack().setOwner(p);
		_players.add(p);
		return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see code.interfaces.IGame#start()
	 * 
	 * Starts the game, populates player list and order of play, creates the board.
	 */
	@Override
	public boolean start()  {
		if (_players.size() >= 2){
			Collections.shuffle(_players);
			_board = new Board(_path);
			_it = _players.iterator();
			_currentPlayer = _it.next();
			return true;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see code.interfaces.IGame#endTurn()
	 * 
	 * attempts to end the current turn and if successful returns a true value.  Calls methods
	 * on the board to sort, orient, extend and check current word, then check adjacent words
	 * if successful, scores words, resets temporary values in board, sets player's score and
	 * last word, and iterates to next player
	 */
	@Override
	public boolean endTurn() {
		if(!_board.getTempTiles().isEmpty()){
			_board.sortTiles();
			_board.checkOrientation();
			_board.extendWord();
			if(!_firstTurn){
				if(_board.checkValidWord(0)){
					_board.scoreMain();
					_board.scoreAdjacent();
					int tempScore = _board.turnScore();
					String s = _board.getWordString();
					_currentPlayer.setScore(tempScore);
					_currentPlayer.setLastWord(s);
					System.out.println("Valid Word!: "+s+" Score: "+tempScore);
					_currentPlayer.getRack().fillRack(_currentPlayer, getBag());
					nextPlayer();
					_board.clearWords();
					return true;
					//check for end of game criteria
				}	
			}
		}
		return false;	
	}
		
	
	/**
	 * @return true if the current turn is the first turn
	 */
	public boolean isFirstTurn(){
		return _firstTurn;
	}
	
	/**
	 * @param boolean - sets first turn status of game
	 */
	public void setFirstTurn(Boolean b){
		_firstTurn = b;
	}
	
	/**
	 * @return player - the current player for the present turn
	 */
	public Player getCurrentPlayer(){
		return _currentPlayer;
	}
	
	/**
	 * @return the game board itself
	 */
	public Board getBoard(){
		return _board;
	}

	/* (non-Javadoc)
	 * @see code.interfaces.IGame#lastTurnWordScores()
	 * 
	 * Iterates through list of players, returns list of last word played by each player
	 * with associated score of previous turn
	 */
	@Override
	public List<SimpleImmutableEntry<String, Integer>> lastTurnWordScores() {
		ArrayList<SimpleImmutableEntry<String,Integer>> list = new ArrayList<SimpleImmutableEntry<String,Integer>>(); 
		for(Player p:_players){
			String s = p.getLastWord();
			int i = p.getLastScore();
			SimpleImmutableEntry<String,Integer> sie = new SimpleImmutableEntry<String,Integer>(s,i);
			list.add(sie);
		}
		return list;
	}
	
	/**
	 * @return path to the dictionary (string)
	 */
	public String getPath(){
		return _path;
	}
	
	/**
	 * @return tilebag associated with the game
	 */
	public TileBag getBag(){
		return _tileBag;
	}
	
	/**
	 * @return currently selected tile
	 */
	public Tile getCurrentTile(){
		return _currentTile;
	}
	
	/**
	 * @param Tile - sets current tile field to passed tile
	 */
	public void setCurrentTile(Tile t){
		_currentTile = t;
	}

	/**
	 * Iterates through list of players at end of turn, re-initializes iterator once through 
	 * the list  
	 */
	public void nextPlayer(){
		if(_it.hasNext() == true){
			_currentPlayer = _it.next();
		}
		else {
			_it = _players.iterator();
			_currentPlayer = _it.next();
		}
	}
	
	/**
	 * @return arraylist of players in game
	 */
	public ArrayList<Player> getPlayers(){
		return _players;
	}
	
	public boolean isEndGame(){
		return _tileBag.isEmpty();
	}		
}
