package com.katana.tenement.framework.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Component
public class RedisClient {

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
        return result;
    }

    public boolean set(final String key, final Object bean) {
        final String value = JSON.toJSONString(bean);
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
        return result;
    }

    public boolean set(final String key, final Object bean, final long liveTime) {
        final String value = JSON.toJSONString(bean);
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            connection.expire(serializer.serialize(key), liveTime);
            return true;
        });
        return result;
    }

    public boolean set(final String key, final String value, final long liveTime) {
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            connection.expire(serializer.serialize(key), liveTime);
            return true;
        });
        return result;
    }

    public boolean setNX(final String key, final String value) {
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Boolean aBoolean = connection.setNX(serializer.serialize(key), serializer.serialize(value));
            connection.close();
            return aBoolean;
        });
        return result;
    }

    public boolean setNX(final String key, final String value, final long liveTime) {
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Boolean aBoolean = connection.setNX(serializer.serialize(key), serializer.serialize(value));
            connection.expire(serializer.serialize(key), liveTime);
            connection.close();
            return aBoolean;
        });
        return result;
    }

    public String getSet(final String key, final String value) {
        String result = redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
            return serializer.deserialize(ret);
        });
        return result;
    }

    public String get(final String key) {
        String result = redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] value = connection.get(serializer.serialize(key));
            return serializer.deserialize(value);
        });
        return result;
    }

    public void remove(final String key) {
        redisTemplate.delete(key);
    }

    public <T> T get(final String key, final Class<T> clazz) {
        String result = redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] value = connection.get(serializer.serialize(key));
            return serializer.deserialize(value);
        });
        return JSON.parseObject(result, clazz);
    }

    public boolean expire(final String key, long liveTime) {
        return redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
    }

    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public long getExpireTime(final String key) {
        return redisTemplate.getExpire(key);
    }

    public <T> boolean setList(String key, List<T> list) {
        String value = JSONObject.toJSONString(list);
        return set(key, value);
    }

    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            List<T> list = JSONObject.parseArray(json, clz);
            return list;
        }
        return null;
    }

    public long lpush(final String key, Object obj) {
        final String value = JSONObject.toJSONString(obj);
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
            return count;
        });
        return result;
    }

    public long lpush(final String key, String obj) {
        final String value = obj;
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
            return count;
        });
        return result;
    }

    public long lpush(final String key, String obj, final long liveTime) {
        final String value = obj;
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
            connection.expire(serializer.serialize(key), liveTime);
            return count;
        });
        return result;
    }

    public long lpush(final String key, Object obj, final long liveTime) {
        final String value = JSONObject.toJSONString(obj);
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
            connection.expire(serializer.serialize(key), liveTime);
            return count;
        });
        return result;
    }

    public long rpush(final String key, Object obj) {
        final String value = JSONObject.toJSONString(obj);
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
            return count;
        });
        return result;
    }

    public long rpush(final String key, Object obj, final long liveTime) {
        final String value = JSONObject.toJSONString(obj);
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
            connection.expire(serializer.serialize(key), liveTime);
            return count;
        });
        return result;
    }

    public String lpop(final String key) {
        String result = redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] res = connection.lPop(serializer.serialize(key));
            return serializer.deserialize(res);
        });
        return result;
    }

    public String rpop(final String key) {
        String result = redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] res = connection.rPop(serializer.serialize(key));
            return serializer.deserialize(res);
        });
        return result;
    }

    public List<String> rpopAll(final String key) {
        List<String> result = redisTemplate.execute((RedisCallback<List<String>>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            List<byte[]> res = connection.lRange(serializer.serialize(key), 0, -1);
            connection.lTrim(serializer.serialize(key), 1001, 1001);
            List<String> resStr = new ArrayList<>();
            res.forEach(e -> {
                resStr.add(new String(e));
            });
            return resStr;
        });
        return result;
    }

    public long pfadd(final String key, String value) {
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Long res = connection.pfAdd(serializer.serialize(key), serializer.serialize(value));
            connection.expire(serializer.serialize(key), 60 * 10);
            return res.longValue();
        });
        return result;
    }

    public long pfadd(final String key, String value, final long liveTime) {
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Long res = connection.pfAdd(serializer.serialize(key), serializer.serialize(value));
            connection.expire(serializer.serialize(key), liveTime);
            return res.longValue();
        });
        return result;
    }

    public long pfcount(final String key) {
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Long res = connection.pfCount(serializer.serialize(key));
            return res.longValue();
        });
        return result;
    }

    public long sadd(final String key, final String value) {
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Long res = connection.sAdd(serializer.serialize(key), serializer.serialize(value));
            return res.longValue();
        });
        return result;
    }

    public boolean sIsMember(final String key, final String value) {
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Boolean res = connection.sIsMember(serializer.serialize(key), serializer.serialize(value));
            return res.booleanValue();
        });
        return result;
    }

    public long srem(final String key, final String value) {
        long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Long res = connection.sRem(serializer.serialize(key), serializer.serialize(value));
            return res.longValue();
        });
        return result;
    }

    public Set<String> scan(final String match, final int count) {
        Set<String> result = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> binaryKeys = new HashSet<>();
            ScanOptions scanOptions = ScanOptions.scanOptions().count(count).match(match).build();
            Cursor<byte[]> cursor1 = connection.scan(scanOptions);
            while (cursor1.hasNext()) {
                binaryKeys.add(new String(cursor1.next()));
            }
            return binaryKeys;
        });
        return result;
    }

    /**
     * key 模糊查询
     *
     * @param key
     * @return
     */
    public Set<String> keys(final String key) {
        Set<String> result = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Set<byte[]> res = connection.keys(serializer.serialize(key));
            Set<String> newRes = new TreeSet<>();
            res.forEach(v -> {
                newRes.add(serializer.deserialize(v));
            });
            return newRes;
        });
        return result;
    }

}

