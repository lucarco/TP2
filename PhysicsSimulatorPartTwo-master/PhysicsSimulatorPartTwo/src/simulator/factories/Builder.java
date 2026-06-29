package simulator.factories;

import org.json.JSONObject;	

public abstract class Builder<T> {
	
	protected String type;
	protected String desc;
	
	public Builder(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

	public T createInstance(JSONObject info) {

		T x;
		if (this.type.equals(info.getString("type"))) {
			x = createTheInstance(info.getJSONObject("data"));
			return x;

		} else
			return null;
	}

	abstract T createTheInstance(JSONObject data);

	protected JSONObject createData() {
		return new JSONObject();
	}


	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", type);
		info.put("desc", desc);
		info.put("data", createData());
		
		return info;

	}

}
