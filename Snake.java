import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;

public class Snake extends GameDriverV4 {
  boolean spawn;
  
  int gameState = 0;
  
  int ticks;
  
  int ticksTimer;
  
  Grid grid;
  
  Head head;
  
  Head2 head2;
  
  Food food;
  
  Ghosts ghosts;
  
  Fruit fruit;
  
  ghostHandler handler;
  
  ArrayList<BodyPart> body;
  
  blockHandler blocks;
  
  BufferedImage pacmanPic = addImage("pacmanPic.png");
  
  BufferedImage orangeGhostPic = addImage("orangeGhostPic.png");
  
  BufferedImage killGhostPic = addImage("killGhostPic.png");
  
  BufferedImage cherry = addImage("cherry.png");
  
  BufferedImage cherry2 = addImage("cherry2.png");
  
  BufferedImage dead = addImage("dyingPac.png");
  
  Sprite deadPac;
  
  Font font1 = new Font("Verdana", 1, 36);
  
  Font font2 = new Font("Impact", 1, 150);
  
  Font font3 = new Font("Courier New", 1, 240);
  
  Font font4 = new Font("Courier New", 1, 135);
  
  Font font5 = new Font("Impact", 1, 65);
  
  Font font6 = new Font("Impact", 1, 35);
  
  Font font7 = new Font("Verdana", 0, 20);
  
  Color c2 = new Color(255, 255, 6);
  
  Color c3 = new Color(255, 0, 0);
  
  Color c4 = new Color(255, 184, 255);
  
  Color c5 = new Color(25, 25, 166);
  
  Color c6 = new Color(0, 255, 255);
  
  Color c7 = new Color(255, 184, 82);
  
  String[] fileNames;
  
  SoundDriver sounds;
  
  int powerTimer;
  
  int powerDelay;
  
  int deathTimer;
  
  int deathDelay;
  
  int speed;
  
  int highScore = 4400;
  
  int highScore2 = 3300;
  
  boolean themesong;
  
  boolean eatsong;
  
  boolean death;
  
  boolean laser;
  
  int laserTimer;
  
  int laserDelay;
  
  int hitTimer;
  
  int hitDelay;
  
  public Snake() {
    this.fileNames = new String[5];
    this.fileNames[0] = "pacman_beginning.wav";
    this.fileNames[1] = "pacman_chomp.wav";
    this.fileNames[2] = "pacman_eatfruit.wav";
    this.fileNames[3] = "pacman_eatghost.wav";
    this.fileNames[4] = "pacman_death.wav";
    this.sounds = new SoundDriver(this.fileNames);
  }
  
  public void reset() {
    this.spawn = true;
    this.hitTimer = 0;
    this.hitDelay = 50;
    this.deadPac = new Sprite(this.dead, 1, 11, 7);
    this.death = false;
    this.blocks = null;
    this.themesong = false;
    this.eatsong = false;
    this.fruit = null;
    this.speed = 3;
    this.powerTimer = 0;
    this.powerDelay = 1200;
    this.deathTimer = 0;
    this.deathDelay = 97;
    this.ticks = 0;
    this.ticksTimer = 50;
    this.head = new Head();
    this.food = new Food();
    this.body = new ArrayList<>();
    this.ghosts = new Ghosts();
    this.handler = new ghostHandler();
    this.grid = new Grid();
    this.laser = false;
    this.laserTimer = 0;
    this.laserDelay = 300;
  }
  
  public static void main(String[] args) {
    Snake s1 = new Snake();
    s1.start();
    s1.setFocusable(true);
    s1.setBackGroundColor(Color.BLACK);
  }
  
