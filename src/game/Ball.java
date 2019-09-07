package game;

import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class Ball {
	static GameManager gm = GameManager.gm;

	public Transform transform;
	public Vector velocity;
	public static float airResistance = 0.5f;
	public float angularVelocity;
	static String ballPath = "data/cannonBall.png";
	static String ballPathAlt = "data/cannonBallAlt.png";
	private PImage cannonBall;
	static public Vector gravity = Vector.mul(Vector.down(), 10f);
	private int randBall;
	public float bounceFriction = -0.25f;
	boolean touchingGround = false;
	public List<Target> targets;

	/** Constructor for a ball */
	Ball(Vector pos, Vector scale, Vector vel, float angularVel, List<Target> targets) {
		transform = new Transform(pos, scale, 0f);
		velocity = vel;
		angularVelocity = angularVel;
		randBall = PApplet.floor(gm.random(100));
		this.targets = targets;
		if (randBall == 0) {
			this.cannonBall = gm.loadImage(ballPathAlt);
		} else {
			this.cannonBall = gm.loadImage(ballPath);
		}

		cannonBall.resize((int) (gm.width / Transform.UPW * transform.scale.x),
				(int) (gm.height / Transform.UPH * transform.scale.y));
	}

	/** Constructor for a ball */
	Ball(Vector pos, Vector vel, float angularVel, List<Target> targets) {
		this(pos, Vector.one(), vel, angularVel, targets);
	}

	/**
	 * Update the physics of the ball, and then draw it unto the screen.
	 */
	void update() {

		// changing position and speed
		
		
		velocity.sub(Vector.div(Vector.mul(velocity, airResistance), (gm.frameRate)));
		velocity.add(Vector.div(gravity, gm.frameRate));

		

		transform.position.add(Vector.div(velocity, gm.frameRate));
		transform.rotation += angularVelocity / gm.frameRate;

		/* Check for wall bounds and bounce off of them. */
		if (transform.position.x < 0 + AngleCollision()) {
			velocity.x *= bounceFriction;
			transform.position.x = 0 + AngleCollision();
			doCollision();
		} else if (transform.position.x > Transform.UPW - AngleCollision()) {
			velocity.x *= bounceFriction;
			transform.position.x = Transform.UPW - AngleCollision();
			doCollision();
		} else if (transform.position.y < gm.terrain.groundHeight + AngleCollision()) {
			if (touchingGround) {
				velocity.mul(0);
				angularVelocity *= 0;
				transform.position.y = gm.terrain.groundHeight + AngleCollision();
			} else {
				velocity.y *= bounceFriction;
				transform.position.y = gm.terrain.groundHeight + AngleCollision();
				doCollision();
				touchingGround = true;
			}
		} else if (transform.position.y > Transform.UPH - AngleCollision()) {
			velocity.y *= bounceFriction;
			transform.position.y = Transform.UPH - AngleCollision();
			doCollision();
		} else {
			touchingGround = false;
		}
		targetCollision();
		draw();
	}

	/**
	 * Draw the ball unto the screen.
	 */
	public void draw() {
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
	
	/** destroys the target when it is hit */
	public void targetCollision() {
		int i;
		boolean hit = false;
		for (i = 0; i < targets.size(); i++) {
			Target t = targets.get(i);
			if(Vector.dist(transform.position, t.transform.position ) < AngleCollision() + t.transform.scale.x/2) {
				hit = true;
				break;
			}
		}
		if (hit) {
			gm.score += 1;
			if(gm.highscore < gm.score) {
				gm.highscore = gm.score;
			}
			targets.remove(i);
			gm.ballsToRemove.add(this);
		}
	}

	/** makes the hitbox as a function of the angle */
	float AngleCollision() {
		return (float) (-Math.abs(Math.sin(transform.rotation * 2 + Math.PI / 2)) * 0.4 + 1.4) * transform.scale.x / 2f;
	}
}
