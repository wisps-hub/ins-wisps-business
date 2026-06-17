package com.wisps.auth.provider.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfig {

    @Value("${spring.data.redis.lettuce.pool.enabled:false}")
    protected boolean poolEnabled;
    @Value("${spring.data.redis.host:localhost}")
    private String host;
    @Value("${spring.data.redis.port:6379}")
    private Integer port;
    @Value("${spring.data.redis.password}")
    protected String password;
    @Value("${spring.data.redis.database:0}")
    protected int database;


    @Bean("CustomizeStringRedisTemplate")
    public StringRedisTemplate redisTemplate(RedisConnectionFactory cf) {
        if (cf instanceof LettuceConnectionFactory) {
            ((LettuceConnectionFactory) cf).setValidateConnection(true);
            log.info("LettuceConnectionFactory setValidateConnection is true");
            if (poolEnabled) {
                ((LettuceConnectionFactory) cf).setShareNativeConnection(false);
            }
            log.info("LettuceConnectionFactory setShareNativeConnection is {}", !poolEnabled);
        } else if (cf instanceof JedisConnectionFactory) {
            ((JedisConnectionFactory) cf).getPoolConfig().setTestOnBorrow(true);
            GenericObjectPoolConfig poolConfig = ((JedisConnectionFactory) cf).getPoolConfig();
            log.info("JedisConnectionFactory JedisPool stats ➜ poolConfig={}", JSONUtil.toJsonStr(poolConfig));
        }
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(cf);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        try {
            config.useSingleServer()
                    .setAddress("redis://" + host + ":" + port)
                    .setPassword(StrUtil.isBlank(password) ? null : password)
                    .setDatabase(database);
            return Redisson.create(config);
        } catch (Exception e) {
            log.warn("redis init error redis host:{} password:{}", host, password, e);
            throw e;
        }
    }

}