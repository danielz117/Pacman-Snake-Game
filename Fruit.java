import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Fruit extends GameObject {
  Random rand;
  
  Sprite sprite;
  
  BufferedImage fruit;
  
  public Fruit() {
    super(0, 0, 30, 30, 0, 0, Color.white);
    this.rand = new Random();
    int xLoc = this.rand.nextInt(39) * 30;
    int yLoc = this.rand.nextInt(25) * 30;
    setLocation(xLoc, yLoc);
    this.fruit = addImage("fruit.png");
    this.sprite = new Sprite(this.fruit, 1, 8, 20);
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
    win.setColor(this.col);
    win.drawImage(this.sprite.getCurrentClip(), (BufferedImageOp)null, (int)getX() - 3, (int)getY() - 2);
  }
}
