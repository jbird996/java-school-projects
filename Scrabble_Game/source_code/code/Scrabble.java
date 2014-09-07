package code;

import java.util.ArrayList;

/**
 * @author Jason, Mike, Cole
 * Main driver of game, takes in args (player names, dictionary path) and passes along to game 
 * class for initialization
 *
 */
public class Scrabble {

	public static void main(String[] args){
		String path = args[0];
		ArrayList<String> al = new ArrayList<String>();
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-d"))path = args[i+1];
			if(args[i].equals("-p"))al.add(args[i+1]);
		}
		System.out.println(al);
		javax.swing.SwingUtilities.invokeLater(new GUI(path, al));
	}
}
