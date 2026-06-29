package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{
	
	double eps;
	
	public EpsilonEqualStates(double eps) {
		eps = this.eps;
	}
	
	public boolean epsEqualDouble(double a, double b) {
		
		if(Math.abs(a-b) <= eps) {
		return true;
		} else {
			return false;
		}
	}
	
	public boolean epsEqualVector(Vector2D v1, Vector2D v2) {
		if(v1.distanceTo(v2) <= eps) {
		return true;
		} else {
			return false;
		}
	}

	public boolean equal(JSONObject s1, JSONObject s2) {
		boolean ok = false;
		boolean ok1 = true;
		
		JSONArray ar1 = s1.getJSONArray("object");
		JSONArray ar2 = s2.getJSONArray("object");
		
		
		
		for(int i = 0; i < ar1.length(); i++) {
			
			Vector2D p1 = new Vector2D(ar1.getJSONObject(i).getJSONArray("p"));
			Vector2D p2 = new Vector2D(ar2.getJSONObject(i).getJSONArray("p"));
			
			Vector2D v1 = new Vector2D(ar1.getJSONObject(i).getJSONArray("v"));
			Vector2D v2 = new Vector2D(ar2.getJSONObject(i).getJSONArray("v"));
			
			Vector2D f1 = new Vector2D(ar1.getJSONObject(i).getJSONArray("f"));
			Vector2D f2 = new Vector2D(ar2.getJSONObject(i).getJSONArray("f"));
			
				if(ar1.getJSONObject(i).get("id") != 
					ar2.getJSONObject(i).get("id") || 
					!epsEqualDouble(ar1.getJSONObject(i).getDouble("m"), ar2.getJSONObject(i).getDouble("m"))
					|| !epsEqualVector(p1, p2) || !epsEqualVector(v1, v2) || !epsEqualVector(f1, f2) ) {
					ok1 = false;
					break;
				}
			
		}
		if(s1.get("time")==s2.get("time")) {
			if(ok1)
				ok = true;
		}
		
		return ok;
	}
		
}
