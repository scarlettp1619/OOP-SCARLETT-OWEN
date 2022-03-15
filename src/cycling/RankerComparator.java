package cycling;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;

public class RankerComparator implements Comparator<Integer> {
	
	HashMap<Integer, LocalTime> compareMap = null;
	
	public RankerComparator(HashMap<Integer, LocalTime> map) {
		compareMap = map;
	}
	
	@Override
	public int compare(Integer rider1, Integer rider2) {
		return compareMap.get(rider1).compareTo(compareMap.get(rider1));
	}
}
