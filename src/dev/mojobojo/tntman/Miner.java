package dev.mojobojo.tntman;

import org.lwjgl.input.Keyboard;

public class Miner {

	Vector2d position;
	Sprite[] sprites;

	boolean jumping = false;
	
	float scale = 2.5f;
	float walkSpeed = 5.0f;
	float velocity = 0.0f;
	
	long time = 0;
	
	int walkingAnimStep = 0;
	boolean walking = false;
	
	public Miner(Texture spriteSheet) {
		sprites = new Sprite[2];
		sprites[0] = spriteSheet.getSprite(0, 0);
		sprites[1] = spriteSheet.getSprite(1, 0);
		
		position = new Vector2d();
		position.x = 25;
		position.y = Game.height - (32 * scale) - 32;
	}
	
	public void update() {
		
		long tmpTime = System.currentTimeMillis();
		if (tmpTime - time > 100) {
			time = tmpTime;
			
			if (walking) {
				walkingAnimStep = walkingAnimStep == 1 ? 0 : 1;
			}
			else {
				walkingAnimStep = 0;
			}
		}
		
		if ((Input.keys[Keyboard.KEY_SPACE] || Input.keys[Keyboard.KEY_UP] || Input.keys[Keyboard.KEY_W]) && !jumping) {
			velocity = 5.0f;
			jumping = true;
		}
		
		walking = false;
		if (Input.keys[Keyboard.KEY_A] || Input.keys[Keyboard.KEY_LEFT]) {
			position.x -= walkSpeed;
			walking = true;
		}
		if (Input.keys[Keyboard.KEY_D] || Input.keys[Keyboard.KEY_RIGHT]) {
			position.x += walkSpeed;
			walking = true;
		}
		
		position.y -= velocity;
		
		velocity -= 0.1f;
		
		if (velocity >= 5.0f) {
			velocity = 5.0f;
		}
		
		if (position.y > Game.height - (32 * scale) - 32) {
			position.y = Game.height - (32 * scale) - 32;
			jumping = false;
		}
	}
	
	public void render() {
		sprites[walkingAnimStep].draw((float)position.x, (float)position.y, scale);
	}
}
