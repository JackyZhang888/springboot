package com.example.demospringboot.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Job {
    private int id;
    private int sendmail;
    private int analysisLog;
}
