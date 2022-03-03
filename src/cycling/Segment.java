package cycling;

public class Segment {
	
	SegmentType segmentType;
	double segmentLocation;
	double segmentLength;
	double avgGradient;
	
	public Segment(double loc, SegmentType type, double aGrad, double length){
		segmentLocation = loc;
		segmentType = type;
		avgGradient = aGrad;
		segmentLength = length;
		System.out.println("Segment has been constructed.");
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
}
