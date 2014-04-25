package com.tests.beam;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tests.beam.mesh.MeshHelper;
import com.tests.beam.mesh.MeshType;
import com.tests.beam.mesh.Meshes;

public class Main extends ApplicationAdapter {
	private Sprites sprites;
	private OrthographicCamera camera;
	private Beam beam;
	private Carousel carousel;
	private Texture background;
	private Array<MeshHelper> randomBackgrounds = new Array<MeshHelper>();
	private SpriteBatch batch;
	int tilesW, tilesH;

	@Override
	public void create() {
		setSprites(new Sprites(this));
		setBatch(new SpriteBatch());
		setBackground(new Texture(Gdx.files.internal("background/cell_clear.jpg")));
		getBackground().setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		createRandomBackgrounds();
		setCamera(new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()));
		generateMeshes(MeshType.BEAM);
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
				String cellName = "cell_broken";
				int r = MathUtils.random(10);
				if (r >= 7) {
					cellName = "cell_dirty";
				} else if (r >=4 && r < 7) {
					cellName = "cell_blood";
				}
				
				MeshHelper mesh = Meshes.create(
		    			this, 
		    			getSprites().get(SpriteType.CELL, cellName), 
		    			0, 
		    			x*32 , y*32,
		    			32, 32,
		        		null);
				randomBackgrounds.add(mesh);
				added.put(x+":"+y, mesh);
				--amount;
			}
		}
		/*
		for (int x = 0; x < rows; ++x) {
			if (amount <= 0) return;
			for (int y = 0; y < cols; ++y) {
				int r = MathUtils.random(0, coeficient);
				int rb = MathUtils.random(r);
				System.out.println(r+" : "+rb);
				if (r < 150 && rb>100 && x%2==MathUtils.random(1) && y%2==MathUtils.random(1)) {
					MeshHelper mesh = Meshes.create(
			    			this, 
			    			getSprites().get(SpriteType.CELL, "cell_blood"), 
			    			0, 
			    			x*32 , y*32,
			    			32, 32,
			        		Color.valueOf("30e957ff"));
					randomBackgrounds.add(mesh);
					--amount;
				}
			}
		}*/
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
		setCarousel(new Carousel(this, 4));
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

		getBatch().begin();
		getBatch().draw(getBackground(), 0, 0,
				getBackground().getWidth() * tilesW,
				getBackground().getHeight() * tilesH, 0, tilesH, tilesW, 0);
		getBatch().end();
		
//		for(int i = 0, l = randomBackgrounds.size; i < l; ++i) randomBackgrounds.get(i).drawMesh();
		MeshHelper mesh = Meshes.create(
    			this, 
    			getSprites().get(SpriteType.CELL, "cell_blood"), 
    			0, 
    			100 , 150,
    			32*7, 32*7,
        		null);
		mesh.drawMesh();
		
		
		
		if (getCarousel() != null)
			getCarousel().render();
		if (getBeam() != null)
			getBeam().render();
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
}
