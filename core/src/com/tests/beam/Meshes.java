package com.tests.beam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Meshes {
	public static MeshHelper create(Main main, Sprite sprite, int rotation, float x, float y, int width, int height, Color colour) {
		MeshHelper mesh = new MeshHelper(main, sprite, rotation);
		mesh.createMesh( 
	    		x, y,
	    		width, height,
	    		colour
			);
		return mesh;
	}
}
