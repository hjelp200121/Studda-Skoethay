package game;

import processing.core.PConstants;

public class Terrain {
	static GameManager gm = GameManager.gm;
	
	public float groundHeight;
	public static int color = 0xff9b7653;
	 Vector[] corners = new Vector[4];
	
	public Terrain(float groundHeight){
		this.groundHeight = groundHeight;
		corners[0] = new Vector(0,0);
		corners[1] = new Vector(0,groundHeight);
		corners[2] = new Vector(Transform.UPW, 0);
		corners[3] = new Vector(Transform.UPW, groundHeight);
	}
	
	void show() {
		gm.rectMode(PConstants.CORNERS);
		Vector bl = Transform.toScreenPoint(corners[0]);
		Vector tr = Transform.toScreenPoint(corners[3]);
		gm.fill(color);
		gm.rect(bl.x,bl.y,tr.x,tr.y);
	}
}
