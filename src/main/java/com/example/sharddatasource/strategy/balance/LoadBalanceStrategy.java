package com.example.sharddatasource.strategy.balance;

import java.util.List;

/**
 * 负载均衡策略
 *
 * @author wws
 * @version 1.0.0
 * @date 2019-08-15 15:46
 **/
public interface LoadBalanceStrategy {

    String select(List<String> dataSources);

}
