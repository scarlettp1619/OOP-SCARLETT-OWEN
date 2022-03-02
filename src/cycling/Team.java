package cycling;

public class Team {
	String teamName;
	String teamDescription;
	int riderLength = 0;
	//int teamID;
	
	Rider[] rider = new Rider[riderLength]; //riderLength is just temporary don't
	// really know what to put here tbh
	
	//throws illegalnameexception if name already exists
	//throws invalidnameexception if name is null
	
	public Team(String name, String description, Rider[] riders) {
		name = teamName;
		description = teamDescription;
		riders = rider;
		
	}
}
