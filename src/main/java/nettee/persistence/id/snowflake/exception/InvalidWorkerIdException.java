package nettee.persistence.id.snowflake.exception;

import static nettee.persistence.id.snowflake.SnowflakeConstants.SnowflakeDefault.MAX_WORKER_ID;

public class InvalidWorkerIdException extends RuntimeException{

    private final long workdId;
    public InvalidWorkerIdException(long workdId) {
        super("datacenter Id can't be greater than %d or less than 0. Input %d".formatted(MAX_WORKER_ID, workdId));
        this.workdId = workdId;
    }

    public long getWorkdId() {
        return workdId;
    }
}
