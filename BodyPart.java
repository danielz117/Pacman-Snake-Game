import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BodyPart extends GameObject {
  int speed = 3;
  
  BodyPart leader;
  
  int timer = 0;
  
  int delay = 25;
  
  boolean chillOut = true;
  
  BufferedImage killghost;
  
  Sprite sprite;
  
  boolean head = false;
  
  int initialDX;
  
  int initialDY;
  
  public BodyPart(int x, int y) {
    super(x, y, 30, 30, 0, 0, Color.blue);
    this.killghost = addImage("killghost.png");
    this.sprite = new Sprite(this.killghost, 1, 2, 15);
  }
  
  public BodyPart(BodyPart leader) {
    this(leader.x, leader.y);
    this.col = Color.ORANGE;
    this.leader = leader;
    this.dx = 0;
    this.dy = 0;
    this.initialDX = leader.dx;
    this.initialDY = leader.dy;
  }
  
  public void followTheLeader() {
    if (this.dx != 0) {
      if (this.x == this.leader.x) {
        this.dx = 0;
        this.dy = this.leader.dy;
      } 
    } else if (this.y == this.leader.y) {
      this.dy = 0;
      this.dx = this.leader.dx;
    } 
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
    super.moveAndDraw(win);
    if (!this.head)
      win.drawImage(this.sprite.getCurrentClip(), null, (int)getX(), (int)getY() - 4); 
    if (!this.chillOut)
      followTheLeader(); 
    if (this.leader != null && this.chillOut)
      if (this.timer >= this.delay) {
        this.chillOut = false;
        this.dx = this.initialDX;
        this.dy = this.initialDY;
      } else {
        this.timer++;
      }  
    if (this.leader != null && this.chillOut)
      if (this.timer >= this.delay) {
        this.chillOut = false;
        this.dx = this.initialDX;
        this.dy = this.initialDY;
      } else {
        this.timer++;
      }  
  }
}


