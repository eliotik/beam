package com.tests.beam.mesh;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tests.beam.Main;

public class Meshes {
	public static MeshHelper create(Main main, Sprite sprite, int rotation, float x, float y, int width, int height, Color colour) {
		if (colour == null) {
			MeshTexturedHelper mesh = new MeshTexturedHelper(main, sprite, rotation);
			mesh.createMesh( 
		    		x, y,
		    		width, height
				);
			return mesh;
		} else {
		
			MeshHelper mesh = new MeshHelper(main, sprite, rotation);
			mesh.createMesh( 
		    		x, y,
		    		width, height,
		    		colour
				);
			return mesh;
		}
	}
}
