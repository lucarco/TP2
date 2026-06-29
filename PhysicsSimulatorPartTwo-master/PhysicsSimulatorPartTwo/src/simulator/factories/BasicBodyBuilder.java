package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		super("basic", "Default body");
	}

	Body createTheInstance(JSONObject data) {
		
		return new Body(data.getString("id"), new Vector2D(data.getJSONArray("v").getDouble(0), 
				data.getJSONArray("v").getDouble(1)), new Vector2D(data.getJSONArray("p").getDouble(0),
				data.getJSONArray("p").getDouble(1)), data.getDouble("m"));
	}
	
}
