package com.shopping.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.common.util.CookieUtils;
import com.shopping.sso.service.UserLoginService;

public class LoginInterceptor  implements HandlerInterceptor {
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	
	@Value("${SSO_URL}")
	private String SSO_URL;
	
	@Autowired
	private UserLoginService loginservice;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		if(StringUtils.isEmpty(token)) {
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL().toString());
			return false;
		}
		shoppingResult result = loginservice.getUserByToken(token);
		if(result.getStatus()!=200){
			//5.用户已经过期  --》重定向到登录的页面
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL().toString());
			return false;
		}
		request.setAttribute("USER_INFO", result.getData());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
