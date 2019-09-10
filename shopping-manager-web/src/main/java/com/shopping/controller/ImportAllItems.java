package com.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.common.pojo.SearchItem;
import com.shopping.common.pojo.shoppingResult;
import com.shopping.search.service.SearchService;


public class ImportAllItems {
//	@Autowired
//	private SearchService searchService;
//	
//	@RequestMapping("/index/importAll")
//	@ResponseBody
//	public TaotaoResult importAllItems() {
//		try {
//			TaotaoResult result = searchService.importAllSearchItems();
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return TaotaoResult.build(500, "导入数据失败");
//		}
//	}
}
