package code;

import code.DataModel;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
//import javax.swing.Timer;

/**
 * @author Jason Hatfield
 *Drives the graphical component of the game.  Run as a threaded process, and implementing 
 *a MouseListener to allow checking for a match on the click of the mouse.  Utilizing the listener 
 *classes, the string representations of the image, name and description are passed back into
 *the GUI and checked against each person looking for a match.  When a match is found the 
 *selected items are removed and the _totalMatches variable is updated to represent the current
 *number of matches that have been confirmed.  Once _totalMatches reaches the number of people
 *in the game, it then ends.
 */
public class GraphicalUserInterface implements Runnable, MouseListener {

	private JFrame _mainFrame;				//main container for each item added to the game
	private DataModel _dm;					//represents the datamodel of the game, passed in via the lab8 class
	private String _selectedImageString;	//String rep. of the selected image
	private String _selectedName;			//String rep. of the selected name (button)
	private JPanel _iconFrame;				//JPanel holding the ImageIcons 
	private JPanel _nameFrame;				//JPanel holding the name buttons
	private JPanel _descriptionFrame;		//JPanel holding the description JTextAreas
	private JLabel _selectedImageLabel;		//JLabel holding the selected image, used for removal
	private JButton _selectedButton;		//Selected JButton
	private String _selectedDescription;	//Selected description
	private JPanel _selectedDescriptionPanel;//JPanel holding the selected description
	private int _totalMatches;				//Tracks the number of confirmed and removed matches
	
	/**
	 * @param dm - datamodel of the game
	 * Basic constructor, instantiates the instance variables.
	 */
	public GraphicalUserInterface(DataModel dm){
		
		_dm = dm;
		_selectedImageString = "";
		_selectedImageLabel = new JLabel();
		_selectedButton = new JButton();
		_selectedDescription = "";
		_selectedDescriptionPanel = new JPanel();
		_totalMatches = 0;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * Threaded process that builds the GUI, cycles through the datamodel and adds the randomized 
	 * lists to containers, then to the main frame which is packed.  
	 */
	@Override
	public void run() {

		_mainFrame = new JFrame("Jason's Matching Game");
		_mainFrame.getContentPane().setLayout(new GridLayout(0,3));
		
		_iconFrame = new JPanel();							//Holds the images
		_iconFrame.setLayout(new GridLayout(0,1));
		_nameFrame = new JPanel();							//Holds the name JButtons
		_nameFrame.setLayout(new GridLayout(0,1));
		_descriptionFrame = new JPanel();					//Holds the desc. JTextAreas
		_descriptionFrame.setLayout(new GridLayout(0,1));
		
		_mainFrame.add(_iconFrame);
		_mainFrame.add(_nameFrame);
		_mainFrame.add(_descriptionFrame);
		
		//Cycle through datamodel and add data to respective containers
		for(int i = 0; i < _dm.size(); i++){
			
			//Get image, name, and description
			ImageIcon img = _dm.getImage(i);
			String n = _dm.getName(i);
			String d = _dm.getDescription(i);
			
			//add image
			JLabel iconLabel = new JLabel(img);
			iconLabel.addMouseListener(new ImageListener(iconLabel, this));
			_iconFrame.add(iconLabel);
			
			//add name
			JButton name = new JButton(n);
			name.addActionListener(new NameListener(name, this));
			name.addMouseListener(this);
			_nameFrame.add(name);
			
			//add description
			JTextArea t = new JTextArea(d);
			t.setColumns(20);
			t.setWrapStyleWord(true);
			t.setLineWrap(true);
			JPanel panel = new JPanel();
			t.addMouseListener(new DescriptionListener(t, this));
			t.addMouseListener(this);
			panel.add(t);
			_descriptionFrame.add(panel);
			
		}
				
		//main frame standard configuration
		_mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_mainFrame.pack();
		_mainFrame.setVisible(true);
		
	}

	/**
	 * @param s - sets the string representation of the currently selected image
	 */ 
	public void setImageString(String s){
		
		_selectedImageString = s;
		
	}
	
	/**
	 * @param s - sets the string representation of the currently selected name
	 */
	public void setNameString(String s){
		
		_selectedName = s;
		
	}
	
	/**
	 * @param icon - sets the currently selected JLabel from the selected image
	 */
	public void setImageLabel(JLabel icon){
		
		_selectedImageLabel = icon;
		
	}
	
	/**
	 * @param name - sets the currently selected name JButton
	 */
	public void setNameButton(JButton name){
		
		_selectedButton = name;
		
	}
	
	/**
	 * @param s - sets the string representation of the currently selected description
	 */
	public void setDescriptionString(String s){
		
		_selectedDescription = s;
		
	}

	/**
	 * @param area - sets the JTextArea of the currently selected description
	 */
	public void setDescriptionPanel(JTextArea area){
		
		_selectedDescriptionPanel = (JPanel) area.getParent();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * On mouse click, the currently selected image, name, and description are checked across
	 * all persons for a match, if found then items are removed, _totalMatches is incremented, 
	 * and the frame is redrawn and packed.  Once all matches are found, game is exited.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
			
		for(int i = 0; i < _dm.size(); i++){
			
			Person p = _dm.getPerson(i);
			ImageIcon ii = p.getImage(); 
			String iconString = ii.toString();
			String n = p.getName();
			String d = p.getDescription();
			
			//check for match
			if(_selectedImageString.equals(iconString) && _selectedName.equals(n) && _selectedDescription.equals(d)){
				
				System.out.println("Good Choice");
				_iconFrame.remove(_selectedImageLabel);
				_nameFrame.remove(_selectedButton);
				_descriptionFrame.remove(_selectedDescriptionPanel);
				_mainFrame.pack();
				_mainFrame.repaint();
				_totalMatches++;
				
			}
			
			//Check for potential remaining matches
			if(_totalMatches == _dm.size()){

				System.exit(0);
				
			}
			
		}
				
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//  Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//  Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//  Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated method stub
		
	}
}
