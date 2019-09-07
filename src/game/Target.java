package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Target {

	public enum MoveType {
		NONE, LOOP, BOUNCE
	}

	static GameManager gm = GameManager.gm;

	static String targetPath = "data/cannonTarget.png";
	private PImage target;
	private float moveProgress = 0f;
	private boolean movingForward = true;

	public float circuitDuration;
	MoveType moveType;
	public Transform transform;
	Vector[] points;

	Target(Vector pos, Vector scale, float circuitDuration, Vector[] points, MoveType moveType) {
		transform = new Transform(pos, scale, 0f);
		this.points = points;
		this.circuitDuration = circuitDuration;
		this.moveType = moveType;

		this.target = gm.loadImage(targetPath);
		target.resize((int) (gm.width / Transform.UPW * transform.scale.x),
				(int) (gm.height / Transform.UPH * transform.scale.y));
	}

	Target(Vector pos, Vector scale) {
		this(pos, scale, 0, null, MoveType.NONE);
	}

	void update() {
		if (moveType == MoveType.LOOP) {
			doLoop();
		} else if (moveType == MoveType.BOUNCE) {
			doBounce();
		}
		draw();
	}

	/** Move according to the loop move type. */
	void doLoop() {

		moveProgress += 1 / (gm.frameRate * circuitDuration);
		if (moveProgress >= 1) {
			moveProgress -= 1;
		}

		int index = PApplet.floor(moveProgress * points.length);
		float remainder = moveProgress * points.length - index;
		/* Make sure index is not out of bounds. */
		index = index >= points.length ? points.length - 1 : index;

		Vector from = points[index];
		Vector to = index == (points.length - 1) ? points[0] : points[index + 1];
		Vector newPos = Vector.lerp(from, to, remainder);

		this.transform.position = newPos;
	}

	/** Move according to the bounce move type. */
	void doBounce() {

		if (movingForward) {
			moveProgress += 1 / (gm.frameRate * circuitDuration);
			if (moveProgress >= 1) {
				moveProgress = 2 - moveProgress;
				movingForward = false;
			}
		} else {
			moveProgress -= 1 / (gm.frameRate * circuitDuration);
			if (moveProgress <= 0) {
				moveProgress = -moveProgress;
				movingForward = true;
			}
		}

		int index = PApplet.floor(moveProgress * (points.length - 1));
		float remainder = moveProgress * (points.length - 1) - index;
		/* Make sure index is not out of bounds. */
		index = index >= points.length ? points.length - 1 : index;

		Vector from = points[index];
		Vector to = points[index + 1];
		Vector newPos = Vector.lerp(from, to, remainder);

		this.transform.position = newPos;
	}

	public void draw() {
		gm.imageMode(PApplet.CENTER);
		gm.pushMatrix();
		gm.translate(transform.toScreenPoint().x, transform.toScreenPoint().y);
		gm.image(target, 0, 0);
		gm.popMatrix();
	}
}
