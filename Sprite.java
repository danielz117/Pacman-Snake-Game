import java.awt.image.BufferedImage;

public class Sprite {
  BufferedImage[] clips;
  
  int delay;
  
  int timer = 0;
  
  int pos = 0;
  
  public Sprite(BufferedImage Image, int vert, int horizontal, int delay) {
    this.delay = delay;
    this.clips = new BufferedImage[vert * horizontal];
    int width = Image.getWidth() / horizontal;
    int height = Image.getHeight() / vert;
    int index = 0;
    for (int i = 0; i < Image.getHeight(); i += height) {
      for (int j = 0; j < Image.getWidth(); j += width) {
        BufferedImage temp = Image.getSubimage(j, i, width, height);
        this.clips[index] = temp;
        index++;
      } 
    } 
  }
  
  public BufferedImage getCurrentClip() {
    BufferedImage result = this.clips[this.pos];
    if (this.timer > this.delay) {
      this.pos++;
      this.pos %= this.clips.length;
      this.timer = 0;
    } else {
      this.timer++;
    } 
    return result;
  }
}
