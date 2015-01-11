package com.hubclub.RChop.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hubclub.RChop.RCgame;
import com.hubclub.RChop.Enviroment.Enviroment;
import com.hubclub.RChop.Enviroment.Renderer;

public class GameScreen implements Screen {
	private SpriteBatch batch;
	
	private Enviroment env;
	private Renderer renderer; 
	
	public GameScreen() {
		this.batch = RCgame.batch;
		
		env = new Enviroment();
		renderer = new Renderer();
	}
	
	@Override
	public void render(float delta) {
		env.update(delta);
		
		batch.begin();
			renderer.render(batch);
		//batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
