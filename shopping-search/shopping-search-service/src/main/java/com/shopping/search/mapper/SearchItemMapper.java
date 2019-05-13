package com.shopping.search.mapper;

import java.util.List;

import com.shopping.common.pojo.SearchItem;

public interface SearchItemMapper {
	
	public List<SearchItem> getSearchItemList();
	public SearchItem getSearchItemById(Long id);
}
