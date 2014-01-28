package dev.mojobojo.tntman;

import java.util.ArrayList;
import java.util.Random;

public class RockGround {
	
	public int groundLength;
	ArrayList<Vector2d> bricks;
	Texture spriteSheet;
	Sprite rock;
	
	public RockGround(Texture spriteSheet, int groundLength) {
		this.groundLength = groundLength;
		this.spriteSheet = spriteSheet;
		rock = spriteSheet.getSprite(0, 3);
		bricks = new ArrayList<Vector2d>();
		
		Random r = new Random();

		for (int i = 0; i < groundLength; i++) {
			Vector2d vec = new Vector2d();
			vec.x = i * 32;
			vec.y = Game.height - 32;
			bricks.add(vec);
			
			if ((i * 32) < 128) {
				continue;
			}
			
			int rand = r.nextInt(5);
			
			if (rand == 0) {
				for (int l = 1; l < r.nextInt(5); l++) {
					Vector2d vec2 = new Vector2d();
					vec2.x = i * 32;
					vec2.y = (Game.height - 32) - (l * 32);
					bricks.add(vec2);
				}
			}
		}
	}
	
	public void draw(float x, float y)  {
		Vector2d[] abricks = new Vector2d[bricks.size()];
		bricks.toArray(abricks);
		//int drawnCount = 0;
		for (int i = 0; i < bricks.size(); i++) {
			Vector2d brick = abricks[i];
			
			float xDest = (float)brick.x + x;
			float yDest = (float)brick.y + y;
			
			if (xDest >= -32 && xDest <= Game.width + 32) {
				rock.draw(xDest, yDest, 1.0f);
				//drawnCount++;
			}
			else {
				//System.out.println(i);
			}
		}
		
		//System.out.println(drawnCount);
	}
}
