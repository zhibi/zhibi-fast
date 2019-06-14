package zhibi.fast.commons.uid.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import zhibi.fast.commons.uid.BitsAllocator;
import zhibi.fast.commons.uid.UidGenerator;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * UidGenerator 的基础实现
 *
 * @author 执笔
 */
@Slf4j
public class DefaultUidGenerator implements UidGenerator {


    /**
     * 默认分配
     * 加起来要等于63
     */
    @Setter
    private int timeBits = 28;
    @Setter
    private int workerBits = 22;
    @Setter
    private int seqBits = 13;
    /**
     * 机器id
     */
    private long workerId;
    /**
     * 序列
     */
    private long sequence;

    private long epochSeconds = TimeUnit.MILLISECONDS.toSeconds(1543909948833L);

    private BitsAllocator bitsAllocator;


    private long lastSecond = -1L;


    /**
     * 单机时候机器ID和序列可以随便设置
     * 主要是为了方便分布式部署
     *
     * @param workerId 机器ID
     * @param sequence 序列
     */
    public DefaultUidGenerator(long workerId, long sequence) {
        this.workerId = workerId;
        this.sequence = sequence;
        // initialize bits allocator
        bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);
        if (workerId > bitsAllocator.getMaxWorkerId()) {
            throw new RuntimeException("Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId());
        }
        log.info("Initialized bits(1, {}, {}, {}) for workerID:{}", timeBits, workerBits, seqBits, workerId);
    }

    @Override
    public long getUID() {
        return nextId();
    }

    @Override
    public String parseUID(long uid) {
        long totalBits = BitsAllocator.TOTAL_BITS;
        long signBits = bitsAllocator.getSignBits();
        long timestampBits = bitsAllocator.getTimestampBits();
        long workerIdBits = bitsAllocator.getWorkerIdBits();
        long sequenceBits = bitsAllocator.getSequenceBits();

        // parse UID
        long sequence = (uid << (totalBits - sequenceBits)) >>> (totalBits - sequenceBits);
        long workerId = (uid << (timestampBits + signBits)) >>> (totalBits - workerIdBits);
        long deltaSeconds = uid >>> (workerIdBits + sequenceBits);

        Date thatTime = new Date(TimeUnit.SECONDS.toMillis(epochSeconds + deltaSeconds));
        String thatTimeStr = DateFormatUtils.format(thatTime, "yyyy-MM-dd HH:mm:ss");

        // format as string
        return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}", uid, thatTimeStr, workerId, sequence);
    }


    /**
     * Get UID
     *
     * @return UID
     */
    private synchronized long nextId() {
        long currentSecond = getCurrentSecond();

        // Clock moved backwards, refuse to generate uid
        if (currentSecond < lastSecond) {
            long refusedSeconds = lastSecond - currentSecond;
            throw new RuntimeException(String.format("Clock moved backwards. Refusing for %d seconds", refusedSeconds));
        }

        // At the same second, increase sequence
        if (currentSecond == lastSecond) {
            sequence = (sequence + 1) & bitsAllocator.getMaxSequence();
            // Exceed the max sequence, we wait the next second to generate uid
            if (sequence == 0) {
                currentSecond = getNextSecond(lastSecond);
            }

            // At the different second, sequence restart from zero
        } else {
            sequence = 0L;
        }

        lastSecond = currentSecond;

        // 分配uid
        return bitsAllocator.allocate(currentSecond - epochSeconds, workerId, sequence);
    }

    /**
     * 获得下一毫秒
     */
    private long getNextSecond(long lastTimestamp) {
        long timestamp = getCurrentSecond();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentSecond();
        }

        return timestamp;
    }

    /**
     * 获取当前毫秒
     */
    private long getCurrentSecond() {
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        if (currentSecond - epochSeconds > bitsAllocator.getMaxDeltaSeconds()) {
            throw new RuntimeException("Timestamp bits is exhausted. Refusing UID generate. Now: " + currentSecond);
        }
        return currentSecond;
    }
}
