package Class;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;

import com.baseproject.framework.Interface.FileIO;

//
public class AndroidFileIO implements FileIO  {

	// �ϐ��錾
	AssetManager assets;
	String externalStoragePath;
	
	//
	public AndroidFileIO( AssetManager _assets ){
		
		this.assets = _assets;
		this .externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
		
	}
	
	//
	@Override
	public InputStream readAsset( String _fileName ) throws IOException {
		
		return assets.open( _fileName );
		
	}
	
	//
	@Override
	public InputStream readFile( String _fileName ) throws IOException {
		
		return new FileInputStream( externalStoragePath + _fileName );
		
	}
	
	//
	@Override
	public OutputStream writeFile( String _fileName ) throws IOException{
		
		return new FileOutputStream( externalStoragePath + _fileName );
		
	}
	
}
