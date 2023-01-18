package com.example.demo.redis.core;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/20 17:46
 * @Version: 1.0.0
 * @Description: redis有序集合操作
 */
public interface RedisZSetOperations {
    
   
    Boolean add(RedisKeyNs key, Serializable id, String value, double score);

    Long add(RedisKeyNs key, Serializable id, Set<ZSetDataModel> typedTuples);

    Long remove(RedisKeyNs key, Serializable id, String... values);

    /**
     * 权重自增
     * @param key
     * @param id
     * @param value
     * @param delta
     * @return
     */
    Double incrementScore(RedisKeyNs key, Serializable id, String value, double delta);

    /**
     * 查排名
     * @param key
     * @param id
     * @param value
     * @return
     */
    Long rank(RedisKeyNs key, Serializable id, String value);

    /**
     * 查反向排名
     * @param key
     * @param id
     * @param value
     * @return
     */
    Long reverseRank(RedisKeyNs key, Serializable id, String value);

    /**
     * 获取排序在{@start}至{@end}之间的元素
     * @param key
     * @param id
     * @param start
     * @param end
     * @return
     */
    Set<String> range(RedisKeyNs key, Serializable id, long start, long end);

    /**
     * 从已排序的set中获取介于{@code start}和{@code end}之间的元素（带权重）
     * @param key
     * @param id
     * @param start
     * @param end
     * @return
     */
    Set<ZSetDataModel> rangeWithScores(RedisKeyNs key, Serializable id, long start, long end);

    /**
     * 从已排序的集合中获取得分介于{@code min}和{@code max}之间的元素
     * @param key
     * @param id
     * @param min
     * @param max
     * @return
     */
    Set<String> rangeByScore(RedisKeyNs key, Serializable id, double min, double max);

    /**
     * 从已排序的set中获取得分介于{@code min}和{@code max}之间的元素（带权重）
     * @param key
     * @param id
     * @param min
     * @param max
     * @return
     */
    Set<ZSetDataModel> rangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max);

    /**
     * 从已排序的set中获取范围从{@code start}到{@code end}的元素，其中得分在{@code min}和* {@code max}之间
     * @param key
     * @param id
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    Set<String> rangeByScore(RedisKeyNs key, Serializable id, double min, double max, long offset, long count);
    /**
     * 从已排序的set中获取范围从{@code start}到{@code end}的元素（带权重），其中得分在{@code min}和* {@code max}之间
     * @param key
     * @param id
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    Set<ZSetDataModel> rangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max, long offset, long count);

    Set<String> reverseRange(RedisKeyNs key, Serializable id, long start, long end);

    Set<ZSetDataModel> reverseRangeWithScores(RedisKeyNs key, Serializable id, long start, long end);

    Set<String> reverseRangeByScore(RedisKeyNs key, Serializable id, double min, double max);

    Set<ZSetDataModel> reverseRangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max);

    Set<String> reverseRangeByScore(RedisKeyNs key, Serializable id, double min, double max, long offset, long count);

    Set<ZSetDataModel> reverseRangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max, long offset, long count);

    Long count(RedisKeyNs key, Serializable id, double min, double max);

    Long size(RedisKeyNs key, Serializable id);

    Long zCard(RedisKeyNs key, Serializable id);

    Double score(RedisKeyNs key, Serializable id, String value);

    Long removeRange(RedisKeyNs key, Serializable id, long start, long end);

    Long removeRangeByScore(RedisKeyNs key, Serializable id, double min, double max);

    Set<String> rangeByLex(RedisKeyNs key, Serializable id, ZSetRange range);

    Set<String> rangeByLex(RedisKeyNs key, Serializable id, ZSetRange range, ZSetLimit limit);
        

}
