package game;

import java.util.Deque;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Cannon {
	/* Universal barrel offset before scaling */
	private static Vector _barrelOffset = new Vector(.1f, .5f);

	static GameManager gm = GameManager.gm;
	/* The angle of the cannon barrel in its neutral position. */
	static float basisAngle = PApplet.HALF_PI / 2f;
	
	static float minCharge = 0.5f;
	static float maxCharge = 2f;

	/* The image resources. */
	static String basePath = "data/cannonBase.png";
	static String barrelPath = "data/cannonBarrel.png";

	public Transform transform;
	/* A stack of ammunition for the cannon to use. */
	Deque<Ball> ammunition;
	/* Different cannon-parameters. */
	public float angle, minAngle, maxAngle, rotationSpeed, power;
	/* The offset of the cannon barrel relative to the object position. */
	public Vector barrelOffset;
	
	/* The ball currently loaded into the cannon. */
	private Ball loadedBall = null;

	private PImage cannonBase, cannonBarrel;

	public Cannon(Vector position, Vector scale, Deque<Ball> ammunition, float minAngle, float angle, float maxAngle,
			float rotationSpeed, float power) {
		this.transform = new Transform(position, scale, 0f);
		this.ammunition = ammunition;
		this.angle = angle;
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		this.rotationSpeed = rotationSpeed;
		this.power = power;
		this.barrelOffset = new Vector(_barrelOffset.x * scale.x, _barrelOffset.y * scale.y);

		this.cannonBase = gm.loadImage(basePath);
		this.cannonBarrel = gm.loadImage(barrelPath);

		/*
		 * Resize the images to fit the world coordinate system. The cannon barrel is
		 * scaled to be larger than the base.
		 */
		cannonBarrel.resize((int) (gm.width / Transform.UPW * transform.scale.x * 2f),
				(int) (gm.height / Transform.UPH * transform.scale.y * 2f));
		cannonBase.resize((int) (gm.width / Transform.UPW * transform.scale.x),
				(int) (gm.height / Transform.UPH * transform.scale.y));
	}

	/**
	 * Rotate the cannon at its speed, unless it would exceed the minimal or maximal
	 * angle.
	 * 
	 * @param ccw Should the cannon rotate counter clock wise? If false, rotates
	 *            clock wise.
	 */
	public void rotate(boolean ccw) {
		float deltaAngle = rotationSpeed / gm.frameRate;
		/* This should be !ccw, but this works. */
		if (ccw) {
			deltaAngle *= -1;
		}
		/* Apply the angle. */
		angle += deltaAngle;
		/* Check of the angle is too large or too small. */
		if (angle < minAngle) {
			angle = minAngle;
		} else if (angle > maxAngle) {
			angle = maxAngle;
		}
	}

	/**
	 * Update the cannon. This is synonymous to the {@link #draw() draw} method.
	 */
	public void update() {
		draw();
	}

	/**
	 * Draw the cannon on the screen.
	 */
	public void draw() {
		gm.imageMode(PApplet.CENTER);
		gm.pushMatrix();
		gm.translate(transform.toScreenPoint().x, transform.toScreenPoint().y);
		gm.rotate(angle);
		gm.translate(Transform.toScreenScale(barrelOffset).x, Transform.toScreenScale(barrelOffset).y);
		gm.image(cannonBarrel, 0, 0);
		gm.translate(-Transform.toScreenScale(barrelOffset).x, -Transform.toScreenScale(barrelOffset).y);
		gm.rotate(-angle);
		gm.image(cannonBase, 0, 0);
		gm.popMatrix();
	}
	
	/**
	 * Attempt to load a ball from the {@link #ammunition ammunition stack} into
	 * the cannon. If there is no ammunition to load, or there is already ammunition returns {@code false},
	 * otherwise {@code true}.
	 * 
	 * @return {@code true} when loading the ammunition successfully, otherwise {@code false}.
	 */
	public boolean loadCannon () {
		if (loadedBall != null || ammunition.isEmpty()) {
			return false;
		}
		loadedBall = ammunition.pop();
		return true;
	}
	
	/**
	 * Returns {@code true} or {@code false} whether or not the cannon is loaded. 
	 * @return
	 */
	public boolean isLoaded () {
		if (loadedBall == null) {
			return false;
		}
		return true;
	}

	/**
	 * Attempt to shoot the {@link #loadedBall loaded ball} and put
	 * it into the {@link game.GameManager#balls list of balls}.
	 * 
	 * If there is a loaded ball for the cannon to shoot, it will return {@code true},
	 * otherwise {@code false}.
	 * 
	 * @return {@code true} when there is a loaded ball to shoot, otherwise {@code false}.
	 */
	public boolean shoot(float charge) {
		if (loadedBall != null) {
			/* Correctly position the ball inside the barrel. */
			Vector pos = new Vector(barrelOffset);
			pos.rotate(-angle);
			pos.add(transform.position);
			/* Calculate the force vector. */
			Vector force = new Vector(1f, 0f);
			// Add a little extra angle to compensate for gravity
			force.rotate(Cannon.basisAngle - angle + PConstants.PI / 36f);
			force.mul(power);
			force.mul(minCharge + charge * (maxCharge - minCharge));
			/* Assign a random rotation to the ball. */
			float angularVel = gm.random(20f) - 10f;

			loadedBall.transform.position = pos;
			loadedBall.velocity.add(force);
			loadedBall.angularVelocity = angularVel;

			/* Add the ball to the list of balls in the scene. */
			gm.balls.add(loadedBall);
			loadedBall = null;

			return true;
		}
		return false;
	}
}
