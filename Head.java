import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Head extends BodyPart implements Action {
  boolean spawn;
  
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
  
  boolean level3;
  
  int shootingtimer;
  
  int bulletdelay;
  
  ArrayList<lazer> bullets = new ArrayList<>();
  
  public Head() {
    super(399, 300);
    setLocation(this.xCoor, this.yCoor);
    this.spawn = true;
    this.dead = false;
    this.up = false;
    this.down = false;
    this.right = true;
    this.left = false;
    this.score = 0;
    this.dx = 3;
    this.head = true;
    this.powerup = false;
    this.speedup = false;
    this.distance = false;
    this.pacman = addImage("pacman.png");
    this.sprite = new Sprite(this.pacman, 1, 3, 7);
    this.ticks = 0;
    this.ticksTimer = 100;
    this.level3 = false;
    this.shootingtimer = 0;
    this.bulletdelay = 0;
  }
  
  public void rotateImage(Graphics2D win) {
    AffineTransform change = win.getTransform();
    double theta = 0.0D;
    if (this.dx > 0 && this.dy == 0) {
      theta = 0.0D;
    } else if (this.dx < 0 && this.dy == 0) {
      theta = Math.PI;
    } else if (this.dy < 0 && this.dx == 0) {
      theta = 4.71238898038469D;
    } else if (this.dy > 0 && this.dx == 0) {
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
  
  public void shoot(Graphics2D win) {
    if (this.shootingtimer < this.bulletdelay) {
      this.shootingtimer++;
    } else {
      this.shootingtimer = 0;
    } 
    if (this.shootingtimer == 0)
      if (this.dx > 0 && this.dy == 0) {
        lazer bullet = new lazer(90, 8, (int)getX() - 25, (int)getCenterY(), 0, 60);
        this.bullets.add(bullet);
      } else if (this.dx < 0 && this.dy == 0) {
        lazer bullet = new lazer(90, 8, (int)getX() - 35, (int)getCenterY(), 0, -60);
        this.bullets.add(bullet);
      } else if (this.dx == 0 && this.dy > 0) {
        lazer bullet = new lazer(8, 90, (int)getCenterX() - 3, (int)getCenterY() - 35, 60, 0);
        this.bullets.add(bullet);
      } else if (this.dx == 0 && this.dy < 0) {
        lazer bullet = new lazer(8, 90, (int)getCenterX() - 5, (int)getY() - 30, -60, 0);
        this.bullets.add(bullet);
      }  
    for (int i = 0; i < this.bullets.size(); i++) {
      lazer bullet = this.bullets.get(i);
      if (bullet.getX() > 1190.0D || bullet.getX() < 10.0D) {
        this.bullets.remove(i);
        i--;
      } 
      if (bullet.getY() < 10.0D || bullet.getY() > 790.0D) {
        this.bullets.remove(i);
        i--;
      } 
    } 
    for (lazer l1 : this.bullets)
      l1.draw(win); 
  }
  
  public void moveAndDraw(Graphics2D win) {
    super.moveAndDraw(win);
    if (!this.dead)
      rotateImage(win); 
    if (GameDriverV4.Keys[38] && (int)getX() % 30 == 0 && !this.speedup && this.dy != this.speed) {
      this.dx = 0;
      this.dy = -this.speed;
    } else if (GameDriverV4.Keys[40] && (int)getX() % 30 == 0 && !this.speedup && this.dy != -this.speed) {
      this.dx = 0;
      this.dy = this.speed;
    } else if (GameDriverV4.Keys[37] && (int)getY() % 30 == 0 && !this.speedup && this.dx != this.speed) {
      this.dy = 0;
      this.dx = -this.speed;
    } else if (GameDriverV4.Keys[39] && (int)getY() % 30 == 0 && !this.speedup && this.dx != -this.speed) {
      this.dy = 0;
      this.dx = this.speed;
    } 
    if (GameDriverV4.Keys[32] && this.powerup && (int)getY() % 30 == 0 && (int)getX() % 30 == 0) {
      this.speedup = true;
      this.powerup = false;
    } 
    if (this.speedup) {
      if (this.dx == 0 && this.dy == -this.speed) {
        this.dy = -this.speed * this.speedtimes;
      } else if (this.dx == 0 && this.dy == this.speed) {
        this.dy = this.speed * this.speedtimes;
      } else if (this.dy == 0 && this.dx == -this.speed) {
        this.dx = -this.speed * this.speedtimes;
      } else if (this.dy == 0 && this.dx == this.speed) {
        this.dx = this.speed * this.speedtimes;
      } 
    } else if (this.dx == 0 && this.dy == -this.speed * this.speedtimes) {
      this.dy = -this.speed;
    } else if (this.dx == 0 && this.dy == this.speed * this.speedtimes) {
      this.dy = this.speed;
    } else if (this.dy == 0 && this.dx == -this.speed * this.speedtimes) {
      this.dx = -this.speed;
    } else if (this.dy == 0 && this.dx == this.speed * this.speedtimes) {
      this.dx = this.speed;
    } 
  }
}

