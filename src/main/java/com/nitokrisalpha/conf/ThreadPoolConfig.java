package com.nitokrisalpha.conf;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootConfiguration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService copyTaskExecutors() {
        return Executors.newFixedThreadPool(1);
    }

}
