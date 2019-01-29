package com.qbk.redisearch;

import io.redisearch.Document;
import io.redisearch.Query;
import io.redisearch.Schema;
import io.redisearch.SearchResult;
import io.redisearch.client.Client;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;


public class RedisSearchClient extends Client {

	/**
	 * 构造函数
	 */
	public RedisSearchClient() {
		super("kk", "10.1.24.222", 3306,30000,36,"123456");
	}

	/**
	 * 添加数据
	 */
	 @Override
	 public boolean addDocument(String docId, Map<String, Object> fields) {
		   return super.addDocument(docId, fields);
		 
	 }
	 
	/**
	 * 批量添加数据
	 */
	 public int addDocuments(String docId, List<Map<String, Object>> list ) {
		 int i=0;
		for(Map<String, Object> fields :list){
			boolean isOK =super.addDocument(docId, fields);
			if(isOK){
				i++;
			}
		}
		   return i;
	 }

	 /**
	  * 修改数据
	  */
	  @Override
	  public boolean updateDocument(String docId, double score, Map<String, Object> fields){
		  return super.updateDocument(docId, score, fields);
	  }
	 /**
	  * 删除索引
	  */
	 @Override
	 public boolean dropIndex() {
	        return super.dropIndex();
	    }
	 /**
	  * 创建索引
	  */
	 @Override
	 public boolean createIndex(Schema schema, IndexOptions options){
		 return super.createIndex(schema, options);
	 }
	 /**
	  * 删除数据
	  */
	 @Override
	 public boolean deleteDocument(String docId){
		 return super.deleteDocument(docId);
	 }
	 /**
	  * 批量删除
	  * @param docIds
	  * @return
	  */
	 public int deleteDocuments(List<String> docIds){
		 int i=0;
		 for(String id :docIds){
			boolean isTrue = super.deleteDocument(id);
			if(isTrue){
				i++;
			}
		 }
		 return i;
	 }
	 /**
	  * 获取数据
	  */
	 @Override
	 public Document getDocument(String docId){
		 return super.getDocument(docId);
	 }
	 /**
	  * 搜索
	  */
	 @Override
	 public SearchResult search(Query q) {
		 return super.search(q);
	 }
	/**
	 * 搜索不包含内容只包含docID，score值的列表
	 * @param q
	 * @return
	 */
	 public SearchResult searchNocontent(Query q) {
		 return super.search(q.setNoContent());
	 }


	/**
	 * 中文分词器
	 */
	public static String separateWord(String Str){
		StringReader sr=new StringReader(Str);
		IKSegmenter ik=new IKSegmenter(sr, true);
		Lexeme lex=null;
		String result ="";
		try {
			while((lex=ik.next())!=null){
				result+=lex.getLexemeText()+" ";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 对数字分词
	 * @param number
	 * @return
	 */
	public static String parseWordNumber(String number){
		char[] array = number.toCharArray();
		String numberstr="";
		StringBuffer resultStr = new StringBuffer();
		int i=1;
		for(char a : array){
			System.out.println(a+"--");
			numberstr+=a;
			if(i>=2){
				resultStr.append(numberstr+" ");
			}
			i++;
		}
		return resultStr.toString();
	}
}
