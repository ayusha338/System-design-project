package com.bookstore.phase2_spring_jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class AppConfig {
    public static DataSource getDataSOurce(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3308/bookstore_db");
        config.setUsername("bookstore_user");
        config.setPassword("bookstore_pass");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Connection Pool settings
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(20000);
        config.setPoolName("BookstoreHikariPool");
        return new HikariDataSource(config);
    }
    public static JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(getDataSOurce());
    }
}
