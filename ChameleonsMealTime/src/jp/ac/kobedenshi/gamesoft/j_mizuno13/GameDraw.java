package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.ac.kobedenshi.gamesoft.j_mizuno13.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.*;

public class GameDraw extends SurfaceView implements SurfaceHolder.Callback, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

	//�ǂݍ��މ摜�̖����o�^�p(�ǂݍ��މ摜�̐���ύX����ꍇ�ɁA�܂����̒l��ς���)
	private final static int PICTURES = 53;
	private final static int SE_SOUNDS = 8;
	private final static int BGM_SOUNDS = 5;
	
	private final static float BGM_MAX_VOLUME = 0.45f;
	
	private final static int ENEMY_BASE_POINT = 5;
	private final static int ENEMY_MIN_POINT = 3;
	
	private SurfaceHolder holder;
	private GestureDetector gd;
	private Bitmap[] bmp = new Bitmap[PICTURES];
	private Bitmap Basebmp = Bitmap.createBitmap( 800, 480, Bitmap.Config.ARGB_8888);
	
	//�`��p�ƂȂ�L�����o�X�^�ϐ��̐錾
	private Canvas Mainc;
	private Canvas c;	
	
	private int[] se_soundID = new int[SE_SOUNDS];
	
	private final static float CHARA_POSITION_X = 0.0f, CHARA_POSITION_Y = 250.0f;
	private final static float ENEMY_POSITION_X = 400.0f, ENEMY_POSITION_Y = 0.0f;	
	private final static float TONGUE_POSITION_X = CHARA_POSITION_X + 195.0f, TONGUE_POSITION_Y = CHARA_POSITION_Y + 60.0f;
	private final static float TONGUE_WIDTH = 605.0f/*, TONGUE_HEIGHT = 20.0f*/;
	
	private int GAME_SCENE = 0;
	private int TITLE_SCENE = 0;
	protected int STAGE_LEVEL = 0;
	public static int ENEMY_LEVEL = 0;
	//���Ԍo�߂�\���J�E���^(������ֈڍs����ۂɎg�p���Ă��܂�)
	private int time_cnt;
	private final static int AFTERNOON = 3000;
	
	private int blackout_cnt;
	protected int blackout_cnt1;
	
	
	private int result_cnt;
	
	private int thistime_score;
	
	public static boolean pause_flag = false;
	
	private boolean sound_on_flag = true;
	private boolean change_sound_flag = false; 
	
	private boolean game_finish = false;
	
	private boolean next_step = false;
	
	public boolean tongue_appeared = false;
	
	protected float touch_position_x = 0.0f, touch_position_y = 0.0f;
	protected float down_position_x = 0.0f, down_position_y = 0.0f;
    protected float up_position_x = 0.0f, up_position_y = 0.0f;
    protected float fling_vector_x = 0.0f, fling_vector_y = 0.0f;
	
	public static int SCORE = 0;
	
	protected static boolean fever_mode;
	protected static boolean now_feverring;
	protected static boolean finish_feverring;
	
		
	//�w�i�`��p�ϐ�
	private BackGround background;
	private Title title;
	//�G�`��p�ϐ�
	private Enemy enemy;
	private Tongue tongue;
	private Charactor charactor;
	private UI ui;
	private CheckFlag check_vector;
	private SoundPlayer sp;

	private int Ang = 0;
	private float alpha_Ang = 0.0f;
	private float alpha_Ang2 = 90.0f;
	
	private float vol_control;

	private int hit_up_cnt;
	private int hit_just_cnt;
	
	private int title_cnt;

	private boolean next_scene_flag;
	private int next_scene_cnt;
	private int title_scene_no;	
		
	private final static float NowVer = 1.10f;
	
	public GameDraw( Context context ){
		super( context );
		
        //�摜�`��ׂ̈̐ݒ�̏�����
		Resources r = getResources();
		bmp[0] = BitmapFactory.decodeResource( r, R.drawable.title );      
		bmp[1] = BitmapFactory.decodeResource( r, R.drawable.back1 );
		bmp[2] = BitmapFactory.decodeResource( r, R.drawable.back2 );		
		bmp[3] = BitmapFactory.decodeResource( r, R.drawable.came11 );       	
		bmp[4] = BitmapFactory.decodeResource( r, R.drawable.came21 );
		bmp[5] = BitmapFactory.decodeResource( r, R.drawable.tongue3 );
		bmp[6] = BitmapFactory.decodeResource( r, R.drawable.ha11 );
		bmp[7] = BitmapFactory.decodeResource( r, R.drawable.back5 );
		bmp[8] = BitmapFactory.decodeResource( r, R.drawable.back3 );
		bmp[9] = BitmapFactory.decodeResource( r, R.drawable.hit );
		bmp[10] = BitmapFactory.decodeResource( r, R.drawable.ha2 );
		bmp[11] = BitmapFactory.decodeResource( r, R.drawable.kazu4 );
		bmp[12] = BitmapFactory.decodeResource( r, R.drawable.uimoji2 );
		bmp[13] = BitmapFactory.decodeResource( r, R.drawable.ha12 );
		bmp[14] = BitmapFactory.decodeResource( r, R.drawable.ha13 );
		bmp[15] = BitmapFactory.decodeResource( r, R.drawable.ha14 );
		bmp[16] = BitmapFactory.decodeResource( r, R.drawable.ha15 );
		bmp[17] = BitmapFactory.decodeResource( r, R.drawable.came12 );
		bmp[18] = BitmapFactory.decodeResource( r, R.drawable.came13 );
		bmp[19] = BitmapFactory.decodeResource( r, R.drawable.came14 );
		bmp[20] = BitmapFactory.decodeResource( r, R.drawable.came15 );
		bmp[21] = BitmapFactory.decodeResource( r, R.drawable.came22 );
		bmp[22] = BitmapFactory.decodeResource( r, R.drawable.came23 );
		bmp[23] = BitmapFactory.decodeResource( r, R.drawable.gageframe );
		bmp[24] = BitmapFactory.decodeResource( r, R.drawable.stamina );
		bmp[25] = BitmapFactory.decodeResource( r, R.drawable.came01 );
		bmp[26] = BitmapFactory.decodeResource( r, R.drawable.came02 );
		bmp[27] = BitmapFactory.decodeResource( r, R.drawable.came03 );
		bmp[28] = BitmapFactory.decodeResource( r, R.drawable.came04 );
		bmp[29] = BitmapFactory.decodeResource( r, R.drawable.came05 );
		bmp[30] = BitmapFactory.decodeResource( r, R.drawable.kazu4 );
		bmp[31] = BitmapFactory.decodeResource( r, R.drawable.fever );		
		bmp[32] = BitmapFactory.decodeResource( r, R.drawable.fever_back );		
		bmp[33] = BitmapFactory.decodeResource( r, R.drawable.back7 );		
		bmp[34] = BitmapFactory.decodeResource( r, R.drawable.fever2 );		
		bmp[35] = BitmapFactory.decodeResource( r, R.drawable.fever3 );		
		bmp[36] = BitmapFactory.decodeResource( r, R.drawable.fever4 );		
		bmp[37] = BitmapFactory.decodeResource( r, R.drawable.fever5 );
		bmp[38] = BitmapFactory.decodeResource( r, R.drawable.branch );
		bmp[39] = BitmapFactory.decodeResource( r, R.drawable.back6 );
		bmp[40] = BitmapFactory.decodeResource( r, R.drawable.jungleback2 );		
		bmp[41] = BitmapFactory.decodeResource( r, R.drawable.moon1 );		
		bmp[42] = BitmapFactory.decodeResource( r, R.drawable.star1 );		
		bmp[43] = BitmapFactory.decodeResource( r, R.drawable.star2 );
		bmp[44] = BitmapFactory.decodeResource( r, R.drawable.chanse );
		bmp[45] = BitmapFactory.decodeResource( r, R.drawable.hitmark1 );
		bmp[46] = BitmapFactory.decodeResource( r, R.drawable.hitmark2 );
		bmp[47] = BitmapFactory.decodeResource( r, R.drawable.hitmark3 );
		bmp[48] = BitmapFactory.decodeResource( r, R.drawable.eye1 );
		bmp[49] = BitmapFactory.decodeResource( r, R.drawable.uimoji3 );
		bmp[50] = BitmapFactory.decodeResource( r, R.drawable.uimoji4 );
		bmp[51] = BitmapFactory.decodeResource( r, R.drawable.ge );	
		bmp[52] = BitmapFactory.decodeResource( r, R.drawable.uimoji5 );	
		
		//�W�F�X�`���[�N���X���g���ׂ̃��m
	    gd = new GestureDetector( context, this );
	    
	    //�L�����o�X�N���X�̃C���X�^���X��
	    Mainc = new Canvas();
	    c = new Canvas( Basebmp );
	    
	    //��x����̏�����
		FirstInit();
				
	}
	
	public GameDraw( Context context, AttributeSet attrs ){
		super( context, attrs );
	}
		
	public void FirstInit(){
		holder = getHolder();
		holder.addCallback( this );
		setFocusable( true );
		requestFocus();
		
//        Mainc = new Canvas();
//        c = new Canvas( Basebmp );
		
		background = new BackGround( bmp[1].getWidth(), bmp[1].getHeight(), bmp[1].getWidth(), bmp[1].getHeight() );
		title = new Title( bmp[0].getWidth(), bmp[0].getHeight(), bmp[0].getWidth(), bmp[0].getHeight() );
		enemy = new Enemy( ENEMY_POSITION_X, ENEMY_POSITION_Y, ENEMY_LEVEL );
		tongue = new Tongue( TONGUE_POSITION_X, TONGUE_POSITION_Y );
		charactor = new Charactor( CHARA_POSITION_X, CHARA_POSITION_Y );
		check_vector = new CheckFlag();
		ui = new UI();
		sp = new SoundPlayer( getContext(), SE_SOUNDS, BGM_SOUNDS );

		title.Init();
		enemy.Init( ENEMY_LEVEL );
		ui.Init();
				
		TITLE_SCENE = 0;
		STAGE_LEVEL = 1;
		ENEMY_LEVEL = ENEMY_BASE_POINT;
		SCORE = 0;
		
		time_cnt = AFTERNOON;
		
		fever_mode = false;
		now_feverring = false;
		finish_feverring = false;
		
		game_finish = false;
		
		next_step = false;
		
		change_sound_flag = false;
		
		blackout_cnt = 0;
		blackout_cnt1 = 0;
		
		result_cnt = 0;
		thistime_score = 0;
		
		
		hit_up_cnt = 0;
		hit_just_cnt = 0;
		
		title_cnt = 0;
		
		next_scene_flag = false;
		next_scene_cnt = 0;
		title_scene_no = 0;
		
		vol_control = BGM_MAX_VOLUME;
						
		//BGM�t�@�C���̏�����(���[�h�܂�)
		//��1�����͎g�p����t�@�C�����A��2�����͂���BGM�t�@�C���̃i���o�[�Ƃ��܂�
		sp.InitBGM( "flyflocking.ogg", 0 );  //�n�G����ԉ�
		sp.InitBGM( "title.ogg", 1 );
		sp.InitBGM( "result.ogg", 2 );
		sp.InitBGM( "playing1.ogg", 3 );
		sp.InitBGM( "chanse.ogg", 4 );
		
		//SE�t�@�C���̏�����(���[�h�܂ށA����̊֌W�ł��̊֐����Ńt�@�C�������[�h���Ă��܂�)
		//��1�����͎g�p����t�@�C�����A��2�����͂���SE�t�@�C���̃i���o�[�Ƃ��܂�
		se_soundID[0] = sp.InitSE( "tongue1.ogg", 0 );   //�オ�L�т�Ƃ��̉�
		se_soundID[1] = sp.InitSE( "swallow.ogg", 1 );  //�G�����ݍ��ނƂ��̉�
		se_soundID[2] = sp.InitSE( "select.ogg", 2 );  //�Z���N�g��
		se_soundID[3] = sp.InitSE( "surprise.ogg", 3 );  //�V�L�^��
		se_soundID[4] = sp.InitSE( "burp.ogg", 4 );  //�Q�b�v
		se_soundID[5] = sp.InitSE( "star1.ogg", 5 );  //�ڂ�����Ƃ�
		se_soundID[6] = sp.InitSE( "up.ogg", 6 );  //�e���V����UP��
		se_soundID[7] = sp.InitSE( "hit.ogg", 7 );  //�W���X�g�q�b�g		
		
	}
	
	
	@Override
	public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ){
		
	}
	
	@Override
	//�܂��A����������s�����
	public void surfaceCreated( SurfaceHolder holder ){
		
		FirstInit();
		start();		//start()��run()�����s�������
		
	}	
	
	@Override
	public void surfaceDestroyed( SurfaceHolder holder ){
				
		sp.ReleaseBGM( "flyflocking.ogg", 0 );
		sp.ReleaseBGM( "title.ogg", 1 );
		sp.ReleaseBGM( "result.ogg", 2 );
		sp.ReleaseBGM( "playing1.ogg", 3 );
		sp.ReleaseBGM( "chanse.ogg", 4 );

		sp.ReleaseSE( se_soundID[0], 0 );
		sp.ReleaseSE( se_soundID[1], 1 );
		sp.ReleaseSE( se_soundID[2], 2 );
		sp.ReleaseSE( se_soundID[3], 3 );
		sp.ReleaseSE( se_soundID[4], 4 );
		sp.ReleaseSE( se_soundID[5], 5 );
		sp.ReleaseSE( se_soundID[6], 6 );
		sp.ReleaseSE( se_soundID[7], 7 );
						
	}
	
	
    //�ȉ��̏������A�������[�v����--------------------------------------------------------------------------------------------------------------------
	public void start(){
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate( new Runnable() {
			
			@Override
			//�}���`�X���b�h�ɂ���ׁArun���\�b�h�ɕ`���v�Z��ݒ�&�o�^����d�l�Ƃ��Ă���
			public void run(){
				
				//�L�����o�X���b�N
				Mainc = holder.lockCanvas();
				
				
				//�{�^�����������Ƃ��p
				if( next_scene_flag == true ){
					next_scene_cnt += 1;
					if( next_scene_cnt > 10 ){
						switch( title_scene_no ){
						case 0:
							next_step = true;
							break;
						case 1:
							TITLE_SCENE = 1;
							break;
						case 2:
							TITLE_SCENE = 2;
							break;
						case 3:
							TITLE_SCENE = 3;
							break;
						case 4:
							switch( TITLE_SCENE ){
							case 0:
								GAME_SCENE = 0;
								break;
								
							case 1:
								GAME_SCENE = 1;
								break;
								
							case 2:
								GAME_SCENE = 1;
								break;
								
							case 3:
								GAME_SCENE = 1;
								break;
								
							default:
								break;
																
							}
							break;
						case 5:
							GAME_SCENE = 4;
							break;
						case 6:
							GAME_SCENE = 5;
							break;
						case 7:
							if( GAME_SCENE == 1 && TITLE_SCENE == 0 ){
								blackout_cnt = 0;
								GAME_SCENE = 0;
							}else{
								TITLE_SCENE = 0;
							}
							break;							
						}
						next_scene_cnt = 0;
						title_scene_no = 0;
						next_scene_flag = false;
					}
				}

				
				
				switch( GAME_SCENE ){
				//���C���^�C�g���`��
				case 0:
					
					TitleMain( c );
					
					Paint p = new Paint();
					p.setTextSize( 40 );
					p.setColor( Color.BLACK );
					c.drawText( "Ver. " + NowVer, 630.0f, 460.0f, p );
					
					
					if( tongue.appeared_tongue_flag == false ){
						title_cnt += 1;
						if( title_cnt >= 60 ){
							ui.DrawSentence( c, bmp[52], 0, 0, 300, 50, 400, 250 );
							ui.DrawHandFling( c, bmp[52], 0, 62, 46, 57, 130, 180, title_cnt );
							ui.DrawHandTouch( c, bmp[52], 157, 62, 46, 57, 550, 180, title_cnt );
							if( title_cnt >= 90 ){
								title_cnt = 0;
							}
						}
					}
					
					if( sound_on_flag == true ){
						sp.SetVolumeBGM( "title.ogg", 1, BGM_MAX_VOLUME, BGM_MAX_VOLUME );
						sp.PlayBGM( "title.ogg", 1);
					}
					
					//�u���b�N�A�E�g�����ʂ�`��
					background.DrawBackshadow( c, bmp[7], blackout_cnt );					
					//�}�̕`��
					background.DrawBackground( c, bmp[38] );
					
					
					//����܂߂��L�����̕`��
					CheckFlag check_vector = new CheckFlag();
					int select = check_vector.CheckVector( fling_vector_x );
					tongue.TongueScaleChange( select );
					if( tongue.go_tongue_flag == true ){
						if( sound_on_flag == true ){
							sp.PlaySE( se_soundID[0], 0 );
						}
						title_cnt = 0;
					}
					tongue.DrawTongue( c, bmp[5] );
					

					if( tongue.ready_tongue_flag == false ){
						charactor.DrawChara( c, bmp[3], bmp[25], bmp[26], bmp[27], bmp[28], bmp[29] );
					}else{
						charactor.DrawChara( c, bmp[3], bmp[4], bmp[21], bmp[22] );
					}	
					
					if( next_step == true ){
						blackout_cnt += 8;
						if( blackout_cnt > 230 ){
							blackout_cnt = 230;
							next_step = false;
							GAME_SCENE = 1;
						}
					}
					break;
					
				//�X�e�[�W�^�C�g���`��
				case 1:
					title_cnt = 0;
					if( next_step == false ){
						TitleMain( c );
						//�u���b�N�A�E�g�����ʂ�`��
						background.DrawBackshadow( c, bmp[7], blackout_cnt );
						switch( TITLE_SCENE ){
						case 0:
							//�Q�[���X�^�[�g
							ui.DrawSentence( c, bmp[49], 0, 384, 320, 66, 400, 70 );
//							ui.DrawHandTouchResult( c, bmp[52], 157, 62, 46, 57, 350, 60 );
							if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 0 ){
								ui.DrawSentence( c, bmp[49], 0, 384, 320, 66, 400, 70, "on" );
							}							
							//�X�R�A�����L���O
							ui.DrawSentence( c, bmp[49], 0, 460, 390, 54, 400, 180 );
//							ui.DrawHandTouchResult( c, bmp[52], 157, 62, 46, 57, 350, 170 );
							if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 1 ){
								ui.DrawSentence( c, bmp[49], 0, 460, 390, 54, 400, 180, "on" );
							}
							//�I�v�V����
							ui.DrawSentence( c, bmp[49], 0, 528, 244, 62, 400, 271 );
//							ui.DrawHandTouchResult( c, bmp[52], 157, 62, 46, 57, 350, 261 );
							if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 2 ){
								ui.DrawSentence( c, bmp[49], 0, 528, 244, 62, 400, 271, "on" );
							}
							//�N���W�b�g
							ui.DrawSentence( c, bmp[49], 0, 722, 220, 62, 400, 372 );
//							ui.DrawHandTouchResult( c, bmp[52], 157, 62, 46, 57, 350, 361 );
							if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 3 ){
								ui.DrawSentence( c, bmp[49], 0, 722, 220, 62, 400, 372, "on" );
							}
							//�߂�
							ui.DrawSentence( c, bmp[49], 10, 1055, 156, 60, 50, 100 );
							if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 7 ){
								ui.DrawSentence( c, bmp[49], 10, 1055, 156, 60, 50, 100, "on" );
							}
							
							break;
						//�X�R�A�����N��\��
						case 1:
							
							ui.DrawSentence( c, bmp[49], 0, 786, 140, 50, 350, 40 );
							ui.DrawScore( c,  bmp[11], LoadSavedScore( 0 ), 500, 100 );
							
							ui.DrawSentence( c, bmp[49], 0, 840, 140, 50, 350, 120 );
							ui.DrawScore( c,  bmp[11], LoadSavedScore( 1 ), 500, 180 );
							
							ui.DrawSentence( c, bmp[49], 0, 892, 140, 50, 350, 200 );
							ui.DrawScore( c,  bmp[11], LoadSavedScore( 2 ), 500, 260 );
							
							ui.DrawSentence( c, bmp[49], 0, 947, 140, 50, 350, 280 );
							ui.DrawScore( c,  bmp[11], LoadSavedScore( 3 ), 500, 340 );
							
							ui.DrawSentence( c, bmp[49], 0, 1000, 140, 50, 350, 360 );
							ui.DrawScore( c,  bmp[11], LoadSavedScore( 4 ), 500, 420 );
							
							//�߂�
							ui.DrawSentence( c, bmp[49], 10, 1055, 156, 60, 50, 100 );
							if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 7 ){
								ui.DrawSentence( c, bmp[49], 10, 1055, 156, 60, 50, 100, "on" );
							}
							
							break;
						//�I�v�V����
						case 2:
							ui.DrawSentence( c, bmp[49], 0, 594, 194, 62, 330, 100 );
							if( sound_on_flag == false ){
									ui.DrawSentence( c, bmp[49], 0, 660, 105, 57, 430, 180 );
									ui.DrawSentence( c, bmp[49], 105, 660, 210, 57, 590, 180, "on" );
									if( change_sound_flag == true ){
										//���y���~�߂�(�C�j�b�g�ƃZ�b�g�ŏ�肭�����܂��E�E�E)
										sp.StopBGM( "title.ogg", 1 );
//										sp.SetVolumeBGM( "title.ogg", 1, BGM_MAX_VOLUME, BGM_MAX_VOLUME );
//										sp.InitBGM( "title.ogg", 1 );
//										change_sound_flag = false;
									}
							}else{
								ui.DrawSentence( c, bmp[49], 0, 660, 105, 57, 430, 180, "on" );
								ui.DrawSentence( c, bmp[49], 105, 660, 210, 57, 590, 180 );
								if( change_sound_flag == true ){
									//���y���Đ�(�C�j�b�g�ƃZ�b�g�ŏ�肭�����܂��E�E�E)
									sp.InitBGM( "title.ogg", 1 );
									sp.SetVolumeBGM( "title.ogg", 1, BGM_MAX_VOLUME, BGM_MAX_VOLUME );
									sp.PlayBGM( "title.ogg", 1 );
//									change_sound_flag = false;
								}
							}
							
							//�߂�
							ui.DrawSentence( c, bmp[49], 10, 1055, 156, 60, 50, 100 );
							if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 7 ){
								ui.DrawSentence( c, bmp[49], 10, 1055, 156, 60, 50, 100, "on" );
							}
						
							break;
						case 3:
							
							Paint cp = new Paint();
							cp.setTextSize( 36 );
							cp.setColor( Color.WHITE );
							c.drawText( "�����f�ޒ񋟁@On-Jin ���l �l", 250.0f, 200.0f, cp );
							c.drawText( "���肪�Ƃ��������܂���", 300.0f, 250.0f, cp );
							
							//�߂�
							ui.DrawSentence( c, bmp[49], 10, 1055, 156, 60, 50, 100 );
							if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 7 ){
								ui.DrawSentence( c, bmp[49], 10, 1055, 156, 60, 50, 100, "on" );
							}
							
							break;
						default:
							
							//�߂�
							ui.DrawSentence( c, bmp[49], 10, 1055, 156, 60, 50, 100 );
							if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 7 ){
								ui.DrawSentence( c, bmp[49], 10, 1055, 156, 60, 50, 100, "on" );
							}
							
							break;
						}
					}else{
                        //���̔w�i��`��
						background.DrawBackground( c, bmp[1], bmp[2], bmp[8], time_cnt );
						//�W�����O���͗l��`��
						background.DrawBackground( c, bmp[40] );
						//�u���b�N�A�E�g�����ʂ�`��
						background.DrawBackshadow( c, bmp[7], blackout_cnt );
					}
					//�}�̕`��
					background.DrawBackground( c, bmp[38] );
					//�L�����̕`��
					charactor.DrawChara( c, bmp[3], bmp[25], bmp[26], bmp[27], bmp[28], bmp[29] );
					
					
					if( next_step == true ){
						if( sound_on_flag == true ){
							sp.SetVolumeBGM( "playing1.ogg", 3, BGM_MAX_VOLUME, BGM_MAX_VOLUME );
							sp.PlayBGM( "playing1.ogg", 3 );
						}
						//�u���b�N�A�E�g����X�s�[�h
						blackout_cnt -= 2;
						
						//���������܁`���I�I
						if( blackout_cnt > 20 && blackout_cnt < 210 ){
							ui.DrawSentence( c, bmp[49], 0, 88, 530, 90, 135, 70 );
						}
						if( blackout_cnt < 0 ){
							blackout_cnt = 0;
							next_step = false;
							if( sound_on_flag == true ){
								sp.SetVolumeBGM( "flyflocking.ogg", 0, BGM_MAX_VOLUME - 0.05f, BGM_MAX_VOLUME - 0.05f );
								sp.PlayBGM( "flyflocking.ogg", 0 );
							}
							GAME_SCENE = 2;
						}
					}					
					
					break;
					
				//�Q�[�����C��(�{��)�`��
				case 2:
					
					GameMain( c );
										
				    break;
				
				//���U���g�`��
				case 3:
					if( sound_on_flag == true ){
						vol_control -= 0.005f;
						if( vol_control <= 0.0f ){
							vol_control = 0.0f;
						}
						sp.SetVolumeBGM( "playing1.ogg", 3, vol_control, vol_control );
						sp.SetVolumeBGM( "chanse.ogg", 4, vol_control, vol_control );
					}
					
					blackout_cnt += 3;
					if( blackout_cnt > 230 ){
						blackout_cnt = 230;
					}
					
					if( blackout_cnt >= 220 ){
						result_cnt += 1;
						if( result_cnt >= 370 ){
							result_cnt = 370;
						}
					}
					
					//���̔w�i�̕`��
					background.DrawBackground( c, bmp[1], bmp[2], bmp[8], time_cnt );
					//��̔w�i�̕`��
					background.DrawBackground( c, bmp[39], time_cnt );
					//���̕`��
					background.DrawBackgroundMoon( c, bmp[41], bmp[42], bmp[43], time_cnt );
					//�W�����O���͗l��`��
					background.DrawBackground( c, bmp[40] );
					//�u���b�N�A�E�g�����ʂ�`��
					background.DrawBackshadow( c, bmp[7], blackout_cnt );
					//�}�̕`��
					background.DrawBackground( c, bmp[38] );
					//�L�����̕`��
					if( result_cnt >= 35 && result_cnt <= 70 ){
						if( sound_on_flag == true ){
							sp.StopBGM( "playing1.ogg", 3 );
							sp.StopBGM( "chanse.ogg", 4 );
							sp.StopBGM( "flyflocking.ogg", 0 );
						}						
						
						if( SCORE >= 5000 ){
							charactor.DrawChara( c, bmp[22] );
							charactor.DrawChara( c, bmp[51], CHARA_POSITION_X + 240.0f, CHARA_POSITION_Y + 30.0f );

							//�������ɁA�G��H�ׂ���������΁E�E�E��if��������
							if( result_cnt >= 40 && result_cnt <= 45 ){
								if( sound_on_flag == true ){
									sp.PlaySE( se_soundID[4], 4 );
								}
							}
						}else{
							charactor.DrawChara( c, bmp[3], bmp[25], bmp[26], bmp[27], bmp[28], bmp[29] );							
						}
					}else{
						charactor.DrawChara( c, bmp[3], bmp[25], bmp[26], bmp[27], bmp[28], bmp[29] );
					}
					
					if( result_cnt >= 100 ){
						if( sound_on_flag == true ){
							sp.SetVolumeBGM( "result.ogg", 2, BGM_MAX_VOLUME, BGM_MAX_VOLUME );
							sp.PlayBGM( "result.ogg", 2 );
						}
					}					
					//�����������܂ł����I�I
					if( result_cnt >= 100 && result_cnt <= 180 ){
						ui.DrawSentence( c, bmp[49], 0, 0, 664, 88, 65, 70 );
					}
					//�X�R�A�\��
					if( result_cnt >= 220 ){
						if( thistime_score < SCORE ){
							if( SCORE < 10000 ){
								thistime_score += 150;
							}else if( SCORE > 10000 && SCORE < 100000 ){
								thistime_score += 550;
							}else if( SCORE >= 10000 ){
								thistime_score += 1050;								
							}
						}
						if( thistime_score >= SCORE ){
							thistime_score = SCORE;
						}
						ui.DrawSentence( c, bmp[50], 0, 0, 352, 50, 430, 30 );
						ui.DrawScore( c,  bmp[11], thistime_score, 430, 150 );
						ui.DrawSentence( c, bmp[50], 0, 50, 206, 100, 430, 150 );
						if( LoadSavedScore( 0 ) >= thistime_score ){
							ui.DrawScore( c,  bmp[11], LoadSavedScore( 0 ), 430, 270 );
						}else{
							ui.DrawScore( c,  bmp[11], thistime_score, 430, 270 );							
						}
						//�V�L�^�Ȃ��
						if( LoadSavedScore( 0 ) < SCORE ){
							Ang += 1;
							if( Ang >= 360 ){
								Ang = 0;
							}
							ui.DrawSentence( c, bmp[49], 0, 180, 360, 72, 30, 100, (float)Math.sin( Ang ) );
							if( result_cnt >= 222 && result_cnt <= 226 ){
								if( sound_on_flag == true ){
									sp.PlaySE( se_soundID[3], 3 );
								}
							}
						}
					}
					//���̌�ǂ����邩�H�H�̑I����
					if( result_cnt >= 340 ){
						if( thistime_score < SCORE ){
							thistime_score = SCORE;
						}
						ui.DrawSentence( c, bmp[49], 0, 252, 414, 65, 370, 300 );
						if( result_cnt >= 350 ){
							ui.DrawHandTouchResult( c, bmp[52], 157, 62, 46, 57, 330, 290 );
						}
						if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 6 ){
							ui.DrawSentence( c, bmp[49], 0, 252, 414, 65, 370, 300, "on" );									
						}
						
						ui.DrawSentence( c, bmp[49], 0, 316, 414, 68, 370, 400 );
						if( result_cnt >= 350 ){
							ui.DrawHandTouchResult( c, bmp[52], 157, 62, 46, 57, 330, 390 );
						}
						if( next_scene_flag == true && next_scene_cnt <= 10 && title_scene_no == 5 ){
							ui.DrawSentence( c, bmp[49], 0, 316, 414, 68, 370, 400, "on" );
						}						
						
					}
					break;
				
				//�f�[�^�̋L�^�����Ă���^�C�g����
				case 4:
					if( sound_on_flag == true ){
						sp.StopBGM( "result.ogg", 2 );
					}
					Save( SCORE );
					FirstInit();
					TITLE_SCENE = 0;
					GAME_SCENE = 0;					
					break;
				
				//�f�[�^�̋L�^�����Ă���Q�[���V�[����
				case 5:
					if( sound_on_flag == true ){
						sp.StopBGM( "result.ogg", 2 );
					}
					Save( SCORE );
					FirstInit();
					next_step = true;
					blackout_cnt = 230;
					TITLE_SCENE = 0;
					GAME_SCENE = 1;
					break;
	
				default:
					break;
					
				}
								
				//�ŏI�I�Ƀf�B�X�v���C�T�C�Y�ɉ摜�����킹�Ă���\������
				Rect disp_src = new Rect();
				Rect disp_dst = new Rect();
				
				disp_src.set( 0, 0, 800, 480 );
				disp_dst.set( 0, 0, getWidth(), getHeight() );
				Mainc.drawBitmap( Basebmp, disp_src, disp_dst, null );

				//�L�����o�X�A�����b�N
				holder.unlockCanvasAndPost( Mainc );
				
			}
						
		}, 34, 34, TimeUnit.MILLISECONDS );    //���s���Ԃ̓R�R�̈����ŕύX�\(�x�����ԁA�J��Ԃ�����)
		
	}
	//�����܂�-----------------------------------------------------------------------------------------------------------------------------------
		
	
	//�^�b�`�C�x���g
	@Override
	public boolean onTouchEvent( MotionEvent event ){
		gd.onTouchEvent( event );
		
		int n = event.getAction();            //�ǂ̂悤�ȃ^�b�`�A�N�V������������������͂���
		touch_position_x = event.getX();	  //�^�b�`�A�N�V��������X���W�����o��
		touch_position_y = event.getY();	  //�^�b�`�A�N�V��������Y���W�����o��
				
		touch_position_x = touch_position_x * ( 800.0f / getWidth() );  //�䗦�Œ���(800*480�Ńv���O������g��ł���̂�)
		touch_position_y = touch_position_y * ( 480.0f / getHeight() );	//�䗦�Œ���(800*480�Ńv���O������g��ł���̂�)	
		
        //���U���g�̃X�L�b�v
		if( GAME_SCENE == 3 ){
			blackout_cnt += 30;
			if( blackout_cnt > 230 ){
				blackout_cnt = 230;
			}			
			if( blackout_cnt >= 220 ){
				result_cnt += 80;
			}
		}	
		
		switch( n ){
	    //�w�ŉ����ꂽ��
		case MotionEvent.ACTION_DOWN:
			down_position_x = touch_position_x;
			down_position_y = touch_position_y;
			break;
		//�h���b�O���ꂽ��
		case MotionEvent.ACTION_MOVE:
			break;
		//�w�����ꂽ�Ƃ�
		case MotionEvent.ACTION_UP:
			up_position_x = touch_position_x;
			up_position_y = touch_position_y;
			break;
		}
		return true;
	}
	
	//�t���b�N
	@Override
	public boolean onFling( MotionEvent event1, MotionEvent event2, float Vec_x, float Vec_y ){
		
		//�t���b�N���Ɋe�����ւ̈ړ����������
		fling_vector_x = Vec_x;
		fling_vector_y = Vec_y;
				
		if( pause_flag != true && GAME_SCENE != 1 ){
			if( tongue.appeared_tongue_flag == false ){
				tongue.AngleCheck( Vec_x, Vec_y, down_position_x, down_position_y );
				if( down_position_x <= 500.0f && down_position_y >= 200.0f && up_position_x > down_position_x ){
					tongue.GoTongueFlagChange( 1 );
					tongue.AppearedTongueFlagChange( 1 );
				}
			}
		}
	    return false;
	}
	
	//�ȉ�5�̃��\�b�h�́A�G���[���o���Ȃ��ׂ̂Ƃ肠�����̎����ł�
	@Override
	public boolean onDown( MotionEvent event ) {
		return false;
	}
	@Override
	public void onLongPress( MotionEvent event ) {
	
//		//(���ݒu)��ŏ�����
//		if( GAME_SCENE == 1 || GAME_SCENE == 2 ){
//			//�^�C�g���ɖ߂�
//			if( touch_position_x <= 100.0f && touch_position_y <= 100.0f ){
//				GAME_SCENE = 0;
//				FirstInit();
//			}
//		}
//		
//        //(���ݒu)�G����萔�o��������
//		if( GAME_SCENE == 2 ){
//			if( touch_position_x >= 700.0f && touch_position_y >= 400.0f ){
//				ENEMY_LEVEL = 10;
//				enemy.Init( ENEMY_LEVEL );
//			}			
//		}
//		
//		//(���ݒu)��ŕK���������A�t�B�[�o�[�p
//		if( GAME_SCENE == 2 ){
//			if( touch_position_x >= 700.0f && touch_position_y >= 280.0f && touch_position_y <= 330.0f ){
//				UI.setFever_x( -1.0f );				
//				UI.setFever_x( 1.0f );
//				now_feverring = true;
//			}			
//		}		
		
	}
	@Override
	public boolean onScroll(MotionEvent event1, MotionEvent event2, float x, float y ) {
		return false;
	}
	@Override
	public void onShowPress( MotionEvent event ) {
	}
	@Override
	public boolean onSingleTapUp( MotionEvent event ) {
		
		//��ԍŏ��̃^�C�g�����玟�̃V�[����
		if( GAME_SCENE == 0 && next_step == false ){
			if( sound_on_flag == true ){
				sp.PlaySE( se_soundID[2], 2 );
			}
			next_step = true;
		}		
		
		if( GAME_SCENE == 1 && TITLE_SCENE == 0 && next_step == false ){
			//�߂�
			if( ( down_position_x >= 50.0f && down_position_x <= 196.0f ) && ( down_position_y >= 100.0f && down_position_y <= 154.0f ) ){
				if( sound_on_flag == true ){
					sp.PlaySE( se_soundID[2], 2 );
				}
				next_scene_flag = true;
				title_scene_no = 7;
			}
		}
		
		if( GAME_SCENE == 1 && next_step == false ){
			if( TITLE_SCENE == 0 ){
				//�Q�[���X�^�[�g
				if( ( down_position_x >= 400.0f && down_position_x <= 720.0f ) && ( down_position_y >= 70.0f && down_position_y <= 136.0f ) ){
					if( sound_on_flag == true ){
						//����n�͕K��1��̂ݒʂ�悤�ɂ��邱�ƁI�I
						sp.StopBGM( "swallow.ogg", 1 );
					}
					if( sound_on_flag == true ){
						sp.PlaySE( se_soundID[2], 2 );
					}
					next_scene_flag = true;
					title_scene_no = 0;
				}
				//�X�R�A�����L���O
				if( ( down_position_x >= 400.0f && down_position_x <= 790.0f ) && ( down_position_y >= 180.0f && down_position_y <= 234.0f ) ){
					if( sound_on_flag == true ){
						sp.PlaySE( se_soundID[2], 2 );
					}
					next_scene_flag = true;
					title_scene_no = 1;
				}
				//�I�v�V����
				if( ( down_position_x >= 400.0f && down_position_x <= 644.0f ) && ( down_position_y >= 271.0f && down_position_y <= 333.0f ) ){
					if( sound_on_flag == true ){
						sp.PlaySE( se_soundID[2], 2 );
					}
					next_scene_flag = true;
					title_scene_no = 2;
				}
				//�N���W�b�g
				if( ( down_position_x >= 400.0f && down_position_x <= 620.0f ) && ( down_position_y >= 372.0f && down_position_y <= 434.0f ) ){
					if( sound_on_flag == true ){
						sp.PlaySE( se_soundID[2], 2 );
					}
					next_scene_flag = true;
					title_scene_no = 3;
				}
			}else{
				//�߂�
				if( ( down_position_x >= 50.0f && down_position_x <= 196.0f ) && ( down_position_y >= 100.0f && down_position_y <= 154.0f ) ){
					if( sound_on_flag == true ){
						sp.PlaySE( se_soundID[2], 2 );
					}
					next_scene_flag = true;
					title_scene_no = 7;
				}
			}
			
			//�I�v�V�������̃T�E���h�̑I��
			if( TITLE_SCENE == 2 ){
				if( sound_on_flag == false ){
					if( ( down_position_x >= 430.0f && down_position_x <= 535.0f ) && ( down_position_y >= 180.0f && down_position_y <= 237.0f ) ){
						sound_on_flag = true;
						change_sound_flag = true;
						if( sound_on_flag == true ){
							sp.PlaySE( se_soundID[2], 2 );
						}
					}
				}else{
					if( ( down_position_x >= 590.0f && down_position_x <= 695.0f ) && ( down_position_y >= 180.0f && down_position_y <= 237.0f ) ){
						sound_on_flag = false;
						change_sound_flag = true;
					}					
				}
			}
		}
		
		if( GAME_SCENE == 3 ){
			if( result_cnt >= 350 ){
				//������x�v���C
				if( ( down_position_x >= 370.0f ) && ( down_position_y >= 300.0f && down_position_y <= 368.0f ) ){
					if( sound_on_flag == true ){
						sp.PlaySE( se_soundID[2], 2 );
					}
					next_scene_flag = true;
					title_scene_no = 6;
				}
				//�^�C�g����
				if( ( down_position_x >= 370.0f ) && ( down_position_y >= 400.0f ) ){
					if( sound_on_flag == true ){
						sp.PlaySE( se_soundID[2], 2 );
					}
					next_scene_flag = true;
					title_scene_no = 5;
				}
			}
		}

//		//(��)�|�[�Y
//		if( GAME_SCENE == 2 ){
//			if( touch_position_x <= 70.0f && touch_position_y <= 70.0f ){
//				if( pause_flag == false ){
//					pause_flag = true;
//				}else{
//					pause_flag = false;
//				}
//			}			
//		}
						
		return false;
		
	}
	@Override
	public boolean onSingleTapConfirmed( MotionEvent event ){
		return false;
	}
	//�_�u���^�b�v
	@Override
	public boolean onDoubleTap( MotionEvent event ){

//		//�f�o�b�O�p
//		if( GAME_SCENE == 2 ){
//			if( down_position_x <= 100.0f && down_position_y >= 400.0f ){
//				UI.stamina_x += 0.3f;
//			}
//			if( down_position_x <= 100.0f && ( down_position_y >= 200.0f && down_position_y <= 350.0f ) ){
//				UI.stamina_x -= 0.3f;
//			}
//		}
		
		return false;
	}
	@Override
	public boolean onDoubleTapEvent( MotionEvent event ){
		return false;
	}
	
		
	//�^�C�g���`��
	public void TitleMain( Canvas c ){
		title.DrawTitle( c, bmp[0] );
	}
		
	public void GameMain( Canvas c ){
		
		if( SCORE >= 9999999 ){
			SCORE = 9999999;
		}
		
		//��̐�[�̍��W������ɒ��߂��邽�߂̃��m
		float hdx = -10.0f, hdy = 0.0f;
		if( tongue.Angle >= -25.0f ){
			hdy = 10.0f;
		}else{
			hdx = -15.0f;
			hdy = -5.0f;
		}
		
		if( game_finish == false ){
			time_cnt -= 1;
			if( time_cnt <= 0 ){
				time_cnt = 0;
			}
		}
								
		//���̔w�i�̕`��
		background.DrawBackground( c, bmp[1], bmp[2], bmp[8], time_cnt );
		
		//��̔w�i�̕`��
		background.DrawBackground( c, bmp[39], time_cnt );
		
		//���̕`��
		background.DrawBackgroundMoon( c, bmp[41], bmp[42], bmp[43], time_cnt );
		
		//�W�����O���͗l��`��
		background.DrawBackground( c, bmp[40] );		
		
		//�}�̕`��
		background.DrawBackground( c, bmp[38] );
		
		//�ȉ��̃O���[�v�́A�������Update�֐��Q
		if( pause_flag != true ){
			enemy.Move( tongue.TONGUE_POSITION_X, tongue.TONGUE_POSITION_Y, tongue.tongue_scale_x,
					tongue.tongue_scale_y, tongue.Angle, hdx, hdy, ENEMY_LEVEL, finish_feverring, game_finish );

			int select = check_vector.CheckVector( fling_vector_x );
			tongue.TongueScaleChange( select );
			if( tongue.go_tongue_flag == true ){
				UI.stamina_x -= 0.0025f;
				if( sound_on_flag == true ){
					sp.PlaySE( se_soundID[0], 0 );
				}
			}

			if( game_finish == false ){
				enemy.CoughtFlag( ( ( TONGUE_WIDTH * tongue.tongue_scale_x ) + TONGUE_POSITION_X + hdx ),
						( ( ( TONGUE_WIDTH * tongue.tongue_scale_x ) * (float)Math.sin( Math.toRadians( (double)tongue.Angle ) ) + TONGUE_POSITION_Y ) + hdy ),
						10.0f,
						25.0f,
						ENEMY_LEVEL, select, tongue.getTongueScaleX() );
			}
			
		}		
				
		enemy.DrawEnemy( c, bmp[6], bmp[13], bmp[14], bmp[15], bmp[16], bmp[10], ENEMY_LEVEL );
		tongue.DrawTongue( c, bmp[5] );
		
		
		//���݂̓G�̐��𒲐����鏈��
		if( finish_feverring == false  && game_finish == false ){
			if( ( enemy.IsEnmeyAppearFlag( ENEMY_LEVEL, ENEMY_MIN_POINT ) ) == true ){
				enemy.ToEnemyAppearFlag( ENEMY_LEVEL );
			}
		}
		
				
		if( UI.getFever_x() >= 0.1f ){
			alpha_Ang += ( 0.1f * ( UI.getFever_x() * 8.0f ) );
			if( alpha_Ang >= 360.0f ){
				alpha_Ang = 0.0f;
			}
		}

		if( UI.getStamina_x() >= 0.5f ){
			alpha_Ang2 = 90.0f;
		}
		if( UI.getStamina_x() < 0.5f ){
			alpha_Ang2 += Math.abs( UI.getStamina_x() - 1.0f ) * 0.5f;
			if( alpha_Ang2 >= 360.0f ){
				alpha_Ang2 = 0.0f;
			}
		}
		
		//�J�����I���̕`��
		if( tongue.ready_tongue_flag == false ){
			if( Enemy.eatting_flag == true ){
				if( sound_on_flag == true ){
					sp.PlaySE( se_soundID[1], 1 );
				}
				charactor.DrawChara( c, bmp[3], bmp[17], bmp[18], bmp[19], bmp[20], UI.getFever_x(), (float)Math.sin( alpha_Ang ) );				
			}else{
				charactor.DrawChara( c, bmp[3], bmp[25], bmp[26], bmp[27], bmp[28], bmp[29], UI.getFever_x(), (float)Math.sin( alpha_Ang ) );
			}
		}else{
			charactor.DrawChara( c, bmp[3], bmp[4], bmp[21], bmp[22], UI.getFever_x(), (float)Math.sin( alpha_Ang ) );
		}	

		
		if( enemy.isJust_hit_on() == true ){
			hit_just_cnt += 1;
			if( hit_just_cnt >= 10 ){
				hit_just_cnt = 10;
			}
			if( hit_just_cnt >= 2 && hit_just_cnt <= 2 ){
				if( sound_on_flag == true ){
					sp.PlaySE( se_soundID[7], 7 );
				}
			}
			ui.DrawJustHitMark( c, bmp[46], enemy.getJust_hit_position_x(), enemy.getJust_hit_position_y() );
		}else{
			hit_just_cnt = 0;
		}
		

		if( UI.isJust_mark_flag() == true ){
			ui.DrawHitJ( c, bmp[45] );
		}

		if( UI.isCombo_mark_flag() == true ){
			ui.DrawHitC( c, bmp[45], bmp[30], CHARA_POSITION_X + 150.0f, CHARA_POSITION_Y - 80.0f , enemy.getCombo_cnt() );
			ui.DrawHitJ( c, bmp[45] );
		}

		//�e���V�����t�o�̖��\��
		if( now_feverring == false && finish_feverring == false ){
			if( UI.isHit_up_flag() == true ){
				hit_up_cnt += 1;
				if( hit_up_cnt >= 10 ){
					hit_up_cnt = 10;
				}
				ui.DrawHitUP( c, bmp[47] );
				if( hit_up_cnt >= 4 && hit_up_cnt <= 4 ){
					if( sound_on_flag == true ){
						sp.PlaySE( se_soundID[6], 6 );
					}
				}
			}else{
				hit_up_cnt = 0;
			}
		}

		
		ui.DrawTime( c, bmp[12], bmp[23], bmp[24], now_feverring, (float)Math.sin( alpha_Ang2 ) );
		ui.DrawScore( c,  bmp[11], bmp[12], SCORE );
		ui.DrawFeverGage( c, bmp[23], bmp[31], now_feverring );
		
		
		if( fever_mode == true ){
			if( sound_on_flag == true ){
				sp.PauseBGM( "playing1.ogg", 3 );
				sp.SetVolumeBGM( "chanse.ogg", 4, BGM_MAX_VOLUME, BGM_MAX_VOLUME );
				sp.RePlayBGM( "chanse.ogg", 4 );
				vol_control = 0.0f;
			}
			now_feverring = true;
			ENEMY_LEVEL = 25;
			enemy.Init( ENEMY_LEVEL );
			fever_mode = false;
		}
		

		if( fever_mode == false ){
			vol_control += 0.001f;
			if( vol_control >= BGM_MAX_VOLUME ){
				vol_control = BGM_MAX_VOLUME;
			}
		}
		

		if( now_feverring == true ){
			ui.DrawFeverMark( c, bmp[44], bmp[7] );
			//�����t�B�[�o�[���ɓG��H�ׂ����Ă��܂�����A�G�̐ݒ����ԍŏ��̏�Ԃɖ߂�
			if( enemy.IsAteFlag( ENEMY_LEVEL ) ){
				ENEMY_LEVEL = ENEMY_BASE_POINT;
				STAGE_LEVEL = 1;
				enemy.Init( ENEMY_LEVEL );
				finish_feverring = false;
				if( sound_on_flag == true ){
					sp.PauseBGM( "chanse.ogg", 4 );
					sp.SetVolumeBGM( "playing1.ogg", 3, vol_control, vol_control );
					sp.PlayBGM( "playing1.ogg", 3 );
				}
			}			
		}
		
		if( finish_feverring == true ){
			if( enemy.EnemyGetOut( ENEMY_LEVEL ) == true ){
				if( sound_on_flag == true ){
					sp.PauseBGM( "chanse.ogg", 4 );
					sp.SetVolumeBGM( "playing1.ogg", 3, vol_control, vol_control );					
					sp.PlayBGM( "playing1.ogg", 3 );
				}
				UI.setHit_up_flag( false );
				UI.setHit_up_cnt( 0.0f );
				ENEMY_LEVEL = ENEMY_BASE_POINT;
				STAGE_LEVEL = 1;
				enemy.Init( ENEMY_LEVEL );
				Enemy.setJust_hit_on( false );
				finish_feverring = false;
			}
		}
		
		if( pause_flag == true ){
			background.DrawPauseground( c, bmp[7] );
		}
		
		//�X�^�~�i�Q�[�W�������Ȃ�΁A�Q�[���I�����ă��U���g�ֈڍs����
		if( UI.stamina_x <= 0.02f || SCORE == 9999999 ){
			//�܂��Q�[�����I���������Ƃ������t���O�𗧂Ă�
			game_finish = true;
			
			if( sound_on_flag == true ){
				vol_control -= 0.005f;
				if( vol_control <= 0.0f ){
					vol_control = 0.0f;
				}
				sp.SetVolumeBGM( "playing1.ogg", 3, vol_control, vol_control );
				sp.SetVolumeBGM( "flyflocking.ogg", 0,  vol_control, vol_control );
			}
			
			//��ʂɎc���Ă���G����ʊO�֒ǂ����A���̌�ȉ��̏������s��
			if( enemy.EnemyGetOut( ENEMY_LEVEL ) == true ){
				if( sound_on_flag == true ){
					sp.StopBGM( "flyflocking.ogg", 0 );
				}
				//���U���g�V�[����
				GAME_SCENE = 3;
			}
		}
			
	}
	
	//�X�R�A�Z�[�u
	public void Save( int SCORE ){
		String[] s = new String[5]; 
		int[] score = new int[5];
		OutputStream out = null;
		BufferedWriter bw = null;
		for( int i = 0; i < 5; i++ ){
			score[i] = LoadSavedScore( i );
		}
		for( int i = 4; i > 0; i-- ){
			if( score[i] > score[i-1] ){
				int w;
				w = score[i];
				score[i] = score[i-1];
				score[i-1] = w;
			}
		}
		//����̃v���C�̃X�R�A�Ɗ����̃X�R�A���ׂāA�K�v������Ε��ёւ���
		for( int j = 0; j < 5; j++ ){
			if( SCORE >= score[j] ){
				switch( j ){
				case 0:
					score[j+4] = score[j+3];
					score[j+3] = score[j+2];
					score[j+2] = score[j+1];
					score[j+1] = score[j];
					score[j] = SCORE;					
					break;
				case 1:
					score[j+3] = score[j+2];
					score[j+2] = score[j+1];
					score[j+1] = score[j];
					score[j] = SCORE;					
					break;
				case 2:
					score[j+2] = score[j+1];
					score[j+1] = score[j];
					score[j] = SCORE;					
					break;
				case 3:
					score[j+1] = score[j];
					score[j] = SCORE;
					break;
				case 4:
					score[j] = SCORE;
					break;
				default:
					break;
				}
				break;				
			}
		}
		try{
            out = getContext().openFileOutput( "saved_score", Context.MODE_PRIVATE );   
            bw = new BufferedWriter( new OutputStreamWriter( out ) );
            for( int k = 0; k < 5; k++ ){
            	s[k] = String.valueOf( score[k] );
            	//��������ł�����s
            	bw.append( s[k] + "\n");
            }
            //�N���[�Y����(�d�v�I�Y��Ȃ����ƁI)
            bw.flush();
            bw.close();   
			out.close();
		}catch( Exception e ){
		}
				
	}

	//�X�R�A���[�h
	public int Load( int SCORE ){
		String[] w = new String[5];
		int[] s = new int[5];
		int i = 0;
		FileInputStream file = null;
		BufferedReader in = null;
		try{
			file = getContext().openFileInput( "saved_score" );
			in = new BufferedReader( new InputStreamReader( file ) );
			while( ( w[i] = in.readLine() ) != null ){
				i += 1;
				if( i >= 5 ){
					break;
				}
			}
			file.close();
			in.close();
			for( int j = 0; j < 5; j++ ){
				s[i] = Integer.parseInt( w[j] );
				if( SCORE >= s[i] ){
					return SCORE;
				}
			}
			return -1;
		}catch( Exception e ){
			return -1;
		}
	}
			
	//���ʂ��w�肵�ẴX�R�A���[�h
	public int LoadSavedScore( int k ){
		int score = 0;
		String[] w = new String[5];
		int[] s = new int[5];
		FileInputStream file = null;
		BufferedReader in = null;
		try{
			file = getContext().openFileInput( "saved_score" );
			in = new BufferedReader( new InputStreamReader( file ) );
			for( int i = 0; i < 5; i++ ){
				w[i] = in.readLine();
			}
			file.close();
			in.close();
			for( int j = 0; j < 5; j++ ){
				s[j] = Integer.parseInt( w[j] );
			}
		}catch( Exception e ){
		}
		score = s[k];
		
		return score;
		
	}

	//�X�R�A���[�h���Ă���̕`��
	public void LoadSavedScore( Canvas c ){
		String[] w = new String[5];
		int[] s = new int[5];
		FileInputStream file = null;
		BufferedReader in = null;
		try{
			file = getContext().openFileInput( "saved_score" );
			in = new BufferedReader( new InputStreamReader( file ) );
			for( int i = 0; i < 5; i++ ){
				w[i] = in.readLine();
			}
			file.close();
			in.close();
			for( int j = 0; j < 5; j++ ){
				s[j] = Integer.parseInt( w[j] );
			}
		}catch( Exception e ){
		}
		
		Paint p = new Paint();
		p.setTextSize( 30 );
		for( int k = 0; k < 5; k++ ){
			c.drawText( "s : " + s[k], 200, 100 + k * 30, p );
		}
	}
	
}
