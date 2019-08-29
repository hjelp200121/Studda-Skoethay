package game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import processing.core.PApplet;

public class GameManager extends PApplet {

	static final int AMMO_STACK_HEIGHT = 4;
	static final Vector AMMO_STACK_POS = new Vector(2f, 3f);
	static final Vector AMMO_SIZE = new Vector(.5f, .5f);

	// Static singleton instance.
	static GameManager gm = null;

	/* The C&Bs */
	Deque<Ball> ammunition;
	List<Ball> balls;
	Cannon cannon;

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
		/* Initialise the cannon. */
		cannon = new Cannon(new Vector(3f, 3f), new Vector(1f, 1f), ammunition, -PI / 5, 0, PI / 5, PI / 4, 10);
		/* Load ammunition into the stack. */
		refillAmmunition();
	}

	/** drawing everything */
	public void draw() {

		if (pressingUp && !pressingDown) {
			cannon.rotate(true);
		} else if (pressingDown && !pressingUp) {
			cannon.rotate(false);
		}

		background(255);
		/* Draw the cannon and balls. */
		for (Ball b : ammunition) {
			b.update();
		}
		cannon.show();
	}

	public void refillAmmunition() {
		int stack = AMMO_STACK_HEIGHT;
		int skip = ammunition.size();

		float y = AMMO_STACK_POS.y;
		while (stack > 0) {
			float x = AMMO_STACK_POS.x - stack / 2f * AMMO_SIZE.x;
			for (int i = 0; i < stack; i++) {
				if (skip == 0) {
					Vector pos = new Vector(x, y);
					Vector scale = new Vector(AMMO_SIZE);
					Ball ball = new Ball(pos, scale, Vector.zero(), 0f);
					ball.kinematic = false;
					ammunition.add(ball);
				} else {
					skip--;
				}
				x += AMMO_SIZE.x * 0.9f; // ???
			}
			y += AMMO_SIZE.y * 0.9f; // ?????
			stack--;
		}
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
				ammunition.push(new Ball(pos, new Vector(0.5f, 0.5f), rot, random(40f) - 20f));
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