package synesketch.art.sketch.synemania;

import processing.core.PApplet;
import synesketch.emotion.Emotion;

public class SynemaniaVisualizationHappiness extends SynemaniaVisualization {
	
	public SynemaniaVisualizationHappiness(PApplet viz, String className) {
		super(viz, className);
		particles = new SynemaniaHappinessParticle[maxHappies];
		for (int i = 0; i < particles.length; i++) {
			particles[i] = new SynemaniaHappinessParticle();
		}
	}
	
	public class SynemaniaHappinessParticle extends SynemaniaParticle {
		
		public SynemaniaHappinessParticle() {
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
			color = palette.getRandomHappinessColor();
		}
		
		public void move() {
			sketch.stroke(sketch.red(color), sketch.green(color), sketch.blue(color), 
					30*saturationFactor);
			sketch.point(x,y-1);
			sketch.stroke(0, 25*saturationFactor);
			sketch.point(x,y+1);

			x+=vx;
			y+=vy;
			vx = speed*PApplet.sin(theta); 
			vy = speed*PApplet.cos(theta);
			theta+=thetaD;
			thetaD+=thetaDD;
			speed*=speedD;

			if (sketch.random(1000)>997) {
				speedD = 1.0f;
				thetaDD = 0.00001f;
				if (sketch.random(100)>70) {
					x = dim/2;
					y = dim/2;
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
		return Emotion.HAPPINESS;		
	}

}
