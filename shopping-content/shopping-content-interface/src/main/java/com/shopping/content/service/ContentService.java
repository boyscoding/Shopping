package com.shopping.content.service;

import java.util.List;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.pojo.shoppingResult;
import com.shopping.pojo.TbContent;

public interface ContentService {
	
	public List<TbContent> getContentList(long cid);
	public shoppingResult saveContent(TbContent tbContent);
}
