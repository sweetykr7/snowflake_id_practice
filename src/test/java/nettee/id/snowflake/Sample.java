package nettee.id.snowflake;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import nettee.jpa.id.snowflake.SnowflakeIdGenerated;

@Entity
@Table(
        schema = "sample",
        name = "sample"
)
public class Sample {
    @Id
    @SnowflakeIdGenerated
    public long id;
}
