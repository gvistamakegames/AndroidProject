package com.android.helloworld;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

	Button button;
	int touchCount;
	
    @Override
    // コンストラクタの代わりとして
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button = new Button( this );
        button.setText( "Touch Me!" );
        button.setOnClickListener( this );
        setContentView( button );
        //setContentView(R.layout.activity_main);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
	
    public void onClick( View v ){
    	touchCount++;
    	button.setText( "Touched Me　" + touchCount + "　time(s)" );
    }
    
}
