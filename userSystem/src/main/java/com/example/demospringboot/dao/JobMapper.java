package com.example.demospringboot.dao;

import org.apache.ibatis.annotations.Param;

public interface JobMapper {
    int getSendmail();

    void setSendmail(@Param("cnt") int cnt);

    int getAnalysisLog();

    void setAnalysisLog(@Param("cnt") int cnt);

}
