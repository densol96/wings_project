package lv.wings.service;

import java.time.Duration;
import lv.wings.enums.RedisKeyType;

public interface TokenStoreService {
    void storeToken(RedisKeyType type, String token, Integer userId, Duration ttl);

    void storeToken(RedisKeyType type, String token, Integer userId);

    Integer getUserIdByToken(RedisKeyType type, String token);

    void deleteToken(RedisKeyType type, String token);

    boolean hasActiveTokenOfThisType(RedisKeyType type, Integer userId);
}
