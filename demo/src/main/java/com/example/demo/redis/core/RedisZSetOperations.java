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

    Double incrementScore(RedisKeyNs key, Serializable id, String value, double delta);

    Long rank(RedisKeyNs key, Serializable id, String value);

    Long reverseRank(RedisKeyNs key, Serializable id, String value);

    Set<String> range(RedisKeyNs key, Serializable id, long start, long end);

    Set<ZSetDataModel> rangeWithScores(RedisKeyNs key, Serializable id, long start, long end);

    Set<String> rangeByScore(RedisKeyNs key, Serializable id, double min, double max);

    Set<ZSetDataModel> rangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max);

    Set<String> rangeByScore(RedisKeyNs key, Serializable id, double min, double max, long offset, long count);

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
