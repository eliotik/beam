package com.tests.beam.mesh;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Meshes {
	public static MeshHelper create(OrthographicCamera camera, Sprite sprite, int rotation, float x, float y, int width, int height, Color colour) {
		if (colour == null) {
			MeshTexturedHelper mesh = new MeshTexturedHelper(camera, sprite, rotation);
			mesh.createMesh( 
		    		x, y,
		    		width, height
				);
			return mesh;
		} else {
		
			MeshHelper mesh = new MeshHelper(camera, sprite, rotation);
			mesh.createMesh( 
		    		x, y,
		    		width, height,
		    		colour
				);
			return mesh;
		}
	}
}
