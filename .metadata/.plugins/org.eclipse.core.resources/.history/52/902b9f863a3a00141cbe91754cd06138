package com.baseproject.framework.Interface;


// グラフィックインターフェース
public interface Graphics {

	public static enum PixmapFormat {
		
		ARGB8888, ATGB4444, RGB565
		
	}
	
	public Pixmap newPixmap( String _fileName, PixmapFormat _format );
	
	public void clean( int _color );
	
	public void drawPixel( int _x, int _y, int _color );
	
	public void drawLine( int _x, int _y, int _x2, int _y2, int _color );
	
	public void drawRect( int _x, int _y, int _width, int _height, int _color );
	
	public void drawPixmap( Pixmap _pixmap, int _x, int _y, int _srcX, int _srcY, int _srcWidth, int _srcHeight );
	
	public void drawPixmap( Pixmap _pixmap, int _x, int _y );
	
	public int getWidth();
	
	public int getHeight();
	
}
