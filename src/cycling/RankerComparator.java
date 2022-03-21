package cycling;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class RankerComparator implements Comparator<Integer> {
	
	Map<Integer, LocalTime> compareMap;
	
	public RankerComparator(Map<Integer, LocalTime> map) {
		compareMap = map;
	}
	
	@Override
	public int compare(Integer rider1, Integer rider2) {
		long timeDiff = Duration.between(compareMap.get(rider1), compareMap.get(rider2)).toNanos();
		if(timeDiff > 0) {
			return -1;
		}else {
			return 1;
		}
		//return compareMap.get(rider1).compareTo(compareMap.get(rider2));
	}
}
