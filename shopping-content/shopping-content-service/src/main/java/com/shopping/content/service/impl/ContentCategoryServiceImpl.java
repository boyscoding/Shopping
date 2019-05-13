package com.shopping.content.service.impl;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.pojo.shoppingResult;
import com.shopping.content.service.ContentCategoryService;

import com.shopping.mapper.TbContentCategoryMapper;
import com.shopping.pojo.TbContent;
import com.shopping.pojo.TbContentCategory;
import com.shopping.pojo.TbContentCategoryExample;
import com.shopping.pojo.TbContentCategoryExample.Criteria;


@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper mapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
 
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = mapper.selectByExample(example);
		List<EasyUITreeNode> resuList = new ArrayList<EasyUITreeNode>();
		for(TbContentCategory tb : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tb.getId());
			node.setText(tb.getName());
			node.setState(tb.getIsParent()?"closed":"open");
			resuList.add(node);
		}
		return resuList;
	}

	@Override
	public shoppingResult addContentCategory(long parentId, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setIsParent(false);
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		mapper.insert(tbContentCategory);
		TbContentCategory parentNode = mapper.selectByPrimaryKey(parentId);
		if(!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			mapper.updateByPrimaryKey(parentNode);
			 
		}
		
		
		return shoppingResult.ok(tbContentCategory);
	}

	@Override
	public shoppingResult updateContentCategory(String name) {
		TbContent tbContent = new TbContent();
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		
		return null;
	}


}
