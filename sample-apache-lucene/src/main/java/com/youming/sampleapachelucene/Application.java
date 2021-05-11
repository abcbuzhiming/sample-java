package com.youming.sampleapachelucene;

import java.io.IOException;

import com.youming.sampleapachelucene.service.LuceneSerice;


public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello sample-apache-lucene");
		try {
			//LuceneSerice.createMemoryIndex();
			LuceneSerice.query("标题");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
