package com.baseproject.framework;

// オーディオインターフェース
public interface Audio {
	
	public Music newMusic( String _fileName );
	
	public Sound newSound( String _fileName );

}
