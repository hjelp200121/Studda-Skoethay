package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Ball {
	static GameManager gm = GameManager.gm;
	
	Vector position, velocity;
	public static float radius = 25;
	public static float friction = 0.99f;
	public float rotation = 1f;
	public float angle = 0f;
	static String ballPath = "data/cannonBall.png";
	private PImage cannonBall;
	static public Vector gravity = Vector.mul(Vector.down(), 0.1f);
	
	/** Constructor for a ball */
	Ball(Vector pos, Vector vel) {
		position = pos;
		velocity = vel;
		this.cannonBall = gm.loadImage(ballPath);
	}
	
	void update() {
		
		//changing position and speed
		velocity.mul(friction);
		velocity.add(gravity);
		/*if (Math.abs(velocity.x) + Math.abs(velocity.y) < 0.1) {
			velocity.mul(0);
			rotation = 0;
			
		} */
		position.add(velocity);
		angle += rotation;
		
		/** wall bounds */
		if (position.x < 0 + AngleCollision()) {
			velocity.x *= -1;
			position.x = 0 + AngleCollision();
			rotation *= 0.75;
		}
		if (position.x > 1200 - AngleCollision()) {
			velocity.x *= -1;
			position.x = 1200 - AngleCollision();
			rotation *= 0.75;
		}
		if (position.y < 0 + AngleCollision()) {
			velocity.y *= -1;
			position.y = 0 + AngleCollision();
			rotation *= 0.75;
		}
		if (position.y > 600 - AngleCollision()) {
			velocity.y *= -1;
			position.y = 600 - AngleCollision();
			rotation *= 0.75;
		}
		
		//Drawing the ball and rotation
		gm.imageMode(PApplet.CENTER);
		cannonBall.resize(50,0);
		gm.pushMatrix();
		gm.translate(position.x, position.y);
		gm.rotate(angle);
		gm.image(cannonBall,0,0);
		gm.popMatrix();
	}
	
	/** makes the hitbox as a function of the angle */
	float AngleCollision() {
		return (float) (-Math.abs (Math.sin (angle * 2 + Math.PI / 2)) * 0.4 + 1.4) * radius;
	}
}