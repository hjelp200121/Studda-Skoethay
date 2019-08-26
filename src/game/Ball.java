package game;

public class Ball {
	static GameManager gm = GameManager.gm;
	
	Vector position, velocity;
	public static float radius = 25;
	public static float friction = 0.95f;
	public float rotation = 5;
	static public Vector gravity = Vector.mul(Vector.down(), 0.1f);
	
	/** Constructor for a ball */
	Ball(Vector pos, Vector vel) {
		position = pos;
		velocity = vel;
	}
	
	void update() {
		
		//changing position and speed
		//velocity.mul(friction);
		velocity.add(gravity);
		position.add(velocity);
		
		//Drawing the ball and rotation
		gm.pushMatrix();
		gm.translate(position.x, position.y);
		gm.rotate(rotation);
		gm.ellipse(0, 0, radius, radius);
		gm.popMatrix();
	}
}