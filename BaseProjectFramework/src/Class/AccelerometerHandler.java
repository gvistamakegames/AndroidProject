package Class;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

//
public class AccelerometerHandler implements SensorEventListener{

	// �ϐ��錾
	float accelX;
	float accelY;
	float accelZ;
	
	//
	public AccelerometerHandler( Context _context ){
		
		SensorManager manager = (SensorManager)_context.getSystemService( Context.SENSOR_SERVICE );
		if( manager.getSensorList( Sensor.TYPE_ACCELEROMETER ).size() != 0 ){
			
			Sensor accelerometer = manager.getSensorList( Sensor.TYPE_ACCELEROMETER ).get( 0 );
			
			manager.registerListener( this, accelerometer, SensorManager.SENSOR_DELAY_GAME );
			
		}
		
	}
	
	//
	@Override
	public void onAccuracyChanged( Sensor _sensor, int _accuracy ){
		
		// �������ł͉������s���Ȃ�
		
	}
	
	//
	@Override
	public void onSensorChanged( SensorEvent _event ){
		
		accelX = _event.values[0];
		accelY = _event.values[1];
		accelZ = _event.values[2];
		
	}
	
	// �Q�b�^�[
	public float getAccelX(){
	
		return accelX;
		
	}
	
	// �Q�b�^�[
	public float getAccelY(){
		
		return accelY;
		
	}
	
	// �Q�b�^�[
	public float getAccelZ(){
		
		return accelZ;
		
	}
	
}