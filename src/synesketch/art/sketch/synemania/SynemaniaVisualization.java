package synesketch.art.sketch.synemania;

import processing.core.PApplet;
import synesketch.art.util.SynesketchVisualization;

public abstract class SynemaniaVisualization extends SynesketchVisualization {
	
	protected String className;
	
	protected static int maxHappies = 600;
	protected static int maxSaddies = 800;
	protected static int maxAngries = 800;
	protected static int maxSurprises = 200;
	protected static int maxFearies = 400;
	protected static int maxDisgusties = 900;
	protected static int maxNeutrals = 30;
	
	protected float saturationFactor = 1.0f;
	
	protected int maxNumberOfParticles, numberOfParticles;
	
	protected SynemaniaParticle particles[];

	protected SynemaniaVisualization(PApplet viz, String className) {
		super(viz);
		this.setClassName(className);
	}

	@Override
	public void draw() {
		int numberOfParticles = Math.round(particles.length * saturationFactor);		
		for (int i = 0; i < numberOfParticles; i++) {			
			try {				
				particles[i].move();				
			} catch (Exception exc) { 
				exc.printStackTrace();
			}
		}
	}
	
	@Override
	public void setWeight(float weight) {
		saturationFactor = (float) Math.sqrt(weight);
		numberOfParticles = Math.round(particles.length * saturationFactor);
	}

	@Override
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
