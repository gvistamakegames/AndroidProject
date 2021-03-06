package jp.ac.kobedenshi.gamesoft.j_mizuno13;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

//import java.io.InputStream;
//import java.io.OutputStream;
//
//import android.content.Context;

public class File {
	
	private final static int maxScoreNumber = 5;
	private final static int minScoreNumber = 1;
	
	Context context;

	public File( Context _context ) {
		
		context = _context;
	
	}
		
	public void saveData() {
		
		
		
	}
	
	public void loadData() {
		
		
		
	}
	
	public void saveScore( int _nowScore ) {
		
		// 変数宣言
		String[] sWord = new String[maxScoreNumber];
		int[] score = new int[maxScoreNumber];
		int i = 0;
		OutputStream out = null;
		BufferedWriter bw = null;
		
		// まず、既存のスコアを全て読み込む
		for( i = 0; i < maxScoreNumber; i++ ){
			
			// loadScoreの引数は1から昇順で指定する
			score[i] = loadScore( i + 1 );
			
		}
		
		// 読み込んだ既存のスコアを降順で並び替える
		for( i = maxScoreNumber - 1; i > 0; i-- ){
			
			if( score[i] > score[i-1] ){
				
				int w;
				w = score[i-1];
				score[i-1] = score[i];
				score[i] = w;
				
			}
			
		}
		
		// もしスコアが更新されていれば、それを反映させる
		for( i = 0; i < maxScoreNumber; i++ ){
			
			if( _nowScore >= score[i] ){
				
				int lastScores = maxScoreNumber - i;
				
				for( int j = 0; j < lastScores - 1; j++ ){
					score[maxScoreNumber - ( 1 + j )] = score[maxScoreNumber - ( 2 + j )];				
				}

				score[i] = _nowScore;		
				
				break;
				
			}
			
		}
		
		
	}
	
	public int loadScore( int _scoreNumber ) {
		
		// 変数宣言
		String[] sWord = new String[maxScoreNumber];
		int[] score = new int[5];
		int i = 0;
		int scoreNumber = _scoreNumber - 1;
		
		FileInputStream file = null;
		BufferedReader in = null;
		
		try{
			
			file = context.openFileInput( "saved_score" );
			in = new BufferedReader( new InputStreamReader( file ) );
			
			while( ( sWord[i] = in.readLine() ) != null ){
				i++;
				if( i >= maxScoreNumber ){
					break;
				}
			}
			
			file.close();
			in.close();
			
			for( i = 0; i < maxScoreNumber; i++ ){
				
				score[i] = Integer.parseInt( sWord[i] );
				
			}
			
			return score[scoreNumber];
			
		}catch( Exception e ){
			
			return -1;
			
		}
				
	}
	

}
