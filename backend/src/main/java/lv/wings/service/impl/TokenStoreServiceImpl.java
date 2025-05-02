package lv.wings.service.impl;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lv.wings.service.TokenStoreService;

@Service
@RequiredArgsConstructor
public class TokenStoreServiceImpl implements TokenStoreService {
    private final RedisTemplate<String, Integer> redisTemplate;

    @Override
    public void storeToken(String token, Integer userId, Duration ttl) {
        redisTemplate.opsForValue().set(token, userId, ttl);
    }

    @Override
    public void storeToken(String token, Integer userId) {
        redisTemplate.opsForValue().set(token, userId);
    }

    @Override
    public Integer getUserIdByToken(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }


}
