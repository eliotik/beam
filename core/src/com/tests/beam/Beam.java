package com.tests.beam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Beam {
	private Array<MeshHelper> meshes = new Array<MeshHelper>();
	
	final static int BEAM_WIDTH = 64;
	final static int BEAM_HEIGHT = 64;
	
	public Beam(Main main, float x, float y, int size, int rotation, String backgroundColour, String overlayColour) {
		addMesh(main, "start_background", x - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT)[0], y - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT)[1], BEAM_WIDTH, BEAM_HEIGHT, rotation, backgroundColour);
		addMesh(main, "middle_background", x - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT + 2 * BEAM_HEIGHT * size)[0], y - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT + 2 * BEAM_HEIGHT * size)[1], BEAM_WIDTH, BEAM_HEIGHT*size, rotation, backgroundColour);
		addMesh(main, "end_background", x - getSlip(rotation,  BEAM_WIDTH, 3 * BEAM_HEIGHT * size)[0], y - getSlip(rotation,  BEAM_WIDTH, 3 * BEAM_HEIGHT * size)[1], BEAM_WIDTH, BEAM_HEIGHT, rotation, backgroundColour);
		
		addMesh(main, "start_overlay", x - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT)[0], y - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT)[1], BEAM_WIDTH, BEAM_HEIGHT, rotation, overlayColour);
		addMesh(main, "middle_overlay", x - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT + 2 * BEAM_HEIGHT * size)[0], y - getSlip(rotation,  BEAM_WIDTH, BEAM_HEIGHT + 2 * BEAM_HEIGHT * size)[1], BEAM_WIDTH, BEAM_HEIGHT*size, rotation, overlayColour);
		addMesh(main, "end_overlay", x - getSlip(rotation,  BEAM_WIDTH, 3 * BEAM_HEIGHT * size)[0], y - getSlip(rotation,  BEAM_WIDTH, 3 * BEAM_HEIGHT * size)[1], BEAM_WIDTH, BEAM_HEIGHT, rotation, overlayColour);
	}

	private void addMesh(Main main, String spriteName, float x, float y, int width, int height, int rotation, String colour) {
		MeshHelper mesh = Meshes.create(
			main, 
			main.getSprites().get(spriteName), 
			rotation, 
			x, y,
    		width, height,
    		Color.valueOf(colour)
		);
		getMeshes().add(mesh);
	}

    private float[] getSlip(float angle, int width, int height){
        float[] slip = new float[2];
        float sin = MathUtils.sinDeg(angle);
        float cos = MathUtils.cosDeg(angle);
        slip[0] = (width/2)*cos - (height/2)* sin;
        slip[1] = (width/2)*sin + (height/2)* cos;

        return slip;
    }
	
	public void render() {
		for(int i = 0, l = getMeshes().size; i < l; ++i) getMeshes().get(i).drawMesh();
	}
	
	public void dispose() {
		for(int i = 0, l = getMeshes().size; i < l; ++i) getMeshes().get(i).dispose();
	}

	public Array<MeshHelper> getMeshes() {
		return meshes;
	}

	public void setMeshes(Array<MeshHelper> meshes) {
		this.meshes = meshes;
	}
}
