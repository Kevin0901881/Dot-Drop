package com.kevinli.dotdropgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kevinli.dotdropgame.DotDrop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Resolution
		config.width = DotDrop.WIDTH;
		config.height = DotDrop.HEIGHT;
		// Game Title
		config.title = DotDrop.TITLE;
		new LwjglApplication(new DotDrop(), config);
	}
}
