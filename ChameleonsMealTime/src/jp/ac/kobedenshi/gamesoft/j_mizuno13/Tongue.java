package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Tongue {
	
	//各変数を宣言(舌の拡大率XY、舌の描画位置XY、舌の描画角度)
	protected float tongue_scale_x, tongue_scale_y = 1.0f;
	protected float TONGUE_POSITION_X, TONGUE_POSITION_Y, Angle;
	//舌の状態を表すフラグ変数を宣言(準備状態と出ている状態)
	public boolean ready_tongue_flag = false;
	public boolean go_tongue_flag = false;
	public boolean appeared_tongue_flag = false;
    //double Ang = Math.atan2(20, 20);
	private final static float TONGUE_MAXANGLE_OF_TOP = -35.0f;
	private final static float TONGUE_MAXANGLE_OF_BOTTOM = 20.0f;
	private final static float TONGUE_SCALELEVEL_FIRST = 0.2f;
	private final static float TONGUE_SCALELEVEL_SECOND = 0.4f;
	private final static float TONGUE_SCALELEVEL_THIRD = 0.6f;
	private final static float TONGUE_SCALELEVEL_FOURTH = 0.8f;
	private final static float TONGUE_SCALELEVEL_FIFTH = 1.0f;	
	private final static float TONGUE_FRONT_SPEED = 0.1f;
	private final static float TONGUE_BACK_SPEED = 0.1f;
	private final static float TONGUE_MINIMUM_SCALE = 0.0f;
	
	private float tongue_speed_level = 0.75f;
	
	//コンストラクタで初期化
	public Tongue( float TONGUE_POSITION_X, float TONGUE_POSITION_Y  ){
		this.TONGUE_POSITION_X = TONGUE_POSITION_X;
		this.TONGUE_POSITION_Y = TONGUE_POSITION_Y;
 	}


	public float getTongueScaleX(){
		return tongue_scale_x;
	}
	
	
	public void AppearedTongueFlagChange( int Appeared ){
		//渡された値が１なら真、それ以外なら偽と定義する
		if( Appeared == 1 ){
			this.appeared_tongue_flag = true;
		}else{
			this.appeared_tongue_flag = false;
		}
	}	
	
	public void GoTongueFlagChange( int go ){
		//渡された値が１なら真、それ以外なら偽と定義する
		if( go == 1 ){
			this.go_tongue_flag = true;
		}else{
			this.go_tongue_flag = false;
		}
	}

	public void ReadyTongueFlagChange( int ready ){
		//渡された値が１なら真、それ以外なら偽と定義する
		if( ready == 1 ){
			this.ready_tongue_flag = true;
		}else{
			this.ready_tongue_flag = false;
		}
	}
	
	public void AngleCheck( float up_position_x, float up_position_y, float down_position_x, float down_position_y ){
		//フリック時、ダウンの座標とアップの座標を取り、それぞれの位置から角度を求める
		this.Angle = (int)Math.toDegrees( Math.atan2( ( up_position_y - down_position_y ), ( up_position_x - down_position_x ) ) );
		//角度の上限と下限を設定
		if( this.Angle < TONGUE_MAXANGLE_OF_TOP ){
			this.Angle = TONGUE_MAXANGLE_OF_TOP;
		}else if( Angle > TONGUE_MAXANGLE_OF_BOTTOM ){
			this.Angle = TONGUE_MAXANGLE_OF_BOTTOM;
		}		
	}
	
	public void TongueScaleChange( int select ){
		if( this.go_tongue_flag == true ){
			
			Charactor.eatting_cnt = 0;
			Charactor.eatting_now_flag = true;
			Charactor.open_mouse_flag = true;
			Charactor.waitting_flag = false;
			Charactor.eye_space = false;
			Enemy.eatting_flag = false;
			
			ReadyTongueFlagChange( 1 );
			tongue_scale_x += TONGUE_FRONT_SPEED * tongue_speed_level;			
			switch( select ){
			case 0:
				if( tongue_scale_x >= TONGUE_SCALELEVEL_FIRST ){
					tongue_scale_x = TONGUE_SCALELEVEL_FIRST;
					GoTongueFlagChange( 0 );
				}				
				break;
			case 1:
				if( tongue_scale_x >= TONGUE_SCALELEVEL_SECOND ){
					tongue_scale_x = TONGUE_SCALELEVEL_SECOND;
					GoTongueFlagChange( 0 );
				}				
				break;
			case 2:
				if( tongue_scale_x >= TONGUE_SCALELEVEL_THIRD ){
					tongue_scale_x = TONGUE_SCALELEVEL_THIRD;
					GoTongueFlagChange( 0 );
				}				
				break;
			case 3:
				if( tongue_scale_x >= TONGUE_SCALELEVEL_FOURTH ){
					tongue_scale_x = TONGUE_SCALELEVEL_FOURTH;
					GoTongueFlagChange( 0 );
				}				
				break;
			case 4:
				if( tongue_scale_x >= TONGUE_SCALELEVEL_FIFTH ){
					tongue_scale_x = TONGUE_SCALELEVEL_FIFTH;
					GoTongueFlagChange( 0 );
				}				
				break;				
			default:
				if( tongue_scale_x >= TONGUE_SCALELEVEL_FIRST ){
					tongue_scale_x = TONGUE_SCALELEVEL_FIRST;
					GoTongueFlagChange( 0 );
				}
				break;				
			}			
		}else{
			//※以下の処理は、舌が出ていない時、常に処理されている感じ
			tongue_scale_x -= TONGUE_BACK_SPEED * tongue_speed_level;
			Charactor.open_mouse_flag = false;
			if( tongue_scale_x < TONGUE_MINIMUM_SCALE ){
				tongue_scale_x = TONGUE_MINIMUM_SCALE;
				ReadyTongueFlagChange( 0 );
				AppearedTongueFlagChange( 0 );
				this.Angle = 0.0f;
			}			
		}		
	}
	
	
	public void DrawTongue( Canvas c, Bitmap bmp ){
		//※マトリックスクラスをここでインスタンス化
		Matrix tongue_matrix = new Matrix();
	    //※以下、マトリックスの各値の記述順序に注意する事
		tongue_matrix.postScale( tongue_scale_x, this.tongue_scale_y );
        tongue_matrix.postRotate( this.Angle );
		tongue_matrix.postTranslate( this.TONGUE_POSITION_X, this.TONGUE_POSITION_Y );
		//舌描画
		c.drawBitmap( bmp, tongue_matrix, null );
	}
		
}
