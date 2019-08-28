package game;

import processing.core.PApplet;

public class Vector {
	public float x, y;
	
	/** Constructor for a vector*/
	public Vector (float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/** Alternative to writing Vector */
	public Vector () {
		x = 0;
		y = 0;
	}
	
	/** Constructor for a vector using another vector */
	public Vector (Vector v) {
		x = v.x;
		y = v.y;
	}
	
	/** Alternative to writing Vector2(0, -1). */
	public static Vector down() {
		return new Vector(0, -1);
	}
	
	/** Alternative to writing Vector2(1, 0). */
	public static Vector right() {
		return new Vector(1, 0);
	}
	
	/** Alternative to writing Vector2(0, 1). */
	public static Vector up() {
		return new Vector(0, 1);
	}
	
	/** Alternative to writing Vector2(-1, 0). */
	public static Vector left() {
		return new Vector(-1, 0);
	}
	
	/** Alternative to writing Vector2(1, 1). */
	public static Vector one() {
		return new Vector(1, 1);
	}
	
	/** Alternative to writing Vector2(0, 0). */
	public static Vector zero() {
		return new Vector(0, 0);
	}
	
	/** adding 2 vectors getting a new one out */
	public static Vector add (Vector v1, Vector v2) {
		return new Vector(v1.x + v2.x, v1.y + v2.y);
	}
	
	/** subtracting 2 vectors getting a new one out */
	public static Vector sub (Vector v1, Vector v2) {
		return new Vector(v1.x - v2.x, v1.y - v2.y);
	}
	
	/** multiplying 2 vectors getting a new one out */
	public static Vector mul (Vector v, float d) {
		return new Vector(v.x * d, v.y * d);
		}
	
	/** dividing 2 vectors getting a new one out */
	public static Vector div (Vector v, float d) {
		return new Vector(v.x / d, v.y / d);
	}
	
	public static Vector copy (Vector v) {
		return new Vector(v.x, v.y);
	}
	
	/**
	 * Rotate the vector by 'd' radians.
	 * @param v The vector to rotate
	 * @param d The amount of radians to rotate
	 * @return
	 */
	public static Vector rotate (Vector v, float d) {
		float rx = PApplet.cos(-d) * v.x + PApplet.sin(-d) * v.y;
		float ry = -PApplet.sin(-d) * v.x + PApplet.cos(-d) * v.y;
		return new Vector(rx, ry);
	}
	
	/** adding the value of the second vector to the first */
	public void add (Vector v) {
		x += v.x;
		y += v.y;
	}
	
	/** subtracting the value of the second vector from the first */
	public void sub (Vector v) {
		x -= v.x;
		y -= v.y;
	}
	
	/** multiplying the value of the second vector to the first */
	public void mul (float d) {
		x *= d;
		y *= d;
	}
	
	/** dividing the value of the second vector from the first */
	public void div (float d) {
		x /= d;
		y /= d;
	}
	
	public void rotate (float d) {
		float rx = PApplet.cos(-d) * x + PApplet.sin(-d) * y;
		float ry = -PApplet.sin(-d) * x + PApplet.cos(-d) * y;
		x = rx;
		y = ry;
	}
}