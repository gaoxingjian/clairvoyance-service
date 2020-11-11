package com.cav.clairvoyance.service;
import java.util.*;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    Boolean delete(String key);

    Long delete(Collection<String> keys);

    Boolean hasKey(String key);

    Boolean expire(String key, long timeout, TimeUnit unit);

    Set<String> keys(String pattern);

    Boolean persist(String key);

    Long getExpire(String key, TimeUnit unit);

    void set(String key, Object value);

    void set(String key, Object value, long time, TimeUnit unit);

    Boolean setifAbsen(String key, Object value, long time, TimeUnit unit);

    Object get(String key);

    Object getSet(String key, Object value);

    List<Object> mget(Collection<String> keys);

    long incrby(String key, long increment);

    Long decrby(String key, long decrement);

    Integer append(String key, String value);

    public Object hget(String key, Object field);

    public void hset(String key, Object field, Object value);

    Boolean hexists(String key, Object field);

    Long hdel(String key, Object... fields);

    Map<Object, Object> hgetall(String key);

    void hmset(String key, Map<String, Object> hash);

    List<Object> hmget(String key, Collection<Object> fields);

    Long hIncrBy(String key, Object field, long increment);

    Long lpush(String key, Object... strs);

    Long rpush(String key, Object... strs);

    Object lpop(String key);

    Object rpop(String key);

    List<Object> lrange(String key, long start, long end);

    void ltrim(String key, long start, long end);

    Object lindex(String key, long index);

    Long llen(String key);

    Long sadd(String key, Object... members);

    Long scard(String key);

    Boolean sismember(String key, Object member);

    Object srandmember(String key);

    List<Object> srandmember(String key, int count);

    Object spop(String key);

    Set<Object> smembers(String key);

    Long srem(String key, Object... members);

    Boolean smove(String srckey, String dstkey, Object member);

    Set<Object> sUnion(String key, String otherKeys);

    Boolean zadd(String key, double score, Object member);

    Long zrem(String key, Object... members);

    Long zcard(String key);

    Double zincrby(String key, double score, Object member);

    Long zcount(String key, double min, double max);

    Long zrank(String key, Object member);

    Double zscore(String key, Object member);

    Set<Object> zrange(String key, long min, long max);

    Set<Object> zReverseRange(String key, long start, long end);

    Set<Object> zrangebyscore(String key, double min, double max);

    Set<Object> zrevrangeByScore(String key, double min, double max);

}
