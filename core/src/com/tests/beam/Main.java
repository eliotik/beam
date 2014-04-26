package com.tests.beam;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tests.beam.mesh.MeshHelper;
import com.tests.beam.mesh.MeshType;
import com.tests.beam.mesh.Meshes;

public class Main extends ApplicationAdapter {
	public static final boolean Y_DIRECTION_UP = true;
	
	private Sprites sprites;
	private Textures textures;
	private OrthographicCamera camera;
	private Beam beam;
	private Carousel carousel;
	private Texture background;
	private Array<MeshHelper> randomBackgrounds = new Array<MeshHelper>();
	private SpriteBatch batch;
	int tilesW, tilesH;

	@Override
	public void create() {
		setCamera(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		setSprites(new Sprites(this));
		setTextures(new Textures(this));
		setBatch(new SpriteBatch());
		setBackground(getTextures().get("background"));
		getBackground().setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		tilesW = Gdx.graphics.getWidth() / getBackground().getWidth() + 1;
		tilesH = Gdx.graphics.getHeight() / getBackground().getHeight() + 1;
		createRandomBackgrounds();
		
		generateMeshes(MeshType.CAROUSEL);
	}

	private void createRandomBackgrounds() {
		
		//int coeficient = 25 * 15;
		int amount = MathUtils.random(5, 75);
		int cols = Gdx.graphics.getWidth() / 32;
		int rows = Gdx.graphics.getHeight() / 32;
		HashMap<String,MeshHelper> added = new HashMap<String,MeshHelper>();
		
		for (int i = 0; i <= amount; ++i) {
			int x = MathUtils.random(cols);
			int y = MathUtils.random(rows);
			if (!added.containsKey(x+":"+y)) {
				String textureName = "cell_broken";
				int r = MathUtils.random(10);
				if (r >= 7) {
					textureName = "cell_dirty";
				} else if (r >=4 && r < 7) {
					textureName = "cell_blood";
				}
				
				MeshHelper mesh = Meshes.create(
		    			camera, 
		    			new Sprite(getTextures().get(textureName)), 
		    			0, 
		    			x*32 + 4 , y*32 + 1,
		    			32, 32,
		        		null);
				randomBackgrounds.add(mesh);
				added.put(x+":"+y, mesh);
				--amount;
			}
		}
	}

	private void generateMeshes(MeshType type) {
		switch (type) {
		case CAROUSEL:
			createCarousel();
			break;
		case BEAM:
			createBeam();
			break;
		}
	}

	private void createBeam() {
		setBeam(new Beam(this, Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 3, 125, "5f2f32ff", "ffffffff"));
	}

	private void createCarousel() {
		setCarousel(new Carousel(this, 5));
	}

	@Override
	public void resize(int width, int height) {
		tilesW = width / getBackground().getWidth() + 1;
		tilesH = height / getBackground().getHeight() + 1;
	}

	@Override
	public void render() {
		Gdx.graphics.getGL20().glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

		getCamera().update();
		getCamera().setToOrtho(Y_DIRECTION_UP, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		if (getBeam() != null) getBeam().update();
		
		getBatch().begin();
		int tileCount = 30;
		getBatch().draw(getBackground(), 0, 0,
				getBackground().getWidth() * tileCount, 
				getBackground().getHeight() * tileCount, 
	            0, tileCount, 
	            tileCount, 0);
//		getBatch().draw(getBackground(), 0, 0,
//				getBackground().getWidth() * tilesW,
//				getBackground().getHeight() * tilesH, 0, tilesH, tilesW, 0);
		getBatch().end();
		
		for(int i = 0, l = randomBackgrounds.size; i < l; ++i) randomBackgrounds.get(i).drawMesh();
		
		if (getCarousel() != null) getCarousel().render();
		if (getBeam() != null) getBeam().render();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		getSprites().dispose();
		getTextures().dispose();
		getBatch().dispose();
		getBackground().dispose();
		if (getCarousel() != null)
			getCarousel().dispose();
		if (getBeam() != null)
			getBeam().dispose();
		for(int i = 0, l = randomBackgrounds.size; i < l; ++i) randomBackgrounds.get(i).dispose();
	}

	public Sprites getSprites() {
		return sprites;
	}

	public void setSprites(Sprites sprites) {
		this.sprites = sprites;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public Beam getBeam() {
		return beam;
	}

	public void setBeam(Beam beam) {
		this.beam = beam;
	}

	public Carousel getCarousel() {
		return carousel;
	}

	public void setCarousel(Carousel carousel) {
		this.carousel = carousel;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public Texture getBackground() {
		return background;
	}

	public void setBackground(Texture background) {
		this.background = background;
	}

	public Textures getTextures() {
		return textures;
	}

	public void setTextures(Textures textures) {
		this.textures = textures;
	}
}
