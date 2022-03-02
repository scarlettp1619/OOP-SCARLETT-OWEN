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
}
