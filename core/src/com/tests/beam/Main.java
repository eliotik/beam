package com.tests.beam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Main extends ApplicationAdapter {
	private Sprites sprites;
	private OrthographicCamera camera;
	private Beam beam;
	private Carousel carousel;
	
	@Override
	public void create() {
		setSprites(new Sprites(this));
		setCamera(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		generateMeshes(MeshType.BEAM);
	}

	private void generateMeshes(MeshType type) {
		switch(type) {
		case CAROUSEL: createCarousel(); break;
		case BEAM: createBeam(); break;
		}
	}

	private void createBeam() {
		setBeam(new Beam(this, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 3, 125, "5f2f32ff", "ffffffff"));
	}

	private void createCarousel() {
		setCarousel(new Carousel(this, 4));
	}
	
	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render() {
		Gdx.graphics.getGL20().glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		createBeam();
		
		getCamera().update();
		if (getCarousel() != null) getCarousel().render();
		if (getBeam() != null) getBeam().render();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		getSprites().dispose();
		if (getCarousel() != null) getCarousel().dispose();
		if (getBeam() != null) getBeam().dispose();
	}

	public Sprites getSprites() {
		return sprites;
	}

	public void setSprites(Sprites sprites) {
		this.sprites = sprites;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public Beam getBeam() {
		return beam;
	}

	public void setBeam(Beam beam) {
		this.beam = beam;
	}

	public Carousel getCarousel() {
		return carousel;
	}

	public void setCarousel(Carousel carousel) {
		this.carousel = carousel;
	}
}
