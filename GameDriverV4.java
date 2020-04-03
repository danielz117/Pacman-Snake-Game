import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class GameDriverV4 extends Canvas implements Runnable, KeyListener {
  private int FramesPerSecond;
  
  private Color backGroundColor = Color.WHITE;
  
  protected static boolean[] Keys;
  
  private JFrame frame;
  
  private String title = "JFrame";
  
  private int width = 1178, height = 790;
  
  public GameDriverV4(int frames) {
    this.FramesPerSecond = frames;
    addKeyListener(this);
    Keys = new boolean[402];
  }
  
  public GameDriverV4() {
    this(180);
  }
  
  public void start() {
    this.frame = new JFrame();
    this.frame.setSize(this.width, this.height);
    this.frame.setTitle(this.title);
    this.frame.setDefaultCloseOperation(3);
    this.frame.setLocationRelativeTo((Component)null);
    this.frame.setResizable(false);
    this.frame.add(this);
    this.frame.setVisible(true);
    startThread();
  }
  
  private synchronized void startThread() {
    Thread t1 = new Thread(this);
    t1.start();
  }
  
  public void setFrames(int num) {
    this.FramesPerSecond = num;
  }
  
  private void render() {
    BufferStrategy buffs = getBufferStrategy();
    if (buffs == null) {
      createBufferStrategy(3);
      buffs = getBufferStrategy();
    } 
    Graphics g = buffs.getDrawGraphics();
    g.setColor(this.backGroundColor);
    g.fillRect(0, 0, getWidth(), getHeight());
    draw((Graphics2D)g);
    g.dispose();
    buffs.show();
  }
  
  public abstract void draw(Graphics2D paramGraphics2D);
  
  public void run() {
    setFocusable(true);
    long beforeTime = System.currentTimeMillis();
    while (true) {
      long timeDiff = System.currentTimeMillis() - beforeTime;
      long sleep = (1000 / this.FramesPerSecond) - timeDiff;
      if (sleep < 0L)
        sleep = 2L; 
      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e) {
        String msg = String.format("Thread interrupted: %s", new Object[] { e.getMessage() });
        System.out.println(msg);
      } 
      render();
      beforeTime = System.currentTimeMillis();
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
  
  public Color getBackGroundColor() {
    return this.backGroundColor;
  }
  
  public void setBackGroundColor(Color backGroundColor) {
    this.backGroundColor = backGroundColor;
  }
  
  public void keyPressed(KeyEvent e) {
    Keys[e.getKeyCode()] = true;
  }
  
  public void keyReleased(KeyEvent e) {
    Keys[e.getKeyCode()] = false;
  }
  
  public void keyTyped(KeyEvent e) {}
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
}
