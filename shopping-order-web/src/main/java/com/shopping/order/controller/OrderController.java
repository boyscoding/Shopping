package com.shopping.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.common.util.CookieUtils;
import com.shopping.common.util.JsonUtils;
import com.shopping.cart.service.CartService;
import com.shopping.order.pojo.OrderInfo;
import com.shopping.order.service.OrderService;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbUser;
import com.shopping.sso.service.UserLoginService;

@Controller
public class OrderController {
	
	@Autowired
	private UserLoginService loginservice;
	@Autowired
	private CartService cartservice;
	@Autowired
	private OrderService orderservice;
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	@Value("${TT_CART_KEY}")
	private String TT_CART_KEY;
	
	@RequestMapping("/order/order-cart")
	public String showOrder(HttpServletRequest request,HttpServletResponse response) {
//		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
//		if(StringUtils.isNotBlank(token)) {
//			TaotaoResult result = loginservice.getUserByToken(token);
//			if(result.getStatus() == 200) {
//				TbUser user = (TbUser) result.getData();
//				 List<TbItem> cartList2 = getCartList(request);
//				 List<TbItem> cartList = cartservice.getCartList(user.getId());
//				 for(TbItem tbItem : cartList2) {
//					 boolean flag = false;
//					 if(cartList != null) {
//						 for(TbItem tbItem1 : cartList) {
//							 if(tbItem1.getId() == tbItem.getId().longValue()) {
//								 tbItem1.setNum(tbItem1.getNum() + tbItem.getNum());
//								 cartservice.updateItemCartByItemId(user.getId(), tbItem1.getId(),tbItem1.getNum());
//								 flag = true;
//							 }
//						 }
//					 }
//					 if(flag = false) {
//						 cartservice.addItemCart( tbItem, tbItem.getNum(),user.getId());
//					 }
//				}
//				 if(!cartList2.isEmpty()) {
//					 CookieUtils.deleteCookie(request, response, TT_CART_KEY);
//				 }
//				 cartList = cartservice.getCartList(user==null?-1:user.getId());
//				 request.setAttribute("cartList", cartList);
//			}
//		}
		 
		TbUser user = (TbUser) request.getAttribute("USER_INFO");
		 List<TbItem> cartList2 = getCartList(request);
		 List<TbItem> cartList = cartservice.getCartList(user.getId());
		 for(TbItem tbItem : cartList2) {
			 boolean flag = false;
			 if(cartList != null) {
				 for(TbItem tbItem1 : cartList) {
					 if(tbItem1.getId() == tbItem.getId().longValue()) {
						 tbItem1.setNum(tbItem1.getNum() + tbItem.getNum());
						 cartservice.updateItemCartByItemId(user.getId(), tbItem1.getId(),tbItem1.getNum());
						 flag = true;
					 }
				 }
			 }
			 if(flag = false) {
				 cartservice.addItemCart( tbItem, tbItem.getNum(),user.getId());
			 }
		}
		 if(!cartList2.isEmpty()) {
			 CookieUtils.deleteCookie(request, response, TT_CART_KEY);
		 }
		 cartList = cartservice.getCartList(user==null?-1:user.getId());
		 request.setAttribute("cartList", cartList);
		 return "order-cart";
	}
	
	
	
	private List<TbItem> getCartList(HttpServletRequest request) {
		String jsonString  =CookieUtils.getCookieValue(request, TT_TOKEN_KEY,true);
		List<TbItem> list = new ArrayList<TbItem>();
		if(StringUtils.isNotBlank(jsonString)) {
			list = JsonUtils.jsonToList(jsonString, TbItem.class);
		}
		return list;
	}
	@RequestMapping(value="/order/create",method=RequestMethod.POST)
	public String createOrder(HttpServletRequest request,OrderInfo info) {
		TbUser user = (TbUser) request.getAttribute("USER_INFO");
		info.setUserId(user.getId());
		info.setBuyerNick(user.getUsername());
		shoppingResult result = orderservice.createOrder(info);
		request.setAttribute("orderId", result.getData());
		request.setAttribute("payment", info.getPayment());
		DateTime dateTime = new DateTime();
		DateTime plusDays = dateTime.plusDays(3);//加3天
		request.setAttribute("date", plusDays.toString("yyyy-MM-dd"));
		return "success";
		
		
	}
}
