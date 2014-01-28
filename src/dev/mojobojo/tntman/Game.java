
package dev.mojobojo.tntman;

import org.lwjgl.opengl.Display;

public class Game {
	
	public static final int width = 800;
	public static final int height = 450;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display.setTitle("TNT Man");
		MainGame game = new MainGame();
		game.createDisplay(width,  height);
		game.mainLoop();
	}

}
