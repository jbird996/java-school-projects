package code;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;


/**
 * @author Jason Hatfield
 *The datamodel provides a series of arraylists to store each person, image, name, and description.
 *Each list except the person list is shuffled for ease of randomizing the matching board.  Get methods
 *included as well.
 */
public class DataModel {
	
	private ArrayList<Person> _people;
	private ArrayList<ImageIcon> _images;
	private ArrayList<String> _names;
	private ArrayList<String> _descriptions;
	
	
	/**
	 * @param folder - string which specifies the randomized folder to read.
	 * @param n - integer to signify the number of people that will be added to the board.
	 * Constructor which instantiates each arraylist, reads the data from file via the person
	 * class, and stores each piece of datum into the appropriate arraylist(s).  Lists except
	 * for the person list are then shuffled.
	 */
	public DataModel(String folder, int n){
	
		_people = new ArrayList<Person>();
		_images = new ArrayList<ImageIcon>();
		_names = new ArrayList<String>();
		_descriptions = new ArrayList<String>();
		
		for(int i = 0; i < n ; ++i){
			Person someone;
			someone = new Person(folder, "/file0" + i + ".game");
			_people.add(someone);
			_images.add(someone.getImage());
			_names.add(someone.getName());
			_descriptions.add(someone.getDescription());
			
		}
		
		Collections.shuffle(_images);
		Collections.shuffle(_names);
		Collections.shuffle(_descriptions);
		
	}
	
	/**
	 * @param p - index of desired person.
	 * @return - person object represented by the index.
	 * Simple getter method.
	 */
	public Person getPerson(int p){
				
		return _people.get(p);
				
	}
	
	/**
	 * @param i - index of desired image.
	 * @return - ImageIcon of desired index.
	 * Simple getter method
	 */
	public ImageIcon getImage(int i){
		
		return _images.get(i);
		
	}
	
	/**
	 * @param n - index of desired name.
	 * @return - String name of desired index.
	 * Simple getter method.
	 */
	public String getName(int n){
		
		return _names.get(n);
	}
	
	/**
	 * @param d - index of desired description.
	 * @return - String description of desired index.
	 * Simple getter method.
	 */
	public String getDescription(int d){
		
		return _descriptions.get(d);
		
	}
	/**
	 * @return - integer indicating the number of people in the game.
	 */
	public int size(){
		
		return _people.size();
		
	}
	
}
