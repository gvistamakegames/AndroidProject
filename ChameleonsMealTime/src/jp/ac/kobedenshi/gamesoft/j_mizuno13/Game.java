package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.view.KeyEvent;
import android.view.Window;
//import android.view.WindowManager;
import android.view.WindowManager;
//import android.widget.Toast;

public class Game extends Activity{
	@Override
	public void onCreate( Bundle bundle ){
		super.onCreate( bundle );
		//getWindow().clearFlags( WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN );  //�E�C���h�E�̃I�v�V�����ݒ�
		getWindow().setFormat( PixelFormat.TRANSLUCENT );
		requestWindowFeature( Window.FEATURE_NO_TITLE );    //�E�C���h�E�̃I�v�V�����ݒ�
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN );		//�E�C���h�E�̉����Ƃ�
		setContentView( new GameDraw( this ) );  //�܂��\��������ꏊ���w��
	}
	
	@Override
	//�z�[���{�^���������� or Activity���؂�ւ��^�C�~���O�ŌĂ΂��B
	public void onUserLeaveHint(){
		finish();
	}

	@Override
	//�߂�{�^������������
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			finish();
			return true;
		}
		return false;
	}

//	@Override
//	protected void onStop() {
//		// TODO �����������ꂽ���\�b�h�E�X�^�u
//		Toast.makeText(this, "�X�g�b�v�I�I" , Toast.LENGTH_SHORT).show();
//		super.onStop();
//	}
//
//	@Override
//	//���A��
//	protected void onResume() {
//		// TODO �����������ꂽ���\�b�h�E�X�^�u
//		Toast.makeText(getApplicationContext(), "���W���[���ł���" , Toast.LENGTH_SHORT).show();
//		super.onResume();
//	}
//
	@Override
	//�z�[���{�^�������������A�߂�{�^������������	
	protected void onPause() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
//		Toast.makeText(getApplicationContext(), "�|�[�Y�ł���" , Toast.LENGTH_SHORT).show();
		super.onPause();
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
//		Toast.makeText(this, "�f�X�g���[�f�E�E�E" , Toast.LENGTH_SHORT).show();
		super.onDestroy();
		finish();
	}	
	
}
