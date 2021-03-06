package Class;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.baseproject.framework.Interface.Input;
import com.baseproject.framework.Interface.TouchHandler;

// 
public class AndroidInput implements Input  {

	AccelerometerHandler accelHandler;
	KeyboardHandler keyHandler;
	TouchHandler touchHandler;
	
	// 
	public AndroidInput( Context _context, View _view, float _scaleX, float _scaleY ){
		
		accelHandler = new AccelerometerHandler( _context );
		keyHandler = new KeyboardHandler( _view );
		if( VERSION.SDK_INT < 5 ){
			
			touchHandler = new SingleTouchHandler( _view, _scaleX, _scaleY );
			
		}else{
			
			touchHandler = new MultiTouchHandler( _view, _scaleX, _scaleY );
			
		}
		
	}
	
	// 
	@Override
	public boolean isKeyPressed( int _keyCode ){
		
		return keyHandler.isKeyPressed( _keyCode );
		
	}
	
	// 
	@Override
	public boolean isTouchDown( int _pointer ){
		
		return touchHandler.isTouchDown( _pointer );
		
	}
	
	//
	@Override
	public int getTouchX( int _pointer ){
		
		return touchHandler.getTouchX( _pointer );
		
	}
	
	//
	@Override
	public int getTouchY( int _pointer ){
		
		return touchHandler.getTouchY( _pointer );
		
	}
	
	//
	@Override
	public float getAccelX(){
		
		return accelHandler.getAccelX();
		
	}
	
	//
	@Override
	public float getAccelY(){
		
		return accelHandler.getAccelY();
		
	}
	
	//
	@Override
	public float getAccelZ(){
		
		return accelHandler.getAccelZ();
		
	}
	
	//
	@Override
	public List<TouchEvent> getTouchEvents(){
		
		return touchHandler.getTouchEvents();
		
	}
	
	// 
	public List<KeyEvent> getKeyEvents(){
		
		return keyHandler.getKeyEvents();
		
	}
	
}
