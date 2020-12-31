package synesketch.art.sketch.synemania;

import processing.core.PApplet;
import synesketch.emotion.Emotion;

public class SynemaniaVisualizationFearAlt extends SynemaniaVisualization {

	public SynemaniaVisualizationFearAlt(PApplet viz, String className) {
		super(viz, className);
		particles = new SynemaniaFearParticle[maxFearies*20];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new SynemaniaFearParticle();
		}
	}
	
	public class SynemaniaFearParticle extends SynemaniaParticle {
		
		public SynemaniaFearParticle() {
			super();		
		}
		
		public void collide() {
			theta = sketch.random(PApplet.TWO_PI);
			speed = sketch.random(0.5f, 3.5f);
			speedD = sketch.random(0.996f, 1.001f);
			thetaD = 0;
			thetaDD = 0;
			color = palette.getRandomHappinessColor();

			while (PApplet.abs(thetaDD)<0.00001) {
				thetaDD = sketch.random(-0.001f, 0.001f);
			}
		}

		public void move() {
			sketch.stroke(sketch.red(color), sketch.green(color), sketch.blue(color), 50*saturationFactor);
			sketch.point(x, y);
			sketch.stroke(0, 30*saturationFactor);
			sketch.point(x, y-1);
			sketch.stroke(255, 20*saturationFactor);
			sketch.point(x, y+1);

			x += vx;
			y += vy;
			vx = speed * PApplet.sin(theta);
			vy = speed * PApplet.cos(theta);

			if (sketch.random(1000) > 950) {
				speedD = 1.0f;
				thetaDD = 0.00001f;
				if (sketch.random(100)>70) {
					collide();
				}
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
		return Emotion.FEAR;		
	}

}
