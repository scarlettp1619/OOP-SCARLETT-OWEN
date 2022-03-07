import java.time.LocalDateTime;
import java.util.Arrays;

import cycling.BadCyclingPortal;
import cycling.BadMiniCyclingPortal;
import cycling.CyclingPortalInterface;
import cycling.DuplicatedResultException;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidCheckpointsException;
import cycling.InvalidLengthException;
import cycling.InvalidLocationException;
import cycling.InvalidNameException;
import cycling.InvalidStageStateException;
import cycling.InvalidStageTypeException;
import cycling.MiniCyclingPortalInterface;
import cycling.NameNotRecognisedException;
import cycling.StageType;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {
	/**
	 * Test method.
	 * 
	 * @param args not used
	 * @throws InvalidNameException 
	 * @throws IllegalNameException 
	 * @throws IDNotRecognisedException 
	 * @throws InvalidLengthException 
	 * @throws InvalidStageTypeException 
	 * @throws InvalidStageStateException 
	 * @throws InvalidLocationException 
	 * @throws InvalidCheckpointsException 
	 * @throws DuplicatedResultException 
	 * @throws NameNotRecognisedException 
	 */
	public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLengthException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException, DuplicatedResultException, InvalidCheckpointsException, NameNotRecognisedException {
		System.out.println("The system compiled and started the execution...");

		CyclingPortalInterface portal = new BadCyclingPortal();
		//CyclingPortalInterface portal = new BadCyclingPortal();
		LocalDateTime testTime = LocalDateTime.now();
		
		
	
		// portal.createRace("name of whatever", "des"); // invalid name
		portal.createRace("nameofwhatever2", "description of race 0"); // valid name
		//portal.removeRaceById(100);
		//portal.addStageToRace(0, "stagename0", "stagedes", 5, testTime, StageType.FLAT);
		//portal.addStageToRace(0, "stagename1", "stagedes", 5, testTime, StageType.FLAT);
		//String details = portal.viewRaceDetails(0);
		//int stages = portal.getNumberOfStages(1);
		//portal.removeStageById(1);
		//portal.addIntermediateSprintToStage(0, 10);
		//portal.addIntermediateSprintToStage(0, 10);
		//portal.addIntermediateSprintToStage(1, 10);
		//portal.registerRiderResultsInStage(0, 0);
		//portal.removeSegment(1);
		portal.removeRaceByName("nameofwhatdever2");
		//System.out.println(Arrays.toString(portal.getStageSegments(1)));
		
		
		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

	}

}
