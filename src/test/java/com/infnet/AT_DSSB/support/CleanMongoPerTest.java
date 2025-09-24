package com.infnet.AT_DSSB.support;

import com.infnet.AT_DSSB.config.TestContainersConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class CleanMongoPerTest extends TestContainersConfig {

    @Autowired protected MongoTemplate mongo;

    @BeforeEach
    void cleanDb() {
        mongo.getDb().drop();
    }
}
