package com.shopping.content.service;

import java.util.List;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.pojo.shoppingResult;

public interface ContentCategoryService {
	
	public List<EasyUITreeNode> getContentCategoryList(long parentId);
	public shoppingResult addContentCategory(long parentId,String name);
	public shoppingResult updateContentCategory(String name);
	//public List<TbContent> getContentList(long cid);
}
