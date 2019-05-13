package com.shopping.cart.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.common.util.CookieUtils;
import com.shopping.common.util.JsonUtils;
import com.shopping.cart.service.CartService;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbUser;
import com.shopping.service.ItemService;
import com.shopping.sso.service.UserLoginService;

@Controller
public class CartController {
	
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private UserLoginService loginservice;
	@Autowired
	private CartService cartservice;
	
	

	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	@Value("${TT_CART_KEY}")
	private String TT_CART_KEY;
	
	
	
	@RequestMapping("/cart/add/{itemId}")
	public String addItemCart(@PathVariable Long itemId,Integer num,HttpServletRequest request,HttpServletResponse response) {
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		//调用SSO的服务查询用户的信息
		shoppingResult result = loginservice.getUserByToken(token);
		//获取商品数据
		TbItem tbItem = itemService.getItemById(itemId);
		if(result.getStatus() == 200) {
			System.out.println(TT_CART_KEY);
			TbUser user = (TbUser) result.getData();
			//添加购物车
			cartservice.addItemCart(tbItem, num, user.getId());
		}
		else {
			List<TbItem> cartList = getCartListFromCookie(request);
			//判断是否购物车是否存在商品
			boolean flag = false;
			for(TbItem tbItem2 : cartList) {
				//如果直接tbItem.getId() == itemId的话，会比较的是地址
				//所以要把他转换成基本类型
				if(tbItem2.getId() == itemId.longValue()) {
					tbItem2.setNum(tbItem2.getNum()+num);
					flag = true;
					break;
				}
			}
				//因为是个for循环，所以不清楚什么时候获取到了信息所以加个for循环
				if(flag == true) {
					CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList),7*24*3600,true);
					}
				else {
						TbItem tbItem3 = itemService.getItemById(itemId);
						tbItem3.setNum(num);
						String image = tbItem3.getImage();
						if(StringUtils.isNotBlank(image)) {
							tbItem3.setImage(image.split(",")[0]);
					}
						//添加到购物车中
						cartList.add(tbItem3);
						CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList),7*24*3600,true);
				 
			}
			
		}
		return "cartSuccess";
		
	}
	@RequestMapping("/cart/cart")
	public String getCartList(HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		shoppingResult result = loginservice.getUserByToken(token);
		if(result.getStatus() == 200) {
			TbUser user = (TbUser) result.getData();
			List<TbItem> cartList = cartservice.getCartList(user.getId());
			request.setAttribute("cartList", cartList);
		}
		else {
			List<TbItem> cartList = getCartListFromCookie(request);
			request.setAttribute("cartList", cartList);
		}
		return "cart";
	}
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public shoppingResult updateItemCartByItemId(@PathVariable Long itemId,@PathVariable Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		shoppingResult result = loginservice.getUserByToken(token);
		if(result.getStatus() == 200) {
			TbUser user = (TbUser) result.getData();
			cartservice.updateItemCartByItemId(user.getId(), itemId, num);
			
		}else {
			updateCookieItemCart(itemId,num,request,response);
		}
		return shoppingResult.ok();
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteItemCartByItemId(@PathVariable Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		// 1.引入服务
		// 2.注入服务

		// 3.判断用户是否登录
		// 从cookie中获取用户的token信息
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		// 调用SSO的服务查询用户的信息
		shoppingResult result = loginservice.getUserByToken(token);

		// 获取商品的数据
		if (result.getStatus() == 200) {
			// 4.如果已登录，调用service的方法
			TbUser user = (TbUser) result.getData();
			// 删除
			cartservice.deleteItemCartByItemId(user.getId(), itemId);
		} else {
			// 5.如果没有登录 调用cookie的方法 删除cookie中的商品
			deleteCookieItemCartByItemId(itemId, request, response);
		}
		return "redirect:/cart/cart.html";// 重定向
	}
	
	
	
	
	private void updateCookieItemCart(Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {

		List<TbItem> cartList = getCartListFromCookie(request);
		boolean flag = false;
		for(TbItem tbItem : cartList) {
			if(tbItem.getId() == itemId.longValue()) {
				tbItem.setNum(num);
				flag = true;
				break;
			}
		}
		if(flag == true) {
			CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList),7 * 24 * 3600,
					true);
		}else {
			TbItem tbItem4 = itemService.getItemById(itemId);
		tbItem4.setNum(num);
		String image = tbItem4.getImage();
		if(StringUtils.isNotBlank(image)) {
			tbItem4.setImage(image.split(",")[0]);
	}
		//添加到购物车中
		cartList.add(tbItem4);
		CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList),7*24*3600,true);
}
	}
	
	private void deleteCookieItemCartByItemId(Long itemId, HttpServletRequest request,	HttpServletResponse response) {
		List<TbItem> cartList = getCartListFromCookie(request);
		boolean flag = false;
		for(TbItem tbItem : cartList) {
			if(tbItem.getId() == itemId.longValue()) {
				cartList.remove(tbItem);
				flag = true;
				break;
			}
		}
		if(flag == true) {
			CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList),7 * 24 * 3600,
					true);
		}
	}
	private List<TbItem> getCartListFromCookie(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request, TT_CART_KEY, true);
		if(StringUtils.isBlank(json)) {
			return new ArrayList<TbItem>();
		}
		List<TbItem> jsonToList = JsonUtils.jsonToList(json, TbItem.class);
		return jsonToList;
	}
}
