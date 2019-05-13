package com.shopping.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.item.pojo.Item;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemDesc;
import com.shopping.service.ItemService;

@Controller
public class ItemController {
//	@Autowired
//	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private ItemService itemservice;

	@RequestMapping("/item/{itemId}")
	public String getItem(@PathVariable Long itemId, Model model) {
		// 1.引入服务
		// 2.注入服务
		// 3.调用服务的方法
		// 商品的基本信息 tbitem 没有getImages
		TbItem tbItem = itemservice.getItemById(itemId);
		// 商品的描述信息
		TbItemDesc itemDesc = itemservice.getItemDescById(itemId);
		// 4.tbitem 转成 item
		Item item = new Item(tbItem);
		// 5.传递数据到页面中
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
//	}
//	@RequestMapping("/genhtml")
//	@ResponseBody
//	public String genHtml() throws Exception{
//		Configuration configuration = freeMarkerConfigurer.getConfiguration();
//		Template template = configuration.getTemplate("template.ftl");
//		Map dataModel = new HashMap<>();
//		dataModel.put("springtestkey", "1000");
//		Writer out = new FileWriter(new File("D:/mycode/eclipse/freemarker/springtestfreemarker.html"));
//		template.process(dataModel, out);
//		out.close();
//		return "OK";
//		
	}
	
}
