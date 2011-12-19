package com.andrios.pregnancyjournal.Models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SerialBitmap implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5893840524583053858L;
	public Bitmap bitmap;

    // TODO: Finish this constructor
    public SerialBitmap(Bitmap bitmap) {
        // Take your existing call to BitmapFactory and put it here
        this.bitmap = bitmap;
    }
    
    SerialBitmap(){
    	this.bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);;
    }

    // Converts the Bitmap into a byte array for serialization
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
    	System.out.println("Write Object");
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    // Deserializes a byte array representing the Bitmap and decodes it
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
    	System.out.println("Read Object");
    	ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while((b = in.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}

