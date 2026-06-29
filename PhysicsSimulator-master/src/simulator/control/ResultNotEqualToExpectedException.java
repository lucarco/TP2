package simulator.control;

import org.json.JSONObject;

public class ResultNotEqualToExpectedException extends Exception {
	
	private static final String message = "State is not equal to expected one.";
	
	public ResultNotEqualToExpectedException(JSONObject jsonObject, JSONObject jsonObject2, int i) {
		super(message + "Different states: " + jsonObject + "," + jsonObject2 + ". Number of the execution step: " + i);
	}

}
