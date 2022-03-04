package cycling;

public enum StageState {
	
	/**
	* Stage is currently under preparation
	* Segments can be added but no results can be
	*/
	UNDER_PREPARATION,
	
	/**
	 * Stage is currently waiting for results
	 * Segments can no longer be added but results can be
	 */
	WAITING_FOR_RESULTS,
	
	/**
	 * Stage is complete
	 * Neither Segments nor results can be added
	 */
	COMPLETE;
}
