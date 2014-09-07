package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import code.interfaces.IBoard;

/**
 * @author Jason, Mike, Cole
 * 
 * Manages tiles and word checking functions.  Initializes a board, generates and sets a home square
 * holds tiles placed during current word, sorts, extends, and checks for adjacent words.  Handles 
 * scoring of words as well.
 *
 */
public class Board implements IBoard {
	private int _homeSquareRow; //Location of the homesquare row
	private int _homeSquareCol; //Location of the homesquare column
	private Tile[][] _board;	//A matrix of tiles this is essentially the board
	private Tile _homeTile;		//The home square tile
	private ArrayList<Tile> _tempTiles;//Tiles placed onto the board during current turn
	private ArrayList<Tile> _word; //holds value of the intended word for this turn
	private ArrayList<Tile> _adjacentWord; //holds current adjacent word for checking
	private ArrayList<String> _dictionary; //full list of words in dictionary
	private ArrayList<String> _playedWords; //holds words that pass check and that are scored
	private Tile[][] _previousBoard; //A copy of the previous turn's board
	private boolean _isVertical = false;//true if _word orientation is vertical
	private boolean _isHorizontal = false;//true if _word orientation is horizontal
	private int _rLowerBound;//used in randomizing size of board
	private int _rUpperBound;//used in randomizing size of board
	private int _turnScore;//holds value of the current turn score
	private ArrayList<Tile> _modTiles;//used in generation of multipliers for tiles on init
	private String _path;//file path to dictionary file, passed as arg from main
	private Player _boardPlayer;//allows setting ownership of tiles to the board itself


