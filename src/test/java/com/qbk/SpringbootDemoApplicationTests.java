package com.qbk;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qbk.bean.UserBean;
import com.qbk.utils.EhcacheUtil;
import org.apache.catalina.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//redisTemplate测试类
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {

	/*两者之间的区别主要在于他们使用的序列化类。
    RedisTemplate使用的是JdkSerializationRedisSerializer
	StringRedisTemplate使用的是StringRedisSerializer*/

    /*RedisTemplate使用的序列类在在操作数据的时候，比如说存入数据会将数据先序列化成字节数组
    然后在存入Redis数据库，这个时候打开Redis查看的时候，你会看到你的数据不是以可读的形式
    展现的，而是以字节数组显示*/
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private EhcacheUtil ehcacheUtil ;

    @Test
    public void redisTest() {
        /*查看redisTemplate 的Serializer  :JdkSerializationRedisSerializer*/
        System.out.println(redisTemplate.getKeySerializer());
        System.out.println(redisTemplate.getValueSerializer());

		/*查看StringRedisTemplate 的Serializer : StringRedisSerializer*/
        System.out.println(stringRedisTemplate.getKeySerializer());
        System.out.println(stringRedisTemplate.getValueSerializer());

		 /*将stringRedisTemplate序列化类设置成RedisTemplate的序列化类*/
//        stringRedisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
//        stringRedisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

		 /*将redisTemplate序列化类设置成tringRedisTemplate的序列化类*/
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());

        UserBean user = new UserBean();//Serializable
        user.setUserName("卡卡");
        Map map = new HashMap();
        map.put("k1", "v1");
        map.put("k2", user);

        //存值
        redisTemplate.opsForValue().set("test:t1", user);
        stringRedisTemplate.opsForValue().set("test:t2", "redis测试");
        //取值
        UserBean user2 = (UserBean) redisTemplate.opsForValue().get("test:t1");
        System.out.println(user2.getUserName());
        String str2 = stringRedisTemplate.opsForValue().get("test:t2");
        System.out.println(str2);
        //删除
        redisTemplate.delete("test:t1");

        //只改key 方便查看
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setStringSerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set("test:t3", user);
        redisTemplate.opsForHash().put("map", "k", user);
        redisTemplate.opsForHash().putAll("map2", map);
        redisTemplate.opsForList().leftPush("list", user);
        redisTemplate.opsForList().leftPush("list",new String[]{"s1","s2"});

        /* redis数据库里面本来存的是字符串数据或者你要存取的数据就是字符串类型数据的时候，那么你就使用StringRedisTemplate即可，
        但是如果你的数据是复杂的对象类型，而取出的时候又不想做任何的数据转换，直接从Redis里面取出一个对象，那么使用RedisTemplate是更好的选择。*/
    }

    @Test
    public void redisTest2() {
        // 使用StringRedisSerializer 和 Jackson2JsonRedisSerialize 替换默认序列化
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		// 设置value的序列化规则和 key的序列化规则
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();

		//测试
        UserBean user = new UserBean();//Serializable
        user.setUserName("卡卡");
        redisTemplate.opsForValue().set("test:t1", "222");
        redisTemplate.opsForValue().set("test:t2", user);
        String str2 = (String) redisTemplate.opsForValue().get("test:t1");
        System.out.println(str2);
        UserBean user2 = (UserBean) redisTemplate.opsForValue().get("test:t2");
        System.out.println(user2);
    }

    @Test
    public void redisTest3() {
        //测试
        UserBean user = new UserBean();//Serializable
        user.setUserName("卡卡");
        redisTemplate.opsForValue().set("test:t1", "222");
        redisTemplate.opsForValue().set("test:t2", user);
        String str2 = (String) redisTemplate.opsForValue().get("test:t1");
        System.out.println(str2);
        UserBean user2 = (UserBean) redisTemplate.opsForValue().get("test:t2");
        System.out.println(user2);

    }

    @Test
    public  void  cacheManagerTest(){
        UserBean user = new UserBean();//Serializable
        user.setUserName("qu卡卡");

        ehcacheUtil.put("qbk","user",user);

        List list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        ehcacheUtil.put("qbk","list",list);

    }

}