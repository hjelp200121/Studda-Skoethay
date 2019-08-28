package game;

import java.util.Deque;

import processing.core.PApplet;
import processing.core.PImage;

public class Cannon {
	static final Vector barrelOffset = new Vector(.1f, .5f); // Find appropriate value
	static GameManager gm = GameManager.gm;

	static String basePath = "data/cannonBase.png";
	static String barrelPath = "data/cannonBarrel.png";
	// static String stackPath = "data/cannonBallStack.png";

	public Transform transform;
	Deque<Ball> ammunition;
	public float angle, minAngle, maxAngle, rotationSpeed;
	public float power;

	private PImage cannonBase, cannonBarrel, ballStack;
	/*
	 * The barrel pivot can also be used for the starting point when shooting balls.
	 */

	public Cannon(Vector position, Vector scale, Deque<Ball> ammunition, float minAngle, float angle, float maxAngle,
			float rotationSpeed, float power) {
		this.transform = new Transform(position, scale, 0f);
		this.ammunition = ammunition;
		this.angle = angle;
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		this.rotationSpeed = rotationSpeed;
		this.power = power;

		this.cannonBase = gm.loadImage(basePath);
		this.cannonBarrel = gm.loadImage(barrelPath);
		// this.ballStack = gm.loadImage(stackPath);

		cannonBase.resize((int) (cannonBase.width * transform.scale.x * .5f),
				(int) (cannonBase.height * transform.scale.y * .5f));
		cannonBarrel.resize((int) (cannonBarrel.width * transform.scale.x * .5f),
				(int) (cannonBarrel.height * transform.scale.y * .5f));
	}

	public void show() {
		gm.imageMode(PApplet.CENTER);
		gm.pushMatrix();
		gm.translate(transform.toScreenPoint().x, transform.toScreenPoint().y);
		gm.rotate(angle);
		gm.translate(Transform.toScreenScale(barrelOffset).x * transform.scale.x,
				Transform.toScreenScale(barrelOffset).y * transform.scale.y);
		gm.image(cannonBarrel, 0, 0);
		gm.translate(-Transform.toScreenScale(barrelOffset).x * transform.scale.x,
				-Transform.toScreenScale(barrelOffset).y * transform.scale.y);
		gm.rotate(-angle);
		gm.image(cannonBase, 0, 0);
		gm.popMatrix();
	}

	public void shoot() {

	}
}