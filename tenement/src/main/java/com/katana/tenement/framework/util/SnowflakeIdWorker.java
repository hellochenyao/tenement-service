package com.katana.tenement.framework.util;


/**
 * 雪花算法得到唯一id
 * Created by grw on 2018/6/25.
 */
public class SnowflakeIdWorker {
    private final static long TWEPOCH = 1288834974657L;

    // 机器标识位数
    private final static long WORKER_ID_BITS = 5L;

    // 数据中心标识位数
    private final static long DATA_CENTER_ID_BITS = 5L;

    // 机器ID最大值 31
    private final static long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    // 数据中心ID最大值 31
    private final static long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);

    // 毫秒内自增位
    private final static long SEQUENCE_BITS = 12L;

    // 机器ID偏左移12位
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;

    private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    // 时间毫秒左移22位
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    private final static long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);
    private final long workerId;
    private final long dataCenterId;
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public SnowflakeIdWorker(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("%s must range from %d to %d", 0,
                    MAX_WORKER_ID));
        }

        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("%s must range from %d to %d", 0,
                    MAX_DATA_CENTER_ID));
        }

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized long nextValue() {
        long timestamp = time();
        if (timestamp < lastTimestamp) {
            return 0;
        }

        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;

        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;

        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.time();
        while (timestamp <= lastTimestamp) {
            timestamp = this.time();
        }
        return timestamp;
    }

    private long time() {
        return System.currentTimeMillis();
    }
}
