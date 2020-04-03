import java.awt.Color;
import java.awt.Graphics2D;

public class Grid {
  Color col = new Color(25, 25, 166);
  
  public void moveAndDraw(Graphics2D win) {
    int i;
    for (i = 0; i < 1171; i += 30) {
      win.setColor(this.col);
      win.drawLine(i, 0, i, 752);
    } 
    for (i = 0; i < 752; i += 30) {
      win.setColor(this.col);
      win.drawLine(0, i, 1171, i);
    } 
  }
}