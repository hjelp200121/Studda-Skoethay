package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Target {
	static GameManager gm = GameManager.gm;
	
	static String targetPath = "data/cannonTarget.png";
	private PImage target;
	public float cycleSpeed;
	public Transform transform;
	Vector[] points;
	
	Target(Vector pos, Vector scale, float speed, Vector[] points){
		transform = new Transform(pos, scale, 0f);
		this.points = points;
		cycleSpeed = speed;
		
		this.target = gm.loadImage(targetPath);
		target.resize((int) (gm.width / Transform.UPW * transform.scale.x),
				(int) (gm.height / Transform.UPH * transform.scale.y));
	}
	
	Target(Vector pos, Vector scale){
		this(pos, scale, 0, null);
	}
	
	void update() {
		draw();
	}
	
	public void draw() {
		gm.imageMode(PApplet.CENTER);
		gm.pushMatrix();
		gm.translate(transform.toScreenPoint().x, transform.toScreenPoint().y);
		gm.image(target, 0, 0);
		gm.popMatrix();
	}
}
