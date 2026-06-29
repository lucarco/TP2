package simulator.model;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body {
		
	private double lFactor;
	private double lFrequency;
	private double c = 0.0;

	public MassLossingBody(String id, Vector2D v,Vector2D p, double m, double lFrequency, double lFactor) {
		super(id, v, p, m);
		this.lFactor = lFactor;
		this.lFrequency = lFrequency;
	}
	
	@Override
	void move(double t) {
		
		super.move(t);
		
		c += t;
		
		if(c >= lFrequency) {
			m = m * (1 - lFactor);
			c = 0.0;
		}
		
	}
	
}
