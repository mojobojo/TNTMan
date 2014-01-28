package dev.mojobojo.tntman;

import static org.lwjgl.opengl.GL11.*;

public class Sprite {
	
	int textureID;
	
	public Sprite(int textureID) {
		this.textureID = textureID;
	}
	
	public void draw(float x, float y, float scale) {
		glBindTexture(GL_TEXTURE_2D, textureID);
		
		float size = 32.0f * scale;

		glBegin(GL_QUADS);
		{
			glColor3f(1.0f, 1.0f, 1.0f);

			glTexCoord2d(0.0, 1.0);
			glVertex3f(x, y + size, 0);

			glTexCoord2d(1.0, 1.0);
			glVertex3f(x + size, y + size, 0);

			glTexCoord2d(1.0, 0.0);
			glVertex3f(x + size, y, 0);

			glTexCoord2d(0.0, 0.0);
			glVertex3f(x, y, 0);
		}
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}

}
