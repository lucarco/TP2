package simulator.model;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body {
		
	private double lFactor;
	private double lFrequency;
	private double c = 0.0;

	public MassLossingBody(String id, Vector2D v,Vector2D p, double m, double lFactor, double lFrequency) {
		super(id, v, p, m);
		this.lFactor = lFactor;
		this.lFrequency = lFrequency;
	}
	
	@Override
	void move(double t) {
		c += t;
		if(m==0) {
			a = a.scale(0);
		} else {
			a = f.scale(1/m);
		}
		p = (p.plus(v.scale(t))).plus(a.scale(0.5*t*t));
		
		
		
		if(c >= lFrequency) {
			m = m * (1 - lFactor);
			c = 0.0;
		}
		
		v = v.plus(a.scale(t));
		
		
	}
	
}
