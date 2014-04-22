package com.tests.beam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Beam extends ApplicationAdapter {
	private TextureAtlas beam;

	private OrthographicCamera camera;

	private MeshHelper meshEndBackgroundHelper;
	private MeshHelper meshEndOverlayHelper;
	private Sprite spriteEndBackground;
	private Sprite spriteEndOverlay;
	private MeshHelper meshStartBackgroundHelper;
	private MeshHelper meshStartOverlayHelper;
	private Sprite spriteStartBackground;
	private Sprite spriteStartOverlay;
	private MeshHelper meshMiddleBackgroundHelper;
	private MeshHelper meshMiddleOverlayHelper;
	private Sprite spriteMiddleBackground;
	private Sprite spriteMiddleOverlay;

	public static int angle = 125;
	public static boolean started = false;

	@Override
	public void create () {
		setBeam(new TextureAtlas(Gdx.files.internal("beam/beam.atlas")));

		setSpriteEndBackground(getBeam().createSprite("end_background"));
		setSpriteEndOverlay(getBeam().createSprite("end_overlay"));
		setSpriteStartBackground(getBeam().createSprite("start_background"));
		setSpriteStartOverlay(getBeam().createSprite("start_overlay"));
		setSpriteMiddleBackground(getBeam().createSprite("middle_background"));
		setSpriteMiddleOverlay(getBeam().createSprite("middle_overlay"));

		setCamera(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		setMeshEndBackgroundHelper(new MeshHelper(this, getSpriteEndBackground()));
		getMeshEndBackgroundHelper().createMesh();
		setMeshEndOverlayHelper(new MeshHelper(this, getSpriteEndOverlay()));
		getMeshEndOverlayHelper().createMesh();
		setMeshStartBackgroundHelper(new MeshHelper(this, getSpriteStartBackground()));
		getMeshStartBackgroundHelper().createMesh();
		setMeshStartOverlayHelper(new MeshHelper(this, getSpriteStartOverlay()));
		getMeshStartOverlayHelper().createMesh();
		setMeshMiddleBackgroundHelper(new MeshHelper(this, getSpriteMiddleBackground()));
		getMeshMiddleBackgroundHelper().createMesh();
		setMeshMiddleOverlayHelper(new MeshHelper(this, getSpriteMiddleOverlay()));
		getMeshMiddleOverlayHelper().createMesh();

		final int startX = 250;
        final int startY = 150;
        final int width = 64;
        final int height = 64;
        final int sizeMultiplier = 3;

        getMeshStartBackgroundHelper().constructMesh( getSpriteStartBackground(),
        		1,
        		startX, startY,
        		0, 0,
        		width, height,
        		1, 1,
        		Beam.angle,
        		Color.valueOf("30e957ff"),
        		255);
        getMeshStartOverlayHelper().constructMesh( getSpriteStartOverlay(),
        		1,
        		startX, startY,
        		0, 0,
        		width, height,
        		1, 1,
        		Beam.angle,
        		Color.valueOf("ffffffff"),
        		0);

		getMeshMiddleBackgroundHelper().constructMesh( getSpriteMiddleBackground(),
				1,
				startX+52.5f*sizeMultiplier, startY+36.6f*sizeMultiplier,
				0, 0,
				width, height*sizeMultiplier,
				1, 1,
				Beam.angle,
				Color.valueOf("30e957ff"),
				255);
		getMeshMiddleOverlayHelper().constructMesh( getSpriteMiddleOverlay(),
				1,
				startX+52.5f*sizeMultiplier, startY+36.6f*sizeMultiplier,
				0, 0,
				width, height*sizeMultiplier,
				1, 1,
				Beam.angle,
				Color.valueOf("ffffffff"),
				255);

		getMeshEndBackgroundHelper().constructMesh( getSpriteEndBackground(),
				1,
				startX+52.5f*sizeMultiplier+52.5f, startY+36.6f*sizeMultiplier+36.6f,
				0, 0,
				width, height,
				1, 1,
				Beam.angle,
				Color.valueOf("30e957ff"),
				255);
		getMeshEndOverlayHelper().constructMesh( getSpriteEndOverlay(),
				1,
				startX+52.5f*sizeMultiplier+52.5f, startY+36.6f*sizeMultiplier+36.6f,
				0, 0,
				width, height,
				1, 1,
				Beam.angle,
				Color.valueOf("ffffffff"),
				255);

	}

	@Override
	public void dispose(){
		getBeam().dispose();
		getMeshEndBackgroundHelper().dispose();
		getMeshMiddleBackgroundHelper().dispose();
		getMeshStartBackgroundHelper().dispose();
	}

	@Override
	public void render () {
		Gdx.graphics.getGL20().glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
        getCamera().update();

//        getMeshStartBackgroundHelper().setVertices(new float[] {
//    		250.0f, 150.0f, -1.17487E37f, 0.6484375f, 0.015625f,
//    		197.5688f, 113.29892f, 2.37334E-39f, 0.6484375f, 0.515625f,
//    		160.86772f, 165.73012f, 2.37334E-39f, 0.7734375f, 0.515625f,
//    		213.29892f, 202.4312f, -7.17487E37f, 0.7734375f, 0.015625f
//		});

//        final int startX = 250;
//        final int startY = 150;
//        final int width = 64;
//        final int height = 64;
//        final int sizeMultiplier = 3;
//
//        if (!Beam.started) {
//    		Timer.schedule(new Task() {
//    			@Override
//    			public void run() {
//    				if (!Beam.started) Beam.started = true;
//    				if (Beam.angle >= 360) Beam.angle = 0;
//
//    		        getMeshStartBackgroundHelper().constructMesh( getSpriteStartBackground(),
//    		        		1,
//    		        		startX, startY,
//    		        		0, 0,
//    		        		width, height,
//    		        		1, 1,
//    		        		Beam.angle,
//    		        		new Color(48/255f, 233/255f, 87/255f, 255/255f),
//    		        		0);
//    		        getMeshStartOverlayHelper().constructMesh( getSpriteStartOverlay(),
//    		        		1,
//    		        		startX, startY,
//    		        		0, 0,
//    		        		width, height,
//    		        		1, 1,
//    		        		Beam.angle,
//    		        		new Color(255/255f, 255/255f, 255/255f, 255/255f),
//    		        		0);
//
//    				getMeshMiddleBackgroundHelper().constructMesh( getSpriteMiddleBackground(),
//    						1,
//    						startX+52.5f*sizeMultiplier, startY+36.6f*sizeMultiplier,
//    						0, 0,
//    						width, height*sizeMultiplier,
//    						1, 1,
//    						Beam.angle,
//    						new Color(48, 233, 87, 255),
//    						255);
//    				getMeshMiddleOverlayHelper().constructMesh( getSpriteMiddleOverlay(),
//    						1,
//    						startX+52.5f*sizeMultiplier, startY+36.6f*sizeMultiplier,
//    						0, 0,
//    						width, height*sizeMultiplier,
//    						1, 1,
//    						Beam.angle,
//    						new Color(255, 255, 255, 255),
//    						255);
//
//    				getMeshEndBackgroundHelper().constructMesh( getSpriteEndBackground(),
//    						1,
//    						startX+52.5f*sizeMultiplier+52.5f, startY+36.6f*sizeMultiplier+36.6f,
//    						0, 0,
//    						width, height,
//    						1, 1,
//    						Beam.angle,
//    						new Color(48, 233, 87, 255),
//    						255);
//    				getMeshEndOverlayHelper().constructMesh( getSpriteEndOverlay(),
//    						1,
//    						startX+52.5f*sizeMultiplier+52.5f, startY+36.6f*sizeMultiplier+36.6f,
//    						0, 0,
//    						width, height,
//    						1, 1,
//    						Beam.angle,
//    						new Color(255, 255, 255, 255),
//    						255);
//    				Beam.angle++;
//
//    			}
//    		}, 0, 1 / 25.0f);
//        }

		getMeshStartBackgroundHelper().drawMesh();
		getMeshStartOverlayHelper().drawMesh();

		getMeshMiddleBackgroundHelper().drawMesh();
		getMeshMiddleOverlayHelper().drawMesh();

		getMeshEndBackgroundHelper().drawMesh();
		getMeshEndOverlayHelper().drawMesh();


	}

	public TextureAtlas getBeam() {
		return beam;
	}

	public void setBeam(TextureAtlas beam) {
		this.beam = beam;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public MeshHelper getMeshEndBackgroundHelper() {
		return meshEndBackgroundHelper;
	}

	public void setMeshEndBackgroundHelper(MeshHelper meshHelper) {
		this.meshEndBackgroundHelper = meshHelper;
	}

	public Sprite getSpriteEndBackground() {
		return spriteEndBackground;
	}

	public void setSpriteEndBackground(Sprite sprite) {
		this.spriteEndBackground = sprite;
	}

	public Sprite getSpriteEndOverlay() {
		return spriteEndOverlay;
	}

	public void setSpriteEndOverlay(Sprite spriteEndOverlay) {
		this.spriteEndOverlay = spriteEndOverlay;
	}

	public MeshHelper getMeshEndOverlayHelper() {
		return meshEndOverlayHelper;
	}

	public void setMeshEndOverlayHelper(MeshHelper meshEndOverlayHelper) {
		this.meshEndOverlayHelper = meshEndOverlayHelper;
	}

	public MeshHelper getMeshStartBackgroundHelper() {
		return meshStartBackgroundHelper;
	}

	public void setMeshStartBackgroundHelper(MeshHelper meshStartBackgroundHelper) {
		this.meshStartBackgroundHelper = meshStartBackgroundHelper;
	}

	public MeshHelper getMeshStartOverlayHelper() {
		return meshStartOverlayHelper;
	}

	public void setMeshStartOverlayHelper(MeshHelper meshStartOverlayHelper) {
		this.meshStartOverlayHelper = meshStartOverlayHelper;
	}

	public Sprite getSpriteStartBackground() {
		return spriteStartBackground;
	}

	public void setSpriteStartBackground(Sprite spriteStartBackground) {
		this.spriteStartBackground = spriteStartBackground;
	}

	public Sprite getSpriteStartOverlay() {
		return spriteStartOverlay;
	}

	public void setSpriteStartOverlay(Sprite spriteStartOverlay) {
		this.spriteStartOverlay = spriteStartOverlay;
	}

	public MeshHelper getMeshMiddleBackgroundHelper() {
		return meshMiddleBackgroundHelper;
	}

	public void setMeshMiddleBackgroundHelper(MeshHelper meshMiddleBackgroundHelper) {
		this.meshMiddleBackgroundHelper = meshMiddleBackgroundHelper;
	}

	public MeshHelper getMeshMiddleOverlayHelper() {
		return meshMiddleOverlayHelper;
	}

	public void setMeshMiddleOverlayHelper(MeshHelper meshMiddleOverlayHelper) {
		this.meshMiddleOverlayHelper = meshMiddleOverlayHelper;
	}

	public Sprite getSpriteMiddleBackground() {
		return spriteMiddleBackground;
	}

	public void setSpriteMiddleBackground(Sprite spriteMiddleBackground) {
		this.spriteMiddleBackground = spriteMiddleBackground;
	}

	public Sprite getSpriteMiddleOverlay() {
		return spriteMiddleOverlay;
	}

	public void setSpriteMiddleOverlay(Sprite spriteMiddleOverlay) {
		this.spriteMiddleOverlay = spriteMiddleOverlay;
	}
}
