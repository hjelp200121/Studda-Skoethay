package game;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class GameManager extends PApplet {
	
	// Static singleton instance.
	static GameManager gm = null;
	
	List<Ball> balls;
	Cannon C;

	public void settings() {
		System.out.println("Starting application...");
		gm = this;
		
		size(1000, 1000);
		balls = new ArrayList<Ball>();
	}
	
	public void setup() {
		frameRate(60);
		smooth();
		background(255);
		C = new Cannon(new Vector(400,400),null,50,30,10,5,10);
		
	}
	
	public void draw() {
		background(255);
		C.show();
	}
	
	public void keyPressed() {
		if (key == ' ') {
			balls.add(new Ball(new Vector(1,1), new Vector(2,2)));
		}
	}
}
