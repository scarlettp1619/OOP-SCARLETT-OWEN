package cycling;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Stage {
	
	StageType stageType;
	String stageName;
	String stageDescription;
	double stageLength;
	LocalDateTime stageStartTime;
	
	HashMap<Integer, Segment> segments = new HashMap<Integer, Segment>();
	static int segmentIdCounter = 0;
	
	
	//Constructor
	public Stage(String name, String description, double length, LocalDateTime startTime, StageType type){
		stageType = type;
		stageName = name;
		stageDescription = description;
		stageLength = length;
		stageStartTime= startTime;
		System.out.println("Stage has been constructed.");
	}
	
	
	//Type
	public StageType getStageType() {
		return stageType;
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
	
	public HashMap<Integer, Segment> getSegment() {
		return segments;
	}
}
