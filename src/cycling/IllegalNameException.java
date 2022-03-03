package cycling;

import java.util.HashMap;

/**
 * Thrown when attempting to assign a race name already in use in the
 * system.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class IllegalNameException extends Exception {
	
	static String checkName;
	
	/**
	 * Constructs an instance of the exception with no message
	 */
	public IllegalNameException() {
		// do nothing

	}

	/**
	 * Constructs an instance of the exception containing the message argument
	 * 
	 * @param message message containing details regarding the exception cause
	 */
	public IllegalNameException(String message) {
		super(message);
	}
	
	
	static void checkRaceName(String name, int[] raceIds, HashMap<Integer, Race> races) throws IllegalNameException {
		for (int i = 0; i <= raceIds.length - 1; i++) {
			int raceId = raceIds[i];
			Race tempRace = races.get(raceId);
			checkName = tempRace.getName();
			if (name == checkName) {
				throw new IllegalNameException("Race name \"" + name + "\" already exists!");
			}
		}
	}
	
	static void checkStageName(String name, int raceId, HashMap<Integer, Race> races) throws IllegalNameException {
		Race tempRace = races.get(raceId);
		HashMap<Integer, Stage> stages = tempRace.getStages();
		int[] stagesIds = tempRace.getStageIds();
		for (int i = 0; i <= stagesIds.length - 1; i++) {
			Stage tempStage = stages.get(stagesIds[i]);
			String stageName = tempStage.getStageName();
			if (name == stageName) {
				throw new IllegalNameException("Stage name \"" + name + "\" already exists!");
			}
		}
	}

}
