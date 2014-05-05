package com.rollingcode.shootfastgame;

import java.io.Serializable;

import android.graphics.Bitmap;

public class SerializableBitmap implements Serializable {

	private static final long serialVersionUID = 1L;

	private Bitmap bitmap;
	
	public SerializableBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
}
