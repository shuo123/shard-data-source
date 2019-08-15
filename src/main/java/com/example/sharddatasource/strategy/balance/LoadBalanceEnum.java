package com.example.sharddatasource.strategy.balance;

import com.example.sharddatasource.strategy.balance.impl.RandomLoadBalanceStrategy;

/**
 * 负载均衡策略枚举
 *
 * @author wws
 * @version 1.0.0
 * @date 2019-08-15 15:54
 **/
public enum LoadBalanceEnum {

    /**
     * 随机
     */
    RANDOM("random", new RandomLoadBalanceStrategy());

    private String name;

    private LoadBalanceStrategy strategy;

    LoadBalanceEnum(String name, LoadBalanceStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public static LoadBalanceStrategy getStrategy(String name) {
        if (RANDOM.name.equals(name)) {
            return RANDOM.strategy;
        }
        return RANDOM.strategy;
    }
}
