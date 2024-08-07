package com.github.vladimirpokhodnya.authjwtapi.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class H2Config {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource() {{
            setDriverClassName("org.h2.Driver");
            setUrl("jdbc:h2:~/testdb");
            setUsername("sa");
            setPassword("");
        }};
    }

}
