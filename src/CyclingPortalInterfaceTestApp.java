import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
		System.out.println("The system compiled and started the execution... \n");

		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
		//CyclingPortalInterface portal = new BadCyclingPortal();
		LocalDateTime testTime = LocalDateTime.now();
		
		//createTestSave("save");
		
		
		try {
			portal.loadCyclingPortal("save");
		} catch (ClassNotFoundException | IOException e) {
		}
		
		System.out.println(Arrays.toString(portal.getRaceStages(0)));
		System.out.println(Arrays.toString(portal.getRiderResultsInStage(0, 0)));
		System.out.println(Arrays.toString(portal.getRidersRankInStage(0)));
		System.out.println(Arrays.toString(portal.getRankedAdjustedElapsedTimesInStage(0)));
		
		
		// portal.createRace("name of whatever", "des"); // invalid name
		//portal.createRace("nameofwhatever2", "description of race 0"); // valid name
		//portal.removeRaceById(100);
		//portal.addStageToRace(0, "stagename0", "stagedes", 5, testTime, StageType.FLAT);
		//portal.addStageToRace(0, "stagename0", "stagedes", 5, testTime, StageType.FLAT);
		//portal.addStageToRace(0, "stagename1", "stagedes", 5, testTime, StageType.FLAT);
		//String details = portal.viewRaceDetails(0);
		//int stages = portal.getNumberOfStages(1);
		//portal.removeStageById(1);
		//portal.addIntermediateSprintToStage(1, 10);
		//portal.registerRiderResultsInStage(0, 0);
		//portal.removeSegment(1);
		//System.out.println(Arrays.toString(portal.getStageSegments(1)));
		//portal.removeRaceByName("sdf");
		//System.out.println(Arrays.toString(portal.getRaceIds()));
		
		
		
		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

	}
	
	static void createTestSave(String saveName) {
		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
		LocalDateTime testTime = LocalDateTime.of(0, 1, 1, 0, 0);
		LocalTime regTime = LocalTime.of(1, 1, 1, 1);
		LocalTime regTime2 = LocalTime.of(2, 2, 2, 2);
		LocalTime regTime3 = LocalTime.of(3, 3, 3, 3);
		try{
			portal.createRace("Race1", "Desc 1");
			portal.addStageToRace(0, "Stage1", "sDesc 1", 5, testTime, StageType.FLAT);
			portal.addStageToRace(0, "Stage2", "sDesc 2", 5, testTime, StageType.FLAT);
			portal.addIntermediateSprintToStage(0, 2);
			portal.addIntermediateSprintToStage(0, 3);
			portal.createTeam("Team1", "tDesc 1");
			portal.createTeam("Team2", "tDesc 2");
			portal.createRider(0, "Rider1", 2003);
			portal.createRider(0, "Rider2", 2003);
			portal.createRider(0, "Rider3", 2003);
			portal.registerRiderResultsInStage(0, 1, regTime, regTime);
			portal.registerRiderResultsInStage(0, 0, regTime2, regTime2);
			portal.registerRiderResultsInStage(0, 2, regTime3, regTime3);
			portal.saveCyclingPortal(saveName);
		}catch(InvalidNameException | IllegalNameException | IDNotRecognisedException | InvalidLengthException | DuplicatedResultException | InvalidCheckpointsException | InvalidStageStateException | InvalidLocationException | InvalidStageTypeException | IOException e) {
			System.out.println(e);
		}

	}

}
