package cycling;

import java.time.LocalTime;
import java.util.HashMap;

public class Segment {
	
	SegmentType segmentType;
	double segmentLocation;
	double segmentLength;
	double avgGradient;
	
	HashMap<Integer, LocalTime> riderTimes =  new HashMap<Integer, LocalTime>();
	
	public Segment(double loc, SegmentType type, double aGrad, double length){
		segmentLocation = loc;
		segmentType = type;
		avgGradient = aGrad;
		segmentLength = length;
		System.out.println("Segment has been constructed. " + this);
	}
	
		//Type
		public SegmentType getSegmentType() {
			return segmentType;
		}
		
		public SegmentType setSegmentType(SegmentType type) {
			return segmentType = type;
		}
		
		
		//Description
		public double getSegmentLocation() {
			return segmentLocation;
		}
		
		public void setSegmentLocation(double location) {
			segmentLocation = location;
		}
		
		
		//Length
		public double getSegmentLength() {
			return segmentLength;
		}
		
		public void setSegmentLength(double length) {
			segmentLength = length;
		}
		
		
		//Gradient
		public double getAverageGradient() {
			return avgGradient;
		}
		
		public void setAverageGradient(double aGrad) {
			avgGradient = aGrad;
		}
		
		//Rider Time
		public void setRiderTime(int riderId, LocalTime time) {
			riderTimes.put(riderId, time);
		}
		
		
		//toString
		@Override
		public String toString() {
			String str = String.format("Type: %s, Location: %f, Length: %f, AverageGradient: %f", segmentType, segmentLocation, segmentLength, avgGradient);
			return str;
		}
}
