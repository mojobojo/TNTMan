package dev.mojobojo.tntman;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

public class Texture {
	
	//private String path;
	public int width, height;
	public int[] pixels;
	public final int SIZE = 32;
	
	public Texture() {
	}
	
	public void loadFile(String path) {
		try {
			//this.path = path;
			BufferedImage image = ImageIO.read(Texture.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Sprite getSprite(int xIndex, int yIndex) {
		int textureID = glGenTextures();
	    glBindTexture(GL_TEXTURE_2D, textureID);

	    ByteBuffer buffer = BufferUtils.createByteBuffer(32 * 32 * 4); // 64 * 64 * 4
	    
	    for(int y = 0; y < SIZE; y++) {    	
	    	int yp = yIndex * 32 + y;
	    	
            for(int x = 0; x < SIZE; x++) {
            	int xp = xIndex * 32 + x;
            	
                int pixel = pixels[xp + yp * width];
                
                int r = ((pixel >> 16) & 0xFF);
                int g = ((pixel >> 8) & 0xFF);
                int b = (pixel & 0xFF);
                int a = (r == 255 && g == 0 && b == 255) ? 0 : 255; // magenta seems to be the standard for alpha colors
                
                buffer.put((byte)r);
                buffer.put((byte)g);
                buffer.put((byte)b);
                buffer.put((byte)a);
            }
        }

	    // buffer gets flipped because everything was put backwards
        buffer.flip();
       
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 32, 32, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		return new Sprite(textureID);
	}
	
	void swapColor(int colorOrig, int colorNew) {
		for (int i = 0; i < pixels.length; i++) {
			
			if ((pixels[i] & 0xFFFFFF) == colorOrig) {
				pixels[i] = colorNew;
			}
		}
	}
}
