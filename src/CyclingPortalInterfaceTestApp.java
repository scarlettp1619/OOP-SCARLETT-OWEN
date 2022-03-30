import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import cycling.CyclingPortal;
import cycling.MiniCyclingPortal;
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
import cycling.SegmentType;
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

		CyclingPortalInterface portal = new CyclingPortal();
		LocalDateTime testTime = LocalDateTime.of(0, 1, 1, 0, 0);
		
		portal.createRace("Race1", "epicrace");
		System.out.println("Race IDS: " + Arrays.toString(portal.getRaceIds()));
		System.out.println("Race details of 0 " + portal.viewRaceDetails(0));
		
		portal.removeRaceById(0);
		System.out.println("Race IDS: " + Arrays.toString(portal.getRaceIds()));
		
		portal.createRace("Race2", "epicrace");
		portal.createRace("Race3", "epic");
		
		System.out.println("Race details of 1 " + portal.viewRaceDetails(1));
		System.out.println("Race details of 2 " + portal.viewRaceDetails(2));
		
		portal.addStageToRace(1, "Stage1Race1", "sdesc1", 5, testTime, StageType.FLAT);
		portal.addStageToRace(1, "Stage1Race2", "sdesc2", 5, testTime, StageType.HIGH_MOUNTAIN);
		portal.addStageToRace(1, "Stage1Race3", "sdesc3", 5, testTime, StageType.MEDIUM_MOUNTAIN);
		portal.addStageToRace(1, "Stage1Race4", "sdesc4", 5, testTime, StageType.TT);
		
		portal.addStageToRace(2, "Stage1Race5", "sdesc5", 5, testTime, StageType.FLAT);
		portal.addStageToRace(2, "Stage1Race6", "sdesc6", 5, testTime, StageType.HIGH_MOUNTAIN);
		portal.addStageToRace(2, "Stage1Race7", "sdesc7", 5, testTime, StageType.MEDIUM_MOUNTAIN);
		portal.addStageToRace(2, "Stage1Race8", "sdesc8", 5, testTime, StageType.TT);
		
		System.out.println("number of stages in 1 " + portal.getNumberOfStages(1));
		System.out.println("number of stages in 2 " + portal.getNumberOfStages(2));
		
		System.out.println("stages in 1 " + Arrays.toString(portal.getRaceStages(1)));
		System.out.println("stages in 2 " + Arrays.toString(portal.getRaceStages(2)));
		
		System.out.println("stage length of 1 " + portal.getStageLength(1));
		System.out.println("stage length of 7 " + portal.getStageLength(7));
		
		portal.removeStageById(0);
		
		System.out.println("number of stages in 1 " + portal.getNumberOfStages(1));
		System.out.println("stages in 1 " + Arrays.toString(portal.getRaceStages(1)));
		
		portal.addCategorizedClimbToStage(1, 2.0, SegmentType.C1, 2.0, 5.0);
		portal.addIntermediateSprintToStage(1, 3.0);
		
		System.out.println("segment ids " +Arrays.toString(portal.getStageSegments(1)));
		
		portal.removeSegment(1);
		System.out.println("segment ids " +Arrays.toString(portal.getStageSegments(1)));
		
		System.out.println(Arrays.toString(portal.getRaceStages(2)));
		portal.concludeStagePreparation(5);
		//
		
		portal.createTeam("teamepic", "the epic team");
		portal.createTeam("teamepic2", "the epic team (two)");
		portal.createTeam("teamepic3", "the epic team (three)");
		System.out.println("team IDs " + Arrays.toString(portal.getTeams()));
		
		portal.removeTeam(1);
		System.out.println("team IDs " + Arrays.toString(portal.getTeams()));
		
		portal.createRider(0, "jeff", 1984);
		portal.createRider(0, "jeff", 1984);
		portal.createRider(0, "bob", 1993);
		portal.createRider(2, "jeff3", 1986);
		System.out.println(Arrays.toString(portal.getTeamRiders(0)));
		System.out.println(Arrays.toString(portal.getTeamRiders(2)));
		
		// IDNotRecognisedException not working properly here
		// fuck it i'm gonna stop with these comments none of the idnotrecognised works
		
		portal.removeRider(0);
		portal.removeRider(3);
		System.out.println(Arrays.toString(portal.getTeamRiders(2)));
		
		
		LocalTime regTime0 = LocalTime.of(0, 5, 0, 0);
		LocalTime regTime1 = LocalTime.of(0, 10, 0, 0);
		LocalTime regTime2 = LocalTime.of(0, 15, 0, 0);
		
		LocalTime regTime3 = LocalTime.of(0, 5, 0, 0);
		LocalTime regTime4 = LocalTime.of(0, 11, 0, 0);
		LocalTime regTime5 = LocalTime.of(0, 13, 0, 0);
		
		System.out.println("about to register results for rider 1 in stage");
		portal.registerRiderResultsInStage(1, 1, regTime0, regTime1, regTime2);
		System.out.println(Arrays.toString(portal.getRiderResultsInStage(1, 1)));
		System.out.println(portal.getRiderAdjustedElapsedTimeInStage(1, 1));
		
		portal.deleteRiderResultsInStage(1, 1);
		System.out.println(Arrays.toString(portal.getRiderResultsInStage(1, 1)));
		
		System.out.println("about to register results for rider 1 in stage");
		portal.registerRiderResultsInStage(1, 1, regTime0, regTime1, regTime2);
		System.out.println("registered rider results for rider 1 in stage");
		portal.registerRiderResultsInStage(1, 1, regTime3, regTime4, regTime5);
		System.out.println(Arrays.toString(portal.getRiderResultsInStage(1, 1)));
		System.out.println("registered rider results for rider 2 in stage");
		portal.registerRiderResultsInStage(1, 2, regTime3, regTime4, regTime5);
		
		System.out.println(Arrays.toString(portal.getRidersRankInStage(1)));
		System.out.println(Arrays.toString(portal.getRankedAdjustedElapsedTimesInStage(1)));
		
		System.out.println(Arrays.toString(portal.getRidersPointsInStage(1)));
		
		 System.out.println(Arrays.toString(portal.getRidersMountainPointsInStage(1)));
		// Exception in thread "main" java.lang.ClassCastException: class [Ljava.lang.Object; cannot be cast
		// to class [Lcycling.Segment; ([Ljava.lang.Object; is in module java.base of loader 'bootstrap';
		// [Lcycling.Segment; is in unnamed module of loader 'app')
		
		//at cycling.Stage.getSegmentsAsArray(Stage.java:104)
		// at cycling.BadCyclingPortal.getRidersMountainPointsInStage(BadCyclingPortal.java:506)
		// at CyclingPortalInterfaceTestApp.main(CyclingPortalInterfaceTestApp.java:151)
		
		// Basically, getSegmentsAsArray() is broken.
		
		// I already know eraseCyclingPortal, save, load and removeRaceByName work because I already tested
		// them earlier on.
		
		// System.out.println(portal.getGeneralClassificationTimesInRace(1));
		// Exception in thread "main" java.lang.NullPointerException: Cannot read the array length because "timeArray" is null.
		
		// System.out.println(portal.getRidersPointsInRace(1));
		// Same exception with timeArray being null.
		
		// System.out.println(portal.getRidersMountainPointsInRace(1));
		// Same exception with timeArray being null.
		
		// System.out.println(portal.getRidersGeneralClassificationRank(1));
		// Same exception with timeArray being null.
	
		
		
		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

	}
	
	static void createTestSave(String saveName) {
		CyclingPortalInterface portal = new CyclingPortal();
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
