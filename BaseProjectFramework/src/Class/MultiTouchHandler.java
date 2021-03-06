package Class;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.baseproject.framework.Interface.Input.TouchEvent;
import com.baseproject.framework.Interface.TouchHandler;

import Class.Pool;
import Class.Pool.PoolObjectFactory;

// 
public class MultiTouchHandler implements TouchHandler {
	
	boolean[] isTouched = new boolean[20];
	int[] touchX = new int[20];
	int[] touchY = new int[20];
	Pool<TouchEvent> touchEventPool;
	
	
	List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
	float scaleX;
	float scaleY;
	
	// 
	public MultiTouchHandler( View _view, float _scaleX, float _scaleY ) {
		
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
			
			@Override
			public TouchEvent createObject() {
				
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
	public boolean onTouch ( View _view, MotionEvent _event ) {
		
		synchronized( this ){
			
			int action = _event.getAction() & MotionEvent.ACTION_MASK;
			int pointerIndex = ( _event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK ) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
			int pointerID = _event.getPointerId( pointerIndex );
			TouchEvent touchEvent;
			
			switch( action ){
			
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				touchEvent = touchEventPool.newObject();
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				touchEvent.pointer = pointerID;
				touchEvent.x = touchX[pointerID] = (int)( _event.getX( pointerIndex ) * scaleX );
				touchEvent.y = touchY[pointerID] = (int)( _event.getY( pointerIndex ) * scaleY );
				isTouched[pointerID] = true;
				touchEventsBuffer.add( touchEvent );
				break;
			
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL:
				touchEvent = touchEventPool.newObject();
				touchEvent.type = TouchEvent.TOUCH_UP;
				touchEvent.pointer = pointerID;
				touchEvent.x = touchX[pointerID] = (int)( _event.getX( pointerIndex ) * scaleX );
				touchEvent.y = touchY[pointerID] = (int)( _event.getY( pointerIndex ) * scaleY );
				isTouched[pointerID] = false;
				touchEventsBuffer.add( touchEvent );
				break;
			
			case MotionEvent.ACTION_MOVE:
				int pointerCount = _event.getPointerCount();
				for( int i = 0; i < pointerCount; i++ ){
					
					pointerIndex = i;
					pointerID = _event.getPointerId( pointerIndex );
					touchEvent = touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DRAGGED;
					touchEvent.pointer = pointerID;
					touchEvent.x = touchX[pointerID] = (int)( _event.getX( pointerIndex ) * scaleX );
					touchEvent.y = touchY[pointerID] = (int)( _event.getY( pointerIndex ) * scaleY );
					touchEventsBuffer.add( touchEvent );
				}
				break;
				
			}
						
			return true;
			
		}
		
	}

	// 
	@Override
	public boolean isTouchDown( int _pointer ){
		
		synchronized( this ){
			
			if( _pointer < 0 || _pointer >= 20 ){
				
				return false;
				
			}else{
				
				return isTouched[_pointer];
				
			}
			
		}
		
	}

	// 
	@Override
	public int getTouchX( int _pointer ){
		
		synchronized( this ){
			
			if( _pointer < 0 || _pointer >= 20 ){
				
				return 0;
				
			}else{
				
				return touchX[_pointer];
				
			}
			
		}
		
	}	
	
	// 
	@Override
	public int getTouchY( int _pointer ){
		
		synchronized( this ){
			
			if( _pointer < 0 || _pointer >= 20 ){
				
				return 0;
				
			}else{
				
				return touchY[_pointer];
				
			}
			
		}
		
	}
	
	// 
	@Override
	public List<TouchEvent> getTouchEvents() {
		
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
