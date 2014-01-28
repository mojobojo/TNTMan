package dev.mojobojo.tntman;

public class Font {
	
	float size = 1.0f;
	Texture fontMap;
	Sprite[] alphabet;
	Sprite[] numbers;

	public Font(float size, int color) {
		this.size = size;
		fontMap = new Texture();
		fontMap.loadFile("/font.png");
		
		fontMap.swapColor(0, color);
		
		alphabet = new Sprite[26];
		for (int i = 0; i < 26; i++) {
			alphabet[i] = fontMap.getSprite(i, 0);
		}
		
		numbers = new Sprite[10];
		for (int i = 0; i < 10; i++) {
			numbers[i] = fontMap.getSprite(i, 1);
		}
	}
	
	public void drawText(float x, float y, String str, float scale) {
		char[] chars = str.toLowerCase().toCharArray();

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			
			if (c >= 'a' && c <= 'z') {
				alphabet[c - 'a'].draw(x + (i * (32 * scale)), y, scale);
			}
			else if (c >= '0' && c <= '9') {
				numbers[c - '0'].draw(x + (i * (32 * scale)), y, scale);
			}
			else {
				// do nothing
			}
		}
	}
	
	public void drawText(float x, float y, String str) {
		drawText(x, y, str, size);
	}
}
