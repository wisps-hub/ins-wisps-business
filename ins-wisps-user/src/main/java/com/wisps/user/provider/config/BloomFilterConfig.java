package com.wisps.user.provider.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BloomFilterConfig {

    /**
     * 用户名布隆过滤器
     */
    @Bean("nickNameBloomFilter")
    public RBloomFilter<String> nickNameBloomFilter(RedissonClient redissonClient){
        RBloomFilter<String> nickNameBloomFilter = redissonClient.getBloomFilter("nickName");
        if (nickNameBloomFilter != null && !nickNameBloomFilter.isExists()) {
            nickNameBloomFilter.tryInit(100000L, 0.01);
        }
        return nickNameBloomFilter;
    }

    /**
     * 邀请码布隆过滤器
     */
    @Bean("inviteCodeBloomFilter")
    public RBloomFilter<String> inviteCodeBloomFilter(RedissonClient redissonClient){
        RBloomFilter<String> inviteCodeBloomFilter = redissonClient.getBloomFilter("inviteCode");
        if (inviteCodeBloomFilter != null && !inviteCodeBloomFilter.isExists()) {
            inviteCodeBloomFilter.tryInit(100000L, 0.01);
        }
        return inviteCodeBloomFilter;
    }

    /**
     * 邀请排行榜
     */
    @Bean("inviteRank")
    public RScoredSortedSet<String> inviteRank(RedissonClient redissonClient){
        return redissonClient.getScoredSortedSet("inviteRank");
    }

}