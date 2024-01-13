package com.example.demospringboot.task;

import com.example.demospringboot.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class AsyncTasks {
    public static Random random = new Random();

    // @Async注解中的参数指向异步任务的线程池
    @Async("taskExecutor")
    public CompletableFuture<String> doAsyncTask(String taskNo){
        log.info("start AsyncTask: {}", taskNo);
        long start = System.currentTimeMillis();
        ThreadUtils.sleepUtil(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("end:{}, task:{} ms", taskNo, end - start);
        return CompletableFuture.completedFuture("finished");
    }

}
