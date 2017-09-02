package com.kevinli.dotdropgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kevinli.dotdropgame.states.GameStateManager;
import com.kevinli.dotdropgame.states.Start;

public class DotDrop extends ApplicationAdapter {
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 1920;

	public static final String TITLE = "Dot Drop";
	private GameStateManager gsm;
	private SpriteBatch batch;

	public ActionResolver ar;
	public static PlayServices playServices;

	private Preferences pref;

	public DotDrop(ActionResolver ar, PlayServices playServices) {
		this.ar = ar;
		this.playServices = playServices;
	}

	@Override
	public void create () {
		pref = Gdx.app.getPreferences("com.kevin.dotdropgame.settings");
		if (!pref.contains("adCounter")) {
			pref.putInteger("adCounter", 0);
			pref.flush();
		}
		pref.putInteger("adCounter", 0);
		pref.flush();
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		if (playServices.isSignedIn()) {
			playServices.signIn();
		}
		gsm.push(new Start(gsm, this));
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
