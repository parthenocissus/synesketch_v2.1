package synesketch.art.sketch.hooloovoo;

import processing.core.PApplet;
import synesketch.SynesketchState;
import synesketch.art.sketch.synemania.SynemaniaParticle;
import synesketch.art.util.SynesketchPalette;
import synesketch.art.util.SynesketchVisualization;
import synesketch.emotion.Emotion;
import synesketch.emotion.EmotionalState;

public abstract class HooloovooVisualization extends SynesketchVisualization {
	
	protected String className;
	
	protected float saturationFactor = 1.0f;
	
	protected int maxNumberOfParticles, numberOfParticles;
	
	protected SynesketchPalette palette;

	protected int[] currentPalette;

	protected int[] bwPalette;

	protected int dim = 400;
	protected int size = 40;
	protected int delay = 600;
	protected int trans = 50;
	protected float sat = 1.0f;

	protected HooloovooVisualization(PApplet viz, String className) {
		super(viz);
		this.setClassName(className);
		
		currentPalette = new int[38];
		
		bwPalette = new int[] {-10461088, -7303024, -6579301, -10987432, -7368817, -9868951, 
				-5921371, -10526881, -8421505, -8224126, -6381922, -8224126, -8816263, 
				-10724260, -11645362, -9934744, -5658199, -8947849, -5395027, -6579301, 
				-9868951, -6842473, -11053225, -9276814, -6645094, -8816263, -6710887, 
				-5921371, -10987432, -8092540, -7039852, -7697782, -5789785, -8750470, 
				-10197916, -6381922, -8750470, -5855578};
		
		palette = new SynesketchPalette("standard");
		
		viz.colorMode(PApplet.HSB, 1.0f);
		viz.size(dim, dim);
		viz.noStroke();
		currentPalette = bwPalette;
		
	}

	@Override
	public void draw() {
		//colorMode(RGB);
		//size = 40;
		//delay = 600;
		
	}

	@Override
	public void setWeight(float weight) {
		saturationFactor = (float) Math.sqrt(weight);
		//numberOfParticles = Math.round(particles.length * saturationFactor);
	}

	@Override
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
