package com.youming.elasticsearchrestclient.service;

import java.io.Console;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.action.DocWriteResponse;

//elasticsearch client的工具类
public class ElasticService {
	private static RestClient restClient;
	private static RestHighLevelClient restHighLevelClient;
	static {
		// Http Basic Auth
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "eck2019@86h96#AC"));

		RestClientBuilder builder = RestClient.builder(new HttpHost("elastic.youming.com", 85))
				.setHttpClientConfigCallback(new HttpClientConfigCallback() {
					@Override
					public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
						return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					}
				});
		builder.setFailureListener(new RestClient.FailureListener() {
			@Override
			public void onFailure(Node node) {
				System.out.println("连接失败:" + node.getHost().toString());
			}
		});

		restClient = builder.build();
		restHighLevelClient = new RestHighLevelClient(builder);
	}

	/**
	 * client必须显式的关闭
	 * 
	 * @throws IOException
	 */
	public static void closeClient() {
		try {
			restClient.close();
			restHighLevelClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 创建索引，并设置Mapping
	 */
	public static void createIndexMapping() throws IOException {
		CreateIndexRequest request = new CreateIndexRequest("twitter");
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		{
			builder.startObject("properties");
			{
				builder.startObject("projectId");
				{
					builder.field("type", "keyword");
				}
				builder.endObject();

				builder.startObject("title");
				{
					builder.field("type", "text");
					builder.field("analyzer", "ik_smart");
				}
				builder.endObject();

				builder.startObject("content");
				{
					builder.field("type", "text");
					builder.field("analyzer", "ik_smart");
				}
				builder.endObject();

				builder.startObject("createTime");
				{
					builder.field("type", "date");
					builder.field("format", "yyyy-MM-dd HH:mm:ss.SSS");
				}
				builder.endObject();
			}
			builder.endObject();
		}
		builder.endObject();
		request.mapping(builder);			

		CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
		if (!createIndexResponse.isAcknowledged() || !createIndexResponse.isShardsAcknowledged()) { // 指示是否所有节点都已确认请求,指示在超时之前是否为索引中的每个分片启动了必需数量的分片副本
			System.out.println("error");
		}
	}
	
	
	public static void IndexData() throws IOException
    {
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		{
		    builder.field("projectId", "12154545");
		    builder.field("title", "trying out Elasticsearch");
		    builder.field("content", "trying out Elasticsearch");
		    builder.timeField("createTime", "1990-01-01 10:00:01.234");
		}
		builder.endObject();
		//IndexRequest indexRequest = new IndexRequest("twitter").id("1").source(builder);		//指定id
		IndexRequest indexRequest = new IndexRequest("twitter").source(builder);		//不指定id
		IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		
		System.out.println("Request:" + indexRequest.toString());		//打印请求数据
		
		String index = indexResponse.getIndex();
		String id = indexResponse.getId();
		if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
		    //doc是第一次
		} else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
		    //doc是更新
		}
		ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
		if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
		    
		}
		if (shardInfo.getFailed() > 0) {
		    for (ReplicationResponse.ShardInfo.Failure failure :
		            shardInfo.getFailures()) {
		        String reason = failure.reason();
		        System.out.println("reason:" + reason);
		    }
		}
    }
	
	/**
	 * query match 查询
	 * @param searchStr 查询字符串
	 * @param fromIndex 记录起始数
	 * @param pageSize 返回的记录数
	 * @throws IOException 
	 * */
	public static void QueryMatch(String searchStr, int fromIndex, int pageSize) throws IOException {
		SearchRequest searchRequest = new SearchRequest("baobiao_ik");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		searchRequest.source(sourceBuilder);
		
		sourceBuilder.query(QueryBuilders.matchQuery("title", searchStr)); 
		sourceBuilder.from(fromIndex); 
		sourceBuilder.size(pageSize); 
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); 		//超时设置
		
		String[] includeFields = new String[] {"title", "projectId","createTime"};		//_source过滤
		String[] excludeFields = null;
		sourceBuilder.fetchSource(includeFields, excludeFields);
		//高亮设定
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.preTags("<font color=red>").postTags("</font>").encoder("html");		//设置前置和后置tag，编码
		HighlightBuilder.Field highlightTitle =
		        new HighlightBuilder.Field("title"); 
		//highlightTitle.highlighterType("unified");
		
		highlightBuilder.field(highlightTitle);  
		sourceBuilder.highlighter(highlightBuilder);
		
		System.out.println("Request:" + searchRequest.toString());		//打印请求数据
		
		SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		
		RestStatus status = searchResponse.status();
		TimeValue took = searchResponse.getTook();
		Boolean terminatedEarly = searchResponse.isTerminatedEarly();
		boolean timedOut = searchResponse.isTimedOut();
		
		SearchHits hits = searchResponse.getHits();
		
		TotalHits totalHits = hits.getTotalHits();
		// the total number of hits, must be interpreted in the context of totalHits.relation
		long numHits = totalHits.value;
		// whether the number of hits is accurate (EQUAL_TO) or a lower bound of the total (GREATER_THAN_OR_EQUAL_TO)
		TotalHits.Relation relation = totalHits.relation;
		float maxScore = hits.getMaxScore();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
		    // do something with the SearchHit
			var score = hit.getScore();      //得分
            var projectId = hit.getSourceAsMap().get("projectId");
            var createTime = hit.getSourceAsMap().get("createTime");
            var title = hit.getHighlightFields().get("title").fragments()[0];       //高亮数据
            System.out.printf("projectId:%s;createTime:%s;score:%s;title:%s\r\n", projectId,createTime,score,title);
		}
	}
}
