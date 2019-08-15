package com.example.sharddatasource.config;

import com.example.sharddatasource.datasource.MultiDataSource;
import com.example.sharddatasource.datasource.MultiDataSourceHolder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wws
 * @version 1.0.0
 * @date 2019-08-14 16:06
 **/
@Configuration
public class MultiDataSourceConfig {

    private DataSource master;

    private Map<String, DataSource> slaves;

    private String[] slaveNames;

    private final Environment env;

    public MultiDataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public DataSource multiDataSource() throws ClassNotFoundException {
        initMaster();
        initSlave();

        Map<Object, Object> map = new HashMap<>(16);
        map.put(MultiDataSourceHolder.MASTER, master);
        for (String slaveName : slaveNames) {
            map.put(MultiDataSourceHolder.SLAVE + "-" + slaveName, slaves.get(slaveName));
        }

        MultiDataSource dataSource = new MultiDataSource();
        dataSource.setDefaultTargetDataSource(master);
        dataSource.setTargetDataSources(map);
        dataSource.setSlaveNames(Arrays.asList(slaveNames));
        dataSource.setLoadBalanceStrategy(getLoadBalance());
        return dataSource;
    }

    private void initMaster() throws ClassNotFoundException {
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(env);
        Binder binder = new Binder(sources);
        BindResult<DataSourceConfig> bindResult = binder.bind("multi.jdbc.datasource.master", DataSourceConfig.class);
        DataSourceConfig dataSourceConfig = bindResult.get();
        this.master = buildDataSource(dataSourceConfig);
    }

    private void initSlave() throws ClassNotFoundException {
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(env);
        Binder binder = new Binder(sources);
        BindResult<String> bindResult = binder.bind("multi.jdbc.datasource.slaves.names", String.class);
        String names = bindResult.get();
        this.slaveNames = names.split(",");
        slaves = new HashMap<>(slaveNames.length);
        for (String slaveName : slaveNames) {
            BindResult<DataSourceConfig> dataSourceConfigBindResult
                    = binder.bind("multi.jdbc.datasource.slaves." + slaveName, DataSourceConfig.class);
            DataSourceConfig dataSourceConfig = dataSourceConfigBindResult.get();
            DataSource dataSource = buildDataSource(dataSourceConfig);
            this.slaves.put(slaveName, dataSource);
        }
    }

    private DataSource buildDataSource(@NotNull DataSourceConfig dataSourceConfig) throws ClassNotFoundException {
        String type = dataSourceConfig.getType();
        Class<? extends DataSource> dataSourceClazz = (Class<? extends DataSource>) Class.forName(type);
        return DataSourceBuilder.create().type(dataSourceClazz)
                .driverClassName(dataSourceConfig.getDriverClassName())
                .url(dataSourceConfig.getUrl())
                .username(dataSourceConfig.getUsername())
                .password(dataSourceConfig.getPassword())
                .build();
    }

    private String getLoadBalance(){
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(env);
        Binder binder = new Binder(sources);
        BindResult<String> bindResult = binder.bind("multi.jdbc.datasource.slaves.load-balance", String.class);
        return bindResult.get();
    }

    @Data
    private static class DataSourceConfig {

        private String driverClassName;

        private String url;

        private String username;

        private String password;

        private String type = "com.zaxxer.hikari.HikariDataSource";
    }

}
