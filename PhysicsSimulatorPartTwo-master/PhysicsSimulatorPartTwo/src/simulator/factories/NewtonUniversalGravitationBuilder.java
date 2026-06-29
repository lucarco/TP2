package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;


public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	String type;
	String desc;
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton´s law of universal gravitation");
	}
	
	@Override
	ForceLaws createTheInstance(JSONObject data) {
		double g = 6.67E-11;
		if(data.has("G")) {
			g = data.getDouble("G");
		}
		return new NewtonUniversalGravitation(g);
	}
	
	protected JSONObject createData() {
		JSONObject x = new JSONObject();
		x.put("G", "the gravitational constant (a number)");
		return x;
	}
	
}


