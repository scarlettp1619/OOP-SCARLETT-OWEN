import java.util.Arrays;

import cycling.BadCyclingPortal;
import cycling.BadMiniCyclingPortal;
import cycling.CyclingPortalInterface;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidNameException;
import cycling.MiniCyclingPortalInterface;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {
	/**
	 * Test method.
	 * 
	 * @param args not used
	 * @throws InvalidNameException 
	 * @throws IllegalNameException 
	 * @throws IDNotRecognisedException 
	 */
	public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException {
		System.out.println("The system compiled and started the execution...");

		MiniCyclingPortalInterface portal = new BadMiniCyclingPortal();
		//CyclingPortalInterface portal = new BadCyclingPortal();
		
		
	
		int raceId = portal.createRace("name", "des");
		String details = portal.viewRaceDetails(0);
		String stages;
		
		//System.out.println(Arrays.toString(portal.getRaceIds()));
		
		
		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

	}

}