	/**
	 * @param path to the dictionary file
	 * 
	 * Initializes the tile matrix(aka the board) with 20 rows and 20 columns
	 * Calls boardInit() to create the matrix with all empty tiles.
	 * Calls homeSquareInit() to randomize the homesquare and keep it in bounds
	 * @throws IOException 
	 */
	public Board(String path){
		_boardPlayer = new Player("Board");
		_word = new ArrayList<Tile>();
		_playedWords = new ArrayList<String>();
		_adjacentWord = new ArrayList<Tile>();
		_path = path;
		_tempTiles = new ArrayList<Tile>();
		_dictionary = new ArrayList<String>();
		_turnScore = 0;
		int rows = 20;
		int col = 20;
		_board = new Tile[rows][col];
		_previousBoard = _board;
		homeSquareInit();
		boardInit();
		this.place(_homeTile, homeSquareCol(), homeSquareRow() );
		try {
			initializeDictionary();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	/*
	 * Responsible for creating the home square.
	 * The homesquare is denoted by a * on the board
	 * The while statement checks to see if 
	 * the homesquare is placed on an illegal
	 * square and if it is, then it relocates it with another random value to ensure
	 * it's place on a valid square at all times.
	 * 
	 */
	public void homeSquareInit(){
		Random rR = new Random();
		Random rC = new Random();
		_homeTile = new Tile('*',0);
		_homeTile.setEmpty(true);

		_homeSquareRow = rR.nextInt(19);
		_homeSquareCol = rC.nextInt(19);

		while ((_rUpperBound <= _homeSquareRow) && (_homeSquareCol <= _rLowerBound) || 
				(_homeSquareRow <= _rLowerBound) && (_homeSquareCol >= _rUpperBound)){
			_homeSquareRow = rR.nextInt(19); 
			_homeSquareCol = rC.nextInt(19);
		}	

	}

	/*
	 * public method that returns the location of the homesquare row.
	 */
	@Override
	public int homeSquareRow() {		
		return _homeSquareRow;
	}
	
	/*
	 * public method that returns the integer location of the homesquare column.
	 */
	@Override
	public int homeSquareCol() {
		return _homeSquareCol;
	}

	/**
	 * @return Tile[][] matrix, the board itself.
	 */
	public Tile[][] getBoardMatrix(){
		return _board;
	}
	
	/*
	 * (non-Javadoc)
	 * @see code.interfaces.IBoard#get(int, int)
	 * returns the tile at the row and column in the board matrix
	 */
	@Override
	public Tile get( int col, int row) {
		return _board[col][row];
	}

	/*
	 * (non-Javadoc)
	 * @see code.interfaces.IBoard#place(code.Tile, int, int)
	 * Method used to remove tile from current player's tilerack, which is then passed 
	 * into the board, and added to the _tempTiles list, setting the location within the tile as well.
	 */
	@Override
	public boolean place(Tile tile, int col, int row) {
		if(tile != null && get(col,row).checkEmpty() == true ){
			_board[col][row] = tile;
			_board[col][row].getTile().setLocation(col, row);
			_tempTiles.add(tile);//_board[col][row].getTile());
			if(tile.getValue() > 0 && tile.getOwner().getRack().getRack().size() > 0) tile.getOwner().getRack().remove(tile);
			return true;

		}			

		else return false;
	}

	/* (non-Javadoc)
	 * @see code.interfaces.IBoard#retract(code.Tile)
	 * Method taking in a tile that is to be removed from the board (and _tempTile arraylist)
	 * And then added back to the current player's tilerack.
	 */
	@Override
	public boolean retract(Tile tile) { 

		for(int i = 0; i < _tempTiles.size();i++){
			if(_tempTiles.get(i).equals(tile)){
				_tempTiles.remove(tile);
				_board[tile.getCol()][tile.getRow()] = new Tile();
			}
		}
		tile.getOwner().getRack().add(tile);
		return true;

	}

	/* (non-Javadoc)
	 * @see code.interfaces.IBoard#isEmpty(int, int)
	 * Checks a location on the board and returns true if empty, false if the location is occupied
	 */
	@Override
	public boolean isEmpty(int row, int col) {
		if(_board[row][col].checkEmpty() == true)return true;

		else return false;
	}
	
	/**
	 * Sets the owner of all original tiles on the board (empty tiles) to the board player.
	 */
	public void setAllOwnerToBoard(){
		for (int col = 0; col < _board.length; col++ ){
			for (int row = 0; row < _board[0].length; row++){
				if(_board[col][row] != null)_board[col][row].setOwner(_boardPlayer);

			}
		}
	}

	/**
	 * Method to initialize the board, places "empty" tiles in valid spaces leaving null values
	 * in the tiles that are out of bounds. It also sets down the Tiles with modifiers
	 * on the board.
	 */
	public void boardInit(){
		Random r = new Random();
		Tile emptyTile = new Tile();

		_rLowerBound = r.nextInt(3) + 3;
		_rUpperBound = 17 - r.nextInt(4);

		for (int col = 0; col < _board.length; col++ ){
			for (int row = 0; row < _board[0].length; row++){
				if ( (col >= _rUpperBound) && (row <= _rLowerBound) || (col <= _rLowerBound) && (row >= _rUpperBound)){
					_board[col][row] = null;
				}
				else {
					_board[col][row] = new Tile();
				}
			}

		}

		this.setModTiles();

		for (int i = 0; i < _board.length; i++ ){
			for (int j = 0; j < _board[0].length; j++){

				if(_board[i][j] != null) {

					int selected = r.nextInt(_modTiles.size());
					Tile modTile = _modTiles.get(selected);
					_board[i][j].setLetterMult(modTile.getLetterMult());
					_board[i][j].setWordMult(modTile.getWordMult());
					
				}
			}

		}
		
	}

	/* (non-Javadoc)
	 * @see code.interfaces.IBoard#boardConfiguration()
	 * Returns string representation of the board
	 */
	@Override
	public String boardConfiguration() {
		String b = "";
		for (int i = 0; i < _board.length; i++ ){
			for (int j = 0; j < _board[0].length; j++){
				if ( _board[i][j] == null){
					b += ' ';
				}
				else {					
					b += _board[i][j].getWordMult();
				}
			}
			b+= '\n';
		}
		return b;
	}

	/**
	 * @return ArrayList<Tile>; The tiles placed this turn
	 */
	public ArrayList<Tile> getTempTiles(){
		return _tempTiles;

	}

	/**
	 * Method to clear ArrayList of tiles at the end of the turn, or if retracting all tiles
	 */
	public void clearTempTiles(){
		_tempTiles.clear();
	}

	/**
	 * @return current turn arraylist of tiles.
	 */
	public ArrayList<Tile> getWord(){
		return _word;
	}

	/**
	 * Method to allow clearing of board
	 */
	public void clearBoard(){
		for (int i = 0; i < _board.length; i++ ){
			for (int j = 0; j < _board[0].length; j++){

				if ( _board[i][j] != null){
					if( _board[i][j] != _previousBoard[i][j]){
						_word.add(_board[i][j]);

						if(get(i,j)!= null && get(i,j).isRetractable() == true) {
							_tempTiles.add(get(i,j));
							_board[i][j] = new Tile();

						}
					}
				}
			}
		}
	}

	/**
	 * Scans board and puts _word in order from L-->R or top-->bottom.
	 */
	public void sortTiles(){
		for (int i = 0; i < _board.length; i++ ){
			for (int j = 0; j < _board[0].length; j++){
				if ( _board[i][j] != null){
					for(int k = 0; k < _tempTiles.size(); k++){
						if(_board[i][j] == _tempTiles.get(k)){
							_word.add(_tempTiles.get(k));
						}
					}
				}
			}
		}
	}

	/**
	 * Will take the tiles placed by the player during the current turn, move to the beginning of the chain of non-empty tiles,
	 * and beginning with the first, add it the _word continuing until the first '_' is encountered. 
	 */
	public void extendWord(){
		Tile temp = _word.get(0);
		ArrayList<Tile> tempWord = new ArrayList<Tile>();
		if(_isVertical){
			while(temp.hasTop(this,temp.getCol(),temp.getRow())){
				temp = _board[temp.getCol()-1][temp.getRow()];	
			}
			if(temp.getChar() == '_'){
				temp = _board[temp.getCol()+1][temp.getRow()];
			}
			_word.clear();
			while(temp.getChar() != '_'){
				_word.add(temp);
				System.out.println("Letter added: " + temp.getChar());
				temp = _board[temp.getCol()+1][temp.getRow()];
			}
			if(temp.getChar() == '_'){
				_word.remove(temp);
			}
			
		}else if(_isHorizontal){
			while(temp.hasLeft(this,temp.getCol(),temp.getRow())){
				temp = _board[temp.getCol()][temp.getRow()-1];
			}
			if(temp.getChar() == '_'){
				temp = _board[temp.getCol()][temp.getRow()+1];
			}
			_word.clear();
			while(temp.getChar() != '_'){
				_word.add(temp);
				temp = _board[temp.getCol()][temp.getRow()+1];
			}
			if(temp.getChar() == '_'){
				_word.remove(temp);
			}
			
		}
	}

	/**
	 * @param Tile t
	 * Iterates over all tiles in the current turn word to determine if any new adjacent words were formed during the turn.
	 * If so, words are checked for validity, and scored as necessary.  
	 */
	public void extendAdjacent(Tile t){
		Tile temp = t;
		ArrayList<Tile> tempWord = new ArrayList<Tile>();
		if(_isVertical){
			while(temp.hasLeft(this,temp.getCol(),temp.getRow())){
				temp = _board[temp.getCol()-1][temp.getRow()];
			}
			while(temp.getChar() != '_'){
				tempWord.add(temp);
				temp = _board[temp.getCol()+1][temp.getRow()];
			}
			_adjacentWord = tempWord;
		}else if(_isHorizontal){
			while(temp.hasTop(this,temp.getCol(),temp.getRow())){
				temp = _board[temp.getCol()][temp.getRow()-1];
			}
			while(temp.getChar() != '_'){
				tempWord.add(temp);
				temp = _board[temp.getCol()][temp.getRow()+1];
			}
			_adjacentWord = tempWord;
		}
	}

	/**
	 * Determines the orientation of a word, 
	 * then sets either the _isVertical or _isHorizonal true based on the case.
	 */
	public void checkOrientation(){
		if(_word.get(0)!= null && _word.get(0).getRow() == _word.get(_word.size()-1).getRow()){
			System.out.println("vertical set");
			_isVertical = true;
		}else if(_word.get(0).getCol() == _word.get(_word.size()-1).getCol()){
			System.out.println("horiz set");
			_isHorizontal = true;
		}
	}

	/**
	 * @param list
	 * @return A 
	 * String representation of a given word.
	 * Finds the string representation of a given word by searching through the 
	 * tiles for their char. It then adds those chars into a string giving a 
	 * representation of the word.
	 */
	public String wordToString(ArrayList<Tile> list){
		char c = ' ';
		String s = "";
		for(Tile t:list){
			c = t.getChar();
			s += c;
		}
		return s;
	}

 
	/**
	 * @return A boolean determining validity of a given word.
	 * Checks the validity of a given word against a set dictionary file. It does
	 * this by comparing the string representation of a given word against the _dictionary
	 * ArrayList.
	 */
	public boolean checkValidWord(int i){	
		if(i == 0){
			return _dictionary.contains(wordToString(_word).toLowerCase());	
		}else if(i == 1){
			return _dictionary.contains(wordToString(_adjacentWord));
		}
		return false;// Stubbed, should be false
	}
	
	/**
	 * @return true if at least one of the tiles placed intersect a word already on the board.
	 */
	public Boolean hasAdjacent(){
		for(Tile t:_word){
			if(t.hasLeft(this, t.getCol(), t.getRow()) || t.hasRight(this, t.getCol(),t.getRow())
					|| t.hasLeft(this, t.getCol(), t.getRow()) || t.hasRight(this, t.getCol(),t.getRow())){
				return true;
			}
		}	
		return true; // Needs to be false here, stubbed for function
	}


	/**
	 * @return int which is the score of any new words that have been formed by tileplacement.
	 * Iterates over tiles in present word, checking for adjacencies, comparing adjacent words to those already played, and scoring 
	 * if valid.
	 */
	public int checkAdjacent(){
		int i = 0;
		for(Tile t:_word){
			if(_isVertical){
				if(t.hasLeft(this, t.getCol(), t.getRow()) || t.hasRight(this, t.getCol(),t.getRow())){
					extendAdjacent(t);
					String s = wordToString(_adjacentWord);
					if(!_playedWords.contains(s)){
						if(!checkValidWord(1)){
							return -1;						
						}else{
							i = i + scoreAdjacent();
						}
					}
				}				
			}else if(_isHorizontal){
				if(t.hasTop(this, t.getCol(), t.getRow()) || t.hasBottom(this, t.getCol(),t.getRow())){
					extendAdjacent(t);
					String s = wordToString(_adjacentWord);
					if(!_playedWords.contains(s)){
						if(!checkValidWord(1)){
							return -1;						
						}else{
							i = i + scoreAdjacent();
						}
					}
				}
			}
		}
		return i;
	}
	
	/**
	 * @return int value of the score of the word placed this turn.
	 */
	public int scoreMain(){
		int i = 0;
		for(Tile t: _word){
			i = i + t.getValue();
		}
		_turnScore = _turnScore + i;
		_playedWords.add(wordToString(_word));
		return i;
	}
	
	/**
	 * @return int value of the score of a new adjacent word.
	 */
	public int scoreAdjacent(){
		int i = 0;
		for(Tile t:_adjacentWord){
			i = i + t.getValue();
		}
		_turnScore = _turnScore + i;
		_playedWords.add(wordToString(_adjacentWord));
		_adjacentWord.clear();
		return i;
	}
	
	/**
	 * @return int value of the current turn total score.
	 */
	public int turnScore(){
		int temp = _turnScore;
		_turnScore = 0;
		return temp;
	}
	
	/**
	 * @return string representation of the word played in the present turn.
	 */
	public String getWordString(){
		String s = wordToString(_word);
		_word.clear();
		return s;
	}
	
	/**
	 * @return true if one of the tiles placed this turn are aver the home square.
	 */
	public boolean checkOnHome(){
		for(Tile t: _word){
			if(t.getCol() == homeSquareCol() && t.getRow() == homeSquareRow()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Resets all arraylists at the end of a turn.
	 */
	public void clearWords(){
		_word.clear();
		_adjacentWord.clear();
		_tempTiles.clear();
		_isVertical = false;
		_isHorizontal = false;
	}
	
	/**
	 * Takes String arg for dictionary path, scans each line adding it to an arraylist dictionary for word validity checking.
	 */
	
	String initializeDictionary() throws IOException {
		System.out.println(_path);
	    BufferedReader br = new BufferedReader(new FileReader(_path +".txt"));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	            _dictionary.add(line);
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	        System.out.println(_dictionary);
	    }
	}

	/**
	 * This method first determines the amount of available spaces on the board, 
	 * it does this by counting the amount of playable tiles on the board upon creation.
	 * Based off of that determines how many multipliers need to be on the board. 
	 * It then randomly pairs Letter Multipliers and Word Multipliers for each individual tile
	 * After that has been completed it will store those tiles into the _modTiles ArrayList.
	 */
	public void setModTiles(){

		int spaces = 0;

		for (int i = 0; i < _board.length; i++ ){
			for (int j = 0; j < _board[0].length; j++){
				if ( _board[i][j] != null){
					spaces++;
				}
			}
		}

		int letterTwos = (int) (spaces * (10.0f/100.0f));
		int letterOnes = (int) (spaces * (80.0f/100.0f));
		int letterZeros = (int) (spaces * (5.0f/100.0f));
		int letterNeg = (int) (spaces * (5.0f/100.0f));

		int wordThree = (int) (spaces * (5.0f/100.0f));
		int wordTwo = (int) (spaces * (15.0f/100.0f));
		int wordOne = (int) (spaces * (75.0f/100.0f));
		int wordZero = (int) (spaces * (5.0f/100.0f));

		ArrayList<Tile> tempModTiles = new ArrayList<Tile>();
		_modTiles = new ArrayList<Tile>();
		Random r = new Random();

		for(int i = 0; i < letterTwos; i++){
			Tile t = new Tile();
			t.setLetterMult(2);
			tempModTiles.add(t);
		}

		for(int i = 0; i < letterOnes; i++){
			Tile t = new Tile();
			t.setLetterMult(1);
			tempModTiles.add(t);
		}

		for(int i = 0; i < letterZeros; i++){
			Tile t = new Tile();
			t.setLetterMult(0);
			tempModTiles.add(t);
		}

		for(int i = 0; i < letterNeg; i++){
			Tile t = new Tile();
			t.setLetterMult(-1);
			tempModTiles.add(t);
		}

		for(int i = 0; i < wordThree; i++){
			Tile t = tempModTiles.get(r.nextInt(tempModTiles.size()));
			t.setWordMult(3);
			_modTiles.add(t);

		}

		for(int i = 0; i < wordTwo; i++){
			Tile t = tempModTiles.get(r.nextInt(tempModTiles.size()));
			t.setWordMult(2);
			_modTiles.add(t); 

		}

		for(int i = 0; i < wordOne; i++){
			Tile t = tempModTiles.get(r.nextInt(tempModTiles.size()));
			t.setWordMult(1);
			_modTiles.add(t);

		}

		for(int i = 0; i < wordZero; i++){
			Tile t = tempModTiles.get(r.nextInt(tempModTiles.size()));
			t.setWordMult(0);
			_modTiles.add(t);

		}
	}
}
