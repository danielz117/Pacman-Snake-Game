import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GameObject extends Rectangle {
  Color col;
  
  int dx;
  
  int dy;
  
  int multi = 1;
  
  boolean stop = false;
  
  int previousX;
  
  int previousY;
  
  int previousDX;
  
  int previousDY;
  
  public GameObject(int x, int y, int width, int height, int dx, int dy, Color col) {
    super(x, y, width, height);
    this.col = col;
    this.dx = dx;
    this.dy = dy;
  }
  
  public void moveAndDraw(Graphics2D win) {
    this.previousX = this.x;
    this.previousY = this.y;
    this.previousDX = this.dx;
    this.previousDY = this.dy;
    if (!this.stop)
      translate(this.dx * this.multi, this.dy * this.multi); 
    win.setColor(this.col);
  }
}
