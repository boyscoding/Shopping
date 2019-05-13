package com.shopping.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.common.util.CookieUtils;
import com.shopping.sso.service.UserLoginService;

@Controller
public class UserLoginController {
	@Autowired
	private UserLoginService userLoginService;
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	@RequestMapping(value="/user/login",method =RequestMethod.POST)
	public shoppingResult login(HttpServletRequest request,HttpServletResponse response, String username,String password) {
		shoppingResult result = userLoginService.login(username, password);
		if(result.getStatus()==200) {
			CookieUtils.setCookie(request, response, TT_TOKEN_KEY,result.getData().toString());
		}
		return result;
		
	}
	
	@RequestMapping(value="/user/token/{token}",method = RequestMethod.GET)
	@ResponseBody
	public shoppingResult getUseTaotaoResult(@PathVariable String token) {
		return userLoginService.getUserByToken(token);
	}
	@RequestMapping("/page/{page}")
	public String showPage(@PathVariable String page,String redirect,Model model){
		model.addAttribute("redirect", redirect);
		return page;
	}
}
