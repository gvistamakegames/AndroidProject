package com.baseproject.framework.Interface;

// �C���|�[�g
import com.baseproject.framework.Interface.Screen;

import AddClass.LoadingScreen;
import Class.AndroidGame;
import com.baseproject.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

// ���C���A�N�e�B�r�B�e�B
public class MainActivity extends AndroidGame {
	
	@Override
	public Screen getStartScreen(){
		
		return new LoadingScreen( this );
		
	}
	
}