package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;

public class UI {

//	private final static int TIME_LEFT_POSITION_X = 575;
	private final static int TIME_LEFT_POSITION_X = 160;
	private final static int SCORE_LEFT_POSITION_X = 130;
	private final static int FEVER_LEFT_POSITION_X = 665;
	private final static int FEVER_LEFT_POSITION_Y = 85;
	private final static int SCORE_POSITION_Y = 65;
	
	private float timebar_angle;
	private float scorebar_angle;
	private float alpha_angle;
	private final static float angle_speed = 0.5f;
	
	private float timebar_move_y = 0;
	private float scorebar_move_y = 0;

	public long time_cnt = 0;
	public long start_time = 0;
	public long stop_time = 0;
	public long now_time = 0;
	public long uncount_time = 0;
	public long restart_time = 0;

	private static boolean just_mark_flag;
	private int just_mark_cnt;
	private float just_mark_angle;
	private float just_mark_scale;	
	private static boolean combo_mark_flag;
	private int combo_mark_cnt;
	private int combo_max = 0;
	
	private Matrix gage_matrix;
	private Matrix stamina_matrix;
	private Matrix fever_matrix;
	private Matrix fever_gage_matrix;
	private Matrix fever_mark_matrix;
	private Matrix fever_sentence_matrix;
	
	public static float stamina_x;
	public static boolean stamina_up;
	public static boolean stamina_up_more;
	
	private static float fever_x;
	private float fever_mark_x = 800.0f;
	private float fever_mark_y = 90.0f;
	private float fever_sentence_x = 800.0f;
	private float fever_sentence_y = 134.0f;
	
	private float Angle;
	
	private boolean sentence_go;
	private int wait_cnt;
	
	private static float just_hit_cnt;
	
	private static float hit_up_cnt;
	private static boolean hit_up_flag;
	
	private int move_cnt;
	
	
	//コンストラクタ
	public UI(){
		
	}
	
	public void Init(){
		alpha_angle = 180.0f;
		timebar_angle = 0.0f;
		scorebar_angle = 180.0f;
		start_time = SystemClock.currentThreadTimeMillis();
		stamina_x = 1.0f;
		stamina_up = false;
		stamina_up_more = false;
		setJust_mark_flag(false);
		setCombo_mark_flag(false);
		just_mark_cnt = 0;
		just_hit_cnt = 0.0f;
		combo_mark_cnt = 0;
		setFever_x(0.0f);
		Angle = 0.0f;
		sentence_go = true;
		wait_cnt = 0;
		hit_up_cnt = 0;
		hit_up_flag = false;
		move_cnt = 0;
	}
	
	public void StopTime(){
		stop_time = now_time - start_time;
	}
	
	public void RestartTime(){
		start_time = uncount_time;
	}
		
	//事実上のスタミナゲージです
	public void DrawTime( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, boolean fever, float ang ){
		gage_matrix = new Matrix();
		stamina_matrix = new Matrix();	

		Rect time_src, time_dst;
		time_src = new Rect();
		time_dst = new Rect();

		alpha_angle += 0.2f;
		if( alpha_angle >= 360.0f ){
			alpha_angle = 0.0f;
		}		
		
		timebar_angle += angle_speed;
		if( timebar_angle >= 360.0f ){
			timebar_angle = 0.0f;
		}
		if( Math.sin( timebar_angle ) >= 0 ){
			timebar_move_y = 5.0f;
		}else{
			timebar_move_y = 0.0f;
		}
		time_src.set( 0, 0, 165, 90 );
		time_dst.set( TIME_LEFT_POSITION_X - 155, 65 + (int)timebar_move_y, TIME_LEFT_POSITION_X -155 + 165, 155 + (int)timebar_move_y );
		c.drawBitmap( bmp1, time_src, time_dst, null );

		if( GameDraw.pause_flag == false ){
			if( stamina_up == false ){
				if( fever == false ){
					if( stamina_x >= 0.0f ){
						stamina_x -= 0.00035f;
					}
				}else{
					if( stamina_x >= 0.0f ){
						stamina_x -= 0.00045f;
					}					
				}
			}
			//通常のスタミナの回復度
			if( stamina_up == true ){
				stamina_x += 0.01f;
				stamina_up = false;
			}
			//ジャストやコンボが決まった時のスタミナの回復度
			if( stamina_up_more == true ){
				stamina_x += 0.045f;
				stamina_up_more = false;				
			}

			if( stamina_x <= 0.0f ){
				stamina_x = 0.0f;
			}
			if( stamina_x >= 1.0f ){
				stamina_x = 1.0f;
			}
		}

		
		float alpha = ( ( ang + 1.0f ) / 2.0f );		
		Paint p = new Paint();
		
		p.setARGB( (int)( ( 255 ) * alpha ), 0, 0, 0 );

		stamina_matrix.postScale( stamina_x, 0.6f );
		stamina_matrix.postTranslate( TIME_LEFT_POSITION_X + 12.5f, 73.5f );
		
		c.drawBitmap( bmp3, stamina_matrix, p );
				
		gage_matrix.postScale( 1.0f, 0.6f );
		gage_matrix.postTranslate( TIME_LEFT_POSITION_X + 10.0f, 72.25f );
		c.drawBitmap( bmp2, gage_matrix, null );
		
	}

