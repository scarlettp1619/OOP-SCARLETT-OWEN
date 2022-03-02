package cycling;
import java.time.LocalDateTime;
import java.util.*;

public class Race {
	
	String raceName;
	String raceDescriptor;
	HashMap<Integer, Stage> stages = new HashMap<Integer, Stage>();
	int stageIdCounter = 0;
	
	public Race(String name, String descriptor){
		raceName = name;
		raceDescriptor = descriptor;
		System.out.println("Race has been constructed. " + name + ", " + descriptor);
	}

	public int addStage(String stageName, String description, double length, LocalDateTime startTime, StageType type) {
		Stage stage = new Stage(stageName, description, length, startTime, type);
		stages.put(stageIdCounter, stage);
		stageIdCounter++;
		return stageIdCounter-1;
	}
	
	public HashMap<Integer, Stage> getStages() {
		return stages;
	}
	
	public String getDescription() {
		return raceDescriptor;
	}
	
}
