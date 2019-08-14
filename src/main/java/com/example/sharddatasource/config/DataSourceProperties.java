package com.example.sharddatasource.config;

import com.example.sharddatasource.datasource.MultipleDataSource;
import com.example.sharddatasource.datasource.MultipleDataSourceHolder;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wws
 * @version 1.0.0
 * @date 2019-08-14 15:19
 **/
@Configuration
public class DataSourceProperties {

    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    private String masterUrl = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";

    private String masterUserName = "root";

    private String masterPassword = "root";

    private String salveUrl = "jdbc:mysql://localhost:3306/test-1?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";

    private String salveUserName = "root";

    private String salvePassword = "root";

    @Bean
    @Primary
    public DataSource dataSource(){
        HikariDataSource master = new HikariDataSource();
        master.setJdbcUrl(masterUrl);
        master.setDriverClassName(driverClassName);
        master.setUsername(masterUserName);
        master.setPassword(masterPassword);

        HikariDataSource salve = new HikariDataSource();
        salve.setJdbcUrl(salveUrl);
        salve.setDriverClassName(driverClassName);
        salve.setUsername(salveUserName);
        salve.setPassword(salvePassword);

        Map<Object, Object> map = new HashMap<>(16);
        map.put(MultipleDataSourceHolder.MASTER, master);
        map.put(MultipleDataSourceHolder.SLAVE, salve);

        MultipleDataSource dataSource = new MultipleDataSource();
        dataSource.setDefaultTargetDataSource(master);
        dataSource.setTargetDataSources(map);

        return dataSource;
    }

}
