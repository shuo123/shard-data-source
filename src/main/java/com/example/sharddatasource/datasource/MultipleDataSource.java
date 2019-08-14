package com.example.sharddatasource.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author wws
 * @version 1.0.0
 * @date 2019-08-14 14:13
 **/
public class MultipleDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return MultipleDataSourceHolder.getDataSourceKey();
    }

}
