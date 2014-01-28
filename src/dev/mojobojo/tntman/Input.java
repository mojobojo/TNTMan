package dev.mojobojo.tntman;

import org.lwjgl.input.Keyboard;

public class Input {
	
	public static boolean[] keys = new boolean[256];
	
	public Input() {
		
	}
	
	public static void updateInput() {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = Keyboard.isKeyDown(i);
		}
	}
}
