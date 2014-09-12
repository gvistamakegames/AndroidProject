package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Title {

	private final static float ALPHA_SPEED = 0.2f;
	private final static float MIN_ALPHA = 0.0f;
	private final static float MAX_ALPHA = 220.0f;
//	private final static float ORIGINAL_MAX_ALPHA = 255.0f;
	
	Rect src_title, dst_title;
	static int stagetitle_cnt;
	static float alpha, speed;
	static boolean stage_state;
	static boolean stop_stage;
	static boolean draw_ready;
	static boolean draw_result;
	static int draw_cnt;
	

	//コンストラクタで初期化
	public Title( int BmpWidth, int BmpHeight, int DispWidth, int DispHeight ){
		src_title = new Rect( 0, 0, BmpWidth, BmpHeight );
		dst_title = new Rect( 0, 0, DispWidth, DispHeight );
		alpha = MAX_ALPHA;
		stage_state = false;
		stop_stage = true;
		draw_ready = true;
		draw_result = false;
	}
	
	public void Init(){
		stage_state = false;
		draw_cnt = 0;
	}

	public void DrawTitle( Canvas c, Bitmap bmp ){
		c.drawBitmap( bmp, src_title, dst_title, null );
	}

	public boolean StageTitleState(){
		if( alpha == MIN_ALPHA ){
			stage_state = false;
			return true;
		}
		if( alpha == MAX_ALPHA ){
			stage_state = true;
			return true;
		}
		return false;
	}
	
	public boolean StageTitleCount(){
		if( stage_state == true ){
			speed += ALPHA_SPEED;
			alpha -= speed;
			if( alpha <= MIN_ALPHA ){
				alpha = MIN_ALPHA;
				speed = 0.0f;
				//このタイミングでリザルトの表示フラグをオンにする
				draw_result = true;
				return true;
			}
		}
		if( stage_state == false ){
			speed += ALPHA_SPEED * 5.0f;
			alpha += speed;
			if( alpha >= MAX_ALPHA ){
				alpha = MAX_ALPHA;
				speed = 0.0f;
				stop_stage = true;
			}
		}
		return false;
	}
	
	public boolean CheckStopStage(){
		if( stop_stage == true ){
			return true;
		}
		return false;
	}
	
	public void DrawStageMask( Canvas c, Bitmap bmp, int STAGE_LEVEL ){
		Paint p = new Paint();
		p.setARGB( (int)alpha, 255, 0, 0 );		
		c.drawBitmap( bmp, src_title, dst_title, p );
	}

	public void DrawStageTitle( Canvas c, int STAGE_LEVEL, int SCORE ){
		draw_cnt += 1;
		Paint p = new Paint();
		p.setAntiAlias( true );
		p.setColor( Color.WHITE );
		p.setTextSize( 60 );
		//ステージ開始前の表示
		if( draw_ready == true ){
			if( draw_cnt >= 0 && draw_cnt < 70 ){
				c.drawText( "お食事タイム " + STAGE_LEVEL + " 回目", 140, 192, p );
			}
			if( draw_cnt >= 35 && draw_cnt < 100 ){
				c.drawText( "準備はイイですか?", 150, 252, p );
			}
			p.setTextSize( 90 );
			if( draw_cnt >= 110 ){
				c.drawText( "いただきま～す!", 70, 312, p );
			}
			if( draw_cnt >= 160 ){
				stop_stage = false;
				stage_state = true;	
				draw_ready = false;
				draw_cnt = 0;
			}
		}
		//ステージ終了後の表示
		if( draw_result == true ){
			p.setTextSize( 80 );
			if( draw_cnt < 50 ){
				c.drawText( "ごちそうさまでした!", 30, 312, p );
			}
			p.setTextSize( 40 );			
			if( draw_cnt >= 75 && draw_cnt < 120){
				c.drawText( "これまでのスコア : " + SCORE, 100, 222, p );
			}			
			if( draw_cnt >= 160 ){
				draw_ready = true;
				draw_result = false;
				draw_cnt = 0;				
			}
		}
	}

}
