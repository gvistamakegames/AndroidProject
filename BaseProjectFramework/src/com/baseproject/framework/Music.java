package com.baseproject.framework;

// �~���[�W�b�N�C���^�[�t�F�[�X
public interface Music {
	
	public void play();
	
	public void stop();
	
	public void pause();
	
	public void setLooping( boolean _looping );
	
	public void setVolume( float _volume );
	
	public boolean isPlaying();
	
	public boolean isStopping();
	
	public boolean isLooping();
	
	public void dispose();

}