  public void eatTheFood(Graphics2D win) {
    if (this.handler.hit && this.handler.pellet) {
      this.sounds.play(3);
      if (this.body.size() == 0 && !this.head.speedup) {
        if (!this.head.level3) {
          BodyPart b = this.head;
          this.body.add(new BodyPart(b));
        } 
        this.head.score += 100;
        this.handler.hit = false;
      } else if (!this.head.speedup) {
        BodyPart b = this.body.get(this.body.size() - 1);
        this.body.add(new BodyPart(b));
        this.head.score += 100;
        this.handler.hit = false;
      } 
    } 
    if (this.food != null) {
      this.food.moveAndDraw(win);
      if (this.head.intersects(this.food)) {
        if (this.head.speedup) {
          ArrayList<BodyPart> tempBody = new ArrayList<>();
          if (this.body.size() > 0)
            for (int k = 0; k < this.body.size(); k++)
              tempBody.add(this.body.get(k));  
          int distance = (Math.abs((int)this.head.getX() - (int)((BodyPart)this.body.get(0)).getX()) + 
            Math.abs((int)this.head.getY() - (int)((BodyPart)this.body.get(0)).getY())) / 39;
          this.head.score += distance * 100;
          this.body.clear();
          for (int i = 0; i < distance; i++) {
            if (this.body.size() == 0) {
              BodyPart b = this.head;
              this.body.add(new BodyPart(b));
              if (this.head.dx == 0 && this.head.dy > 0)
                ((BodyPart)this.body.get(0)).dy = this.speed; 
              if (this.head.dx == 0 && this.head.dy < 0)
                ((BodyPart)this.body.get(0)).dy = -this.speed; 
            } else {
              BodyPart b = this.body.get(this.body.size() - 1);
              this.body.add(new BodyPart(b));
              if (this.head.dx == 0 && this.head.dy > 0)
                ((BodyPart)this.body.get(this.body.size() - 1)).dy = this.speed; 
              if (this.head.dx == 0 && this.head.dy < 0)
                ((BodyPart)this.body.get(this.body.size() - 1)).dy = -this.speed; 
            } 
            ((BodyPart)this.body.get(i)).chillOut = false;
            if (this.head.dy == 0 && this.head.dx < 0) {
              ((BodyPart)this.body.get(i)).setLocation((int)this.head.getX() + (i + 1) * 39, (int)this.head.getY());
            } else if (this.head.dy == 0 && this.head.dx > 0) {
              ((BodyPart)this.body.get(i)).setLocation((int)this.head.getX() - (i + 1) * 39, (int)this.head.getY());
            } else if (this.head.dy > 0 && this.head.dx == 0) {
              ((BodyPart)this.body.get(i)).setLocation((int)this.head.getX(), (int)this.head.getY() - (i + 1) * 39);
            } else if (this.head.dy < 0 && this.head.dx == 0) {
              ((BodyPart)this.body.get(i)).setLocation((int)this.head.getX(), (int)this.head.getY() + (i + 1) * 39);
            } 
          } 
          for (int j = 0; j < tempBody.size(); j++) {
            BodyPart b = this.body.get(this.body.size() - 1);
            this.body.add(new BodyPart(b));
            ((BodyPart)this.body.get(this.body.size() - 1)).setLocation((int)((BodyPart)tempBody.get(j)).getX(), (int)((BodyPart)tempBody.get(j)).getY());
            ((BodyPart)this.body.get(this.body.size() - 1)).dx = ((BodyPart)tempBody.get(j)).dx;
            ((BodyPart)this.body.get(this.body.size() - 1)).dy = ((BodyPart)tempBody.get(j)).dy;
          } 
          this.head.speedup = false;
        } 
        this.sounds.play(2);
        this.handler.pellet = true;
        this.food = null;
      } 
    } 
    if (!this.handler.pellet && this.food == null)
      this.food = new Food(); 
  }
  
  public void power(Graphics2D win) {
    if (this.powerTimer > this.powerDelay && this.fruit == null) {
      this.fruit = new Fruit();
    } else {
      this.powerTimer++;
    } 
    if (this.fruit != null) {
      this.fruit.moveAndDraw(win);
      if (this.fruit.intersects(this.head)) {
        this.head.powerup = true;
        this.fruit = null;
        this.powerTimer = 0;
        this.sounds.play(2);
      } 
    } 
  }
  
