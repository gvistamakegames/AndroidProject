package Class;

import android.graphics.Bitmap;

import com.baseproject.framework.Interface.Graphics.PixmapFormat;
import com.baseproject.framework.Interface.Pixmap;

// 
public class AndroidPixmap implements Pixmap {

	// �ϐ��錾
	Bitmap bitmap;
	PixmapFormat format;
	
	// 
	public AndroidPixmap( Bitmap _bitmap, PixmapFormat _format ){
		
		this.bitmap = _bitmap;
		this.format = _format;
		
	}
	
	// 
	@Override
	public int getWidth(){
		
		return bitmap.getWidth();
	
	}
	
	// 
	@Override
	public int getHeight(){
	
		return bitmap.getHeight();
		
	}

	// 
	@Override
	public PixmapFormat getFormat(){
		
		return format;
		
	}
	
	// 
	@Override
	public void dispose(){
	
		bitmap.recycle();
		
	}
	
}
