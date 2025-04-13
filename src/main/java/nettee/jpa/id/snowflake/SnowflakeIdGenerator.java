package nettee.jpa.id.snowflake;

import nettee.persistence.id.snowflake.Snowflake;
import nettee.persistence.id.snowflake.SnowflakeProperties;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.EventType;
import org.hibernate.id.IdentifierGenerator;

public class SnowflakeIdGenerator implements IdentifierGenerator {

    private final Snowflake snowflake;
    public SnowflakeIdGenerator(SnowflakeProperties snowflakeProperties) {
        this.snowflake = new Snowflake(snowflakeProperties);
    }

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return snowflake.nextId();
    }
}
