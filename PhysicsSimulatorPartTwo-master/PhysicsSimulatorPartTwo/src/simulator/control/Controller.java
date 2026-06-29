package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.List;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	PhysicsSimulator p;
	Factory<Body> f;
	Factory<ForceLaws> fl;
	
	public Controller(PhysicsSimulator p, Factory<Body> f, Factory<ForceLaws> fl) {
		this.p = p;
		this.f = f;
		this.fl = fl;
	}
	
	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray a = jsonInput.getJSONArray("bodies");
		for(int i=0; i<a.length(); i++) {
			p.addBody(f.createInstance(a.getJSONObject(i)));
		}
		
	}
	
public void run(int n) {
	for(int i=0; i<n; i++) {
		p.advance();
	}
}

	
public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws ResultNotEqualToExpectedException {
		
		int i = 0;
		JSONArray a = null;
		
		if(expOut != null) {
			JSONObject jsonInput = new JSONObject(new JSONTokener(expOut));
			a = jsonInput.getJSONArray("states");
		}
		
		PrintStream s = new PrintStream(out);
		s.println("{");
		s.println("\"states\": [");
		if(a != null && !cmp.equal(a.getJSONObject(0), p.getState())) {
			throw new ResultNotEqualToExpectedException(a.getJSONObject(0), p.getState(), i);
		}
		s.println(p.getState());
		for(i=1; i<=n; i++) {
			p.advance();
			JSONObject x = p.getState();
			if(a != null && !cmp.equal(a.getJSONObject(i), x)) {
				throw new ResultNotEqualToExpectedException(a.getJSONObject(i), p.getState(), i);
			}
			s.println(",");
			s.println(x);
		}
		s.println("]");
		s.println("}");
	}

public void reset() {
	p.reset();
}

public void setDeltaTime(Double dt) {
	p.setDeltaTime(dt);
}

public void addObserver(SimulatorObserver o) {
	p.addObserver(o);
}

public List<JSONObject> getForceLawsInfo(){
	return fl.getInfo();
}

public void setForcesLaws(JSONObject info) {
	ForceLaws x = fl.createInstance(info);
	p.setForceLaws(x);
}
	
}
