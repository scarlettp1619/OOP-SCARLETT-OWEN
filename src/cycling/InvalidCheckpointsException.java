package cycling;

import java.time.LocalTime;
import java.util.HashMap;

/**
 * Each race result should contains the times for each segment (mountain and
 * sprint) within a stage, plus the start time and the finish time. The list of checkpoints must
 * follow its chronological sequence, i.e., checkpoint_i {@literal <=} checkpoint_i+1.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class InvalidCheckpointsException extends Exception {

	/**
	 * Constructs an instance of the exception with no message
	 */
	public InvalidCheckpointsException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public InvalidCheckpointsException(String message) {
		// thrown if length of checkpoints is not equal to n + 2, where n is number of segments in stage 
		super(message);
	}
	
	static void checkLength(Stage stage, LocalTime... checkpoints) throws InvalidCheckpointsException{
		int segments = stage.getSegmentIds().length + 2;
		// get segment length + 2 (start & finish)
		int check = checkpoints.length;
		// get number of checkpoints
		if (segments != check) {
			// if checkpoints don't match segments
			throw new InvalidCheckpointsException("Length of checkpoints is invalid!");
			// throw exception
		}
	}

}
