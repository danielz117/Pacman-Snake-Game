import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class lazer extends Rectangle {
  Color c;
  
  int dY;
  
  int dX;
  
  public lazer(int width, int height, int x, int y, int dy, int dx) {
    this.c = new Color(219, 51, 21);
    setSize(width, height);
    setLocation(x, y - 5);
    this.dY = dy;
    this.dX = dx;
  }
  
  public void draw(Graphics2D win) {
    translate(this.dX, this.dY);
    win.setColor(this.c);
    win.fillOval(this.x, this.y, this.width, this.height);
  }
}
