package com.example.demo.sharedingConfig;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Collection;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/7/26 10:07
 * @Version: 1.0.0
 * @Description: 描述
 */
public class MyDataSourceAlgorithm implements HintShardingAlgorithm<String> {
    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<String> hintShardingValue) {
        return hintShardingValue.getValues();
    }
}
