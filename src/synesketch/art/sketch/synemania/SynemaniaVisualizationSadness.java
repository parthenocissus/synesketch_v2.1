package synesketch.art.sketch.synemania;

import processing.core.PApplet;
import synesketch.emotion.Emotion;

public class SynemaniaVisualizationSadness extends SynemaniaVisualization {
	
	public SynemaniaVisualizationSadness(PApplet viz, String className) {
		super(viz, className);
		particles = new SynemaniaSadnessParticle[maxSaddies];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new SynemaniaSadnessParticle();
		}
	}

	private class SynemaniaSadnessParticle extends SynemaniaParticle {
		
		private float sadTheta;
		
		public SynemaniaSadnessParticle() {			
			collide();
			sadTheta = sketch.random(PApplet.TWO_PI);
		}
		
		public void collide() {
			x = dim/2;
			y = dim/2;
			speed = sketch.random(2,32);
			speedD = sketch.random(0.0001f, 0.001f);
			theta = sadTheta + sketch.random(-0.1f, 0.1f);
			thetaD = 0;
			thetaDD = 0;

			while (PApplet.abs(thetaDD) < 0.001) {
				thetaDD = sketch.random(-0.1f, 0.1f);
			}

			color = palette.getRandomSadnessColor();
		}

		public void move() {
			int mya = 0;			
			sketch.stroke(sketch.red(color), sketch.green(color), sketch.blue(color), 42*saturationFactor);
			sketch.point(x,y);
			sketch.stroke(sketch.red(mya), sketch.green(mya), sketch.blue(mya), 5*saturationFactor);
			sketch.point(x, y);
			sketch.stroke(sketch.red(mya), sketch.green(mya), sketch.blue(mya), 15*saturationFactor);
			sketch.point(dim-x, y);
			
			x+=speed*PApplet.sin(theta);
			y+=speed*PApplet.cos(theta);
			theta+=thetaD;
			thetaD+=thetaDD;
			speed-=speedD;

			if ((x<-dim) || (x>dim*2) || (y<-dim) || (y>dim*2)) {
				collide();
			}
		}

	}
	
	@Override
	public int getEmotionType() {
		return Emotion.SADNESS;		
	}

}
