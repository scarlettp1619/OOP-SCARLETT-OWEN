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
public class CyclingPortal implements CyclingPortalInterface {
	
	HashMap<Integer, Race> races = new HashMap<Integer, Race>(); 
	static int raceIdCounter = 0; //Race ID counter is static through multiple portals
	HashMap<Integer, Team> teams = new HashMap<Integer, Team>(); 
	static int teamIdCounter = 0; //Team ID counter is static through multiple portals
	
	PointMaps pointMaps = new PointMaps(); 
	HashMap<Integer, Integer> flatFinishScores = pointMaps.getScorings(StageType.FLAT);
	HashMap<Integer, Integer> hillyFinishScores = pointMaps.getScorings(StageType.MEDIUM_MOUNTAIN);
	HashMap<Integer, Integer> highFinishScores = pointMaps.getScorings(StageType.HIGH_MOUNTAIN);

	
	@Override
	public int[] getRaceIds() {
		int[] raceIds = new int[races.size()]; 
		int counter = 0; 
		for (int i : races.keySet()){ //Iterates through values of races map
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
				// Checks every race name to see if illegal
			}
			InvalidNameException.checkName("Race", name); // Checks race name to see if valid
			Race tempRace = new Race(name, description); // Create temporary race
			races.put(raceIdCounter, tempRace); // Adds the temporary race to hashmap 
			raceIdCounter++;
		} catch (IllegalNameException | InvalidNameException e) {
			System.out.println(e);
		}
		return raceIdCounter-1; // raceIdCounter has already been incremented by 1, therefore it returns it minus 1
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		String details = null; // Default value
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			// Checks if race ID is valid
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
			races.remove(raceId); // removes race from hashmap
		} catch (IDNotRecognisedException e) {
			System.out.println(e);
		} 
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		int stages = -1; // Default value
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			Race tempRace = races.get(raceId);
			stages = tempRace.getStageIds().length; // gets length of stageIds from temprace
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
				for (Stage s : r.getStages().values()) { // Iterates through every stage
					IllegalNameException.checkName(stageName, s.getStageName());
				}
			}
			InvalidNameException.checkName("Stage", stageName);
			InvalidLengthException.checkLength(length);
			Race tempRace = races.get(raceId);
			returnStageId = tempRace.addStage(stageName, description, length, startTime, type); // Adds stage to the temporary race
			races.put(raceId, tempRace); // Update race linked to raceId to the new race that contains the added stage
		} catch (IDNotRecognisedException | IllegalNameException | InvalidNameException | InvalidLengthException e){
			System.out.println(e);
		}
		return returnStageId;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		int[] stageIds = {}; // Initialises stage ID array
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
		for (Race r : races.values()) { // Iterating through all races
			try {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				Stage tempStage = r.getStages().get(stageId); // Gets stage from the race
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
		int segmentId = 0; // Default value to return
		
		for (Race r : races.values()) {
			try {
				Stage tempStage = r.getStage(stageId); // Temporary stage
				
				IDNotRecognisedException.checkID(stageId, r.getStageIds()); // Checks
				InvalidLocationException.checkLocation(location, tempStage.getStageLength());
				InvalidStageStateException.checkStageState(tempStage, tempStage.getStageState());
				InvalidStageTypeException.checkStageType(tempStage);
				
				segmentId = tempStage.addSegment(location, type, averageGradient, length); // Adds segment to temporary stage
																						   // addSegment method returns segmentId of the segment just added
				r.stages.put(stageId, tempStage); // Updates stage
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
		int segmentId = 0; // Default value to return
		for (Race r : races.values()) {
			try {
				Stage tempStage = r.getStage(stageId);
				
				IDNotRecognisedException.checkID(stageId, r.getStageIds()); //Checks
				InvalidLocationException.checkLocation(location, tempStage.getStageLength());
				InvalidStageStateException.checkStageState(tempStage, tempStage.getStageState());
				InvalidStageTypeException.checkStageType(tempStage);
				
				segmentId = tempStage.addSprint(location); // Adds segment of sprint type with an adder
														   // addSprint method returns segmentId of the segment just added
				r.stages.put(stageId, tempStage);
				return segmentId;
			} catch (IDNotRecognisedException | InvalidLocationException | InvalidStageStateException | InvalidStageTypeException e) {
				System.out.println(e);
			}
		}
		return segmentId;
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		for (Race r : races.values()) {
			for (Stage s : r.getStages().values()) { // Iterates through stages and races
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
		for (Race r : races.values()) { // Iterate through races
			try {
				if (Arrays.stream(r.getStageIds()).anyMatch(i -> i == stageId)) { // Checks if stageId matches any that exist in r
					Stage tempStage = r.getStage(stageId);
					InvalidStageStateException.checkStageState(tempStage, tempStage.getStageState());
					tempStage.setStageState(StageState.WAITING_FOR_RESULTS);
					r.stages.put(stageId, tempStage); // Updates stage with new state in it
					return; // Breaks loop to not waste resources
				}
			} catch(InvalidStageStateException e) {
				System.out.println(e);
			}
		}
		
		try {
			throw new IDNotRecognisedException("ID \"" + stageId + "\" does not match any IDs!"); // Should be unreachable unless StagePrep couldn't be ran.
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
					IllegalNameException.checkName(name, t.getTeamName()); //Checks
					InvalidNameException.checkName("Team", name);
			}
			Team tempTeam = new Team(name, description);
			teams.put(teamIdCounter, tempTeam);
			teamIdCounter++;
			} catch (IllegalNameException | InvalidNameException e) {
				System.out.println("Team cannot be created");
		}
		return teamIdCounter-1; // Since teamIdCounter has already been incremented. Has to return it by -1.
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
		int[] teamIds = new int[teams.size()]; // Initialises new array with size of team size
		int counter = 0;
		for (int i : teams.keySet()){
			teamIds[counter] = i; // Sets element of array to the team ID
			counter++;
		}
		return teamIds;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		int[] riderIds = {}; // Default value to return
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
		int returnTeamId = 0; // Default value
		try {
			IDNotRecognisedException.checkID(teamID, getTeams());
			Team tempTeam = teams.get(teamID); // Temporary team
			returnTeamId = tempTeam.addRider(name, yearOfBirth); // Adds rider to tempTeam. addRider returns riderId of rider created.
			teams.put(teamID, tempTeam); // Updates team with new rider in
		} catch(IDNotRecognisedException | IllegalArgumentException e) {
			System.out.println(e);
		}
		return returnTeamId;
	}
	
	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		for (Team t : teams.values()) {
			if (Arrays.stream(t.getRidersId()).anyMatch(i -> i == riderId)) { // If rider ID matches existing rider ID
				t.removeRiders(riderId);
				return;
			}
		}
		
		try {
			throw new IDNotRecognisedException("ID \"" + riderId + "\" does not match any IDs!"); // Should be unreachable
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
				
				tempStage.results.put(riderId, checkpoints); // Enters rider results into tempStage
				r.stages.put(stageId, tempStage); // Updates stage with new results
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
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException { // Adjusted part doesn't work, had no time to fix. Code only aggregates the time of rider.
		LocalTime elapsedTime = LocalTime.of(0, 0); // Empty time to enter into
		try {
			LocalTime[] timeArray = getRiderResultsInStage(stageId, riderId);
			// gets rider time from stage ID and rider ID
			for (LocalTime time : timeArray) {
				elapsedTime = elapsedTime.plusHours(time.getHour()); //Adds time up
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
				r.stages.put(stageId, tempStage); // Updates stage with deleted results
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
				tempStage = r.getStage(stageId); // Gets stage from id if the above check passes without an exception.
			} catch(IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
		
		HashMap<Integer, LocalTime> tempMap = new HashMap<Integer, LocalTime>(); //Creates empty temporary map to store elapsed time in.
		
		for (int riderId : tempStage.getResultsHashMap().keySet()) { // Iterates through the riders that have results in stage
			tempMap.put(riderId, getRiderAdjustedElapsedTimeInStage(stageId, riderId)); // Puts riders elapsed time into the map with their corresponding riderId
		}
		
		RankerComparator ranker = new RankerComparator(tempMap); // Comparator
		TreeMap<Integer, LocalTime> sortedMap = new TreeMap<Integer, LocalTime>(ranker); // Treemap with comparator for sorting
		sortedMap.putAll(tempMap); // Put and sorts the entire temp map into the sortedMap
		
		int[] rankedRiders = Arrays.stream(sortedMap.keySet().toArray(new Integer[sortedMap.size()])).mapToInt(i -> i).toArray(); // Get ordered riderIds from sortedMap as an int array
		return rankedRiders;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		LocalTime[] rankedTimes = null;
		int i = 0; // Counter
		try {
			int[] rankedRiders = getRidersRankInStage(stageId);
			rankedTimes = new LocalTime[rankedRiders.length]; // rankedTime is a new LocalTime array with size of the length of the rankedRiders
			for(int riderId : rankedRiders) {
				rankedTimes[i] = getRiderAdjustedElapsedTimeInStage(stageId, riderId); // For every rider in ranked times, get their times, already sorted by getRidersRankInStage.
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
				int i = 0; // Counter
				while(i < riderRanks.length) {
					if(pointMaps.getScorings(s.getStageType()).containsKey(i)) {  // pointMaps.getScorings(stageType) gets a map with ranks corresponding to the points you get
						riderScores[i] = pointMaps.getScorings(s.getStageType()).get(i); // Gets score from points map based on stage type and adds to array
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
	
	// Not used in interface. Makes other parts functional.
	public int[][] getRidersRankInSegment(int stageId) throws IDNotRecognisedException {
		Stage tempStage = null;

		for (Race r : races.values()) {
			if(Arrays.stream(r.getStageIds()).anyMatch(i -> i == stageId)){
				tempStage = r.getStage(stageId);
			}
		}
		int[][] arrayOfRiderRanks = new int[tempStage.getSegmentIds().length][tempStage.getRandomResult().length]; // 2D array: size of the array initialized to be the of the amount of segments and the amount of scores recorded per segment. 
		
		HashMap<Integer, LocalTime> tempMap = new HashMap<Integer, LocalTime>();
	
		int j = 0;
		while (j < arrayOfRiderRanks.length) {
			// Same sorting system as getRiderRankInStage. Comments to explain code there
			for (int riderId : tempStage.getResultsHashMap().keySet()) { 
				tempMap.put(riderId, tempStage.getResults(riderId)[j]);
			} 
			
			RankerComparator ranker = new RankerComparator(tempMap);
			SortedMap<Integer, LocalTime> sortedMap = new TreeMap<Integer, LocalTime>(ranker);
			sortedMap.putAll(tempMap);
			
			int[] rankedRiders = Arrays.stream(sortedMap.keySet().toArray(new Integer[sortedMap.size()])).mapToInt(x -> x).toArray(); // gets array of ranked segmentTimes
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
				tempSegment = s.getSegmentsAsArray(); 
				// Returns segments in the order entered
			} catch (IDNotRecognisedException e) {
				System.out.println(e);
			}
		}
			
		int[][] riderRank = getRidersRankInSegment(stageId); //Array: segment place to array of ranks
		int[] riderScores = new int[riderRank[0].length];
			
		int i = 0; // iterate through segment places
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
		// empties team hashmap
		races = new HashMap<Integer, Race>();
		// empties races hashmap
	}

	@Override
    public void saveCyclingPortal(String filename) throws IOException {
        FileOutputStream file = new FileOutputStream("saves/" + filename + ".ser");
        // creates new .ser file based on inputted file name
        try {
            ObjectOutputStream out = new ObjectOutputStream(file);
            // creates a new object to output
            out.writeObject(this);
            // writes all serializable data to file
            out.close();
            // closes output
            System.out.println("Saved in /saves/" + filename);
        } catch (IOException e) {
            System.out.println("Save failed");
        }

    }

	@Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream("saves/" + filename + ".ser");
        // gets file based on inputted file name 
        ObjectInputStream objInput = new ObjectInputStream(file);
        // creates new object to input
        MiniCyclingPortal p = (MiniCyclingPortal) objInput.readObject();
        // reads object	from data saved in file
        this.races = p.races;
        this.teams = p.teams;
        // inserts data into races and teams hashmap
        objInput.close();
    }

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		int raceId = -1;
		ArrayList<String> checkNames = new ArrayList<String>(); // new arraylist of names to add every raceName to
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
	
	// not used in interface. Just here for functionality
	public int getRiderTotalPoints(int raceId, int riderId) {
		int totalPoints = 0;
		for(int stageId : races.get(raceId).getStages().keySet()) {
			try { 
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
	
	// not used in interface. Just here for functionality
		public int getRiderTotalMountainPoints(int raceId, int riderId) { // Exact same functionality as the one above
			int totalPoints = 0;
			for(int stageId : races.get(raceId).getStages().keySet()) {
				try { // needs try-catch because getRidersRankInStage throws IDNotRecognisedException
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
		int[] rankedRiders = getRidersGeneralClassificationRank(raceId); // Gets every rider GC rank from race ID
		int[] pointsOfRiders = new int[rankedRiders.length]; // Initialises int array from number of riders ranked
		
		for (int i = 0; i < rankedRiders.length; i++) {
			pointsOfRiders[i] = getRiderTotalPoints(raceId, rankedRiders[i]); // gets rider points from non-interface method (see above)
		}
		
		return pointsOfRiders;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException { //Works the exact same as getRidersPointsInRace
		int[] rankedRiders = getRidersGeneralClassificationRank(raceId);
		int[] pointsOfRiders = new int[rankedRiders.length];
		
		for (int i = 0; i < rankedRiders.length; i++) {
			pointsOfRiders[i] = getRiderTotalMountainPoints(raceId, rankedRiders[i]); // gets rider points from non-interface method (see above)
		}
		
		return pointsOfRiders;
	}
	
	// not used in interface. Just here for functionality.
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
				tempMap.put(riderId, getRiderTotalTime(raceId, riderId)); // gets total rider time from non-interface method (see above)
			}
		}
		
		RankerComparator ranker = new RankerComparator(tempMap);
		TreeMap<Integer, LocalTime> sortedMap = new TreeMap<Integer, LocalTime>(ranker);
		sortedMap.putAll(tempMap);
		
		int[] rankedRiders = Arrays.stream(sortedMap.keySet().toArray(new Integer[sortedMap.size()])).mapToInt(i -> i).toArray();
		return rankedRiders;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException { //No time. Incomplete
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException { // No time. Incomplete.
		// TODO Auto-generated method stub
		return null;
	}

}
