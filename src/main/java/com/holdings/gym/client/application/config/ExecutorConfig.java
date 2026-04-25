package com.holdings.gym.client.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    public static final String DOMAIN_EXECUTOR = "domainExecutor";

    @Bean(name = DOMAIN_EXECUTOR)
    public Executor domainExecutor() {
        // En Java 21, usar Virtual Threads es la opción recomendada para concurrencia ligera
        return Executors.newVirtualThreadPerTaskExecutor();
    }
    
}