	public void DrawScore( Canvas c, Bitmap bmp1, Bitmap bmp2, int score ){
		Rect score_src, score_dst;
		score_src = new Rect();
		score_dst = new Rect();
		int dust;
		//百万の位
		dust = score / 1000000;
		score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
		score_dst.set( SCORE_LEFT_POSITION_X, SCORE_POSITION_Y - 60, SCORE_LEFT_POSITION_X + 40, SCORE_POSITION_Y );
		c.drawBitmap( bmp1, score_src, score_dst, null );		
		//十万の位
		dust = score / 100000 - ( ( score / 1000000 ) * 10 );
		score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
		score_dst.set( SCORE_LEFT_POSITION_X + 40, SCORE_POSITION_Y - 60, SCORE_LEFT_POSITION_X + 80, SCORE_POSITION_Y );
		c.drawBitmap( bmp1, score_src, score_dst, null );		
		//万の位
		dust = score / 10000 - ( ( score / 100000 ) * 10 );
		score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
		score_dst.set( SCORE_LEFT_POSITION_X + 80, SCORE_POSITION_Y - 60, SCORE_LEFT_POSITION_X + 120, SCORE_POSITION_Y );
		c.drawBitmap( bmp1, score_src, score_dst, null );
        //千の位
		dust = ( score / 1000 ) - ( ( score / 10000 ) * 10 );
		score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
		score_dst.set( SCORE_LEFT_POSITION_X + 120, SCORE_POSITION_Y - 60, SCORE_LEFT_POSITION_X + 160, SCORE_POSITION_Y );
		c.drawBitmap( bmp1, score_src, score_dst, null );
        //百の位
		dust = ( score / 100 ) - ( ( score / 1000 ) * 10 );
		score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
		score_dst.set( SCORE_LEFT_POSITION_X + 160, SCORE_POSITION_Y - 60, SCORE_LEFT_POSITION_X + 200, SCORE_POSITION_Y );
		c.drawBitmap( bmp1, score_src, score_dst, null );
        //十の位
		dust = ( score / 10 ) - ( ( score / 100 ) * 10 );
		score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
		score_dst.set( SCORE_LEFT_POSITION_X + 200, SCORE_POSITION_Y - 60, SCORE_LEFT_POSITION_X + 240, SCORE_POSITION_Y );
		c.drawBitmap( bmp1, score_src, score_dst, null );
        //一の位
		dust =  score % 10;
		score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
		score_dst.set( SCORE_LEFT_POSITION_X + 240, SCORE_POSITION_Y - 60, SCORE_LEFT_POSITION_X + 280, SCORE_POSITION_Y );
		c.drawBitmap( bmp1, score_src, score_dst, null );
	
		scorebar_angle += angle_speed;
		if( scorebar_angle >= 360.0f ){
			scorebar_angle = 0.0f;
		}
		if( Math.sin( scorebar_angle ) >= 0 ){
			scorebar_move_y = 5.0f;
		}else{
			scorebar_move_y = 0.0f;
		}		
		score_src.set( 164, 0, 290, 50 );
		score_dst.set( SCORE_LEFT_POSITION_X - 130, 5 + (int)scorebar_move_y, SCORE_LEFT_POSITION_X - 130 + 127, 55 + (int)scorebar_move_y );
		c.drawBitmap( bmp2, score_src, score_dst, null );		
			
	}

