
package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import code.Board;
import code.GUI;
import code.Game;
import code.Player;
import code.Tile;
import code.TileBag;
import code.TileRack;

public class StageOneTests {
	Game _game;
	GUI _gui;
	Player _player;
	Tile _tile;
	TileBag _tilebag;
	TileRack _tilerack;
	Player _a;
	Player _b;


	
	@Before
	public void setUp() {
		_game = new Game("Mike");
		_a = new Player("a");
		_b = new Player("b");
		_game.register(_a);
		_game.register(_b);
		_game.start();
		
		//Setup here
	}	

	//Board tests
	
	@Test public void homeSquareNotNull(){
		setUp();
		assertTrue("", _game.getBoard().isEmpty(_game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow()));
	}
	@Test public void homeSquareAsterisk(){
		setUp();
		char c = 'a';
		c = _game.getBoard().get(_game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow()).getChar();
		assertTrue("The Home Square is not labeled with an * and may not be visually distinct to the player.", c == '*');
	}
	@Test
	public void homeSquareRowNull(){
		boolean expected = false;
		_game.getBoard().boardInit();
		boolean actual = _game.getBoard().getBoardMatrix()[_game.getBoard().homeSquareCol()][_game.getBoard().homeSquareRow()] == null;
		
		assertTrue("",expected == actual);
	}

	@Test
	public void homeSquareColNull() {
		boolean expected = false;
		_game.getBoard().boardInit();
		boolean actual = _game.getBoard().getBoardMatrix()[_game.getBoard().homeSquareCol()][_game.getBoard().homeSquareRow()] == null;
		assertTrue("homeSquareCol was on a null square.", actual == expected);
	}

	public void tileGetTest(){
		setUp();
		ArrayList<Tile> t = _a.getRack().getRack();
		Tile c = t.get(0);
		_game.getBoard().place(c, _game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow());
		Tile actual = _game.getBoard().get(_game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow());
		assertTrue("Didn't work, chief.", actual == c);
	}

	public void tilePlaceTest(){
		setUp();
		ArrayList<Tile> t = _a.getRack().getRack();
		Tile c = t.get(0);
		_game.getBoard().place(c, _game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow());
		boolean expected = true;
		boolean actual = _game.getBoard().place(c, _game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow());
		assertTrue("", expected == actual);
	}

	public void tileRetractTest(){
		setUp();
		ArrayList<Tile> t = _a.getRack().getRack();
		Tile c = t.get(0);
		_game.getBoard().place(c, _game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow());
		_game.getBoard().retract(c);
		boolean expected = true;
		boolean actual = _game.getBoard().isEmpty(_game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow());

		assertTrue("", expected == actual);
	}

	public void isEmptyTest(){
		setUp();
		boolean expected = true;
		boolean actual = _game.getBoard().isEmpty(_game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow());
		assertTrue("Nada.", actual == expected);
	}
	public void isEmptyTestFalse(){
		boolean expected = false;
		setUp();
		ArrayList<Tile> t = _a.getRack().getRack();
		Tile c = t.get(0);
		_game.getBoard().place(c, _game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow());
		boolean actual = _game.getBoard().isEmpty(_game.getBoard().homeSquareCol(), _game.getBoard().homeSquareRow());
		assertTrue("This Tile is Counted as Empty for Some Reason???", actual == expected);
	}

	public String expectedBoard(){


		_game.getBoard();
		String b = "";
		for (int i = 0; i < _game.getBoard().getBoardMatrix().length; i++ ){
			for (int j = 0; j < _game.getBoard().getBoardMatrix()[0].length; j++){
				if ( _game.getBoard().getBoardMatrix()[i][j] == null){ 
					b += ' ';
				}
				else{
					if( _game.getBoard().getBoardMatrix()[i][j] != null){

						if(_game.getBoard().getBoardMatrix()[i][j].getChar() == '*'){
							b+= '*';
						}
						else{b+="_";}
					}
				}

			}
			b+= '\n';
		}
		return b;
	}

	@Test
	public void testBoardConfig(){
		String expected = expectedBoard();
		String actual = _game.getBoard().boardConfiguration();



		assertTrue(""+expected+""+""+actual, expected.equals(actual));
	}

	//*****IGAME TESTS

