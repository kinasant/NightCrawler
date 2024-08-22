package nightcrawlerMaven;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer {
	 public void playAudio() {
	        try {
	           
	        	File audioFile = new File("nightcrawler.wav");
	            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
	            AudioFormat format = audioStream.getFormat();
	            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
	            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
	            line.open(format);
	            line.start();

	            byte[] bytes = new byte[1024];
	            int read = 0;
	            while ((read = audioStream.read(bytes)) != -1) {
	                line.write(bytes, 0, read);
	            }

	            line.drain();
	            line.close();
	            audioStream.close();
	        } catch (Exception e) {
	            System.err.println("Error: Failed to play audio file - " + e.getMessage());
	        }
}
}
