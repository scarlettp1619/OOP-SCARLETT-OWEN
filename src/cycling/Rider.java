package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

public class Rider implements Serializable{
	
	String riderName;
	int yearOfBirth;
	// initialises rider attributes
	
	//HashMap<Integer, LocalTime> stageTimes = new HashMap<Integer, LocalTime>(); //HashMap of stage Id and time (in seconds) to complete for stage
	HashMap<Integer, LocalTime> segmentTimes = new HashMap<Integer, LocalTime>(); // hashMap of segment Id and time for that score segment
	HashMap<Integer, HashMap<Integer, LocalTime>> timeScores = new HashMap<Integer, HashMap<Integer, LocalTime>>(); // hashMap of segment Id and time for that mountain segment

	// Constructor
	public Rider(String name, int yob) {
		riderName = name;
		yearOfBirth = yob;
		// sets rider attributes
		System.out.println("Rider has been constructed. Name: " + name + ", YOB: " + yob);
	}
	
	//Name
	public String getRiderName() {
		return riderName;
		// returns rider name attribute
	}
	
	
	//YOB
	public int getYOB() {
		return yearOfBirth;
		// returns year of birth attribute
	}
	
	//Scores
	public void addSegmentScore(int stageId, int segmentId, LocalTime score) {
		HashMap<Integer, LocalTime> tempScore = timeScores.get(stageId);
		// gets score from stageID based on current rider
		tempScore.put(segmentId, score);
		// puts score  into score hashmap
		timeScores.put(stageId, tempScore);
	}
	
	public LocalTime[] getSegmentScore(int stageId) {
		return timeScores.get(stageId).values().toArray(new LocalTime[0]);
		// returns segment score for rider
	}
	
	public void removeStageScore(int stageId) {
		timeScores.remove(stageId);
		// removes stage score for rider
	}
	
	
	/*
	public LocalTime getTotalStageTime() {
		LocalTime gcScore = LocalTime.of(0, 0, 0, 0);
		for (LocalTime gc : stageTimes.values()) {
			gcScore = gcScore.plusHours(gc.getHour()).plusMinutes(gc.getMinute()).plusSeconds(gc.getSecond()).plusNanos(gc.getNano());
		}
		return gcScore;
	}
	
	public void setStageTime(int stageId, LocalTime time) {
		stageTimes.put(stageId, time);
	}
	
	public void removeGCScore(int stageId) {
		stageTimes.remove(stageId);
	}
	
	//SC
	public int getTotalSegmentTime() {
		//WIP
		LocalTime scScore = LocalTime.of(0, 0, 0, 0);
		for (LocalTime sc : segmentTimes.values()) {
			scScore = scScore.plusHours(sc.getHour()).plusMinutes(sc.getMinute()).plusSeconds(sc.getSecond()).plusNanos(sc.getNano());
		}
		return 0;
	}
	
	public void setSegmentTimes(int segmentId, LocalTime time) {
		segmentTimes.put(segmentId, time);
	}
	
	public void removeSCScore(int segmentId) {
		segmentTimes.remove(segmentId);
	}
	
	public int getAggregateMC() {
		//WIP
		LocalTime mcScore = LocalTime.of(0, 0, 0, 0);
		for (LocalTime mc : mcScores.values()) {
			mcScore = mcScore.plusHours(mc.getHour()).plusMinutes(mc.getMinute()).plusSeconds(mc.getSecond()).plusNanos(mc.getNano());
		}
		return 0;
	}
	
	public void setMCScores(int segmentId, LocalTime time) {
		mcScores.put(segmentId, time);
	}
	
	public void removeMCScore(int segmentId) {
		mcScores.remove(segmentId);
	}*/
}
