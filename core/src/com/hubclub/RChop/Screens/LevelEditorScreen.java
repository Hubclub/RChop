package com.hubclub.RChop.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.hubclub.RChop.Enviroment.WorldBuilder;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class LevelEditorScreen implements Screen, InputProcessor {
	World world;
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	OrthographicCamera camera;
	WorldBuilder wbuilder;
	SpriteBatch batch;
	BitmapFont font;
	ShapeRenderer grid;
	private float VP_WIDTH=20,VP_HEIGHT=12;
	private Vector2 currentBlock;
	private float vLineX,oLineY;
	
	public LevelEditorScreen (){
		wbuilder = new WorldBuilder();
		wbuilder.createLevel(0);
		world = wbuilder.construct();
		currentBlock = new Vector2();
		
		batch = new SpriteBatch();
		grid = new ShapeRenderer();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VP_WIDTH, VP_HEIGHT);
		camera.update();
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void reload(){
		world.dispose();
		world = wbuilder.construct();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0.2f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen
		
		drawGrid();
		batch.begin();
			drawCoord();
		batch.end();
		debugRenderer.render(world, camera.combined);
	}
	
	public void drawCoord(){
		font.draw(batch, "BLOCK " + currentBlock.x + "|" + currentBlock.y, 0, Gdx.graphics.getHeight());
		font.draw(batch, wbuilder.exists(currentBlock) ? "EXISTS" : "DOES NOT EXIST", 0, Gdx.graphics.getHeight() - font.getLineHeight());
		
		font.draw(batch, "CAM_POS:" + camera.position.x + "|" + camera.position.y,0,font.getLineHeight());
	}
	
	public void drawGrid(){
		grid.begin(ShapeType.Line);
			grid.setColor(Color.RED);
			vLineX = (camera.position.x - VP_WIDTH/2)%WorldBuilder.BLOCK_WIDTH;
			if (vLineX > 0){
				grid.line((WorldBuilder.BLOCK_WIDTH - vLineX)*getPixPerWidth(), 0,
						(WorldBuilder.BLOCK_WIDTH - vLineX)*getPixPerWidth(), Gdx.graphics.getHeight());
			}else{
				grid.line(-vLineX*getPixPerWidth(), 0,
					  -vLineX*getPixPerWidth(), Gdx.graphics.getHeight());
			}
			oLineY = (camera.position.y - VP_HEIGHT/2)%WorldBuilder.BLOCK_HEIGHT;
			if (oLineY > 0){
				grid.line(0, (WorldBuilder.BLOCK_HEIGHT - oLineY)*getPixPerHeight(),
						  Gdx.graphics.getWidth(), (WorldBuilder.BLOCK_HEIGHT - oLineY)*getPixPerHeight());
			}else{
				grid.line(0, -oLineY*getPixPerHeight(),
						  Gdx.graphics.getWidth(), -oLineY*getPixPerHeight());
			}
			// draws a crosshair
			grid.line(Gdx.graphics.getWidth()/2 - 10, Gdx.graphics.getHeight()/2, 
					  Gdx.graphics.getWidth()/2 + 10, Gdx.graphics.getHeight()/2, Color.YELLOW, Color.YELLOW);
			grid.line(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - 10, 
					  Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 + 10, Color.YELLOW, Color.YELLOW);
		grid.end();
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
	
	// Input processing thing
	private float pointThreshold = 0.5f;
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.N){
			wbuilder.setNewBlock(currentBlock);
			reload();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private String pressedkey = "";
	private Vector2 lastclick = new Vector2();
	private Vector2 selectedVertex = new Vector2();

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		lastclick.set(screenX,screenY);
		System.out.println(button);
		if (button == 2){
			pressedkey = "mwheel";
		}else if (button == 0){
			pressedkey = "lclick";
			selectedVertex = wbuilder.findNearestVertex(currentBlock,
					getClickWorldPos(screenX, Gdx.graphics.getHeight() - screenY)
					.sub(currentBlock.x*WorldBuilder.BLOCK_WIDTH, currentBlock.y*WorldBuilder.BLOCK_HEIGHT),
					pointThreshold);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		pressedkey = "";
		currentBlock.set((float) Math.floor(camera.position.x/WorldBuilder.BLOCK_WIDTH), 
						 (float) Math.floor(camera.position.y/WorldBuilder.BLOCK_HEIGHT));
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pressedkey == "mwheel"){
			camera.position.add((lastclick.x - screenX)/getPixPerWidth(),
								(screenY - lastclick.y)/getPixPerHeight(),
								0);
			camera.update();
		}else if (pressedkey == "lclick"){
			selectedVertex.sub((lastclick.x - screenX)/getPixPerWidth(),
							   (screenY - lastclick.y)/getPixPerHeight());
			reload();
		}
		
		lastclick.set(screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
	/*	if (camera.zoom + amount >= 1.0f){
			camera.zoom += amount;
		}
		camera.update();*/
		return false;
	}
	
	// MISC
	private Vector2 getClickWorldPos(int x,int y){
		return new Vector2((x - Gdx.graphics.getWidth()/2)/getPixPerWidth(),
						   (y - Gdx.graphics.getHeight()/2)/getPixPerHeight()).add(camera.position.x, camera.position.y);
	}
	private float getPixPerHeight(){
		return (Gdx.graphics.getHeight() / VP_HEIGHT);
	}
	private float getPixPerWidth(){
		return (Gdx.graphics.getWidth() / VP_WIDTH);
	}
}
