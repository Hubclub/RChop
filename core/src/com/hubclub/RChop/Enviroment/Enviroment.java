package com.hubclub.RChop.Enviroment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.hubclub.RChop.Control.CarInput;
import com.hubclub.RChop.Objects.Car;

public class Enviroment {
	public static final Vector2 G_ACCELERATION = new Vector2(0, -10f);
	
	public static final float LEVEL_HEIGHT = 12;
	//public static final float LEVEL_WIDTH = 20;
	
	protected static World world;
	protected static Car car;
	
	public Enviroment() {
		setDefaultLevel();
		car = new Car(world, 8.5f, 7);
		
		Gdx.input.setInputProcessor(new CarInput(car));
	}
	
	public void update (float delta){
		car.update();
		
		world.step(delta, 12, 4);
	}
	
	public void setDefaultLevel(){
		world = new World(G_ACCELERATION, true); //doSleep is on to improve performance
		float[][] groundVertices = {{0,2}, {5,2}, {10,6}, {15,6}, {20,2}, {25,2}, {50,3},{60,1},{70,4},{80,3},{90,6},{100,1},{110,4},{120,9} };
		
		//create the static body terrain
		BodyDef terrainDef = new BodyDef();
		terrainDef.position.set( new Vector2(0, 0) );
		terrainDef.type = BodyType.StaticBody;
		Body terrain = world.createBody(terrainDef);
		
		//add fixtures to the terrain (ground, roof, walls etc)
		setGround(groundVertices, terrain);
		createWall(terrain, 0 ,0 ,0.5f ,12);
		
		
		
	}
	private void createWall(Body terrain, float x, float y, float width, float height){
		FixtureDef wallDef = new FixtureDef();
		
		PolygonShape wallShape = new PolygonShape();
		wallShape.setAsBox(width/2, height/2, new Vector2(x+width/2, y+height/2), 0);
		wallDef.shape = wallShape;
		terrain.createFixture(wallDef);
	}
	
	private void setGround(float[][] gvc, Body terrain){
		if (gvc.length <3){
			System.out.println("WARNING: ADD MORE VERTICES TO MAKE GROUND");
			return;
		}
		FixtureDef groundDef = new FixtureDef();
		EdgeShape edgeShape = new EdgeShape();
		groundDef.shape = edgeShape;
		groundDef.friction = 1f;
		
		// create the first line
		edgeShape.set(new Vector2 (gvc[0][0],gvc[0][1]),
				  new Vector2 (gvc[1][0],gvc[1][1]));
		//edgeShape.setVertex3(gvc[2][0], gvc[2][1]);
		//edgeShape.setHasVertex3(true);
		
		terrain.createFixture(groundDef);
		
		//the middle lines
		for (int i=1 ; i<gvc.length-2; i++){
			edgeShape.set(new Vector2 (gvc[i][0],gvc[i][1]),
						  new Vector2 (gvc[i+1][0],gvc[i+1][1]));
			//edgeShape.setVertex0(gvc[i-1][0], gvc[i-1][1]);
			//edgeShape.setVertex0(gvc[i+2][0], gvc[i+2][1]);
			//edgeShape.setHasVertex0(true);
			//edgeShape.setHasVertex3(true);
			
			terrain.createFixture(groundDef);
		}
		
		//last line
		edgeShape.set(new Vector2 (gvc[gvc.length-2][0],gvc[gvc.length-2][1]),
				  new Vector2 (gvc[gvc.length-1][0],gvc[gvc.length-1][1]));
		//edgeShape.setVertex0(gvc[gvc.length-3][0], gvc[gvc.length-3][1]);
	//	edgeShape.setHasVertex0(true);
		
		terrain.createFixture(groundDef);
	}
}
