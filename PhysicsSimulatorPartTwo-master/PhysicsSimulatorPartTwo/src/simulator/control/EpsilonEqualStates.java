package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator{
	
	double eps;
	
	public EpsilonEqualStates(double eps) {
		this.eps = eps;
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
	
	public boolean epsEqualArray(JSONArray jsonArray, JSONArray jsonArray2) {
		return (new Vector2D(jsonArray)).distanceTo(new Vector2D(jsonArray2)) <= eps;
	}

	public boolean equal(JSONObject s1, JSONObject s2) {
		
		if(s1.getDouble("time")!=s2.getDouble("time")) {
			return false;
		}
		
		JSONArray ar1 = s1.getJSONArray("bodies");
		JSONArray ar2 = s2.getJSONArray("bodies");
		
		System.out.println(ar1);
		System.out.println(ar2);
		int i = 0;
		while(i < ar1.length()) {
			
			JSONObject body1 = ar1.getJSONObject(i);
			JSONObject body2 = ar2.getJSONObject(i);
			
			if (!(body1.getString("id").equals(body2.getString("id"))) || 
				!epsEqualDouble(body1.getDouble("m"), body2.getDouble("m")) ||
				!epsEqualArray(body1.getJSONArray("v"), body2.getJSONArray("v")) ||
				!epsEqualArray(body1.getJSONArray("p"), body2.getJSONArray("p")) ||
				!epsEqualArray(body1.getJSONArray("f"), body2.getJSONArray("f"))){
				
					return false;
					
			}
				i++;
		}
			
		
		
		
		return true;
	}
		
}
