package com.baseproject.framework.Interface;

//
public abstract class Screen {

	//
	protected final Game game;
	
	//
	public Screen( Game _game ){
		
		this.game = _game;
		
	}
	
	//
	public abstract void update( float _deltaTime );
	
	//
	public abstract void present( float _deltaTime );
	
	//
	public abstract void pause();
	
	//
	public abstract void resume();
	
	//
	public abstract void dispose();
	
}
