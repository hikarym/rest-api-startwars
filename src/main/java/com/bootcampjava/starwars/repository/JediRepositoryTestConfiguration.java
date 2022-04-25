package com.bootcampjava.starwars.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class JediRepositoryTestConfiguration {


    @Primary
    @Bean
    public DataSource dataSource() {

        //setup satasource para os testes
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

}
