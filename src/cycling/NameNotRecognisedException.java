package cycling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Thrown when attempting to use a name that does not exit in the
 * system.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class NameNotRecognisedException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public NameNotRecognisedException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public NameNotRecognisedException(String message) {
		super(message);
	}
	
	static void checkName(String name, int[] raceIds, HashMap<Integer, Race> races) throws NameNotRecognisedException{
		ArrayList<String> raceNames = new ArrayList<String>();
		for (int i = 0; i <= raceIds.length - 1; i++) {
			Race tempRace = races.get(i);
			raceNames.add(tempRace.getName());
		}
		if (!raceNames.contains(name)) {
			throw new NameNotRecognisedException("Race name doesn't exist!"); 
		}
	}

}
