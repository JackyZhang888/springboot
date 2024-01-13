package com.example.demospringboot.service.impl;

import com.example.demospringboot.dao.JobMapper;
import com.example.demospringboot.service.AsyncUserService;
import com.example.demospringboot.task.AsyncTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncUserServiceImpl implements AsyncUserService {

    @Autowired
    private AsyncTasks asyncTasks;

    @Autowired
    JobMapper jobMapper;

    @Override
    public void sendMailTask() {
        if (jobMapper.getSendmail() > 0) {
            asyncTasks.doAsyncTask("doSendMailTask");
            jobMapper.setSendmail(0); // 发送结束标记
        }
    };
}
