package com.itmagination.itmtest.common;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
class Config {

    @Bean
    CacheManager cacheManager() {
        Caffeine<Object, Object> cacheConfig = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES);

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(cacheConfig);

        return cacheManager;
    }
}
