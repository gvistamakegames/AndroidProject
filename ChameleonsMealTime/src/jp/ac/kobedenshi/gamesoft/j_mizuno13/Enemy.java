package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Enemy {

	//敵の動きのパターン数を設定、それによりswitch文の内容も変更する事(ランダムをとるので、実際のパターンの数は「設定数-1」の値になる)
	private final static int BASE_PATTERN = 12;
	//敵の動きのパターン数を設定、それによりswitch文の内容も変更する事(ランダムをとるので、実際のパターンの数は「設定数-1」の値になる)
	private final static int MOVE_PATTERN = 10;
	//敵のスピード調整用変数(値を上げるほど、遅くなる)※最低でも3以上の設定で・・・
	private final static int ENEMY_SPEED = 3;
	
	EnemyPosition[] PosData;
	Matrix[] EnemyMatrix;
	static boolean[] enemy_cought_state;
	static boolean[] enemy_iseat_state;
	static boolean[] score_state;
	
	static int total_ate_enemy_cnt;
	private int stage_ate_enemy_cnt;
	private int combo_cnt;
	private static boolean combo_cnt_switch = false;
	
	private int score_up_cnt;
	private int enemy_anime_cnt;
	private boolean anime_vec;
	
    static int change_pattern_cnt;
	
	static float MoveAngle;
	static float ShakeAngle;
	public float ENEMY_POSITION_X, ENEMY_POSITION_Y;
	
	public float hit_mark_x, hit_mark_y;
	
	private final static int CHARS = 3;
	
	public static boolean eatting_flag = false;
	
	private static boolean just_hit_on;
	private static float just_hit_position_x;
	private static float just_hit_position_y;
	
	private float getout_up_speed;
	private float getout_down_speed;
	
	private float alpha_angle;
	
	
		
	//コンストラクタで初期化
	public Enemy( float ENEMY_POSITION_X, float ENEMY_POSITION_Y, int ENEMY_LEVEL ){
		this.ENEMY_POSITION_X = ENEMY_POSITION_X;
		this.ENEMY_POSITION_Y = ENEMY_POSITION_Y;
		total_ate_enemy_cnt = 0;
		setStage_ate_enemy_cnt(0);
		score_up_cnt = 0;
		setCombo_cnt(0);
		enemy_anime_cnt = 0;
		anime_vec = true;
		setJust_hit_on(false);
		getout_up_speed = 0.0f;
		getout_down_speed = 0.0f;
	}
	
	public void Init( int ENEMY_LEVEL ){
		PosData = new EnemyPosition[ENEMY_LEVEL];
		EnemyMatrix = new Matrix[ENEMY_LEVEL];
		enemy_cought_state = new boolean[ENEMY_LEVEL];
		enemy_iseat_state = new boolean[ENEMY_LEVEL];
		score_state = new boolean[ENEMY_LEVEL];
		
		for( int i = 0; i < ENEMY_LEVEL; i++ ){
			//各敵の位置情報をインスタンス化
			PosData[i] = new EnemyPosition( 10.0f, 10.0f );
			enemy_cought_state[i] = false;
			enemy_iseat_state[i] = false;
			score_state[i] = false;
		}
		
		getout_up_speed = 0.0f;
		getout_down_speed = 0.0f;
		
		setStage_ate_enemy_cnt(0);
		
		alpha_angle = 0.0f;
						
	}
		
	public void Move( float TONGUE_POSITION_X, float TONGUE_POSITION_Y, float tongue_scale_x, float tongue_scale_y, float Angle,
			float dx, float dy, int ENEMY_LEVEL, boolean fever, boolean game_finish ){
		float total_e_position_x, total_e_position_y;

		Random rnd2 = new Random();

		//アングルを0～360の間でループで回す
		MoveAngle += 0.2f;
		if( MoveAngle >= 360.0f ){
			MoveAngle = 0.0f;
		}
		ShakeAngle += 0.6f;
		if( ShakeAngle >= 360.0f ){
			ShakeAngle = 0.0f;
		}
		
		//ゲームが進行中の時
		if( game_finish == false ){
			//パターン変更の間隔を設定するためのカウンタを回す
			change_pattern_cnt += 1;
			//パターンカウンタが設定数を上回ったら、各敵のパターンを再度ランダムで変更する
			if( change_pattern_cnt % 20 == 0 ){
				Random rndPat = new Random();
				for( int i = 0; i < ENEMY_LEVEL; i++ ){
					if( PosData[i].enemy_base_pattern == 5 || PosData[i].enemy_base_pattern == 11 ){
						PosData[i].enemy_move_pattern = rndPat.nextInt( MOVE_PATTERN );
					}
				}
			}
			if( change_pattern_cnt % 40 == 0 ){
				Random rndPat = new Random();
				for( int i = 0; i < ENEMY_LEVEL; i++ ){
					PosData[i].enemy_move_pattern = rndPat.nextInt( MOVE_PATTERN );			
					//パターンカウンタをゼロクリア
					change_pattern_cnt = 0;
				}
			}
		}
		
		for( int i = 0; i < ENEMY_LEVEL; i++ ){
			//※マトリックスクラスはここでインスタンス化することで、正常に表示がされた
		 	EnemyMatrix[i] = new Matrix();
			if( enemy_cought_state[i] == false && enemy_iseat_state[i] == false ){
				switch( PosData[i].enemy_base_pattern ){
				//通常の
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					switch( PosData[i].enemy_move_pattern ){
					case 0:
						PosData[i].e_move_y += (float)( ( rnd2.nextInt( 3 ) * 5.0f ) ) / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 1:
						PosData[i].e_move_x += ( (float)( Math.sin( (double) MoveAngle ) * 60.0f ) ) / ENEMY_SPEED;    //X軸のサイン波のアルゴリズム				
						PosData[i].e_move_y += 10.0f / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム	
						break;
					case 2:					
						PosData[i].e_move_x += ( (float)( Math.sin( (double) MoveAngle ) * 30.0f ) ) / ENEMY_SPEED;    //X軸のサイン波のアルゴリズム
						PosData[i].e_move_y += ( (float)( rnd2.nextInt( 3 ) * 10.0f ) ) / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 3:					
						PosData[i].e_move_x += ( (float)( Math.sin( (double) MoveAngle ) * 40.0f ) ) / ENEMY_SPEED;			
						PosData[i].e_move_y += ( (float)( Math.sin( (double) MoveAngle ) * 8.0f ) ) / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 4:
						PosData[i].e_move_x -= ( (float)( Math.sin( (double) MoveAngle ) * 40.0f ) ) / ENEMY_SPEED;
						PosData[i].e_move_y += ( (float)( Math.sin( (double) MoveAngle ) * 8.0f ) ) / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 5:
						PosData[i].e_move_y += ( (float)( Math.sin( (double) MoveAngle ) * 20.0f ) ) / ENEMY_SPEED;    //Y軸のサイン波のアルゴリズム				
						PosData[i].e_move_y += ( (float)( rnd2.nextInt( 3 ) * 6.0f ) ) / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム
						break;				
					case 6:
						PosData[i].e_move_x += 5.0f / ENEMY_SPEED;						
						PosData[i].e_move_y += 5.0f / ENEMY_SPEED;
						break;
					case 7:
						PosData[i].e_move_x -= 5.0f / ENEMY_SPEED;						
						PosData[i].e_move_y += 5.0f / ENEMY_SPEED;
						break;
					case 8:
						PosData[i].e_move_x += 2.0f / ENEMY_SPEED;						
						PosData[i].e_move_y += 2.0f / ENEMY_SPEED;
						break;
					case 9:
						PosData[i].e_move_x -= 2.0f / ENEMY_SPEED;						
						PosData[i].e_move_y += 2.0f / ENEMY_SPEED;
						break;						
					}
					break;

				//赤いヤツ
				case 5:					
					switch( PosData[i].enemy_move_pattern ){
					case 0:
						PosData[i].e_move_y += ( (float)( ( rnd2.nextInt( 3 ) * 5.0f ) ) / ( ENEMY_SPEED - 2 ) );    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 1:
						PosData[i].e_move_x += ( ( (float)( Math.sin( (double) MoveAngle ) * 60.0f ) ) / ( ENEMY_SPEED - 2 ) );    //X軸のサイン波のアルゴリズム				
						PosData[i].e_move_y += 10.0f / ( ENEMY_SPEED - 2 );    //Y軸＋方向へランダムに進むアルゴリズム	
						break;
					case 2:					
						PosData[i].e_move_x += ( ( (float)( Math.sin( (double) MoveAngle ) * 30.0f ) ) / ( ENEMY_SPEED - 2 ) );    //X軸のサイン波のアルゴリズム
						PosData[i].e_move_y += ( (float)( rnd2.nextInt( 3 ) * 10.0f ) ) / ( ENEMY_SPEED - 2 );    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 3:					
						PosData[i].e_move_x += ( ( (float)( Math.sin( (double) MoveAngle ) * 60.0f ) ) / ( ENEMY_SPEED - 2 ) );			
						PosData[i].e_move_y += ( ( (float)( Math.sin( (double) MoveAngle ) * 10.0f ) ) / ( ENEMY_SPEED - 2 ) );    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 4:
						PosData[i].e_move_x -= ( ( (float)( Math.sin( (double) MoveAngle ) * 60.0f ) ) / ( ENEMY_SPEED - 2 ) );
						PosData[i].e_move_y += ( ( (float)( Math.sin( (double) MoveAngle ) * 10.0f ) ) / ( ENEMY_SPEED - 2 ) );    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 5:
						PosData[i].e_move_y += ( ( (float)( Math.sin( (double) MoveAngle ) * 20.0f ) ) / ( ENEMY_SPEED - 2 ) );    //Y軸のサイン波のアルゴリズム				
						PosData[i].e_move_y += ( (float)( rnd2.nextInt( 3 ) * 6.0f ) ) / ( ENEMY_SPEED - 2 );    //Y軸＋方向へランダムに進むアルゴリズム
						break;				
					case 6:
						PosData[i].e_move_x += 10.0f / ( ENEMY_SPEED - 2 );						
						PosData[i].e_move_y += 10.0f / ( ENEMY_SPEED - 2 );
						break;
					case 7:
						PosData[i].e_move_x -= 10.0f / ( ENEMY_SPEED - 2 );						
						PosData[i].e_move_y += 10.0f / ( ENEMY_SPEED - 2 );
						break;
					case 8:
						PosData[i].e_move_x += 2.0f / ( ENEMY_SPEED - 2 );						
						PosData[i].e_move_y += 2.0f / ( ENEMY_SPEED - 2 );
						break;
					case 9:
						PosData[i].e_move_x -= 2.0f / ( ENEMY_SPEED - 2 );						
						PosData[i].e_move_y += 2.0f / ( ENEMY_SPEED - 2 );
						break;						
					}
					break;
					
				//通常の
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
					switch( PosData[i].enemy_move_pattern ){
					case 0:
						PosData[i].e_move_y -= (float)( ( rnd2.nextInt( 3 ) * 5.0f ) ) / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 1:
						PosData[i].e_move_x += ( (float)( Math.sin( (double) MoveAngle ) * 60.0f ) ) / ENEMY_SPEED;    //X軸のサイン波のアルゴリズム				
						PosData[i].e_move_y -= 10.0f / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム		
						break;
					case 2:					
						PosData[i].e_move_x += ( (float)( Math.sin( (double) MoveAngle ) * 30.0f ) ) / ENEMY_SPEED;    //X軸のサイン波のアルゴリズム
						PosData[i].e_move_y -= ( (float)( rnd2.nextInt( 3 ) * 10.0f ) ) / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 3:					
						PosData[i].e_move_x += ( (float)( Math.sin( (double) MoveAngle ) * 60.0f ) ) / ENEMY_SPEED;				
						PosData[i].e_move_y -= ( (float)( Math.sin( (double) MoveAngle ) * 10.0f ) ) / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム	
						break;
					case 4:
						PosData[i].e_move_x -= ( (float)( Math.sin( (double) MoveAngle ) * 60.0f ) ) / ENEMY_SPEED;		
						PosData[i].e_move_y -= ( (float)( Math.sin( (double) MoveAngle ) * 10.0f ) ) / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム		
						break;
					case 5:
						PosData[i].e_move_y -= ( (float)( Math.sin( (double) MoveAngle ) * 20.0f ) ) / ENEMY_SPEED;    //Y軸のサイン波のアルゴリズム				
						PosData[i].e_move_y -= ( (float)( rnd2.nextInt( 3 ) * 6.0f ) ) / ENEMY_SPEED;    //Y軸＋方向へランダムに進むアルゴリズム
						break;				
					case 6:
						PosData[i].e_move_x += 5.0f / ENEMY_SPEED;						
						PosData[i].e_move_y -= 5.0f / ENEMY_SPEED;
						break;
					case 7:
						PosData[i].e_move_x -= 5.0f / ENEMY_SPEED;						
						PosData[i].e_move_y -= 5.0f / ENEMY_SPEED;
						break;	
					case 8:
						PosData[i].e_move_x += 2.0f / ENEMY_SPEED;						
						PosData[i].e_move_y -= 2.0f / ENEMY_SPEED;
						break;
					case 9:
						PosData[i].e_move_x -= 2.0f / ENEMY_SPEED;						
						PosData[i].e_move_y -= 2.0f / ENEMY_SPEED;
						break;							
					}
					break;
					
				//赤いヤツ
				case 11:					
					switch( PosData[i].enemy_move_pattern ){
					case 0:
						PosData[i].e_move_y -= (float)( ( rnd2.nextInt( 3 ) * 5.0f ) ) / ( ENEMY_SPEED - 2 );    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 1:
						PosData[i].e_move_x += ( ( (float)( Math.sin( (double) MoveAngle ) * 60.0f ) ) / ( ENEMY_SPEED - 2 ) );    //X軸のサイン波のアルゴリズム				
						PosData[i].e_move_y -= 10.0f / ENEMY_SPEED * CHARS;    //Y軸＋方向へランダムに進むアルゴリズム		
						break;
					case 2:					
						PosData[i].e_move_x += ( ( (float)( Math.sin( (double) MoveAngle ) * 30.0f ) ) / ( ENEMY_SPEED - 2 ) );    //X軸のサイン波のアルゴリズム
						PosData[i].e_move_y -= ( (float)( rnd2.nextInt( 3 ) * 10.0f ) ) / ENEMY_SPEED * CHARS;    //Y軸＋方向へランダムに進むアルゴリズム
						break;
					case 3:					
						PosData[i].e_move_x += ( ( (float)( Math.sin( (double) MoveAngle ) * 60.0f ) ) / ( ENEMY_SPEED - 2 ) );				
						PosData[i].e_move_y -= ( ( (float)( Math.sin( (double) MoveAngle ) * 10.0f ) ) / ( ENEMY_SPEED - 2 ) );    //Y軸＋方向へランダムに進むアルゴリズム	
						break;
					case 4:
						PosData[i].e_move_x -= ( ( (float)( Math.sin( (double) MoveAngle ) * 60.0f ) ) / ( ENEMY_SPEED - 2 ) );		
						PosData[i].e_move_y -= ( ( (float)( Math.sin( (double) MoveAngle ) * 10.0f ) ) / ( ENEMY_SPEED - 2 ) );    //Y軸＋方向へランダムに進むアルゴリズム		
						break;
					case 5:
						PosData[i].e_move_y -= ( ( (float)( Math.sin( (double) MoveAngle ) * 20.0f ) ) / ( ENEMY_SPEED - 2 ) );    //Y軸のサイン波のアルゴリズム				
						PosData[i].e_move_y -= ( (float)( rnd2.nextInt( 3 ) * 6.0f ) ) / ( ENEMY_SPEED - 2 );    //Y軸＋方向へランダムに進むアルゴリズム
						break;				
					case 6:
						PosData[i].e_move_x += 10.0f / ( ENEMY_SPEED - 2 );						
						PosData[i].e_move_y -= 10.0f / ( ENEMY_SPEED - 2 );
						break;
					case 7:
						PosData[i].e_move_x -= 10.0f / ( ENEMY_SPEED - 2 );						
						PosData[i].e_move_y -= 10.0f / ( ENEMY_SPEED - 2 );
						break;	
					case 8:
						PosData[i].e_move_x += 5.0f / ( ENEMY_SPEED - 2 );						
						PosData[i].e_move_y -= 5.0f / ( ENEMY_SPEED - 2 );
						break;
					case 9:
						PosData[i].e_move_x -= 5.0f / ( ENEMY_SPEED - 2 );						
						PosData[i].e_move_y -= 5.0f / ( ENEMY_SPEED - 2 );
						break;							
					}
					break;
				}
				//最終的に敵を表示する座標を、マトリックスで使用する変数に代入する
				//※( トータル座標 ) = ( 基本となる座標 ) + ( 移動量 )
				total_e_position_x = PosData[i].e_position_x + PosData[i].e_move_x;
				total_e_position_y = PosData[i].e_position_y + PosData[i].e_move_y;
//				Random rndPat = new Random();
				if( total_e_position_x < 440.0f ){
					PosData[i].enemy_move_pattern = 6;
				}
				if( total_e_position_x > 770.0f ){
					PosData[i].enemy_move_pattern = 7;
				}
				
				if( game_finish == false && fever == false ){
					//画面外に消えたら、出現フラグを下します
					if( total_e_position_y > 560.0f ){
						PosData[i].e_move_y = 0.0f;
						PosData[i].e_position_y = -40.0f;
						PosData[i].enemy_base_pattern = -1;
						PosData[i].enemy_move_pattern = -1;
						PosData[i].enemy_appear_flag = false;
					}
					//画面外に消えたら、出現フラグを下します
					if( total_e_position_y < -80.0f ){
						PosData[i].e_move_y = 0.0f;
						PosData[i].e_position_y = -40.0f;
						PosData[i].enemy_base_pattern = -1;
						PosData[i].enemy_move_pattern = -1;
						PosData[i].enemy_appear_flag = false;
					}
				}
				EnemyMatrix[i].postTranslate( total_e_position_x, total_e_position_y );
								
			}else if( enemy_cought_state[i] == true ){
				total_e_position_x = ( ( 605.0f * tongue_scale_x ) + TONGUE_POSITION_X + dx - 20.0f );
				total_e_position_y = ( ( ( 605.0f * tongue_scale_x ) * (float)Math.sin( Math.toRadians( (double)Angle ) ) + TONGUE_POSITION_Y ) + dy - 19.5f );
				EnemyMatrix[i].postScale( 1.0f, 1.0f );
				EnemyMatrix[i].postTranslate( total_e_position_x, total_e_position_y );				
				if( total_e_position_x < 230.0f ){
					enemy_cought_state[i] = false;
					enemy_iseat_state[i] = true;
					if( total_e_position_y > 200.0f ){
						eatting_flag = true;
						
						//基本のスコアアップ(固定値)
						score_up_cnt +=100;
											
						//コンボ用のカウントを、敵を捕獲した分だけ上げる
						setStage_ate_enemy_cnt( getStage_ate_enemy_cnt() + 1 );
						if( getStage_ate_enemy_cnt() >= 2 ){
							UI.setCombo_mark_flag( true );
							UI.stamina_up_more = true;
							setCombo_cnt_switch( true );
							setCombo_cnt( getStage_ate_enemy_cnt() );
							//コンボボーナスのスコアアップ(設定の得点×その時捕食した敵の数)
							score_up_cnt += 150 * getStage_ate_enemy_cnt();
							UI.setHit_up_flag( true );
						}else{
							UI.stamina_up = true;							
						}
						
						if( PosData[i].enemy_base_pattern == 5 || PosData[i].enemy_base_pattern == 11 ){
							UI.stamina_x += 0.07f;
							score_up_cnt += 1500; 
						}
						
						if( isJust_hit_on() == true ){
							UI.setJust_mark_flag( true );
							UI.stamina_up_more = true;
							setJust_hit_on( false );
							UI.setJust_hit_cnt( 0.0f );
							UI.setHit_up_flag( true );
							//ジャストヒットボーナスのスコアアップ(固定値)
							score_up_cnt += 1000;							
						}

						PosData[i].e_move_x = 0.0f;
						PosData[i].e_position_x = 0.0f;
						PosData[i].e_move_y = 0.0f;
						PosData[i].e_position_y = 0.0f;
						PosData[i].enemy_appear_flag = false;
						
												
					}
				}
			}
		}
		
		setStage_ate_enemy_cnt(0);
		
		//スコア加算(一旦ここに記述します)
		if( GameDraw.SCORE < score_up_cnt ){
			GameDraw.SCORE += 50;
		}
		
	}
	
	//仮想で舌の先端と敵の胴体に円を設置、それぞれで当たり判定を行う
	public void  CoughtFlag( float x1, float y1, float r1, float r2, int ENEMY_LEVEL, int select, float tongue_scale_x ){  //舌の各情報と、敵の各情報を受け取る
		float x2, y2, a, b, c;
		float r_length = r1 + r2;
		for( int i = 0; i < ENEMY_LEVEL; i++ ){
			if( enemy_cought_state[i] == false ){
				x2 = PosData[i].e_position_x + PosData[i].e_move_x + r2;
				y2 = PosData[i].e_position_y + PosData[i].e_move_y + r2;
				a = y1 - y2;
				b = x1 - x2;
				c = (float)Math.sqrt( ( a*a ) + ( b*b ) );
				if( c <= r_length ){
					enemy_cought_state[i] = true;
					switch( select ){
					case 0:
						if( tongue_scale_x >= 0.1f ){
							setJust_hit_on(true);
							just_hit_position_x = PosData[i].e_position_x + PosData[i].e_move_x;
							just_hit_position_y = PosData[i].e_position_y + PosData[i].e_move_y;
						}
						break;
					case 1:
						if( tongue_scale_x >= 0.3f ){
							setJust_hit_on(true);
							just_hit_position_x = PosData[i].e_position_x + PosData[i].e_move_x;
							just_hit_position_y = PosData[i].e_position_y + PosData[i].e_move_y;
						}
						break;
					case 2:
						if( tongue_scale_x >= 0.5f ){
							setJust_hit_on(true);
							just_hit_position_x = PosData[i].e_position_x + PosData[i].e_move_x;
							just_hit_position_y = PosData[i].e_position_y + PosData[i].e_move_y;
						}
						break;
					case 3:
						if( tongue_scale_x >= 0.7f ){
							setJust_hit_on(true);
							just_hit_position_x = PosData[i].e_position_x + PosData[i].e_move_x;
							just_hit_position_y = PosData[i].e_position_y + PosData[i].e_move_y;
						}
						break;
					case 4:
						if( tongue_scale_x >= 0.9f ){
							setJust_hit_on(true);
							just_hit_position_x = PosData[i].e_position_x + PosData[i].e_move_x;
							just_hit_position_y = PosData[i].e_position_y + PosData[i].e_move_y;
						}
						break;
					default:
						break;
					}
				}
			}
		}
	}
	
	//出現している全ての敵が画面外に出たかどうかのチェック
	public boolean IsEnmeyAppearFlag( int ENEMY_LEVEL, int ENEMY_MIN_POINT ){
		int j = 0;
		for( int i = 0; i < ENEMY_LEVEL; i++ ){
			if( PosData[i].enemy_appear_flag == false ){
				j = j + 1;
			}
			if( j > ENEMY_MIN_POINT ){
				return true;
			}
		}
		return false;
	}
	
	//
	public void ToEnemyAppearFlag( int ENEMY_LEVEL ){
		for( int i = 0; i < ENEMY_LEVEL; i++ ){
			if( PosData[i].enemy_appear_flag == false ){
				PosData[i] = new EnemyPosition( 10.0f, 10.0f );
				enemy_cought_state[i] = false;
				enemy_iseat_state[i] = false;
				score_state[i] = false;				
				break;
			}
		}
	}	
	
	//出現している全ての敵が食べられたかどうかのチェック
	public boolean IsAteFlag( int ENEMY_LEVEL ){
		int j = 0;
		for( int i = 0; i < ENEMY_LEVEL; i++ ){
			if( enemy_cought_state[i] == false && enemy_iseat_state[i] == true ){
				j = j + 1;
			}
			if( j == ENEMY_LEVEL ){
				return true;
			}
		}
		return false;
	}
	
	//敵を画面外に追いやる処理(イートチャンス終了後に使用します)
	public boolean EnemyGetOut( int ENEMY_LEVEL ){
		int j = 0;
		int k = 0;
		int up_enemy = 0;
		int down_enemy = 0;
		getout_up_speed += 2.0f;
		getout_down_speed += 2.0f;
		for( int i = 0; i < ENEMY_LEVEL; i++ ){
			if( PosData[i].enemy_base_pattern == 0 || PosData[i].enemy_base_pattern == 1 || PosData[i].enemy_base_pattern == 2 
					|| PosData[i].enemy_base_pattern == 3 || PosData[i].enemy_base_pattern == 4 || PosData[i].enemy_base_pattern == 5 ){
				down_enemy += 1;
			}else if( PosData[i].enemy_base_pattern == 6 || PosData[i].enemy_base_pattern == 7  || PosData[i].enemy_base_pattern == 8
					|| PosData[i].enemy_base_pattern == 9 || PosData[i].enemy_base_pattern == 10  || PosData[i].enemy_base_pattern == 11 ){
				up_enemy += 1;
			}
		}
		
		for( int i = 0; i < ENEMY_LEVEL; i++ ){
			if( PosData[i].enemy_appear_flag == true  ){
				if( PosData[i].enemy_base_pattern == 0 || PosData[i].enemy_base_pattern == 1 || PosData[i].enemy_base_pattern == 2 
						|| PosData[i].enemy_base_pattern == 3 || PosData[i].enemy_base_pattern == 4 || PosData[i].enemy_base_pattern == 5 ){
					PosData[i].e_move_y += getout_up_speed;
					if( PosData[i].e_position_y + PosData[i].e_move_y > 490.0f ){
						PosData[i].enemy_appear_flag = false;
					}
				}else if( PosData[i].enemy_base_pattern == 6 || PosData[i].enemy_base_pattern == 7  || PosData[i].enemy_base_pattern == 8
						|| PosData[i].enemy_base_pattern == 9 || PosData[i].enemy_base_pattern == 10  || PosData[i].enemy_base_pattern == 11 ){
					PosData[i].e_move_y -= getout_down_speed;
					if( PosData[i].e_position_y + PosData[i].e_move_y < -10.0f ){
						PosData[i].enemy_appear_flag = false;
					}					
				}
			}else{
				if( PosData[i].enemy_base_pattern == 0 || PosData[i].enemy_base_pattern == 1 || PosData[i].enemy_base_pattern == 2 
						|| PosData[i].enemy_base_pattern == 3 || PosData[i].enemy_base_pattern == 4 || PosData[i].enemy_base_pattern == 5 ){
					j += 1;
				}else if( PosData[i].enemy_base_pattern == 6 || PosData[i].enemy_base_pattern == 7  || PosData[i].enemy_base_pattern == 8
						|| PosData[i].enemy_base_pattern == 9 || PosData[i].enemy_base_pattern == 10  || PosData[i].enemy_base_pattern == 11 ){
					k += 1;
				}
			}
			if( j == down_enemy && k == up_enemy ){
				getout_up_speed = 0.0f;
				getout_down_speed = 0.0f;
				return true;
			}
		}
		return false;
	}
	
	//敵の描画
	public void DrawEnemy( Canvas c, Bitmap bmp1, Bitmap bmp2, Bitmap bmp3, Bitmap bmp4, Bitmap bmp5, Bitmap bmp6, int ENEMY_LEVEL ){
		
		alpha_angle += 1.0f;
		if( alpha_angle >= 360.0f ){
			alpha_angle = 0.0f;
		}
		
		LightingColorFilter filter1 = new LightingColorFilter(Color.rgb( 255, 0, 0 ), 0);
		Paint p = new Paint();
		p.setColorFilter( filter1 );
		p.setARGB( (int)( 255 * ( ( Math.sin( alpha_angle ) + 2.0f ) / 2.0f ) ), 0, 0, 0 );
		
		if( anime_vec == true ){
			enemy_anime_cnt += 1;
		}else{
			enemy_anime_cnt -= 1;
		}
		if( enemy_anime_cnt >= 4 ){
			anime_vec = false;
		}
		if( enemy_anime_cnt <= 0 ){
			anime_vec = true;			
		}
		
		for( int i = 0; i < ENEMY_LEVEL; i++ ){
			if( enemy_cought_state[i] == false && enemy_iseat_state[i] == false ){

				switch( enemy_anime_cnt ){
				case 0:
					c.drawBitmap( bmp1, EnemyMatrix[i], null );
					if( PosData[i].enemy_base_pattern == 5 || PosData[i].enemy_base_pattern == 11 ){
						c.drawBitmap( bmp1, EnemyMatrix[i], p );						
					}
					break;
				case 1:
					c.drawBitmap( bmp2, EnemyMatrix[i], null );
					if( PosData[i].enemy_base_pattern == 5 || PosData[i].enemy_base_pattern == 11 ){
						c.drawBitmap( bmp2, EnemyMatrix[i], p );						
					}
					break;
				case 2:
					c.drawBitmap( bmp3, EnemyMatrix[i], null );
					if( PosData[i].enemy_base_pattern == 5 || PosData[i].enemy_base_pattern == 11 ){
						c.drawBitmap( bmp3, EnemyMatrix[i], p );						
					}
					break;
				case 3:
					c.drawBitmap( bmp4, EnemyMatrix[i], null );
					if( PosData[i].enemy_base_pattern == 5 || PosData[i].enemy_base_pattern == 11 ){
						c.drawBitmap( bmp4, EnemyMatrix[i], p );						
					}
					break;
				case 4:
					c.drawBitmap( bmp5, EnemyMatrix[i], null );
					if( PosData[i].enemy_base_pattern == 5 || PosData[i].enemy_base_pattern == 11 ){
						c.drawBitmap( bmp5, EnemyMatrix[i], p );						
					}
					break;
				default:
					c.drawBitmap( bmp1, EnemyMatrix[i], null );
					if( PosData[i].enemy_base_pattern == 5 || PosData[i].enemy_base_pattern == 11 ){
						c.drawBitmap( bmp1, EnemyMatrix[i], p );						
					}
					break;	
				}
			}else if( enemy_cought_state[i] == true && enemy_iseat_state[i] == false ){
				c.drawBitmap( bmp6, EnemyMatrix[i], null );
				if( PosData[i].enemy_base_pattern == 5 || PosData[i].enemy_base_pattern == 11 ){
					c.drawBitmap( bmp6, EnemyMatrix[i], p );						
				}				
			}
		}
				
	}
	
    public int getCombo_cnt() {
		return combo_cnt;
	}

	public void setCombo_cnt(int combo_cnt) {
		this.combo_cnt = combo_cnt;
	}

	public int getStage_ate_enemy_cnt() {
		return stage_ate_enemy_cnt;
	}

	public void setStage_ate_enemy_cnt(int stage_ate_enemy_cnt) {
		this.stage_ate_enemy_cnt = stage_ate_enemy_cnt;
	}

	public static boolean isCombo_cnt_switch() {
		return combo_cnt_switch;
	}

	public static void setCombo_cnt_switch( boolean onoff ) {
		combo_cnt_switch = onoff;
	}

	public boolean isJust_hit_on() {
		return just_hit_on;
	}

	public static void setJust_hit_on(boolean just_hit_on) {
		Enemy.just_hit_on = just_hit_on;
	}
	
	public float getJust_hit_position_x() {
		return just_hit_position_x;
	}
	
	public float getJust_hit_position_y() {
		return just_hit_position_y;
	}
	

	//各敵の位置を保管
	class EnemyPosition{
		public float e_position_x, e_position_y, e_move_x, e_move_y;
		public int enemy_base_pattern;		
		public int enemy_move_pattern;
		public boolean enemy_appear_flag;
		public EnemyPosition( float x, float y ){
			Random rndPos = new Random();
			float add;
			this.enemy_base_pattern = rndPos.nextInt( BASE_PATTERN );
			this.enemy_move_pattern = rndPos.nextInt( MOVE_PATTERN );
			switch( enemy_base_pattern ){
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:				
				add = ( ( rndPos.nextInt( 5 ) ) * 60.0f );
				this.e_position_x = ENEMY_POSITION_X + add;
				add = ( ( rndPos.nextInt( 5 ) - 5 ) * 5.0f );
				this.e_position_y = ENEMY_POSITION_Y + add;
				break;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:				
				add = ( ( rndPos.nextInt( 5 ) ) * 60.0f );
				this.e_position_x = ENEMY_POSITION_X + add;
				this.e_position_y = 470.0f;
				break;
			}
			this.e_move_x = x;
			this.e_move_y = y;
			this.enemy_appear_flag = true;
		}
	}

}
