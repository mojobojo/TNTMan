package dev.mojobojo.tntman;

import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
//import org.lwjgl.openal.AL;
//import org.lwjgl.openal.AL10;
//import org.lwjgl.util.WaveData;

public class MainGame {
	
	private int width;
	private int height;
	private float sceneryPos = 0.0f;
	public float counterIncrement = 0.0f;
	private long time;
	private int fps;
	private Texture spriteSheet;
	private RockGround rockGround;
	private DirtTop dirtTop;
	private Miner miner;
	private Font font;
	private Lives lives;
	private boolean playing = false;
	private double counter = 0.0f;
	private float startGameMessagePos = 0.0f;
	private long percentDone = 0;
	private int blocksLength = 200;
	private float difficulty = 1.0f;
	private boolean drawStartMessage = true;
	
	private String[] successStrings = new String[] { "Cool", "Awesome", "Good Job", "Great" };
	private String[] failStrings = new String[] { "Fail", "You Suck", "Terrible", "Loser"};
	
	private int stringAnimationNum = 0;
	private float stringAnimationScale = 1.0f;
	private float stringAnimationX = 0.0f;
	private boolean stringAnimating = false;
	private boolean stringIsSuccess = false;
	private boolean drawGameOverString = false;
	private float gameOverStringScale = 1.0f;
	
	public MainGame() {
	}
	
	public void createDisplay(int width, int height) {
		try {
			this.width = width;
			this.height = height;

			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	void reset(boolean newLevel, boolean success) {
		stringAnimationNum = new Random().nextInt(4);
		stringAnimating = true;
		stringIsSuccess = success;
		stringAnimationX = -100;
		stringAnimationScale = 1.0f;
		if (newLevel) {
			blocksLength += 100;
			difficulty += 1;
		}
		miner = new Miner(spriteSheet);
		rockGround = new RockGround(spriteSheet, blocksLength);
		dirtTop = new DirtTop(spriteSheet, blocksLength);
		miner.walkSpeed += (difficulty * 1.5f);
		percentDone = 0;
		counterIncrement = 0;
		sceneryPos = 0;
	}
	
	public void update() {
		Input.updateInput();
		
		if (lives.outOfLives()) {
			playing = false;
			miner = new Miner(spriteSheet);
			lives = new Lives(spriteSheet, 5);
			drawStartMessage = true;
			blocksLength = 200;
			difficulty = 1.0f;
			drawGameOverString = true;
		}
		
		if (Input.keys[Keyboard.KEY_RETURN] && !playing) {
			playing = true;
			drawGameOverString = false;
		}
		
		if (!playing) {
			startGameMessagePos = Game.height / 2 + (float)Math.sin(counter / 10) * 10;
			gameOverStringScale = (float)Math.abs(Math.sin(counter / 30) * 1) + 1.0f;
		}
		
		if (stringAnimating) {
			
			if (stringAnimationX > Game.width + 100) {
				stringAnimating = false;
			}
			
			stringAnimationX += 7;
			stringAnimationScale += 0.05f;
		}
		
		if (playing) {
			
			if (percentDone <= 1) {
				reset(true, true);
			}
			
			if (startGameMessagePos > -25) {
				startGameMessagePos -= 5;
			}
			else {
				drawStartMessage = false;
			}
			
			sceneryPos += counterIncrement;
			counterIncrement += 0.002f * difficulty;
			
			if (counterIncrement > difficulty) {
				counterIncrement = difficulty;
			}
	
			miner.position.x -= counterIncrement;
			miner.update();
			
			Vector2d[] bricks = new Vector2d[rockGround.bricks.size()];
			rockGround.bricks.toArray(bricks);
	
			for (int i = 0; i < rockGround.bricks.size(); i++) {
				Vector2d brick = bricks[i];
				
				float xDest = (float)brick.x - sceneryPos;
				float yDest = (float)brick.y;
				
				if (yDest < Game.height - 32) {
					if (xDest >= miner.position.x && xDest <= miner.position.x + 64 && yDest >= miner.position.y && yDest <= miner.position.y + (32 * miner.scale)) {
						lives.decreaseLives();
						reset(false, false);
						break;
					}
				}
				else {
					
				}
			}
		}
		
		if (miner.position.x < 0) {
			miner.position.x = 0;
		}
		if (miner.position.x > Game.width - (miner.scale * 32)) {
			miner.position.x = Game.width - (miner.scale * 32);
		}
		
		int size = rockGround.groundLength;
		//double d = (miner.position.x + sceneryPos) / (rockGround.bricks.get(size).x);
		//percentDone = 100 - Math.round(d * 100);
		
		percentDone = size - (long)((miner.position.x + sceneryPos) / 32) - 1;
		
		counter++;
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		// draw a gardient background
		glBegin(GL_LINES);
		{
			for (float i = 0, x = height; i < height; i++, x--) {
				glColor3f((139.0f - (i / 2)) / 255.0f, (69.0f - (i / 2)) / 255.0f, (19.0f) / 255.0f);
				glVertex2f(0, x);
				glVertex2f(width, x);
			}
		}
		glEnd();
		
		rockGround.draw((int)-sceneryPos,  0);
		dirtTop.draw((int)-sceneryPos, 0);
		
		miner.render();
		
		if (drawStartMessage) {
			font.drawText(Game.width / 2 - 150, startGameMessagePos, "Hit enter to start");
		}
		
		if (playing) {
			font.drawText(20, 20, percentDone + " units left");
			font.drawText(Game.width - 150, 20, "Level " + (int)difficulty);
			lives.draw(Game.width / 2 - 50, 20);
		}
		
		if (stringAnimating) {
			font.drawText(stringAnimationX, Game.height / 2, stringIsSuccess ? successStrings[stringAnimationNum] : failStrings[stringAnimationNum], stringAnimationScale);
		}
		
		if (drawGameOverString) {
			font.drawText(Game.width / 2 - gameOverStringScale * 100 - 70, 50, "Game Over", gameOverStringScale);
		}
	}
	
	@SuppressWarnings("unused") // frames for some strange reason says its not used
	public void mainLoop() {
		System.out.println("GL Version " + glGetString(GL_VERSION));
		
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		
		spriteSheet = new Texture();
		spriteSheet.loadFile("/sprites.png");
		
		miner = new Miner(spriteSheet);
		rockGround = new RockGround(spriteSheet, blocksLength);
		dirtTop = new DirtTop(spriteSheet, blocksLength);
		font = new Font(0.5f, 0xFFFF7F);
		lives = new Lives(spriteSheet, 5);

		glShadeModel(GL_SMOOTH);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0f, width, height, 0.0f, 0.0f, 1);
		glMatrixMode(GL_MODELVIEW);
	    glDisable(GL_LIGHTING);
	    glDisable(GL_DITHER);
	    glDisable(GL_BLEND);
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);

		glClearColor(100.0f / 255.0f, 149.0f / 255.0f, 237.0f / 255.0f, 1.0f);
		
		while (!Display.isCloseRequested()) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			// update only at 60 times per second
			while (delta >= 1) {
				update();
				delta--;
			}
			
			// render as fast as possible
			render();
			frames++;
			
			if (System.currentTimeMillis() - time > 1000) {
				time = System.currentTimeMillis();
				
				Display.setTitle("fps " + fps);
				fps = 0;
			}
			
			fps++;
			
			Display.update();
		}
		
		Display.destroy();
	}
}
