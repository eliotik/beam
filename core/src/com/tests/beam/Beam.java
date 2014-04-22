package com.tests.beam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Beam extends ApplicationAdapter {
	private SpriteBatch batch;
	private TextureAtlas beam;
	
	private OrthographicCamera camera;

	private MeshHelper meshEndBackgroundHelper;
	private MeshHelper meshEndOverlayHelper;
	private Sprite spriteEndBackground;
	private Sprite spriteEndOverlay;
	
	public static int angle = 0;
	public static boolean started = false;

	@Override
	public void create () {
		setBatch(new SpriteBatch());
		setBeam(new TextureAtlas(Gdx.files.internal("beam/beam.atlas")));
		setSpriteEndBackground(getBeam().createSprite("end_background"));
		setSpriteEndOverlay(getBeam().createSprite("end_overlay"));
		setCamera(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		
		setMeshEndBackgroundHelper(new MeshHelper(this, getSpriteEndBackground()));
		getMeshEndBackgroundHelper().createMesh();
		setMeshEndOverlayHelper(new MeshHelper(this, getSpriteEndOverlay()));
		getMeshEndOverlayHelper().createMesh();
		
	}

	@Override
	public void dispose(){
		getBeam().dispose();
		getBatch().dispose();
		getMeshEndBackgroundHelper().dispose();
	}

	@Override
	public void render () {
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
        getCamera().update();
        if (!Beam.started) {
    		Timer.schedule(new Task() {
    			@Override
    			public void run() {
    				if (!Beam.started) Beam.started = true;
    				if (Beam.angle >= 360) Beam.angle = 0;

    				getMeshEndBackgroundHelper().constructMesh( getSpriteEndBackground(),
    						1,
    						Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,
    						0, 0,
    						64/2, 64,
    						1, 1,
    						Beam.angle, 
    						new Color(48, 233, 87, 255), 
    						255);
    				getMeshEndOverlayHelper().constructMesh( getSpriteEndOverlay(),
    						1,
    						Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,
    						0, 0,
    						64/2, 64,
    						1, 1,
    						Beam.angle, 
    						new Color(255, 255, 255, 255), 
    						255);    				
    				Beam.angle++;
    				
    			}
    		}, 0, 1 / 25.0f);
        }
        
        getMeshEndBackgroundHelper().drawMesh();
		getMeshEndOverlayHelper().drawMesh();
	}

	

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
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
}
