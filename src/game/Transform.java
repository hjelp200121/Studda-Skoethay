package game;


/** Position and scale of an object.
 * 
 *  */
public class Transform {
	public static GameManager gm = GameManager.gm;
	
	/** Units per width. */
	static int UPW = 16;
	/** Units per height. */
	static int UPH = 9;
	
	public Vector position, scale;
	public float rotation;
	
	/** Construct a transform with a position and scale. */
	public Transform(Vector position, Vector scale, float rotation) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
	}
	
	/** Construct a transform with default position and scale. */
	public Transform() {
		this(Vector.zero(), Vector.one(), 0);
	}
	
	/** Return the position of the transform converted to screen points. */
	public Vector toScreenPoint() {
		return toScreenPoint(this.position);
	}
	
	/** Return the scale of the transform converted to screen scale. */
	public Vector toScreenScale() {
		return toScreenScale(this.scale);
	}
	
	/** Move position in the direction and length of 'translation'. */
	public void translate(Vector translation) {
		position.add(translation);
	}
		
	public static Vector toScreenPoint(Vector worldPoint) {
		Vector screenPoint = new Vector();
		screenPoint.x = worldPoint.x * gm.width / UPW;
		screenPoint.y = gm.height - worldPoint.y * gm.height / UPH;
		
		return screenPoint;
	}
	
	public static Vector toWorldPoint(Vector screenPoint) {
		Vector worldPoint = new Vector();
		worldPoint.x = screenPoint.x * UPW / gm.width;
		worldPoint.y = (gm.height - screenPoint.y) * UPH / gm.height;
		
		return screenPoint;
	}
	
	public static Vector toScreenScale(Vector worldScale) {
		Vector screenScale = new Vector();
		screenScale.x = worldScale.x * gm.width / UPW;
		screenScale.y = -worldScale.y * gm.height / UPH;
		
		return screenScale;
	}
	
	public static Vector toWorldScale(Vector screenScale) {
		Vector worldScale = new Vector();
		worldScale.x = screenScale.x * UPW / gm.width;
		worldScale.y = -screenScale.y * UPH / gm.height;
		
		return worldScale;
	}
}
