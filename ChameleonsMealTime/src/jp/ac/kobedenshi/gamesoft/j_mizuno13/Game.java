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
		//getWindow().clearFlags( WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN );  //ウインドウのオプション設定
		getWindow().setFormat( PixelFormat.TRANSLUCENT );
		requestWindowFeature( Window.FEATURE_NO_TITLE );    //ウインドウのオプション設定
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN );		//ウインドウの縁をとる
		setContentView( new GameDraw( this ) );  //まず表示させる場所を指定
	}
	
	@Override
	//ホームボタンを押した or Activityが切り替わるタイミングで呼ばれる。
	public void onUserLeaveHint(){
		finish();
	}

	@Override
	//戻るボタンを押した時
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
//		// TODO 自動生成されたメソッド・スタブ
//		Toast.makeText(this, "ストップ！！" , Toast.LENGTH_SHORT).show();
//		super.onStop();
//	}
//
//	@Override
//	//復帰時
//	protected void onResume() {
//		// TODO 自動生成されたメソッド・スタブ
//		Toast.makeText(getApplicationContext(), "リジュームですね" , Toast.LENGTH_SHORT).show();
//		super.onResume();
//	}
//
	@Override
	//ホームボタンを押した時、戻るボタンを押した時	
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
//		Toast.makeText(getApplicationContext(), "ポーズですよ" , Toast.LENGTH_SHORT).show();
		super.onPause();
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
//		Toast.makeText(this, "デストラーデ・・・" , Toast.LENGTH_SHORT).show();
		super.onDestroy();
		finish();
	}	
	
}
