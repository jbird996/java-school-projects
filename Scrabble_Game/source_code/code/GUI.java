package code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import listeners.EventHandlerEndTurnButton;
import listeners.EventHandlerExitButton;
import listeners.EventHandlerHomeSquareOk;
import listeners.EventHandlerMenuButton;
import listeners.EventHandlerReplayButton;
import listeners.EventHandlerTile;
import listeners.EventHandlerTileRack;

/**
 * @author Michael
 *
 */
public class GUI implements Runnable {
	private String _path; //the path for the dictonary
	private ArrayList<String> _players; //the strings passed in for player names in the run confi
	private Game	_game;//creates a game instance
	private Tile[][] _board;//creates the Tile matrix that get's copied from _game.getBoardMatrix()
	private ArrayList<JFrame> _frames; //Arraylist of all the players frames
	private ArrayList<JPanel> _mainPanels, _boardPanels, _rackPanels, _infoPanels, _endTurnPanels;//Arraylsts for all the players components of the frame

	/**
	 * @param path dictionary path file
	 * @param players player strings from the run config
	 */
	public GUI(String path, ArrayList<String> players){
		_path = path;
		_players = players;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * Instantiates all the arraylists
	 * Starts the game if .start returns true it add the proper amount of components to each arraylist
	 * the calls drawMainPanels() to start the actual drawing.
	 */
	@Override
	public void run() {
		_game = new Game(_path);

		for(int i = 0; i < _players.size(); i++){
			_game.createPlayer(_players.get(i));
		}
		_frames		= new ArrayList<JFrame>();
		_mainPanels = new ArrayList<JPanel>();
		_boardPanels= new ArrayList<JPanel>();
		_rackPanels = new ArrayList<JPanel>();
		_infoPanels = new ArrayList<JPanel>();
		_endTurnPanels = new ArrayList<JPanel>();

		if(_game.start() == true){
			for(int i = 0; i < _game.getPlayers().size(); i++){
				_frames.add(new JFrame(_game.getPlayers().get(i).getName()));
				_frames.get(i).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
				_mainPanels.add(new JPanel());
				_boardPanels.add(new JPanel());
				_rackPanels.add(new JPanel());
				_infoPanels.add(new JPanel());
				_endTurnPanels.add(new JPanel());
			}
			drawMainPanels();
		}
	}

	/**
	 * used for all draws as a method to update all players panels
	 * calls all the separate frame components draw methods
	 * adds mainpanels to the frames
	 * sets mainpanels titles to the player names
	 * 
	 */
	public void drawMainPanels(){
		for(int i = 0; i <  _game.getPlayers().size(); i++){
			_mainPanels.get(i).setLayout(new BoxLayout(_mainPanels.get(i),3));
			drawBoardPanels(i); 
			drawRackPanels(i);
			drawInfoPanels(i);
			drawEndTurnPanels(i);

			_frames.get(i).add(_mainPanels.get(i));
			_frames.get(i).pack();
			_frames.get(i).setVisible(true);
			_frames.get(i).revalidate();
			_boardPanels.get(i).revalidate();
		}

		for(int i = 0; i < _game.getPlayers().size(); i++){
			if(_frames.get(i).getTitle().equals(_game.getCurrentPlayer().getName()) == false)_frames.get(i).toBack();
		}
	}

	/**
	 * @param i received from the drawMainPanels used as an index for each players frame so they all have unique info
	 * sets _board to the underlying boardmatrix in the board class
	 * removes everythings from the _boardPanels arraylist for redrawing
	 * sets layout to 20x20 for the tiles to draw proper
	 * traverses the matrix adding listeners to the tiles and adding the proper character to each tile
	 * also sets the borders, the size of each tile and the background colors
	 * 
	 */
	public void drawBoardPanels(int i){
		_board = _game.getBoard().getBoardMatrix();
		_boardPanels.get(i).removeAll();
		_boardPanels.get(i).setLayout(new GridLayout(20,20));
		for (int col = 0; col < _board.length; col++ ){
			for (int row = 0; row < _board[0].length; row++){
				//				if(_board[col][row] != null && _board[col][row].getTile().getValue() > 0){
				if(_board[col][row] != null){
					_board[col][row].getTile().setLocation(col, row);
					EventHandlerTile tileListener = new EventHandlerTile(this,_game,_game.getPlayers().get(i), _board[col][row].getTile());
					char c = _game.getBoard().get(col, row).getChar();
					String s = "";
					s += c;
					JTextArea jemptyTile = new JTextArea(s);
					
					jemptyTile.setBackground(new Color(255,255,255));
					jemptyTile.setEditable(false);
					Border border = BorderFactory.createLineBorder(Color.BLACK);
					if(_board[col][row].getTile().getOwner() == _game.getCurrentPlayer()){
						border = BorderFactory.createLineBorder(Color.RED);
					}
					Dimension preferredSize = new Dimension();
					preferredSize.setSize(32, 32);
					jemptyTile.setBorder(border);
					jemptyTile.setPreferredSize(preferredSize);
					jemptyTile.setFont(jemptyTile.getFont().deriveFont(30.0f));

					jemptyTile.addMouseListener(tileListener);
					
					_boardPanels.get(i).add(jemptyTile);
				}
				else _boardPanels.get(i).add(new JLabel(""));

			}	
			_boardPanels.get(i).setVisible(true);
			_mainPanels.get(i).add(_boardPanels.get(i));
		}
	}

	/**
	 * @param i gets the right index from the drawMainPanels() method to insure proper player display
	 * removes all components from _rackPanels for redraw purposes
	 * adds all components back including the listeners
	 * sets the font and layouts accordingly
	 */
	public void drawRackPanels(int i){
		_rackPanels.get(i).removeAll();
		for (int j = 0; j < _game.getPlayers().get(i).getRack().getRack().size(); j++){
			EventHandlerTileRack tileRackListener = new EventHandlerTileRack(_game,_game.getPlayers().get(i), _game.getPlayers().get(i).getRack().getRack().get(j));
			char c = _game.getPlayers().get(i).getRack().getRack().get(j).getChar();
			String s = "";
			s += c;
			JLabel jl = new JLabel(s);
			jl.setFont(jl.getFont().deriveFont(26.0f));
			jl.addMouseListener(tileRackListener);
			_rackPanels.get(i).add(jl);
		}
		_rackPanels.get(i).setLayout(new GridLayout(1,_game.getPlayers().get(i).getRack().getRack().size()));
		_mainPanels.get(i).add(_rackPanels.get(i));
	}

	/**
	 * @param i gets the right index from the drawMainPanels() method to insure proper player info displays
	 * removes all components from _infoPanels arrayList to ensure proper redraw
	 * readds all components
	 * sets proper info to display when it is and is not the players turn
	 * sets proper info to display for the currentPlayer()
	 * 
	 */
	public void drawInfoPanels(int i){
		_infoPanels.get(i).removeAll();
		_infoPanels.get(i).setLayout(new BoxLayout(_infoPanels.get(i),3));
		JLabel pLabel = new JLabel("Player: " +_game.getPlayers().get(i).getName() +" Score: " +_game.getPlayers().get(i).getScore());
		pLabel.setFont(pLabel.getFont().deriveFont(25.0f));

		if(_game.getCurrentPlayer().equals(_game.getPlayers().get(i)) == false){
			pLabel.setFont(pLabel.getFont().deriveFont(20.0f));
		}
		_infoPanels.get(i).add(pLabel);	
		for(int j = 0; j < _game.getPlayers().size(); j++){			
			if(_game.getPlayers().get(j).equals(_game.getCurrentPlayer()) && _game.getCurrentPlayer().getName().equals(_frames.get(i).getTitle()) == false){
				JLabel iLabel = new JLabel("Current Player: " +_game.getPlayers().get(j).getName() +" Score: " +_game.getPlayers().get(j).getScore());
				iLabel.setFont(iLabel.getFont().deriveFont(20.0f));
				_infoPanels.get(i).add(iLabel); 
			}
			else if(_game.getPlayers().get(j).equals(_game.getCurrentPlayer()) == false){
				JLabel iLabel = new JLabel("Player: " +_game.getPlayers().get(j).getName() +" Score: " +_game.getPlayers().get(j).getScore());
				_infoPanels.get(i).add(iLabel); 
			}
		}
		_infoPanels.get(i).setVisible(true);
		_mainPanels.get(i).add(_infoPanels.get(i));

	}

	/**
	 * @param i gets the right index from the drawMainPanels() method to insure proper player info displays
	 * removes all components for redraw purposes
	 * re adds all the buttons and listeners
	 * changes end turn text depending if it's that players turn or not
	 *
	 */
	public void drawEndTurnPanels(int i){
		_endTurnPanels.get(i).removeAll();
		JButton b = new JButton("End Turn");
		JButton mB = new JButton("Menu");
		
		_endTurnPanels.get(i).setLayout(new GridLayout(2,1));
		_endTurnPanels.get(i).add(b);
		_endTurnPanels.get(i).setVisible(true);
		EventHandlerEndTurnButton endButtonListener = new EventHandlerEndTurnButton(_game,this,_game.getPlayers().get(i));
		EventHandlerMenuButton		menuButtonListener = new EventHandlerMenuButton(this);
		b.addActionListener(endButtonListener);
		mB.addActionListener(menuButtonListener);
		if(_game.getCurrentPlayer().equals(_game.getPlayers().get(i)) == false){
			b.setEnabled(false);
			b.setText("It is not your turn");
		}
		_endTurnPanels.get(i).add(mB);
		_mainPanels.get(i).add(_endTurnPanels.get(i));
	}

	/**
	 * This is the popup that occurs if the player attempts to place a tile not on the homesquare
	 * adds action listener for it and sets text
	 */
	public void drawHomeSquarePopUp(){
		JDialog popup = new JDialog();
		JLabel 	text  = new JLabel("You must place the first tile on the homesquare (*)");
		JButton button = new JButton("Ok");
		EventHandlerHomeSquareOk actionListner = new EventHandlerHomeSquareOk(popup);
		button.addActionListener(actionListner);
		popup.setLayout(new GridLayout(2,1));
		popup.add(text);
		popup.add(button);
		popup.pack();
		popup.setVisible(true);
	}

	/**
	 * 
	 */
/*	public void drawScoreBoardPopUp(){
		JDialog scoreBoard = new JDialog();
		scoreBoard.setLayout(new GridLayout(_game.getPlayers().size() + 1,0));
		JButton okButton	= new JButton("Ok");
		EventHandlerHomeSquareOk actionListener = new EventHandlerHomeSquareOk(scoreBoard);
		okButton.addActionListener(actionListener);
		for(int j = 0; j < _game.getPlayers().size(); j++){			
			JLabel iLabel = new JLabel("Player: " +_game.getPlayers().get(j).getName() +" Score: " +_game.getPlayers().get(j).getScore());
			scoreBoard.add(iLabel);
		}
		scoreBoard.add(okButton);
		scoreBoard.pack();
		scoreBoard.setVisible(true);
	}*/
	
	/**
	 * This is displayed whenever the menu button is clicked
	 * This is also displayed at the end of the game
	 * Declares the winner or whoever is in the lead
	 * Enables players to return, play again or quit the game.
	 */
	public void drawEndGamePopUp(){
		Player winner = new Player("Temp");
		JDialog scoreBoard = new JDialog();
		scoreBoard.setLayout(new GridLayout(_game.getPlayers().size() + 4,0));
		JButton okButton		= new JButton("Return to game");
		JButton replayButton	= new JButton("Play Again?");
		JButton exitButton = new JButton("Quit Game");
		EventHandlerExitButton exitListener = new EventHandlerExitButton(this);
		EventHandlerReplayButton replayListener = new EventHandlerReplayButton(this);
		EventHandlerHomeSquareOk okListener = new EventHandlerHomeSquareOk(scoreBoard);
		exitButton.addActionListener(exitListener);
		replayButton.addActionListener(replayListener);
		okButton.addActionListener(okListener);
		
		for(int i = 0; i < _game.getPlayers().size(); i++){			
			if(_game.getPlayers().get(i).getScore() > winner.getScore()) winner = _game.getPlayers().get(i);
		}
		
		JLabel winnerLabel = new JLabel("The leader is: " +winner.getName() +"!");
		scoreBoard.add(winnerLabel);
		if(winner.getName().equals("Temp"))winnerLabel.setText("There is a tie!");
		for(int j = 0; j < _game.getPlayers().size(); j++){			
			JLabel iLabel = new JLabel("Player: " +_game.getPlayers().get(j).getName() +" Score: " +_game.getPlayers().get(j).getScore());
			scoreBoard.add(iLabel);
		}
		scoreBoard.add(okButton);
		scoreBoard.add(replayButton);
		scoreBoard.add(exitButton);
		scoreBoard.pack();
		scoreBoard.setVisible(true);
	}
			
	
	/**
	 * Exits the game cleanly.
	 */
	public void exitGame(){
		System.exit(0);
	}
	
	/**
	 * Restarts the game.
	 */
	public void replay(){
		for(int i = 0; i < _game.getPlayers().size(); i++){
			_frames.get(i).setVisible(false);
			_frames.get(i).removeAll();
		}
		run();
	}
	
}
