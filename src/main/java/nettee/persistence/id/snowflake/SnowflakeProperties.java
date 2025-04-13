package nettee.persistence.id.snowflake;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

import static nettee.persistence.id.snowflake.SnowflakeConstants.NETTEE_EPOCH;


@ConfigurationProperties(SnowflakeProperties.PREFIX)
public record SnowflakeProperties(
        Long datacenterId,
        Long workerId,
        Long epoch
) {
    static final String PREFIX = "nettee.persistence.snowflake";

    public SnowflakeProperties {
        Objects.requireNonNull(datacenterId, PREFIX + ".datacenter-id must not be null.");
        Objects.requireNonNull(workerId, PREFIX + ".worker-id must not be null.");

        if (epoch == null) {
            epoch = NETTEE_EPOCH;
        } else if (epoch < 0) {
            epoch = NETTEE_EPOCH;
        }
    }
}
