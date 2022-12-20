package com.example.demo.redis.core;

import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.ZSetOperations;
import java.io.Serializable;
import java.util.Set;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2022/12/20 17:46
 * @Version: 1.0.0
 * @Description: 描述
 */
public interface RedisZSetOperations {
    
   
    Boolean add(RedisKeyNs key, Serializable id, String value, double score);

    Long add(RedisKeyNs key, Serializable id, Set<ZSetOperations.TypedTuple<String>> typedTuples);

    Long remove(RedisKeyNs key, Serializable id, Object... values);

    Double incrementScore(RedisKeyNs key, Serializable id, String value, double delta);

    Long rank(RedisKeyNs key, Serializable id, Object o);

    Long reverseRank(RedisKeyNs key, Serializable id, Object o);

    Set<String> range(RedisKeyNs key, Serializable id, long start, long end);

    Set<ZSetOperations.TypedTuple<String>> rangeWithScores(RedisKeyNs key, Serializable id, long start, long end);

    Set<String> rangeByScore(RedisKeyNs key, Serializable id, double min, double max);

    Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max);

    Set<String> rangeByScore(RedisKeyNs key, Serializable id, double min, double max, long offset, long count);

    Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max, long offset, long count);

    Set<String> reverseRange(RedisKeyNs key, Serializable id, long start, long end);

    Set<ZSetOperations.TypedTuple<String>> reverseRangeWithScores(RedisKeyNs key, Serializable id, long start, long end);

    Set<String> reverseRangeByScore(RedisKeyNs key, Serializable id, double min, double max);

    Set<ZSetOperations.TypedTuple<String>> reverseRangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max);

    Set<String> reverseRangeByScore(RedisKeyNs key, Serializable id, double min, double max, long offset, long count);

    Set<ZSetOperations.TypedTuple<String>> reverseRangeByScoreWithScores(RedisKeyNs key, Serializable id, double min, double max, long offset, long count);

    Long count(RedisKeyNs key, Serializable id, double min, double max);

    Long size(RedisKeyNs key, Serializable id);

    Long zCard(RedisKeyNs key, Serializable id);

    Double score(RedisKeyNs key, Serializable id, Object o);

    Long removeRange(RedisKeyNs key, Serializable id, long start, long end);

    Long removeRangeByScore(RedisKeyNs key, Serializable id, double min, double max);

    Set<String> rangeByLex(RedisKeyNs key, Serializable id, RedisZSetCommands.Range range);

    Set<String> rangeByLex(RedisKeyNs key, Serializable id, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit);
        

}
