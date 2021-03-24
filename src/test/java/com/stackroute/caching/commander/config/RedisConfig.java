package com.stackroute.caching.commander.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * The configuration to run redis embedded server for testing
 */
@TestConfiguration
public class RedisConfig {


    /*
       the port to be used for testing running redis server
   */
    @Value("${spring.redis.port:6379}")
    private int redisPort;

    private RedisServer redisServer;


    /*
       to configure port and start embedded redis server for testing
   */
    @PostConstruct
    public void startRedis() {
        redisServer = RedisServer.builder()
                .port(redisPort)
                .setting("maxmemory 128M") //maxheap 128M
                .build();
        redisServer.start();
    }

    /*
       to stop embedded redis server after testing
   */
    @PreDestroy
    public void stopRedis() throws InterruptedException {
        redisServer.stop();
    }
}

