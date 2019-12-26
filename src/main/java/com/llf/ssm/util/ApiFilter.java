package com.llf.ssm.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ApiFilter implements Filter {
	
	private String[] exceptionApiPathArray;
	
	private SystemConfig systemConfig;

	private RedisUtil redisUtil;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest)arg0; 
	    HttpServletResponse response=(HttpServletResponse) arg1;
	    // 跨域支持，测试用
	    response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        //arg2.doFilter(arg0, arg1);
		
        boolean isException = false;
	    String url = request.getServletPath();
	    for(int i = 0; i < exceptionApiPathArray.length; i++ ) {
    			int index = exceptionApiPathArray[i].indexOf("*");
	    		if(index != -1) {
	    			if(url.length() >= index) {
	    				String sub = url.substring(0, index);
	    				if(sub.equals(exceptionApiPathArray[i].substring(0, index))) {
		    				isException = true;
		    				break;
	    				}
	    			}
	    		} else {
	    			if(url.equals(exceptionApiPathArray[i])) {
	    				isException = true;
	    				break;
	    			}
	    		}	    		
	    }
	    if(isException) {
    			arg2.doFilter(arg0, arg1);
	    } else {
		    	String key = request.getHeader("key");
				String token = request.getHeader("token");
	    		if(key == null && token == null) {
	    			key = request.getParameter("key");
	    			token = request.getParameter("token");
	    		}
	    		if(key != null && token != null) {
	    			Token t = (Token) redisUtil.get(key);
		    		// long expireTime = systemConfig.getActiveTime() * 10 * 60 * 1000;
		    		if(t != null && t.getToken() != null && t.getToken().equals(token)) {
		    			// redisUtil.set(key, t, expireTime);
		    			redisUtil.set(key, t);
		    			arg2.doFilter(arg0, arg1);
		    		} else {
		    			// token无效或过期
		    			ResultJSON result = new ResultJSON();
		    			result.setStatus(StatusEnum.OVERDUEL.getKey());
		    			result.setMsg(StatusEnum.OVERDUEL.getMsg());
		    			response.setContentType("text/html;charset=utf-8");  
		    			response.getWriter().write(JsonUtil.objToJsonString(result));
		    			response.flushBuffer();
		    		}
	    		} else {
	    			// 没有key或token，无权限访问
	    			ResultJSON result = new ResultJSON();
	    			result.setStatus(StatusEnum.WITHOUTPERMISSION.getKey());
	    			result.setMsg(StatusEnum.WITHOUTPERMISSION.getMsg());
	    			response.setContentType("text/html;charset=utf-8");  
	    			response.getWriter().write(JsonUtil.objToJsonString(result));
	    			response.flushBuffer();
	    		}
	    }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = arg0.getServletContext();
		WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(context);
		systemConfig = (SystemConfig) appContext.getBean("systemConfig");
		redisUtil = (RedisUtil) appContext.getBean("redisUtil");			
		String str = systemConfig.getExceptionApiPaths();
		exceptionApiPathArray = str.split(",");
	}

}
