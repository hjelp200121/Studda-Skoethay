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