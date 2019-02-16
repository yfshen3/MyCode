package org.lf.admin.api.baseapi.util;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

/**
 * 缓存工具类
 *
 * @author sunwill
 */
@Slf4j
public class EhCacheUtil {
    public enum CacheNameEnum {
        /*CacheAccountInfo,*/
        IdentifySms
        /*,MajorPickCache,MajorListCache;*/
    }

    private static final String GET_CACHE_ERROR = "获取缓存实例出错";
    private static CacheManager cacheManager;

    /**
     * 获得缓存管理器实例
     *
     * @return
     */
    private static final synchronized CacheManager getManagerInstance() {
        if (cacheManager == null) {
            Configuration xmlConfig = new XmlConfiguration(EhCacheUtil.class.getResource("/ehcache.xml"));
            cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
            cacheManager.init();
        }
        return cacheManager;
    }

    /**
     * 获取缓存实例
     *
     * @param cacheName 缓存名称（alias）
     * @param keyType
     * @param valueType
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Cache<K, V> getCache(CacheNameEnum cacheName, Class<K> keyType, Class<V> valueType) {
        Cache<K, V> cache = null;
        try {
            cache = getManagerInstance().getCache(cacheName.name(), keyType, valueType);
        } catch (Exception e) {
            log.error(GET_CACHE_ERROR, e);
        }
        if (cache == null) {
            throw new IllegalArgumentException(GET_CACHE_ERROR);
        }
        return cache;
    }

    /**
     * 获取缓存中的值
     *
     * @param cache
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> V getValue(Cache<K, V> cache, K key) {
        return cache.get(key);
    }

    /**
     * 向缓存中添加数据
     *
     * @param cache
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     */
    public static final <K, V> void put(Cache<K, V> cache, K key, V value) {
        cache.put(key, value);
    }

    /**
     * 删除缓存中指定key的值
     *
     * @param cache
     * @param key
     * @param <K>
     * @param <V>
     */
    public static final <K, V> void remove(Cache<K, V> cache, K key) {
        cache.remove(key);
    }

    /**
     * 清空缓存中的内容
     *
     * @param cache
     * @param <K>
     * @param <V>
     */
    public static final <K, V> void clear(Cache<K, V> cache) {
        cache.clear();
    }

    /**
     * 判断缓存中是否存在指定key的值
     *
     * @param cache
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> boolean contains(Cache<K, V> cache, K key) {
        return cache.get(key) != null;
    }

}