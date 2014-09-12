package Class;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.baseproject.framework.Interface.Music;

public class AndroidMusic implements Music, OnCompletionListener {

	// �ϐ��錾
	MediaPlayer mediaPlayer;
	boolean isPrepared = false;
	
	public AndroidMusic( AssetFileDescriptor assetDescriptor ){
		
		mediaPlayer = new MediaPlayer();
		
		try{
			
			mediaPlayer.setDataSource( assetDescriptor.getFileDescriptor(), assetDescriptor.getStartOffset(), assetDescriptor.getLength() );
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener( this );
			
		}catch( Exception e ){
 			
			throw new RuntimeException( "���y�f�[�^�����[�h�ł��܂���ł���" );
			
		}
		
	}
	
	@Override
	public void dispose(){
		
		if( mediaPlayer.isPlaying() ){
			mediaPlayer.stop();
		}
		
		mediaPlayer.release();
				
	}
	
	@Override
	public boolean isLooping(){
		
		return mediaPlayer.isLooping();
		
	}
	
	@Override
	public boolean isPlaying(){
		
		return mediaPlayer.isPlaying();
		
	}
	
	@Override
	public boolean isStopped(){
		
		return !isPrepared;
		
	}	
	
	@Override
	public void pause(){
		
		if( mediaPlayer.isPlaying() ){
			
			mediaPlayer.pause();
			
		}
		
	}
	
	@Override
	public void play(){
		
		if( mediaPlayer.isPlaying() ){
			
			return;
			
		}
		
		try{
			
			synchronized( this ){
				
				if( !isPrepared ){
					
					mediaPlayer.prepare();
					
				}
				
				mediaPlayer.start();
				
			}
			
		}catch( IllegalStateException e ){
			
			e.printStackTrace();
			
		}catch( IOException e ){
			
			e.printStackTrace();
			
		}
		
	}
	
	// �A�N�Z�T
	@Override
	public void setLooping( boolean _isLooping ){
		
		mediaPlayer.setLooping( _isLooping );
		
	}
	
	// �A�N�Z�T
	@Override
	public void setVolume( float _volume ){
		
		mediaPlayer.setVolume( _volume, _volume );
		
	}
	
	@Override
	public void stop(){
		
		mediaPlayer.stop();
		
		synchronized( this ){
			
			isPrepared = false;
			
		}
		
	}
	
	@Override
	public void onCompletion( MediaPlayer _player ){
		
		synchronized( this ){
			
			isPrepared = false;
			
		}
		
	}
	
}