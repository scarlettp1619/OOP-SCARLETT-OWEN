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
	
	HashMap<Integer, Race> races = new HashMap<Integer, Race>(); // initialises races hashmap
	static int raceIdCounter = 0; // initialises race ID counter
	
	HashMap<Integer, Team> teams = new HashMap<Integer, Team>(); // initialises teams hashmap
	static int teamIdCounter = 0; // initialises teams ID counter
	
	PointMaps pointMaps = new PointMaps(); // initialises pont maps for storing points in stage
	HashMap<Integer, Integer> flatFinishScores = pointMaps.getScorings(StageType.FLAT);
	// initialises hashmap for flat stage type points
	HashMap<Integer, Integer> hillyFinishScores = pointMaps.getScorings(StageType.MEDIUM_MOUNTAIN);
	// initialises hashmap for hilly/medium stage type points
	HashMap<Integer, Integer> highFinishScores = pointMaps.getScorings(StageType.HIGH_MOUNTAIN);
	// initialises hashmap for high mountain stage type points
	
	@Override
	public int[] getRaceIds() {
		int[] raceIds = new int[races.size()]; // initialises an array of race Ids to store
		int counter = 0; // initialises counter to set size of array
		for (int i : races.keySet()){ // iterates through races hashmap
			raceIds[counter] = i; // sets index of array to the race ID
			counter++; // increments counter
		}
		return raceIds;
	}
	
	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		try {
			for (Race r : races.values()) {
				IllegalNameException.checkName(name, r.getName());
				// checks every race name to see if illegal
			}
			InvalidNameException.checkName("Race", name); // checks race name to see if valid
			Race tempRace = new Race(name, description); // creates new race with name and description
			races.put(raceIdCounter, tempRace); // adds new race to hashmap 
			raceIdCounter++; // increments race ID (next race will have an ID greater than the last)
		} catch (IllegalNameException | InvalidNameException e) {
			System.out.println(e);
		}
		return raceIdCounter-1;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		String details = null; // set to null in case try catch fails and returns nothing
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			// checks if race ID is valid
			Race tempRace = races.get(raceId); // gets the race by ID
			details = tempRace.getDescription(); // gets description from race class getDescription()
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
		int stages = -1; // set to -1 in case try catch fails
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			Race tempRace = races.get(raceId);
			stages = tempRace.getStageIds().length; // gets every stage ID in the race based on race ID
			// and returns the number of stage IDs (number of stages)
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
				// gets every stage in race
				for (Stage s : stages.values()) {
					IllegalNameException.checkName(stageName, s.getStageName());
					// checks if name of stage is illegal
				}
			}
			InvalidNameException.checkName("Stage", stageName);
			InvalidLengthException.checkLength(length);
			// checks if length of stage is valid
			Race tempRace = races.get(raceId);
			returnStageId = tempRace.addStage(stageName, description, length, startTime, type);
			// adds stage to race object with required parameters
			races.put(raceId, tempRace);
			// adds stage to corresponding raceID
		} catch (IDNotRecognisedException | IllegalNameException | InvalidNameException | InvalidLengthException e){
			System.out.println(e);
		}
		return returnStageId;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		int[] stageIds = {}; // initialises stage ID array
		try {
			IDNotRecognisedException.checkID(raceId, getRaceIds());
			Race tempRace = races.get(raceId);
			// gets race from race ID
			stageIds = tempRace.getStageIds();
			// gets every stage ID from race class getStageIds()
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
				// gets every stage from the corresponding race
				Stage tempStage = stages.get(stageId);
				// gets stage from list of stage using stage ID
				return tempStage.getStageLength();
				// gets stage length from stage class getStageLength()
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
				// removes stage from race hashmap
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
				// gets stage from stageId
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				InvalidLocationException.checkLocation(location, tempStage.getStageLength());
				// checks if location entered is valid or not
				InvalidStageStateException.checkStageState(tempStage, tempStage.getStageState());
				// checks if stage state is valid
				InvalidStageTypeException.checkStageType(tempStage);
				// checks if stage type is valid
				segmentId = tempStage.addSegment(location, type, averageGradient, length);
				// adds a segment to the stage using addSegment method in stage class
				r.stages.put(stageId, tempStage);
				// adds segment to corresponding stage in race hashmap
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
				// adds intermediate sprint using addSprint method in stage class
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
			for (Stage s : r.getStages().values()) { // iterates through stages and races
				try {
					IDNotRecognisedException.checkID(segmentId, s.getSegmentIds());
					InvalidStageStateException.checkStageState(s, s.getStageState());
					s.removeSegment(segmentId);	
					// removes segment from stage
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
					// checks if stageId matches any that exist
					Stage tempStage = r.getStage(stageId);
					InvalidStageStateException.checkStageState(tempStage, tempStage.getStageState());
					tempStage.setStageState(StageState.WAITING_FOR_RESULTS);
					// sets stage state using stage class setStageState()
					r.stages.put(stageId, tempStage);
					// updates stage with new stage state
					return;
				}
			} catch(InvalidStageStateException e) {
				System.out.println(e);
			}
		}
		
		try {
			throw new IDNotRecognisedException("ID \"" + stageId + "\" does not match any IDs!"); // should be unreachable unless StagePrep couldn't be ran.
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
				// gets segment Ids from stage class getSegmentIds()
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
					// checks if team name is illegal
					InvalidNameException.checkName("Team", name);
					// checks if team name is invalid
			}
			Team tempTeam = new Team(name, description);
			// creates new team object with team class with required parameters
			teams.put(teamIdCounter, tempTeam);
			// puts new team into team hashmap
			teamIdCounter++;
			// increases team ID counter so next team doesn't have same ID
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
			// removes team from hashmap
		} catch (IDNotRecognisedException e){
			System.out.println(e);
		}

	}

	@Override
	public int[] getTeams() {
		int[] teamIds = new int[teams.size()];
		// initialises new array of IDs from number of teams
		int counter = 0;
		for (int i : teams.keySet()){
			teamIds[counter] = i;
			// sets index of array to the team ID
			counter++;
			// increments counter
		}
		return teamIds;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		int[] riderIds = {};
		// inintialises array of rider IDs
		try {
			IDNotRecognisedException.checkID(teamId, getTeams());
			// check if team exists or not
			Team tempTeam = teams.get(teamId);
			// gets team from team ID
			riderIds = tempTeam.getRidersId();
			// gets riders from team class getRidersId()
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
			// gets team from team ID
			returnTeamId = tempTeam.addRider(name, yearOfBirth);
			// adds a rider with required parameters from team class addRider()
			teams.put(teamID, tempTeam);
			// updates team with new rider in
		} catch(IDNotRecognisedException | IllegalArgumentException e) {
			System.out.println(e);
		}
		return returnTeamId;
	}
	
	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		for (Team t : teams.values()) {
			if (Arrays.stream(t.getRidersId()).anyMatch(i -> i == riderId)) {
				// if rider ID matches existing rider ID
				t.removeRiders(riderId);
				// remove rider
				return;
			}
		}
		try {
			throw new IDNotRecognisedException("ID \"" + riderId + "\" does not match any IDs!"); // should be unreachable unless t.removeRider couldn't be ran
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
				// gets stage from stageID
				InvalidCheckpointsException.checkLength(tempStage, checkpoints);
				// checks if checkpoints are valid
				InvalidStageStateException.checkStageState(tempStage, tempStage.getStageState());
				DuplicatedResultException.checkResults(tempStage.results.get(riderId));
				// checks if results already exist
				tempStage.results.put(riderId, checkpoints);
				// enters rider results into stage
				r.stages.put(stageId, tempStage);
				// updates stage with new results
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
				// gets result from stage using stage class getResults()
			}
		} catch (IDNotRecognisedException e) {
			System.out.println(e);
		}
		return stageTimes;
	}
	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		LocalTime elapsedTime = LocalTime.of(0, 0);
		// creates empty time to enter into, set to 0 in case rider doesnt exist
		try {
			LocalTime[] timeArray = getRiderResultsInStage(stageId, riderId);
			// gets rider time from stage ID and rider ID
			for (LocalTime time : timeArray) {
				elapsedTime = elapsedTime.plusHours(time.getHour());
				// adds hours from rider time
				elapsedTime = elapsedTime.plusSeconds(time.getSecond());
				// adds seconds
				elapsedTime = elapsedTime.plusMinutes(time.getMinute());
				// adds minutes
				elapsedTime = elapsedTime.plusNanos(time.getNano());
				// adds nanoseconds
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
				// gets stage from stage ID
				tempStage.removeResults(riderId);
				// removes results from stage class removeResults()
				r.stages.put(stageId, tempStage);
				// updates stage with new results
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
		// initialises time hashmap
		for (int riderId : tempStage.getResultsHashMap().keySet()) {
			// for very rider in stage that has results
			tempMap.put(riderId, getRiderAdjustedElapsedTimeInStage(stageId, riderId));
			// put rider results in time hashmap
		}
		
		RankerComparator ranker = new RankerComparator(tempMap);
		// initialises rank comparator object to compare ranks 
		TreeMap<Integer, LocalTime> sortedMap = new TreeMap<Integer, LocalTime>(ranker);
		// sorts all times that have been compared
		sortedMap.putAll(tempMap);
		// puts every time into the treemap
		
		int[] rankedRiders = Arrays.stream(sortedMap.keySet().toArray(new Integer[sortedMap.size()])).mapToInt(i -> i).toArray();
		// creates an array of ranked riders using sorted times
		return rankedRiders;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		LocalTime[] rankedTimes = null;
		// local time array of ranked times to return null if stage doesn't exist
		int i = 0;
		try {
			int[] rankedRiders = getRidersRankInStage(stageId);
			rankedTimes = new LocalTime[rankedRiders.length];
			// initialises localtime array with number of riders that have been ranked
			for(int riderId : rankedRiders) {
				rankedTimes[i] = getRiderAdjustedElapsedTimeInStage(stageId, riderId);
				// for every rider in ranked times, get their times, already sorted by getRidersRankInStage.
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
			// gets every rider rank
			riderScores = new int[riderRanks.length];
			// creates new int array from number of rider results
			for (Race r : races.values()) {
				IDNotRecognisedException.checkID(stageId, r.getStageIds());
				Stage s = r.getStage(stageId);
				// gets stage where results are stored
				int i = 0;
				while(i < riderRanks.length) {
					if(pointMaps.getScorings(s.getStageType()).containsKey(i)) {
						riderScores[i] = pointMaps.getScorings(s.getStageType()).get(i);
						// gets score from points map based on stage type and adds to array
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
	
	// not used in interface. Makes other parts functional.
	public int[][] getRidersRankInSegment(int stageId) throws IDNotRecognisedException {
		Stage tempStage = null;

		for (Race r : races.values()) {
			if(Arrays.stream(r.getStageIds()).anyMatch(i -> i == stageId)){
				tempStage = r.getStage(stageId);
			}
		}
		int[][] arrayOfRiderRanks = new int[tempStage.getSegmentIds().length][tempStage.getRandomResult().length];
		//2D array: size of the array initialized to be the of the amount of segments and the amount of scores recorded per segment. 
		
		HashMap<Integer, LocalTime> tempMap = new HashMap<Integer, LocalTime>();
		// temporary hashmap to store values in
	
		int j = 0; // used as a counter to iterate through the amounts of segments
		while (j < arrayOfRiderRanks.length) {
			for (int riderId : tempStage.getResultsHashMap().keySet()) { 
				tempMap.put(riderId, tempStage.getResults(riderId)[j]);
			} // gets a map of riderId to the j(th) SegmentTime
			
			RankerComparator ranker = new RankerComparator(tempMap);
			SortedMap<Integer, LocalTime> sortedMap = new TreeMap<Integer, LocalTime>(ranker);
			// sorts rider times into treemap
			sortedMap.putAll(tempMap);
			// adds sorted times into tree map
			
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
				// returns segments in the order entered.
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
		ArrayList<String> checkNames = new ArrayList<String>();
		// new arraylist of names to add every race to
		for (int i : races.keySet()) {
			Race tempRace = races.get(i);
			// gets every race by ID
			checkNames.add(tempRace.getName());
			// adds race name to array list
			if (name == tempRace.getName()) {
					// if names match
					raceId = i;
					// sets race ID to race with matching name
					break;
					// ends loop early
			}
		}
		try {
			NameNotRecognisedException.checkName(name, checkNames);
			// checks if name exists or not
			races.remove(raceId);
			// removes race by ID (taken from name) if it exists
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
			// initialises array from riders GC rank
			timesOfRiders = new LocalTime[rankedRiders.length];
			// creates new array  from number of ranked riders
			
			for (int i = 0; i < rankedRiders.length; i++) {
				timesOfRiders[i] = getRiderTotalTime(raceId, rankedRiders[i]);
				// get times from riders
			}
		} catch (IDNotRecognisedException e) {
			
		}
		return timesOfRiders;
	}
	
	// not used in interface. Just here for functionality
	public int getRiderTotalPoints(int raceId, int riderId) {
		int totalPoints = 0;
		for(int stageId : races.get(raceId).getStages().keySet()) {
			try { // needs try-catch because getRidersRankInStage throws IDNotRecognisedException
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
		public int getRiderTotalMountainPoints(int raceId, int riderId) { //Exact same functionality as the one
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
		int[] rankedRiders = getRidersGeneralClassificationRank(raceId);
		// gets every rider rank from race ID
		int[] pointsOfRiders = new int[rankedRiders.length];
		// initialises int array from number of riders ranked
		
		for (int i = 0; i < rankedRiders.length; i++) {
			pointsOfRiders[i] = getRiderTotalPoints(raceId, rankedRiders[i]);
			// gets rider points from non-interface method (see above)
		}
		
		return pointsOfRiders;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException { //Works the exact same as getRidersPointsInRace
		int[] rankedRiders = getRidersGeneralClassificationRank(raceId);
		int[] pointsOfRiders = new int[rankedRiders.length];
		
		for (int i = 0; i < rankedRiders.length; i++) {
			pointsOfRiders[i] = getRiderTotalMountainPoints(raceId, rankedRiders[i]);
			// gets rider points from non-interface method (see above)
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
				tempMap.put(riderId, getRiderTotalTime(raceId, riderId));
				// gets total rider time from non-interface method (see above)
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
