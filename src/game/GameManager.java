package game;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class GameManager extends PApplet {
	
	// Static singleton instance.
	static GameManager gm = null;
	
	List<Ball> balls;

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
		
	}
	
	public void draw() {
		background(255);
		
	}
	
	public void keyPressed() {
		if (key == ' ') {
			balls.add(new Ball(new Vector(1,1), new Vector(2,2)));
		}
	}
}
