package com.xinhuanet.redis;

import com.xinhuanet.config.Conf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
public class RedisRepository {

    private static final Logger logger = LoggerFactory.getLogger(RedisRepository.class);

    @Autowired
    private Conf conf;

    /**
     * 配置jedispool
     */
    @Bean
    @Primary
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setMaxWaitMillis(2000);

        //(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password)
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,
                conf.getRedishost(),
                conf.getRedisport(),
                conf.getRedistimeout(),
                conf.getRedispassword());

        return jedisPool;
    }

    @Autowired
    protected JedisPool jedisPool;

    //====================================================================================================================//
    //============================================[通用方法]===============================================================//
    //====================================================================================================================//

    /**
     * 通用方法：模糊查询keys
     */
    public TreeSet<String> keys(String pattern) {
        TreeSet<String> keys = new TreeSet<>();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            keys.addAll(jedis.keys("*" + pattern + "*"));
            logger.debug("Keys gotten!");
            return keys;
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * 通用方法：删除key
     */
    public void delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            logger.debug("RedisRepository:delete cache key={}", key);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    //====================================================================================================================//
    //============================================[String类型]============================================================//
    //====================================================================================================================//

    /**
     * String类型：插入(永不超时)
     */
    public void set(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * String类型：插入(带超时)
     */
    public void setWithExpireTime(String key, String value, int expireTime) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            jedis.setex(key, expireTime, value);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * String类型：查询
     */
    public String get(String key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            logger.debug("RedisRepository:get cache key={},value={}", key, value);
            return value;
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    //====================================================================================================================//
    //==============================================[SET类型]=============================================================//
    //====================================================================================================================//
    //========================与List类型类似    最大元素数量是4294967295    不允许出现重复的元素    无序========================//
    //====================================================================================================================//

    /**
     * Set类型：插入元素(key自动创建/删除)
     */
    public void sadd(String key, String... element) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.sadd(key, element);
            logger.debug("RedisRepository:sadd cache key={},element={}", key, element);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * Set类型：查询元素是否存在
     */
    public boolean sismember(String key, String element) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, element);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * Set类型：查询所有元素
     */
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(key);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * Set类型：删除对应元素
     */
    public Long srem(String key, String... element) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key, element);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    //====================================================================================================================//
    //==============================================[LIST类型]=============================================================//
    //====================================================================================================================//
    //============================字符串链表    最大元素数量是4294967295    可重复    有序====================================//
    //====================================================================================================================//

    /**
     * List类型：插入元素(尾部)(key自动创建/删除)
     */
    public void rpush(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.rpush(key, value);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    /**
     * List类型：查询所有元素
     */
    public List<String> lrange(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(key, 0, -1);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection lost.", e);
            throw e;
        } finally {
            closeResource(jedis);
        }
    }

    //====================================================================================================================//

    /**
     * 关闭jedis
     */
    protected void closeResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
