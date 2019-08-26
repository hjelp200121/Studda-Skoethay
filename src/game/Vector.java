package game;
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
		return new Vector(0, 1);
	}
	
	/** Alternative to writing Vector2(1, 0). */
	public static Vector right() {
		return new Vector(1, 0);
	}
	
	/** Alternative to writing Vector2(0, 1). */
	public static Vector up() {
		return new Vector(0, -1);
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
	
	public static Vector add (Vector v1, Vector v2) {
		return new Vector(v1.x + v2.x, v1.y + v2.y);
	}
	
	public static Vector sub (Vector v1, Vector v2) {
		return new Vector(v1.x - v2.x, v1.y - v2.y);
	}
	
	public static Vector mul (Vector v, float d) {
		return new Vector(v.x * d, v.y * d);
		}
	
	public static Vector div (Vector v, float d) {
		return new Vector(v.x / d, v.y / d);
	}
		
	public void add (Vector v) {
		x += v.x;
		y += v.y;
	}
	
	public void sub (Vector v) {
		x -= v.x;
		y -= v.y;
	}
	
	public void mul (float d) {
		x *= d;
		y *= d;
	}
	
	public void div (float d) {
		x /= d;
		y /= d;
	}
	
	public void copy (Vector v) {
		x = v.x;
		y = v.y;
	}
}