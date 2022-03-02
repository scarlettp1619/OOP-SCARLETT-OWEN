package cycling;
import java.time.LocalDateTime;

public class Stage {
	
	StageType stageType;
	String stageName;
	String stageDescription;
	double stageLength;
	LocalDateTime stageStartTime;
	
	public Stage(String name, String description, double length, LocalDateTime startTime, StageType type){
		stageType = type;
		stageName = name;
		stageDescription = description;
		stageLength = length;
		stageStartTime= startTime;
	}
}
