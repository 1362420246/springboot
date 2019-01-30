package com.qbk;

import io.redisearch.Document;
import io.redisearch.Query;
import io.redisearch.Schema;
import io.redisearch.SearchResult;
import io.redisearch.client.Client;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by qbk on 2019/1/28.
 * redisearch测试类
 */
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisearchTest {

    //使用JedisSentinelPool初始化客户端:
    private static final String MASTER_NAME = "mymaster";
    private static final Set<String> sentinels;
    static {
        sentinels = new HashSet();
        sentinels.add("localhost:7000");
        sentinels.add("localhost:7001");
        sentinels.add("localhost:7002");
    }

    /**
     * 客户端
     */
    private Client client;

    /**
     * 初始化客户端
     */
    @Before
    public void setUp() throws Exception {
        //Client client = new Client("testung", MASTER_NAME, sentinels);

        //用JedisPool初始化客户端:
        client = new Client("qbk", "10.1.24.222", 6379,300000,500,"123456");
    }

    /**
     * 创建索引
     */
    @Test
    public void test(){
        // 定义一个索引模式
        Schema sc = new Schema()
                //添加字段 和字段等级
                .addTextField("title", 5.0)
                .addTextField("body", 10.0)
                .addNumericField("price");
        //删除索引
        client.dropIndex();
        // 创建索引
        boolean index = client.createIndex(sc, Client.IndexOptions.Default());
        System.out.println("创建索引："+index);
    }

    /**
     * 添加文档
     */
    @Test
    public void test2(){
        //向索引中添加文档:
        Map<String, Object> fields = new HashMap<>();
        //按照空格分词
        fields.put("title", "hello world");
        fields.put("body", "lorem ipsum");
        fields.put("price", 1001);

        //向索引中添加文档:
        Map<String, Object> fields1 = new HashMap<>();
        //按照空格分词
        fields1.put("title", "hlorem ipsum");
        fields1.put("body", "hello world");
        fields1.put("price", 101);

        Map<String, Object> fields2 = new HashMap<>();
        //添加中文并使用中文分词器
        fields2.put("title",separateWord( "曲博卡"));
        fields2.put("body", "男");
        fields2.put("price", 23);

        boolean doc = client.addDocument("doc", fields);
        boolean doc1 = client.addDocument("doc1", fields1);
        boolean doc2 = client.addDocument("doc2", fields2);
        System.out.println("添加文档结果："+doc);
        System.out.println("添加文档结果："+doc1);
        System.out.println("添加文档结果："+doc2);
    }

    /**
     * 查询
     */
    @Test
    public void test3(){
        //搜索指数:
        // 创建复杂查询
        Query q = new Query("world")
                 //过滤条件
                .addFilter(new Query.NumericFilter("price", 0, 2000))
                //分页
                .limit(0,5);
        //搜索结果
        SearchResult result = client.search(q);
        System.out.println(result);
        List<Document> docs = result.docs;
        System.out.println(docs);
    }

    /**
     * 中文词查询
     */
    @Test
    public void test4(){
        //搜索指数:
        // 创建复杂查询
        Query q = new Query(separateWord("曲博卡"));
        //搜索结果
        SearchResult result = client.search(q);
        System.out.println(result);
        List<Document> docs = result.docs;
        System.out.println(docs);
    }


    /**
     * 查询分解
     */
    @Test
    public void test5(){
        Query query = new Query("hello world");
        //数字过滤
        Query.NumericFilter f = new Query.NumericFilter("price", 0, 1001);
        //添加过滤
        query.addFilter(f);
        //分页
        query.limit(0,100);
        //限制字段查询
        //query.limitFields("title");
        //按照字段等级，匹配排序
        query.setWithScores();
        //搜索
        SearchResult result = client.search(query);
        //搜索结果文档
        List<Document> docs = result.docs;
        System.out.println(docs);
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




}
