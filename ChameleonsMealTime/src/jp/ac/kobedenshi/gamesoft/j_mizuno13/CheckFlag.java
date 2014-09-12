package jp.ac.kobedenshi.gamesoft.j_mizuno13;

public class CheckFlag {
	
	private final static int SHORT_LENGTH = 1; 
	private final static int MIDIUM_LENGTH = 2; 	
	private final static int LONG_LENGTH = 3;
	private final static int VERYLONG_LENGTH = 4;	
	private final static float FLING_DISTANCE_VERYSHORT = 2000.0f;
	private final static float FLING_DISTANCE_SHORT = 4000.0f;
	private final static float FLING_DISTANCE_MIDIUM = 7000.0f;
	private final static float FLING_DISTANCE_LONG = 10000.0f;	
	
	public CheckFlag(){
		
	}
	
	//フリックの強さによって、舌の描画の長さを決める数値を返す
	public int CheckVector( float fling_vector_x ){
		int select = SHORT_LENGTH;
		if( fling_vector_x < FLING_DISTANCE_VERYSHORT ){
			select = SHORT_LENGTH;
		}
		if( fling_vector_x >= FLING_DISTANCE_VERYSHORT && fling_vector_x < FLING_DISTANCE_SHORT ){
			select = SHORT_LENGTH;
		}		
		if( fling_vector_x >= FLING_DISTANCE_SHORT && fling_vector_x < FLING_DISTANCE_MIDIUM ){
			select = MIDIUM_LENGTH;
		}
		if( fling_vector_x >= FLING_DISTANCE_MIDIUM && fling_vector_x < FLING_DISTANCE_LONG ){
			select = LONG_LENGTH;
		}
		if( fling_vector_x >= FLING_DISTANCE_LONG ){
			select = VERYLONG_LENGTH;
		}
		return select;
	}

}
