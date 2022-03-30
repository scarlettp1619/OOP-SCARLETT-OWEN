package cycling;
import java.io.Serializable;
import java.util.*;

public class Team implements Serializable{
	
	String teamName;
	String teamDescription;
	// initialises team attributes
	
	HashMap<Integer, Rider> riders = new HashMap<Integer, Rider>();
	// new rider hashmap for storing riders in teams
	static int riderIdCounter = 0;
	
	// Constructor
	public Team(String name, String description) {
		teamName = name;
		teamDescription = description;
		// constructs team name and description attribuets
		//System.out.println("Team has been constructed.");
	}
	
	//Name
	public String getTeamName() {
		return teamName;
		// returns team name
	}
		
		
	//Description
	public String getTeamDescription() {
		return teamDescription;
		// returns team description
	}
	
	
	//Riders
	public int addRider(String name, int yob) {
		Rider tempRider = new Rider(name, yob);
		// creates new rider object with required attributes
		riders.put(riderIdCounter, tempRider);
		// adds rider to rider hashmap
		riderIdCounter++;
		// increases rider counter so no two riders have same ID
		return riderIdCounter-1;
	}
	
	public void removeRiders(int RidersId) {
		riders.remove(RidersId);
		// removes rider from rider hashmap
	}
	
	public Rider getRider(int RidersId) {
		return riders.get(RidersId);
		// returns rider using rider ID
	}
	
	public int[] getRidersId() {
		// same as getRaceIds
		int[] riderIds = new int[riders.size()];
		int counter = 0;
		for (int i : riders.keySet()){
			riderIds[counter] = i;
			counter++;
		}
		return riderIds;
	}
	
	public HashMap<Integer, Rider> getRiders() {
		return riders;
	}
}
