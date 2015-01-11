package com.hubclub.RChop.Enviroment;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;

public class WorldBuilder {
	public static final float BLOCK_HEIGHT=12;
	public static final float BLOCK_WIDTH=20;
		public class WorldData {
			protected int level;
			protected ArrayList<Block> blocks;
			protected Vector2 carPos;
			
			public WorldData (int level){
				this.level = level;
				carPos = new Vector2();
				blocks = new ArrayList<WorldBuilder.Block>(); // has a max capacity of 10 !
				blocks.add(new Block(0, 0));
			}
		}
		public class Block{
			protected Vector2 blockPos; // block position in the grid
			protected ArrayList<LandBody> landbodies;
			
			public Block(int x, int y){
				blockPos = new Vector2(x, y);
				landbodies = new ArrayList<LandBody>();
				landbodies.add(new LandBody());
			}
		}
		public class LandBody{
			protected ArrayList<Vector2> vertices;
			protected String textureName;
			
			public LandBody(){
				vertices = new ArrayList<Vector2>();
				// a simple rectangle in the center
				vertices.add(new Vector2(8, 4));
				vertices.add(new Vector2(8, 6));
				vertices.add(new Vector2(12, 6));
				vertices.add(new Vector2(12, 4));
			}
		}
	// the actual class:
	private WorldData wdata;
	private Json json;
	
	public WorldBuilder (){
		json = new Json();
	}
	public World construct(){
		/*** Constructs the world variable using the data from the currently loaded WorldData
		 */
		World world = new World(new Vector2(), true);
		
		Iterator<Block> b = wdata.blocks.iterator();
		while (b.hasNext()){
			Block block = b.next();
			// create a body
			BodyDef blockDef = new BodyDef();
			blockDef.position.set(block.blockPos);
			blockDef.type = BodyType.StaticBody;
			Body blockBody = world.createBody(blockDef);
			// add fixtures
			Iterator<LandBody> l = block.landbodies.iterator();
			while (l.hasNext()){
				LandBody land = l.next();
				FixtureDef landDef = new FixtureDef();
				PolygonShape landShape = new PolygonShape();
				Vector2[] a = new Vector2[land.vertices.size()];
				land.vertices.toArray(a);
				landShape.set(a);
				landDef.shape = landShape;
				
				blockBody.createFixture(landDef);
			}
		}
		
		return world;
	}
	
	public void loadWorldData(int level){
		//TO DO
	}
	public void saveWorldData(){
		//TO DO
	}
	public void createLevel(int level){
		wdata = new WorldData(level);
		System.out.println(json.prettyPrint(wdata));
		wdata.level = level;
	}
}
