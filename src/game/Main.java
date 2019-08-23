package game;
import processing.core.PApplet;
import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet {
	
	List<Ball> balls;

	public static void main(String[] args) {
		PApplet.main("Main");
	}

	public void settings() {
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