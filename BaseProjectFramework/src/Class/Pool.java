package Class;

import java.util.ArrayList;
import java.util.List;

//
public class Pool<T> {

	//
	public interface PoolObjectFactory<T>{
		
		public T createObject();
		
	}
	
	// �ϐ��錾
	private final List<T> freeObjects;
	private final PoolObjectFactory<T> factory;
	private final int maxSize;
	
	//
	public Pool( PoolObjectFactory<T> _factory, int _maxSize ){
		
		this.factory = _factory;
		this.maxSize = _maxSize;
		this.freeObjects = new ArrayList<T>( _maxSize );
		
	}
	
	//
	public T newObject(){
		
		T object = null;
		
		if( freeObjects.size() == 0 ){
			
			object = factory.createObject();
			
		}else{
			
			object = freeObjects.remove( freeObjects.size() - 1 );
			
		}
		
		return object;
		
	}
	
	//
	public void free( T _object ){
		
		if( freeObjects.size() < maxSize ){
			
			freeObjects.add( _object );
			
		}
		
	}
	
}
