package com.tests.beam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tests.beam.mesh.MeshHelper;
import com.tests.beam.mesh.Meshes;

public class Beam {
	private Array<MeshHelper> meshes = new Array<MeshHelper>();
	private final Main main;
	final static int BEAM_WIDTH = 64;
	final static int BEAM_HEIGHT = 64;
	
	private float lifeTimeOfLaser;
	public final float totalTimeOfLaser = 2.0f;
	private float alphaValue = 1;
	
	private int rotation;
	private float x;
	private float y; 
	private int size;
	private String backgroundColour;
	private String overlayColour;
	
	public Beam(Main main, float x, float y, int size, int rotation, String backgroundColour, String overlayColour) {
		this.main = main;
		setX(x);
		setY(y);
		setSize(size);
		setRotation(rotation);
		setBackgroundColour(backgroundColour);
		setOverlayColour(overlayColour);
		initMeshes();
	}

	private void initMeshes() {
        double mMF = meshMultiplierFactor(getSize());
		float[] meshFirstPart = getSlip(getRotation(),  BEAM_WIDTH, BEAM_HEIGHT);
		float[] meshSecondPart = getSlip(getRotation(),  BEAM_WIDTH, BEAM_HEIGHT + 2 * BEAM_HEIGHT * getSize());
		float[] meshThirdPart = getSlip(getRotation(),  BEAM_WIDTH, (int) (mMF * BEAM_HEIGHT * getSize()));
		
		addMesh("start_background", getX() - meshFirstPart[0], getY() - meshFirstPart[1], BEAM_WIDTH, BEAM_HEIGHT, getRotation(), getBackgroundColour());
		addMesh("start_overlay", getX() - meshFirstPart[0], getY() - meshFirstPart[1], BEAM_WIDTH, BEAM_HEIGHT, getRotation(), getOverlayColour());
		
		addMesh("middle_background", getX() - meshSecondPart[0], getY() - meshSecondPart[1], BEAM_WIDTH, BEAM_HEIGHT*getSize(), getRotation(), getBackgroundColour());
		addMesh("middle_overlay", getX() - meshSecondPart[0], getY() - meshSecondPart[1], BEAM_WIDTH, BEAM_HEIGHT*getSize(), getRotation(), getOverlayColour());
		
		addMesh("end_background", getX() - meshThirdPart[0], getY() - meshThirdPart[1], BEAM_WIDTH, BEAM_HEIGHT, getRotation(), getBackgroundColour());
		addMesh("end_overlay", getX() - meshThirdPart[0], getY() - meshThirdPart[1], BEAM_WIDTH, BEAM_HEIGHT, getRotation(), getOverlayColour());
	}

	private void addMesh(String spriteName, float x, float y, int width, int height, int rotation, String colour) {
		MeshHelper mesh = Meshes.create(
			getMain().getCamera(), 
			main.getSprites().get(SpriteType.BEAM, spriteName), 
			rotation, 
			x, y,
    		width, height,
    		Color.valueOf(colour)
		);
		getMeshes().add(mesh);
	}

    private double meshMultiplierFactor(int size) {
        return  (double)(5 + (size - 1)* 2)/size;
    }

    private float[] getSlip(float angle, int width, int height){
        float[] slip = new float[2];
        float sin = MathUtils.sinDeg(angle);
        float cos = MathUtils.cosDeg(angle);
        slip[0] = (width/2)*cos - (height/2)* sin;
        slip[1] = (width/2)*sin + (height/2)* cos;

        return slip;
    }
	
    public int getGroupId(int index) {
    	return (int) (Math.floor(index/2)+1);
    }
    
	public void render() {
		for(int i = 0, l = getMeshes().size; i < l; ++i) getMeshes().get(i).drawMesh();
	}
	
	public void dispose() {
		for(int i = 0, l = getMeshes().size; i < l; ++i) getMeshes().get(i).dispose();
	}
	
	public void clear() {
		getMeshes().clear();
	}

	public Array<MeshHelper> getMeshes() {
		return meshes;
	}

	public void setMeshes(Array<MeshHelper> meshes) {
		this.meshes = meshes;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public void reconstruct() {
		reconstruct(getX(), getY(), getSize(), rotation);
	}
	
	public void reconstruct(float x, float y, int size, int rotation) {
		setRotation(rotation);
		for (int i = 0, l = getMeshes().size; i < l; ++i) {
			switch(getGroupId(i)) {
			case 1:
				getMeshes().get(i).updateMesh(x - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT)[0], y - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT)[1], rotation, getAlphaValue());
				break;
			case 2:
				getMeshes().get(i).updateMesh(x - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT + 2 * BEAM_HEIGHT * size)[0], y - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT + 2 * BEAM_HEIGHT * size)[1], rotation, getAlphaValue());
				break;
			case 3: 
				getMeshes().get(i).updateMesh(x - getSlip(rotation,  BEAM_WIDTH, 3 * BEAM_HEIGHT * size)[0], y - getSlip(rotation,  BEAM_WIDTH, 3 * BEAM_HEIGHT * size)[1], rotation, getAlphaValue());
				break;
			default: 
				break;
			}
		}
	}

	public float getLifeTimeOfLaser() {
		return lifeTimeOfLaser;
	}

	public void setLifeTimeOfLaser(float lifeTimeOfLaser) {
		this.lifeTimeOfLaser = lifeTimeOfLaser;
		if (lifeTimeOfLaser > getTotalTimeOfLaser()) {
			this.lifeTimeOfLaser = 0f;
		}
	}

	public float getTotalTimeOfLaser() {
		return totalTimeOfLaser;
	}

	public void update() {
		setLifeTimeOfLaser(getLifeTimeOfLaser() + Gdx.graphics.getDeltaTime());
		setAlphaValue(1 - getLifeTimeOfLaser() );
		reconstruct();
	}

	public float getAlphaValue() {
		return alphaValue;
	}

	public void setAlphaValue(float alphaValue) {
		this.alphaValue = alphaValue;
	}

	public Main getMain() {
		return main;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getBackgroundColour() {
		return backgroundColour;
	}

	public void setBackgroundColour(String backgroundColour) {
		this.backgroundColour = backgroundColour;
	}

	public String getOverlayColour() {
		return overlayColour;
	}

	public void setOverlayColour(String overlayColour) {
		this.overlayColour = overlayColour;
	}
}
