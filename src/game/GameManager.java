package game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

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
	public Deque<Ball> ballsToRemove;
	Cannon cannon;
	public Terrain terrain;
	public ChargeBar bar;
	public List<Target> targets;

	/* User input state. */
	boolean pressingUp = false;
	boolean pressingDown = false;
	boolean pressingSpace = false;
	
	/*Variables to do with the intro*/
	boolean introScreen = true, emilShown = false;
	static String emilPath = "data/emilLogo.png";
	private PImage emilLogo;
	float emilTimer;
	int emilColor;
	
	/* Miscellaneous variables*/
	int fadeIn = 280;
	
	/* Variables to do with the score system*/
	int stageCount;
	int score = 0;

	/** size and initialisation */
	public void settings() {
		/* Process command line arguments. */
		if (args != null) {
			for (String arg : args) {
				System.out.println(arg);
				if (arg.equals("--skip-emil")) {
					introScreen = false;
					fadeIn = 0;
				}
			}
		}
		
		System.out.println("Starting application...");
		/* Set the static singleton instance. */
		gm = this;
		fullScreen();
		/* Initialise the stack of ammunition. */
		ammunition = new ArrayDeque<Ball>();
		balls = new ArrayList<Ball>();
		ballsToRemove = new ArrayDeque<Ball>();
		targets = new ArrayList<Target>();
	}

	/** basic settings */
	public void setup() {
		surface.setTitle("Studda Sk�thay");
		frameRate(60);
		smooth();
		background(255);
		stageCount = 1;
		bar = new ChargeBar(new Vector(1f, 5f), new Vector(1.5f, 3f), 1f, 10);
		/* Initialise terrain. */
		terrain = new Terrain(groundHeight, new Color(0xff9b7653));
		/* Initialise the cannon and targets */
		cannon = new Cannon(new Vector(2.5f, groundHeight + 0.5f), new Vector(1f, 1f), ammunition, -PI / 5, 0, PI / 5,
				PI / 4, 10f);
		spawnTargets(3);
		/* Load ammunition into the stack. */
		refillAmmunition();
		/* Initialise emil */
		emilLogo = gm.loadImage(emilPath);
		emilLogo.resize(floor(gm.height*0.5f),floor(gm.height*0.5f));
		emilTimer = gm.frameRate*3;
		emilColor = 256;
	}

	/*Draws intro with logo and moves on to the game*/
	public void draw() {
		if (introScreen) {
			fill(255,255,255,emilColor);
			gm.imageMode(PApplet.CENTER);
			gm.image(emilLogo,width/2,height/2);
			rect(0,0,width,height);
			emilTimer --;
			if (emilColor > -10 && !emilShown) {
				emilColor -= 2;
			} else {
				emilShown = true;
				emilColor +=6;
			}
			if (emilTimer < 0) {
				introScreen = false;
			}
		} else {
			tick();
		}
	}
	
	/** drawing everything */
	public void tick() {
		
		while (ballsToRemove.size() > 0) {
			balls.remove(ballsToRemove.poll());
		}
		
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

		/* Draw & charge the charge bar */
		if (!ammunition.isEmpty() || cannon.isLoaded()) {
			if (pressingSpace) {
				bar.charge();
			}
		bar.draw();
		}
		
		if(targets.size() == 0) {
			stageCount ++;
			spawnTargets(3);
			for(Ball b: balls) {
				ballsToRemove.add(b);
			}
			refillAmmunition();
		} else if(ammunition.size() == 0){
			boolean allStill = true;
			for(Ball b: balls) {
				if(!b.touchingGround) {
					allStill = false;
					break;
				}
			}
			if(allStill && !cannon.isLoaded()) {
				stageCount = 1;
				score = 0;
				while (targets.size() > 0) {
					targets.remove(0);
				}
				spawnTargets(3);
				for(Ball b: balls) {
					ballsToRemove.add(b);
				}
				refillAmmunition();
			}
		}
		
		/* Draws various text*/
		textSize(32);
		fill(255);
		text("Stage: " + stageCount, 30, 40);
		text("Score: " + score, 30, 80);
		
		/* Draw the fade in */
		if (fadeIn > 0) {
			fill(255,255,255,fadeIn);
			gm.rectMode(PApplet.CORNER);
			rect(0,0,width,height);
			fadeIn -= 3;
		}
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
					Ball ball = new Ball(pos, scale, Vector.zero(), 0f, targets);
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
	
	public void spawnTargets (int targetCount) {
		for(int i = 0; i < targetCount; i++) {
			targets.add(new Target(new Vector(random(5f,15f),random(2f,8f)),new Vector(targetSize)));
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
				cannon.loadCannon();
				pressingSpace = true;
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
			} else if (key == ' ') {
				cannon.shoot(bar.chargeAmount);
				bar.chargeAmount = 0f;
				pressingSpace = false;
			}
		}
	}
}
