package Class;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.baseproject.framework.Interface.Audio;
import com.baseproject.framework.Interface.FileIO;
import com.baseproject.framework.Interface.Game;
import com.baseproject.framework.Interface.Graphics;
import com.baseproject.framework.Interface.Input;
import com.baseproject.framework.Interface.Screen;

@SuppressLint("Wakelock")
public abstract class AndroidGame extends Activity implements Game {
	
	AndroidFastRenderView renderView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;

	@Override
	public void onCreate( Bundle _savedInstanceState ){
		
		super.onCreate( _savedInstanceState );
		
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		
		boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		int frameBufferWidth = isLandscape ? 480 : 320;
		int frameBufferHeight = isLandscape ? 320 : 480;
		Bitmap frameBuffer = Bitmap.createBitmap( frameBufferWidth, frameBufferHeight, Config.RGB_565 );
		
		float scaleX = (float)( frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth() );
		float scaleY = (float)( frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight() );
		
		renderView = new AndroidFastRenderView( this, frameBuffer );
		graphics = new AndroidGraphics( getAssets(), frameBuffer );
		fileIO = new AndroidFileIO( getAssets() );
		audio = new AndroidAudio( this );
		input = new AndroidInput( this, renderView, scaleX, scaleY );
		screen = getStartScreen();
		setContentView( renderView );
		PowerManager powerManager = (PowerManager)getSystemService( Context.POWER_SERVICE );
		wakeLock = powerManager.newWakeLock( PowerManager.FULL_WAKE_LOCK, "BaseGame" );		// �X�g�����O���̓s�x�v�ύX
		
	}
	
	@Override
	public void onResume(){
		
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
		
	}
	
	@Override
	public void onPause(){
		
		super.onPause();
		wakeLock.release();
		renderView.pause();
		screen.pause();
		
		if( isFinishing() ){
			screen.dispose();
		}
		
	}
	 //
	@Override
	public Input getInput(){
		
		return input;
		
	}
	
	//
	@Override
	public FileIO getFileIO(){
		
		return fileIO;
		
	}
	
	//
	@Override
	public Graphics getGraphics(){
		
		return graphics;
		
	}
	
	//
	@Override
	public Audio getAudio(){
		
		return audio;
		
	}
	
	//
	@Override
	public void setScreen( Screen _screen ){
		
		if( _screen == null ){
			
			throw new IllegalArgumentException( "Screen must not be null" );
			
		}
		
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update( 0 );
		this.screen = _screen;
		
	}
	
	//
	public Screen getCurrentScreen(){
		
		return screen;
		
	}
	
}
