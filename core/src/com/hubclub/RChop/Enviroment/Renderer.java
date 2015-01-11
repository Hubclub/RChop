package com.hubclub.RChop.Enviroment;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;

public class Renderer {
	public static final float VP_WIDTH = 20; // hardcoded for now
	public static final float VP_HEIGHT = 12; // same here
	
	public static final float PIX_PER_WIDTH = Gdx.graphics.getWidth() / VP_WIDTH;
	public static final float PIX_PER_HEIGHT = Gdx.graphics.getHeight() / VP_HEIGHT; // to do: remove < & ^
	public static final float PIX_PER_METER = Gdx.graphics.getWidth() / VP_WIDTH;
	
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	public OrthographicCamera camera;
	public static boolean debug = true; // hardcoded for now
	
	Array<Body> bodies;
	
	public Renderer (){
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VP_WIDTH, VP_HEIGHT);
		camera.update();
		bodies = new Array<Body>();
	}
	
	public void render (SpriteBatch batch){
		bodies.clear();
		Enviroment.world.getBodies(bodies);
		Iterator<Body> it = bodies.iterator();
		
		while (it.hasNext()){
			Body b = it.next();
			
			Sprite e = (Sprite) b.getUserData();
			
			if (e != null){
				//Vector2 pos = b.getPosition().cpy().scl(2).sub(v)  http://stackoverflow.com/questions/25006702/drawing-an-image-to-a-box2d-body
				//e.setPosition(  b.getPosition().x * PIX_PER_WIDTH - e.getWidth()/2,
				//				b.getPosition().y * PIX_PER_HEIGHT - e.getHeight()/2 );
				
				e.setPosition( (b.getPosition().x - camera.position.x + VP_WIDTH/2 )* PIX_PER_WIDTH - e.getWidth()/2,
							   (b.getPosition().y - camera.position.y + VP_HEIGHT/2 )* PIX_PER_HEIGHT - e.getHeight()/2);
				
				e.setRotation((float) Math.toDegrees(b.getAngle()));
				e.draw(batch);
			}
		}
		
		batch.end();
		
		if (debug)
		debugRenderer.render(Enviroment.world, camera.combined);
		
		if (Enviroment.car.getPosition().x > camera.position.x){
			camera.position.x = Enviroment.car.getPosition().x;
			camera.update();
		}
	}
}
