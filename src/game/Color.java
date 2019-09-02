package game;

public class Color {
	private int r, g, b, a;

	public Color (int r, int g, int b, int a) {
		setR(r);
		setG(g);
		setB(b);
		setA(a);
	}

	public Color (int r, int g, int b) {
		this(r, g, b, 0xFF);
	}

	public Color (int hex) {
		this(
				0xFF & hex >>> 0,
				0xFF & hex >>> 8,
				0xFF & hex >>> 16,
				0xFF & hex >>> 24
		);
	}
	
	/* Static colors. */

	static Color white () {
		return new Color(0xFFFFFFFF);
	}
	static Color gray () {
		return new Color(0xFF7F7F7F);
	}
	static Color black () {
		return new Color(0xFF000000);
	}
	static Color red () {

		return new Color(0xFFFF0000);
	}
	static Color yellow () {

		return new Color(0xFFFFFF00);
	}
	static Color green () {

		return new Color(0xFF00FF00);
	}
	static Color cyan () {

		return new Color(0xFF00FFFF);
	}
	static Color blue () {

		return new Color(0xFF0000FF);
	}
	static Color magenta () {

		return new Color(0xFFFF00FF);
	}

	static Color mul (Color c, float a) {
		int r = Math.round(c.r * a);
		int g = Math.round(c.g * a);
		int b = Math.round(c.b * a);

		return new Color(r, g, b, 0xFF);
	}

	static Color add (Color c1, Color c2) {
		int r = c1.getR() + c2.getR();
		int g = c1.getG() + c2.getG();
		int b = c1.getB() + c2.getB();
		int a = c1.getA() + c2.getA();

		return new Color(r, g, b, a);
	}

	static Color lerp (Color c1, Color c2, float t) {
		int r = Math.round(c1.r + t * (c2.r - c1.r));
		int g = Math.round(c1.g + t * (c2.g - c1.g));
		int b = Math.round(c1.b + t * (c2.b - c1.b));
		int a = Math.round(c1.a + t * (c2.a - c1.a));

		return new Color(r, g, b, a);
	}

	public int getR () {
		return r;
	}
	public int getG () {
		return g;
	}
	public int getB () {
		return b;
	}
	public int getA () {
		return a;
	}
	public void setR (int r) {
		r = r < 0x00 ? 0x00 : r;
		r = r > 0xFF ? 0xFF : r;
		this.r = r;
	}
	public void setG (int g) {
		g = g < 0x00 ? 0x00 : g;
		g = g > 0xFF ? 0xFF : g;
		this.g = g;
	}
	public void setB (int b) {
		b = b < 0x00 ? 0x00 : b;
		b = b > 0xFF ? 0xFF : b;
		this.b = b;
	}
	public void setA (int a) {
		a = a < 0x00 ? 0x00 : a;
		a = a > 0xFF ? 0xFF : a;
		this.a = a;
	}

	public int toHex () {
		return (r << 0) + (g << 8) + (b << 16) + (a << 24);
	}

	public void mul (float a) {
		int r = Math.round(this.r * a);
		int g = Math.round(this.g * a);
		int b = Math.round(this.b * a);

		setR(r);
		setG(g);
		setB(b);
	}

	public void add (Color c) {
		int r = this.r + c.r;
		int g = this.g + c.g;
		int b = this.b + c.b;
		int a = this.a + c.a;

		setR(r);
		setG(g);
		setB(b);
		setA(a);
	}
}
