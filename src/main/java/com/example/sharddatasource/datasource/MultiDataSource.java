package com.example.sharddatasource.datasource;

import com.example.sharddatasource.strategy.balance.LoadBalanceEnum;
import com.example.sharddatasource.strategy.balance.LoadBalanceStrategy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.List;

/**
 * @author wws
 * @version 1.0.0
 * @date 2019-08-14 14:13
 **/
public class MultiDataSource extends AbstractRoutingDataSource {

    private List<String> slaveNames;

    private LoadBalanceStrategy  loadBalanceStrategy;

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceKey = MultiDataSourceHolder.getDataSourceKey();
        return getDataSourceKey(dataSourceKey);
    }

    private String getDataSourceKey(String dataSourceKey){
        if(dataSourceKey.startsWith(MultiDataSourceHolder.SLAVE)){
            return MultiDataSourceHolder.SLAVE + "-" + loadBalanceStrategy.select(slaveNames);
        }
        return dataSourceKey;
    }

    public List<String> getSlaveNames() {
        return slaveNames;
    }

    public void setSlaveNames(List<String> slaveNames) {
        this.slaveNames = slaveNames;
    }

    public LoadBalanceStrategy getLoadBalanceStrategy() {
        return loadBalanceStrategy;
    }

    public void setLoadBalanceStrategy(String loadBalance) {
        this.loadBalanceStrategy = LoadBalanceEnum.getStrategy(loadBalance);
    }
}
