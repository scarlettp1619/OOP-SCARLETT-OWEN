package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;

public class Segment implements Serializable{
	
	SegmentType segmentType;
	double segmentLocation;
	double segmentLength;
	double avgGradient;
	// initialises segment attributes
	
	HashMap<Integer, LocalTime> riderTimes =  new HashMap<Integer, LocalTime>();
	// initialises rider time hashmap
	
	// constructor
	public Segment(double loc, SegmentType type, double aGrad, double length){
		segmentLocation = loc;
		segmentType = type;
		avgGradient = aGrad;
		segmentLength = length;
		// sets stage attributes
		System.out.println("Segment has been constructed. " + this);
	}
	
		//Type
		public SegmentType getSegmentType() {
			return segmentType;
			// returns segment type attribute
		}
		
		public SegmentType setSegmentType(SegmentType type) {
			return segmentType = type;
			// sets segment type attribute
		}
		
		
		//Description
		public double getSegmentLocation() {
			return segmentLocation;
			// returns segment location attribute
		}
		
		public void setSegmentLocation(double location) {
			segmentLocation = location;
			// sets segment location attribute
		}
		
		
		//Length
		public double getSegmentLength() {
			return segmentLength;
			// returns segment length attribute
		}
		
		public void setSegmentLength(double length) {
			segmentLength = length;
			// sets segment length attribute
		}
		
		
		//Gradient
		public double getAverageGradient() {
			return avgGradient;
			// returns gradient attribute
		}
		
		public void setAverageGradient(double aGrad) {
			avgGradient = aGrad;
			// sets gradient attribute
		}
		
		//Rider Time
		public void setRiderTime(int riderId, LocalTime time) {
			riderTimes.put(riderId, time);
			// enters rider time into segment
		}
		
		
		//toString
		@Override
		public String toString() {
			String str = String.format("Type: %s, Location: %f, Length: %f, AverageGradient: %f", segmentType, segmentLocation, segmentLength, avgGradient);
			return str;
		}
}
