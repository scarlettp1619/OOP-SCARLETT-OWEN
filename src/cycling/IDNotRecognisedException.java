package cycling;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Thrown when attempting to use an ID that does not exit in the system.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class IDNotRecognisedException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public IDNotRecognisedException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public IDNotRecognisedException(String message) {
		super(message);
	}
	
	static void checkID(int Ids, int[] arrayOfIds) throws IDNotRecognisedException {
		boolean containsID = IntStream.of(arrayOfIds).anyMatch(x -> x == Ids);
		for (int i = 0; i <= arrayOfIds.length - 1; i++) {
			if (!containsID) {
				throw new IDNotRecognisedException("ID \"" + Ids + "\" does not match any IDs!");
			}
		}
	}

}
