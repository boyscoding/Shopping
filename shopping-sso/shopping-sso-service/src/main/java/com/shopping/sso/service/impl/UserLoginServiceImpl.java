package com.shopping.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.common.util.JsonUtils;
import com.shopping.mapper.TbUserMapper;
import com.shopping.pojo.TbUser;
import com.shopping.pojo.TbUserExample;
import com.shopping.pojo.TbUserExample.Criteria;
import com.shopping.sso.jedis.JedisClient;
import com.shopping.sso.service.UserLoginService;
@Service
public class UserLoginServiceImpl implements UserLoginService {
	@Autowired
	private TbUserMapper usermapper;
	@Autowired
	private JedisClient client;
	
	@Value("${USER_INFO}")
	private String USER_INFO;
	@Value("${EXPIRE_TIME}")
	private int EXPIRE_TIME;
	
	@Override
	public shoppingResult login(String username, String password) {
		if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)) {
			return shoppingResult.build(400, "账号密码错误");
		}
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = usermapper.selectByExample(example);
		if(list == null && list.size() == 0 ) {
			return shoppingResult.build(400, "账号密码错误");
		}
		TbUser user = list.get(0);
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
		if(!md5DigestAsHex.equals(user.getPassword())) {
			return shoppingResult.build(400, "账号密码错误");
		}
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		client.set(USER_INFO+":"+token, JsonUtils.objectToJson(user));
		client.expire(USER_INFO+":"+token, EXPIRE_TIME);
		// cookie 在表现层 并且要跨域
		return shoppingResult.ok(token);
	}

	@Override
	public shoppingResult getUserByToken(String token) {
		String string = client.get(USER_INFO + ":" + token);
		if(StringUtils.isNotBlank(string)) {
			TbUser user = JsonUtils.jsonToPojo(string, TbUser.class);
			client.expire(USER_INFO+ ":" +token, EXPIRE_TIME);
			return shoppingResult.ok(user);
		}
		return shoppingResult.build(400, "重新登陆");
	}

}
