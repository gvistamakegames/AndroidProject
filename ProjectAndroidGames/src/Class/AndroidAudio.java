package Class;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.baseproject.framework.Interface.Audio;
import com.baseproject.framework.Interface.Music;
import com.baseproject.framework.Interface.Sound;

//
public class AndroidAudio implements Audio  {

	// 変数宣言
	AssetManager assets;
	SoundPool soundPool;
	
	//
	public AndroidAudio( Activity _activity ){
		
		_activity.setVolumeControlStream( AudioManager.STREAM_MUSIC );
		this.assets = _activity.getAssets();
		this.soundPool = new SoundPool( 20, AudioManager.STREAM_MUSIC, 0 );
		
	}
	
	//
	@Override
	public Music newMusic( String _fileName ){
		
		try{
			
			AssetFileDescriptor assetDescriptor = assets.openFd( _fileName );
			return new AndroidMusic( assetDescriptor );
			
		}catch( IOException e ){
			
			throw new RuntimeException( "「"+ _fileName + "」" + "の音楽データをロードできませんでした" );
			
		}
		
	}
	
	//
	@Override
	public Sound newSound( String _fileName ){
		
		try{
			
			AssetFileDescriptor assetDescriptor = assets.openFd( _fileName );
			int soundID = soundPool.load( assetDescriptor, 0 );
			return new AndroidSound( soundPool, soundID );
			
		}catch( IOException e ){
			
			throw new RuntimeException( "「"+ _fileName + "」" + "の音楽データをロードできませんでした" );
			
		}
		
	}
	
}
