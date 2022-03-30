package cycling;

import java.io.Serializable;
import java.time.*;
import java.util.HashMap;

public class Stage implements Serializable{
	
	StageState stageState = StageState.UNDER_PREPARATION;
	// default stage state when created
	
	StageType stageType;
	String stageName;
	String stageDescription;
	double stageLength;
	LocalDateTime stageStartTime;
	// initialises stage attributes
	
	HashMap<Integer, Segment> segments = new HashMap<Integer, Segment>();
	// segment hashmap for creating segments
	static int segmentIdCounter = 0;
	
	HashMap<Integer, LocalTime[]> results = new HashMap<Integer, LocalTime[]>();
	// results hashmap
	
	//Constructor
	public Stage(String name, String description, double length, LocalDateTime startTime, StageType type){
		stageType = type;
		stageName = name;
		stageDescription = description;
		stageLength = length;
		stageStartTime= startTime;
		// sets stage attributes
		System.out.println("Stage has been constructed. " + this);
	}
	
	
	//Type
	public StageType getStageType() {
		return stageType;
		// returns stage type attribute
	}
	
	//State
	public StageState getStageState() {
		return stageState;
		// returns stage state attribute
	}
	
	public void setStageState(StageState state) {
		stageState = state;
		// sets stage state attribute
	}
	
	//Name
	public String getStageName() {
		return stageName;
		// return stage name attribute
	}
	
	
	//Description
	public String getStageDescription() {
		return stageDescription;
		// returns stage description attribute
	}
	
	
	//Length
	public double getStageLength() {
		return stageLength;
		// returns stage length attribute
	}
	
	
	//Time
	public LocalDateTime getStartTime() {
		return stageStartTime;
		// returns stage start time attribute
	}
	
	//Segments
	public int addSegment(double location, SegmentType type, double averageGradient, double length) {
		Segment tempSegment = new Segment(location, type, averageGradient, length);
		// creates new segment object with required attributes
		segments.put(segmentIdCounter, tempSegment);
		// adds segment to segment hashmap
		segmentIdCounter++;
		// increase segment ID so next segment has different ID
		return segmentIdCounter-1;
	}
	
	public int addSprint(double location) {
		Segment tempSegment = new Segment(location, SegmentType.SPRINT, 0.0, location);
		// creates new segment object (specifically sprint)
		segments.put(segmentIdCounter, tempSegment);
		segmentIdCounter++;
		return segmentIdCounter-1;
	}
	
	public void removeSegment(int segmentId) {
		segments.remove(segmentId);
		// removes segment from segment hashmap by ID
	}
	
	public Segment getSegment(int segmentId) {
		return segments.get(segmentId);
		// returns segment by ID
	}
	
	public int[] getSegmentIds() {
		// same as get race IDs
		int[] segmentIds = new int[segments.size()];
		int counter = 0;
		for (int i : segments.keySet()){
			segmentIds[counter] = i;
			counter++;
		}
		return segmentIds;
	}
	
	public Segment[] getSegmentsAsArray() {
		return segments.values().toArray(new Segment[0]);
	}
	
	public HashMap<Integer, Segment> getSegment() {
		return segments;
		// returns segments
	}
	
	public LocalTime[] getResults(int riderId) {
		return results.get(riderId);
		// returns rider results in segment from rider ID
	}
	
	public LocalTime[] getRandomResult() {
		for (int i : results.keySet()) {
			return results.get(i);
		}
		return null;
	}
	
	public Integer[] getResultIds() {
        return results.keySet().toArray(new Integer[0]);
        // returns result IDs
    }
	
	public void removeResults(int riderId) {
		results.remove(riderId);
		// removes results from hashmap
	}
	
	public HashMap<Integer, LocalTime[]> getResultsHashMap(){
		return results;
		// returns results
	}
	
	//toString
	@Override
	public String toString() {
		String str = String.format("Name: %s, Description: %s, Type: %s, Length: %f, StartTime: %s", stageName, stageDescription, stageType, stageLength, stageStartTime);
		return str;
	}
}
