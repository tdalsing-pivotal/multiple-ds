package com.vmware.multiple.ds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@SpringBootApplication
@RestController
@Slf4j
public class MultipleDSApp {

    JdbcTemplate primaryJdbcTemplate;
    JdbcTemplate secondaryJdbcTemplate;

    public MultipleDSApp(JdbcTemplate primaryJdbcTemplate, JdbcTemplate secondaryJdbcTemplate) {
        this.primaryJdbcTemplate = primaryJdbcTemplate;
        this.secondaryJdbcTemplate = secondaryJdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(MultipleDSApp.class, args);
    }

    @Bean
    public static JdbcTemplate primaryJdbcTemplate(DataSource primaryDataSource) {
        return new JdbcTemplate(primaryDataSource);
    }

    @Bean
    public static JdbcTemplate secondaryJdbcTemplate(DataSource secondaryDataSource) {
        return new JdbcTemplate(secondaryDataSource);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void go() {
        log.info("go");
        String sql = "insert into test_table (test_id,test_name) values (1,'name-1')";

        try {
            primaryJdbcTemplate.update(sql);
        } catch (Exception e) {
            log.error("go: primary: e = {}", e.toString());
        }

        try {
            secondaryJdbcTemplate.update(sql);
        } catch (Exception e) {
            log.error("go: primary: e = {}", e.toString());
        }
    }
}
