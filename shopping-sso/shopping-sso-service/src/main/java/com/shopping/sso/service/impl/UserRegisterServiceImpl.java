package com.shopping.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.mapper.TbUserMapper;
import com.shopping.pojo.TbUser;
import com.shopping.pojo.TbUserExample;
import com.shopping.pojo.TbUserExample.Criteria;
import com.shopping.sso.service.UserRegisterService;
@Service
public class UserRegisterServiceImpl implements UserRegisterService{
	@Autowired
	private TbUserMapper usermapper;
	@Override
	public shoppingResult checkData(String param, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type == 1) {
			if(StringUtils.isEmpty(param)) {
				return shoppingResult.ok(false);
			}
			criteria.andUsernameEqualTo(param);
		}
		else if(type == 2){
			criteria.andPhoneNotEqualTo(param);
		}
		else if(type == 3) {
			criteria.andEmailEqualTo(param);
		}
		else {
			return shoppingResult.build(400, "非法参数");
		}
		List<TbUser> list = usermapper.selectByExample(example);
		if(list != null && list.size()>0) {
			return shoppingResult.ok(false);
		}
		return shoppingResult.ok(true);
	}
	@Override
	public shoppingResult register(TbUser user) {
		if(StringUtils.isEmpty(user.getUsername())) {
				return shoppingResult.build(400, "注册失败");
		}
		shoppingResult result = checkData(user.getUsername(), 1);
		if(!(boolean)result.getData()) {
			return shoppingResult.build(400, "注册失败");
		}
		if(
				StringUtils.isEmpty(user.getPassword())) {
			return shoppingResult.build(400, "注册失败");
		}
		 
		if(StringUtils.isEmpty(user.getPhone())) {
			shoppingResult result2 = checkData(user.getUsername(), 2);
			if(!(boolean)result2.getData()) {
				return shoppingResult.build(400, "注册失败");
			}
		}
		if(StringUtils.isEmpty(user.getEmail())) {
			shoppingResult result2 = checkData(user.getEmail(),3);
			if(!(boolean)result2.getData()){
				return shoppingResult.build(400, "注册失败. 请校验数据后请再提交数据");
		}
		
	}
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		String md5password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5password);
		usermapper.insertSelective(user);
		return shoppingResult.ok();
	}
}

