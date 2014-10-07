package Class;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import Class.AndroidGame;

public class AndroidFastRenderView extends SurfaceView implements Runnable  {

	// 
	AndroidGame game; 
	Bitmap frameBuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;
	
	// 
	public AndroidFastRenderView( AndroidGame _game, Bitmap _frameBuffer ){
		
		super( _game );
		this.game = _game;
		this.frameBuffer = _frameBuffer;
		this.holder = getHolder();
		
	}
	
	// 
	public void resume(){
		
		running = true;
		renderThread = new Thread( this );
		renderThread.start();
		
	}
	
	// 
	public void run(){
		
		
		
	}
	
	
	
	
	
}