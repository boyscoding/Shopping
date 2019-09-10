package com.shopping.order.service;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.order.pojo.OrderInfo;

public interface OrderService {
	public shoppingResult createOrder(OrderInfo orderInfo);
}
