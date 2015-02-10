package AddClass;

import java.util.List;

import android.graphics.Color;

import com.baseproject.framework.Interface.Game;
import com.baseproject.framework.Interface.Graphics;
import com.baseproject.framework.Interface.Input.TouchEvent;
import com.baseproject.framework.Interface.Pixmap;
import com.baseproject.framework.Interface.Screen;

public class GameScreen extends Screen {

	enum GameState {
		Ready,
		Running,
		Paused,
		GameOver,
		StateSum		// ステートの合計数
	}
	
	GameState state = GameState.Ready;
	World world;
	int oldScore = 0;
	String score = "0";
	
	// 
	public GameScreen( Game _game ){
		
		super( _game );
		
		world = new World();
		
	}
	
	// 
	@Override
	public void update( float _deltaTime ){
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		if( state == GameState.Ready ){
			updateReady( touchEvents );
		}
		if( state == GameState.Running ){
			updateRunning( touchEvents, _deltaTime );
		}
		if( state == GameState.Paused ){
			updatePaused( touchEvents );
		}
		if( state == GameState.GameOver ){
			updateGameOver( touchEvents );
		}
		
	}
	
	// 
	private void updateReady( List<TouchEvent> _touchEvents ){
		
		if( _touchEvents.size() > 0 ){
			
			state = GameState.Running;
			
		}
		
	}
	
	// 
	private void updateRunning( List<TouchEvent> _touchEvents, float _deltaTime ){
		
		int len = _touchEvents.size();
		
		for( int i = 0; i < len; i++ ){
			
			TouchEvent event = _touchEvents.get( i );
			
			if( event.type == TouchEvent.TOUCH_UP ){
				
				if( event.x < 64 && event.y < 64 ){
					
					if( Settings.soundEnabled ){
						
						Assets.click.play( 1 );
						
					}
					
					state = GameState.Paused;

					return;

				}
				
			}
			
			if( event.type == TouchEvent.TOUCH_DOWN ){
				
				if( event.x < 64 && event.y > 416 ){
					
					world.snake.turnLeft();
					
				}
				
				if( event.x > 256 && event.y > 416 ){
					
					world.snake.turnRight();
					
				}
								
			}			
			
		}// for文の終わり
		
		world.update( _deltaTime );

		if( world.gameOver ){
			
			if( Settings.soundEnabled ){
				
				Assets.bitten.play( 1 );
				
			}
			
			state = GameState.GameOver;
			
		}

		if( oldScore != world.score ){
			
			oldScore = world.score;
			
			score = "" + oldScore;
			
			if( Settings.soundEnabled ){
				
				Assets.eat.play( 1 );
				
			}
			
		}
		
	}
	
	// 
	private void updatePaused( List<TouchEvent> _touchEvents ){
		
		int len = _touchEvents.size();

		for( int i = 0; i < len; i++ ){
			
			TouchEvent event = _touchEvents.get( i );
			
			if( event.type == TouchEvent.TOUCH_UP ){
				
				if( event.x > 80 && event.x <= 240 ){
					
					if( event.y > 100 && event.y <= 148 ){
						
						if( Settings.soundEnabled ){
							
							Assets.click.play( 1 );
							
						}
						
						state = GameState.Running;
						
						return;
						
					}
					
					if( event.y > 148 && event.y < 196 ){
						
						if( Settings.soundEnabled ){
							
							Assets.click.play( 1 );
							
						}
						
						game.setScreen( new MainMenuScreen( game ) );
						
						return;
						
					}
					
				}
				
			}
			
		}
		
	}
	
	// 
	private void updateGameOver( List<TouchEvent> _touchEvents ){
		
		int len = _touchEvents.size();
		
		
	}
	
}
