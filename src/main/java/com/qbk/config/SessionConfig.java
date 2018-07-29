package com.qbk.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * spring-session配置 会导致配置文件配置方式失效
 * 通过@EnableRedisHttpSession可以知道，Spring Session是通过RedisHttpSessionConfiguration类进行配置，该类是用于创建一个过滤SessionRepositoryFilter
 * 扩展知识:Spring Session提供了3种方式存储session的方式。
 *
 * @EnableRedisHttpSession-存放在缓存redis
 * @EnableMongoHttpSession-存放在Nosql的MongoDB
 * @EnableJdbcHttpSession-存放数据库 此filter放在所有filter之前，接管session管理。
 * 如何获取getSession：
 * 先检查是不是已经有session了。如果有的话，就将其返回，
 * 否则的话，它会检查当前的请求中是否有session id。
 * 如果有的话，将会根据这个session id，从它的SessionRepository中加载session。
 * 如果session repository中没有session，或者在当前请求中，没有当前
 * session id与请求关联的话，那么它会创建一个新的session，并将其
 * 持久化到session repository中
 * 如何存储session：
 * 请求时，先获取当前session，不为空时即保存session。保存后，判断
 * 当前请求中的sessionId是否与当前sessionId一致，若不一致，则将当
 * 前sessionId保存至cookie。
 */
//这个类用配置redis服务器的连接，设置Session过期时间，单位：秒
//maxInactiveIntervalInSeconds为SpringSession的过期时间（单位：秒）
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10)
public class SessionConfig {

    @Bean
    public static ConfigureRedisAction configureRedisAction(RedisConnectionFactory redisConnectionFactory) {
        return ConfigureRedisAction.NO_OP;
    }
}