	public void DrawScore( Canvas c, Bitmap bmp, int score, int pos_x, int pos_y ){
		Rect score_src, score_dst;
		score_src = new Rect();
		score_dst = new Rect();
		int dust;
		//百万の位
		if( score >= 1000000 ){
			dust = score / 1000000;
			score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
			score_dst.set( pos_x, pos_y - 60, pos_x + 40, pos_y );
			c.drawBitmap( bmp, score_src, score_dst, null );
		}
		//十万の位
		if( score >= 100000 ){
			dust = score / 100000 - ( ( score / 1000000 ) * 10 );
			score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
			score_dst.set( pos_x + 40, pos_y - 60, pos_x + 80, pos_y );
			c.drawBitmap( bmp, score_src, score_dst, null );
		}
		//万の位
		if( score >= 10000 ){
			dust = score / 10000 - ( ( score / 100000 ) * 10 );
			score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
			score_dst.set( pos_x + 80, pos_y - 60, pos_x + 120, pos_y );
			c.drawBitmap( bmp, score_src, score_dst, null );
		}
        //千の位
		if( score >= 1000 ){
		dust = ( score / 1000 ) - ( ( score / 10000 ) * 10 );
		score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
		score_dst.set( pos_x + 120, pos_y - 60, pos_x + 160, pos_y );
		c.drawBitmap( bmp, score_src, score_dst, null );
		}
        //百の位
		if( score >= 100 ){
			dust = ( score / 100 ) - ( ( score / 1000 ) * 10 );
			score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
			score_dst.set( pos_x + 160, pos_y - 60, pos_x + 200, pos_y );
			c.drawBitmap( bmp, score_src, score_dst, null );
		}
        //十の位
		if( score >= 10 ){
			dust = ( score / 10 ) - ( ( score / 100 ) * 10 );
			score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
			score_dst.set( pos_x + 200, pos_y - 60, pos_x + 240, pos_y );
			c.drawBitmap( bmp, score_src, score_dst, null );
		}
        //一の位
		dust =  score % 10;
		score_src.set( 0 + 40 * dust, 0, 40 + 40 * dust, 60 );
		score_dst.set( pos_x + 240, pos_y - 60, pos_x + 280, pos_y );
		c.drawBitmap( bmp, score_src, score_dst, null );
				
	}

	public void DrawFeverMark( Canvas c, Bitmap bmp1, Bitmap bmp2 ){
		//各マトリクスはここでインスタンス化
		fever_sentence_matrix = new Matrix();		
		//フィーバーのマークと文字を右から左へ移動させる為の計算
		if( sentence_go == true && fever_sentence_x > -720.0f ){
			fever_sentence_x -= 40.0f; 
		}
		if( fever_sentence_x <= 60.0f ){
			sentence_go = false;
		}
		if( sentence_go == false ){
			wait_cnt += 1;			
		}
		if( wait_cnt >= 40 ){
			sentence_go = true;			
		}
		
		Paint p = new Paint();
		Rect src_shadow = new Rect();
		Rect dst_shadow = new Rect();

		int alpha = 80;

		src_shadow.set( 0, 0, 800, 250 );
		dst_shadow.set( 0, 114, 800, 364 );
		
		p.setARGB( alpha, 0, 0, 0 );
		
		if( fever_sentence_x > -720.0f ){
			c.drawBitmap( bmp2, src_shadow, dst_shadow, p );
		}
		
		
		fever_sentence_matrix.postTranslate( fever_sentence_x, fever_sentence_y );
		c.drawBitmap( bmp1, fever_sentence_matrix, null );

	}
	
