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
			
			
			
			
		};
		
		
	}
	
	

}