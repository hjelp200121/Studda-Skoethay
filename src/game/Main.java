package game;

import processing.core.PApplet;

public class Main {

	public static final String sketchName = "game.StuddaSkoethay";

	public static void main(String[] args) {
		String[] pAppletArgs = getPAppletArgs(args);
		PApplet.main(pAppletArgs);

	}

	static String[] getPAppletArgs(String[] args) {
		String[] pAppletArgs = new String[args.length + 1];
		pAppletArgs[0] = sketchName;
		for (int i = 1; i < pAppletArgs.length; i++) {
			pAppletArgs[i] = args[i - 1];
		}
		return pAppletArgs;
	}

}