	public void DrawFeverMark( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4, Bitmap bmp5, Bitmap bmp6 ){
		//各マトリクスはここでインスタンス化
		fever_mark_matrix = new Matrix();
		fever_sentence_matrix = new Matrix();
		
		//サイン波で数値を計算する用の角度を回す
		Angle += 1.0f;
		if( Angle >= 360.0f ){
			Angle = 0.0f;
		}
				
		//フィーバーのマークと文字を右から左へ移動させる為の計算
		if( fever_mark_x > -500.0f ){
			fever_mark_x -= 18.0f;
			fever_sentence_x -= 18.0f; 
		}
		
		//後ろのギザギザマークの拡大縮小率を設定(0.8～1.0の間で推移するように)
		fever_mark_matrix.postScale( 0.8f - (float)( Math.sin( Angle ) * 0.2f ), 0.8f - (float)( Math.sin( Angle ) * 0.2f ), 250.0f, 150.0f );
		
		//フィーバーのマークと文字の移動量を設定		
		fever_mark_matrix.postTranslate( fever_mark_x, fever_mark_y );
		fever_sentence_matrix.postTranslate( fever_sentence_x, fever_sentence_y );
		
		//後ろのギザギザマーク		
		c.drawBitmap( bmp1, fever_mark_matrix, null );
		
		//フィーバーの文字表示
		if( (float)Math.sin( Angle ) < -0.75f && (float)Math.sin( Angle ) >= -1.0f ){
			c.drawBitmap( bmp5, fever_sentence_matrix, null );			
		}
		if( (float)Math.sin( Angle ) < 0.5f && (float)Math.sin( Angle ) >= -0.75f ){
			c.drawBitmap( bmp6, fever_sentence_matrix, null );			//4
		}
		if( (float)Math.sin( Angle ) < 0.25f && (float)Math.sin( Angle ) >= -0.5f ){
			c.drawBitmap( bmp2, fever_sentence_matrix, null );			
		}
		if( (float)Math.sin( Angle ) < 0.0f && (float)Math.sin( Angle ) >= -0.25f ){
			c.drawBitmap( bmp5, fever_sentence_matrix, null );			
		}
		if( (float)Math.sin( Angle ) < 0.25f && (float)Math.sin( Angle ) >= 0.0f ){
			c.drawBitmap( bmp6, fever_sentence_matrix, null );			
		}
		if( (float)Math.sin( Angle ) < 0.5f && (float)Math.sin( Angle ) >= 0.25f ){
			c.drawBitmap( bmp5, fever_sentence_matrix, null );			
		}
		if( (float)Math.sin( Angle ) < 0.75f && (float)Math.sin( Angle ) >= 0.5f ){
			c.drawBitmap( bmp2, fever_sentence_matrix, null );			
		}
		if( (float)Math.sin( Angle ) <= 1.0f && (float)Math.sin( Angle ) >= 0.75f ){
			c.drawBitmap( bmp6, fever_sentence_matrix, null );			//4
		}		

	}
	
	public void DrawFeverGage( Canvas c, Bitmap bmp1, Bitmap bmp2, boolean fever ){
		fever_matrix = new Matrix();
		fever_gage_matrix = new Matrix();
		
		fever_gage_matrix.postScale( 0.58f, 0.6f );
		fever_gage_matrix.postTranslate( FEVER_LEFT_POSITION_X, FEVER_LEFT_POSITION_Y );

		fever_matrix.postScale( getFever_x(), 1.0f );
		fever_matrix.postTranslate( FEVER_LEFT_POSITION_X + 3.0f, FEVER_LEFT_POSITION_Y + 2.5f );
		
		if( fever == false ){
			//チャンスゲージを増加させる
			if( just_mark_cnt >= 5 && just_mark_cnt <= 15 ){
				setFever_x(getFever_x() + 0.01f);
			}
		}else{
			setFever_x(getFever_x() - 0.0025f);
		}
		//※あえてバーの長さを調節しています
		if( getFever_x() >= 0.98f ){
			GameDraw.fever_mode = true;
		}
		//※ゼロ未満にすることでスタート直後に実行されることを防いでいます
		if( getFever_x() < 0.0f ){
			setFever_x(0.0f);
			fever_mark_x = 800.0f;
			fever_mark_y = 90.0f;
			fever_sentence_x = 800.0f;
			fever_sentence_y = 134.0f;
			wait_cnt = 0;
			GameDraw.now_feverring = false;
			GameDraw.finish_feverring = true;
		}
		
	}
	
