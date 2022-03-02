package cycling;

public class Team {
	String teamName;
	String teamDescription;
	
	public Team(String name, String description) {
		teamName = name;
		teamDescription = description;
		
		System.out.println("Team has been constructed.");
	}
}
