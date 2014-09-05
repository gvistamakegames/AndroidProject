package com.baseproject.framework;

import com.baseproject.framework.Graphics.PixmapFormat;

// ピクセルマップインターフェース
public interface Pixmap {

	public int getWidth();
	
	public int getHeight();
	
	public PixmapFormat getFormat();
	
	public void dispose();
	
}
