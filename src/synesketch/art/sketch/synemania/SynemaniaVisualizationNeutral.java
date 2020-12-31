package synesketch.art.sketch.synemania;

import processing.core.PApplet;
import synesketch.emotion.Emotion;

public class SynemaniaVisualizationNeutral extends SynemaniaVisualization {

	public SynemaniaVisualizationNeutral(PApplet viz, String className) {
		super(viz, className);
		particles = new SynemaniaNeutralParticle[maxNeutrals];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new SynemaniaNeutralParticle();			
		}
	}
	
	public class SynemaniaNeutralParticle extends SynemaniaParticle {
		
		float gray;
		
		public SynemaniaNeutralParticle() {
			super();
			gray = sketch.random(255);			
		}
		
		public void collide() {			
			x = dim/2;
			y = dim/2;
			theta = sketch.random(PApplet.TWO_PI);
			speed = sketch.random(0.5f, 3.5f);
			speedD = sketch.random(0.996f, 1.001f);
			thetaD = 0;
			thetaDD = 0;
			while (PApplet.abs(thetaDD)<0.00001) {
				thetaDD = sketch.random(-0.001f, 0.001f);
			}
		}

		public void move() {
			sketch.stroke(gray, 28);
			sketch.point(x, y-1);
			x += vx;
			y += vy;
			vx = speed * PApplet.sin(theta);
			vy = speed * PApplet.cos(theta);
			if (sketch.random(1000) > 990) {
				x = dim/2;
				y = dim/2;
				collide();
			}
			if ((x<-dim) || (x>dim*2) || (y<-dim) || (y>dim*2)) {
				x = dim/2;
				y = dim/2;
				collide();
			}
		}

	}
	
	@Override
	public int getEmotionType() {
		return Emotion.NEUTRAL;		
	}

}
