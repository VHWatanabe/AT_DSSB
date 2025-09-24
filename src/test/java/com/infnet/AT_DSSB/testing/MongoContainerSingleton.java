package com.infnet.AT_DSSB.testing;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public final class MongoContainerSingleton {

    private static final MongoDBContainer INSTANCE =
            new MongoDBContainer(DockerImageName.parse("mongo:7"));

    static {
        INSTANCE.start();
    }

    private MongoContainerSingleton() {}

    public static MongoDBContainer getInstance() {
        return INSTANCE;
    }
}
