package com.example.demospringboot.service.impl;

import com.example.demospringboot.task.ScheduledTasks;
import com.example.demospringboot.dao.JobMapper;
import com.example.demospringboot.service.SchedUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SchedUserServiceImpl implements SchedUserService {

    @Autowired
    private ScheduledTasks scheduledTasks;

    @Autowired
    JobMapper jobMapper;

    @Override
    public void AnalysisLogTask() {
        if (jobMapper.getAnalysisLog() > 0) {
            scheduledTasks.AnalysisLogTask();
        }
    };
}

