package cycling;
import java.util.*;

public class Team {
	
	String teamName;
	String teamDescription;
	
	HashMap<Integer, Rider> riders = new HashMap<Integer, Rider>();
	int riderIdCounter = 0;
	
	public Team(String name, String description) {
		teamName = name;
		teamDescription = description;
		//System.out.println("Team has been constructed.");
	}
	
	//Name
	public String getTeamName() {
		return teamName;
	}
		
		
	//Description
	public String getTeamDescription() {
		return teamDescription;
	}
	
	
	//Riders
	public int addRider(String name, int yob) {
		Rider tempRider = new Rider(name, yob);
		riders.put(riderIdCounter, tempRider);
		riderIdCounter++;
		return riderIdCounter-1;
	}
	
	public void removeRiders(int RidersId) {
		riders.remove(RidersId);
	}
	
	public Rider getRider(int RidersId) {
		return riders.get(RidersId);
	}
	
	public int[] getRidersId() {
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
