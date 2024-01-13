package com.example.demospringboot;

import com.example.demospringboot.job.SchedJobManager;
import com.example.demospringboot.job.AsyncJobManager;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.cache.annotation.EnableCaching;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import com.spring4all.swagger.EnableSwagger2Doc;

@Slf4j
@EnableCaching
@EnableAsync
@SpringBootApplication
@EnableSwagger2Doc
// 需要指定扫描的类，并在配置文件指定mybatis.mapper-locations为对应的xml路径
@MapperScan(value = {"com.example.demospringboot.dao"})
public class DemospringbootApplication implements CommandLineRunner {
    @Autowired
    private SchedJobManager schedJobManager;

    @Autowired
    private AsyncJobManager asyncJobManager;

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(DemospringbootApplication.class, args);
    }

    @Override
    public void run(String... strings) throws SQLException {
        initDatabase();
        schedJobManager.startSchedExecutor();
        asyncJobManager.startSyncExecutor();
    }

    private void initDatabase() throws SQLException {
        log.info("======== 自动初始化数据库开始 ========");
        Resource initData = new ClassPathResource("schema.sql");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            ScriptUtils.executeSqlScript(connection, initData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        log.info("======== 自动初始化数据库结束 ========");
    }
}
