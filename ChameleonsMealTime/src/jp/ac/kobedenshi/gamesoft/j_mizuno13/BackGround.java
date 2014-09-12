package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class BackGround {
	
	private Rect src_background, dst_background;
	private Matrix[] cloud_matrix;
	private Matrix sun_matrix;
	private Matrix moon_matrix;
	private Matrix[] star_matrix;	
	private int[] cloud_scale;
	private float[] cloud_position_x;
	private float[] cloud_position_y;
	private float[] cloud_move_x;
	private float[] cloud_move_y;
	private float[] cloud_add_x;	
	private float[] cloud_base_angle;
	private float angle;
	private float cloud_angle;
	private float sun_angle;
	private float sun_scale;
	private float sun_position_y;
	private boolean sun_scale_flag;
	private float moon_angle;
	private float moon_position_y;
	
	private int[] alpha;
	private int alpha3;
	private final static float angle_speed = 0.05f;
	private final static int CLOUD_NUMBER = 5;
	private final static int STAR_NUMBER = 18;

	//コンストラクタで初期化
	public BackGround( int bmpWidth, int bmpHeight,  int DispWidth, int DispHeight ){
		src_background = new Rect( 0, 0, bmpWidth, bmpHeight );
		dst_background = new Rect( 0, 0, DispWidth, DispHeight );
		cloud_matrix = new Matrix[CLOUD_NUMBER];
		cloud_scale = new int[CLOUD_NUMBER];
		cloud_position_x = new float[CLOUD_NUMBER];
		cloud_position_y = new float[CLOUD_NUMBER];		
		cloud_move_x = new float[CLOUD_NUMBER];
		cloud_move_y = new float[CLOUD_NUMBER];
		cloud_add_x = new float[CLOUD_NUMBER];
		cloud_base_angle = new float[CLOUD_NUMBER];
		alpha = new int[CLOUD_NUMBER];
		Random rnd = new Random();
		for( int i = 0; i < CLOUD_NUMBER; i++ ){
			cloud_base_angle[i] = i * 10.0f;
			cloud_position_x[i] = 260.0f + i * 160.0f;
			cloud_position_y[i] = 50.0f + i * 40.0f;
			cloud_add_x[i] = ( (float)rnd.nextInt( 2 ) + 1.0f ) * 0.8f;
			alpha[i] = 255;
		}
		
		star_matrix = new Matrix[STAR_NUMBER];
		for( int i = 0; i < STAR_NUMBER; i++ ){
			star_matrix[i] = new Matrix();
			star_matrix[i].postTranslate( 20.0f + (float)( ( rnd.nextInt( 10 ) + 1 ) * 60 ), 10.0f + (float)( ( rnd.nextInt( 10 ) + 1 ) * 30 ) );
		}

		sun_scale = 0.0f;
		sun_scale_flag = true;
		sun_position_y = 35.0f;
		moon_position_y = 460.0f;
		alpha3 = 0;
		
	}
	
	public void DrawBackground( Canvas c, Bitmap bmp ){
		c.drawBitmap( bmp, src_background, dst_background, null );
	}
		
	public void DrawBackground( Canvas c, Bitmap bmp, int time ){
		Paint p = new Paint();
		int alpha = 255 - time / 3;
		if( alpha < 0 ){
			alpha = 0;
		}
		p.setARGB( alpha, 255, 0, 0 );
		c.drawBitmap( bmp, src_background, dst_background, p );
	}
	
	public void DrawBackgroundMoon( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, int time ){
		Random rnd = new Random();
		moon_matrix = new Matrix();
		
		Paint p = new Paint();
		if( time < 400 ){
			moon_position_y -= 1.0f;
		}
		if( time < 300 ){
			alpha3 += 1;			
		}
		if( alpha3 > 255 ){
			alpha3 = 255;
		}
		if( moon_position_y <= 55.0f ){
			moon_position_y = 55.0f;
		}
		p.setARGB( alpha3, 0, 0, 0 );
		
		
		float ma = (float)Math.sin( moon_angle ) * 20.0f;
		moon_angle += 0.05f;
		if( moon_angle >= 360.0f ){
			moon_angle = 0.0f;
		}
		
		moon_matrix.postRotate( ma, 64.0f, 68.5f );
		moon_matrix.postTranslate( 460.0f, moon_position_y );
		c.drawBitmap( bmp1, moon_matrix, p );
				
		for( int i = 0; i < STAR_NUMBER; i++ ){
			if( rnd.nextInt( 2 ) == 0 ){
				c.drawBitmap( bmp2, star_matrix[i], p );
			}else{
				c.drawBitmap( bmp3, star_matrix[i], p );				
			}
		}
		
	}
		
	public void DrawBackground( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, int time ){
		Paint p = new Paint();
		int alpha2 = time / 3;
		if( alpha2 < 0 ){
			alpha2 = 0;
		}
		if( alpha2 > 255 ){
			alpha2 = 255;
		}
		
		if( time < 600 && alpha2 > 0 ){
			sun_position_y += 1.0f;
		}		
		p.setARGB( alpha2, 0, 0, 0 );
		
		sun_matrix = new Matrix();
		sun_angle += 2.0f;
		if( sun_angle >= 360.0f ){
			sun_angle = 0.0f;
		}
		if( sun_scale_flag == true ){
			sun_scale += 0.005f;
		}else{
			sun_scale -= 0.005f;
		}
		if( sun_scale >= 0.1f ){
			sun_scale_flag = false;
		}else if( sun_scale <= 0.0f ){
			sun_scale_flag = true;
		}
		sun_matrix.postRotate( sun_angle, 100.0f, 100.0f );
		sun_matrix.postScale( 0.8f - sun_scale, 0.8f - sun_scale, 100.0f, 100.0f );
		sun_matrix.postTranslate( 430.0f, sun_position_y );
		
		if( alpha2 > 0 ){
			c.drawBitmap( bmp1, src_background, dst_background, null );
			c.drawBitmap( bmp2, sun_matrix, p );
		}
		
		angle += angle_speed;
		if( angle >= 360.0f ){
			angle = 0.0f;
		}

		
		for( int i = 0; i < CLOUD_NUMBER; i++ ){
			
			cloud_base_angle[i] += angle_speed / 2;
			if( cloud_base_angle[i] >= 360.0f ){
				cloud_base_angle[i] = 0.0f;
			}

			
			cloud_matrix[i] = new Matrix();

			cloud_base_angle[i] += cloud_angle;

			cloud_scale[i] += 1;
			cloud_move_x[i] += cloud_add_x[i];		
			if( cloud_position_x[i] + cloud_move_x[i] >= 700.0f ){
				alpha[i] -= 4;
				if( alpha[i] <= 0 ){
					alpha[i] = 0;
				}				
			}
			if( cloud_position_x[i] + cloud_move_x[i] < 700.0f ){
				alpha[i] += 2;
				if( alpha[i] >= 255 ){
					alpha[i] = 255;
				}
			}			
			if( cloud_position_x[i] + cloud_move_x[i] >= 800.0f ){
				cloud_move_x[i] = 0;
				cloud_position_x[i] = 260.0f;
				Random rnd = new Random();
				cloud_add_x[i] = ( (float)rnd.nextInt( 2 ) + 1.0f ) * 0.8f;
				cloud_position_y[i] = 70.0f + ( ( rnd.nextInt( 5 ) + 1 ) * 20.0f );
			}
			cloud_move_y[i] += Math.sin( angle ) * 1.0f;
			
			if( (float)Math.sin( cloud_angle ) <= 0 ){
				cloud_matrix[i].postScale( 0.8f - ( (float)Math.sin( cloud_base_angle[i] ) * 2.0f ) * -0.1f, 0.8f - ( (float)Math.sin( cloud_base_angle[i] ) * 2.0f ) * -0.1f );
			}else{
				cloud_matrix[i].postScale( 0.8f - ( (float)Math.sin( cloud_base_angle[i] ) * 2.0f ) * 0.1f, 0.8f - ( (float)Math.sin( cloud_base_angle[i] ) * 2.0f ) * 0.1f );				
			}
			cloud_matrix[i].postTranslate( cloud_position_x[i] + cloud_move_x[i], cloud_position_y[i] + cloud_move_y[i] );
						
			Paint cloud_paint[] = new Paint[CLOUD_NUMBER];
            cloud_paint[i] = new Paint();
			
			cloud_paint[i].setAlpha( alpha[i] );
			if( time > 600 ){
				c.drawBitmap( bmp3, cloud_matrix[i], cloud_paint[i] );
			}else{
				c.drawBitmap( bmp3, cloud_matrix[i], p );
			}
		}
	}	
	
	public void DrawBackshadow( Canvas c, Bitmap bmp ){
		Paint p = new Paint();
		Rect src_shadow = new Rect();
		Rect dst_shadow = new Rect();

		int alpha = 50;

		src_shadow.set( 0, 0, 800, 135 );
		dst_shadow.set( 0, 0, 800, 135 );
		
		p.setARGB( alpha, 0, 0, 0 );
		
		c.drawBitmap( bmp, src_shadow, dst_shadow, p );
		
	}
	
	public void DrawBackshadow( Canvas c, Bitmap bmp, int cnt ){
		Paint p = new Paint();
		Rect src_shadow = new Rect();
		Rect dst_shadow = new Rect();

		int alpha = cnt;

		src_shadow.set( 0, 0, 800, 480 );
		dst_shadow.set( 0, 0, 800, 480 );
		
		p.setARGB( alpha, 0, 0, 0 );
		
		c.drawBitmap( bmp, src_shadow, dst_shadow, p );
		
	}	
	
	public void DrawPauseground( Canvas c, Bitmap bmp ){
		Paint p = new Paint();
		p.setARGB( 200, 255, 0, 0 );		
		c.drawBitmap( bmp, src_background, dst_background, p );
	}

}
