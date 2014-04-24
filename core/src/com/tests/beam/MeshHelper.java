package com.tests.beam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;

public class MeshHelper {
    private static final boolean Y_DIRECTION_UP = true;
    
	private Mesh mesh;
    private ShaderProgram shader;
    private Texture texture;
    private Sprite sprite;
    private final Main main;
    private int rotation = 0;
    
    public MeshHelper(Main main, Sprite sprite, int rotation) {
    	this.main = main;
    	setSprite(sprite);
    	setRotation(rotation);
        setTexture(sprite.getTexture());
        createShader();
    }

	public void createMesh(float meshStartX , float meshStartY, float meshWidth, float meshHeight, Color colour) {
		float[] vertices = constructMesh(
				1,
				meshStartX , meshStartY,
				0, 0,
				meshWidth, meshHeight,
				1f, 1f,
				colour,
				1);
    	setMesh(new Mesh(true, vertices.length, 0,
    			new VertexAttribute(Usage.Position, 2, "a_position"),
    			new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
    			new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords")));
        getMesh().setVertices(vertices);
    }

    private void createShader() {
        // this shader tells opengl where to put things
    	String vertexShader = "attribute vec4 a_position;    \n"
				+ "attribute vec4 a_color;       \n"
				+ "attribute vec2 a_texCoords;   \n"
				+ "varying vec4 v_color;         \n"
				+ "varying vec2 v_texCoords;     \n"
				+ "uniform mat4 u_projTrans;     \n"
				+ "void main()                   \n"
				+ "{                             \n"
				+ "   v_color = a_color;         \n"
				+ "   v_texCoords = a_texCoords; \n"
//				+ "   gl_Position = a_position;  \n"
				+ "   gl_Position = u_projTrans * a_position; \n"
				+ "}                             \n";
 
    	// this one tells it what goes in between the points (i.e colour/texture)
    	String fragmentShader = "#ifdef GL_ES                \n"
				  + "precision mediump float;    \n"
				  + "#endif                      \n"
				  + "varying vec4 v_color;       \n"
				  + "varying vec2 v_texCoords;   \n"
				  + "uniform sampler2D u_texture;\n"
				  + "void main()                 \n"
				  + "{                           \n"
				  + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);   \n"
				  + "}                           \n";
 
        // make an actual shader from our strings
        setShader(new ShaderProgram(vertexShader, fragmentShader));
 
        // check there's no shader compile errors
        if (!getShader().isCompiled())
            throw new IllegalStateException(getShader().getLog());
    }
    
	public void drawMesh() {
		// this should be called in render()
		if (getMesh() == null)
			throw new IllegalStateException("drawMesh called before a mesh has been created.");

		GL20 gl = Gdx.graphics.getGL20();
	    //we don't necessarily need these, but its good practice to enable
	    //the things we need. we enable 2d textures and set the active one
	    //to 0. we could have multiple textures but we don't need it here.
	    gl.glEnable(GL20.GL_TEXTURE_2D);
	    gl.glActiveTexture(GL20.GL_TEXTURE0);
	 
	    gl.glEnable(GL20.GL_BLEND);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
	    
	    getMain().getCamera().setToOrtho(Y_DIRECTION_UP, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    
	    getShader().begin();
	    getShader().setUniformMatrix("u_projTrans", getMain().getCamera().combined);
	    //this sets our uniform 'u_texture' (i.e. the gl texture we want to use) to 0.
	    getShader().setUniformi("u_texture", 0);
	    getTexture().bind();
		
		getShader().begin();
		getMesh().render(getShader(), GL20.GL_TRIANGLE_FAN);
		getShader().end();
		gl.glDisable(GL20.GL_TEXTURE_2D);
		gl.glDisable(GL20.GL_BLEND);
	}
	
	   public void dispose() {
	        getMesh().dispose();
	        getShader().dispose();
	        getTexture().dispose();
	    }

	public Sprite getSprite() {
		return sprite;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * Creates a mesh which will draw a repeated texture. To be used whenever we
	 * are dealing with a region of a TextureAtlas
	 *
	 * @param vertices
	 *            For re-use, the vertices to use for the mesh. If insufficient
	 *            are provided, a new array will be constructed
	 * @param region
	 *            The AtlasRegion to use
	 * @param scale
	 *            The factor by which we want to repeat our texture
	 * @param x
	 * @param y
	 * @param originX
	 * @param originY
	 * @param width
	 * @param height
	 * @param scaleX
	 *            Scale by which we want to expand the mesh on X
	 * @param scaleY
	 *            Scale by which we want to expand the mesh on Y
	 * @param rotation
	 *            Degrees of rotation for mesh
	 * @param colorBase
	 *            The initial base color
	 * @param alpha
	 *            The alpha by which to mult the colorBase by; resulting in the
	 *            end interpolation target.
	 * @return
	 */
	public float[] constructMesh(int scale,
			float x, float y, float originX,
			float originY, float width, float height, float scaleX,
			float scaleY, Color colorT, float alpha) {
//		if (scale * MAX_VERTICES > getVertices().length) {
//			setVertices( new float[MAX_VERTICES * scale] );
//		}
		float[] vertices = new float[20];
		
		float color = colorT.toFloatBits();
		float colorE;

		int idx = 0;

		// bottom left and top right corner points relative to origin
		final float worldOriginX = x + originX;
		final float worldOriginY = y + originY;
		float fx = -originX;
		float fy = -originY;
		float fx2 = width - originX;
		float fy2 = height - originY;

		// scale
		if (scaleX != 1 || scaleY != 1) {
			fx *= scaleX;
			fy *= scaleY;
			fx2 *= scaleX;
			fy2 *= scaleY;
		}

		// construct corner points, start from top left and go counter clockwise
		final float p1x = fx;
		final float p1y = fy;
		final float p2x = fx;
		final float p2y = fy2;
		final float p3x = fx2;
		final float p3y = fy2;
		final float p4x = fx2;
		final float p4y = fy;

		float Fx1;
		float Fy1;
		float Fx2;
		float Fy2;
		float Fx3;
		float Fy3;
		float Fx4;
		float Fy4;

		// rotate
		if (rotation != 0) {
			final float cos = MathUtils.cosDeg(rotation);
			final float sin = MathUtils.sinDeg(rotation);

			Fx1 = cos * p1x - sin * p1y;
			Fy1 = sin * p1x + cos * p1y;

			Fx2 = cos * p2x - sin * p2y;
			Fy2 = sin * p2x + cos * p2y;

			Fx3 = cos * p3x - sin * p3y;
			Fy3 = sin * p3x + cos * p3y;

			Fx4 = Fx1 + (Fx3 - Fx2);
			Fy4 = Fy3 - (Fy2 - Fy1);
		} else {
			Fx1 = p1x;
			Fy1 = p1y;

			Fx2 = p2x;
			Fy2 = p2y;

			Fx3 = p3x;
			Fy3 = p3y;

			Fx4 = p4x;
			Fy4 = p4y;
		}

		float x1 = Fx1 + worldOriginX;
		float y1 = Fy1 + worldOriginY;
		float x2 = Fx2 + worldOriginX;
		float y2 = Fy2 + worldOriginY;

		float scaleX2 = ((Fx2 - Fx1) / scale);
		float scaleY2 = ((Fy2 - Fy1) / scale);
		float scaleX3 = ((Fx3 - Fx4) / scale);
		float scaleY3 = ((Fy3 - Fy4) / scale);
		float scaleAlpha = (colorT.a - (colorT.a * alpha)) / scale;

		float x3 = x1;
		float y3 = y1;
		float x4 = x2;
		float y4 = y2;

		final float u = getSprite().getU();
		final float v = getSprite().getV();
		final float u2 = getSprite().getU2();
		final float v2 = getSprite().getV2();

		for (int i = 1; i <= scale; i++) {
			x1 = Fx1 + scaleX2 * (i - 1) + worldOriginX;
			y1 = Fy1 + scaleY2 * (i - 1) + worldOriginY;
			x2 = Fx1 + scaleX2 * i + worldOriginX;
			y2 = Fy1 + scaleY2 * i + worldOriginY;

			x3 = Fx4 + scaleX3 * i + worldOriginX;
			y3 = Fy4 + scaleY3 * i + worldOriginY;
			x4 = Fx4 + scaleX3 * (i - 1) + worldOriginX;
			y4 = Fy4 + scaleY3 * (i - 1) + worldOriginY;

			color = colorT.toFloatBits();
			colorT.a -= scaleAlpha;
			colorE = colorT.toFloatBits();

			vertices[idx++] = x1;
			vertices[idx++] = y1;
			vertices[idx++] = color;
			vertices[idx++] = u;
			vertices[idx++] = v;

			vertices[idx++] = x2;
			vertices[idx++] = y2;
			vertices[idx++] = colorE;
			vertices[idx++] = u;
			vertices[idx++] = v2;

			vertices[idx++] = x3;
			vertices[idx++] = y3;
			vertices[idx++] = colorE;
			vertices[idx++] = u2;
			vertices[idx++] = v2;

			vertices[idx++] = x4;
			vertices[idx++] = y4;
			vertices[idx++] = color;
			vertices[idx++] = u2;
			vertices[idx++] = v;
//			System.out.println("-------------------------------");
//			System.out.println(x1 +", "+ y1 +", "+ color +", "+ u +", "+ v);
//			System.out.println(x2 +", "+ y2 +", "+ colorE +", "+ u +", "+ v2);
//			System.out.println(x3 +", "+ y3 +", "+ colorE +", "+ u2 +", "+ v2);
//			System.out.println(x4 +", "+ y4 +", "+ color +", "+ u2 +", "+ v);

		}

		return vertices;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	public ShaderProgram getShader() {
		return shader;
	}

	public void setShader(ShaderProgram shader) {
		this.shader = shader;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Main getMain() {
		return main;
	}
}