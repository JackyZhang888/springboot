package com.example.demospringboot.job;

import com.example.demospringboot.service.AsyncUserService;
import com.example.demospringboot.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AsyncJobManager {

    private static final ThreadPoolTaskExecutor ASYNC_EXECUTOR_POOL =
            ThreadUtils.getThreadPool(ThreadUtils.ASYNC_EXECUTOR_POOL_PREFIX);

    @Autowired
    private AsyncUserService asyncUserService;

    public void startSyncExecutor() {
        ASYNC_EXECUTOR_POOL.execute(new AsyncExecutor(asyncUserService));
    }

    static class AsyncExecutor implements Runnable {
        private AsyncUserService asyncUserService;

        public AsyncExecutor(AsyncUserService asyncUserService) {
            this.asyncUserService = asyncUserService;
        }

        @Override
        public void run() {
            while (true) {
                asyncUserService.sendMailTask();
                // sleep 1s
                ThreadUtils.sleepUtil(1000L);
            }
        }
    }
}

