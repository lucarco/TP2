package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	
	
	
	@Override
	ForceLaws createTheInstance(JSONObject data) {
		if(!data.has("c")) {
			Vector2D c = new Vector2D();
			data.put("c", c);
		}
		if(!data.has("g")) {
			data.put("g", 9.81);
		}
		return new MovingTowardsFixedPoint(new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1)), data.getDouble("g"));
	}

}
