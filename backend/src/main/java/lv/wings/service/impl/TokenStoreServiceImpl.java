package lv.wings.service.impl;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lv.wings.enums.RedisKeyType;
import lv.wings.service.TokenStoreService;

@Service
@RequiredArgsConstructor
public class TokenStoreServiceImpl implements TokenStoreService {
    private final RedisTemplate<String, Integer> redisTemplate;
    private final RedisTemplate<String, String> indexTemplate;

    @Override
    public void storeToken(RedisKeyType type, String token, Integer userId, Duration ttl) {
        redisTemplate.opsForValue().set(type.buildKey(token), userId, ttl);
        indexTemplate.opsForValue().set(type.buildIndexKey(userId), token, ttl);
    }

    @Override
    public void storeToken(RedisKeyType type, String token, Integer userId) {
        redisTemplate.opsForValue().set(type.buildKey(token), userId);
        indexTemplate.opsForValue().set(type.buildIndexKey(userId), token);
    }

    @Override
    public Integer getUserIdByToken(RedisKeyType type, String token) {
        return redisTemplate.opsForValue().get(type.buildKey(token));
    }

    @Override
    public void deleteToken(RedisKeyType type, String token) {
        String key = type.buildKey(token);
        Integer userId = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(type.buildKey(token));
        indexTemplate.delete(type.buildIndexKey(userId));
    }

    @Override
    public boolean hasActiveTokenOfThisType(RedisKeyType type, Integer userId) {
        return indexTemplate.hasKey(type.buildIndexKey(userId));
    }

}
