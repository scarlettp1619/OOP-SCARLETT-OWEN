package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;

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
	static int raceIdCounter = 0;
	
	HashMap<Integer, Team> teams = new HashMap<Integer, Team>();
	static int teamIdCounter = 0;

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
		try {
			IllegalNameException.checkRaceName(name, getRaceIds(), races);
			InvalidNameException.checkName("Race", name);
			Race tempRace = new Race(name, description);
			races.put(raceIdCounter, tempRace);
			raceIdCounter++;
		} catch (IllegalNameException | InvalidNameException e) {
			System.out.println(e);
		}
		return raceIdCounter-1;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		String details = null;
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			Race tempRace = races.get(raceId);
			details = tempRace.getDescription();
		} catch (IDNotRecognisedException e) {
			System.out.println(e);
		}
		return details;
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			races.remove(raceId);
			/* Remove race ID from hash map, decrement ID counter
	   		 * Remove the results from the race along with it (further elaboration)
			 * Results are stored with the riders
			 * addResults() ----- */
		} catch (IDNotRecognisedException e) {
			System.out.println(e);
		} 
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		int stages = -1;
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			Race tempRace = races.get(raceId);
			stages = tempRace.getStageIds().length;
		} catch (IDNotRecognisedException e) {
			System.out.println(e);
		}
		return stages;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		int returnStageId = -1;
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			IllegalNameException.checkStageName(stageName, raceId, races);
			InvalidNameException.checkName("Stage", stageName);
			InvalidLengthException.checkLength(length);
			Race tempRace = races.get(raceId);
			returnStageId = tempRace.addStage(stageName, description, length, startTime, type);
			races.put(raceId, tempRace);
		} catch (IDNotRecognisedException | IllegalNameException | InvalidNameException | InvalidLengthException e){
			System.out.println(e);
		}
		return returnStageId;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		int[] stageIds = {};
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			Race tempRace = races.get(raceId);
			stageIds = tempRace.getStageIds();
		} catch(IDNotRecognisedException e) {
			System.out.println(e);
		}
		return stageIds;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		double stageLength = 0;
		for (Race r : races.values()) {
			try {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				HashMap<Integer, Stage> stages = r.getStages();
				Stage tempStage = stages.get(stageId);
				stageLength = tempStage.getStageLength();
			} catch (IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
		return stageLength;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		for (Race r : races.values()) {
			try {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				r.removeStage(stageId);
			} catch (IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		int segmentId = 0;
		for (Race r : races.values()) {
			try {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				Stage tempStage = r.getStage(stageId);
				segmentId = tempStage.addSegment(location, type, averageGradient, length);
				r.stages.put(stageId, tempStage);
			} catch (IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
		return segmentId;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		int segmentId = 0;
		for (Race r : races.values()) {
			try {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				Stage tempStage = r.getStage(stageId);
				segmentId = tempStage.addSprint(location);
				r.stages.put(stageId, tempStage);
			} catch (IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
		return segmentId;
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		for (Race r : races.values()) {
			for (Stage s : r.getStages().values()) {
				s.removeSegment(segmentId);
			}
		}
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		for (Race r : races.values()) {
			Stage tempStage = r.getStage(stageId);
			tempStage.setStageState(StageState.WAITING_FOR_RESULTS);
			r.stages.put(stageId, tempStage);
		}
	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		int[] segIds = null;
		for (Race r : races.values()) {
			Stage tempStage = r.getStage(stageId);
			segIds = tempStage.getSegmentIds();
		}
		return segIds;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		Team tempTeam = new Team(name, description);
		teams.put(teamIdCounter, tempTeam);
		teamIdCounter++;
		return teamIdCounter-1;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		try {
			IDNotRecognisedException.checkID(teamId, getTeams());
			teams.remove(teamId);
		} catch (IDNotRecognisedException e){
			System.out.println(e);
		}

	}

	@Override
	public int[] getTeams() {
		int[] teamIds = new int[teams.size()];
		int counter = 0;
		for (int i : teams.keySet()){
			teamIds[counter] = i;
			counter++;
		}
		return teamIds;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		int[] riderIds = {};
		try {
			IDNotRecognisedException.checkID(teamId, getTeams());
			Team tempTeam = teams.get(teamId);
			riderIds = tempTeam.getRidersId();
		} catch (IDNotRecognisedException e) {
			System.out.println(e);
		}
		return riderIds;
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		int returnTeamId = 0;
		try {
			IDNotRecognisedException.checkID(teamID, getTeams());
			Team tempTeam = teams.get(teamID);
			returnTeamId = tempTeam.addRider(name, yearOfBirth);
			teams.put(teamID, tempTeam);
		} catch(IDNotRecognisedException | IllegalArgumentException e) {
			System.out.println(e);
		}
		return returnTeamId;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		for (Team t : teams.values()) {
			try {
				IDNotRecognisedException.checkID(riderId, t.getRidersId());
				t.removeRiders(riderId);
			} catch (IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		LocalTime totalTime = LocalTime.of(0, 0, 0, 0);
		/*for (Race r : races.values()) {
			for (int segmentIds : r.getStages().keySet()) {
				//WIp
				
			}
		}*/
		for (LocalTime time : checkpoints) {
			totalTime = totalTime.plusHours(time.getHour()).plusMinutes(time.getMinute()).plusSeconds(time.getSecond()).plusNanos(time.getNano());
		}
		for (Team t : teams.values()) {
			Rider rider = t.getRider(riderId);
			rider.setStageTime(stageId, totalTime);
		}
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
