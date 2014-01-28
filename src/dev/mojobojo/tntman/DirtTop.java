package dev.mojobojo.tntman;

import java.util.ArrayList;
import java.util.Random;

public class DirtTop {
	
	ArrayList<Vector2d> bricks;
	Texture spriteSheet;
	Sprite dirt;
	Sprite rock;
	Sprite sand;
	
	public DirtTop(Texture spriteSheet, int groundLength) {
		this.spriteSheet = spriteSheet;
		rock = spriteSheet.getSprite(0, 3);
		dirt = spriteSheet.getSprite(1, 3);
		sand = spriteSheet.getSprite(2, 3);
		bricks = new ArrayList<Vector2d>();
		
		Random r = new Random();

		for (int i = 0; i < groundLength; i++) {
			Vector2d vec = new Vector2d();
			vec.x = i * 32;
			vec.y = 0;
			bricks.add(vec);
			
			int rand = r.nextInt(2);
			
			if (rand == 0) {
				int max = r.nextInt(7);
				for (int l = 1; l < max; l++) {
					Vector2d vec2 = new Vector2d();
					vec2.x = i * 32;
					vec2.y = (l * 32);
					bricks.add(vec2);
				}
			}
		}
	}
	
	public void draw(int x, int y)  {
		Vector2d[] abricks = new Vector2d[bricks.size()];
		bricks.toArray(abricks);
		//int drawnCount = 0;
		for (int i = 0; i < bricks.size(); i++) {
			Vector2d brick = abricks[i];
			
			float xDest = (float)brick.x + x;
			float yDest = (float)brick.y + y;
			
			if (xDest >= -32 && xDest <= Game.width + 32) {
				
				if (yDest < 64) {
					dirt.draw(xDest, yDest, 1.0f);
				}
				else if (yDest <= 96 && yDest >= 64) {
					sand.draw(xDest, yDest, 1.0f);
				}
				else {
					rock.draw(xDest, yDest, 1.0f);
				}
				//drawnCount++;
			}
			else {
				//System.out.println(i);
			}
		}
		
		//System.out.println(drawnCount);
	}
}
