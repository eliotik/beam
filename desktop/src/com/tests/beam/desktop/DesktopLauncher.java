package com.tests.beam.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tests.beam.Beam;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Beam";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Beam(), config);
	}
}
