package game;

import processing.core.PConstants;

/* Create class for the terrain of the game */
public class Terrain {
	static GameManager gm = GameManager.gm;
	
	/* the groundHeight variable is chosen when initialising the ground, and affects a few other variables in the program */
	public float groundHeight;
	public Color color;
	 Vector[] corners = new Vector[4];
	
	public Terrain(float groundHeight, Color color){
		this.groundHeight = groundHeight;
		this.color = color;
		/* Creates four vectors to act as the corners of the ground */
		corners[0] = new Vector(0,0);
		corners[1] = new Vector(0,groundHeight);
		corners[2] = new Vector(Transform.UPW, 0);
		corners[3] = new Vector(Transform.UPW, groundHeight);
	}
	
	/* Method to display the ground texture */
	void show() {
		gm.rectMode(PConstants.CORNERS);
		Vector bl = Transform.toScreenPoint(corners[0]);
		Vector tr = Transform.toScreenPoint(corners[3]);
		gm.fill(color.toHex());
		gm.rect(bl.x,bl.y,tr.x,tr.y);
	}
}
