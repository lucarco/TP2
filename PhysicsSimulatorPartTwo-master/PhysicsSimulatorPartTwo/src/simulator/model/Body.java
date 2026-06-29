package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {

protected String id;

protected Vector2D p, v;
Vector2D f = new Vector2D();
protected double m;
Vector2D a = new Vector2D();

public Body(String id, Vector2D v,Vector2D p, double m){
this.id = id;
this.v = v;
this.p = p;
this.m = m;
}

public String getId(){
	return id;
}

public Vector2D getVelocity(){
	return v;
}

public Vector2D getPosition(){
	return p;
}

public Vector2D getForce(){
	return f;
}

public double getMass(){
	return m;
}

void addForce(Vector2D force) {
	f = f.plus(force);
}
void resetForce() {
	f = new Vector2D();
}
void move(double t) {

	if(m<=0) {
		a = a.scale(0);
	} else {
		a = f.scale(1.0/m);
	}
	p = p.plus((v.scale(t).plus(a.scale(0.5*t*t))));
	v = v.plus(a.scale(t));
}



public JSONObject getState() {
	
	JSONObject jo1 = new JSONObject();

	jo1.put("id", id);

	jo1.put("m", m);

	jo1.put("p", p.asJSONArray());
	jo1.put("v", v.asJSONArray());
	jo1.put("f", f.asJSONArray());
	
	return jo1;
}



@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Body other = (Body) obj;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	return true;
}

public String toString() {
	return getState().toString();
}

}