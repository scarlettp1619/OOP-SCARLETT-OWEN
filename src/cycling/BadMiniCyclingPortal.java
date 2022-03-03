package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * BadMiniCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the MiniCyclingPortalInterface interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class BadMiniCyclingPortal implements MiniCyclingPortalInterface {
	
	HashMap<Integer, Race> races = new HashMap<Integer, Race>();
	int raceIdCounter = 0;
	
	HashMap<Integer, Team> teams = new HashMap<Integer, Team>();
	int teamIdCounter = 0;
	
	//FUCK WE NEED HASHMAPS TO MAP IDS TO OBJECTS FOR EASY SEARCHING SO WE DONT NEED BGI RUNTIME COMPLEXITIES

	@Override
	public int[] getRaceIds() {
		int[] raceIds = new int[races.size()];
		int counter = 0;
		for (int i : races.keySet()){
			raceIds[counter] = i;
			counter++;
		}
		return raceIds;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		Race tempRace = new Race(name, description);
		races.put(raceIdCounter, tempRace);
		raceIdCounter++;
		return raceIdCounter-1;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		Race tempRace = races.get(raceId);
		return tempRace.getDescription();
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		races.remove(raceId);
		raceIdCounter--;
		/* Loop through races[] to find the ID of the race that needs to be removed, then remove it
   		 * Remove the results from the race along with it (further elaboration)
		 * Results are stored with the riders
		 * addResults() -----
		 */ 
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// loop to search race from raceID, then a simple get method from Race[] class
		return 0;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		Race tempRace = races.get(raceId);
		int returnStageId = tempRace.addStage(stageName, description, length, startTime, type);
		races.put(raceId, tempRace);
		return returnStageId;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		Race tempRace = races.get(raceId);
		HashMap<Integer, Stage> stages = tempRace.getStages();
		int[] stageIds = new int[stages.size()];
		for (int i : stages.keySet()) {
			stageIds[i] = i;
		}
		return stageIds;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// loop through races and then try to get stageID with the hashmap object in race object, then return the length (km)
		for (Race r : races.values()) {
			HashMap<Integer, Stage> sHash = r.getStages();
			Stage stage = sHash.get(stageId);
			//incomplete
		}
		return 0;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		/* same search as method above
		 * result removal (elaboration needed)
		 */

	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		// add climb segment with constructor method to stages, use hashmap bs yadda yadda
		return 0;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		// add intermediate sprint segment with constructor method to stages, same thing as climb segment
		return 0;
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		// same as remove stages method but again more loops and hashmaps

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
