package com.shopping.sso.service;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.pojo.TbUser;
public interface UserRegisterService {

	public shoppingResult checkData(String param,Integer type);
	public shoppingResult register(TbUser user);
}
