package com.shopping.portal.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.common.util.JsonUtils;
import com.shopping.portal.pojo.Ad1Node;
import com.shopping.content.service.ContentService;
import com.shopping.pojo.TbContent;

@Controller
public class IndexController {
		@Autowired
		private ContentService contentService;
		
		@Value("${AD1_CATEGORY_ID}")
		private Long AD1_CATEGORY_ID;
		
		@Value("${AD1_HEIGHT}")
		private String AD1_HEIGHT;
		@Value("${AD1_HEIGHT_B}")
		private String AD1_HEIGHT_B;
		@Value("${AD1_WIDTH}")
		private String AD1_WIDTH;
		@Value("${AD1_WIDTH_B}")
		private String AD1_WIDTH_B;

		@RequestMapping("/index")
		public String showIndex(Model model) {
			
			List<TbContent> list = contentService.getContentList(AD1_CATEGORY_ID);
			List<Ad1Node> nodes = new ArrayList<Ad1Node>();
			
			for(TbContent tbContent : list) {
				Ad1Node node = new Ad1Node();
				node.setAlt(tbContent.getSubTitle());
				node.setHref(tbContent.getUrl());
				node.setSrc(tbContent.getPic());
				node.setSrcB(tbContent.getPic2());
				node.setHeight(AD1_HEIGHT);
				node.setHeightB(AD1_HEIGHT_B);
				node.setWidth(AD1_WIDTH);
				node.setWidthB(AD1_WIDTH_B);
				nodes.add(node);
			}
			String json = JsonUtils.objectToJson(nodes);
			model.addAttribute("ad1", json);
			return "index";
		}
		
	
	
}
