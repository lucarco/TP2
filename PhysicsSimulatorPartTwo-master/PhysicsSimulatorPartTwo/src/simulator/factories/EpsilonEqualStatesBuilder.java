package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {
	
	

	public EpsilonEqualStatesBuilder() {
		super("epseq", "epsilon equal states");
	}

	StateComparator createTheInstance(JSONObject data) {
		double eps = 0.0;
		if(data.has("eps")) {
			eps = data.getDouble("eps");
		}
		return new EpsilonEqualStates(eps);
	}
	
}


