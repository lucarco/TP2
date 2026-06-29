package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	

	public MassLosingBodyBuilder() {
		super("mlb", "Mass loosing body");
	}

	@Override
	Body createTheInstance(JSONObject data) {
		
		return new MassLossingBody(data.getString("id"),new Vector2D(data.getJSONArray("v").getDouble(0), 
				data.getJSONArray("v").getDouble(1)), new Vector2D(data.getJSONArray("p").getDouble(0),
				data.getJSONArray("p").getDouble(1)), data.getDouble("m"),
				data.getDouble("freq"), data.getDouble("factor") );
	}

}
