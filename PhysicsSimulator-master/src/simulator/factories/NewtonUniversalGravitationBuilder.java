package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	
	@Override
	ForceLaws createTheInstance(JSONObject data) {
		if(!data.has("G")) {
			data.put("G", 6.67E-11);
		}
		return new NewtonUniversalGravitation(data.getDouble("G"));
	}

}
