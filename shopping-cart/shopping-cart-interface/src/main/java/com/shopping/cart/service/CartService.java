package com.shopping.cart.service;

import java.util.List;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.pojo.TbItem;

public interface CartService {
/**
 * 添加购物车
 * 
 */
	public shoppingResult addItemCart(TbItem tbitem,Integer num,Long userId );
	
/**
 * 根据用户ID查询用户的购物车列表
 */
	public List<TbItem> getCartList(Long userId);
/**
 * 根据商品的ID更新数量
 */
	public shoppingResult updateItemCartByItemId(Long userId,Long itemId,Integer num);
/**
* 根据商品的ID删除数量
*/	
	public shoppingResult deleteItemCartByItemId(Long userId,Long itemId);

}
