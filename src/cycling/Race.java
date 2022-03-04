package cycling;
import java.time.LocalDateTime;
import java.util.*;

public class Race {
	
	String raceName;
	String raceDescriptor;
	
	HashMap<Integer, Stage> stages = new HashMap<Integer, Stage>();
	int stageIdCounter = 0;
	
	//Constructor
	public Race(String name, String descriptor){
		raceName = name;
		raceDescriptor = descriptor;
		System.out.println("Race has been constructed. Name: " + name + ", Description: " + descriptor);
	}
	
	
	//Stages
	public int addStage(String stageName, String description, double length, LocalDateTime startTime, StageType type) {
		Stage tempStage = new Stage(stageName, description, length, startTime, type);
		stages.put(stageIdCounter, tempStage);
		stageIdCounter++;
		return stageIdCounter-1;
	}
	
	public void removeStage(int stageId) {
		stages.remove(stageId);
	}
	
	public Stage getStage(int stageId) {
		return stages.get(stageId);
	}
	
	public int[] getStageIds() {
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
	}

	public void setDescription(String des) {
		raceDescriptor = des;
	}
	
	public String getDescription() {
		return raceDescriptor;
	}
	
	public void setName(String name) {
		raceName = name;
	}
	
	public String getName() {
		return raceName;
	}
}