package com.infnet.AT_DSSB.config;

import com.infnet.AT_DSSB.testing.MongoContainerSingleton;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

public abstract class TestContainersConfig {

    protected static final MongoDBContainer MONGO = MongoContainerSingleton.getInstance();

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> MONGO.getReplicaSetUrl("at_dssb_test"));
    }
}
