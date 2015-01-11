package com.hubclub.RChop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hubclub.RChop.Screens.GameScreen;
import com.hubclub.RChop.Screens.LevelEditorScreen;

public class RCgame extends Game{
	public static SpriteBatch batch;
	

	public void create() {
		batch = new SpriteBatch();
	//	setScreen(new GameScreen());
		setScreen(new LevelEditorScreen());
	}
	
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0.2f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen
		
		this.getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	public void dispose(){
		batch.dispose();
	}
}
