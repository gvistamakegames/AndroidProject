package com.baseproject.framework.Interface;

// �C���|�[�g
import com.baseproject.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

// ���C���A�N�e�B�r�B�e�B
public class MainActivity extends Activity {

	@Override
	protected void onCreate( Bundle _savedInstanceState ) {
		
		super.onCreate( _savedInstanceState );
		setContentView( R.layout.activity_main );
		
	}

	@Override
	public boolean onCreateOptionsMenu( Menu _menu ) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.main, _menu );
		return true;
		
	}

}