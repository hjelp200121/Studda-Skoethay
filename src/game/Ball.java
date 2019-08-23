package game;
import processing.core.PApplet;

public class Ball extends PApplet{
	Vector position, velocity;
	public static float radius = 25;
	public static float friction = 0.95f;
	public float rotation = 5;
	
	Ball(Vector pos, Vector vel) {
		position = pos;
		velocity = vel;
	}
	
	void update() {
		velocity.mul(friction);
		position.add(velocity);
		
		pushMatrix();
		translate(position.x, position.y);
		rotate(rotation);
		ellipse(0, 0, radius, radius);
		popMatrix();
	}
}