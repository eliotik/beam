package com.tests.beam;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Textures {
	private Main main;
	private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public Textures(Main main) {
		this.setMain(main);
	}
	
	public void dispose() {
		Iterator<Entry<String, Texture>> it = getTextures().entrySet().iterator();
	    while (it.hasNext()) {
	        @SuppressWarnings("rawtypes")
			Map.Entry data = (Map.Entry)it.next();
	        ((Texture)data.getValue()).dispose();
	    }
	}

	public Texture get(String name) {
		if (getTextures().containsKey(name)) return getTextures().get(name);
		Texture texture = new Texture(Gdx.files.internal(getTextureFilePath(name)));
		getTextures().put(name, texture);
		return texture;	
	}
	
	private String getTextureFilePath(String name) {
		switch(name) {
		case "cell_clear":
		case "background": return "background/cell_clear.jpg";
		case "cell_blood": return "background/cell_blood.jpg";
		case "cell_broken": return "background/cell_broken.jpg";
		case "cell_dirty": return "background/cell_dirty.jpg";
		}
		return "";
	}

	public HashMap<String, Texture> getTextures() {
		return textures;
	}

	public void setTextures(HashMap<String, Texture> textures) {
		this.textures = textures;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}
}
