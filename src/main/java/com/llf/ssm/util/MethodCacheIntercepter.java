package com.llf.ssm.util;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 缓存拦截器
 * @author Administrator
 *
 */
public class MethodCacheIntercepter implements MethodInterceptor {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	private RedisUtil redisUtil;
	// 不加入缓存的Service名
	private List<String> targetNameList;
	// 不加入缓存的方法名
	private List<String> methodNameList;
	// 默认缓存过期时间
	private Long defaultCacheExpireTime;
	// 
	private Long xxxRecordManagerTime;
	// 
	private Long xxxSetRecordManagerTime;
	
	public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
	
	/**
	 * 初始化，读取不需加入缓存的类和方法名
	 */
	public MethodCacheIntercepter() {
		try {
			String[] targetNames = {};
			String[] methodNames = {};
			// 过期时间设置
			defaultCacheExpireTime = 3600L;
			xxxRecordManagerTime = 60L;
			xxxSetRecordManagerTime = 60L;
			// 创建list
			targetNameList = new ArrayList<String>(targetNames.length);
			methodNameList = new ArrayList<String>(methodNames.length);
			Integer maxLen = targetNames.length > methodNames.length ? targetNames.length : methodNames.length;
			
			// 不需加入缓存的类和方法名
			for(int i = 0; i < maxLen; i++) {
				if(i < targetNames.length) {
					targetNameList.add(targetNames[i]);
				}
				if(i < methodNames.length) {
					methodNameList.add(methodNames[i]);
				}
			}
			
			
		} catch(Exception e) {
			logger.error("初始化缓存拦截器失败", e);
		}
	}
	
	/**
     * 是否加入缓存
     *
     * @return
     */
    private boolean isAddCache(String targetName, String methodName) {
        boolean flag = true;
        if (targetNameList.contains(targetName)
                || methodNameList.contains(methodName)) {
            flag = false;
        }
        return flag;
    }
    
    /**
     * 创建缓存key
     *
     * @param targetName
     * @param methodName
     * @param arguments
     */
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {
        StringBuffer sbu = new StringBuffer();
        sbu.append(targetName).append("_").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sbu.append("_").append(arguments[i]);
            }
        }
        return sbu.toString();
    }
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		Object value = null;
		String targetName = invocation.getThis().getClass().getName();
	    String methodName = invocation.getMethod().getName();
	    // 不需要缓存
	    if (!isAddCache(targetName, methodName)) {
            // 执行方法返回结果
            return invocation.proceed();
        }
	    Object[] arguments = invocation.getArguments();
        String key = getCacheKey(targetName, methodName, arguments);
        logger.debug("redisKey: " + key);
        try {
            // 判断是否有缓存
            if (redisUtil.exists(key)) {
                return redisUtil.get(key);
            }
            // 写入缓存
            value = invocation.proceed();
            if (value != null) {
                final String tkey = key;
                final Object tvalue = value;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (tkey.startsWith("com.service.impl.xxxRecordManager")) {
                            redisUtil.set(tkey, tvalue, xxxRecordManagerTime);
                        } else if (tkey.startsWith("com.service.impl.xxxSetRecordManager")) {
                            redisUtil.set(tkey, tvalue, xxxSetRecordManagerTime);
                        } else {
                            redisUtil.set(tkey, tvalue, defaultCacheExpireTime);
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            logger.error("缓存拦截器处理异常", e);
            if (value == null) {
                return invocation.proceed();
            }
        }
        return value;
	}
	
	
	
}
