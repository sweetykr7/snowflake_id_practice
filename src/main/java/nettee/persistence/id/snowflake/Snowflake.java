package nettee.persistence.id.snowflake;

import nettee.persistence.id.snowflake.exception.ClockBackwardException;

import static nettee.persistence.id.snowflake.SnowflakeConstants.SnowflakeDefault.*;
import static nettee.persistence.id.snowflake.validator.SnowflakeConstructingValidator.validateDatacenterId;
import static nettee.persistence.id.snowflake.validator.SnowflakeConstructingValidator.validateWorkerId;

public final class Snowflake {
    private final long datacenterid;
    private final long workerId;
    private final long epoch;

    private long sequence;
    private long lastTimestamp = -1L;

    public Snowflake(SnowflakeProperties snowflakeProperties){
        this(
            snowflakeProperties.datacenterId(),
                snowflakeProperties.workerId(),
                snowflakeProperties.epoch()
        );
    }
    public Snowflake(long datacenterid, long workerId, long epoch) {
        //validator
        validateDatacenterId(datacenterid);
        validateWorkerId(workerId);

        this.datacenterid = datacenterid;
        this.workerId = workerId;
        this.epoch = epoch;
    }

    //동시성 제어를 엄격하게 할때
    public synchronized long nextId() {
        var timestamp = timeGen();
        if(timestamp < lastTimestamp) {
            throw new ClockBackwardException(timestamp, lastTimestamp);
        }

        // 0000 ... 0001 0000 0000 0000 원래값(오버플로우가 된다면)
        // 0000 ... 0000 1111 1111 1111 마스크
        // 0000 ... 0000 0000 0000 0000 결괏값
        if (timestamp == lastTimestamp) {
            sequence++;
            sequence = (sequence + 1) & (SEQUENCE_MASK);
            if(timestamp == 0) { //overflow
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else { //timestamp가 lastTimestamp 보다 큰 케이스기 때문에 0부터 다시 하면 된다.
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        long id;
        id = (timestamp - epoch) << TIMESTAMP_LEFT_SHIFT;
        id |= datacenterid <<  DATACENTER_ID_SHIFT;
        id |= workerId << WORKER_ID_SHIFT;
        id |= sequence;
        return id;
    }


//    busy Wait
//    쓰레드를 잡고 있다.
//    컨택스트 스위칭
//    쓰레드를 안쓰거나 sleep같은 행동을 하더라도 어차피 완벽하게 제어를 할 수 없다.
//    밀리초 동안에 안되는거는 다른문제일 가능성이 있다.
//    그리고 아이디는 빨리줘야하므로 빠르게 처리하고 주는게 좋다. 컨택스트 스위칭이 더 비쌀수도 있다.
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();

        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }  //이거 말고 LongSupplier를 만들어야함.


}
