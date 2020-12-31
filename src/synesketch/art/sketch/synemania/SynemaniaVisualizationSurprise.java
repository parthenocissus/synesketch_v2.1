package synesketch.art.sketch.synemania;

import processing.core.PApplet;
import synesketch.emotion.Emotion;

public class SynemaniaVisualizationSurprise extends SynemaniaVisualization {

	public SynemaniaVisualizationSurprise(PApplet viz, String className) {
		super(viz, className);
		particles = new SynemaniaSurpriseParticle[maxSaddies];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new SynemaniaSurpriseParticle();
		}
	}
	
	public class SynemaniaSurpriseParticle extends SynemaniaParticle {
		
		public SynemaniaSurpriseParticle() {
			super();		
		}
		
		public void collide() {
			x = dim/2;
			y = dim/2;
			theta = sketch.random(PApplet.TWO_PI);
			speed = sketch.random(1.0f, 6.0f);

			speedD = sketch.random(0.95f, 1.001f);
			thetaD = 0;
			thetaDD = 0;
			color = palette.getRandomSurpriseColor();

			while (PApplet.abs(thetaDD)<0.00001) {
				thetaDD = sketch.random(-0.001f, 0.001f);
			}
		}

		public void move() {
			sketch.stroke(sketch.red(color), sketch.green(color), sketch.blue(color), 50*saturationFactor);
			sketch.point(x, y);
			sketch.stroke(0, 25*saturationFactor);
			sketch.point(x, y+1);
			for (int dy=1; dy<3; dy++) {
				sketch.stroke(sketch.red(color), sketch.green(color), sketch.blue(color), (80-dy*4)*saturationFactor);
				sketch.point(x, y-dy);
			}

			x += vx;
			y += vy;
			vx = speed*PApplet.sin(theta);
			vy = speed*PApplet.cos(theta);
			theta+=thetaD;

			thetaD += thetaDD;
			speed *= speedD;
			speedD *= 0.9999f;
			
			if (sketch.random(1000) > 980) {
				speed *= -1;
				speedD = 2-speedD;
				if (sketch.random(100)>30) {
					x = dim/2;
					y = dim/2;
					collide();
				}
			}
		}

	}
	
	@Override
	public int getEmotionType() {
		return Emotion.SURPRISE;		
	}

}
