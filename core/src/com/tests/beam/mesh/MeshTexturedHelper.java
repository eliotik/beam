package com.tests.beam.mesh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MeshTexturedHelper extends MeshHelper {

	public MeshTexturedHelper(OrthographicCamera camera, Sprite sprite, int rotation) {
		super(camera, sprite, rotation);
	}

	public void createMesh(float meshStartX , float meshStartY, float meshWidth, float meshHeight) {
		float[] vertices = constructMesh(
				meshStartX , meshStartY,
				meshWidth, meshHeight);
		createShader();
		setMesh(new Mesh(true, 4, 6, 
				VertexAttribute.Position(), 
				VertexAttribute.ColorUnpacked(), 
				VertexAttribute.TexCoords(0)));
    	getMesh().setVertices(vertices);
    	getMesh().setIndices(new short[] { 0, 1, 2, 2, 3, 0});
	        
    }
	
    private float[] constructMesh(float meshStartX, float meshStartY,
			float meshWidth, float meshHeight) {
    	/*return new float[] {
				-0.5f, -0.5f, 0, 1, 1, 1, 1, 0, 1, 
				0.5f, -0.5f, 0, 1, 1, 1, 1, 1, 1, 
				0.5f, 0.5f, 0, 1, 1, 1, 1, 1, 0,
				-0.5f, 0.5f, 0, 1, 1, 1, 1, 0, 0
				};*/
		return new float[] {
			meshStartX, meshStartY-meshHeight, 0, 1, 1, 1, 1, 0.0f ,1.0f ,
            meshStartX+meshWidth, meshStartY-meshHeight, 0, 1, 1, 1, 1, 1.0f, 1.0f,
            meshStartX+meshWidth, meshStartY, 0, 1, 1, 1, 1, 1.0f, 0.0f,
            meshStartX, meshStartY, 0, 1, 1, 1, 1, 0.0f, 0.0f 
		};
		/*
		-5f, -5f, 0, 0, 1,      // bottom left
        5f, -5f, 0, 1, 1,       // bottom right
        5f, 5f, 0, 1, 0,        // top right
        -5f, 5f, 0, 0, 0});     // top left
		*/
	}

	private void createShader() {
		String vertexShader = "attribute vec4 a_position;    \n"
				+ "attribute vec4 a_color;\n" 
				+ "attribute vec2 a_texCoord0;\n"
				+ "uniform mat4 u_worldView;\n" 
				+ "varying vec4 v_color;"
				+ "varying vec2 v_texCoords;"
				+ "void main()                  \n"
				+ "{                            \n"
				+ "   v_color = vec4(1, 1, 1, 1); \n"
				+ "   v_texCoords = a_texCoord0; \n"
				+ "   gl_Position =  u_worldView * a_position;  \n"
				+ "}                            \n";
		String fragmentShader = "#ifdef GL_ES\n"
				+ "precision mediump float;\n"
				+ "#endif\n"
				+ "varying vec4 v_color;\n"
				+ "varying vec2 v_texCoords;\n"
				+ "uniform sampler2D u_texture;\n"
				+ "void main()                                  \n"
				+ "{                                            \n"
				+ "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n"
				+ "}";
 
        // make an actual shader from our strings
        setShader(new ShaderProgram(vertexShader, fragmentShader));
 
        // check there's no shader compile errors
        if (!getShader().isCompiled())
            throw new IllegalStateException(getShader().getLog());
        
        if (getShader().getLog().length()!=0)
			System.out.println(getShader().getLog());
    }
	
	public void drawMesh() {
		if (getMesh() == null)
			throw new IllegalStateException("drawMesh called before a mesh has been created.");

		Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		getTexture().bind();
		getShader().begin();
		getShader().setUniformMatrix("u_worldView", getCamera().combined);
		getShader().setUniformi("u_texture", 0);
		getMesh().render(getShader(), GL20.GL_TRIANGLE_FAN);
		getShader().end();
	}
}