  public void checkDeath() {
    Point center = new Point((int)this.head.getCenterX(), 
        (int)this.head.getCenterY());
    int i;
    for (i = 0; i < this.body.size(); i++) {
      if (((BodyPart)this.body.get(i)).contains(center) && !((BodyPart)this.body.get(i)).chillOut)
        this.death = true; 
    } 
    if (this.hitTimer > this.hitDelay) {
      this.spawn = false;
    } else {
      this.hitTimer++;
    } 
    if (this.handler.hit && !this.handler.pellet && !this.spawn)
      this.death = true; 
    if (this.head.intersectsLine(0.0D, -3.0D, 1200.0D, -3.0D))
      this.death = true; 
    if (this.head.intersectsLine(-3.0D, 0.0D, -3.0D, 800.0D))
      this.death = true; 
    if (this.head.intersectsLine(1172.0D, 0.0D, 1172.0D, 800.0D))
      this.death = true; 
    if (this.head.intersectsLine(0.0D, 755.0D, 1172.0D, 755.0D))
      this.death = true; 
    if (this.blocks != null)
      for (i = 0; i < 6; i++) {
        if (((Block)this.blocks.blocks.get(i)).intersects(this.head))
          this.death = true; 
      }  
  }
  
  public void deathAnimation(Graphics2D win) {
    AffineTransform change = win.getTransform();
    double theta = 0.0D;
    if (this.head.dx > 0 && this.head.dy == 0) {
      theta = 0.0D;
    } else if (this.head.dx < 0 && this.head.dy == 0) {
      theta = Math.PI;
    } else if (this.head.dy < 0 && this.head.dx == 0) {
      theta = 4.71238898038469D;
    } else if (this.head.dy > 0 && this.head.dx == 0) {
      theta = 1.5707963267948966D;
    } 
    win.rotate(theta, this.head.getCenterX(), this.head.getCenterY());
    win.drawImage(this.deadPac.getCurrentClip(), (BufferedImageOp)null, (int)this.head.getX(), (int)this.head.getY());
    win.setTransform(change);
    if (!this.sounds.isPlaying(4))
      this.sounds.play(4); 
    this.head.dead = true;
    this.head.dx = 0;
    this.head.dy = 0;
    int i;
    for (i = 0; i < this.body.size(); i++) {
      ((BodyPart)this.body.get(i)).dx = 0;
      ((BodyPart)this.body.get(i)).dy = 0;
    } 
    for (i = 0; i < this.handler.zoo.size(); i++) {
      ((Ghosts)this.handler.zoo.get(i)).multi = 0;
      ((Ghosts)this.handler.zoo.get(i)).multi = 0;
    } 
  }
  
  public void splashPage(Graphics2D win) {
    if (!this.sounds.isPlaying(0) && this.themesong)
      this.sounds.loop(0); 
    win.setFont(this.font1);
    win.setColor(this.c3);
    win.drawString("DANIEL ZHOU", 450, 475);
    win.setColor(this.c4);
    win.drawString("Press ENTER for Level 1", 355, 535);
    win.setColor(this.c6);
    win.drawString("Press 1 for instructions", 355, 720);
    win.setColor(this.c7);
    win.drawString("Press SHIFT for Level 2", 355, 600);
    win.setColor(this.c5);
    win.drawString("Press 2 for Two Players", 355, 660);
    win.setFont(this.font2);
    win.setColor(this.c2);
    win.drawString("PACMAN", 330, 200);
    win.setFont(this.font3);
    win.setColor(this.c5);
    win.drawString("SNAKE", 230, 400);
    if (GameDriverV4.Keys[10]) {
      this.gameState = 1;
      reset();
    } else if (GameDriverV4.Keys[49]) {
      this.gameState = 2;
    } else if (GameDriverV4.Keys[16]) {
      this.gameState = 4;
      reset();
    } else if (GameDriverV4.Keys[50]) {
      this.gameState = 5;
      reset();
    } 
  }
  
  public void instructions(Graphics2D win) {
    if (!this.sounds.isPlaying(0) && this.themesong)
      this.sounds.loop(0); 
    win.setColor(this.c2);
    win.setFont(this.font1);
    win.drawString("Press ESC to return to main menu", 250, 750);
    win.drawString("GOAL: Move PACMAN using up, down, right,", 135, 50);
    win.drawString("and left arrows to become the longest snake", 135, 100);
    win.setColor(this.c3);
    win.drawString("If PACMAN eats the pellet, you have a short", 135, 160);
    win.drawString("amount of time to eat ghosts", 135, 210);
    win.setColor(this.c5);
    win.drawString("If PACMAN eats the ghost without the pellet or", 135, 270);
    win.drawString("eats his snake, then you LOSE", 135, 320);
    win.drawImage(this.pacmanPic, (BufferedImageOp)null, 200, 330);
    win.drawImage(this.orangeGhostPic, (BufferedImageOp)null, 200, 435);
    win.drawImage(this.killGhostPic, (BufferedImageOp)null, 190, 515);
    win.drawImage(this.cherry, (BufferedImageOp)null, 150, 590);
    win.setFont(this.font5);
    win.setColor(this.c6);
    win.drawString("PACMAN Head", 400, 400);
    win.drawString("Enemy Ghost", 400, 498);
    win.drawString("Food/Body Ghost", 400, 590);
    win.drawString("Fruit Powerup", 400, 680);
    win.setFont(this.font7);
    win.drawString("(press SPACE to launch PACMAN to eat pellet)", 400, 710);
    if (GameDriverV4.Keys[27])
      this.gameState = 0; 
  }
  
