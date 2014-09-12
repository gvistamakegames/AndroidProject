package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import android.content.Context;
import android.widget.Toast;

public class ShowMessage {
	
	String msg;
	
	public ShowMessage( String s ){
		msg = s;
	}
	
	protected void ShowMsg( Context context ){
		Toast toast = Toast.makeText( context, msg, Toast.LENGTH_SHORT );
		toast.show();
	}

}
