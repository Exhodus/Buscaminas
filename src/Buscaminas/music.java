package Buscaminas;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//Aixó m'ho ha passat el Sam de l'altre classe per possar-hi música pero no aconsegueixo que m'agafi l'arxiu be
public class music {
	public static File menuMusica = new File("Resources/Music/hahaha.wav");
	public static Clip clip;
	
	public music() {}
	
	public static void playMusic() {
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(menuMusica);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
		}catch(UnsupportedAudioFileException e){
			System.err.println("Archivo no compatible");
		}catch(IOException e) {
			System.err.println("Error al cargar el archivo");
		}catch(LineUnavailableException e) {
			System.err.println("Linea de audio no disponible");
		}
	}
	
	public static void stop() {
		clip.stop();
	}
	

}
