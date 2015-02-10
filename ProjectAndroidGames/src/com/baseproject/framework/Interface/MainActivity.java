package com.baseproject.framework.Interface;

// インポート
import com.baseproject.framework.Interface.Screen;

import AddClass.LoadingScreen;
import Class.AndroidGame;
import com.baseproject.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

// メインアクティビィティ
public class MainActivity extends AndroidGame {
	
	@Override
	public Screen getStartScreen(){
		
		return new LoadingScreen( this );
		
	}
	
}