public class Vector {
	public double x, y;
	
	public Vector (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector () {
		x = 0;
		y = 0;
	}
	
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

	
	public void add (Vector v) {
		x += v.x;
		y += v.y;
	}
	
	public void sub (Vector v) {
		x -= v.x;
		y -= v.y;
	}
	
	public void mul (double d) {
		x *= d;
		y *= d;
	}
	
	public void div (double d) {
		x /= d;
		y /= d;
	}
	
	public void copy (Vector v) {
		x = v.x;
		y = v.y;
	}
}