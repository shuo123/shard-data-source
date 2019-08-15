package com.example.sharddatasource.strategy.balance.impl;

import com.example.sharddatasource.strategy.balance.LoadBalanceStrategy;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机负载均衡
 *
 * @author wws
 * @version 1.0.0
 * @date 2019-08-15 15:50
 **/
public class RandomLoadBalanceStrategy implements LoadBalanceStrategy {

    @Override
    public String select(List<String> dataSources) {
        int size = dataSources.size();
        return dataSources.get(ThreadLocalRandom.current().nextInt(size));
    }

}