	@Test
	public void orderofPlayTest(){
		Player p1, p2, p3, p4;
		p1 = new Player("A");
		p2 = new Player("b");
		p3 = new Player("c");
		p4 = new Player("d");
		_game.register(p1);
		_game.register(p2);
		_game.register(p3);
		_game.register(p4);

		ArrayList<Player> playerList = new ArrayList<Player>();
		playerList.add(p1);
		playerList.add(p2);
		playerList.add(p3);
		playerList.add(p4);

		boolean expected = true;
		boolean actual = _game.orderOfPlay().containsAll(playerList);
		assertTrue("", actual == expected);
	}

	@Test
	public void playerScoreTest1(){
		Player p1 = new Player();
		p1.setScore(50);
		int expected = 50;
		int actual = p1.getScore();

		assertTrue("Player score is "+p1.getScore(), expected == actual);
	}

	@Test
	public void playerScoreTest2(){
		Player p1 = new Player();
		p1.setScore(0);
		int expected = 0;
		int actual = p1.getScore();

		assertTrue("No, sorry.", expected == actual);
	}

	@Test
	public void playerScoreTest3(){

		String s = "Player One";
		Player p1 = new Player(s);
		p1.setScore(-1);
		int expected = 0;
		int actual = p1.getScore();

		assertTrue("Player score is"+p1.getScore(), expected == actual);
	}

	@Test
	public void registerTest(){
		String s = "Player One";
		Player p1 = new Player(s);
		boolean actual = _game.register(p1);
		boolean expected = true;
		assertTrue("", actual == expected);
	}
	@Test
	public void regsiterTestFalse(){
		String s = "Player One";
		Player p = new Player(s);
		p = null;
		boolean actual = _game.register(p);
		boolean expected = false;
		assertTrue("I have no idea how you got this to pass but good job?", actual == expected);

	}

	@Test
	public void startTest(){
		String s1 = "Player One";
		String s2 = "Player Two";
		Player p1 = new Player(s1);
		Player p2 = new Player(s2);
		boolean p1Reg = _game.register(p1);
		boolean p2Reg = _game.register(p2);
		boolean started = _game.start();
		boolean actual = p1Reg && p2Reg && started;
		boolean expected = true;
		assertTrue("Swag, but no bag.", actual == expected);
	}

	@Test
	public void endTurnTest(){
		boolean actual = _game.endTurn();
		boolean expected = true;
		assertTrue("Close, but no cigar.", actual == expected);
	}

	@Test
	public void lastTurnWordScoresTest(){
		HashMap<String,Integer> map = new HashMap<String,Integer>();

		map.put("Orange", 18);
		List<SimpleImmutableEntry<String, Integer>> actual =_game.lastTurnWordScores();
		boolean expected = true;

		assertTrue("", map.equals(actual) == expected);
	}

	
	@Test
	public void randomziedBoardShape(){
		
		Board board = new Board(_game.getPath());
		Board board2 = new Board(_game.getPath());
		Board board3 = new Board(_game.getPath());
		boolean a1 = false;
		if(board.boardConfiguration().equals(board2.boardConfiguration()) && board.boardConfiguration().equals(board3.boardConfiguration())){
			a1 = true;
		}
		assertFalse("", a1);
	}
	@Test
	public void wordValidation(){
		setUp();
		//set the _word to be some hypothetical word.
		ArrayList<Tile> thisword = new ArrayList<Tile>();
		Tile a1 = new Tile('a', 0);
		Tile a2 = new Tile('p', 0);
		Tile a3 = new Tile('p', 0);
		Tile a4 = new Tile('l', 0);
		Tile a5 = new Tile('e', 0);
		thisword.add(a1);
		thisword.add(a2);
		thisword.add(a3);
		thisword.add(a4);
		thisword.add(a5);
		ArrayList<Tile> curseword = new ArrayList<Tile>();
		curseword = _game.getBoard().getWord();
		curseword = thisword;
		boolean actual = _game.getBoard().checkValidWord(0);
		boolean expected = true;
		assertTrue(_game.getBoard().wordToString(thisword)+ " didnt register as valid",expected == actual);
	}
	@Test
	public void wordValidationFalse(){
		setUp();
		//set the _word to be some hypothetical word.
		ArrayList<Tile> thisword = new ArrayList<Tile>();
		Tile a1 = new Tile('a', 0);
		Tile a2 = new Tile('p', 0);
		Tile a3 = new Tile('b', 0);
		Tile a4 = new Tile('l', 0);
		Tile a5 = new Tile('e', 0);
		thisword.add(a1);
		thisword.add(a2);
		thisword.add(a3);
		thisword.add(a4);
		thisword.add(a5);
		thisword = _game.getBoard().getWord();
		boolean actual = _game.getBoard().checkValidWord(0);
		boolean expected = false;
		assertTrue("apble registered as valid",expected == actual);
	}
}

