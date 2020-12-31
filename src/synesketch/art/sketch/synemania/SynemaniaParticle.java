package synesketch.art.sketch.synemania;

import synesketch.art.util.SynesketchPalette;

public abstract class SynemaniaParticle {
	
	protected int dim = 500;
	
	protected SynesketchPalette palette;
	
	protected float saturationFactor = 1.0f;
	
	protected int color;
	protected float x, y, vx, vy, theta, 
		speed, speedD, thetaD, thetaDD;
	
	public SynemaniaParticle() {
		x = dim/2;
		y = dim/2;
		palette = new SynesketchPalette("standard");
	}
	
	public abstract void collide();
	
	public abstract void move();

}
