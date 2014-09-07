package code;

/**
 * @author Jason
 *This is the main driver class of the matching game.  A new datamodel is created as well as
 *instantiating the GUI as a threaded process. 
 */
public class Lab8 {

	//Constructor which takes a string and an integer
	public Lab8(String folder, int n) {
		DataModel dm = new DataModel(folder, n);
		javax.swing.SwingUtilities.invokeLater(new GraphicalUserInterface(dm));
	}

	/*Main method, uses a random number to determine if the game is created using the "uta"
	folder or the "fac" folder.  This also ensures that the proper number of people are
	accounted for in each case.*/
	public static void main(String[] args) {
		if (Math.random() < 0.5) {
			new Lab8("uta", 8);
		}
		else {
			new Lab8("fac", 6);
		}
	}

}
