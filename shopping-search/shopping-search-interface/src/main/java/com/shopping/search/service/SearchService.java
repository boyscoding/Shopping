package com.shopping.search.service;



import com.shopping.common.pojo.SearchResult;
import com.shopping.common.pojo.shoppingResult;

public interface SearchService {

	public shoppingResult importAllSearchItems()  throws Exception;
	public SearchResult search(String queryString ,Integer page,Integer rows) throws Exception;
	public shoppingResult updateSearchItemById(Long itemId) throws Exception;
	
}
