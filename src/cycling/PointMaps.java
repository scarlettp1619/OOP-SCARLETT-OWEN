package cycling;

import java.util.HashMap;

//exist to make changing score easier

@SuppressWarnings("serial")
public class PointMaps {
	
	HashMap<Integer, Integer> flatFinishScores = new HashMap<Integer, Integer>(){{ //first place is 0 for easy array stuff
		put(0, 50); put(1, 30); put(2, 20); put(3, 18); 
		put(4, 16); put(5, 14); put(6, 12); put(7, 10);
		put(8, 8); put(9, 7); put(10, 6); put(11, 5);
		put(12, 4); put(13, 3); put(14, 2);
	}};
	// sets points for flat finish scores
	
	HashMap<Integer, Integer> hillyFinishScores = new HashMap<Integer, Integer>(){{
		put(0, 30); put(1, 25); put(2, 22); put(3, 19); 
		put(4, 17); put(5, 15); put(6, 13); put(7, 11);
		put(8, 9); put(9, 7); put(10, 6); put(11, 5);
		put(12, 4); put(13, 3); put(14, 2);
	}};
	// sets points for hilly finish scores
	
	HashMap<Integer, Integer> highFinishScores = new HashMap<Integer, Integer>(){{ //same scorings as time trial
		put(0, 20); put(1, 17); put(2, 15); put(3, 13); 
		put(4, 11); put(5, 10); put(6, 9); put(7, 8);
		put(8, 7); put(9, 6); put(10, 5); put(11, 4);
		put(12, 3); put(13, 2); put(14, 1);
	}};
	// sets points for high finish scores
	
	HashMap<Integer, Integer> c4Scores = new HashMap<Integer, Integer>(){{
		put(0, 1);
	}};
	
	HashMap<Integer, Integer> c3Scores = new HashMap<Integer, Integer>(){{
		put(0, 2); put(1, 1);
	}};
	
	HashMap<Integer, Integer> c2Scores = new HashMap<Integer, Integer>(){{
		put(0, 5); put(1, 3); put(2, 2); put(3, 1);
	}};
	
	HashMap<Integer, Integer> c1Scores = new HashMap<Integer, Integer>(){{
		put(0, 10); put(1, 8); put(2, 6); put(3, 4); 
		put(4, 2); put(5, 1);
	}};
	
	HashMap<Integer, Integer> hcScores = new HashMap<Integer, Integer>(){{
		put(0, 20); put(1, 15); put(2, 12); put(3, 10); 
		put(4, 8); put(5, 6); put(6, 4); put(7, 2);
	}};
	
	
	public HashMap<Integer, Integer> getScorings(StageType scoreType){
		switch(scoreType) {
		case FLAT:
			return flatFinishScores;
		case MEDIUM_MOUNTAIN:
			return hillyFinishScores;
		case HIGH_MOUNTAIN:
			return highFinishScores;
		case TT:
			return highFinishScores;
		default:
			return null;
		}
	}
	
	public HashMap<Integer, Integer> getScorings(SegmentType scoreType){
		switch(scoreType) {
		case C4:
			return c4Scores;
		case C3:
			return c3Scores;
		case C2:
			return c2Scores;
		case C1:
			return c1Scores;
		case HC:
			return hcScores;
		default:
			return null;
		}
	}
}