	public void DrawJustHitMark( Canvas c, Bitmap bmp, float x, float y ){
		Matrix just_matrix = new Matrix();
		just_hit_cnt += 0.15f;
		if( just_hit_cnt >= 1.0f ){
			just_hit_cnt = 1.0f;
		}
		just_matrix.postScale( just_hit_cnt, just_hit_cnt, 30.0f, 30.0f );
		just_matrix.postTranslate( x, y );
		c.drawBitmap( bmp, just_matrix, null );		
	}
	
	public void DrawHitJ( Canvas c, Bitmap bmp ){
		Matrix just_mark_matrix = new Matrix();
		just_mark_cnt += 1;
		if( just_mark_cnt <= 30 ){
			just_mark_angle += 25.0f;
		}
		just_mark_scale += 0.4f;
		if( just_mark_scale >= 1.0f ){
			just_mark_scale = 1.0f;
		}
		just_mark_matrix.postRotate( just_mark_angle, 40.0f, 40.0f );
		just_mark_matrix.postScale( just_mark_cnt * 0.02f, just_mark_cnt * 0.02f, 40.0f, 40.0f );
		just_mark_matrix.postTranslate( 100.0f, 235.0f );
		c.drawBitmap( bmp, just_mark_matrix, null );
		if( just_mark_cnt >= 40 ){
			just_mark_cnt = 0;
			just_mark_angle = 0.0f;
			just_mark_scale = 0.0f;
			UI.setJust_mark_flag( false );
		}		
	}
	
	public void DrawHitJ( Canvas c, Bitmap bmp, float x, float y ){
		just_mark_cnt += 1;
		Rect src = new Rect();
		Rect dst = new Rect();
		src.set( 0, 0, 220, 140 );
		dst.set( (int)x, (int)y, (int)x + 220, (int)y + 140 );
		c.drawBitmap( bmp, src, dst, null );
		if( just_mark_cnt >= 50 ){
			just_mark_cnt = 0;
			UI.setJust_mark_flag( false );
		}
	}

	public void DrawHitC( Canvas c, Bitmap bmp1, Bitmap bmp2, float x, float y, int combo_cnt ){
		if( combo_max < combo_cnt ){
			combo_max += 1;
		}
		//コンボ中に新たにコンボを上書きされた時の処理
		if( Enemy.isCombo_cnt_switch() == true ){
			combo_mark_cnt = 0;
			combo_max = 0;
			Enemy.setCombo_cnt_switch( false );
			just_mark_cnt = 0;
			just_mark_angle = 0.0f;
			just_mark_scale = 0.0f;
			UI.setJust_mark_flag( false );
		}
		combo_mark_cnt += 1;
		Rect num_src = new Rect();
		Rect num_dst = new Rect();		
		if( combo_cnt <= 9 ){
			num_src.set( 0 + combo_max * 40, 0, 40 + combo_max * 40, 60 );
			num_dst.set( (int)x + 40, (int)y + 15, (int)x + 40 + 40, (int)y + 15 + 60 );		
			c.drawBitmap( bmp2, num_src, num_dst, null );
			//×マーク
			num_src.set( 400, 0, 440, 60 );
			num_dst.set( (int)x, (int)y + 15, (int)x + 40, (int)y + 15 + 60 );		
			c.drawBitmap( bmp2, num_src, num_dst, null );			
		}else{
			if( combo_max <= 9 ){
				//１の位
				num_src.set( 0 + ( combo_max % 10 ) * 40, 0, 40 + ( combo_max % 10 ) * 40, 60 );
				num_dst.set( (int)x + 45, (int)y + 15, (int)x + 45 + 40, (int)y + 15 + 60 );		
				c.drawBitmap( bmp2, num_src, num_dst, null );
				//×マーク
				num_src.set( 400, 0, 440, 60 );
				num_dst.set( (int)x + 5, (int)y + 15, (int)x + 5 + 40, (int)y + 15 + 60 );		
				c.drawBitmap( bmp2, num_src, num_dst, null );			
			}else{
				//１０の位
				num_src.set( 0 + ( combo_max / 10 ) * 40, 0, 40 + ( combo_max / 10 ) * 40, 60 );
				num_dst.set( (int)x + 5, (int)y + 15, (int)x + 5 + 40, (int)y + 15 + 60 );		
				c.drawBitmap( bmp2, num_src, num_dst, null );
				//１の位
				num_src.set( 0 + ( combo_max % 10 ) * 40, 0, 40 + ( combo_max % 10 ) * 40, 60 );
				num_dst.set( (int)x + 45, (int)y + 15, (int)x + 45 + 40, (int)y + 15 + 60 );		
				c.drawBitmap( bmp2, num_src, num_dst, null );
				//×マーク
				num_src.set( 400, 0, 440, 60 );
				num_dst.set( (int)x - 35, (int)y + 15, (int)x - 35 + 40, (int)y + 15 + 60 );		
				c.drawBitmap( bmp2, num_src, num_dst, null );
			}
		}
		if( combo_mark_cnt >= 40 ){
			combo_mark_cnt = 0;
			UI.setCombo_mark_flag( false );
			just_mark_cnt = 0;
			just_mark_angle = 0.0f;
			just_mark_scale = 0.0f;
			UI.setJust_mark_flag( false );
		}
	}
	
