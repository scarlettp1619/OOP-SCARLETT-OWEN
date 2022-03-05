package cycling;

import java.util.*;

public class Rider {
	
	String riderName;
	int yearOfBirth;
	
	HashMap<Integer, Integer> gcScores = new HashMap<Integer, Integer>(); //HashMap of stage Id and time (in seconds) to complete for stage
	HashMap<Integer, Integer> scScores = new HashMap<Integer, Integer>(); //HashMap of segment Id and score for that score segment
	HashMap<Integer, Integer> mcScores = new HashMap<Integer, Integer>(); //HashMap of segment Id and score for that mountain segment
	
	public Rider(String name, int yob) {
		riderName = name;
		yearOfBirth = yob;
		System.out.println("Rider has been constructed.");
	}
	
	//Name
	public String getRiderName() {
		return riderName;
	}
	
	
	//YOB
	public int getYOB() {
		return yearOfBirth;
	}
	
	//GC
	public int getAggregateGC() {
		int gcScore = 0;
		for (int gc : gcScores.values()) {
			gcScore = gcScore + gc;
		}
		return gcScore;
	}
	
	public void setGCScore(int stageId, int timeInSeconds) {
		gcScores.put(stageId, timeInSeconds);
	}
	
	public void removeGCScore(int stageId) {
		gcScores.remove(stageId);
	}
	
	//SC
	public int getAggregateSC() {
		int scScore = 0;
		for (int sc : scScores.values()) {
			scScore = scScore + sc;
		}
		return scScore;
	}
	
	public void setSCScores(int segmentId, int score) {
		scScores.put(segmentId, score);
	}
	
	public void removeSCScore(int segmentId) {
		scScores.remove(segmentId);
	}
	
	//MC
	public int getAggregateMC() {
		int mcScore = 0;
		for (int mc : mcScores.values()) {
			mcScore = mcScore + mc;
		}
		return mcScore;
	}
	
	public void setMCScores(int segmentId, int score) {
		mcScores.put(segmentId, score);
	}
	
	public void removeMCScore(int segmentId) {
		mcScores.remove(segmentId);
	}
}
