package Class;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import Class.Pool;
import com.baseproject.framework.Interface.Input.TouchEvent;
import Class.Pool.PoolObjectFactory;

import com.baseproject.framework.Interface.TouchHandler;

// シングルタッチハンドラークラス
public class SingleTouchHandler implements TouchHandler {

	// 変数宣言
	boolean isTouched;
	
	int touchX;
	int touchY;
	
	Pool<TouchEvent> touchEventPool;
	
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	
	float scaleX;
	float scaleY;
	
	// コンストラクタ
	public SingleTouchHandler( View _view, float _scaleX, float _scaleY ){
		
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>(){
			
			@Override
			public TouchEvent createObject(){
				
				return new TouchEvent();
				
			}
			
		};
		
		touchEventPool = new Pool<TouchEvent>( factory, 100 );
		_view.setOnTouchListener( this );
		
		this.scaleX = _scaleX;
		this.scaleY = _scaleY;
		
	}
	
	// 
	@Override
	public boolean onTouch( View _v, MotionEvent _event ){
		
		synchronized( this ){
			
			TouchEvent touchEvent = touchEventPool.newObject();
			
			switch( _event.getAction() ){
			case MotionEvent.ACTION_DOWN:
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				isTouched = true;
				break;
			case MotionEvent.ACTION_MOVE:
				touchEvent.type = TouchEvent.TOUCH_DRAGGED;
				isTouched = true;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				touchEvent.type = TouchEvent.TOUCH_UP;
				isTouched = false;
				break;					
			}
			
			touchEvent.x = touchX = (int)( _event.getX() * scaleX );
			touchEvent.y = touchY = (int)( _event.getY() * scaleY );
			
			touchEventsBuffer.add( touchEvent );
			
			return true;
			
		}
		
	}
	
	//
	@Override
	public boolean isTouchDown( int _pointer ){
		
		synchronized( this ){
			
			if( _pointer == 0 ){
				
				return isTouched;
				
			}else{
				
				return false;
				
			}
			
		}
		
	}
	
	//
	@Override
	public int getTouchX( int _pointer ){
		
		synchronized( this ){
			
			return touchX;
			
		}
		
	}
	
	//
	@Override
	public int getTouchY( int _pointer ){
		
		synchronized( this ){
			
			return touchY;
			
		}
		
	}
	
	//
	@Override
	public List<TouchEvent> getTouchEvents(){
		
		synchronized( this ){
			
			int len = touchEvents.size();
			
			for( int i = 0; i < len; i++ ){
				
				touchEventPool.free( touchEvents.get( i ) );
				
			}
			
			touchEvents.clear();
			touchEvents.addAll( touchEventsBuffer );
			touchEventsBuffer.clear();
			
			return touchEvents;
			
		}
		
	}
	
}
