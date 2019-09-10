package com.shopping.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.shopping.search.service.impl.SearchServiceImpl;

public class ItemChangeMessageListener implements MessageListener{
	//注入service 直接调用方法更新
		//判断消息的类型是否为textmessage
		//如果是 则获取商品ID
	@Autowired
	private SearchServiceImpl searchItemService;

		@Override
		public void onMessage(Message message) {
			try {
				TextMessage textMessage = null;
				Long itemId = null; 
				//取商品id
				if (message instanceof TextMessage) {
					textMessage = (TextMessage) message;
					itemId = Long.parseLong(textMessage.getText());
				}
				//向索引库添加文档
				searchItemService.updateSearchItemById(itemId);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
