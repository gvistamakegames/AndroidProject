package com.baseproject.framework.Interface;

import java.util.List;

import android.view.View.OnTouchListener;

import com.baseproject.framework.Interface.Input.TouchEvent;

//タッチハンドラーインターフェース
public interface TouchHandler extends OnTouchListener {

	//
	public boolean isTouchDown( int _pointer );
	
	//
	public int getTouchX( int _pointer );
	
	//
	public int getTouchY( int _pointer );
	
	//
	public List<TouchEvent> getTouchEvents();
	
}
