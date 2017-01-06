package com.copler.social.bridge.config;

import com.copler.social.bridge.facebook.services.UserDataService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by Andrew on 1/5/2017.
 */
@Configuration
@EnableCaching
@ComponentScan("com.copler.social.bridge")
public class CachingConfig {

    public static final String USERS_CACHE = "users";

    @Bean
    public CacheManager cacheManager() {
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache(USERS_CACHE)));
        return cacheManager;
    }

    @Bean
    public UserDataService customerDataService() {
        return new UserDataService();
    }

}
