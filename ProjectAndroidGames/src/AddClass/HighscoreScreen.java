package AddClass;

import java.util.List;

import com.baseproject.framework.Interface.Game;
import com.baseproject.framework.Interface.Graphics;
import com.baseproject.framework.Interface.Screen;
import com.baseproject.framework.Interface.Input.TouchEvent;

public class HighscoreScreen extends Screen{

	String lines[] = new String[5];
	
	public HighscoreScreen( Game _game ){
		
		super( _game );
		
		// 
		for( int i = 0; i < 5; i++ ){
			
			lines[i] = "" + ( i + 1 ) + "." + Settings.highscores[i];
			
		}
		
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
				
				if( event.x < 64 && event.y > 416 ){
					
					if( Settings.soundEnabled ){
						
						Assets.click.play( 1 );
						
					}
					
					game.setScreen( new MainMenuScreen( game ) );
					
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
		g.drawPixmap( Assets.mainMenu, 64, 20, 0, 42, 196, 42 );
		
		int y = 100;
		
		for( int i = 0; i < 5; i++ ){
		
			drawText( g, lines[i], 20, y );
			
			y += 50;
			
		}
		
		g.drawPixmap( Assets.buttons, 0, 416, 64, 64, 64, 64 );
		
	}
	
	// 
	public void drawText( Graphics _g, String _line, int _x, int _y ){
		
		int len = _line.length();
		
		for( int i = 0; i < len; i++ ){
			
			char character = _line.charAt( i );
			
			if( character == ' ' ){
				
				_x += 20;
				
				continue;
				
			}
			
			int srcX = 0;
			int srcWidth = 0;
			
			if( character == '.' ){
				
				srcX = 200;
				srcWidth = 10;
				
			}else{
				
				srcX = ( character - '0' ) * 20;
				srcWidth = 20;
				
			}
			
			_g.drawPixmap( Assets.numbers, _x, _y, srcX, 0, srcWidth, 32 );
			
			_x += srcWidth;
			
		}
		
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
