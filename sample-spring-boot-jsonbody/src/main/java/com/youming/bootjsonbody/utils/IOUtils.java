package com.youming.bootjsonbody.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;

public class IOUtils {

	public static byte[] toByteArray(ServletInputStream servletInputStream) {
		byte[] b = null;
		try {
			b = new byte[servletInputStream.available()];

			servletInputStream.read(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	public static String toString(byte[] source,String characterEncoding) {
		String str = null;
		try {
			str = new String(source, 0, source.length, characterEncoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
