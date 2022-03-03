package cycling;

public class Rider {
	String riderName;
	int yearOfBirth;
	
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
	
}
