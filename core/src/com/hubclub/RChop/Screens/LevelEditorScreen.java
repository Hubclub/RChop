package com.hubclub.RChop.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.hubclub.RChop.Enviroment.WorldBuilder;

public class LevelEditorScreen implements Screen {
	World world;
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	OrthographicCamera camera;
	WorldBuilder wbuilder;
	
	public LevelEditorScreen (){
		wbuilder = new WorldBuilder();
		wbuilder.createLevel(0);
		world = wbuilder.construct();
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 20, 12);
		camera.update();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0.2f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen
		
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
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
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
