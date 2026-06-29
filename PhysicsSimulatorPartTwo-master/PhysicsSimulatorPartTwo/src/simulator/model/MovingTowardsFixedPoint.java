package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	
	Vector2D c;
	double g;
	
	public MovingTowardsFixedPoint(Vector2D c, double g) {
		this.c = c;
		this.g = g;
	}
	
	@Override
	public void apply(List<Body> bs) {
		for(Body b : bs) {
			b.addForce(getForce(b));
		}

	}
	
	private Vector2D getForce(Body i) {
		
		Vector2D d = new Vector2D();
		Vector2D force = new Vector2D();
		
		d = c.minus(i.getPosition());
		d = d.direction();
		force = d.scale(i.getMass()*g);
		return force;
	}
	
	public String toString() {
		return "Moving towards "+c+" with constant acceleration "+g;
	}
}