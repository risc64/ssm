package com.llf.ssm.util;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisUtil {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	private RedisTemplate<Serializable, Object> redisTemplate;
	
	/**
	 * 缓存中是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}
	/**
	 * 从缓存中删除
	 * @param key
	 */
	public void remove(final String key) {
		if(exists(key)) {
			redisTemplate.delete(key);
		}
	}
	/**
	 * 从缓存中批量删除
	 * @param keys
	 */
	public void remove(final String... keys) {
		for(String key : keys) {
			remove(key);
		}
	}
	/**
	 * 从缓存中读取
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}
	/**
	 * 写入缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			logger.error("写入缓存异常", e);
		}
		return result;
	}
	/**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("系统异常",e);
        }
        return result;
    }

    public void setRedisTemplate(
            RedisTemplate<Serializable, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
	
}
