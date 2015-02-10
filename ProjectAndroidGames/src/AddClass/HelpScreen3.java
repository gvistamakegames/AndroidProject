package AddClass;

import java.util.List;

import com.baseproject.framework.Interface.Game;
import com.baseproject.framework.Interface.Graphics;
import com.baseproject.framework.Interface.Input.TouchEvent;
import com.baseproject.framework.Interface.Screen;

public class HelpScreen3 extends Screen{

	public HelpScreen3( Game _game ){
		
		super( _game );
		
	}
	
	// 
	@Override
	public void update( float _deltaTime ){
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		for( int i = 0; i < len; i++ ){
		
			TouchEvent event = touchEvents.get( i );
			if( event.type == TouchEvent.TOUCH_UP ){
				
				if( event.x > 256 && event.y > 416 ){
					
					game.setScreen( new MainMenuScreen( game ) );
					if( Settings.soundEnabled ){
						
						Assets.click.play( 1 );
						
					}
					
					return;
					
				}
				
			}
			
		}
		
	}

	//
	@Override
	public void present( float _deltaTime ){
		
		Graphics g = game.getGraphics();
		g.drawPixmap( Assets.background, 0, 0 );
		g.drawPixmap( Assets.help1, 64, 100 );
		g.drawPixmap( Assets.buttons, 256, 416, 0, 64, 64, 64 );
		
	}	
	
	// 
	@Override
	public void pause(){
		
	}

	// 
	@Override
	public void resume(){
		
	}
	
	// 
	@Override
	public void dispose(){
		
	}
	
}
