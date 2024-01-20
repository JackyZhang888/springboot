package com.example.plugin;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.plugin.MybatisInterceptor;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class MybatisInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Autowired
    private MybatisInterceptor mybatisInterceptor;

    @PostConstruct
    public void addMysqlInterceptor() {
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
          //  org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
          //  configuration.addInterceptor(sqlInterceptor);
        }
    }
}