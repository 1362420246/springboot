package com.qbk;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.http11.Http11NioProtocol;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.crypto.KeyGenerator;
import javax.crypto.KeyGeneratorSpi;
import java.lang.reflect.Method;
import java.security.Provider;
import java.time.Duration;

//入口类
/**@SpringBootApplication  = @SpringBootConfiguration(@Configuration)  +  @ComponentScan +@EnableAutoConfiguration
 * @ComponentScan ：主要就是定义扫描的路径从中找出标识了需要装配的类自动装配到spring的bean容器中
 *@EnableAutoConfiguration : 是让springboot根据类路径中的jar包依赖为当前项目进行自动配置，
 * 例如添加spring-boot-starter-web依赖，会自动添加Tomcat和sprngmvc的依赖，那么springboot会对tomcat和springmvc进行自动配置。
 * springboot会自动扫描@SpringBootApplication所在类的同级包，以及下级包里的bean，建议入口类放置的位置 groupId+arctifactID组合的包名下
 * @SpringBootApplication(exclude={DataSourceAutoConfiguration.class})  关闭特定的配置
 */
@SpringBootApplication
//开启注解的缓存支持
@EnableCaching
public class SpringbootDemoApplication {

	@Bean//实例化 redisTemplate 。更换默认序列化
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		// 使用Jackson2JsonRedisSerialize 替换默认序列化
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		//设置序列化
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		//配置redisTemplate
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		//读取 redis配置文件中的配置
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		// 设置value的序列化规则和 key的序列化规则
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
		redisTemplate.setKeySerializer(new StringRedisSerializer());//key序列化
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());//Hash key序列化
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean //缓存管理器 CacheManager: RedisCacheManager .如果配置此处，配置文件中的cache配置会失效
	CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory ) {
//		RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory).build();
//		RedisCacheManager redisCacheManager = RedisCacheManager.create(redisConnectionFactory);

		/* 设置CacheManager的值序列化方式为JdkSerializationRedisSerializer,
		   但其实RedisCacheConfiguration默认就是使用StringRedisSerializer序列化key，JdkSerializationRedisSerializer序列化value,
		  所以以下注释代码为默认实现  */
//		ClassLoader loader = this.getClass().getClassLoader();
//		JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
//		RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);
//		RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

		// 使用Jackson2JsonRedisSerialize 替换默认序列化
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		//设置序列化
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);
		//RedisCacheConfiguration
		RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
//		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
		//设置默认超过期时间是 秒
		RedisCacheConfiguration configuration = defaultCacheConfig.entryTtl(Duration.ofSeconds(100));
		//初始化一个RedisCacheWriter
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
		//初始化RedisCacheManager
		RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, configuration);
		return redisCacheManager;

//		return RedisCacheManager.create(redisConnectionFactory);
	}

	public static void main(String[] args) {
		//启动springboot应用项目
		SpringApplication.run(SpringbootDemoApplication.class, args);
	}
}
