package com.example.demospringboot.job;

import com.example.demospringboot.service.SchedUserService;
import com.example.demospringboot.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SchedJobManager {
    private static final ThreadPoolTaskExecutor SCHED_EXECUTOR_POOL =
            ThreadUtils.getThreadPool(ThreadUtils.SCHED_EXECUTOR_POOL_PREFIX);

    @Autowired
    private SchedUserService schedUserService;

    public void startSchedExecutor() {
        SCHED_EXECUTOR_POOL.execute(new Executor(schedUserService));
    }

    static class Executor implements Runnable {
        private SchedUserService schedUserService;

        public Executor(SchedUserService schedUserService) {
            this.schedUserService = schedUserService;
        }

        @Override
        public void run() {
            while (true) {
                schedUserService.AnalysisLogTask();
                // sleep 1s
                ThreadUtils.sleepUtil(1000L);
            }
        }
    }
}

