package lv.wings.enums;

public enum RedisKeyType {
    PASSWORD_RESET("reset:token:"), //
    ACCOUNT_UNLOCK("unlock:token:"), //
    REQUEST_UNLOCK("request:unlock:token:");

    private final String prefix;

    RedisKeyType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String buildKey(String identifier) {
        return prefix + identifier;
    }

    public String buildKey(long id) {
        return prefix + id;
    }
}
