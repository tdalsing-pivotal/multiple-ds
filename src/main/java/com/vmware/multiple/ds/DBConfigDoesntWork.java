package com.vmware.multiple.ds;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Profile("doesntwork")
@Configuration
public class DBConfigDoesntWork {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.secondary-datasource")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }
}
