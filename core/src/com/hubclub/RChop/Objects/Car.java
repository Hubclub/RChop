package com.hubclub.RChop.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.hubclub.RChop.Textures;
import com.hubclub.RChop.Enviroment.Renderer;

public class Car {
	public static final float CAR_WIDTH = 2f;
	public static final float CAR_HEIGHT = 1f;
	public static final float WHEEL_RADIUS = 0.5f;
	public static final float TORQUE_FORWARD = -15f;
	public static final float TORQUE_BACKWARDS = 2f;
	public static final Vector2 HYDRAULIC_FORCE = new Vector2(0, 1000); // 500 N ?? O_o

	public float force;
	public Vector2 hydraulicForceBack = new Vector2();
	public Vector2 hydraulicForceFront = new Vector2();
	private Body car;
	private Body backWheel;
	private Body frontWheel;
	private Sprite carSprite;
	private Sprite frontWheelSprite,backWheelSprite;
	
	public Car(World world,float x, float y){
		// 		create the car Body
		BodyDef carDef = new BodyDef();
		carDef.position.set(x, y);
		carDef.type = BodyType.DynamicBody;
		carDef.fixedRotation = false;
		carDef.allowSleep = false;
		car = world.createBody(carDef);
		
		FixtureDef carFixtureDef = new FixtureDef();
		PolygonShape carShape = new PolygonShape();
		//car shape vertices
		float[] carVertices = { -1,-0.5f,
								-1,0f,
								-0.5f,0.5f,
								-0.2f,0.5f,
								1,0f,
								1,-0.5f};
		carShape.set(carVertices);
		carFixtureDef.shape = carShape;
		carFixtureDef.density = 5f;
		carFixtureDef.restitution = 0.2f;
		carFixtureDef.friction = 0.8f;
		
		car.createFixture(carFixtureDef);
		
		
		// set the sprite as user data
		carSprite = new Sprite(Textures.car);
		carSprite.setScale( CAR_WIDTH / (carSprite.getTexture().getWidth()/Renderer.PIX_PER_METER),
							CAR_HEIGHT / (carSprite.getTexture().getHeight()/Renderer.PIX_PER_METER));
		car.setUserData(carSprite);
		
		//		create the body of the wheels
		BodyDef wheelDef = new BodyDef();
		wheelDef.position.set(x - 0.9f, y - 0.5f);
		wheelDef.type = BodyType.DynamicBody;
		wheelDef.fixedRotation = false;
		wheelDef.allowSleep = false;
		backWheel = world.createBody(wheelDef);
		
		wheelDef.position.set(x + 0.9f, y - 0.5f);
		frontWheel = world.createBody(wheelDef);
		
		// and set the wheel sprites
		backWheelSprite = new Sprite(Textures.wheel);
		backWheelSprite.setScale(WHEEL_RADIUS*2 / (backWheelSprite.getTexture().getWidth()/Renderer.PIX_PER_METER),
								WHEEL_RADIUS*2 / (backWheelSprite.getTexture().getHeight()/Renderer.PIX_PER_METER));
		backWheel.setUserData(backWheelSprite);
		
		frontWheelSprite = new Sprite(Textures.wheel);
		frontWheelSprite.setScale(WHEEL_RADIUS*2 / (frontWheelSprite.getTexture().getWidth()/Renderer.PIX_PER_METER),
								WHEEL_RADIUS*2 / (frontWheelSprite.getTexture().getHeight()/Renderer.PIX_PER_METER));
		frontWheel.setUserData(frontWheelSprite);
		
		// the fixtures
		FixtureDef wheelFixtureDef = new FixtureDef();
		CircleShape wheelShape = new CircleShape();
		wheelShape.setRadius(WHEEL_RADIUS);
		wheelShape.setPosition(new Vector2(0, 0));
		wheelFixtureDef.shape = wheelShape;
		wheelFixtureDef.density=1f;
		wheelFixtureDef.friction = 0.99f;
		backWheel.createFixture(wheelFixtureDef);
		frontWheel.createFixture(wheelFixtureDef);
		
		// the joints
		WheelJointDef wheelJointDef = new WheelJointDef();
		wheelJointDef.initialize(car, backWheel, backWheel.getWorldCenter(), new Vector2(0f, 1f));
		wheelJointDef.collideConnected = false;
		wheelJointDef.frequencyHz = 8;
		world.createJoint(wheelJointDef);
		
		wheelJointDef.initialize(car, frontWheel, frontWheel.getWorldCenter(), new Vector2(0f, 1f));
		world.createJoint(wheelJointDef);
		
	
		
	}
	public void update(){
		// motor force
		backWheel.applyTorque(force, true);
		frontWheel.applyTorque(force, true);
		// back hydraulics
		backWheel.applyForce(hydraulicForceBack.setAngleRad(car.getAngle() - 3.14f/2), backWheel.getWorldCenter(), true);
		car.applyForce(hydraulicForceBack.setAngleRad(car.getAngle() + 3.14f/2), backWheel.getWorldCenter(), true);
		// front hydraulics
		frontWheel.applyForce(hydraulicForceFront.setAngleRad(car.getAngle() - 3.14f/2), frontWheel.getWorldCenter(), true);
		car.applyForce(hydraulicForceFront.setAngleRad(car.getAngle() + 3.14f/2), frontWheel.getWorldCenter(), true);
	}
	
	public void setHydraulicsBack(Vector2 v){
		hydraulicForceBack = v;
	}
	
	public void setHydraulicsFront(Vector2 v){
		hydraulicForceFront = v;
	}
	
	public void setForce(float f){
		force = f;
	}
	
	public Vector2 getPosition(){
		return car.getPosition();
	}
	
}
