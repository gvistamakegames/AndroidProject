package AddClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.baseproject.framework.Interface.FileIO;

public class Settings {

	public static boolean soundEnabled = true;
	public static int[] highscores = new int[]{ 100, 80, 50, 30, 10 };
	
	//
	public static void load( FileIO _files ){
		
		BufferedReader in = null;
		
		try{
			
			//※読み込むファイル名は現在不定
			in = new BufferedReader( new InputStreamReader( _files.readFile( ".file" ) ) );
			soundEnabled = Boolean.parseBoolean( in.readLine() );

			//※5回回すのはランキングが5番までなので(こちらも不定)
			for( int i = 0; i < 5; i++ ){
				highscores[i] = Integer.parseInt( in.readLine() );
			}
			
		}catch( IOException e ){
			
			// デフォルト設定があるのでエラーは無視
			
		}catch( NumberFormatException e ){
			
			// デフォルト設定があるのでエラーは無視
			
		}finally{
			
			try{
				
				if( in != null ){
					
					in.close();
					
				}
				
			}catch( IOException e ){
							
			}
			
		}
		
	}
	
	//
	public static void save( FileIO _files ){
		
		BufferedWriter out = null;
		
		try{

			//※読み込むファイル名は現在不定			
			out = new BufferedWriter( new OutputStreamWriter( _files.writeFile( ".file" ) ) );
			
			out.write( Boolean.toString( soundEnabled ) );
			
			for( int i = 0; i < 5; i++ ){
				
				out.write( Integer.toString( highscores[i] ) );
				
			}
			
		}catch( IOException e ){
			
		}finally{
			
			try{
				
				if( out != null ){
				
					out.close();
					
				}
				
			}catch( IOException e ){
				
			}
			
		}
		
		
	}
	
	public static void addScore( int _score ){

		// スコア入れ替え処理		
		for( int i = 0; i < 5; i++ ){
			
			if( highscores[i] < _score ){
				
				for( int j = 5 - 1; j > i; j-- ){
					
					highscores[j] = highscores[j-1];
					
				}
				
				highscores[i] = _score;
				
				break;
				
			}
			
		}
		
	}
	
}
