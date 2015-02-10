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
			
			//���ǂݍ��ރt�@�C�����͌��ݕs��
			in = new BufferedReader( new InputStreamReader( _files.readFile( ".file" ) ) );
			soundEnabled = Boolean.parseBoolean( in.readLine() );

			//��5��񂷂̂̓����L���O��5�Ԃ܂łȂ̂�(��������s��)
			for( int i = 0; i < 5; i++ ){
				highscores[i] = Integer.parseInt( in.readLine() );
			}
			
		}catch( IOException e ){
			
			// �f�t�H���g�ݒ肪����̂ŃG���[�͖���
			
		}catch( NumberFormatException e ){
			
			// �f�t�H���g�ݒ肪����̂ŃG���[�͖���
			
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

			//���ǂݍ��ރt�@�C�����͌��ݕs��			
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

		// �X�R�A����ւ�����		
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