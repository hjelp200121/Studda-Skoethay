package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Ball {
	static GameManager gm = GameManager.gm;
	
	Vector position, velocity;
	public static float radius = 25;
	public static float friction = 0.95f;
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
		//velocity.mul(friction);
		velocity.add(gravity);
		position.add(velocity);
		angle += rotation;
		
		//Drawing the ball and rotation
		gm.imageMode(PApplet.CENTER);
		cannonBall.resize(50,0);
		gm.pushMatrix();
		gm.translate(position.x, position.y);
		gm.rotate(angle);
		gm.image(cannonBall,0,0);
		gm.popMatrix();
	}
}