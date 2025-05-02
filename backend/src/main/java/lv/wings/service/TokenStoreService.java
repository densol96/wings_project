package lv.wings.service;

import java.time.Duration;

public interface TokenStoreService {
    void storeToken(String token, Integer userId, Duration ttl);

    void storeToken(String token, Integer userId);

    Integer getUserIdByToken(String token);

    void deleteToken(String token);
}
