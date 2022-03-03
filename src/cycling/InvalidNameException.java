package cycling;

/**
 * Thrown when attempting to assign a race name null, empty or having more than
 * the system limit of characters. A name must be a single word, i.e., no
 * white spaces allowed.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class InvalidNameException extends Exception {

	static String checkName;
	
	/**
	 * Constructs an instance of the exception with no message
	 */
	public InvalidNameException() {
		// do nothing
	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public InvalidNameException(String message) {
		super(message);
	}

	static void checkName(String type, String name) throws InvalidNameException {
		if (name == null || name.length() > 30 || name.isEmpty() || name.contains(" ")) {
				throw new InvalidNameException(type +  " name \"" + name + "\" is invalid!");
		}
	}
}
