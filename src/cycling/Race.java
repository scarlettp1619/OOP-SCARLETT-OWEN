package cycling;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Race implements Serializable{
	
	String raceName;
	String raceDescriptor;
	// initialises race attributes
	
	HashMap<Integer, Stage> stages = new HashMap<Integer, Stage>();
	// sets stage hashmap to insert stages into race
	static int stageIdCounter = 0;
	
	// constructor
	public Race(String name, String descriptor){
		raceName = name;
		// sets race name to name (used in methods on cycling portal)
		raceDescriptor = descriptor;
		// sets name description to description
		System.out.println("Race has been constructed. " + this);
	}
	
	
	//Stages
	public int addStage(String stageName, String description, double length, LocalDateTime startTime, StageType type) {
		Stage tempStage = new Stage(stageName, description, length, startTime, type);
		// creates new stage object with required parameters
		stages.put(stageIdCounter, tempStage);
		// adds stage to stage hashmap
		stageIdCounter++;
		// increments stage ID so next stage ID will be different
		return stageIdCounter-1;
	}
	
	public void removeStage(int stageId) {
		stages.remove(stageId);
		// removes stage from hashmap
	}
	
	public Stage getStage(int stageId) {
		return stages.get(stageId);
		// gets stage object from stage ID
	}
	
	public int[] getStageIds() {
		// same as getRaceIds, iterates through and sets array to every stage ID
		int[] stageIds = new int[stages.size()];
		int counter = 0;
		for (int i : stages.keySet()){
			stageIds[counter] = i;
			counter++;
		}
		return stageIds;
	}
	
	public HashMap<Integer, Stage> getStages() {
		return stages;
		// returns every stage object
	}

	public void setDescription(String des) {
		raceDescriptor = des;
		// sets description attribute of race
	}
	
	public String getDescription() {
		return raceDescriptor;
		// returns description attribute
	}
	
	public void setName(String name) {
		raceName = name;
		// sets name attribute of race
	}
	
	public String getName() {
		return raceName;
		// returns name attribute
	}
	
	//toString
	@Override
	public String toString() {
		String str = String.format("Name: %s, Description: %s", raceName, raceDescriptor);
		return str;
	}
}