package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	private List<Body> bs = new ArrayList<Body>();
	double time;
	ForceLaws forceLaw;
	double currentTime = 0.0;
	
	public PhysicsSimulator(double time, ForceLaws forceLaw) {
		if(time <= 0) {
			throw new IllegalArgumentException("The value of time is non-positive");
		} 
		if(forceLaw == null) {
			throw new IllegalArgumentException("The value of force law is not valid");
		}
		this.time = time;
		this.forceLaw = forceLaw;
	}
	
	public void advance() {
		int i;
		for(i=0; i<bs.size(); i++) {
			bs.get(i).resetForce();
		}
		
		forceLaw.apply(bs);
		
		for(i=0; i<bs.size(); i++) {
			bs.get(i).move(time);
		}
		currentTime += time;
		
	}
	
	public void addBody(Body b) {
		
		boolean found = false;
		for(int i=0; i<bs.size(); i++) {
			if(b.getId()==bs.get(i).getId()) {
				found = true;
			}
		}
		if(found) {
			throw new IllegalArgumentException("There is already a body with this id");
		}
		
		bs.add(b);
		
	}
	
	public JSONObject getState() {
		
		JSONObject jo1 = new JSONObject();

		jo1.put("time", currentTime);
		JSONArray a = new JSONArray();
		
		for(int i=0; i < bs.size(); i++) {
			a.put(bs.get(i).getState());
		}
		jo1.put("bodies", a);

		
		return jo1;
		

	}
	
	public String toString() {
		return getState().toString();
	}
	
	
	
	

}
