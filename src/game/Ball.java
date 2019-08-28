package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Ball {
	static GameManager gm = GameManager.gm;

	public Transform transform;
	public Vector velocity;
	public static float friction = 0.5f;
	public float angularVelocity;
	static String ballPath = "data/cannonBall.png";
	static String ballPathAlt = "data/cannonBallAlt.png";
	private PImage cannonBall;
	static public Vector gravity = Vector.mul(Vector.down(), 10f);
	private int randBall;

	/** Constructor for a ball */
	Ball(Vector pos, Vector scale, Vector vel, float angularVel) {
		transform = new Transform(pos, scale, 0f);
		velocity = vel;
		angularVelocity = angularVel;
		randBall = PApplet.floor(gm.random(10));
		if (randBall == 0) {
			this.cannonBall = gm.loadImage(ballPathAlt);
		} else {
			this.cannonBall = gm.loadImage(ballPath);
		}

		cannonBall.resize((int) (cannonBall.width * transform.scale.x * .5f),
				(int) (cannonBall.height * transform.scale.y * .5f));
	}

	/** Constructor for a ball */
	Ball(Vector pos, Vector vel, float angularVel) {
		this(pos, Vector.one(), vel, angularVel);
	}

	void update() {

		// changing position and speed
		velocity.sub(Vector.div(Vector.mul(velocity, friction), (gm.frameRate)));
		velocity.add(Vector.div(gravity, gm.frameRate));

		if (Math.abs(velocity.x) + Math.abs(velocity.y) < 0.01) {
			velocity.mul(0);
			angularVelocity = 0;
		}

		transform.position.add(Vector.div(velocity, gm.frameRate));
		transform.rotation += angularVelocity / gm.frameRate;

		/** wall bounds */
		if (transform.position.x < 0 + AngleCollision()) {
			velocity.x *= -1;
			transform.position.x = 0 + AngleCollision();
			doCollision();
		}
		if (transform.position.x > Transform.UPW - AngleCollision()) {
			velocity.x *= -1;
			transform.position.x = Transform.UPW - AngleCollision();
			doCollision();
		}
		if (transform.position.y < 0 + AngleCollision()) {
			velocity.y *= -1;
			transform.position.y = 0 + AngleCollision();
			doCollision();
		}
		if (transform.position.y > Transform.UPH - AngleCollision()) {
			velocity.y *= -1;
			transform.position.y = Transform.UPH - AngleCollision();
			doCollision();
		}

		// Drawing the ball and rotation
		gm.imageMode(PApplet.CENTER);
		gm.pushMatrix();
		gm.translate(transform.toScreenPoint().x, transform.toScreenPoint().y);
		gm.rotate(transform.rotation);
		gm.image(cannonBall, 0, 0);
		gm.popMatrix();
	}

	/** changes rotation when hitting a wall/ground */
	void doCollision() {
		angularVelocity *= (float) -Math.cos(transform.rotation);
	}

	/** makes the hitbox as a function of the angle */
	float AngleCollision() {
		return (float) (-Math.abs(Math.sin(transform.rotation * 2 + Math.PI / 2)) * 0.4 + 1.4) * transform.scale.x / 2f;
	}
}