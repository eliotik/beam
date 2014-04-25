package com.tests.beam;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Sprites {
	
	private final Main main;
	private final TextureAtlas beamAtlas;
	private final TextureAtlas cellsAtlas;
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	public Sprites(Main main) {
		this.main = main;
		beamAtlas = new TextureAtlas(Gdx.files.internal("beam/beam.atlas"));
		cellsAtlas = new TextureAtlas(Gdx.files.internal("background/cells.atlas"));
	}
	
	public void dispose() {
		getBeamAtlas().dispose();
		getCellsAtlas().dispose();
	}
	
	public Sprite get(SpriteType type, String name) {
		if (getSprites().containsKey(name)) return getSprites().get(name);
		switch(type) {
		case BEAM: return getBeamSprite(name);
		case CELL: return getCellSprite(name);
		default:
			return null;
		}
		
	}
	
	private Sprite getCellSprite(String name) {
		Sprite sprite = getCellsAtlas().createSprite(name);
		getSprites().put(name, sprite);
		return sprite;
	}

	private Sprite getBeamSprite(String name) {
		Sprite sprite = getBeamAtlas().createSprite(name);
		getSprites().put(name, sprite);
		return sprite;
	}

	public HashMap<String, Sprite> getSprites() {
		return sprites;
	}

	public void setSprites(HashMap<String, Sprite> sprites) {
		this.sprites = sprites;
	}

	public TextureAtlas getBeamAtlas() {
		return beamAtlas;
	}

	public Main getMain() {
		return main;
	}

	public TextureAtlas getCellsAtlas() {
		return cellsAtlas;
	}
}
