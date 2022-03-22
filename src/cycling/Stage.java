package cycling;

import java.io.Serializable;
import java.time.*;
import java.util.HashMap;

public class Stage implements Serializable{
	
	StageState stageState = StageState.UNDER_PREPARATION;
	
	StageType stageType;
	String stageName;
	String stageDescription;
	double stageLength;
	LocalDateTime stageStartTime;
	
	HashMap<Integer, Segment> segments = new HashMap<Integer, Segment>();
	static int segmentIdCounter = 0;
	
	HashMap<Integer, LocalTime[]> results = new HashMap<Integer, LocalTime[]>();
	
	//Constructor
	public Stage(String name, String description, double length, LocalDateTime startTime, StageType type){
		stageType = type;
		stageName = name;
		stageDescription = description;
		stageLength = length;
		stageStartTime= startTime;
		System.out.println("Stage has been constructed. " + this);
	}
	
	
	//Type
	public StageType getStageType() {
		return stageType;
	}
	
	//State
	public StageState getStageState() {
		return stageState;
	}
	
	public void setStageState(StageState state) {
		stageState = state;
	}
	
	//Name
	public String getStageName() {
		return stageName;
	}
	
	
	//Description
	public String getStageDescription() {
		return stageDescription;
	}
	
	
	//Length
	public double getStageLength() {
		return stageLength;
	}
	
	
	//Time
	public LocalDateTime getStartTime() {
		return stageStartTime;
	}
	
	//Segments
	public int addSegment(double location, SegmentType type, double averageGradient, double length) {
		Segment tempSegment = new Segment(location, type, averageGradient, length);
		segments.put(segmentIdCounter, tempSegment);
		segmentIdCounter++;
		return segmentIdCounter-1;
	}
	
	public int addSprint(double location) {
		Segment tempSegment = new Segment(location, SegmentType.SPRINT, 0.0, location);
		segments.put(segmentIdCounter, tempSegment);
		segmentIdCounter++;
		return segmentIdCounter-1;
	}
	
	public void removeSegment(int segmentId) {
		segments.remove(segmentId);
	}
	
	public Segment getSegment(int segmentId) {
		return segments.get(segmentId);
	}
	
	public int[] getSegmentIds() {
		int[] segmentIds = new int[segments.size()];
		int counter = 0;
		for (int i : segments.keySet()){
			segmentIds[counter] = i;
			counter++;
		}
		return segmentIds;
	}
	
	public Segment[] getSegmentsAsArray() { //at this point im just brute forcing shit cause nothing else is working without having to overhaul the last like 6 methods fml
		return (Segment[]) results.values().toArray();
	}
	
	public HashMap<Integer, Segment> getSegment() {
		return segments;
	}
	
	public LocalTime[] getResults(int riderId) {
		return results.get(riderId);
	}
	
	public LocalTime[] getRandomResult() {
		for (int i : results.keySet()) {
			return results.get(i);
		}
		return null;
	}
	
	public Integer[] getResultIds() {
        return results.keySet().toArray(new Integer[0]);
    }
	
	public void removeResults(int riderId) {
		results.remove(riderId);
	}
	
	public HashMap<Integer, LocalTime[]> getResultsHashMap(){
		return results;
	}
	
	//toString
	@Override
	public String toString() {
		String str = String.format("Name: %s, Description: %s, Type: %s, Length: %f, StartTime: %s", stageName, stageDescription, stageType, stageLength, stageStartTime);
		return str;
	}
}