	public void DrawHitUP( Canvas c, Bitmap bmp ){
		Matrix hit_mark_up = new Matrix();
		hit_up_cnt += 0.045f;
		if( hit_up_cnt >= 1.0f ){
			hit_up_cnt = 0.0f;
			hit_up_flag = false;
		}
		hit_mark_up.postScale( 1.0f, hit_up_cnt, 50.0f, 160.0f );
		hit_mark_up.postTranslate( 30.0f, 250.0f );
		c.drawBitmap( bmp, hit_mark_up, null );	
	}
	
	public void DrawSentence( Canvas c, Bitmap bmp, int s_x, int s_y, int s_width, int s_height, int pos_x, int pos_y ){
		Rect s_src = new Rect();
		Rect s_dst = new Rect();
		s_src.set( s_x, s_y, s_x + s_width, s_y + s_height );
		s_dst.set( pos_x, pos_y, pos_x + s_width, pos_y + s_height );
		c.drawBitmap( bmp, s_src, s_dst, null );			
	}
	
	public void DrawSentence( Canvas c, Bitmap bmp, int s_x, int s_y, int s_width, int s_height, int pos_x, int pos_y, float sin ){
		Rect s_src = new Rect();
		Rect s_dst = new Rect();
		s_src.set( s_x, s_y, s_x + s_width, s_y + s_height );
		s_dst.set( pos_x, pos_y, pos_x + s_width, pos_y + s_height );
		
		LightingColorFilter filter1 = new LightingColorFilter(Color.rgb(255, 255, 0), 0);
		Paint p = new Paint();
		p.setColorFilter( filter1 );
		p.setARGB( 205 + (int)( 50 * sin ), 0, 0, 0 );		
		c.drawBitmap( bmp, s_src, s_dst, p );			
	}

	public void DrawSentence( Canvas c, Bitmap bmp, int s_x, int s_y, int s_width, int s_height, int pos_x, int pos_y, String on ){
		Rect s_src = new Rect();
		Rect s_dst = new Rect();
		s_src.set( s_x, s_y, s_x + s_width, s_y + s_height );
		s_dst.set( pos_x, pos_y, pos_x + s_width, pos_y + s_height );
		
		LightingColorFilter filter1 = new LightingColorFilter(Color.rgb(255, 0, 0), 0);
		Paint p = new Paint();
		p.setColorFilter( filter1 );
		p.setARGB( 255, 0, 0, 0 );		
		c.drawBitmap( bmp, s_src, s_dst, p );			
	}
	
