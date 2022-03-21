package cycling;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

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
	
	PointMaps pointMaps = new PointMaps();
	HashMap<Integer, Integer> flatFinishScores = pointMaps.getScorings(StageType.FLAT);
	HashMap<Integer, Integer> hillyFinishScores = pointMaps.getScorings(StageType.MEDIUM_MOUNTAIN);
	HashMap<Integer, Integer> highFinishScores = pointMaps.getScorings(StageType.HIGH_MOUNTAIN);

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
			for (Race r : races.values()) {
				IllegalNameException.checkName(name, r.getName());
			}
			InvalidNameException.checkName("Race", name);
			Race tempRace = new Race(name, description);
			races.put(raceIdCounter, tempRace);
			raceIdCounter++;
		} catch (InvalidNameException e) {
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
			InvalidNameException.checkName("Stage", stageName);
			InvalidLengthException.checkLength(length);
			Race tempRace = races.get(raceId);
			returnStageId = tempRace.addStage(stageName, description, length, startTime, type);
			races.put(raceId, tempRace);
		} catch (IDNotRecognisedException | InvalidNameException | InvalidLengthException e){
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
		for (Race r : races.values()) {
			Stage tempStage = r.getStage(stageId);
			tempStage.results.put(riderId, checkpoints);
			r.stages.put(stageId, tempStage);
		}
			/*for (int x : getStageSegments(stageId)) {
				
				for (Team t : teams.values()) {
					Rider rider = t.getRider(riderId);
					rider.addSegmentScore(stageId, x, time);
					t.riders.put(riderId, rider);
				}
			}*/
	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		LocalTime[] stageTimes = null;
		for (Race r : races.values()) {
			Stage tempStage = r.getStage(stageId);
			stageTimes = tempStage.getResults(riderId);
		}
		/*for (Team t : teams.values()) {
			Rider rider = t.getRider(riderId);
			stageTimes = rider.getSegmentScore(stageId);
		}*/
		return stageTimes;
	}
	
	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		LocalTime[] timeArray = getRiderResultsInStage(stageId, riderId);
		LocalTime elapsedTime = LocalTime.of(0, 0);
		for (LocalTime time : timeArray) {
			elapsedTime = elapsedTime.plusHours(time.getHour());
			elapsedTime = elapsedTime.plusSeconds(time.getSecond());
			elapsedTime = elapsedTime.plusMinutes(time.getMinute());
			elapsedTime = elapsedTime.plusNanos(time.getNano());
			//System.out.println(time);
		}
		return elapsedTime;
	}
	
	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		for (Race r : races.values()) {
			Stage tempStage = r.getStage(stageId);
			tempStage.removeResults(riderId);
			r.stages.put(stageId, tempStage);
		}
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		Stage tempStage = null;
		for(Race r : races.values()) {
			try {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				tempStage = r.getStage(stageId);	
			} catch(IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
		HashMap<Integer, LocalTime> tempMap = new HashMap<Integer, LocalTime>();
		for (int riderId : tempStage.getResultsHashMap().keySet()) {
			tempMap.put(riderId, getRiderAdjustedElapsedTimeInStage(stageId, riderId));
		}
		
		RankerComparator ranker = new RankerComparator(tempMap);
		TreeMap<Integer, LocalTime> sortedMap = new TreeMap<Integer, LocalTime>(ranker);
		
		sortedMap.putAll(tempMap);
		
		/*for(int i : sortedMap.keySet()) {
			System.out.print(i);
		}*/
		
		int[] rankedRiders = Arrays.stream(sortedMap.keySet().toArray(new Integer[sortedMap.size()])).mapToInt(i -> i).toArray();
		return rankedRiders;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		int[] rankedRiders = getRidersRankInStage(stageId);
		LocalTime[] rankedTimes = new LocalTime[rankedRiders.length];
		int i = 0;
		for(int riderId : rankedRiders) {
			rankedTimes[i] = getRiderAdjustedElapsedTimeInStage(stageId, riderId);
			i++;
		}
		return rankedTimes;
	}
	
	//----
	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		int[] riderRanks = getRidersRankInStage(stageId);
		int[] riderScores = new int[riderRanks.length];
		for (Race r : races.values()) {
			Stage s = r.getStage(stageId);
			int i = 0;
			while(i < riderRanks.length) {
				if(pointMaps.getScorings(s.getStageType()).containsKey(i)) {
					riderScores[i] = pointMaps.getScorings(s.getStageType()).get(i);
				} else {
					riderScores[i] = 0;
				}
				i++;
			}
		}
		return riderScores;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		int[] rankedRiders = getRidersRankInStage(stageId);
		int[] riderScores = new int[rankedRiders.length];
		for (Race r : races.values()) {
			Stage s = r.getStage(stageId);
			for (int riderId : rankedRiders) {
				LocalTime[] times = s.getResults(rankedRiders[riderId]);
			}

		}
		return riderScores;
	}

	@Override
	public void eraseCyclingPortal() {
		teams = new HashMap<Integer, Team>();
		races = new HashMap<Integer, Race>();
	}

	@Override
    public void saveCyclingPortal(String filename) throws IOException {
        FileOutputStream file = new FileOutputStream("saves/" + filename + ".ser");
        try {
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
            out.close();
            System.out.println("Saved in /saves/" + filename);
        } catch (IOException e) {
            System.out.println("Save failed");
        }

    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream("saves/" + filename + ".ser");
        ObjectInputStream objInput = new ObjectInputStream(file);
        BadMiniCyclingPortal p = (BadMiniCyclingPortal) objInput.readObject();
        this.races = p.races;
        this.teams = p.teams;
        objInput.close();
    }
}
