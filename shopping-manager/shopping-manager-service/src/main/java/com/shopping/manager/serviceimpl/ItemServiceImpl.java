package com.shopping.manager.serviceimpl;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import com.shopping.pojo.TbItemCatExample.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.pojo.shoppingResult;
import com.shopping.common.util.IDUtils;
import com.shopping.common.util.JsonUtils;
import com.shopping.manager.jedis.JedisClient;
import com.shopping.mapper.TbItemCatMapper;
import com.shopping.mapper.TbItemDescMapper;
import com.shopping.mapper.TbItemMapper;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemCat;
import com.shopping.pojo.TbItemCatExample;
import com.shopping.pojo.TbItemDesc;
import com.shopping.pojo.TbItemExample;
import com.shopping.service.ItemService;

@Service
public class ItemServiceImpl  implements ItemService{
	@Autowired
	private JmsTemplate jmstemplate;
	@Autowired
	private TbItemDescMapper descmapper;
	@Autowired
	private TbItemMapper mapper;
	@Resource(name = "topicDestination")
	private Destination destination;
	@Autowired
	private JedisClient client;
	@Value("${ITEM_INFO_KEY)")
	private String ITEM_INFO_KEY;
	@Value("${ITEM_INFO_KEY_EXPIRE}")
	private Integer ITEM_INFO_KEY_EXPIRE;
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = mapper.selectByExample(example);
		PageInfo<TbItem> info = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int)info.getTotal());
		result.setRows(info.getList());
		return result;
	}
	@Override
	public shoppingResult saveItem(TbItem item, String desc) {
			// 生成商品的id
				final long itemId = IDUtils.genItemId();
				// 1.补全item 的其他属性
				item.setId(itemId);
				item.setCreated(new Date());
				// 1-正常，2-下架，3-删除',
				item.setStatus((byte) 1);
				item.setUpdated(item.getCreated());
				// 2.插入到item表 商品的基本信息表
				mapper.insertSelective(item);
				// 3.补全商品描述中的属性
				TbItemDesc desc2 = new TbItemDesc();
				desc2.setItemDesc(desc);
				desc2.setItemId(itemId);
				desc2.setCreated(item.getCreated());
				desc2.setUpdated(item.getCreated());
				// 4.插入商品描述数据
				// 注入tbitemdesc的mapper
				descmapper.insertSelective(desc2);

				// 添加发送消息的业务逻辑
				jmstemplate.send(destination, new MessageCreator() {

					@Override
					public Message createMessage(Session session) throws JMSException {
						// 发送的消息
						return session.createTextMessage(itemId + "");
					}
				});
				// 5.返回shoppingresult
				return shoppingResult.ok();
			}
	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		// 查询缓存
				try {
					if (itemId != null) {
						// 从缓存中查询
						String jsonstring = client.get(ITEM_INFO_KEY + ":" + itemId + ":DESC");
						if (StringUtils.isNotBlank(jsonstring)) {// 不为空则直接返回
							client.expire(ITEM_INFO_KEY + ":" + itemId + ":DESC", ITEM_INFO_KEY_EXPIRE);
							return JsonUtils.jsonToPojo(jsonstring, TbItemDesc.class);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

		TbItemDesc tbItemDesc = descmapper.selectByPrimaryKey(itemId);
		// 添加缓存
		try {
			// 注入redisclient
			if (tbItemDesc != null){
				client.set(ITEM_INFO_KEY + ":" + itemId + ":DESC", JsonUtils.objectToJson(tbItemDesc));
				client.expire(ITEM_INFO_KEY + ":" + itemId + ":DESC", ITEM_INFO_KEY_EXPIRE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tbItemDesc;
	}
	@Override
	public TbItem getItemById(Long itemId) {
		try {
			if( itemId != null) {
				String jsonstring = client.get(ITEM_INFO_KEY+":"+itemId+":");
				if(StringUtils.isNotBlank(jsonstring)) {
					client.expire(ITEM_INFO_KEY+":"+itemId+":BASE", ITEM_INFO_KEY_EXPIRE);
					return JsonUtils.jsonToPojo(jsonstring, TbItem.class);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem  tbItem  = mapper.selectByPrimaryKey(itemId);
		try {
			if(tbItem != null) {
				client.set(ITEM_INFO_KEY+":"+itemId+":BASE", JsonUtils.objectToJson(tbItem));
				client.expire(ITEM_INFO_KEY+":"+itemId+":BASE", ITEM_INFO_KEY_EXPIRE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItem;
	}
	@Autowired
	private TbItemCatMapper catmapper;
	@Override
	public List<EasyUITreeNode> getItemCatListByParentId(Long parentId) {
		//1.注入mapper
		//2.创建example
		TbItemCatExample example = new TbItemCatExample();
		//3.设置查询的条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);//select *ｆｒｏｍ　ｔｂｉｔｅｍｃａｔ　ｗｈｅｒｅ　ｐａｒｅｎｔＩｄ＝１
		//4.执行查询  list<ibitemCat>
		List<TbItemCat> list = catmapper.selectByExample(example);
		//5.转成需要的数据类型List<EasyUITreeNode>
		List<EasyUITreeNode> nodes = new ArrayList<>();
		for (TbItemCat cat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(cat.getId());
			node.setText(cat.getName());
			node.setState(cat.getIsParent()?"closed":"open");//"open",closed
			nodes.add(node);
		}
		//6.返回
		return nodes;
	}
 
	
	
	
}
