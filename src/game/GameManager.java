package game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import processing.core.PApplet;

public class GameManager extends PApplet {

	/* The height of the terain. */
	public static final float groundHeight = 1f;

	/* Constants for the ammunition stack, and individual balls. */
	static final int AMMO_STACK_HEIGHT = 4;
	static final Vector AMMO_STACK_POS = new Vector(1.5f, groundHeight);
	static final Vector AMMO_SIZE = new Vector(.5f, .5f);

	/* constant for target size */
	static final Vector targetSize = new Vector(1.5f,1.5f);
	
	// Static singleton instance.
	static GameManager gm = null;

	/* The C&Bs */
	Deque<Ball> ammunition;
	public List<Ball> balls;
	Cannon cannon;
	public Terrain terrain;
	public List<Target> targets;

	/* User input state. */
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
		targets = new ArrayList<Target>();
	}

	/** basic settings */
	public void setup() {
		surface.setTitle("Studda Skøthay");
		frameRate(60);
		smooth();
		background(255);
		/* Initialise terrain. */
		terrain = new Terrain(groundHeight, new Color(0xff9b7653));
		/* Initialise the cannon and targets */
		cannon = new Cannon(new Vector(2.5f, groundHeight + 0.5f), new Vector(1f, 1f), ammunition, -PI / 5, 0, PI / 5,
				PI / 4, 15f);
		targets.add(new Target(new Vector(random(5f,15f),random(2f,8f)),new Vector(targetSize)));
		targets.add(new Target(new Vector(random(5f,15f),random(2f,8f)),new Vector(targetSize)));
		targets.add(new Target(new Vector(random(5f,15f),random(2f,8f)),new Vector(targetSize)));
		/* Load ammunition into the stack. */
		refillAmmunition();
	}

	/** drawing everything */
	public void draw() {

		/* Handle user input. */
		if (pressingUp && !pressingDown) {
			cannon.rotate(true);
		} else if (pressingDown && !pressingUp) {
			cannon.rotate(false);
		}

		background(255);
		/* Draw the terrain */
		terrain.show();
		/* Draw the ammunition pile. */
		for (Ball b : ammunition) {
			b.draw();

		}
		/* Draw the active balls. */
		for (Ball b : balls) {
			b.update();

		}
		/* Draw the targets */
		for (Target t : targets) {
			t.update();
		}
		
		/* Draw the cannon. */
		cannon.update();
	}

	/** Instantly refill the stack of ammunition. */
	public void refillAmmunition() {
		/* Prevent scaling from causing 1 pixel-wide gaps. */
		float margin = 0.95f;

		int stack = AMMO_STACK_HEIGHT;
		int skip = ammunition.size();

		float y = AMMO_STACK_POS.y + AMMO_SIZE.y / 2f * margin;
		while (stack > 0) {
			float x = AMMO_STACK_POS.x - stack / 2f * AMMO_SIZE.x;
			for (int i = 0; i < stack; i++) {
				if (skip == 0) {
					Vector pos = new Vector(x, y);
					Vector scale = new Vector(AMMO_SIZE);
					Ball ball = new Ball(pos, scale, Vector.zero(), 0f);
					gm.ammunition.push(ball);
				} else {
					skip--;
				}
				x += AMMO_SIZE.x * margin;
			}
			y += AMMO_SIZE.y * margin;
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
				cannon.shoot();
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
