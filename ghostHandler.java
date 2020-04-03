import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class ghostHandler {
  int numGhosts;
  
  ArrayList<Ghosts> zoo;
  
  Ghosts ghosts;
  
  BufferedImage redGhost;
  
  BufferedImage blueGhost;
  
  BufferedImage orangeGhost;
  
  BufferedImage pinkGhost;
  
  BufferedImage altGhost;
  
  Sprite red;
  
  Sprite blue;
  
  Sprite orange;
  
  Sprite pink;
  
  Sprite alt;
  
  boolean hit;
  
  boolean pellet;
  
  int altnum = 800;
  
  int addTimer;
  
  int timer;
  
  int addDelay;
  
  boolean add;
  
  boolean move;
  
  int dead;
  
  public ghostHandler() {
    this.move = false;
    this.add = false;
    this.addTimer = 0;
    this.addDelay = 100;
    this.timer = 0;
    this.pellet = false;
    this.hit = false;
    this.zoo = new ArrayList<>();
    this.numGhosts = 5;
    this.altGhost = addImage("altghost.png");
    this.alt = new Sprite(this.altGhost, 1, 3, this.altnum);
    this.redGhost = addImage("redGhost.png");
    this.red = new Sprite(this.redGhost, 1, 8, 60);
    this.blueGhost = addImage("blueGhost.png");
    this.blue = new Sprite(this.blueGhost, 1, 8, 60);
    this.orangeGhost = addImage("orangeGhost.png");
    this.orange = new Sprite(this.orangeGhost, 1, 8, 60);
    this.pinkGhost = addImage("pinkGhost.png");
    this.pink = new Sprite(this.pinkGhost, 1, 8, 60);
    this.dead = 0;
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
  
  public void moveAndDraw(Graphics2D win, Head head) {
    for (int i = 0; i < this.numGhosts - this.dead; i++) {
      this.zoo.add(new Ghosts());
      Ghosts g = this.zoo.get(i);
      g.moveAndDraw(win);
      if (this.move) {
        g.level3 = true;
        if (g.intersects(head) && this.add)
          this.dead++; 
        if (head.powerup)
          for (int j = 0; j < head.bullets.size(); j++) {
            lazer bull = head.bullets.get(j);
            if (bull != null && this.zoo.get(i) != null && 
              bull.intersects(this.zoo.get(i))) {
              this.zoo.remove(i);
              this.dead++;
              head.score += 100;
            } 
          }  
      } 
      if (this.pellet) {
        win.drawImage(this.alt.getCurrentClip(), null, 
            (int)((Ghosts)this.zoo.get(i)).getX() - 3, (int)((Ghosts)this.zoo.get(i)).getY() - 2);
      } else if (((Ghosts)this.zoo.get(i)).type == 0) {
        win.drawImage(this.red.getCurrentClip(), null, 
            (int)((Ghosts)this.zoo.get(i)).getX() - 3, (int)((Ghosts)this.zoo.get(i)).getY() - 2);
      } else if (((Ghosts)this.zoo.get(i)).type == 1) {
        win.drawImage(this.blue.getCurrentClip(), null, 
            (int)((Ghosts)this.zoo.get(i)).getX() - 3, (int)((Ghosts)this.zoo.get(i)).getY() - 2);
      } else if (((Ghosts)this.zoo.get(i)).type == 2) {
        win.drawImage(this.orange.getCurrentClip(), null, 
            (int)((Ghosts)this.zoo.get(i)).getX() - 3, (int)((Ghosts)this.zoo.get(i)).getY() - 2);
      } else if (((Ghosts)this.zoo.get(i)).type == 3) {
        win.drawImage(this.pink.getCurrentClip(), null, 
            (int)((Ghosts)this.zoo.get(i)).getX() - 3, (int)((Ghosts)this.zoo.get(i)).getY() - 2);
      } 
      if (this.addTimer > this.addDelay) {
        this.add = true;
      } else {
        this.addTimer++;
      } 
      if (g.intersects(head) && this.add && !head.spawn) {
        this.hit = true;
        g.death = true;
        this.zoo.remove(i);
        i--;
        this.zoo.add(new Ghosts());
        this.addTimer = 0;
        this.add = false;
      } 
    } 
    if (this.pellet)
      if (this.timer > this.alt.delay) {
        this.pellet = false;
        this.alt = null;
        this.alt = new Sprite(this.altGhost, 1, 3, this.altnum);
      } else {
        this.alt.delay--;
      }  
  }
}
