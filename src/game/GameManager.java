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
	Cannon cannon;
	public Terrain terrain;

	boolean pressingUp = false;
	boolean pressingDown = false;

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
		/* Initialise terrain. */
		terrain = new Terrain(1);
		/* Initialise the cannon. */
		cannon = new Cannon(new Vector(2.5f, terrain.groundHeight + 0.7f), new Vector(1f, 1f), ammunition, -PI / 5, 0, PI / 5, PI / 4, 10);
		/* Load ammunition into the stack. */
	}

	/** drawing everything */
	public void draw() {

		if (pressingUp && !pressingDown) {
			cannon.rotate(true);
		} else if (pressingDown && !pressingUp) {
			cannon.rotate(false);
		}

		background(255);
		/* Draw the terrain */
		terrain.show();
		/* Draw the cannon and balls. */
		for (Ball b : ammunition) {
			b.update();
			
		}
		cannon.show();
	}

	public void keyPressed() {
		if (key == CODED) {
			if (keyCode == UP) {
				pressingUp = true;
			} else if (keyCode == DOWN) {
				pressingDown = true;
			}
		} else {
			
			if (key == 'w' || key == 'W') {
				pressingUp = true;
			} else if (key == 's' || key == 'S') {
				pressingDown = true;
			} else if (key == ' ') {
				/*
				 * When the user presses 'space', add a new ball to the stack.
				 */
				Vector pos = Vector.copy(cannon.barrelOffset);
				pos.rotate(-cannon.angle);
				pos.add(cannon.transform.position);
				Vector rot = new Vector(10f, 0f);
				rot.rotate(Cannon.basisAngle - cannon.angle + PI / 36f);
				ammunition.push(
						new Ball(pos, new Vector(0.5f, 0.5f), rot, random(40f) - 20f));
			}
		}
	}

	public void keyReleased() {
		if (key == CODED) {
			if (keyCode == UP) {
				pressingUp = false;
			} else if (keyCode == DOWN) {
				pressingDown = false;
			}
		} else {
			if (key == 'w' || key == 'W') {
				pressingUp = false;
			} else if (key == 's' || key == 'S') {
				pressingDown = false;
			}
		}
	}
}