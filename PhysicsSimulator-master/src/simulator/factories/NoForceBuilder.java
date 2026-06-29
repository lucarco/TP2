package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {
	
	@Override
	ForceLaws createTheInstance(JSONObject data) {
		return new NoForce();
	}

}
