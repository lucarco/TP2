package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	
	double g;
	
	public NewtonUniversalGravitation(double g) {
		this.g = g;
	}
	@Override
	
	public void apply(List<Body> bs) {
					for (Body b: bs) {
						 for(Body bo: bs){
							 if(b != bo)
								 b.addForce(getForce(b, bo));
			}
		}
	}
	
	public Vector2D getForce(Body i, Body j) {
		Vector2D d;
		Vector2D objForce;
		double f;
		double m;
		
		objForce = new Vector2D();
		
		d = j.getPosition().minus(i.getPosition()); //Calculate the distance between the two bodies
		m = d.magnitude() * d.magnitude(); //calculate the magnitude between the two bodies ^ 2
		
		if(m > 0)
			f = g * ((i.getMass() * j.getMass())/m); //formula given to us
		else {
			f = 0.0;
		}
		d = d.direction();
		objForce = d.scale(f); //formula given to us, (dx * f, dy * f)
		
		return objForce;
	}
	
	public String toString() {
		return "Newton’s Universal Gravitation with G="+g;
	}
	

}
	