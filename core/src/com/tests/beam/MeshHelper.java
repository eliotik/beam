package com.tests.beam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MeshHelper {
    private Mesh mesh;
    private ShaderProgram meshShader;
    private Beam beam;

    public MeshHelper(Beam beam) {
    	setBeam(beam);
        createShader();
    }

    public void createMesh(float[] vertices) {
        mesh = new Mesh(true, vertices.length, 0,
                new VertexAttribute(Usage.Position, 2, "a_position"),
                new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
        mesh.setVertices(vertices);
    }

    public void drawMesh() {
        // this should be called in render()
        if (mesh == null)
            throw new IllegalStateException("drawMesh called before a mesh has been created.");

        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        gl.glActiveTexture(GL20.GL_TEXTURE0);

        meshShader.begin();
        //System.out.println("rendering");
        meshShader.setUniformi("u_texture", 0);
        getBeam().getBeam().findRegion("end_background").getTexture().bind();
        mesh.render(meshShader, GL20.GL_TRIANGLES);
        meshShader.end();
    }

    private void createShader() {
        // this shader tells opengl where to put things
        String vertexShader = "attribute vec4 a_position;    \n"
			        		+ "attribute vec4 a_color;       \n"
			        		+ "attribute vec2 a_texCoords;   \n"
			                + "varying vec4 v_color;         \n"
			                + "varying vec2 v_texCoords;     \n"
                            + "void main()                   \n"
                            + "{                             \n"
                            + "   v_color = a_color;         \n"
                            + "   v_texCoords = a_texCoords; \n"
                            + "   gl_Position = a_position;  \n"
                            + "}                             \n";

        // this one tells it what goes in between the points (i.e
        // colour/texture)
        String fragmentShader = "#ifdef GL_ES                \n"
                              + "precision mediump float;    \n"
                              + "#endif                      \n"
                              + "varying vec4 v_color;       \n"
                              + "varying vec2 v_texCoords;   \n"
                              + "uniform sampler2D u_texture;\n"
                              + "void main()                 \n"
                              + "{                           \n"
                              + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);   \n"
                              + "}";

        // make an actual shader from our strings
        meshShader = new ShaderProgram(vertexShader, fragmentShader);

        // check there's no shader compile errors
        if (meshShader.isCompiled() == false)
            throw new IllegalStateException(meshShader.getLog());
    }

    public void dispose() {
        mesh.dispose();
        meshShader.dispose();
    }

	public Beam getBeam() {
		return beam;
	}

	public void setBeam(Beam beam) {
		this.beam = beam;
	}

}