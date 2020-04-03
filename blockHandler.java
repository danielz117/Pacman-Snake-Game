import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class blockHandler {
  ArrayList<Block> blocks;
  
  ArrayList<Integer> pos;
  
  Color c5 = new Color(25, 25, 166);
  
  int num;
  
  public blockHandler() {
    this.blocks = new ArrayList<>();
    this.pos = new ArrayList<>();
    this.num = 6;
  }
  
  public void Draw(Graphics2D win) {
    int i;
    for (i = 0; i < this.num / 2; i++) {
      if (this.blocks.size() <= 6) {
        this.blocks.add(new Block());
        ((Block)this.blocks.get(i)).type = 0;
      } 
    } 
    for (i = this.num / 2; i < this.num; i++) {
      if (this.blocks.size() <= 6) {
        this.blocks.add(new Block());
        ((Block)this.blocks.get(i)).width = 20;
        ((Block)this.blocks.get(i)).height = 240;
      } 
    } 
    ((Block)this.blocks.get(0)).xLoc = 90;
    ((Block)this.blocks.get(0)).yLoc = 665;
    win.setColor(this.c5);
    win.drawRoundRect(((Block)this.blocks.get(0)).xLoc, ((Block)this.blocks.get(0)).yLoc, ((Block)this.blocks.get(0)).width, ((Block)this.blocks.get(0)).height, 15, 15);
    ((Block)this.blocks.get(1)).xLoc = 450;
    ((Block)this.blocks.get(1)).yLoc = 485;
    win.setColor(this.c5);
    win.drawRoundRect(((Block)this.blocks.get(1)).xLoc, ((Block)this.blocks.get(1)).yLoc, ((Block)this.blocks.get(1)).width, ((Block)this.blocks.get(1)).height, 15, 15);
    ((Block)this.blocks.get(2)).xLoc = 870;
    ((Block)this.blocks.get(2)).yLoc = 95;
    win.setColor(this.c5);
    win.drawRoundRect(((Block)this.blocks.get(2)).xLoc, ((Block)this.blocks.get(2)).yLoc, ((Block)this.blocks.get(2)).width, ((Block)this.blocks.get(2)).height, 15, 15);
    ((Block)this.blocks.get(3)).xLoc = 185;
    ((Block)this.blocks.get(3)).yLoc = 60;
    win.setColor(this.c5);
    win.drawRoundRect(((Block)this.blocks.get(3)).xLoc, ((Block)this.blocks.get(3)).yLoc, ((Block)this.blocks.get(3)).width, ((Block)this.blocks.get(3)).height, 15, 15);
    ((Block)this.blocks.get(4)).xLoc = 1025;
    ((Block)this.blocks.get(4)).yLoc = 480;
    win.setColor(this.c5);
    win.drawRoundRect(((Block)this.blocks.get(4)).xLoc, ((Block)this.blocks.get(4)).yLoc, ((Block)this.blocks.get(4)).width, ((Block)this.blocks.get(4)).height, 15, 15);
    ((Block)this.blocks.get(5)).xLoc = 695;
    ((Block)this.blocks.get(5)).yLoc = 120;
    win.setColor(this.c5);
    win.drawRoundRect(((Block)this.blocks.get(5)).xLoc, ((Block)this.blocks.get(5)).yLoc, ((Block)this.blocks.get(5)).width, ((Block)this.blocks.get(5)).height, 15, 15);
    for (i = 0; i < this.blocks.size(); i++)
      ((Block)this.blocks.get(i)).Draw(win); 
    for (i = 0; i < this.blocks.size(); i++) {
      this.pos.add(Integer.valueOf(((Block)this.blocks.get(i)).xLoc));
      this.pos.add(Integer.valueOf(((Block)this.blocks.get(i)).yLoc));
    } 
  }
}

