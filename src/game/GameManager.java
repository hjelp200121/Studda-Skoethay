package game;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import processing.core.PApplet;

public class GameManager extends PApplet {
	
	// Static singleton instance.
	static GameManager gm = null;

	/* The C&Bs */
	Deque<Ball> ammunition;
	List<Ball> balls;
	Cannon C;

	/** size and initialisation */
	public void settings() {
		System.out.println("Starting application...");
		/* Set the static singleton instance. */
		gm = this;
		fullScreen();
		/* Initialise the stack of ammunition. */
		ammunition = new ArrayDeque<Ball>();
		balls = new ArrayList<Ball>();
	}
	
	/** basic settings */
	public void setup() {
		frameRate(60);
		smooth();
		background(255);
		/* Initialise the cannon. */
		C = new Cannon(new Vector(3f, 3f), new Vector(1f, 1f),ammunition,-10,0,10,5,10);
		/* Load ammunition into the stack. */
	}
	
	/** drawing everything */
	public void draw() {
		background(255);
		/* Draw the cannon and balls. */
		for (Ball b : ammunition) {
			b.update();
		}
		C.show();
	}
	
	/** spawning a ball when space is pressed */
	public void keyPressed() {
		/* When the user presses 'space',
		 * add a new ball to the stack. */
		if (key == ' ') {
			ammunition.push(new Ball(new Vector(1,Transform.UPH-1), new Vector(0.7f, 0.7f), new Vector(16f,2f), 20f));
		}
	}
}