package com.baseproject.framework.Interface;

import com.baseproject.framework.Interface.Graphics.PixmapFormat;

// ピクセルマップインターフェース
public interface Pixmap {

	//
	public int getWidth();
	
	//
	public int getHeight();
	
	//
	public PixmapFormat getFormat();
	
	//
	public void dispose();
	
}
