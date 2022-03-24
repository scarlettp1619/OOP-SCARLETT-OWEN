package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;


/**
 * BadCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortalInterface interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class BadCyclingPortal implements CyclingPortalInterface {
	
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
			for (Race r : races.values()) {
				HashMap<Integer, Stage> stages = r.getStages();
				for (Stage s : stages.values()) {
					IllegalNameException.checkName(stageName, s.getStageName());
				}
			}
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
		for (Race r : races.values()) {
			try {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				HashMap<Integer, Stage> stages = r.getStages();
				Stage tempStage = stages.get(stageId);
				return tempStage.getStageLength();
			} catch (IDNotRecognisedException e) {
				
			}
		}
		return 0;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		for (Race r : races.values()) {
			try {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				r.removeStage(stageId);
				break;
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
				Stage tempStage = r.getStage(stageId);
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				InvalidLocationException.checkLocation(location, tempStage.getStageLength());
				InvalidStageStateException.checkStageState(tempStage, tempStage.getStageState());
				InvalidStageTypeException.checkStageType(tempStage);
				segmentId = tempStage.addSegment(location, type, averageGradient, length);
				r.stages.put(stageId, tempStage);
				return segmentId;
			} catch (IDNotRecognisedException | InvalidLocationException | InvalidStageStateException | InvalidStageTypeException e) {
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
				Stage tempStage = r.getStage(stageId);
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				InvalidLocationException.checkLocation(location, tempStage.getStageLength());
				InvalidStageStateException.checkStageState(tempStage, tempStage.getStageState());
				InvalidStageTypeException.checkStageType(tempStage);
				segmentId = tempStage.addSprint(location);
				r.stages.put(stageId, tempStage);
				break;
			} catch (IDNotRecognisedException | InvalidLocationException | InvalidStageStateException | InvalidStageTypeException e) {
				System.out.println(e);
			}
		}
		return segmentId;
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		for (Race r : races.values()) {
			for (Stage s : r.getStages().values()) {
				try {
					IDNotRecognisedException.checkID(segmentId, s.getSegmentIds());
					InvalidStageStateException.checkStageState(s, s.getStageState());
					s.removeSegment(segmentId);	
				} catch (IDNotRecognisedException | InvalidStageStateException e) {
					System.out.println(e);
				}
			}
		}
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		for (Race r : races.values()) {
			try {
				if (Arrays.stream(r.getStageIds()).anyMatch(i -> i == stageId)) {
					Stage tempStage = r.getStage(stageId);
					InvalidStageStateException.checkStageState(tempStage, tempStage.getStageState());
					tempStage.setStageState(StageState.WAITING_FOR_RESULTS);
					r.stages.put(stageId, tempStage);
					return;
				}
			} catch(InvalidStageStateException e) {
				System.out.println(e);
			}
		}
		
		try {
			throw new IDNotRecognisedException("ID \"" + stageId + "\" does not match any IDs!"); //Should be unreachable unless StagePrep couldn't be ran.
		} catch(IDNotRecognisedException e) {
			System.out.println(e);
		}
	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		for (Race r : races.values()) {
			try {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				Stage tempStage = r.getStage(stageId);
				return tempStage.getSegmentIds();
			} catch(IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
		return null;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		try {
			for (Team t : teams.values()) {
					IllegalNameException.checkName(name, t.getTeamName());
					InvalidNameException.checkName("Team", name);
			}
			Team tempTeam = new Team(name, description);
			teams.put(teamIdCounter, tempTeam);
			teamIdCounter++;
			System.out.println("Team Created. Name: " + name + ", Description: " + description);
			} catch (IllegalNameException | InvalidNameException e) {
				System.out.println("Team cannot be created");
		}
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
			if (Arrays.stream(t.getRidersId()).anyMatch(i -> i == riderId)) {
				t.removeRiders(riderId);
				return;
			}
		}
		try {
			throw new IDNotRecognisedException("ID \"" + riderId + "\" does not match any IDs!"); //Should be unreachable unless t.removeRider couldn't be ran.
		} catch(IDNotRecognisedException e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException, InvalidStageStateException { //To Be Fixed. Prematurely throwing an exception. Runs as intended except for IDNotRecognisedException.
		try {
			for (Team t : teams.values()) {
				IDNotRecognisedException.checkID(riderId, t.getRidersId());
			}
			for (Race r : races.values()) {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				Stage tempStage = r.getStage(stageId);
				InvalidCheckpointsException.checkLength(tempStage, checkpoints);
				InvalidStageStateException.checkStageState(tempStage, tempStage.getStageState());
				DuplicatedResultException.checkResults(tempStage.results.get(riderId));
				tempStage.results.put(riderId, checkpoints);
				r.stages.put(stageId, tempStage);
			}
		} catch (IDNotRecognisedException | InvalidCheckpointsException | InvalidStageStateException | DuplicatedResultException e) {
			System.out.println(e);
		}
	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		LocalTime[] stageTimes = null;
		try {
			for (Team t : teams.values()) {
				IDNotRecognisedException.checkID(riderId, t.getRidersId());
			}
			for (Race r : races.values()) {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				Stage tempStage = r.getStage(stageId);
				stageTimes = tempStage.getResults(riderId);
			}
		} catch (IDNotRecognisedException e) {
			System.out.println(e);
		}
		return stageTimes;
	}
	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		LocalTime elapsedTime = LocalTime.of(0, 0);
		try {
			LocalTime[] timeArray = getRiderResultsInStage(stageId, riderId);
			for (LocalTime time : timeArray) {
				elapsedTime = elapsedTime.plusHours(time.getHour());
				elapsedTime = elapsedTime.plusSeconds(time.getSecond());
				elapsedTime = elapsedTime.plusMinutes(time.getMinute());
				elapsedTime = elapsedTime.plusNanos(time.getNano());
			}
		} catch(IDNotRecognisedException e) {
			System.out.println(e);
		}
		return elapsedTime;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		try {
			for (Team t : teams.values()) {
				IDNotRecognisedException.checkID(riderId, t.getRidersId());
			}
			for (Race r : races.values()) {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				Stage tempStage = r.getStage(stageId);
				tempStage.removeResults(riderId);
				r.stages.put(stageId, tempStage);
			}
		} catch(IDNotRecognisedException e) {
			System.out.println(e);
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
		
		int[] rankedRiders = Arrays.stream(sortedMap.keySet().toArray(new Integer[sortedMap.size()])).mapToInt(i -> i).toArray();
		return rankedRiders;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		LocalTime[] rankedTimes = null;
		int i = 0;
		try {
			int[] rankedRiders = getRidersRankInStage(stageId);
			rankedTimes = new LocalTime[rankedRiders.length];
			for(int riderId : rankedRiders) {
				rankedTimes[i] = getRiderAdjustedElapsedTimeInStage(stageId, riderId);
				i++;
			}
		} catch(IDNotRecognisedException e) {
			System.out.println(e);
		}
		return rankedTimes;
	}
	
	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		int[] riderScores = null;
		try {
			int[] riderRanks = getRidersRankInStage(stageId);
			riderScores = new int[riderRanks.length];
			for (Race r : races.values()) {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
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
		} catch (IDNotRecognisedException e) {
			
		}
		return riderScores;
	}
	
	//Not used in interface. Makes other parts functional.
	public int[][] getRidersRankInSegment(int stageId) throws IDNotRecognisedException {
		Stage tempStage = null;

		for (Race r : races.values()) {
			if(Arrays.stream(r.getStageIds()).anyMatch(i -> i == stageId)){
				tempStage = r.getStage(stageId);
			}
		}
		int[][] arrayOfRiderRanks = new int[tempStage.getSegmentIds().length][tempStage.getRandomResult().length]; //2D array: Size of the array initialized to be the of the amount of segments and the amount of scores recorded per segment. This is really shitty code.
		
		HashMap<Integer, LocalTime> tempMap = new HashMap<Integer, LocalTime>();
	
		int j = 0; //Used as a counter to iterate through the amounts of segments
		while (j < arrayOfRiderRanks.length) {
			for (int riderId : tempStage.getResultsHashMap().keySet()) { 
				tempMap.put(riderId, tempStage.getResults(riderId)[j]);
			} //Gets a map of riderId to the j(th) SegmentTime
			
			RankerComparator ranker = new RankerComparator(tempMap);
			SortedMap<Integer, LocalTime> sortedMap = new TreeMap<Integer, LocalTime>(ranker);
			sortedMap.putAll(tempMap);
			
			int[] rankedRiders = Arrays.stream(sortedMap.keySet().toArray(new Integer[sortedMap.size()])).mapToInt(x -> x).toArray(); //Gets array of ranked segmentTimes
			arrayOfRiderRanks[j] = rankedRiders; 
			j++;
		}

		return arrayOfRiderRanks;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		Segment[] tempSegment = null;
		
		for(Race r : races.values()) {
			try {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				Stage s = r.getStage(stageId);
				tempSegment = s.getSegmentsAsArray(); //This will return segments in the order we have entered them because of the way we have implemented segmentId counters. Eliminating the need for a LinkedHashMap.
			} catch (IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
			
		int[][] riderRank = getRidersRankInSegment(stageId); //Array: segment place to array of ranks
		int[] riderScores = new int[riderRank[0].length];
			
		int i = 0; //iterate through segment places
		while(i < riderRank.length) {
			int j = 0; //iterate through singular rider ranks
			while(j < riderRank[i].length) {
				if(pointMaps.getScorings(tempSegment[i].getSegmentType()).containsKey(j)) {
					riderScores[j] = pointMaps.getScorings(tempSegment[i].getSegmentType()).get(j);
				} else {
					riderScores[j] = 0;
				}
				j++;
			}
			i++;
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

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		int raceId = -1;
		ArrayList<String> checkNames = new ArrayList<String>();
		for (int i : races.keySet()) {
			Race tempRace = races.get(i);
			checkNames.add(tempRace.getName());
			if (name == tempRace.getName()) {
					raceId = i;
					break;
			}
		}
		try {
			NameNotRecognisedException.checkName(name, checkNames);
			races.remove(raceId);
		} catch (NameNotRecognisedException e) {
			System.out.println(e);
		}

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		LocalTime[] timesOfRiders = null;
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			int[] rankedRiders = getRidersGeneralClassificationRank(raceId);
			timesOfRiders = new LocalTime[rankedRiders.length];
			
			for (int i = 0; i < rankedRiders.length; i++) {
				timesOfRiders[i] = getRiderTotalTime(raceId, rankedRiders[i]);
			}
		} catch (IDNotRecognisedException e) {
			
		}
		return timesOfRiders;
	}
	
	//Not used in interface. Just here for functionality
	public int getRiderTotalPoints(int raceId, int riderId) {
		int totalPoints = 0;
		for(int stageId : races.get(raceId).getStages().keySet()) {
			try { //Needs try-catch because getRidersRankInStage throws IDNotRecognisedException
				int[] rankedRiders = getRidersRankInStage(stageId);
				int[] riderPoints = getRidersPointsInStage(stageId);
				for(int i = 0; i < rankedRiders.length; i++) {
					try {
						assert rankedRiders[i] == riderId;
						totalPoints = totalPoints + riderPoints[i];
					} catch (AssertionError e) {
						System.out.println(e);
					}
				}
			} catch (IDNotRecognisedException e) {
				
			}
		}
		return totalPoints;
	}
	
	//Not used in interface. Just here for functionality
		public int getRiderTotalMountainPoints(int raceId, int riderId) { //Exact same functionality as the one
			int totalPoints = 0;
			for(int stageId : races.get(raceId).getStages().keySet()) {
				try { //Needs try-catch because getRidersRankInStage throws IDNotRecognisedException
					int[] rankedRiders = getRidersRankInStage(stageId);
					int[] riderPoints = getRidersMountainPointsInStage(stageId);
					for(int i = 0; i < rankedRiders.length; i++) {
						try {
							assert rankedRiders[i] == riderId;
							totalPoints = totalPoints + riderPoints[i];
						} catch (AssertionError e) {
							System.out.println(e);
						}
					}
				} catch (IDNotRecognisedException e) {
					
				}
			}
			return totalPoints;
		}
	
	
	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {  //Works the exact same as getGeneralClassificationTimesInRace
		int[] rankedRiders = getRidersGeneralClassificationRank(raceId);
		int[] pointsOfRiders = new int[rankedRiders.length];
		
		for (int i = 0; i < rankedRiders.length; i++) {
			pointsOfRiders[i] = getRiderTotalPoints(raceId, rankedRiders[i]);
		}
		
		return pointsOfRiders;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException { //Works the exact same as getRidersPointsInRace
		int[] rankedRiders = getRidersGeneralClassificationRank(raceId);
		int[] pointsOfRiders = new int[rankedRiders.length];
		
		for (int i = 0; i < rankedRiders.length; i++) {
			pointsOfRiders[i] = getRiderTotalMountainPoints(raceId, rankedRiders[i]);
		}
		
		return pointsOfRiders;
	}
	
	//Not used in interface. Just here for functionality.
	public LocalTime getRiderTotalTime(int raceId, int riderId) throws IDNotRecognisedException{
		LocalTime totalTime = LocalTime.of(0, 0, 0, 0);

		for(int stageId : races.get(raceId).getStages().keySet()) {
			try {
				IDNotRecognisedException.checkID(riderId, races.get(raceId).getStage(stageId).getResultsHashMap().keySet().stream().mapToInt(i->i).toArray()); //Has to change from Integer[] to primitive int[] to compare. Long ass code
				LocalTime addedTime = getRiderAdjustedElapsedTimeInStage(stageId, riderId);
				totalTime = totalTime.plusHours(addedTime.getHour());
				totalTime = totalTime.plusSeconds(addedTime.getSecond());
				totalTime = totalTime.plusMinutes(addedTime.getMinute());
				totalTime = totalTime.plusNanos(addedTime.getNano());
			} catch (IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
		return totalTime;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		HashMap<Integer, LocalTime> tempMap = new HashMap<Integer, LocalTime>();
		
		for(Stage s : races.get(raceId).getStages().values()) {
			for(int riderId : s.getResultsHashMap().keySet()) {
				tempMap.put(riderId, getRiderTotalTime(raceId, riderId));
			}
		}
		
		RankerComparator ranker = new RankerComparator(tempMap);
		TreeMap<Integer, LocalTime> sortedMap = new TreeMap<Integer, LocalTime>(ranker);
		sortedMap.putAll(tempMap);
		
		int[] rankedRiders = Arrays.stream(sortedMap.keySet().toArray(new Integer[sortedMap.size()])).mapToInt(i -> i).toArray();
		return rankedRiders;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

}
