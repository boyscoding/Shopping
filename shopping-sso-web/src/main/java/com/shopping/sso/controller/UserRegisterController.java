package com.shopping.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.pojo.TbUser;
import com.shopping.sso.service.UserRegisterService;

@Controller
public class UserRegisterController {
	@Autowired UserRegisterService registerService;
	
	@RequestMapping(value="/user/check/{param}/{type}",method=RequestMethod.GET)
	@ResponseBody
	public shoppingResult checkData(@PathVariable String param,@PathVariable Integer type ) {
		return registerService.checkData(param, type);
	}
	
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public shoppingResult register(TbUser user ) {
		return  registerService.register(user);
	}

}
