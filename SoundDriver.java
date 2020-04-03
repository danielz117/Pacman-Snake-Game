import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundDriver {
  private Clip[] clips;
  
  private int[] framePosition;
  
  private FloatControl gainControl;
  
  public SoundDriver(String[] aClips) {
    this.clips = new Clip[aClips.length];
    this.framePosition = new int[aClips.length];
    try {
      AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
          -1.0F, 
          16, 2, 4, 
          -1.0F, true);
      DataLine.Info info = new DataLine.Info(Clip.class, format);
      for (int i = 0; i < this.clips.length; i++) {
        AudioInputStream soundIn = AudioSystem.getAudioInputStream(getClass().getResource(aClips[i]));
        this.clips[i] = (Clip)AudioSystem.getLine(info);
        this.clips[i].open(soundIn);
        this.gainControl = (FloatControl)this.clips[i].getControl(FloatControl.Type.MASTER_GAIN);
      } 
    } catch (UnsupportedAudioFileException ex) {
      System.out.println("Unsupported Audio File");
    } catch (LineUnavailableException ex) {
      System.out.println("Line Unavailable");
    } catch (IOException ex) {
      System.out.println("IO Error" + ex);
    } 
  }
  
  public void play(int value) {
    this.clips[value].stop();
    this.clips[value].setFramePosition(0);
    this.clips[value].start();
  }
  
  public void loop(int value) {
    this.clips[value].stop();
    this.clips[value].setFramePosition(0);
    this.clips[value].loop(-1);
  }
  
  public void stop(int value) {
    this.clips[value].stop();
  }
  
  public void pause(int value) {
    this.framePosition[value] = this.clips[value].getFramePosition();
    this.clips[value].stop();
  }
  
  public void resume(int value) {
    this.clips[value].setFramePosition(this.framePosition[value]);
    this.clips[value].start();
  }
  
  public boolean isPlaying(int value) {
    return this.clips[value].isRunning();
  }
  
  public void setVolume(float volume) {}
}

