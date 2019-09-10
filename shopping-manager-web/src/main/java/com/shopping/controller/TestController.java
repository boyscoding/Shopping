package com.shopping.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.pojo.EasyUIDataGridResult;
import com.shopping.common.pojo.EasyUITreeNode;
import com.shopping.common.pojo.shoppingResult;
import com.shopping.content.service.ContentCategoryService;
import com.shopping.content.service.ContentService;
import com.shopping.pojo.TbContent;
import com.shopping.pojo.TbItem;
import com.shopping.pojo.TbItemCat;
import com.shopping.search.service.SearchService;
import com.shopping.service.ItemService;

@Controller
public class TestController {
	@Autowired
	private SearchService searchService;
	@Autowired
	private ItemService itemservice;
	@Autowired
	private ContentCategoryService contentCategoryService;
	@Autowired
	private ContentService contentService;
	/**
	 * 展示首页
	 * @return
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	/**
	 * 展示菜单首页
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	//如果获取的名称与String名称不相同，可以使用(@PathVariable(value=page2) String page2)
	public String showItemList(@PathVariable String page) {
		return page;
	}
	/**
	 * 
	 * @param page
	 * @return
	 */
	
	@RequestMapping("/query/{page}")
	@ResponseBody
	//如果获取的名称与String名称不相同，可以使用(@PathVariable(value=page2) String page2)
	public  EasyUIDataGridResult getItemList(Integer page, Integer rows) {
	 
		return itemservice.getItemList(page, rows); 
	}
	/**
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	//如果获取的名称与String名称不相同，可以使用(@PathVariable(value=page2) String page2)
	public  List<EasyUITreeNode> getContentCatList(@RequestParam(defaultValue = "0") Long id) {
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(id);
		return list;  
	}
	@RequestMapping("/content/category/create")
	@ResponseBody
	public shoppingResult createCategory(Long parentId, String name) {
		shoppingResult result = contentCategoryService.addContentCategory(parentId, name);
		return result;
	}
	
	@RequestMapping("/index/importAll")
	@ResponseBody
	public shoppingResult importAllItems() {
		try {
			shoppingResult result = searchService.importAllSearchItems();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return shoppingResult.build(500, "导入数据失败");
		}
	}

	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public shoppingResult save(TbItem item,String desc) {
		try {
			return itemservice.saveItem(item, desc);
		} catch (Exception e) {
			e.printStackTrace();
			return shoppingResult.build(500, "保存失败");
		}
	}
	//url:'/item/cat/list',
	//参数：id
	//返回值：json
	//method:get post
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0") Long parentId){
		//1.引入服务
		//2.注入服务
		//3.调用方法
		List<EasyUITreeNode> list = itemservice.getItemCatListByParentId(parentId);
		return list;
	}
}
