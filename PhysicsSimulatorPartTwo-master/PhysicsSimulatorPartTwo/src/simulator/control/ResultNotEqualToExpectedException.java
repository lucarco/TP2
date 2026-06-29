package simulator.control;

import org.json.JSONObject;

public class ResultNotEqualToExpectedException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String message = "State is not equal to expected one.";
	
	public ResultNotEqualToExpectedException(JSONObject jsonObject, JSONObject jsonObject2, int i) {
		super(message + "Different states: \n" + jsonObject + "\n" + jsonObject2 + "\nNumber of the execution step: " + i);
	}

}
