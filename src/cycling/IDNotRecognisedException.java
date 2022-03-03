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
	
	static void checkID(int raceId, int[] raceIds) throws IDNotRecognisedException {
		boolean containsID = IntStream.of(raceIds).anyMatch(x -> x == raceId);
		for (int i = 0; i <= raceIds.length - 1; i++) {
			if (!containsID) {
				throw new IDNotRecognisedException("ID \"" + raceId + "\" does not match any race IDs!");
			}
		}
	}

}
