package com.nitokrisalpha.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VersionLogger implements CommandLineRunner {

    private static final String version = "1.12";

    @Override
    public void run(String... args) throws Exception {
        log.info(version);
    }
}
