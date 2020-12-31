package synesketch.art.sketch.synemania;

import processing.core.PApplet;
import synesketch.emotion.Emotion;

public class SynemaniaVisualizationAnger extends SynemaniaVisualization {

	public SynemaniaVisualizationAnger(PApplet viz, String className) {
		super(viz, className);
		particles = new SynemaniaAngerParticle[maxAngries];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new SynemaniaAngerParticle();
		}
	}
	
	public class SynemaniaAngerParticle extends SynemaniaParticle {
		
		public SynemaniaAngerParticle() {
			super();
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
			color = palette.getRandomAngerColor();
		}

		public void move() {
			sketch.stroke(255, 8);
			sketch.point(x, y-1);
			float f = 1.0f;
			sketch.stroke(sketch.red(color)*f, sketch.green(color)*f, sketch.blue(color)*f, 42*saturationFactor);
			sketch.point(x, y+1);
			
			x += vx;
			y += vy; 
			vx = speed * PApplet.sin(theta);
			vy = speed * PApplet.cos(theta);
			theta += thetaD;
			if (sketch.random(100) > 95) {
				thetaD += thetaDD;
			}
			speed *= speedD;
			
			if (sketch.random(100) > 98) {
				speedD = 1.0f;
				if (sketch.random(100) > 50) {
					collide();
				}
			} 
			if ((x<-dim) || (x>dim*2) || (y<-dim) || (y>dim*2)) {
				collide();
			}
		}

	}

	@Override
	public int getEmotionType() {
		return Emotion.ANGER;		
	}

}
