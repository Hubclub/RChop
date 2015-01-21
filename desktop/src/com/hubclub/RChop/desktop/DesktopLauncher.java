package com.hubclub.RChop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hubclub.RChop.RCgame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1200;
		config.height = 720;
		config.useGL30 = false; // can't use gl3.0 on windows
		new LwjglApplication(new RCgame(), config);
	}
}
