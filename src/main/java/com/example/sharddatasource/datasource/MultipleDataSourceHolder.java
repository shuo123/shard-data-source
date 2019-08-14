package com.example.sharddatasource.datasource;

/**
 * @author wws
 * @version 1.0.0
 * @date 2019-08-14 14:32
 **/
public class MultipleDataSourceHolder {

    private static final ThreadLocal<String> dataSourceKey = new ThreadLocal<>();

    public static final String MASTER = "masterDataSource";

    public static final String SLAVE = "slaveDataSource";

    private MultipleDataSourceHolder() {
    }

    public static void setDataSourceKey(String key){
        dataSourceKey.set(key);
    }

    public static String getDataSourceKey(){
        String key = dataSourceKey.get();
        if(key == null){
            return MASTER;
        }
        return key;
    }

    public static void clear(){
        dataSourceKey.remove();
    }
}
