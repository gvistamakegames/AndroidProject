package com.baseproject.framework.Interface;

// ミュージックインターフェース
public interface Music {
	
	//
	public void play();
	
	//
	public void stop();
	
	//
	public void pause();
	
	//
	public void setLooping( boolean _looping );
	
	//
	public void setVolume( float _volume );
	
	//
	public boolean isPlaying();
	
	//
	public boolean isStopped();
	
	//
	public boolean isLooping();
	
	//
	public void dispose();

}
