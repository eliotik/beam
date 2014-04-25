package com.tests.beam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Carousel {
	private Array<Beam> beams = new Array<Beam>();
	
	public Carousel(final Main main, int quantity) {
		int localAngle = 360;
		if (quantity <= 0) quantity = 1;
		float angleStep = (int)(localAngle / quantity);
        
		while(localAngle > 0) {
			Beam beam = new Beam(main, Gdx.graphics.getWidth() / 2,
					Gdx.graphics.getHeight() / 2, 3, localAngle, "5f2f32ff", "ffffffff");
			beams.add(beam);
        	localAngle -= angleStep;
        }
        
		Timer.schedule(new Task() {
			@Override
			public void run() {
				for(int i = 0, l = beams.size; i < l; ++i) {
					Beam beam = beams.get(i);
					int angle = beam.getRotation()-1;
					if (angle <= 0) angle = 360;
					beam.update(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 3, angle);
				}
			}
		}, 0, 1 / 25.0f);
	}
	
	public void render() {
		for(int i = 0, l = beams.size; i < l; ++i) beams.get(i).render();
	}
	
	public void dispose() {
		for(int i = 0, l = beams.size; i < l; ++i) beams.get(i).dispose();
	}
}
