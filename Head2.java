import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Head2 extends Rectangle {
  int delay = 0;
  
  int timer = this.delay;
  
  boolean speedup;
  
  boolean distance;
  
  boolean powerup;
  
  int speedtimes = 4;
  
  Sprite sprite;
  
  BufferedImage pacman;
  
  int score;
  
  boolean dead;
  
  boolean up;
  
  boolean down;
  
  boolean right;
  
  boolean left;
  
  int xCoor = 399;
  
  int yCoor = 300;
  
  int ticks;
  
  int ticksTimer;
  
  public Head2(int x, int y) {
    this.xCoor = 399;
    this.yCoor = 300;
    setLocation(this.xCoor, this.yCoor);
    this.dead = false;
    this.up = false;
    this.down = false;
    this.right = true;
    this.left = false;
    this.score = 0;
    this.powerup = false;
    this.speedup = false;
    this.distance = false;
    this.pacman = addImage("pacman.png");
    this.sprite = new Sprite(this.pacman, 1, 3, 7);
    this.ticks = 0;
    this.ticksTimer = 100;
  }
  
  public void rotateImage(Graphics2D win) {
    AffineTransform change = win.getTransform();
    double theta = 0.0D;
    if (this.right && !this.up && !this.down) {
      theta = 0.0D;
    } else if (this.left && !this.up && !this.down) {
      theta = Math.PI;
    } else if (this.up && !this.right && !this.left) {
      theta = 4.71238898038469D;
    } else if (this.down && !this.right && !this.left) {
      theta = 1.5707963267948966D;
    } 
    win.rotate(theta, getCenterX(), getCenterY());
    win.drawImage(this.sprite.getCurrentClip(), null, (int)getX() - 3, (int)getY() - 3);
    win.setTransform(change);
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
    if (!this.dead)
      rotateImage(win); 
    this.ticks++;
    if (this.ticks > this.ticksTimer) {
      if (GameDriverV4.Keys[38] && !this.speedup && !this.down) {
        this.up = true;
        this.down = false;
        this.right = false;
        this.left = false;
      } else if (GameDriverV4.Keys[40] && !this.speedup && !this.up) {
        this.down = true;
        this.up = false;
        this.right = false;
        this.left = false;
      } else if (GameDriverV4.Keys[39] && !this.speedup && !this.left) {
        this.right = true;
        this.left = false;
        this.up = false;
        this.down = false;
      } else if (GameDriverV4.Keys[37] && !this.speedup && !this.right) {
        this.left = true;
        this.right = false;
        this.up = false;
        this.down = false;
      } 
      this.ticks = 0;
    } 
  }
}

