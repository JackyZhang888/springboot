package com.example;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@SpringBootApplication
@MapperScan(value = {"com.example.dao"})
public class Main implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... strings) throws SQLException {
        initDatabase();
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