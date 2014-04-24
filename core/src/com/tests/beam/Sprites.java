package com.tests.beam;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Sprites {
	
	private final Main main;
	private final TextureAtlas beamAtlas;
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	public Sprites(Main main) {
		this.main = main;
		beamAtlas = new TextureAtlas(Gdx.files.internal("beam/beam.atlas"));
	}
	
	public void dispose() {
		getBeamAtlas().dispose();
	}
	
	public Sprite get(String name) {
		if (getSprites().containsKey(name)) return getSprites().get(name);
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
}
