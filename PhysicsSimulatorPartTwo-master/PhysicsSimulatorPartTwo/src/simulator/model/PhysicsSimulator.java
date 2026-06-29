package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver> {
	
	private List<Body> bs = new ArrayList<Body>();
	double time;
	ForceLaws forceLaw;
	double currentTime = 0.0;
	private List<SimulatorObserver> so = new ArrayList<SimulatorObserver>();
	
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
		for(Body b : bs) {
			b.resetForce();
		}
		
		forceLaw.apply(bs);
		
		for(Body b: bs) {
			b.move(time);
		}
		currentTime += time;
		
		for(SimulatorObserver x : so) {
			x.onAdvance(bs, currentTime);
		}
		
	}
	
	public void addBody(Body b) {
		
		boolean found = false;
				if(bs.contains(b)){
				found = true;
			}
		if(found) {
			throw new IllegalArgumentException("There is already a body with this id");
		}
		
		bs.add(b);
		
		for(SimulatorObserver x : so) {
			x.onBodyAdded(bs, b);
		}
	}
	
	public JSONObject getState() {
		
		JSONObject jo1 = new JSONObject();

		jo1.put("time", currentTime);
		JSONArray a = new JSONArray();
		
		for(Body b : bs) {
			a.put(b.getState());
		}
		jo1.put("bodies", a);

		
		return jo1;
		

	}
	
	public void reset() {
		bs.clear();
		currentTime = 0.0;
		for(SimulatorObserver x : so) {
			x.onReset(bs, currentTime, time, forceLaw.toString());
		}
	}
	
	public void setDeltaTime(double dt) {
		if(dt <= 0) {
			throw new IllegalArgumentException("The value of time is non-positive");
		} 
		this.time = dt;
		for(SimulatorObserver x : so) {
			x.onDeltaTimeChanged(time);
		}
	}
	
	public void setForceLaws(ForceLaws forceLaws) {
		if(forceLaws == null) {
			throw new IllegalArgumentException("The value of force law is not valid");
		}
		this.forceLaw = forceLaws;
		for(SimulatorObserver x : so) {
			x.onForceLawsChanged(forceLaw.toString());
		}
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public void addObserver(SimulatorObserver o) {
		if(!so.contains(o)) {
			so.add(o);		
			}
		o.onRegister(bs, currentTime, time, forceLaw.toString());
		
	}
	
	
	
	

}
