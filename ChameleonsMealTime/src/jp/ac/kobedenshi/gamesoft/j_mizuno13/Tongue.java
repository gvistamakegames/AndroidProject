package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Tongue {
	
	//�e�ϐ���錾(��̊g�嗦XY�A��̕`��ʒuXY�A��̕`��p�x)
	protected float tongue_scale_x, tongue_scale_y = 1.0f;
	protected float TONGUE_POSITION_X, TONGUE_POSITION_Y, Angle;
	//��̏�Ԃ�\���t���O�ϐ���錾(������ԂƏo�Ă�����)
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
	
	//�R���X�g���N�^�ŏ�����
	public Tongue( float TONGUE_POSITION_X, float TONGUE_POSITION_Y  ){
		this.TONGUE_POSITION_X = TONGUE_POSITION_X;
		this.TONGUE_POSITION_Y = TONGUE_POSITION_Y;
 	}


	public float getTongueScaleX(){
		return tongue_scale_x;
	}
	
	
	public void AppearedTongueFlagChange( int Appeared ){
		//�n���ꂽ�l���P�Ȃ�^�A����ȊO�Ȃ�U�ƒ�`����
		if( Appeared == 1 ){
			this.appeared_tongue_flag = true;
		}else{
			this.appeared_tongue_flag = false;
		}
	}	
	
	public void GoTongueFlagChange( int go ){
		//�n���ꂽ�l���P�Ȃ�^�A����ȊO�Ȃ�U�ƒ�`����
		if( go == 1 ){
			this.go_tongue_flag = true;
		}else{
			this.go_tongue_flag = false;
		}
	}

	public void ReadyTongueFlagChange( int ready ){
		//�n���ꂽ�l���P�Ȃ�^�A����ȊO�Ȃ�U�ƒ�`����
		if( ready == 1 ){
			this.ready_tongue_flag = true;
		}else{
			this.ready_tongue_flag = false;
		}
	}
	
	public void AngleCheck( float up_position_x, float up_position_y, float down_position_x, float down_position_y ){
		//�t���b�N���A�_�E���̍��W�ƃA�b�v�̍��W�����A���ꂼ��̈ʒu����p�x�����߂�
		this.Angle = (int)Math.toDegrees( Math.atan2( ( up_position_y - down_position_y ), ( up_position_x - down_position_x ) ) );
		//�p�x�̏���Ɖ�����ݒ�
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
			//���ȉ��̏����́A�オ�o�Ă��Ȃ����A��ɏ�������Ă��銴��
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
		//���}�g���b�N�X�N���X�������ŃC���X�^���X��
		Matrix tongue_matrix = new Matrix();
	    //���ȉ��A�}�g���b�N�X�̊e�l�̋L�q�����ɒ��ӂ��鎖
		tongue_matrix.postScale( tongue_scale_x, this.tongue_scale_y );
        tongue_matrix.postRotate( this.Angle );
		tongue_matrix.postTranslate( this.TONGUE_POSITION_X, this.TONGUE_POSITION_Y );
		//��`��
		c.drawBitmap( bmp, tongue_matrix, null );
	}
		
}
