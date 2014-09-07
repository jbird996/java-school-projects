package support_code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileScanner {
	
	private Scanner _scanner;
	
	public FileScanner(String filename) {
		_scanner = null;
		try {
			_scanner = new Scanner(new File(filename));
		}
		catch (FileNotFoundException e) {
			System.err.println("ERROR while creating FileScanner - no such file: "+filename);
		}
	}
	
	public String nextLine() {
		if (isValid()) {
			return _scanner.nextLine();
		}
		return null;
	}

	public boolean isValid() {
		return _scanner != null;
	}

}
