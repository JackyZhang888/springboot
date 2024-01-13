package com.example.demospringboot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ThreadPoolExecutor;

@Repository
@Slf4j
public class ThreadUtils {
    public static final int MAX_POOL_SIZE = 2;
    public static final String SCHED_EXECUTOR_POOL_PREFIX = "sched-exe-" + MAX_POOL_SIZE + "-";
    public static final String ASYNC_EXECUTOR_POOL_PREFIX = "async-exe-" + MAX_POOL_SIZE + "-";

    public static final String ASYNC_TASK_POOL_PREFIX = "async-task-" + MAX_POOL_SIZE + "-";

    // 自定义AsyncTask线程池
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(MAX_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(MAX_POOL_SIZE);
        executor.setKeepAliveSeconds(0);
        executor.setThreadNamePrefix(ASYNC_TASK_POOL_PREFIX);
        // 如果添加到线程池失败，那么主线程会自己去执行该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    // 启动Executor的线程池
    public static ThreadPoolTaskExecutor getThreadPool(String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(MAX_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(MAX_POOL_SIZE);
        executor.setKeepAliveSeconds(0);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        return executor;
    }
    public static void sleepUtil(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("{}", e);
        }
    }
}
