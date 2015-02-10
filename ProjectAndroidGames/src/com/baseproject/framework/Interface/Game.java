package com.baseproject.framework.Interface;

// ゲームインターフェース
public interface Game {

	//
	public Input getInput();
	
	//
	public FileIO getFileIO();
	
	//
	public Graphics getGraphics();
	
	//
	public Audio getAudio();
	
	//
	public void setScreen( Screen _screen );
	
	//
	public Screen getCurrentScreen();
	
	//
	public Screen getStartScreen();
	
}
