import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Food extends GameObject {
  Sprite sprite;
  
  BufferedImage pellet;
  
  public Food() {
    super(0, 0, 30, 30, 0, 0, Color.magenta);
    Random rand = new Random();
    int xLoc = rand.nextInt(39) * 30;
    int yLoc = rand.nextInt(25) * 30;
    setLocation(xLoc, yLoc);
    this.pellet = addImage("pellet.png");
    this.sprite = new Sprite(this.pellet, 1, 6, 7);
  }
  
  public BufferedImage addImage(String name) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(getClass().getResource(name));
    } catch (IOException e) {
      System.out.println(e);
    } 
    return img;
  }
  
  public void moveAndDraw(Graphics2D win) {
    win.drawImage(this.sprite.getCurrentClip(), null, (int)getX() + 6, (int)getY() + 5);
  }
}
