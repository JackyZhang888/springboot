package com.example.demospringboot.task;

import com.example.demospringboot.utils.ThreadUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
@AllArgsConstructor
public class ScheduledTasks {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     *  秒   分   时    日   月   周几
     *  0    *    *    *    *   MON-FRI
     */
    @Scheduled(cron = "10 0 0 * * ?")
    public void AnalysisLogTask() {
        log.info("AnalysisLogTask start：" + dateFormat.format(new Date()));
        ThreadUtils.sleepUtil(10000);
        log.info("AnalysisLogTask end：" + dateFormat.format(new Date()));
    }
}
