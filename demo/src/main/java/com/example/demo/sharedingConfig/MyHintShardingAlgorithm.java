package com.example.demo.sharedingConfig;


import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/5/17 10:47
 * @Version: 1.0.0
 * @Description: 描述
 */
public class MyHintShardingAlgorithm implements HintShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Integer> hintShardingValue) {
        Iterator<String> tables = collection.iterator();
        Collection<String> cl = new ArrayList<>();
        while (tables.hasNext()){
            String table = tables.next();
            Iterator<Integer> clies = hintShardingValue.getValues().iterator();
            while (clies.hasNext()){
                Integer clie = clies.next();
                if(table.endsWith("_"+clie)){
                    cl.add(table);
                }
            }
        }
        return cl;
    }
}
