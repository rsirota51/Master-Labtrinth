package engine.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private Clip _clip;
//	private String _s;
//	private boolean _isPlaying;

	public Sound(String location) throws LineUnavailableException,
			UnsupportedAudioFileException, IOException {
		// TODO Auto-generated constructor stub
		_clip = AudioSystem.getClip();
		AudioInputStream inputStream = AudioSystem
				.getAudioInputStream(new File(location));

		_clip.open(inputStream);
		//_s = location;

	}

	public void play() {		
		if (!_clip.isActive()) {
			_clip.start();
		}
	}

	public void stop() {
		_clip.stop();
	}

	public void setLoop(boolean loop) {
		if (loop) {
			_clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			_clip.loop(0);
		}
	}
}