  public void pacmanwin(Graphics2D win) {
    win.setFont(this.font2);
    win.setColor(this.c3);
    win.drawString("Score: " + this.head.score, 230, 200);
    win.setColor(this.c2);
    win.drawString("PACMAN WINS", 160, 400);
    win.setColor(Color.white);
    win.setFont(this.font1);
    win.drawString("Press ESC to return to main menu", 260, 550);
    if (GameDriverV4.Keys[27])
      this.gameState = 0; 
  }
  
  public void ghostwin(Graphics2D win) {
    win.setFont(this.font2);
    win.setColor(this.c3);
    win.drawString("Score: " + this.head.score, 230, 200);
    win.setColor(this.c2);
    win.drawString("GHOSTS WIN", 160, 400);
    win.setColor(Color.white);
    win.setFont(this.font1);
    win.drawString("Press ESC to return to main menu", 260, 550);
    if (GameDriverV4.Keys[27])
      this.gameState = 0; 
  }
  
  public void gameover(Graphics2D win) {
    win.setFont(this.font2);
    win.setColor(this.c3);
    win.drawString("Score: " + this.head.score, 230, 200);
    win.setColor(this.c2);
    win.drawString("GAMEOVER", 250, 400);
    win.setColor(Color.white);
    win.setFont(this.font1);
    win.drawString("Press ESC to return to main menu", 260, 550);
    if (GameDriverV4.Keys[27])
      this.gameState = 0; 
  }
  
  public void level2(Graphics2D win) {
    this.blocks = new blockHandler();
    this.handler.altnum = 800;
    this.handler.numGhosts = 8;
    this.sounds.pause(0);
    this.eatsong = true;
    if (!this.sounds.isPlaying(1) && this.eatsong)
      this.sounds.loop(1); 
    this.head.moveAndDraw(win);
    this.handler.moveAndDraw(win, this.head);
    this.blocks.Draw(win);
    int i;
    for (i = 0; i < this.body.size(); i++)
      ((BodyPart)this.body.get(i)).moveAndDraw(win); 
    eatTheFood(win);
    power(win);
    checkDeath();
    if (this.food != null)
      for (i = 0; i < this.blocks.blocks.size(); i++) {
        if (this.food.intersects(this.blocks.blocks.get(i)))
          this.food = new Food(); 
        if (this.fruit != null && (
          (Block)this.blocks.blocks.get(i)).intersects(this.fruit))
          this.fruit = new Fruit(); 
      }  
    if (!this.spawn)
      this.head.spawn = false; 
    win.setFont(this.font6);
    win.setColor(Color.white);
    win.drawString("SCORE: " + this.head.score, 50, 730);
    win.drawString("HIGH SCORE: " + this.highScore2, 500, 50);
    if (this.head.powerup) {
      win.drawImage(this.cherry2, (BufferedImageOp)null, 950, 670);
      win.drawString("ON", 1050, 730);
    } 
    if (this.head.speedup) {
      for (i = 0; i < this.body.size(); i++) {
        ((BodyPart)this.body.get(i)).stop = true;
        ((BodyPart)this.body.get(i)).stop = true;
      } 
    } else {
      for (i = 0; i < this.body.size(); i++) {
        ((BodyPart)this.body.get(i)).stop = false;
        ((BodyPart)this.body.get(i)).stop = false;
      } 
    } 
    if (this.death) {
      deathAnimation(win);
      if (this.deathTimer > this.deathDelay) {
        this.gameState = 3;
      } else {
        this.deathTimer++;
      } 
    } 
  }
  
