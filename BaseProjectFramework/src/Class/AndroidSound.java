package Class;

import android.media.SoundPool;

import com.baseproject.framework.Interface.Sound;

//
public class AndroidSound implements Sound {

	// �ϐ��錾
	int soundID;
	SoundPool soundPool;
	
	//
	public AndroidSound( SoundPool _soundPool, int _soundID ){

		this.soundPool = _soundPool;		
		this.soundID = _soundID;
		
	}
	
	//
	@Override
	public void play( float _volume ){
		
		soundPool.play( soundID, _volume, _volume, 0, 0, 1 );
		
	}
	
	//
	@Override
	public void dispose(){
	
		soundPool.unload( soundID );
		
	}
	
}
