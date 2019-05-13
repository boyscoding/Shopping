package com.shopping.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shopping.common.pojo.shoppingResult;
import com.shopping.common.util.JsonUtils;
import com.shopping.content.jedis.JedisClient;
import com.shopping.content.service.ContentService;
import com.shopping.mapper.TbContentMapper;
import com.shopping.pojo.TbContent;
import com.shopping.pojo.TbContentExample;
import com.shopping.pojo.TbContentExample.Criteria;


@Service
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private TbContentMapper mapper;
	
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	
	@Override
	public List<TbContent> getContentList(long cid) {
		try {
			String json = jedisClient.hget(CONTENT_KEY, cid+"");
			if(StringUtils.isNotBlank(json)) {
				System.out.println("查找缓存");
				return JsonUtils.jsonToList(json, TbContent.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = mapper.selectByExample(example);
		try {
			System.out.println("没有缓存，正在添加缓存。。。");
			jedisClient.hset(CONTENT_KEY, cid+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}


	@Override
	public shoppingResult saveContent(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(tbContent.getCreated());
		mapper.insertSelective(tbContent);
		try {
			jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId()+"");
			System.out.println("当插入时，清空缓存!!!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return shoppingResult.ok();}

}
