package game;
import java.util.ArrayDeque;
import java.util.Deque;

import processing.core.PApplet;

public class GameManager extends PApplet {
	
	// Static singleton instance.
	static GameManager gm = null;

	/* The C&Bs */
	Deque<Ball> balls;
	Cannon C;

	/** size and initialisation */
	public void settings() {
		System.out.println("Starting application...");
		
		/* Set the static singleton instance. */
		gm = this;
		size(1000, 1000);
		/* Initialize the stack of ammunition. */
		balls = new ArrayDeque<Ball>();
	}
	
	/** basic settings */
	public void setup() {
		frameRate(60);
		smooth();
		background(255);
		/* Initialize the cannon. */
		C = new Cannon(new Vector(400,400),balls,50,30,10,5,10);
		/* Load ammunition into the stack. */
		balls.push(new Ball (new Vector(50,500), new Vector(5,0)));
	}
	
	/** drawing everything */
	public void draw() {
		background(255);
		/* Draw the cannon and balls. */
		C.show();
		for (Ball b : balls) {
			b.update();
		}
	}
	
	/** spawning a ball when space is pressed */
	public void keyPressed() {
		/* When the user presses 'space',
		 * add a new ball to the stack. */
		if (key == ' ') {
			balls.push(new Ball(new Vector(0,0), new Vector(5,0)));
		}
	}
}