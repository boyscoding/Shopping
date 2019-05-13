package com.shopping.cart.service.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shopping.cart.jedis.JedisClient;
import com.shopping.cart.service.CartService;
import com.shopping.common.pojo.shoppingResult;
import com.shopping.common.util.JsonUtils;
import com.shopping.pojo.TbItem;
@Service
public class CartServiceImpl implements CartService{
	@Value("${TT_CART_REDIS_PRE_KEY}")
	private String TT_CART_REDIS_PRE_KEY;
	@Autowired
	private JedisClient client;
	//登录状态下添加购物车
	@Override
	public shoppingResult addItemCart(TbItem tbitem, Integer num, Long userId) {
		TbItem item = queryItemByItemIdAndUserId(tbitem.getId(),userId);
		if(item != null) {
			item.setNum(item.getNum()+num);
			client.hset(TT_CART_REDIS_PRE_KEY+":"+userId+"", item.getId()+"", JsonUtils.objectToJson(item));
		}else {
			item.setNum(num);
			if(item.getImage() != null) {
				item.setImage(item.getImage().split(",")[0]);
			}
			client.hset(TT_CART_REDIS_PRE_KEY+":"+userId+"", item.getId()+"", JsonUtils.objectToJson(item));
		}
		return shoppingResult.ok();
	}
	private TbItem queryItemByItemIdAndUserId(Long itemId, Long userId) {
		String string = client.hget(TT_CART_REDIS_PRE_KEY+":"+userId+"", itemId+"");
		if(StringUtils.isBlank(string)) {
			TbItem tbItem = JsonUtils.jsonToPojo(string, TbItem.class);
			return tbItem;
		}
		return null;
	}
	@Override
	public List<TbItem> getCartList(Long userId) {
		Map<String,String> map =  client.hgetAll(TT_CART_REDIS_PRE_KEY+":"+userId + "");
		List<TbItem> list = new ArrayList<TbItem>();
		if(map != null) {
			for(Map.Entry<String, String> entry : map.entrySet()) {
				String value = entry.getValue();
				TbItem item = JsonUtils.jsonToPojo(value, TbItem.class);
				list.add(item);
			}
		}
		return list;
	}

	@Override
	public shoppingResult updateItemCartByItemId(Long userId, Long itemId, Integer num) {
		TbItem tbItem = queryItemByItemIdAndUserId(itemId, userId);
		if(tbItem != null) {
			tbItem.setNum(num);
			client.hset(TT_CART_REDIS_PRE_KEY+":"+userId, itemId+"", JsonUtils.objectToJson(tbItem));
		}
		return shoppingResult.ok();
	}

	@Override
	public shoppingResult deleteItemCartByItemId(Long userId, Long itemId) {
		client.hdel(TT_CART_REDIS_PRE_KEY+":"+userId, itemId+"");
		return shoppingResult.ok();
	}

 

}
