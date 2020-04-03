import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Ghosts extends GameObject {
  int type;
  
  int move = 0;
  
  int dx = 0;
  
  int dy = 0;
  
  int speed;
  
  int timer = 0;
  
  int delay;
  
  int timeSwitch;
  
  int xLoc;
  
  int yLoc;
  
  Random rand;
  
  int spaces = 0;
  
  int numspaces = 0;
  
  boolean level3;
  
  boolean death;
  
  public Ghosts() {
    super(0, 0, 30, 30, 0, 0, Color.white);
    this.rand = new Random();
    this.speed = 2;
    this.xLoc = this.rand.nextInt(39) * 30;
    this.yLoc = this.rand.nextInt(25) * 30;
    setLocation(this.xLoc, this.yLoc);
    this.type = this.rand.nextInt(4);
    this.move = this.rand.nextInt(4);
    this.timeSwitch = this.rand.nextInt(4);
    this.level3 = false;
    this.death = false;
  }
  
  public void moveAndDraw(Graphics2D win) {
    win.setColor(this.col);
    if (this.timeSwitch == 0) {
      this.delay = 175;
    } else if (this.timeSwitch == 1) {
      this.delay = 200;
    } else if (this.timeSwitch == 2) {
      this.delay = 225;
    } else if (this.timeSwitch == 3) {
      this.delay = 250;
    } 
    if (this.move == 0) {
      this.dx = this.speed;
      this.dy = 0;
    } else if (this.move == 1) {
      this.dx = -this.speed;
      this.dy = 0;
    } else if (this.move == 2) {
      this.dx = 0;
      this.dy = this.speed;
    } else if (this.move == 3) {
      this.dx = 0;
      this.dy = -this.speed;
    } 
    if (getX() < 0.0D) {
      setLocation(1170, (int)getY());
    } else if (getX() > 1170.0D) {
      setLocation(0, (int)getY());
    } else if (getY() < 0.0D) {
      setLocation((int)getX(), 770);
    } else if (getY() > 770.0D) {
      setLocation((int)getX(), 0);
    } 
    if (this.timer > this.delay) {
      this.move = this.rand.nextInt(4);
      this.timer = 0;
    } else {
      this.timer++;
    } 
    if (this.level3)
      if (GameDriverV4.Keys[87]) {
        this.dx = 0;
        this.dy = -this.speed;
      } else if (GameDriverV4.Keys[83]) {
        this.dx = 0;
        this.dy = this.speed;
      } else if (GameDriverV4.Keys[65]) {
        this.dy = 0;
        this.dx = -this.speed;
      } else if (GameDriverV4.Keys[68]) {
        this.dy = 0;
        this.dx = this.speed;
      }  
    translate(this.dx * this.multi, this.dy * this.multi);
  }
}

