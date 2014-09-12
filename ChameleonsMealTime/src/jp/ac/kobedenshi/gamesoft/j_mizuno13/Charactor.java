package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Charactor {
	
	private final static float ORIGINAL_SCALE = 1.0f;

	private Matrix chara_matrix = new Matrix();
	
	public static boolean waitting_flag;
	private int waitting_cnt;
	private boolean eye_flag;
	private int eye_cnt;
	public static boolean eye_space;
	private int eye_space_cnt;

	public static int eatting_cnt;
	public static boolean eatting_now_flag;
	
	public static int open_mouse_cnt;
	public static boolean open_mouse_flag;
	
	
	//コンストラクタで初期化
	public Charactor( float CHARA_POSITION_X, float CHARA_POSITION_Y ){
		chara_matrix.postTranslate( CHARA_POSITION_X, CHARA_POSITION_Y );
		chara_matrix.postScale( ORIGINAL_SCALE, ORIGINAL_SCALE );
		eatting_cnt = 0;
		eatting_now_flag = false;
		open_mouse_cnt = 0;
		open_mouse_flag = false;
		waitting_cnt = 0;
		waitting_flag = false;
		eye_cnt = 0;
		eye_space = false;
		eye_space_cnt = 0;
		eye_flag = false;
	}
	
	public void DrawChara( Canvas c, Bitmap bmp ){
		
		c.drawBitmap( bmp, chara_matrix, null );
		
	}

	public void DrawChara( Canvas c, Bitmap bmp, float x, float y ){
		Matrix ge_matrix = new Matrix();
		ge_matrix.postTranslate( x, y );
		c.drawBitmap( bmp, ge_matrix, null );
		
	}
	
	public void DrawChara( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4, Bitmap bmp5, Bitmap bmp6, float fever, float ang ){
		
		float alpha1 = ( ( ang + 1.0f ) / 2.0f );
		float alpha2 = ( ( ang - 1.0f ) / 2.0f );		
		
		LightingColorFilter filter1 = new LightingColorFilter(Color.rgb( (int)( 255 * alpha1 ), 255 + (int)( 255 * alpha2 ), 0 ), 0);
		Paint p = new Paint();
		p.setColorFilter( filter1 );
		p.setARGB( (int)( ( 255 * fever ) * alpha1 ), 0, 0, 0 );
		
		
		if( waitting_flag == false ){
			waitting_cnt += 1;
			eye_cnt = 0;
			eye_space_cnt = 0;
		}else if( waitting_flag == true ){
			waitting_cnt = 0;
		}
		if( waitting_cnt >= 35 ){
			waitting_flag = true;
		}
		if( waitting_flag == false ){
			c.drawBitmap( bmp1, chara_matrix, null );
			c.drawBitmap( bmp1, chara_matrix, p );
		}
		if( waitting_flag == true ){
			if( eye_space_cnt % 40 == 0 ){
				eye_space = false;
			}
			if( eye_space == false ){
				if( eye_flag == false ){
					eye_cnt += 1;
				}else{
					eye_cnt -= 1;
				}
				if( eye_cnt >= 5 ){
					eye_flag = true;
				}
				if( eye_cnt <= 0 ){
					eye_cnt = 0;
					eye_flag = false;
					eye_space = true;
					eye_space_cnt = 0;
				}
				switch( eye_cnt / 1 ){
				case 0:
					c.drawBitmap( bmp1, chara_matrix, null );					
					c.drawBitmap( bmp1, chara_matrix, p );
					break;
				case 1:
					c.drawBitmap( bmp6, chara_matrix, null );					
					c.drawBitmap( bmp6, chara_matrix, p );
					break;
				case 2:
					c.drawBitmap( bmp5, chara_matrix, null );					
					c.drawBitmap( bmp5, chara_matrix, p );
					break;
				case 3:
					c.drawBitmap( bmp4, chara_matrix, null );					
					c.drawBitmap( bmp4, chara_matrix, p );
					break;
				case 4:
					c.drawBitmap( bmp3, chara_matrix, null );					
					c.drawBitmap( bmp3, chara_matrix, p );
					break;
				case 5:
					c.drawBitmap( bmp2, chara_matrix, null );					
					c.drawBitmap( bmp2, chara_matrix, p );
					break;
				default:
					c.drawBitmap( bmp1, chara_matrix, null );					
					c.drawBitmap( bmp1, chara_matrix, p );
					break;				
				}
			}else{
				c.drawBitmap( bmp1, chara_matrix, null );
				c.drawBitmap( bmp1, chara_matrix, p );				
			}
			eye_space_cnt += 1;
		}		
	}	

	
	public void DrawChara( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4, Bitmap bmp5, Bitmap bmp6 ){		
		
		if( waitting_flag == false ){
			waitting_cnt += 1;
			eye_cnt = 0;
			eye_space_cnt = 0;
		}else if( waitting_flag == true ){
			waitting_cnt = 0;
		}
		if( waitting_cnt >= 35 ){
			waitting_flag = true;
		}
		if( waitting_flag == false ){
			c.drawBitmap( bmp1, chara_matrix, null );
			c.drawBitmap( bmp1, chara_matrix, null );
		}
		if( waitting_flag == true ){
			if( eye_space_cnt % 40 == 0 ){
				eye_space = false;
			}
			if( eye_space == false ){
				if( eye_flag == false ){
					eye_cnt += 1;
				}else{
					eye_cnt -= 1;
				}
				if( eye_cnt >= 5 ){
					eye_flag = true;
				}
				if( eye_cnt <= 0 ){
					eye_cnt = 0;
					eye_flag = false;
					eye_space = true;
					eye_space_cnt = 0;
				}
				switch( eye_cnt / 1 ){
				case 0:
					c.drawBitmap( bmp1, chara_matrix, null );					
					break;
				case 1:
					c.drawBitmap( bmp6, chara_matrix, null );					
					break;
				case 2:
					c.drawBitmap( bmp5, chara_matrix, null );					
					break;
				case 3:
					c.drawBitmap( bmp4, chara_matrix, null );					
					break;
				case 4:
					c.drawBitmap( bmp3, chara_matrix, null );					
					break;
				case 5:
					c.drawBitmap( bmp2, chara_matrix, null );					
					break;
				default:
					c.drawBitmap( bmp1, chara_matrix, null );					
					break;				
				}
			}else{
				c.drawBitmap( bmp1, chara_matrix, null );
			}
			eye_space_cnt += 1;
		}		
	}	

	
	public void DrawChara( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4, float fever, float ang ){
		

		float alpha1 = ( ( ang + 1.0f ) / 2.0f );
		float alpha2 = ( ( ang - 1.0f ) / 2.0f );
//		float alpha3 = ( ( ang + 1.0f ) / 2.0f );
		
		
		LightingColorFilter filter1 = new LightingColorFilter(Color.rgb( (int)( 255 * alpha1 ), 255 + (int)( 255 * alpha2 ), 0 ), 0);
		Paint p = new Paint();
		p.setColorFilter( filter1 );
		p.setARGB( (int)( ( 255 * fever ) * alpha1 ), 0, 0, 0 );

		
		if( open_mouse_flag == true ){
			open_mouse_cnt += 3;
			
		}
		if( open_mouse_flag == false ){
			open_mouse_cnt -= 1;
		}
		if( open_mouse_cnt >= 27 ){
			open_mouse_cnt = 27;
		}else if( open_mouse_cnt <= 0 ){
			open_mouse_cnt = 0;
		}
		
		switch( open_mouse_cnt / 7 ){
		case 0:
			c.drawBitmap( bmp1, chara_matrix, null );					
			c.drawBitmap( bmp1, chara_matrix, p );			
			break;		
		case 1:
			c.drawBitmap( bmp2, chara_matrix, null );					
			c.drawBitmap( bmp2, chara_matrix, p );			
			break;
		case 2:
			c.drawBitmap( bmp3, chara_matrix, null );					
			c.drawBitmap( bmp3, chara_matrix, p );
			break;
		case 3:
			c.drawBitmap( bmp4, chara_matrix, null );					
			c.drawBitmap( bmp4, chara_matrix, p );
			break;
		default:
			c.drawBitmap( bmp1, chara_matrix, null );
			c.drawBitmap( bmp1, chara_matrix, p );
			break;
		}
	}	

	public void DrawChara( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4, Bitmap bmp5, float fever, float ang ){
		
		float alpha1 = ( ( ang + 1.0f ) / 2.0f );
		float alpha2 = ( ( ang - 1.0f ) / 2.0f );		
		
		LightingColorFilter filter1 = new LightingColorFilter(Color.rgb( (int)( 255 * alpha1 ), 255 + (int)( 255 * alpha2 ), 0 ), 0);
		Paint p = new Paint();
		p.setColorFilter( filter1 );
		p.setARGB( (int)( ( 255 * fever ) * alpha1 ), 0, 0, 0 );

		
		if( eatting_now_flag == true ){
			eatting_cnt += 1;
		}
		if( eatting_cnt >= 9 ){
			eatting_now_flag = false;
			eatting_cnt = 0;
			Enemy.eatting_flag = false;
		}
		
		switch( eatting_cnt / 2 ){
		case 0:
			c.drawBitmap( bmp1, chara_matrix, null );					
			c.drawBitmap( bmp1, chara_matrix, p );			
			break;		
		case 1:
			c.drawBitmap( bmp2, chara_matrix, null );
			c.drawBitmap( bmp2, chara_matrix, p );			
			break;
		case 2:
			c.drawBitmap( bmp3, chara_matrix, null );
			c.drawBitmap( bmp3, chara_matrix, p );
			break;
		case 3:
			c.drawBitmap( bmp4, chara_matrix, null );
			c.drawBitmap( bmp4, chara_matrix, p );
			break;
		case 4:
			c.drawBitmap( bmp5, chara_matrix, null );
			c.drawBitmap( bmp5, chara_matrix, p );
			break;
		default:
			c.drawBitmap( bmp1, chara_matrix, null );
			c.drawBitmap( bmp1, chara_matrix, p );
			break;
		}
	}
	
	public void DrawChara( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4 ){
		
		if( open_mouse_flag == true ){
			open_mouse_cnt += 3;
			
		}
		if( open_mouse_flag == false ){
			open_mouse_cnt -= 1;
		}
		if( open_mouse_cnt >= 27 ){
			open_mouse_cnt = 27;
		}else if( open_mouse_cnt <= 0 ){
			open_mouse_cnt = 0;
		}
		
		switch( open_mouse_cnt / 7 ){
		case 0:
			c.drawBitmap( bmp1, chara_matrix, null );					
			break;		
		case 1:
			c.drawBitmap( bmp2, chara_matrix, null );					
			break;
		case 2:
			c.drawBitmap( bmp3, chara_matrix, null );					
			break;
		case 3:
			c.drawBitmap( bmp4, chara_matrix, null );					
			break;
		default:
			c.drawBitmap( bmp1, chara_matrix, null );
			break;
		}
	}	
	
	public void DrawChara( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4, Bitmap bmp5 ){
		
				if( eatting_now_flag == true ){
			eatting_cnt += 1;
		}
		if( eatting_cnt >= 9 ){
			eatting_now_flag = false;
			eatting_cnt = 0;
			Enemy.eatting_flag = false;
		}
		
		switch( eatting_cnt / 2 ){
		case 0:
			c.drawBitmap( bmp1, chara_matrix, null );					
			break;		
		case 1:
			c.drawBitmap( bmp2, chara_matrix, null );
			break;
		case 2:
			c.drawBitmap( bmp3, chara_matrix, null );
			break;
		case 3:
			c.drawBitmap( bmp4, chara_matrix, null );
			break;
		case 4:
			c.drawBitmap( bmp5, chara_matrix, null );
			break;
		default:
			c.drawBitmap( bmp1, chara_matrix, null );
			break;
		}
	}	
	
	
	public void DrawEye( Canvas c, Bitmap bmp, float Angle, Paint p ){
		Matrix eye_matrix = new Matrix();
		eye_matrix.postRotate( Angle, 143.0f, 54.0f );
		eye_matrix.postTranslate( 0.0f, 250.0f );
		c.drawBitmap( bmp, eye_matrix, p );		
	}
	
	
	
}
