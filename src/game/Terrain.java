package game;

import processing.core.PApplet;
import processing.core.PImage;

/* Create class for the terrain of the game */
public class Terrain {
	static GameManager gm = GameManager.gm;
	
	/* the groundHeight variable is chosen when initialising the ground, and affects a few other variables in the program */
	public float groundHeight;
	public Color color;
	Vector[] corners = new Vector[4];
	 
	/*Images and strings for the terrain textures*/
	static String bgPath = "data/backgroundRealistic.png";
	static String gPath = "data/groundRealistic.png";
	private PImage bground, ground;
	
	public Terrain(float groundHeight, Color color){
		this.groundHeight = groundHeight;
		this.color = color;
		/* Creates four vectors to act as the corners of the ground */
		corners[0] = new Vector(0,0);
		corners[1] = new Vector(0,Transform.UPH);
		corners[2] = new Vector(Transform.UPW, Transform.UPH);
		corners[3] = new Vector(Transform.UPW, groundHeight);
		this.bground = gm.loadImage(bgPath);
		this.ground = gm.loadImage(gPath);
		bground.resize(gm.width,gm.height);
	}
	
	/* Method to display the ground texture */
	void show() {
		Vector bl = Transform.toScreenPoint(corners[0]);
		Vector tr = Transform.toScreenPoint(corners[3]);
		Vector btr = Transform.toScreenPoint(corners[2]);
		/*Draws the background and ground*/
		gm.imageMode(PApplet.CORNERS);
		gm.image(bground, bl.x,bl.y,btr.x,btr.y);
		gm.image(ground, bl.x,bl.y,tr.x,tr.y);
	}
}
