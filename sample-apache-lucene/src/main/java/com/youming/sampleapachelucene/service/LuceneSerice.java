package com.youming.sampleapachelucene.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;

/**
 * Lucene服务
 */
public class LuceneSerice {

	/**
	 * 新建内存索引
	 * 
	 * @throws IOException
	 */
	public static void createMemoryIndex() throws IOException {
		Directory memoryIndex = MMapDirectory.open(Paths.get("F:/luceneMemoryIndex")); // 内存级的缓存，但是仍然存储在硬盘上的路径

		StandardAnalyzer analyzer = new StandardAnalyzer(); // 标准分词器
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		IndexWriter writter = new IndexWriter(memoryIndex, indexWriterConfig);

		Document document = new Document();
		document.add(new StringField("projectId", "95598", Store.YES)); // 不用于分词进行标记的字段用StringField
		document.add(new TextField("title", "我是标题", Store.YES));
		document.add(new TextField("contentbody", "我是内容", Store.YES));

		writter.addDocument(document);
		writter.close(); // 关闭写入
	}

	/**
	 * 查找
	 * 
	 * @throws Exception
	 */
	public static void query(String queryStr) throws Exception {
		Directory memoryIndex = MMapDirectory.open(Paths.get("F:/luceneMemoryIndex")); // 内存级的缓存，但是仍然存储在硬盘上的路径
		
		StandardAnalyzer analyzer = new StandardAnalyzer(); // 分词器
		Query query = new QueryParser("title", analyzer).parse(queryStr);

		// 3. search
		int hitsPerPage = 10;
		IndexReader indexReader = DirectoryReader.open(memoryIndex);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		TopDocs docs = indexSearcher.search(query, hitsPerPage);
		ScoreDoc[] hits = docs.scoreDocs;

		// 4. display results
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = indexSearcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("projectId") + "\t" + d.get("title"));
		}
	}

}
