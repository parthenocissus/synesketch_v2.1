package synesketch.art.sketch.synemania;

import processing.core.PApplet;
import synesketch.emotion.Emotion;

public class SynemaniaVisualizationDisgust extends SynemaniaVisualization {

	public SynemaniaVisualizationDisgust(PApplet viz, String className) {
		super(viz, className);
		particles = new SynemaniaDisgustParticle[maxDisgusties];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new SynemaniaDisgustParticle();
		}
	}
	
	public class SynemaniaDisgustParticle extends SynemaniaParticle {
		
		public SynemaniaDisgustParticle() {
			super();		
		}
		
		public void collide() {
			x = dim/2;
			y = dim/2;
			theta = sketch.random(PApplet.TWO_PI);
			speed = sketch.random(1, 6);
			speedD = sketch.random(0.95f, 1);
			thetaD = 0;
			thetaDD = 0;
			color = palette.getRandomDisgustColor();

			while (PApplet.abs(thetaDD) < 0.00001) {
				thetaDD = sketch.random(-0.001f, 0.001f);
			}
		}

		public void move() {
			
			sketch.stroke(sketch.red(color), sketch.green(color), sketch.blue(color), 20*saturationFactor);
			sketch.point(x,y);
			sketch.stroke(sketch.random(100, 200), 7*saturationFactor);
			sketch.point(x,y-1);
			sketch.stroke(0, 25*saturationFactor);
			sketch.point(x,dim - y);

			x += vx;
			y += vy;
			vx = speed * PApplet.sin(theta);
			vy = speed * PApplet.cos(theta);
			theta += thetaD;
			thetaD += thetaDD;
			if (sketch.random(100) > 90) {
				speed *= speedD;
				speedD *= 0.999999;
			}			


			if (sketch.random(1000)>995) {
				speed*=-1;
				speedD=2-speedD;
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
		return Emotion.DISGUST;		
	}

}
