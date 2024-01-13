package com.example.demospringboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class taskConfig {
    /**
     * 对象线程池定义
     */
    @Value("${spring.task.execution.pool.core-size}")
    private Integer corePoolSize;

    @Value("${spring.task.execution.pool.max-size}")
    private Integer maxPoolSize;

    @Value("${spring.task.execution.pool.queue-capacity}")
    private Integer queueCapacity;

    @Value("${spring.task.execution.pool.keep-alive}")
    private Integer keepAliveSeconds;

    @Value("task1-")
    private String threadNamePrefix1;

    @Value("task2-")
    private String threadNamePrefix2;

    // 使用@Bean 注解表明taskExecutor需要交给Spring进行管理
    // 未指定bean 的名称，默认采用的是"方法名"+"首字母小写"的配置方式
    @Bean
    public Executor taskExecutor1() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix1);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean
    public Executor taskExecutor2() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix2);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}

