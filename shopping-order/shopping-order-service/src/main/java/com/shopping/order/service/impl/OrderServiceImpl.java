package com.shopping.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.mapper.TbOrderItemMapper;
import com.shopping.mapper.TbOrderMapper;
import com.shopping.mapper.TbOrderShippingMapper;
import com.shopping.order.jedis.JedisClient;
import com.shopping.order.pojo.OrderInfo;
import com.shopping.order.service.OrderService;
import com.shopping.pojo.TbOrderItem;
import com.shopping.pojo.TbOrderShipping;

public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_INIT}")
	private String ORDER_ID_INIT;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;

	@Override
	public shoppingResult createOrder(OrderInfo orderInfo) {
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)) {
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_INIT);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		orderInfo.setOrderId(orderId);
		orderInfo.setPostFee("0");
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		orderMapper.insert(orderInfo);
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for(TbOrderItem tbOrderItem : orderItems) {
			Long orderItemId = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY);
			tbOrderItem.setId(orderItemId.toString());
			tbOrderItem.setOrderId(orderId);
			orderItemMapper.insert(tbOrderItem);
			
		}
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		
		return shoppingResult.ok(orderId);
	}

}
