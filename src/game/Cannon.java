package game;
import java.util.Deque;

import processing.core.PImage;

public class Cannon {
	static final Vector barrelPivotOffset = new Vector(); // Find appropriate value
	static GameManager gm = GameManager.gm;

	static String basePath = "data/cannonBase.png";
	static String barrelPath = "data/cannonBarrel.png";

	public Vector position;
	Deque<Ball> ammunition;
	public double angle, minAngle, maxAngle, rotationSpeed;
	public double power;

	private PImage cannonBase, cannonBarrel;
	/* The barrel pivot can also be used for the starting point when shooting balls. */
	private Vector barrelPivot;
	
	public Cannon(Vector position, Deque<Ball> ammunition, double minAngle, double angle, double maxAngle,
			double rotationSpeed, double power) {
		this.position      = position;
		this.ammunition    = ammunition;
		this.angle         = angle;
		this.minAngle      = minAngle;
		this.maxAngle      = maxAngle;
		this.rotationSpeed = rotationSpeed;
		this.power         = power;
		
		this.cannonBase   = gm.loadImage(basePath);
		this.cannonBarrel = gm.loadImage(barrelPath);
		this.barrelPivot  = Vector.add(position, barrelPivotOffset);
	}
}