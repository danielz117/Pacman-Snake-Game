import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle {
  int xLoc;
  
  int yLoc;
  
  int width;
  
  int height;
  
  int type;
  
  int numBlocks = 6;
  
  public Block() {
    if (this.type == 0) {
      this.width = 240;
      this.height = 20;
    } 
    if (this.type == 1) {
      this.width = 20;
      this.height = 240;
    } 
  }
  
  public void Draw(Graphics2D win) {
    setLocation(this.xLoc, this.yLoc);
    setSize(this.width, this.height);
  }
}
