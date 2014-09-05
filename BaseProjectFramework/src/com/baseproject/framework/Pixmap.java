package com.baseproject.framework;

import com.baseproject.framework.Graphics.PixmapFormat;

// �s�N�Z���}�b�v�C���^�[�t�F�[�X
public interface Pixmap {

	public int getWidth();
	
	public int getHeight();
	
	public PixmapFormat getFormat();
	
	public void dispose();
	
}