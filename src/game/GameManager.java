package game;
import java.util.ArrayDeque;
import java.util.Deque;

import processing.core.PApplet;

public class GameManager extends PApplet {
	
	// Static singleton instance.
	static GameManager gm = null;

	Deque<Ball> balls;
	Cannon C;

	/** size and initialisation */
	public void settings() {
		System.out.println("Starting application...");
		gm = this;
		
		size(1000, 1000);
		balls = new ArrayDeque<Ball>();
	}
	
	/** basic settings */
	public void setup() {
		frameRate(60);
		smooth();
		background(255);
		C = new Cannon(new Vector(400,400),null,50,30,10,5,10);
		
	}
	
	/** drawing everything */
	public void draw() {
		background(255);
		C.show();
		
		for (Ball b : balls) {
			b.update();
		}
	}
	
	/** spawning a ball when space is pressed */
	public void keyPressed() {
		if (key == ' ') {
			balls.add(new Ball(new Vector(0,0), new Vector(5,0)));
		}
	}
}