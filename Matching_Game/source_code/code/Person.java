package code;

import javax.swing.ImageIcon;
import support_code.FileScanner;

/**
 * @author Jason Hatfield
 *Uses pre-defined filescanner class to read person information and store as a person in each instance.  
 *Datamodel calls this class to populate it's arraylists 
 */
public class Person {

	private ImageIcon _image;
	private String _name;
	private String _description;
	
	public Person(String folder, String file){
		
		FileScanner fs = new FileScanner(folder+"/"+file);
		_image = new ImageIcon(folder + "/" + fs.nextLine());
		_name = fs.nextLine();
		_description = fs.nextLine(); 
		
	}
	
	public ImageIcon getImage(){
		
		return _image;
		
	}
	
	public String getName(){
		
		return _name;
		
	}
	
	public String getDescription(){
		
		return _description;
		
	}
			
}
