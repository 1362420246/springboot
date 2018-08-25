package com.qbk.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * @Auther: quboka
 * @Date: 2018/8/25 22:06
 * @Description: 缓存管理工具类
 */
@Component
public class EhcacheUtil {

    @Autowired
    private CacheManager manager;

    /**
     * cacheName key的包名
     * key
     * value
     */
    public void put(String cacheName, String key, Object value) {
        Cache cache = manager.getCache(cacheName);
        cache.put(key,value);
    }

    public Object get(String cacheName, String key) {
        Cache cache = manager.getCache(cacheName);
        return  cache.get(key);
    }

    public Cache get(String cacheName) {
        return manager.getCache(cacheName);
    }

    public void clear(String cacheName) {
        Cache cache = manager.getCache(cacheName);
        cache.clear();
    }

    public void evict(String cacheName,String key) {
        Cache cache = manager.getCache(cacheName);
        cache.evict(key);
    }
}
