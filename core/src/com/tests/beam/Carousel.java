package com.tests.beam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Carousel {
	private Array<MeshHelper> meshes = new Array<MeshHelper>();
	
	public Carousel(Main main, int quantity) {
		int localAngle = 360;
		if (quantity <= 0) quantity = 1;
		float angleStep = (int)(localAngle / quantity);
        
        final int meshStartX = Gdx.graphics.getWidth()/2;
		final int meshStartY = Gdx.graphics.getHeight()/2;
		final int meshWidth = 64;
		final int meshHeight = 64;
		final int meshSizeMultiplier = 3;
		while(localAngle > 0) {
        	MeshHelper mesh = Meshes.create(
        			main, 
        			main.getSprites().get("middle_background"), 
        			localAngle, 
        			meshStartX , meshStartY,
        			meshWidth, meshHeight*meshSizeMultiplier,
            		Color.valueOf("30e957ff"));
        	getMeshes().add(mesh);
        	
        	mesh = Meshes.create(
        			main, 
        			main.getSprites().get("middle_overlay"), 
        			localAngle, 
        			meshStartX , meshStartY,
        			meshWidth, meshHeight*meshSizeMultiplier,
            		Color.valueOf("ffffffff"));
        	getMeshes().add(mesh);
        	
        	localAngle -= angleStep;
        }
        
		Timer.schedule(new Task() {
			@Override
			public void run() {
				for(int i = 0, l = getMeshes().size; i < l; ++i) {
					MeshHelper mesh = getMeshes().get(i);
					mesh.setRotation(mesh.getRotation() - 1);
					if (mesh.getRotation() <= 0) mesh.setRotation(360);
					mesh.createMesh( 
						meshStartX, meshStartY,
						meshWidth, meshHeight*meshSizeMultiplier,
						(i % 2 == 0) ? Color.valueOf("30e957ff") : Color.valueOf("ffffffff")
					);
				}
			}
		}, 0, 1 / 25.0f);
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
