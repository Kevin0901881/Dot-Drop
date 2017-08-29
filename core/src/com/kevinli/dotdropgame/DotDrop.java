package com.kevinli.dotdropgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DotDrop extends ApplicationAdapter {
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 1920;

	public static final String TITLE = "Dot Drop";
	private com.kevinli.dotdropgame.states.GameStateManager gsm;
	private SpriteBatch batch;

	public ActionResolver ar;

	public DotDrop(ActionResolver ar) {
		this.ar = ar;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new com.kevinli.dotdropgame.states.GameStateManager();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		gsm.push(new com.kevinli.dotdropgame.states.Start(gsm, this)); // change this to push home screen
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
