package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {

String id;
Vector2D v;
Vector2D p;
Vector2D f = new Vector2D();
double m;
Vector2D a;

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

void addForce(Vector2D f) {
	this.f.plus(f);
}
void resetForce() {
	this.f.minus(this.f);
}
void move(double t) {
	if(m==0) {
		a = a.scale(0);
	} else {
		a = f.scale(1/m);
	}
	p = (p.plus(v.scale(t))).plus(a.scale(0.5*t*t));
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

public String toString() {
	return getState().toString();
}

}