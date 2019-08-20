package engine.sound;

import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPool {
	
	private static SoundPool _pool = null;
	
	private HashMap<String, Sound> _sounds;
	
	private SoundPool() {
		// TODO Auto-generated constructor stub
		_sounds = new HashMap<String,Sound>();
	}
	
	public static SoundPool getSoundPool(){
		if(_pool == null){
			_pool = new SoundPool();
		}
		
		return _pool;
	}
	
	public boolean registerSound(String location){
		Sound s;
		
		try {
			s = new Sound(location);
		} catch (LineUnavailableException | UnsupportedAudioFileException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		_sounds.put(location, s);
		
		return true;
	}
	
	public void playSound(String key, boolean loop){
		if(_sounds.containsKey(key)){
			_sounds.get(key).play();
		}
	}
	
	public void stopSound(String key){
		if(_sounds.containsKey(key)){
			_sounds.get(key).stop();
		}
	}
}
