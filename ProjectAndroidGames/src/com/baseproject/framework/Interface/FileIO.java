package com.baseproject.framework.Interface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//　ファイルI/Oインターフェース
public interface FileIO {

	//
	public InputStream readAsset( String _fileName ) throws IOException;
	
	//
	public InputStream readFile( String _fileName ) throws IOException;
	
	//
	public OutputStream writeFile( String _fileName ) throws IOException;
	
}