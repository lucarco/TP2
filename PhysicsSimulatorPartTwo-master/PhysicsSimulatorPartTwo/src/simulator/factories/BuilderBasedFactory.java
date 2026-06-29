package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	
	List<Builder<T>> _builders;
	List<JSONObject> _info;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		
		_builders = new ArrayList<>(builders);
		_info = new ArrayList<>();
		for(Builder<T> b : _builders) {
			_info.add(b.getBuilderInfo());
		}
		
	}
	
	
	@Override
	public T createInstance(JSONObject info) {
		
		for(Builder<T> b : _builders) {
			T x = b.createInstance(info);
			if ( x != null) {
				return x;
			}
		}
		return null;
	}

	@Override
	public List<JSONObject> getInfo() {
		return _info;
	}

}
