package com.copler.social.bridge.facebook.services;

import com.copler.social.bridge.config.CachingConfig;
import com.copler.social.bridge.facebook.dto.UserDto;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by Andrew on 1/5/2017.
 */
@Service
@CacheConfig(cacheNames = {CachingConfig.USERS_CACHE})
public class UserDataService {

    @Cacheable(key = "#userDto.id")
    public UserDto getUser(final UserDto userDto) {
        return userDto;
    }

    @CachePut(key = "#userDto.id")
    public UserDto putUser(final UserDto userDto) {
        return userDto;
    }

}


