package com.hubclub.RChop.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.hubclub.RChop.Objects.Car;

public class CarInput implements InputProcessor{
	private Vector2 touchPos;
	
	private Car car;
	
	public CarInput(Car car) {
		touchPos = new Vector2();
		this.car = car;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.B)
			car.setHydraulicsBack(Car.HYDRAULIC_FORCE);
		if (keycode == Input.Keys.N)
			car.setHydraulicsFront(Car.HYDRAULIC_FORCE);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.B)
			car.setHydraulicsBack(new Vector2());
		if (keycode == Input.Keys.N)
			car.setHydraulicsFront(new Vector2());
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchPos.set(screenX, screenY);
		if (screenX>Gdx.graphics.getWidth()/2)
			car.setForce(Car.TORQUE_FORWARD);
		else
			car.setForce(Car.TORQUE_BACKWARDS);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		car.setForce(0);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
