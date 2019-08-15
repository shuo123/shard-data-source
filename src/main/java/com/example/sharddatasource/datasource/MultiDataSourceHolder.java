package com.example.sharddatasource.datasource;

/**
 * @author wws
 * @version 1.0.0
 * @date 2019-08-14 14:32
 **/
public class MultiDataSourceHolder {

    private static final ThreadLocal<String> DATA_SOURCE_KEY = new ThreadLocal<>();

    public static final String MASTER = "masterDataSource";

    public static final String SLAVE = "slaveDataSource";

    private MultiDataSourceHolder() {
    }

    public static void setDataSourceKey(String key){
        DATA_SOURCE_KEY.set(key);
    }

    static String getDataSourceKey(){
        String key = DATA_SOURCE_KEY.get();
        if(key == null){
            return MASTER;
        }
        return key;
    }

    public static void clear(){
        DATA_SOURCE_KEY.remove();
    }
}
