package com.youming.demoosapi.clipboard;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Reader;

public class ClipboardWin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello ClipboardWin");
		ClipboardWin.getContentString();
		ClipboardWin.getContentImage();
		ClipboardWin.getContentMix();
	}
	
	/**
	 * 单纯字符串
	 * @throws IOException 
	 * @throws UnsupportedFlavorException 
	 * */
	public static void getContentString() {
		Clipboard sysClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		Transferable clipTransferable = sysClipboard.getContents(null);
		if (!clipTransferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			System.out.println("Clipboard format is not stringFlavor");
			return;
		}
        // Get data stored in the clipboard that is in the form of a string (text)
		String content = null;
		try {
			content = sysClipboard.getData(DataFlavor.stringFlavor).toString();
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        System.out.println("Clipboard String:" + content);
	}
	
	/**
	 * 获取图片内容
	 * */
	public static void getContentImage() {
		Clipboard sysClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		Transferable clipTransferable = sysClipboard.getContents(null);
		if (!clipTransferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
			System.out.println("Clipboard format is not imageFlavor");
			return;
		}
        // Get data stored in the clipboard that is in the form of a string (text)
		Image image = null; 
		try {
			image = (Image)sysClipboard.getData(DataFlavor.imageFlavor);
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Clipboard image:" + image.toString());
	}
	
	/**
	 * 从剪贴板获取混合内容
	 * */
	public static void getContentMix() {
		Clipboard sysClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();		//获得系统剪贴板(注:某些系统的剪贴板带有互斥锁，当有进程在使用剪贴板时，别的进程无法获得)
		Transferable clipTransferable = sysClipboard.getContents(null);
		DataFlavor[] dataFlavorList = clipTransferable.getTransferDataFlavors();
		int wholeLength = 0;
		for (DataFlavor dataFlavor:dataFlavorList) {
			String subType = dataFlavor.getSubType();
			System.out.println("content type:" + subType);
			if (subType.equals("plain")) {
				//Reader reader = dataFlavor.getReaderForText(clipTf);
			}
			/*
			if (data.getSubType().equals("rtf")) {
				Reader reader = data.getReaderForText(clipTf);
				OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("d:\\test.rtf"));
				char[] c = new char[1024];
				int leng = -1;
				while ((leng = reader.read(c)) != -1) {
					osw.write(c, wholeLength, leng);
				}
				osw.flush();
				osw.close();
			}
			*/
		}
	}
}