	public void DrawHandTouch( Canvas c, Bitmap bmp, int s_x, int s_y, int s_width, int s_height, int pos_x, int pos_y, int cnt ){
		Rect s_src = new Rect();
		Rect s_dst = new Rect();
		if( cnt < 70 ){
			s_src.set( s_x, s_y, s_x + s_width, s_y + s_height );
		}else if( cnt < 80 ){
			s_src.set( s_x + s_width, s_y, s_x + s_width * 2, s_y + s_height );
		}else{
			s_src.set( s_x, s_y, s_x + s_width, s_y + s_height );
		}
		s_dst.set( pos_x, pos_y, pos_x + s_width, pos_y + s_height );
		c.drawBitmap( bmp, s_src, s_dst, null );			
	}

	public void DrawHandFling( Canvas c, Bitmap bmp, int s_x, int s_y, int s_width, int s_height, int pos_x, int pos_y, int cnt ){
		Rect s_src = new Rect();
		Rect s_dst = new Rect();
		if( cnt < 70 ){
			s_src.set( s_x, s_y, s_x + s_width, s_y + s_height );
		}else if( cnt < 80 ){
			s_src.set( s_x + s_width, s_y, s_x + s_width + 61, s_y + s_height );
		}else{
			s_src.set( s_x + s_width + 61, s_y, s_x + s_width + 61 + 46, s_y + s_height );
		}
		if( cnt < 70 ){
			s_dst.set( pos_x, pos_y, pos_x + s_width, pos_y + s_height );
		}else if( cnt < 80 ){
			s_dst.set( pos_x, pos_y, pos_x + 61, pos_y + s_height );
		}else{
			s_dst.set( pos_x, pos_y, pos_x + 49, pos_y + s_height );			
		}
		c.drawBitmap( bmp, s_src, s_dst, null );
	}	
	
	public void DrawHandTouchResult( Canvas c, Bitmap bmp, int s_x, int s_y, int s_width, int s_height, int pos_x, int pos_y ){
		move_cnt += 1;
		if( move_cnt >= 40 ){
			move_cnt = 0;
		}
		Rect s_src = new Rect();
		Rect s_dst = new Rect();
		if( move_cnt < 20 ){
			s_src.set( s_x, s_y, s_x + s_width, s_y + s_height );
		}else{
			s_src.set( s_x + s_width, s_y, s_x + s_width * 2, s_y + s_height );
		}
		s_dst.set( pos_x, pos_y, pos_x + s_width, pos_y + s_height );
		c.drawBitmap( bmp, s_src, s_dst, null );			
	}
	
	//「ジャスト」のフラグの状態を返す
	public static boolean isJust_mark_flag() {
		return just_mark_flag;
	}

	//「ジャスト」のフラグの状態を設定する
	public static void setJust_mark_flag(boolean just_mark_flag) {
		UI.just_mark_flag = just_mark_flag;
	}

	//「コンボ」のフラグの状態を返す
	public static boolean isCombo_mark_flag() {
		return combo_mark_flag;
	}

	//「コンボ」のフラグの状態を設定する
	public static void setCombo_mark_flag(boolean combo_mark_flag) {
		UI.combo_mark_flag = combo_mark_flag;
	}

	//フィーバーゲージの状態を返す
	public static float getFever_x() {
		return fever_x;
	}

	//スタミナゲージの状態を返す
	public static float getStamina_x() {
		return stamina_x;
	}

	//フィーバーゲージの状態を設定する
	public static void setFever_x( float set_fever_x ) {
		fever_x = set_fever_x;
	}
	
	//ジャストヒットカウントの状態を設定
	public static void setJust_hit_cnt( float set_just_hit_cnt ) {
		just_hit_cnt = set_just_hit_cnt;
	}
	
	public static boolean isHit_up_flag() {
		return hit_up_flag;
	}
	
	public static void setHit_up_cnt( float set_hit_up_cnt ) {
		hit_up_cnt = set_hit_up_cnt;
	}	

	public static void setHit_up_flag( boolean hit_up_flag ){
		UI.hit_up_flag = hit_up_flag;
	}
	
}
