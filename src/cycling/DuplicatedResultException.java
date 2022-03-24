package cycling;

import java.time.LocalTime;
import java.util.Arrays;

/**
 * Each rider can only have a single result in a race. This exception is thrown
 * when attempting to create another record for the same rider in a the same
 * registered race.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class DuplicatedResultException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public DuplicatedResultException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public DuplicatedResultException(String message) {
		//rider can only have one result per race
		super(message);
	}
	
	static void checkResults(LocalTime[] riderTime) throws DuplicatedResultException {
		if (riderTime != null) {
			throw new DuplicatedResultException("Rider already has results in stage!"); // this isn't done
		}
	}

}
