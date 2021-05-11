package com.youming.elasticsearchrestclient;

import java.io.IOException;

import org.elasticsearch.client.ElasticsearchClient;

import com.youming.elasticsearchrestclient.service.ElasticService;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello Elasticsearch Java REST Client");
		
		try {
			//ElasticService.createIndexMapping();
			//ElasticService.IndexData();
			ElasticService.QueryMatch("环保除尘", 0, 20);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ElasticService.closeClient();
	}

}
