package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundPlayer {

	private AssetFileDescriptor[] bgm_file_desc;
	private AssetFileDescriptor se_file_desc;
	
	private AssetManager asset_manager;
		
	private MediaPlayer[] media_player;
	
	
	private SoundPool[] sound_pool;
	
	
	//コンストラクタ
	public SoundPlayer( Context context, int se_file_point, int bgm_file_point ){
		
		bgm_file_desc = new AssetFileDescriptor[bgm_file_point];
	    media_player = new MediaPlayer[bgm_file_point];
		for( int i = 0; i < bgm_file_point; i++ ){
			bgm_file_desc[i] = null;
			media_player[i] = new MediaPlayer();
		}
		se_file_desc = null;
		
		asset_manager = context.getAssets();

	    sound_pool = new SoundPool[se_file_point];
	    for( int i = 0; i < se_file_point; i++ ){
	    	sound_pool[i] = null;
	    }
	    
	}
	
	public void InitBGM( String filename, int bgm_no ){

		try {
	    	bgm_file_desc[bgm_no] = asset_manager.openFd( filename );
		} catch (IOException e) {
			e.printStackTrace();
		}

	    try {
	    	media_player[bgm_no].setDataSource( bgm_file_desc[bgm_no].getFileDescriptor(), bgm_file_desc[bgm_no].getStartOffset(), bgm_file_desc[bgm_no].getLength() );
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    ReadyBGM( filename, bgm_no );
		
	}
	
		
	public void ReadyBGM( String filename, int bgm_no ){

	    try {
	    	media_player[bgm_no].prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void PlayBGM( String filename, int bgm_no ){
		
		ReadyBGM( filename, bgm_no );
		media_player[bgm_no].start();
		media_player[bgm_no].setLooping( true );
		
	}
	
	public void RePlayBGM( String filename, int bgm_no ){

		ReadyBGM( filename, bgm_no );
		media_player[bgm_no].seekTo( 0 );		
		media_player[bgm_no].start();
		
	}

	public void PauseBGM( String filename, int bgm_no ){

		media_player[bgm_no].pause();
		
	}
	
	public void StopBGM( String filename, int bgm_no ){

		media_player[bgm_no].stop();
		
	}
	
	public void ReleaseBGM( String filename, int bgm_no ){

		media_player[bgm_no].stop();
		media_player[bgm_no].release();
		media_player[bgm_no] = null;

	}
	
	public void SetVolumeBGM( String filename, int bgm_no, float left, float right ){

		media_player[bgm_no].setVolume( left, right );

	}	
	
	public int InitSE( String filename, int soundfile_no ){
		int soundID;
		sound_pool[soundfile_no] = new SoundPool( 1, AudioManager.STREAM_MUSIC, 0 );
		try {
			se_file_desc = asset_manager.openFd( filename );
		} catch (IOException e) {
			e.printStackTrace();
		}
		soundID = sound_pool[soundfile_no].load( se_file_desc, 1 ); 
		return soundID;
	}	
	
	public void PlaySE( int soundID, int soundfile_no ){
		
		sound_pool[soundfile_no].play( soundID, 1.0f, 1.0f, 0, 0, 1 );
		
	}

	public void PlaySE( int soundID, int soundfile_no, float v_left, float v_right ){
		
		sound_pool[soundfile_no].play( soundID, v_left, v_right, 0, 0, 1 );
		
	}

	
	public void StopSE( int soundID,  int soundfile_no ){
		
	    sound_pool[soundfile_no].unload( soundID );
	    sound_pool[soundfile_no].release();

	}
	
	public void ReleaseSE( int soundID, int soundfile_no ){

		sound_pool[soundfile_no].unload( soundID );
		sound_pool[soundfile_no].release();

	}
	
}
