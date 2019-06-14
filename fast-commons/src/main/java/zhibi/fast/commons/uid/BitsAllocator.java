package zhibi.fast.commons.uid;

import lombok.Getter;


/**
 * @author 执笔
 */
public class BitsAllocator {
    /**
     * 一共 64 位
     */
    public static final int  TOTAL_BITS = 1 << 6;
    @Getter
    private final       int  timestampBits;
    @Getter
    private final       int  workerIdBits;
    @Getter
    private final       int  sequenceBits;
    /**
     * Max value for workId & sequence
     */
    @Getter
    private final       long maxDeltaSeconds;
    @Getter
    private final       long maxWorkerId;
    @Getter
    private final       long maxSequence;
    /**
     * Shift for timestamp & workerId
     */
    private final       int  timestampShift;
    private final       int  workerIdShift;
    /**
     * [sign-> second-> workId-> sequence]
     */
    @Getter
    private             int  signBits   = 1;


    public BitsAllocator(int timestampBits, int workerIdBits, int sequenceBits) {
        // 确保分配64位
        int allocateTotalBits = signBits + timestampBits + workerIdBits + sequenceBits;
        if (allocateTotalBits != TOTAL_BITS) {
            throw new IllegalArgumentException("allocate not enough 64 bits");
        }

        // initialize bits
        this.timestampBits = timestampBits;
        this.workerIdBits = workerIdBits;
        this.sequenceBits = sequenceBits;

        // initialize max value
        this.maxDeltaSeconds = ~(-1L << timestampBits);
        this.maxWorkerId = ~(-1L << workerIdBits);
        this.maxSequence = ~(-1L << sequenceBits);

        // initialize shift
        this.timestampShift = workerIdBits + sequenceBits;
        this.workerIdShift = sequenceBits;
    }

    /**
     * Allocate bits for UID according to delta seconds & workerId & sequence<br>
     * <b>Note that: </b>The highest bit will always be 0 for sign
     *
     * @param deltaSeconds
     * @param workerId
     * @param sequence
     * @return
     */
    public long allocate(long deltaSeconds, long workerId, long sequence) {
        return (deltaSeconds << timestampShift) | (workerId << workerIdShift) | sequence;
    }

}