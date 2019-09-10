package com.shopping.service;

import java.util.List;

import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.pojo.shoppingResult;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemCat;
import com.shopping.pojo.TbItemDesc;

public interface ItemService {

	public EasyUIDataGridResult getItemList(Integer page, Integer rows);
	public shoppingResult saveItem(TbItem item, String desc);
	public TbItemDesc getItemDescById(Long itemId);
	public TbItem getItemById(Long itemId);
	public List<EasyUITreeNode> getItemCatListByParentId(Long parentId);
}
