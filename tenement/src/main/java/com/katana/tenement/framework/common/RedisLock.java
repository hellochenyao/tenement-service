package com.katana.tenement.framework.common;

import com.katana.tenement.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Random;

@Slf4j
public class RedisLock implements Lock, AutoCloseable {

    private RedisClient redisClient = SpringUtils.getBean(RedisClient.class);

    /**
     * 是否锁定
     */
    private volatile boolean locked = false;

    private String lockKey;

    /**
     * 锁 等待时间,默认10秒，超时抛出客户端异常（请求太频繁）
     */
    private long waitTime = 10 * 1000;

    /**
     * 锁 超时时间，默认60秒，超时其他客户端可重新获取锁
     */
    private long timeout = 60 * 1000;

    /**
     * 上次设置的值,用于判断
     */
    private String lastTimeValue;

    public RedisLock(String lockKey) {
        this.lockKey = lockKey;
    }

    public RedisLock(String lockKey, long waitTime) {
        this.lockKey = lockKey;
        this.waitTime = waitTime;
    }

    public RedisLock(String lockKey, long waitTime, long timeout) {
        this.lockKey = lockKey;
        this.waitTime = waitTime;
        this.timeout = timeout;
    }

    @Override
    public void lock() {

        long remainingWaitTime = waitTime;
        while (remainingWaitTime > 0) {
            //锁到期时间
            long expires = System.currentTimeMillis() + timeout + 1;
            String expiresStr = String.valueOf(expires);

            //1.判断是否存在锁
            if (redisClient.setNX(lockKey, expiresStr)) {
                lastTimeValue = expiresStr;
                locked = true;
                return;
            }

            //2.判断锁是否超时
            String currentValueStr = redisClient.get(lockKey);
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                //超时
                String oldValueStr = redisClient.getSet(lockKey, expiresStr);
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    lastTimeValue = expiresStr;
                    locked = true;
                    return;
                }
            }

            //其他继续等待
            Random random = new Random();
            //等待随机时间，避免频繁申请锁
            int randomWaitTime = random.nextInt(100) + 50;

            remainingWaitTime -= randomWaitTime;
            try {
                Thread.sleep(randomWaitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new BusinessException("TOO-FREQUENT-OPERATION", "操作太频繁！");
    }

    @Override
    public boolean unlock() {
        if (locked) {
            String currentValueStr = redisClient.get(lockKey);
            if (currentValueStr.equals(lastTimeValue)) {
                redisClient.remove(lockKey);
            }
            locked = false;
        }
        return true;
    }

    @Override
    public void close() throws IOException {
        unlock();
    }
}