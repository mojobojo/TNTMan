package dev.mojobojo.tntman;

public class Lives {

	private int heartsLeft;
	private Sprite heart;

	public Lives(Texture spriteSheet, int startLives) {
		heartsLeft = startLives;
		heart = spriteSheet.getSprite(0, 4);
	}
	
	public void increaseLives() {
		heartsLeft++;
	}
	
	public void decreaseLives() {
		heartsLeft--;
	}
	
	public boolean outOfLives() {
		return heartsLeft < 1;
	}
	
	public void draw(float x, float y) {
		for (int i = 0; i < heartsLeft; i++) {
			heart.draw(x + (i * 32), y, 1.0f);
		}
	}
}
