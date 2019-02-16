package org.lf.admin.api.baseapi.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

    public static final int DEFAULT_MAXSIZE = 50000;
    public static final int DEFAULT_TTL = 60;

    public enum Caches {

        questionnaire_fields(60 * 60),

        questionnaires(3600 * 60),

        permissions(3600 * 60),

        person_questionnaire,

        persons,

        statistics_number(3600 * 60);

        private int maxSize = DEFAULT_MAXSIZE;    //最大數量
        private int ttl = DEFAULT_TTL;        //过期时间（秒）

        Caches() {
        }

        Caches(int ttl) {
            this.ttl = ttl;
        }

        Caches(int maxSize, int ttl) {
            this.maxSize = maxSize;
            this.ttl = ttl;
        }

    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = new ArrayList<>();

        for (Caches cache : Caches.values()) {
            caches.add(new CaffeineCache(
                    cache.name(),
                    Caffeine.newBuilder()
                            .recordStats()
                            .expireAfterWrite(cache.ttl, TimeUnit.SECONDS)
                            .maximumSize(cache.maxSize).build()));
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
