package Class;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnKeyListener;

import com.baseproject.framework.Interface.Input.KeyEvent;
import Class.Pool;
import Class.Pool.PoolObjectFactory;

// キーボードハンドラークラス
public class KeyboardHandler implements OnKeyListener {

	// 変数宣言
	boolean[] pressedKeys = new boolean[128];
	Pool<KeyEvent> keyEventPool;
	List<KeyEvent> keyEventsBuffer = new ArrayList<KeyEvent>();
	List<KeyEvent> keyEvents = new ArrayList<KeyEvent>();
	
	// コンストラクタ
	public KeyboardHandler( View _view ){
		
		//
		PoolObjectFactory<KeyEvent> factory = new PoolObjectFactory<KeyEvent>(){
			
			@Override
			public KeyEvent createObject(){
				
				return new KeyEvent();
				
			}
			
		};
		
		keyEventPool = new Pool<KeyEvent>( factory, 100 );
		_view.setOnKeyListener( this );
		_view.setFocusableInTouchMode( true );
		_view.requestFocus();
		
	}
	
	//
	@Override
	public boolean onKey( View _v, int _keyCode, android.view.KeyEvent _event ){
		
		//
		if( _event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE ){
			
			return false;
			
		}
		
		synchronized( this ){
			
			// 変数宣言
			KeyEvent keyEvent = keyEventPool.newObject();
			keyEvent.keyCode = _keyCode;
			keyEvent.keyChar = (char)_event.getUnicodeChar();
			
			//
			if( _event.getAction() == android.view.KeyEvent.ACTION_DOWN ){
				
				keyEvent.type = KeyEvent.KEY_DOWN;
				
				if( _keyCode > 0 && _keyCode <= 127 ){
					
					pressedKeys[_keyCode] = true;
					
				}
				
			}
			
			//
			if( _event.getAction() == android.view.KeyEvent.ACTION_UP ){
				
				keyEvent.type = KeyEvent.KEY_UP;
				
				if( _keyCode >0 && _keyCode <= 127 ){
					
					pressedKeys[_keyCode] = false;
					
				}
				
			}
			
			keyEventsBuffer.add( keyEvent );
			
		}
		
		return false;
		
	}
	
	//
	public boolean isKeyPressed( int _keyCode ){
		
		if( _keyCode < 0 || _keyCode > 127 ){
			
			return false;
			
		}
		
		return pressedKeys[_keyCode];
		
	}
	
	//
	public List<KeyEvent> getKeyEvents(){
		
		synchronized( this ){
			
			int len = keyEvents.size();
			
			for( int i = 0; i < len; i++ ){
				
				keyEventPool.free( keyEvents.get( i ) );
				
			}
			
			keyEvents.clear();
			
			keyEvents.addAll( keyEventsBuffer );
			
			keyEventsBuffer.clear();
			
			return keyEvents;
			
		}
		
	}
	
}
