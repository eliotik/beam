package com.tests.beam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.tests.beam.mesh.MeshHelper;
import com.tests.beam.mesh.Meshes;

public class Beam {
	private Array<MeshHelper> meshes = new Array<MeshHelper>();
	
	final static int BEAM_WIDTH = 64;
	final static int BEAM_HEIGHT = 64;
	
	public Beam(Main main, float x, float y, int size, int rotation, String backgroundColour, String overlayColour) {
		addMesh(main, "start_background", x, y, BEAM_WIDTH, BEAM_HEIGHT, rotation, backgroundColour);
		addMesh(main, "middle_background", x+52.5f*size, y+36.6f*size, BEAM_WIDTH, BEAM_HEIGHT*size, rotation, backgroundColour);
		addMesh(main, "end_background", x+52.5f*size+52.5f, y+36.6f*size+36.6f, BEAM_WIDTH, BEAM_HEIGHT, rotation, backgroundColour);
		
		addMesh(main, "start_overlay", x, y, BEAM_WIDTH, BEAM_HEIGHT, rotation, overlayColour);
		addMesh(main, "middle_overlay", x+52.5f*size, y+36.6f*size, BEAM_WIDTH, BEAM_HEIGHT*size, rotation, overlayColour);
		addMesh(main, "end_overlay", x+52.5f*size+52.5f, y+36.6f*size+36.6f, BEAM_WIDTH, BEAM_HEIGHT, rotation, overlayColour);
	}

	private void addMesh(Main main, String spriteName, float x, float y, int width, int height, int rotation, String colour) {
		MeshHelper mesh = Meshes.create(
			main, 
			main.getSprites().get(SpriteType.BEAM, spriteName), 
			rotation, 
			x, y,
    		width, height,
    		Color.valueOf(colour)
		);
		getMeshes().add(mesh);
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
