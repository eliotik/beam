package com.tests.beam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Carousel {
	private Array<Beam> beams = new Array<Beam>();
	private Array<Integer> sizes = new Array<Integer>();
	
	public Carousel(final Main main, int quantity) {
		for(int i = 0; i <= quantity; ++i){
			getSizes().add(i+1);	
		}

		int localAngle = 360;
		if (quantity <= 0) quantity = 1;
		float angleStep = (int)(localAngle / quantity);
        int beamNumber = 0;
		while(localAngle > 0) {
			Beam beam = new Beam(main, Gdx.graphics.getWidth() / 2,
					Gdx.graphics.getHeight() / 2, getSizes().get(beamNumber), localAngle, "5f2f32ff", "ffffffff");
			getBeams().add(beam);
        	localAngle -= angleStep;
        	++beamNumber;
        }

		Timer.schedule(new Task() {
			@Override
			public void run() {
				for(int i = 0, l = getBeams().size; i < l; ++i) {
					Beam beam = getBeams().get(i);
					int angle = beam.getRotation()-1;
					if (angle <= 0) angle = 360;
					beam.setRotation(angle);
					beam.update();
				}
			}
		}, 0, 1 / 25.0f);
	}
	
	public void render() {
		for(int i = 0, l = getBeams().size; i < l; ++i) getBeams().get(i).render();
	}
	
	public void dispose() {
		for(int i = 0, l = getBeams().size; i < l; ++i) getBeams().get(i).dispose();
	}

	public Array<Integer> getSizes() {
		return sizes;
	}

	public void setSizes(Array<Integer> sizes) {
		this.sizes = sizes;
	}

	public Array<Beam> getBeams() {
		return beams;
	}

	public void setBeams(Array<Beam> beams) {
		this.beams = beams;
	}
}
