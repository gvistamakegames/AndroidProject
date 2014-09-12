package com.example.lifecicletestactivity;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

	StringBuilder builder = new StringBuilder();
	TextView textView;
	
	private void log( String _text ){
		
		Log.d( "LifeCicleTest", _text );
		builder.append( _text );
		builder.append( "\n" );
		textView.setText( builder.toString() );
		
	}
	
	@Override
	public void onCreate( Bundle _savedInstanceState ) {
		super.onCreate( _savedInstanceState );
		textView = new TextView( this );
		textView.setText( builder.toString() );
		setContentView( textView );
		setContentView( R.layout.activity_main );
		log( "Created" );
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		log( "Resumed" );
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		log( "Paused" );
		if( isFinishing() ){
			log( "Finishing" );
		}
	}

}
