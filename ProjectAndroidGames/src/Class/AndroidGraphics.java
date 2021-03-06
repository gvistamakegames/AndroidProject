package Class;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.baseproject.framework.Interface.Graphics;
import com.baseproject.framework.Interface.Pixmap;

// 
public class AndroidGraphics implements Graphics {

	// 変数宣言
	AssetManager assets;
	Bitmap frameBuffer;
	Canvas canvas;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();
	
	// 
	public AndroidGraphics( AssetManager _assets, Bitmap _frameBuffer ){
		
		this.assets = _assets;
		this.frameBuffer = _frameBuffer;
		this.canvas = new Canvas( _frameBuffer );
		this.paint = new Paint();
		
	}
	
	// 
	@Override
	public Pixmap newPixmap( String _fileName, PixmapFormat _format ){
		
		// 変数宣言
		Config config = null;
		
		// 
		if( _format == PixmapFormat.RGB565 ){
			
			config = Config.RGB_565;
			
		}else if( _format == PixmapFormat.ARGB4444 ){
		
			config = Config.ARGB_4444;
			
		}else{
			
			config = Config.ARGB_8888;
			
		}
		
		// 変数宣言
		Options options = new Options();
		options.inPreferredConfig = config;
		
		InputStream in = null;
		Bitmap bitmap = null;
		
		try{
			
			in = assets.open( _fileName );
			bitmap = BitmapFactory.decodeStream( in );
			
			if( bitmap == null ){
				
				throw new RuntimeException( "Counldn't load bitmap from asset '" + _fileName + "'" );
				
			}
			
		}catch( IOException e ){

			throw new RuntimeException( "Counldn't load bitmap from asset '" + _fileName + "'" );			
			
		}finally{
			
			if( in != null ){
				
				try{
					
					in.close();
					
				}catch( IOException e ){
					
				}
				
			}
			
		}
		
		if( bitmap.getConfig() == Config.RGB_565 ){
			
			_format = PixmapFormat.RGB565;
			
		}else if( bitmap.getConfig() == Config.ARGB_4444 ){
			
			_format = PixmapFormat.ARGB4444;
			
		}else{
			
			_format = PixmapFormat.ARGB8888;
						
		}
		
		return new AndroidPixmap( bitmap, _format );		
		
	}
	
	// 
	@Override
	public void clear( int _color ){
		
		canvas.drawRGB( ( _color & 0xff0000 ) >> 16, ( _color & 0xff00 ) >> 8, ( _color & 0xff ) );
		
	}
	
	// 
	@Override
	public void drawPixel( int _x, int _y, int _color ){
		
		paint.setColor( _color );
		canvas.drawPoint( _x, _y, paint );
		
	}
	
	// 
	@Override
	public void drawLine( int _x1, int _y1, int _x2, int _y2, int _color ){
		
		paint.setColor( _color );
		canvas.drawLine( _x1, _y1, _x2, _y2, paint );
		
	}
	
	// 
	@Override
	public void drawRect( int _x, int _y, int _width, int _height, int _color ){
		
		paint.setColor( _color );
		paint.setStyle( Style.FILL );
		canvas.drawRect( _x, _y, _x + _width - 1, _y + _height - 1, paint );
		
	}
	
	// 
	@Override
	public void drawPixmap( Pixmap _pixmap, int _x, int _y, int _srcX, int _srcY, int _srcWidth, int _srcHeight ){
		
		srcRect.left = _srcX;
		srcRect.top = _srcY;
		srcRect.right = _srcX + _srcWidth - 1;
		srcRect.bottom = _srcY + _srcHeight - 1;

		dstRect.left = _x;
		dstRect.top = _y;
		dstRect.right = _x + _srcWidth - 1;
		dstRect.bottom = _y + _srcHeight - 1;
		
		canvas.drawBitmap( ((AndroidPixmap)_pixmap).bitmap, srcRect, dstRect, null );
		
	}
	
	// 
	@Override
	public void drawPixmap( Pixmap _pixmap, int _x, int _y ){
		
		canvas.drawBitmap( ((AndroidPixmap)_pixmap).bitmap, _x, _y, null );
		
	}
	
	// 
	@Override
	public int getWidth(){
		
		return frameBuffer.getWidth();
		
	}
	
	// 
	@Override
	public int getHeight(){
		
		return frameBuffer.getHeight();
		
	}
	
}
