package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator{

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		boolean ok = true;

		
		if(s1.get("time")!=s2.get("time")) {
			return false;
		}
		
		JSONArray ar1 = s1.getJSONArray("object");
		JSONArray ar2 = s2.getJSONArray("object");
		for(int i = 0; i < ar1.length(); i++) {
				if(ar1.getJSONObject(i).get("id") != 
					ar2.getJSONObject(i).get("id") || ar1.getJSONObject(i).get("mass") !=
					ar2.getJSONObject(i).get("mass")) {
					ok = false;
					break;
				}
			
		}
		
		return ok; 
	
	}
	

}
