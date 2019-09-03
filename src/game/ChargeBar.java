package game;

import processing.core.PConstants;

public class ChargeBar {

	static GameManager gm = GameManager.gm;
	static float margin = 0.95f;
	static float rectRoundness;

	public Transform transform;
	public int barCount = 10;
	/* The colors of the bars when least, most & not charged. */
	public Color least, most, empty;
	/* The background color of the charge bar. */
	public Color bg;

	public float chargeAmount = 0f;
	public float chargeSpeed;
	
	boolean chargingUp = true;

	public ChargeBar(Vector pos, Vector scale, float rot, float chargeSpeed, int barCount, Color least, Color most, Color empty,
			Color bg) {
		this.transform = new Transform(pos, scale, rot);
		this.chargeSpeed = chargeSpeed;
		this.barCount = barCount;
		this.least = least;
		this.most = most;
		this.empty = empty;
		this.bg = bg;
	}

	public ChargeBar(Vector pos, Vector scale, float chargeSpeed, int barCount) {
		this(pos, scale, 0f, chargeSpeed, barCount, Color.red(), Color.green(), new Color(0xAA555555), new Color(0xAAAAAAAA));
	}
	
	public ChargeBar(Vector pos, Vector scale) {
		this(pos, scale, 0f, 1f, 10, Color.green(), Color.red(), new Color(0xAA555555), new Color(0xAAAAAAAA));
	}
	
	public void charge () {
		/* Determine whether to charge up or down. */
		int mul = chargingUp ? 1 : -1;
		
		chargeAmount += chargeSpeed / gm.frameRate * mul;
		if (chargeAmount < 0f) {
			chargingUp = true;
			chargeAmount = -chargeAmount;
		} else if (chargeAmount > 1f) {
			chargingUp = false;
			chargeAmount = 2f - chargeAmount;
		}
	}

	public void draw() {
		gm.pushMatrix();

		gm.translate(transform.toScreenPoint().x, transform.toScreenPoint().y);
		gm.rotate(transform.rotation);

		gm.rectMode(PConstants.CENTER);
		gm.fill(bg.toHex());
		gm.noStroke();
		gm.rect(transform.position.x, transform.position.y, transform.toScreenScale().x, transform.toScreenScale().y);

		float ySize = transform.toScreenScale().y / barCount + 1f; // ???
		int chargedBars = Math.round(chargeAmount * barCount);
		if (chargedBars < 0) {
			chargedBars = 0;
		} else if (chargedBars > barCount) {
			chargedBars = barCount;
		}

		/* Draw the charged bars. */
		Color current;
		for (int i = 0; i < barCount; i++) {
			if (i < chargedBars) {
				current = Color.lerp(least, most, i / (float) barCount);
				/* When lerping, the middle color loses some brightness. */
				current.mul(1.5f);
			} else {
				current = empty;
			}
			gm.fill(current.toHex());
			float yPos = (-transform.toScreenScale().y + ySize) / 2f + ySize * i;
			gm.rect(transform.position.x, yPos, transform.toScreenScale().x * margin, ySize * margin);
		}

		gm.popMatrix();
	}
}