  public void level3(Graphics2D win) {
    this.sounds.pause(0);
    this.handler.altnum = 800;
    this.handler.numGhosts = 10;
    this.handler.move = true;
    this.head.level3 = true;
    if (this.head.powerup)
      this.laser = true; 
    if (this.laser) {
      this.head.shoot(win);
      if (this.laserTimer > this.laserDelay) {
        this.laser = false;
        this.laserTimer = 0;
        this.head.powerup = false;
        for (int j = 0; j < this.head.bullets.size(); j++)
          this.head.bullets.remove(j); 
      } else {
        this.laserTimer++;
      } 
    } 
    this.eatsong = true;
    if (!this.sounds.isPlaying(1) && this.eatsong)
      this.sounds.loop(1); 
    this.head.moveAndDraw(win);
    this.handler.moveAndDraw(win, this.head);
    int i;
    for (i = 0; i < this.body.size(); i++)
      ((BodyPart)this.body.get(i)).moveAndDraw(win); 
    eatTheFood(win);
    power(win);
    checkDeath();
    win.setFont(this.font6);
    win.setColor(Color.white);
    win.drawString("SCORE: " + this.head.score, 50, 730);
    win.drawString("GHOSTS LEFT: " + ((1200 - this.head.score) / 100), 500, 50);
    if (!this.spawn)
      this.head.spawn = false; 
    if (this.head.powerup) {
      win.drawImage(this.cherry2, (BufferedImageOp)null, 950, 670);
      win.drawString("ON", 1050, 730);
    } 
    if (this.head.speedup) {
      for (i = 0; i < this.body.size(); i++) {
        ((BodyPart)this.body.get(i)).stop = true;
        ((BodyPart)this.body.get(i)).stop = true;
      } 
    } else {
      for (i = 0; i < this.body.size(); i++) {
        ((BodyPart)this.body.get(i)).stop = false;
        ((BodyPart)this.body.get(i)).stop = false;
      } 
    } 
    if (this.head.score == 1200)
      this.gameState = 7; 
    if (this.death) {
      deathAnimation(win);
      if (this.deathTimer > this.deathDelay) {
        this.gameState = 6;
      } else {
        this.deathTimer++;
      } 
    } 
  }
  
  public void draw(Graphics2D win) {
    if (this.gameState == 0) {
      this.themesong = true;
      splashPage(win);
    } else if (this.gameState == 1) {
      this.sounds.pause(0);
      this.eatsong = true;
      if (!this.sounds.isPlaying(1) && this.eatsong)
        this.sounds.loop(1); 
      this.head.moveAndDraw(win);
      this.handler.moveAndDraw(win, this.head);
      int i;
      for (i = 0; i < this.body.size(); i++)
        ((BodyPart)this.body.get(i)).moveAndDraw(win); 
      eatTheFood(win);
      power(win);
      checkDeath();
      win.setFont(this.font6);
      win.setColor(Color.white);
      win.drawString("SCORE: " + this.head.score, 50, 730);
      win.drawString("HIGH SCORE: " + this.highScore, 500, 50);
      if (!this.spawn)
        this.head.spawn = false; 
      if (this.head.powerup) {
        win.drawImage(this.cherry2, (BufferedImageOp)null, 950, 670);
        win.drawString("ON", 1050, 730);
      } 
      if (this.head.speedup) {
        for (i = 0; i < this.body.size(); i++) {
          ((BodyPart)this.body.get(i)).stop = true;
          ((BodyPart)this.body.get(i)).stop = true;
        } 
      } else {
        for (i = 0; i < this.body.size(); i++) {
          ((BodyPart)this.body.get(i)).stop = false;
          ((BodyPart)this.body.get(i)).stop = false;
        } 
      } 
      if (this.death) {
        deathAnimation(win);
        if (this.deathTimer > this.deathDelay) {
          this.gameState = 3;
        } else {
          this.deathTimer++;
        } 
      } 
    } else if (this.gameState == 2) {
      this.themesong = true;
      instructions(win);
    } else if (this.gameState == 3) {
      this.sounds.pause(1);
      gameover(win);
    } else if (this.gameState == 4) {
      level2(win);
    } else if (this.gameState == 5) {
      level3(win);
    } else if (this.gameState == 7) {
      pacmanwin(win);
    } else if (this.gameState == 6) {
      ghostwin(win);
    } 
  }
}

