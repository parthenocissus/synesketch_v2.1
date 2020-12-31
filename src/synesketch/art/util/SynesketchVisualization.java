package synesketch.art.util;

import processing.core.PApplet;

public abstract class SynesketchVisualization {
	
	protected PApplet sketch;
	
	protected String className;
	
	public SynesketchVisualization(PApplet viz) {		
		this.sketch = viz;
		this.className = this.getClassName();
	}
	
	public SynesketchVisualization(PApplet viz, String className) {		
		this.sketch = viz;
		this.className = className;
	}

	public abstract void draw();
	
	public abstract void setWeight(float weight);
	
	public abstract int getEmotionType();
	
	public abstract String getClassName();
	
}
